package mis.repository;

import mis.domain.City;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the City entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    @Query("select city from City city where city.state.id=:state")
    List<City> findCityByState(@Param("state") Long state);
}
