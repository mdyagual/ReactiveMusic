package ec.com.reactive.music.service.impl;

import ec.com.reactive.music.domain.dto.AlbumDTO;
import ec.com.reactive.music.domain.entities.Album;
import ec.com.reactive.music.repository.IAlbumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class AlbumServiceImplTest {
    //Se mockea todos los recursos externos*
    @Mock
    IAlbumRepository albumRepositoryMock;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    SongServiceImpl songService;

    //Se injecta es el servicio/caso de uso (?)
    AlbumServiceImpl albumServiceImpl;

    //Antes de cada test instancie album service
    @BeforeEach
    void setup(){
        albumServiceImpl = new AlbumServiceImpl(albumRepositoryMock,songService,modelMapper);

    }



    @Test
    @DisplayName("getAlbumById()")
    void getAlbumById(){
        Album albumDTOExpected = new Album();
        albumDTOExpected.setIdAlbum("12345678-9");
        albumDTOExpected.setName("albumTesting1");
        albumDTOExpected.setArtist("testerArtist");
        albumDTOExpected.setYearRelease(2015);


        ResponseEntity<AlbumDTO> albumDTOResponse = new ResponseEntity<>(modelMapper.map(albumDTOExpected,AlbumDTO.class),HttpStatus.FOUND);

        Mockito.when(albumRepositoryMock.findById("12345678-9")).thenReturn(Mono.just(albumDTOExpected));

        var service = albumServiceImpl.findAlbumById("12345678-9");

        StepVerifier.create(service)
                .expectNext(albumDTOResponse)
                .expectComplete()
                .verify();

        //Si está utilizando lo que yo mockee
        Mockito.verify(albumRepositoryMock).findById("12345678-9");

        /*StepVerifier
                .create(Mono.just(Mockito.when(albumServiceMock.findAlbumById("12345678-9"))
                        .thenReturn(albumDTOResponse)))
                .expectComplete().verifyLater();*/

    }

    @Test
    @DisplayName("getAlbumByIdError()")
    void getAlbumByIdError(){
        Mono<ResponseEntity<AlbumDTO>> albumDTOResponse = Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        StepVerifier
                .create(Mono.just(Mockito.when(albumServiceImpl.findAlbumById("98765432-1"))
                    .thenReturn(albumDTOResponse)))
                .expectComplete().verifyLater();
    }

    @Test
    @DisplayName("saveAlbum()")
    void saveAlbum(){
        AlbumDTO albumDTOExpected = new AlbumDTO();
        albumDTOExpected.setIdAlbum("12345678-9");
        albumDTOExpected.setName("albumTesting1");
        albumDTOExpected.setArtist("testerArtist");
        albumDTOExpected.setYearRelease(2015);

        Mono<ResponseEntity<AlbumDTO>> albumDTOResponse = Mono.just(new ResponseEntity<>(albumDTOExpected,HttpStatus.CREATED));

        StepVerifier
                .create(Mono.just(Mockito.when(albumServiceImpl.saveAlbum(albumDTOExpected))
                        .thenReturn(albumDTOResponse)))
                .expectComplete().verifyLater();
    }

    @Test
    @DisplayName("updateAlbum()")
    void updateAlbum(){
        AlbumDTO albumDTOToSearch = new AlbumDTO();
        albumDTOToSearch.setIdAlbum("12345678-9");
        albumDTOToSearch.setName("albumTesting1");
        albumDTOToSearch.setArtist("testerArtist");
        albumDTOToSearch.setYearRelease(2015);

        AlbumDTO albumDTOExpected = new AlbumDTO();
        albumDTOExpected.setIdAlbum("12345678-9");
        albumDTOExpected.setName("albumTestingEdited");
        albumDTOExpected.setArtist("testerArtist");
        albumDTOExpected.setYearRelease(2015);

        Mono<ResponseEntity<AlbumDTO>> albumDTOResponse = Mono.just(new ResponseEntity<>(albumDTOExpected,HttpStatus.CREATED));

        StepVerifier
                .create(Mono.just(Mockito.when(albumServiceImpl.updateAlbum(albumDTOToSearch.getIdAlbum(), albumDTOExpected))
                        .thenReturn(albumDTOResponse)))
                .expectComplete().verifyLater();

    }




}