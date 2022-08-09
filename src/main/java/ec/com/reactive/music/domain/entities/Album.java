package ec.com.reactive.music.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Document
public class Album {
    @Id
    private final String idAlbum = UUID.randomUUID().toString().substring(0, 10);
    private String name;
    private String artist;
    private Date releaseDate;
    private ArrayList<Song> songs;

}
