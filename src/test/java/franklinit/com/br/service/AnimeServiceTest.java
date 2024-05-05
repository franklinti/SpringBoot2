package franklinit.com.br.service;

import franklinit.com.br.domain.Anime;
import franklinit.com.br.exception.BadRequestExeception;
import franklinit.com.br.repository.AnimeRepository;
import franklinit.com.br.util.AnimeCreator;
import franklinit.com.br.util.AnimePostRequestBodyCreator;
import franklinit.com.br.util.AnimePutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class AnimeServiceTest {

    @InjectMocks
    private AnimeServiceImpl animeService;
    @Mock
    private AnimeRepository animeRepositoryMock;

    @BeforeEach
    void setUp(){
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createAnimeValidAnime()));
        BDDMockito.when(animeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(animePage);

        BDDMockito.when(animeRepositoryMock.findAll())
                .thenReturn(List.of(AnimeCreator.createAnimeValidAnime()));

        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(AnimeCreator.createAnimeValidAnime()));

        BDDMockito.when(animeRepositoryMock.findByNome(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createAnimeValidAnime()));

        BDDMockito.when(animeRepositoryMock.save(ArgumentMatchers.any(Anime.class)))
                .thenReturn(AnimeCreator.createAnimeValidAnime());

        BDDMockito.doNothing().when(animeRepositoryMock).delete(ArgumentMatchers.any(Anime.class));

    }

    @Test
    @DisplayName("Return list of anime when succesful")
    void listAll_ReturnAnimeInPage_whenSuccessful(){
        String expectedName =  AnimeCreator.createAnimeValidAnime().getNome();
        Page<Anime> animePage =  animeService.listAllPage(PageRequest.of(1,1));

        Assertions.assertThat(animePage)
                .isNotNull();
        Assertions.assertThat(animePage.toList())
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getNome())
                .isEqualTo(expectedName);

    }

    @Test
    @DisplayName(" ListAll returns list of anime when succesful")
    void listAllNoPageable_ReturnAnimeInPage_whenSuccessful(){
        String expectedName =  AnimeCreator.createAnimeValidAnime().getNome();
        List<Anime> animePage =  animeService.listAll();

        Assertions.assertThat(animePage)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animePage.get(0).getNome()).isEqualTo(expectedName);

    }

    @Test
    @DisplayName(" FindById returns anime when succesful")
    void findByIdOrThrowBadRequestException_ReturnAnimeInPage_whenSuccessful(){
       Long expectedId = AnimeCreator.createAnimeValidAnime().getId();

       Anime anime = animeService.findByIdOrThrowBadRequestException(1);
       Assertions.assertThat(anime).isNotNull();

       Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);

    }

    @Test
    @DisplayName(" FindByName returns anime when succesful")
    void findByNome_ReturnAnimeInPage_whenSuccessful(){
        String expectedName =  AnimeCreator.createAnimeValidAnime().getNome();
        List<Anime> anime =  animeService.findByNome(expectedName);

        Assertions.assertThat(anime)
                .isNotNull();
        Assertions.assertThat(anime.get(0).getNome()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName(" save returns anime when succesful")
    void save_ReturnAnimeInPage_whenSuccessful(){

        Anime anime =  animeService.save(AnimePostRequestBodyCreator.createAnimePostRequestBody());

        Assertions.assertThat(anime)
                .isNotNull()
                .isEqualTo(AnimeCreator.createAnimeValidAnime());
    }
    @Test
    @DisplayName(" replace returns anime when succesful")
    void replace_ReturnAnimeInPage_whenSuccessful(){
        Assertions.assertThatCode(()->animeService.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName(" delete returns anime when succesful")
    void delete_RemoveAnimeInPage_whenSuccessful(){
        Assertions.assertThatCode(()->animeService.delete(1))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName(" findByIdOrThrowBadRequestException throws BadRequestException when anime is not found")
    void findByIdOrThrowBadRequestException_ThrowsBadRequestException_ReturnAnimeInPage_whenSuccessful(){
        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestExeception.class)
                .isThrownBy(()->this.animeService.findByIdOrThrowBadRequestException(1));

    }
}
