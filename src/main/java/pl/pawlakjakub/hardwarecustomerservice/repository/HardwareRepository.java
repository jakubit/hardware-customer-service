package pl.pawlakjakub.hardwarecustomerservice.repository;

import org.springframework.data.repository.CrudRepository;
import pl.pawlakjakub.hardwarecustomerservice.model.Hardware;

public interface HardwareRepository extends CrudRepository<Hardware, Long> {
}
