package fitpass.fitpass.service;

import fitpass.fitpass.model.dto.CommentDTO;
import fitpass.fitpass.model.entity.Comment;

public interface CommentService {
    Comment create(CommentDTO commentDTO);
}
