package ec.com.reactive.music.repository;

import ec.com.reactive.music.domain.dto.SongDTO;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISongRepository extends ReactiveMongoRepository<SongDTO,String> {
}
