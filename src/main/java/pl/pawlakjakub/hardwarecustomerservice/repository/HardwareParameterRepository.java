package pl.pawlakjakub.hardwarecustomerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pawlakjakub.hardwarecustomerservice.model.HardwareParameter;

import java.util.List;

public interface HardwareParameterRepository extends JpaRepository<HardwareParameter, Long> {
    List<HardwareParameter> findAllByHardware_Id(Long hardwareId);
}
