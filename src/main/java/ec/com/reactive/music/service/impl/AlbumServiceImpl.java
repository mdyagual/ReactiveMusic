package ec.com.reactive.music.service.impl;

import ec.com.reactive.music.domain.dto.AlbumDTO;
import ec.com.reactive.music.domain.entities.Album;
import ec.com.reactive.music.repository.IAlbumRepository;
import ec.com.reactive.music.service.IAlbumService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class AlbumServiceImpl implements IAlbumService {
    @Autowired
    private IAlbumRepository iAlbumRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Mono<ResponseEntity<Flux<AlbumDTO>>>findAllAlbums() {
        return Mono.justOrEmpty(new ResponseEntity<>(this.iAlbumRepository
                    .findAll()
                    .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                    .map(this::entityToDTO),HttpStatus.FOUND))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.NO_CONTENT)));
    }

    @Override
    public Mono<ResponseEntity<AlbumDTO>> findAlbumById(String id) {
        return this.iAlbumRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())))
                .map(this::entityToDTO)
                .map(albumDTO -> new ResponseEntity<>(albumDTO,HttpStatus.FOUND))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

    @Override
    public Mono<ResponseEntity<AlbumDTO>> saveAlbum(AlbumDTO aDto) {
        return this.iAlbumRepository
                .save(dtoToEntity(aDto))
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.EXPECTATION_FAILED.toString())))
                .map(this::entityToDTO)
                .map(albumDTO -> new ResponseEntity<>(albumDTO, HttpStatus.CREATED))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED)));

    }

    @Override
    public Mono<ResponseEntity<AlbumDTO>> updateAlbum(String id, AlbumDTO aDto) {
        return this.iAlbumRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())))
                .flatMap(album -> { //Why flatMap?
                    aDto.setIdAlbum(id);
                    return this.saveAlbum(aDto);
                })
                .map(albumDTOResponseEntity -> new ResponseEntity<>(albumDTOResponseEntity.getBody(),HttpStatus.ACCEPTED))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.NOT_MODIFIED)));

    }

    @Override
    public Mono<ResponseEntity<String>> deleteAlbum(String idAlbum) {
        //Why flatMap?
        return this.iAlbumRepository.findById(idAlbum)
                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NOT_FOUND.toString())))
                .flatMap(album -> this.iAlbumRepository.deleteById(idAlbum))
                .map(voidMono -> new ResponseEntity<>(idAlbum,HttpStatus.ACCEPTED))
                .onErrorResume(throwable -> Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

    @Override
    public AlbumDTO entityToDTO (Album a){
        return this.modelMapper.map(a, AlbumDTO.class);
    }

    @Override
    public Album dtoToEntity(AlbumDTO aDto){
        return this.modelMapper.map(aDto,Album.class);
    }

}
