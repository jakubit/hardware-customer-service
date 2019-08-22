package pl.pawlakjakub.hardwarecustomerservice.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity(name = "hardware_parameters")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class HardwareParameter {
    @Id
    @GeneratedValue
    @Column(name = "hardware_parameter_id")
    private Long id;

    private String value;

    @ManyToOne
    @JoinColumn(name = "hardware_id")
    @JsonIgnore
    Hardware hardware;

    @ManyToOne
    @JoinColumn(name = "parameter_id")
    Parameter parameter;

}
