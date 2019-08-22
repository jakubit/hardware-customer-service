package pl.pawlakjakub.hardwarecustomerservice.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity(name = "hardware")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Hardware implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "hardware_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    private boolean isBroken;

    @OneToMany(mappedBy = "hardware", fetch = FetchType.LAZY)
    private Set<Comment> commentSet = new HashSet<>();

    @OneToMany(mappedBy = "hardware", fetch = FetchType.LAZY)
    private Set<HardwareParameter> hardwareParameterSet = new HashSet<>();

}
