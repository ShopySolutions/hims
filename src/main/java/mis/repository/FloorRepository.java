package mis.repository;

import mis.domain.Floor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Floor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FloorRepository extends JpaRepository<Floor, Long> {

    @Query("select floor from Floor floor where floor.user.login = ?#{principal.username}")
    Page<Floor> findByUserIsCurrentUser(Pageable pageable);

}
