package mis.web.rest;

import com.codahale.metrics.annotation.Timed;
import mis.domain.Floor;

import mis.repository.FloorRepository;
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
 * REST controller for managing Floor.
 */
@RestController
@RequestMapping("/api")
public class FloorResource {

    private final Logger log = LoggerFactory.getLogger(FloorResource.class);

    private static final String ENTITY_NAME = "floor";

    private final FloorRepository floorRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private ProfileRepository profileRepository;

    public FloorResource(FloorRepository floorRepository) {
        this.floorRepository = floorRepository;
    }

    /**
     * POST  /floors : Create a new floor.
     *
     * @param floor the floor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new floor, or with status 400 (Bad Request) if the floor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/floors")
    @Timed
    public ResponseEntity<Floor> createFloor(@Valid @RequestBody Floor floor) throws URISyntaxException {
        log.debug("REST request to save Floor : {}", floor);
        if (floor.getId() != null) {
            throw new BadRequestAlertException("A new floor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        floor.setCreatedate(LocalDate.now());
        floor.setCreateBy(userRepository.getCurrentUserId());
        floor.setUser(userRepository.getCurrentUser());
        floor.setProfile(profileRepository.getCurrentUserProfile());
        Floor result = floorRepository.save(floor);
        return ResponseEntity.created(new URI("/api/floors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /floors : Updates an existing floor.
     *
     * @param floor the floor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated floor,
     * or with status 400 (Bad Request) if the floor is not valid,
     * or with status 500 (Internal Server Error) if the floor couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/floors")
    @Timed
    public ResponseEntity<Floor> updateFloor(@Valid @RequestBody Floor floor) throws URISyntaxException {
        log.debug("REST request to update Floor : {}", floor);
        if (floor.getId() == null) {
            return createFloor(floor);
        }
        floor.setUpdateDate(LocalDate.now());
        floor.setUpdateBy(userRepository.getCurrentUserId());
        Floor result = floorRepository.save(floor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, floor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /floors : get all the floors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of floors in body
     */
    @GetMapping("/floors")
    @Timed
    public ResponseEntity<List<Floor>> getAllFloors(Pageable pageable) {
        log.debug("REST request to get a page of Floors");
        Page<Floor> page = floorRepository.findByUserIsCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/floors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /floors/:id : get the "id" floor.
     *
     * @param id the id of the floor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the floor, or with status 404 (Not Found)
     */
    @GetMapping("/floors/{id}")
    @Timed
    public ResponseEntity<Floor> getFloor(@PathVariable Long id) {
        log.debug("REST request to get Floor : {}", id);
        Floor floor = floorRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(floor));
    }

    /**
     * DELETE  /floors/:id : delete the "id" floor.
     *
     * @param id the id of the floor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/floors/{id}")
    @Timed
    public ResponseEntity<Void> deleteFloor(@PathVariable Long id) {
        log.debug("REST request to delete Floor : {}", id);
        floorRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
