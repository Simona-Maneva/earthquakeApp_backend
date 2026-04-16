package mk.codeit.earthquakeapp.Service;

import lombok.RequiredArgsConstructor;
import mk.codeit.earthquakeapp.Exception.ApiException;
import mk.codeit.earthquakeapp.Exception.ParsingException;
import mk.codeit.earthquakeapp.Model.Earthquake;
import mk.codeit.earthquakeapp.Repository.EarthquakeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EarthquakeService {

    private final EarthquakeRepository repository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String apiUrl =
            "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";

    public List<Earthquake> findAll() {
        return repository.findAll();
    }

    public List<Earthquake> findEarthquakesByMagnitude() {
        return repository.findByMagnitudeGreaterThan(2.0);
    }

    public List<Earthquake> findEarthquakesAfter(Instant time) {
        return repository.findByTimeAfter(time);
    }

    public void deleteEarthquake(String id) {
        repository.deleteById(id);
    }

    public List<Earthquake> syncEarthquakes() {

        String json = restTemplate.getForObject(apiUrl, String.class);

        if (json == null) {
            throw new ApiException("Empty response from USGS API");
        }

        List<Earthquake> earthquakes = parse(json);

        repository.deleteAll();

        return repository.saveAll(earthquakes);
    }

    private List<Earthquake> parse(String json) {

        List<Earthquake> list = new ArrayList<>();

        try {
            JsonNode root = objectMapper.readTree(json);
            JsonNode features = root.get("features");

            if (features == null || !features.isArray()) {
                throw new ParsingException("Invalid JSON structure: missing 'features'");
            }

            for (JsonNode f : features) {

                JsonNode p = f.get("properties");
                if (p == null) continue;

                Earthquake eq = Earthquake.builder()
                        .id(f.path("id").asText())
                        .magnitude(p.hasNonNull("mag") ? p.get("mag").asDouble() : null)
                        .magType(p.path("magType").asText(null))
                        .place(p.path("place").asText("Unknown"))
                        .title(p.path("title").asText("No title"))
                        .time(p.hasNonNull("time")
                                ? Instant.ofEpochMilli(p.get("time").asLong())
                                : null)
                        .build();

                list.add(eq);
            }

        } catch (Exception e) {
            throw new ParsingException("Error parsing earthquake data", e);
        }

        return list;
    }


}
