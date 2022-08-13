package ec.com.reactive.music.domain.dto;

import ec.com.reactive.music.domain.entities.Song;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AlbumDTO {
    private String idAlbum = UUID.randomUUID().toString().substring(0, 10);
    private String name;
    private String artist;
    private Integer yearRelease;
    private ArrayList<SongDTO> songs=new ArrayList<>();

}
