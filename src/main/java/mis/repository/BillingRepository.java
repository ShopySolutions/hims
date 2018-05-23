package mis.repository;

import mis.domain.Billing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Billing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {

    @Query("select billing from Billing billing where billing.user.login = ?#{principal.username}")
    Page<Billing> findByUserIsCurrentUser(Pageable pageable);

}
