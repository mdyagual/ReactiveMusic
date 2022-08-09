package ec.com.reactive.music.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Document
public class Song {
    @Id
    private final String idSong= UUID.randomUUID().toString().substring(0, 10);
    private String name;
    private String lyricsBy;
    private String producedBy;
    private String arrangedBy;
    private String timestamp;
}
