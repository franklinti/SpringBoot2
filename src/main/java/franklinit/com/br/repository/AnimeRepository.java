package franklinit.com.br.repository;

import franklinit.com.br.domain.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimeRepository extends JpaRepository<Anime, Long> {

    List<Anime> findByNome(String nome);
}
