package mk.codeit.earthquakeapp.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Earthquake {

    @Id
    private String id;

    private Double magnitude;

    private String magType;

    private String place;

    private String title;

    private Instant time;
}
