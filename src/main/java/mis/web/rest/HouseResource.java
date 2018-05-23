package mis.web.rest;

import com.codahale.metrics.annotation.Timed;
import mis.domain.House;

import mis.repository.HouseRepository;
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
 * REST controller for managing House.
 */
@RestController
@RequestMapping("/api")
public class HouseResource {

    private final Logger log = LoggerFactory.getLogger(HouseResource.class);

    private static final String ENTITY_NAME = "house";

    private final HouseRepository houseRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private ProfileRepository profileRepository;

    public HouseResource(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    /**
     * POST  /houses : Create a new house.
     *
     * @param house the house to create
     * @return the ResponseEntity with status 201 (Created) and with body the new house, or with status 400 (Bad Request) if the house has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/houses")
    @Timed
    public ResponseEntity<House> createHouse(@Valid @RequestBody House house) throws URISyntaxException {
        log.debug("REST request to save House : {}", house);
        if (house.getId() != null) {
            throw new BadRequestAlertException("A new house cannot already have an ID", ENTITY_NAME, "idexists");
        }
        house.setCreatedate(LocalDate.now());
        house.setCreateBy(userRepository.getCurrentUserId());
        house.setUser(userRepository.getCurrentUser());
        house.setProfile(profileRepository.getCurrentUserProfile());
        House result = houseRepository.save(house);

        return ResponseEntity.created(new URI("/api/houses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /houses : Updates an existing house.
     *
     * @param house the house to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated house,
     * or with status 400 (Bad Request) if the house is not valid,
     * or with status 500 (Internal Server Error) if the house couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/houses")
    @Timed
    public ResponseEntity<House> updateHouse(@Valid @RequestBody House house) throws URISyntaxException {
        log.debug("REST request to update House : {}", house);
        if (house.getId() == null) {
            return createHouse(house);
        }
        house.setUpdateDate(LocalDate.now());
        house.setUpdateBy(userRepository.getCurrentUserId());
        House result = houseRepository.save(house);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, house.getId().toString()))
            .body(result);
    }

    /**
     * GET  /houses : get all the houses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of houses in body
     */
    @GetMapping("/houses")
    @Timed
    public ResponseEntity<List<House>> getAllHouses(Pageable pageable) {
        log.debug("REST request to get a page of Houses");
        Page<House> page = houseRepository.findByUserIsCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/houses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /houses/:id : get the "id" house.
     *
     * @param id the id of the house to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the house, or with status 404 (Not Found)
     */
    @GetMapping("/houses/{id}")
    @Timed
    public ResponseEntity<House> getHouse(@PathVariable Long id) {
        log.debug("REST request to get House : {}", id);
        House house = houseRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(house));
    }

    /**
     * DELETE  /houses/:id : delete the "id" house.
     *
     * @param id the id of the house to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/houses/{id}")
    @Timed
    public ResponseEntity<Void> deleteHouse(@PathVariable Long id) {
        log.debug("REST request to delete House : {}", id);
        houseRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
