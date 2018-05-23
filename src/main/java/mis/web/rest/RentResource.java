package mis.web.rest;

import com.codahale.metrics.annotation.Timed;
import mis.domain.Profile;
import mis.domain.Rent;

import mis.repository.ProfileRepository;
import mis.repository.RentRepository;
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
 * REST controller for managing Rent.
 */
@RestController
@RequestMapping("/api")
public class RentResource {

    @Inject

    private UserRepository userRepository;

    private final Logger log = LoggerFactory.getLogger(RentResource.class);

    private static final String ENTITY_NAME = "rent";

    private final RentRepository rentRepository;

    @Inject
    private ProfileRepository profileRepository;

    public RentResource(RentRepository rentRepository) {
        this.rentRepository = rentRepository;
    }

    /**
     * POST  /rents : Create a new rent.
     *
     * @param rent the rent to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rent, or with status 400 (Bad Request) if the rent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rents")
    @Timed
    public ResponseEntity<Rent> createRent(@Valid @RequestBody Rent rent) throws URISyntaxException {
        log.debug("REST request to save Rent : {}", rent);
        if (rent.getId() != null) {
            throw new BadRequestAlertException("A new rent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        rent.setCreateBy(userRepository.getCurrentUserId());
        rent.setCreatedate(LocalDate.now());
        rent.setUser(userRepository.getCurrentUser());
        rent.setProfile(profileRepository.getCurrentUserProfile());
        Rent result = rentRepository.save(rent);
        return ResponseEntity.created(new URI("/api/rents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rents : Updates an existing rent.
     *
     * @param rent the rent to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rent,
     * or with status 400 (Bad Request) if the rent is not valid,
     * or with status 500 (Internal Server Error) if the rent couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rents")
    @Timed
    public ResponseEntity<Rent> updateRent(@Valid @RequestBody Rent rent) throws URISyntaxException {
        log.debug("REST request to update Rent : {}", rent);
        if (rent.getId() == null) {
            return createRent(rent);
        }
        rent.setUpdateBy(userRepository.getCurrentUserId());
        rent.setUpdateDate(LocalDate.now());

        Rent result = rentRepository.save(rent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rent.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rents : get all the rents.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rents in body
     */
    @GetMapping("/rents")
    @Timed
    public ResponseEntity<List<Rent>> getAllRents(Pageable pageable) {
        log.debug("REST request to get a page of Rents");
        Page<Rent> page = rentRepository.findByUserIsCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rents");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rents/:id : get the "id" rent.
     *
     * @param id the id of the rent to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rent, or with status 404 (Not Found)
     */
    @GetMapping("/rents/{id}")
    @Timed
    public ResponseEntity<Rent> getRent(@PathVariable Long id) {
        log.debug("REST request to get Rent : {}", id);
        Rent rent = rentRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rent));
    }

    /**
     * DELETE  /rents/:id : delete the "id" rent.
     *
     * @param id the id of the rent to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rents/{id}")
    @Timed
    public ResponseEntity<Void> deleteRent(@PathVariable Long id) {
        log.debug("REST request to delete Rent : {}", id);
        rentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
