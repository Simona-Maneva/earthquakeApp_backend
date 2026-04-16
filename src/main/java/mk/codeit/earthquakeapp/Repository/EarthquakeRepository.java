package mk.codeit.earthquakeapp.Repository;

import mk.codeit.earthquakeapp.Model.Earthquake;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface EarthquakeRepository extends JpaRepository<Earthquake,String> {

    List<Earthquake>findByMagnitudeGreaterThan(Double magnitude);

    List<Earthquake> findByTimeAfter(Instant time);
}
