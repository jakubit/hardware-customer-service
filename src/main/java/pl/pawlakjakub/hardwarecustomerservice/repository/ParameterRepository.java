package pl.pawlakjakub.hardwarecustomerservice.repository;

import org.springframework.data.repository.CrudRepository;
import pl.pawlakjakub.hardwarecustomerservice.model.Parameter;

public interface ParameterRepository extends CrudRepository<Parameter, Long> {
}
