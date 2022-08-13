package ec.com.reactive.music.service;

import ec.com.reactive.music.domain.dto.SongDTO;
import reactor.core.publisher.Mono;

public interface ISongService  {
    Mono<SongDTO> saveSong (SongDTO s);
    Mono<Void> deleteSong (String idSong);
    Mono<SongDTO> updateSong(String idSong, SongDTO s);
}
