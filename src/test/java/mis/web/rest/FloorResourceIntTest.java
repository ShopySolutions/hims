package mis.web.rest;

import mis.HmisApp;

import mis.domain.Floor;
import mis.domain.Profile;
import mis.domain.User;
import mis.domain.House;
import mis.repository.FloorRepository;
import mis.web.rest.errors.ExceptionTranslator;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static mis.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FloorResource REST controller.
 *
 * @see FloorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HmisApp.class)
public class FloorResourceIntTest {

    private static final String DEFAULT_FLOOR_NO = "AAAAAAAAAA";
    private static final String UPDATED_FLOOR_NO = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAL_FLAT = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_FLAT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATEDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATEDATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    @Autowired
    private FloorRepository floorRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFloorMockMvc;

    private Floor floor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FloorResource floorResource = new FloorResource(floorRepository);
        this.restFloorMockMvc = MockMvcBuilders.standaloneSetup(floorResource)
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
    public static Floor createEntity(EntityManager em) {
        Floor floor = new Floor()
            .floorNo(DEFAULT_FLOOR_NO)
            .totalFlat(DEFAULT_TOTAL_FLAT)
            .createdate(DEFAULT_CREATEDATE)
            .createBy(DEFAULT_CREATE_BY)
            .updateDate(DEFAULT_UPDATE_DATE)
            .updateBy(DEFAULT_UPDATE_BY);
        // Add required entity
        Profile profile = ProfileResourceIntTest.createEntity(em);
        em.persist(profile);
        em.flush();
        floor.setProfile(profile);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        floor.setUser(user);
        // Add required entity
        House house = HouseResourceIntTest.createEntity(em);
        em.persist(house);
        em.flush();
        floor.setHouse(house);
        return floor;
    }

    @Before
    public void initTest() {
        floor = createEntity(em);
    }

    @Test
    @Transactional
    public void createFloor() throws Exception {
        int databaseSizeBeforeCreate = floorRepository.findAll().size();

        // Create the Floor
        restFloorMockMvc.perform(post("/api/floors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(floor)))
            .andExpect(status().isCreated());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeCreate + 1);
        Floor testFloor = floorList.get(floorList.size() - 1);
        assertThat(testFloor.getFloorNo()).isEqualTo(DEFAULT_FLOOR_NO);
        assertThat(testFloor.getTotalFlat()).isEqualTo(DEFAULT_TOTAL_FLAT);
        assertThat(testFloor.getCreatedate()).isEqualTo(DEFAULT_CREATEDATE);
        assertThat(testFloor.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testFloor.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testFloor.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createFloorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = floorRepository.findAll().size();

        // Create the Floor with an existing ID
        floor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFloorMockMvc.perform(post("/api/floors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(floor)))
            .andExpect(status().isBadRequest());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFloorNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = floorRepository.findAll().size();
        // set the field null
        floor.setFloorNo(null);

        // Create the Floor, which fails.

        restFloorMockMvc.perform(post("/api/floors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(floor)))
            .andExpect(status().isBadRequest());

        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalFlatIsRequired() throws Exception {
        int databaseSizeBeforeTest = floorRepository.findAll().size();
        // set the field null
        floor.setTotalFlat(null);

        // Create the Floor, which fails.

        restFloorMockMvc.perform(post("/api/floors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(floor)))
            .andExpect(status().isBadRequest());

        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFloors() throws Exception {
        // Initialize the database
        floorRepository.saveAndFlush(floor);

        // Get all the floorList
        restFloorMockMvc.perform(get("/api/floors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(floor.getId().intValue())))
            .andExpect(jsonPath("$.[*].floorNo").value(hasItem(DEFAULT_FLOOR_NO.toString())))
            .andExpect(jsonPath("$.[*].totalFlat").value(hasItem(DEFAULT_TOTAL_FLAT.toString())))
            .andExpect(jsonPath("$.[*].createdate").value(hasItem(DEFAULT_CREATEDATE.toString())))
            .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getFloor() throws Exception {
        // Initialize the database
        floorRepository.saveAndFlush(floor);

        // Get the floor
        restFloorMockMvc.perform(get("/api/floors/{id}", floor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(floor.getId().intValue()))
            .andExpect(jsonPath("$.floorNo").value(DEFAULT_FLOOR_NO.toString()))
            .andExpect(jsonPath("$.totalFlat").value(DEFAULT_TOTAL_FLAT.toString()))
            .andExpect(jsonPath("$.createdate").value(DEFAULT_CREATEDATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFloor() throws Exception {
        // Get the floor
        restFloorMockMvc.perform(get("/api/floors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFloor() throws Exception {
        // Initialize the database
        floorRepository.saveAndFlush(floor);
        int databaseSizeBeforeUpdate = floorRepository.findAll().size();

        // Update the floor
        Floor updatedFloor = floorRepository.findOne(floor.getId());
        // Disconnect from session so that the updates on updatedFloor are not directly saved in db
        em.detach(updatedFloor);
        updatedFloor
            .floorNo(UPDATED_FLOOR_NO)
            .totalFlat(UPDATED_TOTAL_FLAT)
            .createdate(UPDATED_CREATEDATE)
            .createBy(UPDATED_CREATE_BY)
            .updateDate(UPDATED_UPDATE_DATE)
            .updateBy(UPDATED_UPDATE_BY);

        restFloorMockMvc.perform(put("/api/floors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFloor)))
            .andExpect(status().isOk());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeUpdate);
        Floor testFloor = floorList.get(floorList.size() - 1);
        assertThat(testFloor.getFloorNo()).isEqualTo(UPDATED_FLOOR_NO);
        assertThat(testFloor.getTotalFlat()).isEqualTo(UPDATED_TOTAL_FLAT);
        assertThat(testFloor.getCreatedate()).isEqualTo(UPDATED_CREATEDATE);
        assertThat(testFloor.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testFloor.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testFloor.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingFloor() throws Exception {
        int databaseSizeBeforeUpdate = floorRepository.findAll().size();

        // Create the Floor

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFloorMockMvc.perform(put("/api/floors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(floor)))
            .andExpect(status().isCreated());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFloor() throws Exception {
        // Initialize the database
        floorRepository.saveAndFlush(floor);
        int databaseSizeBeforeDelete = floorRepository.findAll().size();

        // Get the floor
        restFloorMockMvc.perform(delete("/api/floors/{id}", floor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Floor.class);
        Floor floor1 = new Floor();
        floor1.setId(1L);
        Floor floor2 = new Floor();
        floor2.setId(floor1.getId());
        assertThat(floor1).isEqualTo(floor2);
        floor2.setId(2L);
        assertThat(floor1).isNotEqualTo(floor2);
        floor1.setId(null);
        assertThat(floor1).isNotEqualTo(floor2);
    }
}
