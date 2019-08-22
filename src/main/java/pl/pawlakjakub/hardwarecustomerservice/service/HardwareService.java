package pl.pawlakjakub.hardwarecustomerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import pl.pawlakjakub.hardwarecustomerservice.model.Category;
import pl.pawlakjakub.hardwarecustomerservice.model.Comment;
import pl.pawlakjakub.hardwarecustomerservice.model.Hardware;
import pl.pawlakjakub.hardwarecustomerservice.model.HardwareParameter;
import pl.pawlakjakub.hardwarecustomerservice.repository.HardwareRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Secured("ROLE_USER")
public class HardwareService {

    private HardwareRepository hardwareRepository;

    private CategoryService categoryService;
    private CommentService commentService;
    private HardwareParameterService hardwareParameterService;

    @Autowired
    public HardwareService(HardwareRepository hardwareRepository,
                           CategoryService categoryService,
                           CommentService commentService,
                           HardwareParameterService hardwareParameterService) {
        this.hardwareRepository = hardwareRepository;
        this.categoryService = categoryService;
        this.commentService = commentService;
        this.hardwareParameterService = hardwareParameterService;
    }

    public void addComment(Long hardwareId, Comment comment) {
        Optional<Hardware> hardwareOptional = getHardwareById(hardwareId);
        if (hardwareOptional.isPresent()) {
            Hardware hardware = hardwareOptional.get();
            hardware.getCommentSet().add(comment);

            comment.setHardware(hardware);
            commentService.saveComment(comment);

            saveHardware(hardware);
        }
    }

    public void addParameter(Long hardwareId, HardwareParameter hardwareParameter) {
        Optional<Hardware> hardwareOptional = getHardwareById(hardwareId);
        if (hardwareOptional.isPresent()) {
            Hardware hardware = hardwareOptional.get();
            hardware.getHardwareParameterSet().add(hardwareParameter);

            hardwareParameter.setHardware(hardware);
            hardwareParameterService.saveHardwareParameter(hardwareParameter);

            saveHardware(hardware);
        }
    }

    public Optional<Hardware> getHardwareById(Long id) {
        Optional<Hardware> hardware = hardwareRepository.findById(id);
        return hardware;
    }

    public List<Hardware> getAllHardware() {
        List<Hardware> hardware = new LinkedList<>();
        hardwareRepository.findAll().forEach(hardware::add);
        return hardware;
    }

    public void saveHardware(Hardware hardware) {
        getOrCreateHardwareCategory(hardware);
        hardwareRepository.save(hardware);
    }

    private void getOrCreateHardwareCategory(Hardware hardware) {
        Long categoryId = hardware.getCategory().getId();
        if (categoryId != null) {
            Optional<Category> categoryOptional = categoryService.getCategoryById(categoryId);
            if (categoryOptional.isPresent()) {
                Category category = categoryOptional.get();
                hardware.setCategory(category);
            }
        } else {
            categoryService.saveCategory(hardware.getCategory());
        }
    }


}
