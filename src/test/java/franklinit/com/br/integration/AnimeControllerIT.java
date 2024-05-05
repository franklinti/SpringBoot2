package franklinit.com.br.integration;

import franklinit.com.br.domain.Anime;
import franklinit.com.br.repository.AnimeRepository;
import franklinit.com.br.requests.AnimePostRequestBody;
import franklinit.com.br.util.AnimeCreator;
import franklinit.com.br.util.AnimePostRequestBodyCreator;
import franklinit.com.br.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AnimeControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalServerPort
    private int port;

    @Autowired
    private AnimeRepository animeRepository;


    @Test
    @DisplayName("Return list of anime when succesful")
    void list_ReturnAnimeInPage_whenSuccessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = savedAnime.getNome();

        PageableResponse<Anime> animePage = testRestTemplate.exchange("/animes", HttpMethod.GET, null
                , new ParameterizedTypeReference<PageableResponse<Anime>>() {
                }).getBody();

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage).isNotEmpty();
        Assertions.assertThat(animePage.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getNome()).isEqualTo(expectedName);

    }

    @Test
    @DisplayName(" ListAll returns list of anime when succesful")
    void listAll_ReturnAnimeInPage_whenSuccessful(){
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        String expectedName = savedAnime.getNome();

        List<Anime> animes = testRestTemplate.exchange("/animes/all", HttpMethod.GET, null
                , new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animes
                        .get(0)
                        .getNome())
                .isEqualTo(expectedName);

    }

    @Test
    @DisplayName(" FindById returns anime when succesful")
    void findById_ReturnAnimeInPage_whenSuccessful(){
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        Long expectedLongId =  savedAnime.getId();

        Anime anime =  testRestTemplate.getForObject("/animes/{id}",Anime.class,expectedLongId);

        Assertions.assertThat(anime)
                .isNotNull();
        Assertions.assertThat(anime.getId()).isEqualTo(expectedLongId);
    }

    @Test
    @DisplayName(" FindByName returns anime when succesful")
    void findByName_ReturnAnimeInPage_whenSuccessful(){
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        String expectedName =  savedAnime.getNome();
        String url = String.format("/animes/find?nome=%s",expectedName);
        List<Anime> anime =  testRestTemplate.exchange(url,HttpMethod.GET, null,
        new ParameterizedTypeReference<List<Anime>>(){
        }).getBody();

        Assertions.assertThat(anime)
                .isNotNull();
        Assertions.assertThat(anime.get(0).getNome()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName(" FindByName returns sn empty list of anime when successful")
    void findByName_ReturEmptyLisOfAnime_WhenAnimeIsNotFound(){

       // String url = String.format("/animes/find?nome=dbza");
        List<Anime> animes =  testRestTemplate.exchange("/animes/find?nome=dbz",HttpMethod.GET, null,
        new ParameterizedTypeReference<List<Anime>>(){
        }).getBody();

        Assertions.assertThat(animes)
                .isNotNull().isEmpty();
    }

    @Test
    @DisplayName(" save returns anime when succesful")
    void save_ReturnAnimeInPage_whenSuccessful(){

        AnimePostRequestBody animePostRequestBody = AnimePostRequestBodyCreator.createAnimePostRequestBody();

       ResponseEntity<Anime> animeResponseEntity =  testRestTemplate.postForEntity("/animes", animePostRequestBody, Anime.class);

        Assertions.assertThat(animeResponseEntity)
                .isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(animeResponseEntity.getBody())
                .isNotNull();
        Assertions.assertThat(animeResponseEntity.getBody().getId())
                .isNotNull();
    }

    @Test
    @DisplayName(" replace returns anime when succesful")
    void replace_ReturnAnimeInPage_whenSuccessful(){

        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        savedAnime.setNome("new name");

        ResponseEntity<Void> animeResponseEntity =  testRestTemplate.exchange("/animes", HttpMethod.PUT, new HttpEntity<>(savedAnime), Void.class);

        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

    @Test
    @DisplayName(" delete returns anime when succesful")
    void delete_RemoveAnimeInPage_whenSuccessful(){
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        ResponseEntity<Void> animeResponseEntity =  testRestTemplate.exchange("/animes/{id}", HttpMethod.DELETE,null, Void.class, savedAnime.getId());

        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }
}
