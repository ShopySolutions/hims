package mis.repository;

import mis.domain.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the House entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HouseRepository extends JpaRepository<House, Long> {

    @Query("select house from House house where house.user.login = ?#{principal.username}")
    Page<House> findByUserIsCurrentUser(Pageable pageable);

}
