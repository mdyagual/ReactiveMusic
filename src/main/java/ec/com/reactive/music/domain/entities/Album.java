package ec.com.reactive.music.domain.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Document(collection = "Album")
public class Album {
    @Id
    private String idAlbum;
    private String name;
    private String artist;
    private Integer yearRelease;
    private ArrayList<Song> songs=new ArrayList<>();

}
