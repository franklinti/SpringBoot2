package franklinit.com.br.client;

import franklinit.com.br.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.sound.midi.VoiceStatus;
import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
//        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/{id}", Anime.class, 4);
//        log.info(entity);
//
//        Anime[] objects = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);
//        assert objects != null;
//        log.info(Arrays.toString(objects));
//
//        Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class, 4);
//        log.info(object);
//
//        //@formatter:off
        //ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all", HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
//
//        //@formatter:on
        //log.info(exchange.getBody());

        //Post
//        Anime  k = Anime.builder().nome("Kuririn").build();
//        Anime saveK = new RestTemplate().postForObject("http://localhost:8080/animes", k, Anime.class);
//        log.info("Saved anime {}",saveK);

        //Post
        Anime  f = Anime.builder().nome("Freeza5").build();
        ResponseEntity<Anime> saveK1 = new RestTemplate().exchange("http://localhost:8080/animes",
                HttpMethod.POST,
                new HttpEntity<>(f,createJsonHeader()),
                Anime.class);
        log.info("Saved anime {}",saveK1.getBody());

        Anime animeToUpdate = saveK1.getBody();
        animeToUpdate.setNome("Freeza6");
        ResponseEntity<Void> saveK1Update = new RestTemplate().exchange("http://localhost:8080/animes",
                HttpMethod.PUT,
                new HttpEntity<>(animeToUpdate,createJsonHeader()),
                Void.class);
        log.info("Updated anime {}",saveK1Update.getBody());

        assert animeToUpdate != null;
        ResponseEntity<Void> saveK1DELETE = new RestTemplate().exchange("http://localhost:8080/animes/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                animeToUpdate.getId());
        log.info(saveK1DELETE);
    }

    private static HttpHeaders createJsonHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
