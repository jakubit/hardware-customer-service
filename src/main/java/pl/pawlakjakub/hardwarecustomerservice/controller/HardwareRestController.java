package pl.pawlakjakub.hardwarecustomerservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pawlakjakub.hardwarecustomerservice.model.Comment;
import pl.pawlakjakub.hardwarecustomerservice.model.Hardware;
import pl.pawlakjakub.hardwarecustomerservice.model.HardwareParameter;
import pl.pawlakjakub.hardwarecustomerservice.service.CommentService;
import pl.pawlakjakub.hardwarecustomerservice.service.HardwareParameterService;
import pl.pawlakjakub.hardwarecustomerservice.service.HardwareService;

import java.util.List;

@RestController
@RequestMapping(path = "/api/hardware")
public class HardwareRestController {

    private HardwareService hardwareService;
    private CommentService commentService;
    private HardwareParameterService hardwareParameterService;

    @Autowired
    public HardwareRestController(HardwareService hardwareService, CommentService commentService, HardwareParameterService hardwareParameterService) {
        this.hardwareService = hardwareService;
        this.commentService = commentService;
        this.hardwareParameterService = hardwareParameterService;
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Hardware> getHardwareById(@PathVariable Long id) {
        return hardwareService.getHardwareById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/{id}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Comment> getHardwareComments(@PathVariable Long id) {
        return commentService.getAllHardwareComments(id);
    }

    @PostMapping(path = "/{id}/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveHardwareComment(@PathVariable(name = "id") Long hardwareId, @RequestBody Comment comment) {
        hardwareService.addComment(hardwareId, comment);
    }

    @GetMapping(path = "/{id}/parameters", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<HardwareParameter> getHardwareParameters(@PathVariable Long id) {
        return hardwareParameterService.getAllHardwareParameters(id);
    }

    @PostMapping(path = "/{id}/parameters", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveHardwareParameter(@PathVariable(name = "id") Long hardwareId, @RequestBody HardwareParameter hardwareParameter) {
        hardwareService.addParameter(hardwareId, hardwareParameter);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Hardware> getAllHardware() {
        return hardwareService.getAllHardware();
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveHardware(@RequestBody Hardware hardware) {
        hardwareService.saveHardware(hardware);
    }

}
