package franklinit.com.br.controller;

import franklinit.com.br.domain.Anime;
import franklinit.com.br.requests.AnimePostRequestBody;
import franklinit.com.br.requests.AnimePutRequestBody;
import franklinit.com.br.service.AnimeServiceImpl;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;


@ExtendWith(SpringExtension.class) //teste Unitário
class AnimeControllerTest {

    @InjectMocks //testar class em si
    private AnimeController animeController;

    @Mock // testar todas as class dentro do controller
    private AnimeServiceImpl animeServiceImplMock;

    @BeforeEach
    void setUp(){
    PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createAnimeValidAnime()));
        BDDMockito.when(animeServiceImplMock.listAllPage(ArgumentMatchers.any()))
                .thenReturn(animePage);

        BDDMockito.when(animeServiceImplMock.listAll())
                .thenReturn(List.of(AnimeCreator.createAnimeValidAnime()));

        BDDMockito.when(animeServiceImplMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(AnimeCreator.createAnimeValidAnime());

        BDDMockito.when(animeServiceImplMock.findByNome(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createAnimeValidAnime()));

        BDDMockito.when(animeServiceImplMock.save(ArgumentMatchers.any(AnimePostRequestBody.class)))
                .thenReturn(AnimeCreator.createAnimeValidAnime());

        BDDMockito.doNothing().when(animeServiceImplMock).replace(ArgumentMatchers.any(AnimePutRequestBody.class));

        BDDMockito.doNothing().when(animeServiceImplMock).delete(ArgumentMatchers.anyLong());

    }

    @Test
    @DisplayName("Return list of anime when succesful")
    void list_ReturnAnimeInPage_whenSuccessful(){
        String expectedName =  AnimeCreator.createAnimeValidAnime().getNome();
        Page<Anime> animePage =  animeController.listAllPage(null).getBody();

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage).isNotEmpty();
        Assertions.assertThat(animePage.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getNome()).isEqualTo(expectedName);

    }

    @Test
    @DisplayName(" ListAll returns list of anime when succesful")
    void listAll_ReturnAnimeInPage_whenSuccessful(){
        String expectedName =  AnimeCreator.createAnimeValidAnime().getNome();
        List<Anime> animePage =  animeController.listAll().getBody();

        Assertions.assertThat(animePage)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animePage.get(0).getNome()).isEqualTo(expectedName);

    }

    @Test
    @DisplayName(" FindById returns anime when succesful")
    void findById_ReturnAnimeInPage_whenSuccessful(){
        Long expectedLongId =  AnimeCreator.createAnimeValidAnime().getId();
        Anime anime =  animeController.findById(expectedLongId).getBody();

        Assertions.assertThat(anime)
                .isNotNull();
        Assertions.assertThat(anime.getId()).isEqualTo(expectedLongId);
    }

    @Test
    @DisplayName(" FindByName returns anime when succesful")
    void findByName_ReturnAnimeInPage_whenSuccessful(){
        String expectedName =  AnimeCreator.createAnimeValidAnime().getNome();
        List<Anime> anime =  animeController.findByNome(expectedName).getBody();

        Assertions.assertThat(anime)
                .isNotNull();
        Assertions.assertThat(anime.get(0).getNome()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName(" save returns anime when succesful")
    void save_ReturnAnimeInPage_whenSuccessful(){

        Anime anime =  animeController.save(AnimePostRequestBodyCreator.createAnimePostRequestBody()).getBody();

        Assertions.assertThat(anime)
                .isNotNull()
                .isEqualTo(AnimeCreator.createAnimeValidAnime());
    }
    @Test
    @DisplayName(" replace returns anime when succesful")
    void replace_ReturnAnimeInPage_whenSuccessful(){
        Assertions.assertThatCode(()->animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
                .doesNotThrowAnyException();
        ResponseEntity<Void> entity = animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody());

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

    @Test
    @DisplayName(" delete returns anime when succesful")
    void delete_RemoveAnimeInPage_whenSuccessful(){
        Assertions.assertThatCode(()->animeController.delete(1))
                .doesNotThrowAnyException();
        ResponseEntity<Void> entity = animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody());

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

}