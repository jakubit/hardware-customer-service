package pl.pawlakjakub.hardwarecustomerservice.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity(name = "parameters")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Parameter {

    @Id
    @GeneratedValue
    @Column(name = "parameter_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ParameterName name;

    public enum ParameterName {COLOUR, SIZE, RESOLUTION, MATERIAL}

}
