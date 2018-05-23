package mis.web.rest;

import mis.HmisApp;

import mis.domain.Billing;
import mis.domain.Profile;
import mis.domain.User;
import mis.domain.House;
import mis.domain.Floor;
import mis.domain.Flat;
import mis.domain.Rent;
import mis.repository.BillingRepository;
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
 * Test class for the BillingResource REST controller.
 *
 * @see BillingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HmisApp.class)
public class BillingResourceIntTest {

    private static final String DEFAULT_FLAT_FAIR = "AAAAAAAAAA";
    private static final String UPDATED_FLAT_FAIR = "BBBBBBBBBB";

    private static final String DEFAULT_ELECTRICITY_BILL = "AAAAAAAAAA";
    private static final String UPDATED_ELECTRICITY_BILL = "BBBBBBBBBB";

    private static final String DEFAULT_GAS_BILL = "AAAAAAAAAA";
    private static final String UPDATED_GAS_BILL = "BBBBBBBBBB";

    private static final String DEFAULT_INTERNET_BILL = "AAAAAAAAAA";
    private static final String UPDATED_INTERNET_BILL = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSACTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATEDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATEDATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CREATE_BY = 1L;
    private static final Long UPDATED_CREATE_BY = 2L;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_UPDATE_BY = 1L;
    private static final Long UPDATED_UPDATE_BY = 2L;

    @Autowired
    private BillingRepository billingRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBillingMockMvc;

    private Billing billing;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BillingResource billingResource = new BillingResource(billingRepository);
        this.restBillingMockMvc = MockMvcBuilders.standaloneSetup(billingResource)
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
    public static Billing createEntity(EntityManager em) {
        Billing billing = new Billing()
            .flatFair(DEFAULT_FLAT_FAIR)
            .electricityBill(DEFAULT_ELECTRICITY_BILL)
            .gasBill(DEFAULT_GAS_BILL)
            .internetBill(DEFAULT_INTERNET_BILL)
            .transactionId(DEFAULT_TRANSACTION_ID)
            .createdate(DEFAULT_CREATEDATE)
            .createBy(DEFAULT_CREATE_BY)
            .updateDate(DEFAULT_UPDATE_DATE)
            .updateBy(DEFAULT_UPDATE_BY);
        // Add required entity
        Profile profile = ProfileResourceIntTest.createEntity(em);
        em.persist(profile);
        em.flush();
        billing.setProfile(profile);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        billing.setUser(user);
        // Add required entity
        House house = HouseResourceIntTest.createEntity(em);
        em.persist(house);
        em.flush();
        billing.setHouse(house);
        // Add required entity
        Floor floor = FloorResourceIntTest.createEntity(em);
        em.persist(floor);
        em.flush();
        billing.setFloor(floor);
        // Add required entity
        Flat flat = FlatResourceIntTest.createEntity(em);
        em.persist(flat);
        em.flush();
        billing.setFlat(flat);
        // Add required entity
        Rent rent = RentResourceIntTest.createEntity(em);
        em.persist(rent);
        em.flush();
        billing.setRent(rent);
        return billing;
    }

    @Before
    public void initTest() {
        billing = createEntity(em);
    }

    @Test
    @Transactional
    public void createBilling() throws Exception {
        int databaseSizeBeforeCreate = billingRepository.findAll().size();

        // Create the Billing
        restBillingMockMvc.perform(post("/api/billings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billing)))
            .andExpect(status().isCreated());

        // Validate the Billing in the database
        List<Billing> billingList = billingRepository.findAll();
        assertThat(billingList).hasSize(databaseSizeBeforeCreate + 1);
        Billing testBilling = billingList.get(billingList.size() - 1);
        assertThat(testBilling.getFlatFair()).isEqualTo(DEFAULT_FLAT_FAIR);
        assertThat(testBilling.getElectricityBill()).isEqualTo(DEFAULT_ELECTRICITY_BILL);
        assertThat(testBilling.getGasBill()).isEqualTo(DEFAULT_GAS_BILL);
        assertThat(testBilling.getInternetBill()).isEqualTo(DEFAULT_INTERNET_BILL);
        assertThat(testBilling.getTransactionId()).isEqualTo(DEFAULT_TRANSACTION_ID);
        assertThat(testBilling.getCreatedate()).isEqualTo(DEFAULT_CREATEDATE);
        assertThat(testBilling.getCreateBy()).isEqualTo(DEFAULT_CREATE_BY);
        assertThat(testBilling.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testBilling.getUpdateBy()).isEqualTo(DEFAULT_UPDATE_BY);
    }

    @Test
    @Transactional
    public void createBillingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = billingRepository.findAll().size();

        // Create the Billing with an existing ID
        billing.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillingMockMvc.perform(post("/api/billings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billing)))
            .andExpect(status().isBadRequest());

        // Validate the Billing in the database
        List<Billing> billingList = billingRepository.findAll();
        assertThat(billingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFlatFairIsRequired() throws Exception {
        int databaseSizeBeforeTest = billingRepository.findAll().size();
        // set the field null
        billing.setFlatFair(null);

        // Create the Billing, which fails.

        restBillingMockMvc.perform(post("/api/billings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billing)))
            .andExpect(status().isBadRequest());

        List<Billing> billingList = billingRepository.findAll();
        assertThat(billingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkElectricityBillIsRequired() throws Exception {
        int databaseSizeBeforeTest = billingRepository.findAll().size();
        // set the field null
        billing.setElectricityBill(null);

        // Create the Billing, which fails.

        restBillingMockMvc.perform(post("/api/billings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billing)))
            .andExpect(status().isBadRequest());

        List<Billing> billingList = billingRepository.findAll();
        assertThat(billingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGasBillIsRequired() throws Exception {
        int databaseSizeBeforeTest = billingRepository.findAll().size();
        // set the field null
        billing.setGasBill(null);

        // Create the Billing, which fails.

        restBillingMockMvc.perform(post("/api/billings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billing)))
            .andExpect(status().isBadRequest());

        List<Billing> billingList = billingRepository.findAll();
        assertThat(billingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInternetBillIsRequired() throws Exception {
        int databaseSizeBeforeTest = billingRepository.findAll().size();
        // set the field null
        billing.setInternetBill(null);

        // Create the Billing, which fails.

        restBillingMockMvc.perform(post("/api/billings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billing)))
            .andExpect(status().isBadRequest());

        List<Billing> billingList = billingRepository.findAll();
        assertThat(billingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransactionIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = billingRepository.findAll().size();
        // set the field null
        billing.setTransactionId(null);

        // Create the Billing, which fails.

        restBillingMockMvc.perform(post("/api/billings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billing)))
            .andExpect(status().isBadRequest());

        List<Billing> billingList = billingRepository.findAll();
        assertThat(billingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBillings() throws Exception {
        // Initialize the database
        billingRepository.saveAndFlush(billing);

        // Get all the billingList
        restBillingMockMvc.perform(get("/api/billings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billing.getId().intValue())))
            .andExpect(jsonPath("$.[*].flatFair").value(hasItem(DEFAULT_FLAT_FAIR.toString())))
            .andExpect(jsonPath("$.[*].electricityBill").value(hasItem(DEFAULT_ELECTRICITY_BILL.toString())))
            .andExpect(jsonPath("$.[*].gasBill").value(hasItem(DEFAULT_GAS_BILL.toString())))
            .andExpect(jsonPath("$.[*].internetBill").value(hasItem(DEFAULT_INTERNET_BILL.toString())))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID.toString())))
            .andExpect(jsonPath("$.[*].createdate").value(hasItem(DEFAULT_CREATEDATE.toString())))
            .andExpect(jsonPath("$.[*].createBy").value(hasItem(DEFAULT_CREATE_BY.intValue())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updateBy").value(hasItem(DEFAULT_UPDATE_BY.intValue())));
    }

    @Test
    @Transactional
    public void getBilling() throws Exception {
        // Initialize the database
        billingRepository.saveAndFlush(billing);

        // Get the billing
        restBillingMockMvc.perform(get("/api/billings/{id}", billing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(billing.getId().intValue()))
            .andExpect(jsonPath("$.flatFair").value(DEFAULT_FLAT_FAIR.toString()))
            .andExpect(jsonPath("$.electricityBill").value(DEFAULT_ELECTRICITY_BILL.toString()))
            .andExpect(jsonPath("$.gasBill").value(DEFAULT_GAS_BILL.toString()))
            .andExpect(jsonPath("$.internetBill").value(DEFAULT_INTERNET_BILL.toString()))
            .andExpect(jsonPath("$.transactionId").value(DEFAULT_TRANSACTION_ID.toString()))
            .andExpect(jsonPath("$.createdate").value(DEFAULT_CREATEDATE.toString()))
            .andExpect(jsonPath("$.createBy").value(DEFAULT_CREATE_BY.intValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updateBy").value(DEFAULT_UPDATE_BY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBilling() throws Exception {
        // Get the billing
        restBillingMockMvc.perform(get("/api/billings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBilling() throws Exception {
        // Initialize the database
        billingRepository.saveAndFlush(billing);
        int databaseSizeBeforeUpdate = billingRepository.findAll().size();

        // Update the billing
        Billing updatedBilling = billingRepository.findOne(billing.getId());
        // Disconnect from session so that the updates on updatedBilling are not directly saved in db
        em.detach(updatedBilling);
        updatedBilling
            .flatFair(UPDATED_FLAT_FAIR)
            .electricityBill(UPDATED_ELECTRICITY_BILL)
            .gasBill(UPDATED_GAS_BILL)
            .internetBill(UPDATED_INTERNET_BILL)
            .transactionId(UPDATED_TRANSACTION_ID)
            .createdate(UPDATED_CREATEDATE)
            .createBy(UPDATED_CREATE_BY)
            .updateDate(UPDATED_UPDATE_DATE)
            .updateBy(UPDATED_UPDATE_BY);

        restBillingMockMvc.perform(put("/api/billings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBilling)))
            .andExpect(status().isOk());

        // Validate the Billing in the database
        List<Billing> billingList = billingRepository.findAll();
        assertThat(billingList).hasSize(databaseSizeBeforeUpdate);
        Billing testBilling = billingList.get(billingList.size() - 1);
        assertThat(testBilling.getFlatFair()).isEqualTo(UPDATED_FLAT_FAIR);
        assertThat(testBilling.getElectricityBill()).isEqualTo(UPDATED_ELECTRICITY_BILL);
        assertThat(testBilling.getGasBill()).isEqualTo(UPDATED_GAS_BILL);
        assertThat(testBilling.getInternetBill()).isEqualTo(UPDATED_INTERNET_BILL);
        assertThat(testBilling.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testBilling.getCreatedate()).isEqualTo(UPDATED_CREATEDATE);
        assertThat(testBilling.getCreateBy()).isEqualTo(UPDATED_CREATE_BY);
        assertThat(testBilling.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testBilling.getUpdateBy()).isEqualTo(UPDATED_UPDATE_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingBilling() throws Exception {
        int databaseSizeBeforeUpdate = billingRepository.findAll().size();

        // Create the Billing

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBillingMockMvc.perform(put("/api/billings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billing)))
            .andExpect(status().isCreated());

        // Validate the Billing in the database
        List<Billing> billingList = billingRepository.findAll();
        assertThat(billingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBilling() throws Exception {
        // Initialize the database
        billingRepository.saveAndFlush(billing);
        int databaseSizeBeforeDelete = billingRepository.findAll().size();

        // Get the billing
        restBillingMockMvc.perform(delete("/api/billings/{id}", billing.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Billing> billingList = billingRepository.findAll();
        assertThat(billingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Billing.class);
        Billing billing1 = new Billing();
        billing1.setId(1L);
        Billing billing2 = new Billing();
        billing2.setId(billing1.getId());
        assertThat(billing1).isEqualTo(billing2);
        billing2.setId(2L);
        assertThat(billing1).isNotEqualTo(billing2);
        billing1.setId(null);
        assertThat(billing1).isNotEqualTo(billing2);
    }
}
