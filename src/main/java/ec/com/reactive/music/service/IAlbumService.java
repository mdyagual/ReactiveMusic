package ec.com.reactive.music.service;

import ec.com.reactive.music.domain.dto.AlbumDTO;
import ec.com.reactive.music.domain.dto.SongDTO;
import ec.com.reactive.music.domain.entities.Album;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAlbumService {
    Mono<ResponseEntity<Flux<AlbumDTO>>>findAllAlbums();
    Mono<ResponseEntity<AlbumDTO>> findAlbumById(String id);
    Mono<ResponseEntity<AlbumDTO>> saveAlbum(AlbumDTO aDto);
    Mono<ResponseEntity<AlbumDTO>> updateAlbum (String id, AlbumDTO aDto);
    Mono<ResponseEntity<AlbumDTO>> addSongToAlbum(String idAlbum, SongDTO sDto);
    Mono<ResponseEntity<String>> deleteAlbum (String idAlbum);


    AlbumDTO entityToDTO(Album a);
    Album dtoToEntity(AlbumDTO aDto);

}
