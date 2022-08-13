package ec.com.reactive.music.domain.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;


@Document(collection = "Playlist")
public class Playlist {
    private String name;
    private String username;
    private ArrayList<Song> songs;
    private String duration;
}
