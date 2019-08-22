package pl.pawlakjakub.hardwarecustomerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import pl.pawlakjakub.hardwarecustomerservice.model.Comment;
import pl.pawlakjakub.hardwarecustomerservice.repository.CommentRepository;

import java.util.List;

@Service
@Secured("ROLE_USER")
public class CommentService {

    private CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    public List<Comment> getAllHardwareComments(Long hardwareId) {
        List<Comment> comments = commentRepository.findCommentsByHardware_Id(hardwareId);
        return comments;
    }
}
