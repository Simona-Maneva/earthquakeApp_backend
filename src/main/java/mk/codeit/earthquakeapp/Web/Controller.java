package mk.codeit.earthquakeapp.Web;

import lombok.RequiredArgsConstructor;
import mk.codeit.earthquakeapp.Model.Earthquake;
import mk.codeit.earthquakeapp.Service.EarthquakeService;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/earthquakes")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class Controller {

    private final EarthquakeService earthquakeService;

    @PostMapping("/sync")
    public List<Earthquake> sync() {
        return earthquakeService.syncEarthquakes();
    }

    @GetMapping
    public List<Earthquake> getAll() {
        return earthquakeService.findAll();
    }

    @GetMapping("/greater")
    public List<Earthquake> getGreater() {
        return earthquakeService.findEarthquakesByMagnitude();
    }

    @GetMapping("/after")
    public List<Earthquake> getAfter(@RequestParam Instant time) {
        return earthquakeService.findEarthquakesAfter(time);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        earthquakeService.deleteEarthquake(id);
    }
}
