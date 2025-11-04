package fitpass.fitpass.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import fitpass.fitpass.model.dto.CommentDTO;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User belongsTo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id", referencedColumnName = "id")
    private Comment repliesTo;

//    @Column(name = "is_deleted",nullable = false)
//    private Boolean isDeleted = false;

    public Comment(CommentDTO commentDTO) {
        this.id = commentDTO.getId();
        this.text = commentDTO.getText();
        this.createdAt = commentDTO.getCreatedAt();
        this.belongsTo = new User(commentDTO.getUser());
        this.repliesTo = commentDTO.getComment() != null ? new Comment(commentDTO.getComment()) : null;
//        this.isDeleted = commentDTO.getIsDeleted() != null ? commentDTO.getIsDeleted() : false;
    }
}
