package fitpass.fitpass.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import fitpass.fitpass.model.entity.Image;

@Getter
@Setter
@NoArgsConstructor
public class ImageDTO {
    private Long id;
    private String path;
//    private Boolean isDeleted = false;

    public ImageDTO(Image image){
        this.id = image.getId();
        this.path = image.getPath();
//        this.isDeleted = false;
    }
}
