package ec.com.reactive.music.domain.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Document(collection = "Song")
public class Song {
    @Id
    private String idSong;
    private String name;
    private String lyricsBy;
    private String producedBy;
    private String arrangedBy;
    private String timestamp;
}
