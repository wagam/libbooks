package com.mag.libbooks.web.rest;

import com.mag.libbooks.LibBooksApp;

import com.mag.libbooks.domain.BookMetadatas;
import com.mag.libbooks.repository.BookMetadatasRepository;
import com.mag.libbooks.repository.search.BookMetadatasSearchRepository;
import com.mag.libbooks.service.dto.BookMetadatasDTO;
import com.mag.libbooks.service.mapper.BookMetadatasMapper;
import com.mag.libbooks.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.mag.libbooks.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BookMetadatasResource REST controller.
 *
 * @see BookMetadatasResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LibBooksApp.class)
public class BookMetadatasResourceIntTest {

    private static final Integer DEFAULT_NUMBER_OF_COMMENTS = 1;
    private static final Integer UPDATED_NUMBER_OF_COMMENTS = 2;

    private static final Integer DEFAULT_NUMBER_OF_LIKES = 1;
    private static final Integer UPDATED_NUMBER_OF_LIKES = 2;

    private static final Integer DEFAULT_NOTATION = 1;
    private static final Integer UPDATED_NOTATION = 2;

    @Autowired
    private BookMetadatasRepository bookMetadatasRepository;

    @Autowired
    private BookMetadatasMapper bookMetadatasMapper;

    @Autowired
    private BookMetadatasSearchRepository bookMetadatasSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBookMetadatasMockMvc;

    private BookMetadatas bookMetadatas;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BookMetadatasResource bookMetadatasResource = new BookMetadatasResource(bookMetadatasRepository, bookMetadatasMapper, bookMetadatasSearchRepository);
        this.restBookMetadatasMockMvc = MockMvcBuilders.standaloneSetup(bookMetadatasResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookMetadatas createEntity(EntityManager em) {
        BookMetadatas bookMetadatas = new BookMetadatas()
            .numberOfComments(DEFAULT_NUMBER_OF_COMMENTS)
            .numberOfLikes(DEFAULT_NUMBER_OF_LIKES)
            .notation(DEFAULT_NOTATION);
        return bookMetadatas;
    }

    @Before
    public void initTest() {
        bookMetadatasSearchRepository.deleteAll();
        bookMetadatas = createEntity(em);
    }

    @Test
    @Transactional
    public void createBookMetadatas() throws Exception {
        int databaseSizeBeforeCreate = bookMetadatasRepository.findAll().size();

        // Create the BookMetadatas
        BookMetadatasDTO bookMetadatasDTO = bookMetadatasMapper.toDto(bookMetadatas);
        restBookMetadatasMockMvc.perform(post("/api/book-metadatas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookMetadatasDTO)))
            .andExpect(status().isCreated());

        // Validate the BookMetadatas in the database
        List<BookMetadatas> bookMetadatasList = bookMetadatasRepository.findAll();
        assertThat(bookMetadatasList).hasSize(databaseSizeBeforeCreate + 1);
        BookMetadatas testBookMetadatas = bookMetadatasList.get(bookMetadatasList.size() - 1);
        assertThat(testBookMetadatas.getNumberOfComments()).isEqualTo(DEFAULT_NUMBER_OF_COMMENTS);
        assertThat(testBookMetadatas.getNumberOfLikes()).isEqualTo(DEFAULT_NUMBER_OF_LIKES);
        assertThat(testBookMetadatas.getNotation()).isEqualTo(DEFAULT_NOTATION);

        // Validate the BookMetadatas in Elasticsearch
        BookMetadatas bookMetadatasEs = bookMetadatasSearchRepository.findOne(testBookMetadatas.getId());
        assertThat(bookMetadatasEs).isEqualToIgnoringGivenFields(testBookMetadatas);
    }

    @Test
    @Transactional
    public void createBookMetadatasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookMetadatasRepository.findAll().size();

        // Create the BookMetadatas with an existing ID
        bookMetadatas.setId(1L);
        BookMetadatasDTO bookMetadatasDTO = bookMetadatasMapper.toDto(bookMetadatas);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookMetadatasMockMvc.perform(post("/api/book-metadatas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookMetadatasDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BookMetadatas in the database
        List<BookMetadatas> bookMetadatasList = bookMetadatasRepository.findAll();
        assertThat(bookMetadatasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBookMetadatas() throws Exception {
        // Initialize the database
        bookMetadatasRepository.saveAndFlush(bookMetadatas);

        // Get all the bookMetadatasList
        restBookMetadatasMockMvc.perform(get("/api/book-metadatas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookMetadatas.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberOfComments").value(hasItem(DEFAULT_NUMBER_OF_COMMENTS)))
            .andExpect(jsonPath("$.[*].numberOfLikes").value(hasItem(DEFAULT_NUMBER_OF_LIKES)))
            .andExpect(jsonPath("$.[*].notation").value(hasItem(DEFAULT_NOTATION)));
    }

    @Test
    @Transactional
    public void getBookMetadatas() throws Exception {
        // Initialize the database
        bookMetadatasRepository.saveAndFlush(bookMetadatas);

        // Get the bookMetadatas
        restBookMetadatasMockMvc.perform(get("/api/book-metadatas/{id}", bookMetadatas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bookMetadatas.getId().intValue()))
            .andExpect(jsonPath("$.numberOfComments").value(DEFAULT_NUMBER_OF_COMMENTS))
            .andExpect(jsonPath("$.numberOfLikes").value(DEFAULT_NUMBER_OF_LIKES))
            .andExpect(jsonPath("$.notation").value(DEFAULT_NOTATION));
    }

    @Test
    @Transactional
    public void getNonExistingBookMetadatas() throws Exception {
        // Get the bookMetadatas
        restBookMetadatasMockMvc.perform(get("/api/book-metadatas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBookMetadatas() throws Exception {
        // Initialize the database
        bookMetadatasRepository.saveAndFlush(bookMetadatas);
        bookMetadatasSearchRepository.save(bookMetadatas);
        int databaseSizeBeforeUpdate = bookMetadatasRepository.findAll().size();

        // Update the bookMetadatas
        BookMetadatas updatedBookMetadatas = bookMetadatasRepository.findOne(bookMetadatas.getId());
        // Disconnect from session so that the updates on updatedBookMetadatas are not directly saved in db
        em.detach(updatedBookMetadatas);
        updatedBookMetadatas
            .numberOfComments(UPDATED_NUMBER_OF_COMMENTS)
            .numberOfLikes(UPDATED_NUMBER_OF_LIKES)
            .notation(UPDATED_NOTATION);
        BookMetadatasDTO bookMetadatasDTO = bookMetadatasMapper.toDto(updatedBookMetadatas);

        restBookMetadatasMockMvc.perform(put("/api/book-metadatas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookMetadatasDTO)))
            .andExpect(status().isOk());

        // Validate the BookMetadatas in the database
        List<BookMetadatas> bookMetadatasList = bookMetadatasRepository.findAll();
        assertThat(bookMetadatasList).hasSize(databaseSizeBeforeUpdate);
        BookMetadatas testBookMetadatas = bookMetadatasList.get(bookMetadatasList.size() - 1);
        assertThat(testBookMetadatas.getNumberOfComments()).isEqualTo(UPDATED_NUMBER_OF_COMMENTS);
        assertThat(testBookMetadatas.getNumberOfLikes()).isEqualTo(UPDATED_NUMBER_OF_LIKES);
        assertThat(testBookMetadatas.getNotation()).isEqualTo(UPDATED_NOTATION);

        // Validate the BookMetadatas in Elasticsearch
        BookMetadatas bookMetadatasEs = bookMetadatasSearchRepository.findOne(testBookMetadatas.getId());
        assertThat(bookMetadatasEs).isEqualToIgnoringGivenFields(testBookMetadatas);
    }

    @Test
    @Transactional
    public void updateNonExistingBookMetadatas() throws Exception {
        int databaseSizeBeforeUpdate = bookMetadatasRepository.findAll().size();

        // Create the BookMetadatas
        BookMetadatasDTO bookMetadatasDTO = bookMetadatasMapper.toDto(bookMetadatas);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBookMetadatasMockMvc.perform(put("/api/book-metadatas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bookMetadatasDTO)))
            .andExpect(status().isCreated());

        // Validate the BookMetadatas in the database
        List<BookMetadatas> bookMetadatasList = bookMetadatasRepository.findAll();
        assertThat(bookMetadatasList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBookMetadatas() throws Exception {
        // Initialize the database
        bookMetadatasRepository.saveAndFlush(bookMetadatas);
        bookMetadatasSearchRepository.save(bookMetadatas);
        int databaseSizeBeforeDelete = bookMetadatasRepository.findAll().size();

        // Get the bookMetadatas
        restBookMetadatasMockMvc.perform(delete("/api/book-metadatas/{id}", bookMetadatas.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean bookMetadatasExistsInEs = bookMetadatasSearchRepository.exists(bookMetadatas.getId());
        assertThat(bookMetadatasExistsInEs).isFalse();

        // Validate the database is empty
        List<BookMetadatas> bookMetadatasList = bookMetadatasRepository.findAll();
        assertThat(bookMetadatasList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBookMetadatas() throws Exception {
        // Initialize the database
        bookMetadatasRepository.saveAndFlush(bookMetadatas);
        bookMetadatasSearchRepository.save(bookMetadatas);

        // Search the bookMetadatas
        restBookMetadatasMockMvc.perform(get("/api/_search/book-metadatas?query=id:" + bookMetadatas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookMetadatas.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberOfComments").value(hasItem(DEFAULT_NUMBER_OF_COMMENTS)))
            .andExpect(jsonPath("$.[*].numberOfLikes").value(hasItem(DEFAULT_NUMBER_OF_LIKES)))
            .andExpect(jsonPath("$.[*].notation").value(hasItem(DEFAULT_NOTATION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookMetadatas.class);
        BookMetadatas bookMetadatas1 = new BookMetadatas();
        bookMetadatas1.setId(1L);
        BookMetadatas bookMetadatas2 = new BookMetadatas();
        bookMetadatas2.setId(bookMetadatas1.getId());
        assertThat(bookMetadatas1).isEqualTo(bookMetadatas2);
        bookMetadatas2.setId(2L);
        assertThat(bookMetadatas1).isNotEqualTo(bookMetadatas2);
        bookMetadatas1.setId(null);
        assertThat(bookMetadatas1).isNotEqualTo(bookMetadatas2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookMetadatasDTO.class);
        BookMetadatasDTO bookMetadatasDTO1 = new BookMetadatasDTO();
        bookMetadatasDTO1.setId(1L);
        BookMetadatasDTO bookMetadatasDTO2 = new BookMetadatasDTO();
        assertThat(bookMetadatasDTO1).isNotEqualTo(bookMetadatasDTO2);
        bookMetadatasDTO2.setId(bookMetadatasDTO1.getId());
        assertThat(bookMetadatasDTO1).isEqualTo(bookMetadatasDTO2);
        bookMetadatasDTO2.setId(2L);
        assertThat(bookMetadatasDTO1).isNotEqualTo(bookMetadatasDTO2);
        bookMetadatasDTO1.setId(null);
        assertThat(bookMetadatasDTO1).isNotEqualTo(bookMetadatasDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bookMetadatasMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bookMetadatasMapper.fromId(null)).isNull();
    }
}
