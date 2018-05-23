package mis.web.rest;

import com.codahale.metrics.annotation.Timed;
import mis.domain.Billing;

import mis.repository.BillingRepository;
import mis.repository.ProfileRepository;
import mis.repository.UserRepository;
import mis.web.rest.errors.BadRequestAlertException;
import mis.web.rest.util.HeaderUtil;
import mis.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Billing.
 */
@RestController
@RequestMapping("/api")
public class BillingResource {

    @Inject

    private UserRepository userRepository;

    private final Logger log = LoggerFactory.getLogger(BillingResource.class);

    private static final String ENTITY_NAME = "billing";

    private final BillingRepository billingRepository;

    @Inject
    private ProfileRepository profileRepository;

    public BillingResource(BillingRepository billingRepository) {
        this.billingRepository = billingRepository;
    }

    /**
     * POST  /billings : Create a new billing.
     *
     * @param billing the billing to create
     * @return the ResponseEntity with status 201 (Created) and with body the new billing, or with status 400 (Bad Request) if the billing has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/billings")
    @Timed
    public ResponseEntity<Billing> createBilling(@Valid @RequestBody Billing billing) throws URISyntaxException {
        log.debug("REST request to save Billing : {}", billing);
        if (billing.getId() != null) {
            throw new BadRequestAlertException("A new billing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        billing.setCreateBy(userRepository.getCurrentUserId());
        billing.setCreatedate(LocalDate.now());
        billing.setUser(userRepository.getCurrentUser());
        billing.setProfile(profileRepository.getCurrentUserProfile());
        Billing result = billingRepository.save(billing);
        return ResponseEntity.created(new URI("/api/billings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /billings : Updates an existing billing.
     *
     * @param billing the billing to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated billing,
     * or with status 400 (Bad Request) if the billing is not valid,
     * or with status 500 (Internal Server Error) if the billing couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/billings")
    @Timed
    public ResponseEntity<Billing> updateBilling(@Valid @RequestBody Billing billing) throws URISyntaxException {
        log.debug("REST request to update Billing : {}", billing);
        if (billing.getId() == null) {
            return createBilling(billing);
        }
        billing.setUpdateBy(userRepository.getCurrentUserId());
        billing.setUpdateDate(LocalDate.now());

        Billing result = billingRepository.save(billing);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, billing.getId().toString()))
            .body(result);
    }

    /**
     * GET  /billings : get all the billings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of billings in body
     */
    @GetMapping("/billings")
    @Timed
    public ResponseEntity<List<Billing>> getAllBillings(Pageable pageable) {
        log.debug("REST request to get a page of Billings");
        Page<Billing> page = billingRepository.findByUserIsCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/billings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /billings/:id : get the "id" billing.
     *
     * @param id the id of the billing to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the billing, or with status 404 (Not Found)
     */
    @GetMapping("/billings/{id}")
    @Timed
    public ResponseEntity<Billing> getBilling(@PathVariable Long id) {
        log.debug("REST request to get Billing : {}", id);
        Billing billing = billingRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(billing));
    }

    /**
     * DELETE  /billings/:id : delete the "id" billing.
     *
     * @param id the id of the billing to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/billings/{id}")
    @Timed
    public ResponseEntity<Void> deleteBilling(@PathVariable Long id) {
        log.debug("REST request to delete Billing : {}", id);
        billingRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
