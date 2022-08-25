package ec.com.reactive.music.service.impl;

import ec.com.reactive.music.domain.dto.AlbumDTO;
import ec.com.reactive.music.domain.dto.SongDTO;
import ec.com.reactive.music.domain.entities.Album;
import ec.com.reactive.music.domain.entities.Song;
import ec.com.reactive.music.repository.IAlbumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalTime;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class AlbumServiceImplTest {
    //Se mockea todos los recursos externos: Repos
    @Mock
    IAlbumRepository albumRepositoryMock;

    ModelMapper modelMapper; //Helper; No se mockea

    AlbumServiceImpl albumServiceImpl; //Tiene cosas por dentro mockeadas, entonces no se mockea

    //Antes de cada test instancie album service para que ya no aparezca el nullPointerException
    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        albumServiceImpl = new AlbumServiceImpl(albumRepositoryMock,modelMapper);

    }

    @Test
    @DisplayName("getAlbumById()")
    void getAlbumById(){
        Album albumExpected = new Album();
        albumExpected.setIdAlbum("12345678-9");
        albumExpected.setName("albumTesting1");
        albumExpected.setArtist("testerArtist");
        albumExpected.setYearRelease(2015);

        var albumDTOExpected = modelMapper.map(albumExpected,AlbumDTO.class);

        ResponseEntity<AlbumDTO> albumDTOResponse = new ResponseEntity<>(albumDTOExpected,HttpStatus.FOUND);

        Mockito.when(albumRepositoryMock.findById(Mockito.any(String.class))).thenReturn(Mono.just(albumExpected));

        var service = albumServiceImpl.findAlbumById("12345678-9");

        StepVerifier.create(service)
                .expectNext(albumDTOResponse)
                .expectComplete()
                .verify();

        //Si está utilizando lo que yo mockee
        Mockito.verify(albumRepositoryMock).findById("12345678-9");

    }

    @Test
    @DisplayName("getAlbumByIdError()")
    void getAlbumByIdError(){
        ResponseEntity<AlbumDTO> albumDTOResponse = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Mockito.when(albumRepositoryMock.findById(Mockito.any(String.class))).thenReturn(Mono.empty());

        var service = albumServiceImpl.findAlbumById("12345678-9");

        StepVerifier.create(service)
                .expectNext(albumDTOResponse)
                .expectComplete().verify();
    }

    @Test
    @DisplayName("saveAlbum()")
    void saveAlbum(){
        Album albumExpected = new Album();
        albumExpected.setIdAlbum("12345678-9");
        albumExpected.setName("albumTesting1");
        albumExpected.setArtist("testerArtist");
        albumExpected.setYearRelease(2015);

        var albumDTOExpected = modelMapper.map(albumExpected,AlbumDTO.class);

        ResponseEntity<AlbumDTO> albumDTOResponse = new ResponseEntity<>(albumDTOExpected,HttpStatus.CREATED);

        Mockito.when(albumRepositoryMock.save(Mockito.any(Album.class))).thenReturn(Mono.just(albumExpected));

        var service = albumServiceImpl.saveAlbum(albumDTOExpected);

        StepVerifier.create(service)
                .expectNext(albumDTOResponse)
                .expectComplete()
                .verify();

        //Si está utilizando lo que yo mockee
        Mockito.verify(albumRepositoryMock).save(albumExpected);
    }

    @Test
    @DisplayName("updateAlbum()")
    void updateAlbum(){
        Album albumExpected = new Album();
        albumExpected.setIdAlbum("12345678-9");
        albumExpected.setName("albumTesting");
        albumExpected.setArtist("testerArtist");
        albumExpected.setYearRelease(2015);

        var albumEdited = albumExpected.toBuilder().name("albumTestingEdited").build();

        var albumDTOEdited = modelMapper.map(albumEdited,AlbumDTO.class);


        ResponseEntity<AlbumDTO> albumDTOResponse = new ResponseEntity<>(albumDTOEdited,HttpStatus.ACCEPTED);

        Mockito.when(albumRepositoryMock.findById(Mockito.any(String.class))).thenReturn(Mono.just(albumExpected));
        Mockito.when(albumRepositoryMock.save(Mockito.any(Album.class))).thenReturn(Mono.just(albumEdited));

        var service = albumServiceImpl.updateAlbum("12345678-9", albumDTOEdited);

        StepVerifier.create(service)
                .expectNextMatches(albumDTOResponseEntity -> albumDTOResponseEntity.getStatusCode().is2xxSuccessful())
                .expectComplete()
                .verify();

        //Si está utilizando lo que yo mockee
        Mockito.verify(albumRepositoryMock).save(albumEdited);

    }

    /*@Test
    @DisplayName("addSongToAlbum()")
    void addSongToAlbum(){
        Album albumExpected = new Album();
        albumExpected.setIdAlbum("12345678-9");
        albumExpected.setName("albumTesting");
        albumExpected.setArtist("testerArtist");
        albumExpected.setYearRelease(2015);

        Song songExpected = new Song();
        songExpected.setIdSong("11223344-5");
        songExpected.setName("songTesting");
        songExpected.setArrangedBy("ArrangeTesting");
        songExpected.setLyricsBy("LyricsTesting");
        songExpected.setProducedBy("ProducerTesting");
        songExpected.setTimestamp(LocalTime.of(0,3,45));

        ArrayList<Song> songs = new ArrayList<>();
        songs.add(songExpected);

        var albumExpectedWithSong = albumExpected.toBuilder().build();
        albumExpectedWithSong.setSongs(songs);

        var albumDTOExpected = modelMapper.map(albumExpectedWithSong,AlbumDTO.class);
        var songDTOExpected= modelMapper.map(songExpected, SongDTO.class);

        ResponseEntity<AlbumDTO> albumDTOResponse = new ResponseEntity<>(albumDTOExpected,HttpStatus.ACCEPTED);

        Mockito.when(albumRepositoryMock.findById(Mockito.any(String.class))).thenReturn(Mono.just(albumExpected));
        Mockito.when(albumRepositoryMock.save(Mockito.any(Album.class))).thenReturn(Mono.just(albumExpectedWithSong));

        var service = albumServiceImpl.addSongToAlbum("12345678-9", songDTOExpected);

        StepVerifier.create(service)
                .expectNextMatches(albumDTOResponseEntity -> albumDTOResponseEntity.getStatusCode().is2xxSuccessful())
                .expectComplete()
        .verify();

        //Si está utilizando lo que yo mockee
        Mockito.verify(albumRepositoryMock).findById("12345678-9");
        Mockito.verify(albumRepositoryMock).save(albumExpectedWithSong);
    }*/

    /*@Test
    @DisplayName("deleteAlbum()")
    void deleteAlbum(){
        Album albumExpected = new Album();
        albumExpected.setIdAlbum("12345678-9");
        albumExpected.setName("albumTesting");
        albumExpected.setArtist("testerArtist");
        albumExpected.setYearRelease(2015);

        ResponseEntity<String> responseDelete = new ResponseEntity<>("12345678-9",HttpStatus.ACCEPTED);

        Mockito.when(albumRepositoryMock.findById(Mockito.any(String.class))).thenReturn(Mono.just(albumExpected));
        Mockito.when(albumRepositoryMock.deleteById(Mockito.any(String.class))).thenReturn(Mono.empty());


        var service = albumServiceImpl.deleteAlbum(albumExpected.getIdAlbum());

        StepVerifier.create(service)
                .expectNextMatches(stringResponseEntity -> stringResponseEntity.getStatusCode().is2xxSuccessful())
                .expectComplete()
        .verify();


        Mockito.verify(albumRepositoryMock).findById("12345678-9");
        Mockito.verify(albumRepositoryMock).deleteById("12345678-9");

    }*/




}