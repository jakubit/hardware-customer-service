package pl.pawlakjakub.hardwarecustomerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pawlakjakub.hardwarecustomerservice.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByHardware_Id(Long hardwareId);
}
