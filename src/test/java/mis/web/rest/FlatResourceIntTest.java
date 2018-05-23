package mis.web.rest;

import mis.HmisApp;

import mis.domain.Flat;
import mis.domain.Profile;
import mis.domain.User;
import mis.domain.House;
import mis.domain.Floor;
import mis.repository.FlatRepository;
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
 * Test class for the FlatResource REST controller.
 *
 * @see FlatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HmisApp.class)
public class FlatResourceIntTest {

    private static final String DEFAULT_FLAT_NAME_OR_NO = "AAAAAAAAAA";
    private static final String UPDATED_FLAT_NAME_OR_NO = "BBBBBBBBBB";

    private static final String DEFAULT_FLAT_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_FLAT_SIZE = "BBBBBBBBBB";

    private static final Integer DEFAULT_TOTAL_BED_ROOM = 1;
    private static final Integer UPDATED_TOTAL_BED_ROOM = 2;

    private static final String DEFAULT_DRAW_DIN_DRA_CUM_DIN = "AAAAAAAAAA";
    private static final String UPDATED_DRAW_DIN_DRA_CUM_DIN = "BBBBBBBBBB";

    private static final Integer DEFAULT_TOTAL_WASHROOM = 1;
    private static final Integer UPDATED_TOTAL_WASHROOM = 2;

    private static final Integer DEFAULT_KITCHEN = 1;
    private static final Integer UPDATED_KITCHEN = 2;

    private static final Integer DEFAULT_TOTAL_BELCONY = 1;
    private static final Integer UPDATED_TOTAL_BELCONY = 2;

    private static final Integer DEFAULT_STORE_ROOM = 1;
    private static final Integer UPDATED_STORE_ROOM = 2;

    private static final String DEFAULT_FLOOR_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FLOOR_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_FLAT_FAIR = "AAAAAAAAAA";
    private static final String UPDATED_FLAT_FAIR = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_CHARGE = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_CHARGE = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAL_FAIR = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_FAIR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATEDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATEDATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    @Autowired
    private FlatRepository flatRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFlatMockMvc;

    private Flat flat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FlatResource flatResource = new FlatResource(flatRepository);
        this.restFlatMockMvc = MockMvcBuilders.standaloneSetup(flatResource)
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
    public static Flat createEntity(EntityManager em) {
        Flat flat = new Flat()
            .flatNameOrNo(DEFAULT_FLAT_NAME_OR_NO)
            .flatSize(DEFAULT_FLAT_SIZE)
            .totalBedRoom(DEFAULT_TOTAL_BED_ROOM)
            .drawDinDraCumDin(DEFAULT_DRAW_DIN_DRA_CUM_DIN)
            .totalWashroom(DEFAULT_TOTAL_WASHROOM)
            .kitchen(DEFAULT_KITCHEN)
            .totalBelcony(DEFAULT_TOTAL_BELCONY)
            .storeRoom(DEFAULT_STORE_ROOM)
            .floorType(DEFAULT_FLOOR_TYPE)
            .flatFair(DEFAULT_FLAT_FAIR)
            .serviceCharge(DEFAULT_SERVICE_CHARGE)
            .totalFair(DEFAULT_TOTAL_FAIR)
            .createdate(DEFAULT_CREATEDATE)
            .createBy(DEFAULT_CREATE_BY)
            .updateDate(DEFAULT_UPDATE_DATE)
            .updateBy(DEFAULT_UPDATE_BY);
        // Add required entity
        Profile profile = ProfileResourceIntTest.createEntity(em);
        em.persist(profile);
        em.flush();
        flat.setProfile(profile);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        flat.setUser(user);
        // Add required entity
        House house = HouseResourceIntTest.createEntity(em);
        em.persist(house);
        em.flush();
        flat.setHouse(house);
        // Add required entity
        Floor floor = FloorResourceIntTest.createEntity(em);
        em.persist(floor);
        em.flush();
        flat.setFloor(floor);
        return flat;
    }

    @Before
    public void initTest() {
        flat = createEntity(em);
    }

    @Test
    @Transactional
    public void createFlat() throws Exception {
        int databaseSizeBeforeCreate = flatRepository.findAll().size();

        // Create the Flat
        restFlatMockMvc.perform(post("/api/flats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(flat)))
            .andExpect(status().isCreated());

        // Validate the Flat in the database
        List<Flat> flatList = flatRepository.findAll();
        assertThat(flatList).hasSize(databaseSizeBeforeCreate + 1);
        Flat testFlat = flatList.get(flatList.size() - 1);
        assertThat(testFlat.getFlatNameOrNo()).isEqualTo(DEFAULT_FLAT_NAME_OR_NO);
        assertThat(testFlat.getFlatSize()).isEqualTo(DEFAULT_FLAT_SIZE);
        assertThat(testFlat.getTotalBedRoom()).isEqualTo(DEFAULT_TOTAL_BED_ROOM);
        assertThat(testFlat.getDrawDinDraCumDin()).isEqualTo(DEFAULT_DRAW_DIN_DRA_CUM_DIN);
        assertThat(testFlat.getTotalWashroom()).isEqualTo(DEFAULT_TOTAL_WASHROOM);
        assertThat(testFlat.getKitchen()).isEqualTo(DEFAULT_KITCHEN);
        assertThat(testFlat.getTotalBelcony()).isEqualTo(DEFAULT_TOTAL_BELCONY);
        assertThat(testFlat.getStoreRoom()).isEqualTo(DEFAULT_STORE_ROOM);
        assertThat(testFlat.getFloorType()).isEqualTo(DEFAULT_FLOOR_TYPE);
        assertThat(testFlat.getFlatFair()).isEqualTo(DEFAULT_FLAT_FAIR);
        assertThat(testFlat.getServiceCharge()).isEqualTo(DEFAULT_SERVICE_CHARGE);
        assertThat(testFlat.getTotalFair()).isEqualTo(DEFAULT_TOTAL_FAIR);
        assertThat(testFlat.getCreatedate()).isEqualTo(DEFAULT_CREATEDATE);
        assertThat(testFlat.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testFlat.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testFlat.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createFlatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = flatRepository.findAll().size();

        // Create the Flat with an existing ID
        flat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFlatMockMvc.perform(post("/api/flats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(flat)))
            .andExpect(status().isBadRequest());

        // Validate the Flat in the database
        List<Flat> flatList = flatRepository.findAll();
        assertThat(flatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFlatNameOrNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = flatRepository.findAll().size();
        // set the field null
        flat.setFlatNameOrNo(null);

        // Create the Flat, which fails.

        restFlatMockMvc.perform(post("/api/flats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(flat)))
            .andExpect(status().isBadRequest());

        List<Flat> flatList = flatRepository.findAll();
        assertThat(flatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFlatSizeIsRequired() throws Exception {
        int databaseSizeBeforeTest = flatRepository.findAll().size();
        // set the field null
        flat.setFlatSize(null);

        // Create the Flat, which fails.

        restFlatMockMvc.perform(post("/api/flats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(flat)))
            .andExpect(status().isBadRequest());

        List<Flat> flatList = flatRepository.findAll();
        assertThat(flatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalBedRoomIsRequired() throws Exception {
        int databaseSizeBeforeTest = flatRepository.findAll().size();
        // set the field null
        flat.setTotalBedRoom(null);

        // Create the Flat, which fails.

        restFlatMockMvc.perform(post("/api/flats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(flat)))
            .andExpect(status().isBadRequest());

        List<Flat> flatList = flatRepository.findAll();
        assertThat(flatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDrawDinDraCumDinIsRequired() throws Exception {
        int databaseSizeBeforeTest = flatRepository.findAll().size();
        // set the field null
        flat.setDrawDinDraCumDin(null);

        // Create the Flat, which fails.

        restFlatMockMvc.perform(post("/api/flats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(flat)))
            .andExpect(status().isBadRequest());

        List<Flat> flatList = flatRepository.findAll();
        assertThat(flatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalWashroomIsRequired() throws Exception {
        int databaseSizeBeforeTest = flatRepository.findAll().size();
        // set the field null
        flat.setTotalWashroom(null);

        // Create the Flat, which fails.

        restFlatMockMvc.perform(post("/api/flats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(flat)))
            .andExpect(status().isBadRequest());

        List<Flat> flatList = flatRepository.findAll();
        assertThat(flatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKitchenIsRequired() throws Exception {
        int databaseSizeBeforeTest = flatRepository.findAll().size();
        // set the field null
        flat.setKitchen(null);

        // Create the Flat, which fails.

        restFlatMockMvc.perform(post("/api/flats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(flat)))
            .andExpect(status().isBadRequest());

        List<Flat> flatList = flatRepository.findAll();
        assertThat(flatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalBelconyIsRequired() throws Exception {
        int databaseSizeBeforeTest = flatRepository.findAll().size();
        // set the field null
        flat.setTotalBelcony(null);

        // Create the Flat, which fails.

        restFlatMockMvc.perform(post("/api/flats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(flat)))
            .andExpect(status().isBadRequest());

        List<Flat> flatList = flatRepository.findAll();
        assertThat(flatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStoreRoomIsRequired() throws Exception {
        int databaseSizeBeforeTest = flatRepository.findAll().size();
        // set the field null
        flat.setStoreRoom(null);

        // Create the Flat, which fails.

        restFlatMockMvc.perform(post("/api/flats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(flat)))
            .andExpect(status().isBadRequest());

        List<Flat> flatList = flatRepository.findAll();
        assertThat(flatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFloorTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = flatRepository.findAll().size();
        // set the field null
        flat.setFloorType(null);

        // Create the Flat, which fails.

        restFlatMockMvc.perform(post("/api/flats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(flat)))
            .andExpect(status().isBadRequest());

        List<Flat> flatList = flatRepository.findAll();
        assertThat(flatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFlatFairIsRequired() throws Exception {
        int databaseSizeBeforeTest = flatRepository.findAll().size();
        // set the field null
        flat.setFlatFair(null);

        // Create the Flat, which fails.

        restFlatMockMvc.perform(post("/api/flats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(flat)))
            .andExpect(status().isBadRequest());

        List<Flat> flatList = flatRepository.findAll();
        assertThat(flatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkServiceChargeIsRequired() throws Exception {
        int databaseSizeBeforeTest = flatRepository.findAll().size();
        // set the field null
        flat.setServiceCharge(null);

        // Create the Flat, which fails.

        restFlatMockMvc.perform(post("/api/flats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(flat)))
            .andExpect(status().isBadRequest());

        List<Flat> flatList = flatRepository.findAll();
        assertThat(flatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalFairIsRequired() throws Exception {
        int databaseSizeBeforeTest = flatRepository.findAll().size();
        // set the field null
        flat.setTotalFair(null);

        // Create the Flat, which fails.

        restFlatMockMvc.perform(post("/api/flats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(flat)))
            .andExpect(status().isBadRequest());

        List<Flat> flatList = flatRepository.findAll();
        assertThat(flatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFlats() throws Exception {
        // Initialize the database
        flatRepository.saveAndFlush(flat);

        // Get all the flatList
        restFlatMockMvc.perform(get("/api/flats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(flat.getId().intValue())))
            .andExpect(jsonPath("$.[*].flatNameOrNo").value(hasItem(DEFAULT_FLAT_NAME_OR_NO.toString())))
            .andExpect(jsonPath("$.[*].flatSize").value(hasItem(DEFAULT_FLAT_SIZE.toString())))
            .andExpect(jsonPath("$.[*].totalBedRoom").value(hasItem(DEFAULT_TOTAL_BED_ROOM)))
            .andExpect(jsonPath("$.[*].drawDinDraCumDin").value(hasItem(DEFAULT_DRAW_DIN_DRA_CUM_DIN.toString())))
            .andExpect(jsonPath("$.[*].totalWashroom").value(hasItem(DEFAULT_TOTAL_WASHROOM)))
            .andExpect(jsonPath("$.[*].kitchen").value(hasItem(DEFAULT_KITCHEN)))
            .andExpect(jsonPath("$.[*].totalBelcony").value(hasItem(DEFAULT_TOTAL_BELCONY)))
            .andExpect(jsonPath("$.[*].storeRoom").value(hasItem(DEFAULT_STORE_ROOM)))
            .andExpect(jsonPath("$.[*].floorType").value(hasItem(DEFAULT_FLOOR_TYPE.toString())))
            .andExpect(jsonPath("$.[*].flatFair").value(hasItem(DEFAULT_FLAT_FAIR.toString())))
            .andExpect(jsonPath("$.[*].serviceCharge").value(hasItem(DEFAULT_SERVICE_CHARGE.toString())))
            .andExpect(jsonPath("$.[*].totalFair").value(hasItem(DEFAULT_TOTAL_FAIR.toString())))
            .andExpect(jsonPath("$.[*].createdate").value(hasItem(DEFAULT_CREATEDATE.toString())))
            .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getFlat() throws Exception {
        // Initialize the database
        flatRepository.saveAndFlush(flat);

        // Get the flat
        restFlatMockMvc.perform(get("/api/flats/{id}", flat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(flat.getId().intValue()))
            .andExpect(jsonPath("$.flatNameOrNo").value(DEFAULT_FLAT_NAME_OR_NO.toString()))
            .andExpect(jsonPath("$.flatSize").value(DEFAULT_FLAT_SIZE.toString()))
            .andExpect(jsonPath("$.totalBedRoom").value(DEFAULT_TOTAL_BED_ROOM))
            .andExpect(jsonPath("$.drawDinDraCumDin").value(DEFAULT_DRAW_DIN_DRA_CUM_DIN.toString()))
            .andExpect(jsonPath("$.totalWashroom").value(DEFAULT_TOTAL_WASHROOM))
            .andExpect(jsonPath("$.kitchen").value(DEFAULT_KITCHEN))
            .andExpect(jsonPath("$.totalBelcony").value(DEFAULT_TOTAL_BELCONY))
            .andExpect(jsonPath("$.storeRoom").value(DEFAULT_STORE_ROOM))
            .andExpect(jsonPath("$.floorType").value(DEFAULT_FLOOR_TYPE.toString()))
            .andExpect(jsonPath("$.flatFair").value(DEFAULT_FLAT_FAIR.toString()))
            .andExpect(jsonPath("$.serviceCharge").value(DEFAULT_SERVICE_CHARGE.toString()))
            .andExpect(jsonPath("$.totalFair").value(DEFAULT_TOTAL_FAIR.toString()))
            .andExpect(jsonPath("$.createdate").value(DEFAULT_CREATEDATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFlat() throws Exception {
        // Get the flat
        restFlatMockMvc.perform(get("/api/flats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFlat() throws Exception {
        // Initialize the database
        flatRepository.saveAndFlush(flat);
        int databaseSizeBeforeUpdate = flatRepository.findAll().size();

        // Update the flat
        Flat updatedFlat = flatRepository.findOne(flat.getId());
        // Disconnect from session so that the updates on updatedFlat are not directly saved in db
        em.detach(updatedFlat);
        updatedFlat
            .flatNameOrNo(UPDATED_FLAT_NAME_OR_NO)
            .flatSize(UPDATED_FLAT_SIZE)
            .totalBedRoom(UPDATED_TOTAL_BED_ROOM)
            .drawDinDraCumDin(UPDATED_DRAW_DIN_DRA_CUM_DIN)
            .totalWashroom(UPDATED_TOTAL_WASHROOM)
            .kitchen(UPDATED_KITCHEN)
            .totalBelcony(UPDATED_TOTAL_BELCONY)
            .storeRoom(UPDATED_STORE_ROOM)
            .floorType(UPDATED_FLOOR_TYPE)
            .flatFair(UPDATED_FLAT_FAIR)
            .serviceCharge(UPDATED_SERVICE_CHARGE)
            .totalFair(UPDATED_TOTAL_FAIR)
            .createdate(UPDATED_CREATEDATE)
            .createBy(UPDATED_CREATE_BY)
            .updateDate(UPDATED_UPDATE_DATE)
            .updateBy(UPDATED_UPDATE_BY);

        restFlatMockMvc.perform(put("/api/flats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFlat)))
            .andExpect(status().isOk());

        // Validate the Flat in the database
        List<Flat> flatList = flatRepository.findAll();
        assertThat(flatList).hasSize(databaseSizeBeforeUpdate);
        Flat testFlat = flatList.get(flatList.size() - 1);
        assertThat(testFlat.getFlatNameOrNo()).isEqualTo(UPDATED_FLAT_NAME_OR_NO);
        assertThat(testFlat.getFlatSize()).isEqualTo(UPDATED_FLAT_SIZE);
        assertThat(testFlat.getTotalBedRoom()).isEqualTo(UPDATED_TOTAL_BED_ROOM);
        assertThat(testFlat.getDrawDinDraCumDin()).isEqualTo(UPDATED_DRAW_DIN_DRA_CUM_DIN);
        assertThat(testFlat.getTotalWashroom()).isEqualTo(UPDATED_TOTAL_WASHROOM);
        assertThat(testFlat.getKitchen()).isEqualTo(UPDATED_KITCHEN);
        assertThat(testFlat.getTotalBelcony()).isEqualTo(UPDATED_TOTAL_BELCONY);
        assertThat(testFlat.getStoreRoom()).isEqualTo(UPDATED_STORE_ROOM);
        assertThat(testFlat.getFloorType()).isEqualTo(UPDATED_FLOOR_TYPE);
        assertThat(testFlat.getFlatFair()).isEqualTo(UPDATED_FLAT_FAIR);
        assertThat(testFlat.getServiceCharge()).isEqualTo(UPDATED_SERVICE_CHARGE);
        assertThat(testFlat.getTotalFair()).isEqualTo(UPDATED_TOTAL_FAIR);
        assertThat(testFlat.getCreatedate()).isEqualTo(UPDATED_CREATEDATE);
        assertThat(testFlat.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testFlat.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testFlat.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingFlat() throws Exception {
        int databaseSizeBeforeUpdate = flatRepository.findAll().size();

        // Create the Flat

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFlatMockMvc.perform(put("/api/flats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(flat)))
            .andExpect(status().isCreated());

        // Validate the Flat in the database
        List<Flat> flatList = flatRepository.findAll();
        assertThat(flatList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFlat() throws Exception {
        // Initialize the database
        flatRepository.saveAndFlush(flat);
        int databaseSizeBeforeDelete = flatRepository.findAll().size();

        // Get the flat
        restFlatMockMvc.perform(delete("/api/flats/{id}", flat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Flat> flatList = flatRepository.findAll();
        assertThat(flatList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Flat.class);
        Flat flat1 = new Flat();
        flat1.setId(1L);
        Flat flat2 = new Flat();
        flat2.setId(flat1.getId());
        assertThat(flat1).isEqualTo(flat2);
        flat2.setId(2L);
        assertThat(flat1).isNotEqualTo(flat2);
        flat1.setId(null);
        assertThat(flat1).isNotEqualTo(flat2);
    }
}
