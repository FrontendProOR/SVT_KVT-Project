package fitpass.fitpass.service.implementation;

import fitpass.fitpass.model.dto.CommentDTO;
import fitpass.fitpass.model.entity.Comment;
import fitpass.fitpass.model.entity.User;
import fitpass.fitpass.repository.CommentRepository;
import fitpass.fitpass.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Override
    public Comment create(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setText(commentDTO.getText());
        User user = new User(commentDTO.getUser());
        comment.setBelongsTo(user);
        comment.setCreatedAt(LocalDateTime.now());
        if(commentDTO.getComment() != null){
            comment.setRepliesTo(new Comment(commentDTO.getComment()));
        }
        return commentRepository.save(comment);
    }
}
