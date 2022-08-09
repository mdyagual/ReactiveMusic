package ec.com.reactive.music.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document
public class Playlist {
    private String name;
    private String username;
    private ArrayList<Song> songs;
    private String duration;
}
