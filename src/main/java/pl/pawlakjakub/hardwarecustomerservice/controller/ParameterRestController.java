package pl.pawlakjakub.hardwarecustomerservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pawlakjakub.hardwarecustomerservice.model.Parameter;
import pl.pawlakjakub.hardwarecustomerservice.service.ParameterService;

@RestController
@RequestMapping(path = "/api/parameters")
public class ParameterRestController {

    private ParameterService parameterService;

    @Autowired
    public ParameterRestController(ParameterService parameterService) {
        this.parameterService = parameterService;
    }

    @PostMapping
    public void saveParameter(@RequestBody Parameter parameter) {
        parameterService.saveParameter(parameter);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Parameter> getParameterById(@PathVariable Long id) {
        return parameterService.getParameterById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
