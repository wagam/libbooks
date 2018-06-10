package com.mag.libbooks.web.rest;

import com.mag.libbooks.LibBooksApp;

import com.mag.libbooks.domain.UserMetadatas;
import com.mag.libbooks.repository.UserMetadatasRepository;
import com.mag.libbooks.repository.search.UserMetadatasSearchRepository;
import com.mag.libbooks.service.dto.UserMetadatasDTO;
import com.mag.libbooks.service.mapper.UserMetadatasMapper;
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
 * Test class for the UserMetadatasResource REST controller.
 *
 * @see UserMetadatasResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LibBooksApp.class)
public class UserMetadatasResourceIntTest {

    private static final Integer DEFAULT_NUMBER_OF_READ_BOOKS = 1;
    private static final Integer UPDATED_NUMBER_OF_READ_BOOKS = 2;

    private static final Integer DEFAULT_NUMBER_OF_COMMENTS = 1;
    private static final Integer UPDATED_NUMBER_OF_COMMENTS = 2;

    private static final Integer DEFAULT_NUMBER_OF_LIKES = 1;
    private static final Integer UPDATED_NUMBER_OF_LIKES = 2;

    @Autowired
    private UserMetadatasRepository userMetadatasRepository;

    @Autowired
    private UserMetadatasMapper userMetadatasMapper;

    @Autowired
    private UserMetadatasSearchRepository userMetadatasSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserMetadatasMockMvc;

    private UserMetadatas userMetadatas;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserMetadatasResource userMetadatasResource = new UserMetadatasResource(userMetadatasRepository, userMetadatasMapper, userMetadatasSearchRepository);
        this.restUserMetadatasMockMvc = MockMvcBuilders.standaloneSetup(userMetadatasResource)
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
    public static UserMetadatas createEntity(EntityManager em) {
        UserMetadatas userMetadatas = new UserMetadatas()
            .numberOfReadBooks(DEFAULT_NUMBER_OF_READ_BOOKS)
            .numberOfComments(DEFAULT_NUMBER_OF_COMMENTS)
            .numberOfLikes(DEFAULT_NUMBER_OF_LIKES);
        return userMetadatas;
    }

    @Before
    public void initTest() {
        userMetadatasSearchRepository.deleteAll();
        userMetadatas = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserMetadatas() throws Exception {
        int databaseSizeBeforeCreate = userMetadatasRepository.findAll().size();

        // Create the UserMetadatas
        UserMetadatasDTO userMetadatasDTO = userMetadatasMapper.toDto(userMetadatas);
        restUserMetadatasMockMvc.perform(post("/api/user-metadatas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userMetadatasDTO)))
            .andExpect(status().isCreated());

        // Validate the UserMetadatas in the database
        List<UserMetadatas> userMetadatasList = userMetadatasRepository.findAll();
        assertThat(userMetadatasList).hasSize(databaseSizeBeforeCreate + 1);
        UserMetadatas testUserMetadatas = userMetadatasList.get(userMetadatasList.size() - 1);
        assertThat(testUserMetadatas.getNumberOfReadBooks()).isEqualTo(DEFAULT_NUMBER_OF_READ_BOOKS);
        assertThat(testUserMetadatas.getNumberOfComments()).isEqualTo(DEFAULT_NUMBER_OF_COMMENTS);
        assertThat(testUserMetadatas.getNumberOfLikes()).isEqualTo(DEFAULT_NUMBER_OF_LIKES);

        // Validate the UserMetadatas in Elasticsearch
        UserMetadatas userMetadatasEs = userMetadatasSearchRepository.findOne(testUserMetadatas.getId());
        assertThat(userMetadatasEs).isEqualToIgnoringGivenFields(testUserMetadatas);
    }

    @Test
    @Transactional
    public void createUserMetadatasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userMetadatasRepository.findAll().size();

        // Create the UserMetadatas with an existing ID
        userMetadatas.setId(1L);
        UserMetadatasDTO userMetadatasDTO = userMetadatasMapper.toDto(userMetadatas);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserMetadatasMockMvc.perform(post("/api/user-metadatas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userMetadatasDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserMetadatas in the database
        List<UserMetadatas> userMetadatasList = userMetadatasRepository.findAll();
        assertThat(userMetadatasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserMetadatas() throws Exception {
        // Initialize the database
        userMetadatasRepository.saveAndFlush(userMetadatas);

        // Get all the userMetadatasList
        restUserMetadatasMockMvc.perform(get("/api/user-metadatas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userMetadatas.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberOfReadBooks").value(hasItem(DEFAULT_NUMBER_OF_READ_BOOKS)))
            .andExpect(jsonPath("$.[*].numberOfComments").value(hasItem(DEFAULT_NUMBER_OF_COMMENTS)))
            .andExpect(jsonPath("$.[*].numberOfLikes").value(hasItem(DEFAULT_NUMBER_OF_LIKES)));
    }

    @Test
    @Transactional
    public void getUserMetadatas() throws Exception {
        // Initialize the database
        userMetadatasRepository.saveAndFlush(userMetadatas);

        // Get the userMetadatas
        restUserMetadatasMockMvc.perform(get("/api/user-metadatas/{id}", userMetadatas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userMetadatas.getId().intValue()))
            .andExpect(jsonPath("$.numberOfReadBooks").value(DEFAULT_NUMBER_OF_READ_BOOKS))
            .andExpect(jsonPath("$.numberOfComments").value(DEFAULT_NUMBER_OF_COMMENTS))
            .andExpect(jsonPath("$.numberOfLikes").value(DEFAULT_NUMBER_OF_LIKES));
    }

    @Test
    @Transactional
    public void getNonExistingUserMetadatas() throws Exception {
        // Get the userMetadatas
        restUserMetadatasMockMvc.perform(get("/api/user-metadatas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserMetadatas() throws Exception {
        // Initialize the database
        userMetadatasRepository.saveAndFlush(userMetadatas);
        userMetadatasSearchRepository.save(userMetadatas);
        int databaseSizeBeforeUpdate = userMetadatasRepository.findAll().size();

        // Update the userMetadatas
        UserMetadatas updatedUserMetadatas = userMetadatasRepository.findOne(userMetadatas.getId());
        // Disconnect from session so that the updates on updatedUserMetadatas are not directly saved in db
        em.detach(updatedUserMetadatas);
        updatedUserMetadatas
            .numberOfReadBooks(UPDATED_NUMBER_OF_READ_BOOKS)
            .numberOfComments(UPDATED_NUMBER_OF_COMMENTS)
            .numberOfLikes(UPDATED_NUMBER_OF_LIKES);
        UserMetadatasDTO userMetadatasDTO = userMetadatasMapper.toDto(updatedUserMetadatas);

        restUserMetadatasMockMvc.perform(put("/api/user-metadatas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userMetadatasDTO)))
            .andExpect(status().isOk());

        // Validate the UserMetadatas in the database
        List<UserMetadatas> userMetadatasList = userMetadatasRepository.findAll();
        assertThat(userMetadatasList).hasSize(databaseSizeBeforeUpdate);
        UserMetadatas testUserMetadatas = userMetadatasList.get(userMetadatasList.size() - 1);
        assertThat(testUserMetadatas.getNumberOfReadBooks()).isEqualTo(UPDATED_NUMBER_OF_READ_BOOKS);
        assertThat(testUserMetadatas.getNumberOfComments()).isEqualTo(UPDATED_NUMBER_OF_COMMENTS);
        assertThat(testUserMetadatas.getNumberOfLikes()).isEqualTo(UPDATED_NUMBER_OF_LIKES);

        // Validate the UserMetadatas in Elasticsearch
        UserMetadatas userMetadatasEs = userMetadatasSearchRepository.findOne(testUserMetadatas.getId());
        assertThat(userMetadatasEs).isEqualToIgnoringGivenFields(testUserMetadatas);
    }

    @Test
    @Transactional
    public void updateNonExistingUserMetadatas() throws Exception {
        int databaseSizeBeforeUpdate = userMetadatasRepository.findAll().size();

        // Create the UserMetadatas
        UserMetadatasDTO userMetadatasDTO = userMetadatasMapper.toDto(userMetadatas);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserMetadatasMockMvc.perform(put("/api/user-metadatas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userMetadatasDTO)))
            .andExpect(status().isCreated());

        // Validate the UserMetadatas in the database
        List<UserMetadatas> userMetadatasList = userMetadatasRepository.findAll();
        assertThat(userMetadatasList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserMetadatas() throws Exception {
        // Initialize the database
        userMetadatasRepository.saveAndFlush(userMetadatas);
        userMetadatasSearchRepository.save(userMetadatas);
        int databaseSizeBeforeDelete = userMetadatasRepository.findAll().size();

        // Get the userMetadatas
        restUserMetadatasMockMvc.perform(delete("/api/user-metadatas/{id}", userMetadatas.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean userMetadatasExistsInEs = userMetadatasSearchRepository.exists(userMetadatas.getId());
        assertThat(userMetadatasExistsInEs).isFalse();

        // Validate the database is empty
        List<UserMetadatas> userMetadatasList = userMetadatasRepository.findAll();
        assertThat(userMetadatasList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchUserMetadatas() throws Exception {
        // Initialize the database
        userMetadatasRepository.saveAndFlush(userMetadatas);
        userMetadatasSearchRepository.save(userMetadatas);

        // Search the userMetadatas
        restUserMetadatasMockMvc.perform(get("/api/_search/user-metadatas?query=id:" + userMetadatas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userMetadatas.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberOfReadBooks").value(hasItem(DEFAULT_NUMBER_OF_READ_BOOKS)))
            .andExpect(jsonPath("$.[*].numberOfComments").value(hasItem(DEFAULT_NUMBER_OF_COMMENTS)))
            .andExpect(jsonPath("$.[*].numberOfLikes").value(hasItem(DEFAULT_NUMBER_OF_LIKES)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserMetadatas.class);
        UserMetadatas userMetadatas1 = new UserMetadatas();
        userMetadatas1.setId(1L);
        UserMetadatas userMetadatas2 = new UserMetadatas();
        userMetadatas2.setId(userMetadatas1.getId());
        assertThat(userMetadatas1).isEqualTo(userMetadatas2);
        userMetadatas2.setId(2L);
        assertThat(userMetadatas1).isNotEqualTo(userMetadatas2);
        userMetadatas1.setId(null);
        assertThat(userMetadatas1).isNotEqualTo(userMetadatas2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserMetadatasDTO.class);
        UserMetadatasDTO userMetadatasDTO1 = new UserMetadatasDTO();
        userMetadatasDTO1.setId(1L);
        UserMetadatasDTO userMetadatasDTO2 = new UserMetadatasDTO();
        assertThat(userMetadatasDTO1).isNotEqualTo(userMetadatasDTO2);
        userMetadatasDTO2.setId(userMetadatasDTO1.getId());
        assertThat(userMetadatasDTO1).isEqualTo(userMetadatasDTO2);
        userMetadatasDTO2.setId(2L);
        assertThat(userMetadatasDTO1).isNotEqualTo(userMetadatasDTO2);
        userMetadatasDTO1.setId(null);
        assertThat(userMetadatasDTO1).isNotEqualTo(userMetadatasDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userMetadatasMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userMetadatasMapper.fromId(null)).isNull();
    }
}
