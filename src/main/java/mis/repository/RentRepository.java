package mis.repository;

import mis.domain.Rent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Rent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RentRepository extends JpaRepository<Rent, Long> {

    @Query("select rent from Rent rent where rent.user.login = ?#{principal.username}")
    Page<Rent> findByUserIsCurrentUser(Pageable pageable);

}
