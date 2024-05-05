package franklinit.com.br.repository;

import franklinit.com.br.domain.Anime;
import franklinit.com.br.util.AnimeCreator;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Test anime repo")
class AnimeRepositoryTest {

    @Autowired
    private AnimeRepository animeRepository;
    @Test
    @DisplayName("Save anime successfull")
    void savePersistence_WhenSuccessFul() {
        Anime anime = AnimeCreator.createAnimeToBeSaved();
        Anime animeSave = animeRepository.save(anime);
        Assertions.assertThat(animeSave).isNotNull();
        Assertions.assertThat(animeSave.getId()).isNotNull();
        Assertions.assertThat(animeSave.getNome()).isEqualTo(animeSave.getNome());
    }

    @Test
    @DisplayName("Update anime successfull")
    void update_Persistence_WhenSuccessFul() {
        Anime anime = AnimeCreator.createAnimeToBeSaved();
        Anime animeSave = animeRepository.save(anime);

        animeSave.setNome("UpdateAnime");

        Anime animeUpdate = this.animeRepository.save(animeSave);

        Assertions.assertThat(animeUpdate).isNotNull();
        Assertions.assertThat(animeUpdate.getId()).isNotNull();
        Assertions.assertThat(animeUpdate.getNome()).isEqualTo(animeSave.getNome());

    }

    @Test
    @DisplayName("Delete anime successfull")
    void delete_RemovesAnime_WhenSuccessFul() {
        Anime anime = AnimeCreator.createAnimeToBeSaved();
        Anime animeSave = animeRepository.save(anime);

        this.animeRepository.delete(animeSave);
        Optional<Anime> animeOptional = this.animeRepository.findById(animeSave.getId());

        Assertions.assertThat(animeOptional).isEmpty();
    }

    @Test
    @DisplayName("Find by name return anime when successfull")
    void findByName_ReturnsListOfAnime_WhenSuccessFul() {
        Anime anime = AnimeCreator.createAnimeToBeSaved();
        Anime animeSave = animeRepository.save(anime);
        String nome =  animeSave.getNome();
        List<Anime> animes =  this.animeRepository.findByNome(nome);

        Assertions.assertThat(animes).isNotEmpty();
        Assertions.assertThat(animes).contains(animeSave);
    }
    @Test
    @DisplayName("Find by name return empty lista when no anime is found")
    void findByName_ReturnsEmptyList_WhenAnimeIsNotFound() {
        List<Anime> animes =  this.animeRepository.findByNome("xxxxx");

        Assertions.assertThat(animes).isEmpty();
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when name is empty")
    void save_ThrowsConstraintViolationException_whenNameIsEmpty() {
        Anime anime = new Anime();

//        Assertions.assertThatThrownBy(()-> this.animeRepository.save(anime))
//                .isInstanceOf(ConstraintViolationException.class);
//
               Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(()-> this.animeRepository.save(anime))
                       .withMessageContaining( "The anime name cannot be empty");


    }
}