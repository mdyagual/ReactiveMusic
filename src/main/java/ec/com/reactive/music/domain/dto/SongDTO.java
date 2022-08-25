package ec.com.reactive.music.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm:ss")
public class SongDTO {
    private String idSong= UUID.randomUUID().toString().substring(0, 10);
    private String name;
    private String albumId;
    private String lyricsBy;
    private String producedBy;
    private String arrangedBy;
    private LocalTime timestamp;

}
