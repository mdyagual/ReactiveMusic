package ec.com.reactive.music.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SongDTO {
    private String idSong= UUID.randomUUID().toString().substring(0, 10);
    private String name;
    private String lyricsBy;
    private String producedBy;
    private String arrangedBy;
    private String timestamp;
}
