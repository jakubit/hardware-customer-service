package pl.pawlakjakub.hardwarecustomerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import pl.pawlakjakub.hardwarecustomerservice.model.Parameter;
import pl.pawlakjakub.hardwarecustomerservice.repository.ParameterRepository;

import java.util.Optional;

@Service
@Secured("ROLE_USER")
public class ParameterService {

    private ParameterRepository parameterRepository;

    @Autowired
    public ParameterService(ParameterRepository parameterRepository) {
        this.parameterRepository = parameterRepository;
    }

    public void saveParameter(Parameter parameter) {
        parameterRepository.save(parameter);
    }

    public Optional<Parameter> getParameterById(Long id) {
        Optional<Parameter> optionalParameter = parameterRepository.findById(id);
        return optionalParameter;
    }
}
