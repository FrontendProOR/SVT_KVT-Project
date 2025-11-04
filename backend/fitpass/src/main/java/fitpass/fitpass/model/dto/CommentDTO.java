package fitpass.fitpass.model.dto;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import fitpass.fitpass.model.entity.Comment;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {

    private Long id;
    private String text;
    private LocalDateTime createdAt;
    private UserDTO user;
    private CommentDTO comment;
//    private Boolean isDeleted = false;

    public CommentDTO(Comment comment){
        this.id = comment.getId();;
        this.text = comment.getText();
        this.createdAt = comment.getCreatedAt();
        this.user = new UserDTO(comment.getBelongsTo());
        if(comment.getRepliesTo() != null){
            this.comment =  new CommentDTO(comment.getRepliesTo());
        }
//        this.isDeleted = false;
    }

}