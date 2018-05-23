package mis.repository;

import mis.domain.Profile;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Profile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {


    @Query("select profile from Profile profile where profile.user.login = ?#{principal.username}")
    Profile getCurrentUserProfile();

}
