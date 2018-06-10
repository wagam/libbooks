package com.mag.libbooks.web.rest;

import com.mag.libbooks.LibBooksApp;

import com.mag.libbooks.domain.UserBook;
import com.mag.libbooks.repository.UserBookRepository;
import com.mag.libbooks.repository.search.UserBookSearchRepository;
import com.mag.libbooks.service.dto.UserBookDTO;
import com.mag.libbooks.service.mapper.UserBookMapper;
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

import com.mag.libbooks.domain.enumeration.BookStatus;
/**
 * Test class for the UserBookResource REST controller.
 *
 * @see UserBookResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LibBooksApp.class)
public class UserBookResourceIntTest {

    private static final BookStatus DEFAULT_STATUS = BookStatus.IS_READ;
    private static final BookStatus UPDATED_STATUS = BookStatus.WAIT_FOR_READ;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_LIKED = false;
    private static final Boolean UPDATED_IS_LIKED = true;

    @Autowired
    private UserBookRepository userBookRepository;

    @Autowired
    private UserBookMapper userBookMapper;

    @Autowired
    private UserBookSearchRepository userBookSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserBookMockMvc;

    private UserBook userBook;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserBookResource userBookResource = new UserBookResource(userBookRepository, userBookMapper, userBookSearchRepository);
        this.restUserBookMockMvc = MockMvcBuilders.standaloneSetup(userBookResource)
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
    public static UserBook createEntity(EntityManager em) {
        UserBook userBook = new UserBook()
            .status(DEFAULT_STATUS)
            .comment(DEFAULT_COMMENT)
            .isLiked(DEFAULT_IS_LIKED);
        return userBook;
    }

    @Before
    public void initTest() {
        userBookSearchRepository.deleteAll();
        userBook = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserBook() throws Exception {
        int databaseSizeBeforeCreate = userBookRepository.findAll().size();

        // Create the UserBook
        UserBookDTO userBookDTO = userBookMapper.toDto(userBook);
        restUserBookMockMvc.perform(post("/api/user-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userBookDTO)))
            .andExpect(status().isCreated());

        // Validate the UserBook in the database
        List<UserBook> userBookList = userBookRepository.findAll();
        assertThat(userBookList).hasSize(databaseSizeBeforeCreate + 1);
        UserBook testUserBook = userBookList.get(userBookList.size() - 1);
        assertThat(testUserBook.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUserBook.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testUserBook.isIsLiked()).isEqualTo(DEFAULT_IS_LIKED);

        // Validate the UserBook in Elasticsearch
        UserBook userBookEs = userBookSearchRepository.findOne(testUserBook.getId());
        assertThat(userBookEs).isEqualToIgnoringGivenFields(testUserBook);
    }

    @Test
    @Transactional
    public void createUserBookWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userBookRepository.findAll().size();

        // Create the UserBook with an existing ID
        userBook.setId(1L);
        UserBookDTO userBookDTO = userBookMapper.toDto(userBook);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserBookMockMvc.perform(post("/api/user-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userBookDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserBook in the database
        List<UserBook> userBookList = userBookRepository.findAll();
        assertThat(userBookList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserBooks() throws Exception {
        // Initialize the database
        userBookRepository.saveAndFlush(userBook);

        // Get all the userBookList
        restUserBookMockMvc.perform(get("/api/user-books?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userBook.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].isLiked").value(hasItem(DEFAULT_IS_LIKED.booleanValue())));
    }

    @Test
    @Transactional
    public void getUserBook() throws Exception {
        // Initialize the database
        userBookRepository.saveAndFlush(userBook);

        // Get the userBook
        restUserBookMockMvc.perform(get("/api/user-books/{id}", userBook.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userBook.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.isLiked").value(DEFAULT_IS_LIKED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUserBook() throws Exception {
        // Get the userBook
        restUserBookMockMvc.perform(get("/api/user-books/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserBook() throws Exception {
        // Initialize the database
        userBookRepository.saveAndFlush(userBook);
        userBookSearchRepository.save(userBook);
        int databaseSizeBeforeUpdate = userBookRepository.findAll().size();

        // Update the userBook
        UserBook updatedUserBook = userBookRepository.findOne(userBook.getId());
        // Disconnect from session so that the updates on updatedUserBook are not directly saved in db
        em.detach(updatedUserBook);
        updatedUserBook
            .status(UPDATED_STATUS)
            .comment(UPDATED_COMMENT)
            .isLiked(UPDATED_IS_LIKED);
        UserBookDTO userBookDTO = userBookMapper.toDto(updatedUserBook);

        restUserBookMockMvc.perform(put("/api/user-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userBookDTO)))
            .andExpect(status().isOk());

        // Validate the UserBook in the database
        List<UserBook> userBookList = userBookRepository.findAll();
        assertThat(userBookList).hasSize(databaseSizeBeforeUpdate);
        UserBook testUserBook = userBookList.get(userBookList.size() - 1);
        assertThat(testUserBook.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUserBook.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testUserBook.isIsLiked()).isEqualTo(UPDATED_IS_LIKED);

        // Validate the UserBook in Elasticsearch
        UserBook userBookEs = userBookSearchRepository.findOne(testUserBook.getId());
        assertThat(userBookEs).isEqualToIgnoringGivenFields(testUserBook);
    }

    @Test
    @Transactional
    public void updateNonExistingUserBook() throws Exception {
        int databaseSizeBeforeUpdate = userBookRepository.findAll().size();

        // Create the UserBook
        UserBookDTO userBookDTO = userBookMapper.toDto(userBook);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserBookMockMvc.perform(put("/api/user-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userBookDTO)))
            .andExpect(status().isCreated());

        // Validate the UserBook in the database
        List<UserBook> userBookList = userBookRepository.findAll();
        assertThat(userBookList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserBook() throws Exception {
        // Initialize the database
        userBookRepository.saveAndFlush(userBook);
        userBookSearchRepository.save(userBook);
        int databaseSizeBeforeDelete = userBookRepository.findAll().size();

        // Get the userBook
        restUserBookMockMvc.perform(delete("/api/user-books/{id}", userBook.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean userBookExistsInEs = userBookSearchRepository.exists(userBook.getId());
        assertThat(userBookExistsInEs).isFalse();

        // Validate the database is empty
        List<UserBook> userBookList = userBookRepository.findAll();
        assertThat(userBookList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchUserBook() throws Exception {
        // Initialize the database
        userBookRepository.saveAndFlush(userBook);
        userBookSearchRepository.save(userBook);

        // Search the userBook
        restUserBookMockMvc.perform(get("/api/_search/user-books?query=id:" + userBook.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userBook.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].isLiked").value(hasItem(DEFAULT_IS_LIKED.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserBook.class);
        UserBook userBook1 = new UserBook();
        userBook1.setId(1L);
        UserBook userBook2 = new UserBook();
        userBook2.setId(userBook1.getId());
        assertThat(userBook1).isEqualTo(userBook2);
        userBook2.setId(2L);
        assertThat(userBook1).isNotEqualTo(userBook2);
        userBook1.setId(null);
        assertThat(userBook1).isNotEqualTo(userBook2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserBookDTO.class);
        UserBookDTO userBookDTO1 = new UserBookDTO();
        userBookDTO1.setId(1L);
        UserBookDTO userBookDTO2 = new UserBookDTO();
        assertThat(userBookDTO1).isNotEqualTo(userBookDTO2);
        userBookDTO2.setId(userBookDTO1.getId());
        assertThat(userBookDTO1).isEqualTo(userBookDTO2);
        userBookDTO2.setId(2L);
        assertThat(userBookDTO1).isNotEqualTo(userBookDTO2);
        userBookDTO1.setId(null);
        assertThat(userBookDTO1).isNotEqualTo(userBookDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userBookMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userBookMapper.fromId(null)).isNull();
    }
}
