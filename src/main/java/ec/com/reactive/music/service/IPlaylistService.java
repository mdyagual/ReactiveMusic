package ec.com.reactive.music.service;

import ec.com.reactive.music.domain.dto.PlaylistDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPlaylistService {
    Mono<PlaylistDTO> savePlaylist(PlaylistDTO p);
    Mono<Void> deletePlaylist(String id);
    Mono<PlaylistDTO> updatePlaylist(String id, PlaylistDTO p);
    Flux<PlaylistDTO>  findAllPlaylists();
}
