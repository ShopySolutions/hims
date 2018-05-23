package mis.repository;

import mis.domain.Flat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Flat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FlatRepository extends JpaRepository<Flat, Long> {

    @Query("select flat from Flat flat where flat.user.login = ?#{principal.username}")
    Page<Flat> findByUserIsCurrentUser(Pageable pageable);

}
