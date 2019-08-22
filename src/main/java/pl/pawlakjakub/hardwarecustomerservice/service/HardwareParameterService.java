package pl.pawlakjakub.hardwarecustomerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import pl.pawlakjakub.hardwarecustomerservice.model.HardwareParameter;
import pl.pawlakjakub.hardwarecustomerservice.repository.HardwareParameterRepository;

import java.util.List;

@Service
@Secured("ROLE_USER")
public class HardwareParameterService {

    private HardwareParameterRepository hardwareParameterRepository;

    @Autowired
    public HardwareParameterService(HardwareParameterRepository hardwareParameterRepository) {
        this.hardwareParameterRepository = hardwareParameterRepository;
    }

    public List<HardwareParameter> getAllHardwareParameters(Long hardwareId) {
        List<HardwareParameter> hardwareParameters = hardwareParameterRepository.findAllByHardware_Id(hardwareId);
        return hardwareParameters;
    }

    public void saveHardwareParameter(HardwareParameter hardwareParameter) {
        hardwareParameterRepository.save(hardwareParameter);
    }
}
