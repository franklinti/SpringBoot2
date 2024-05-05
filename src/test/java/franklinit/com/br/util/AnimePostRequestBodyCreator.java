package franklinit.com.br.util;

import franklinit.com.br.domain.Anime;
import franklinit.com.br.requests.AnimePostRequestBody;

public class AnimePostRequestBodyCreator {

    public static AnimePostRequestBody createAnimePostRequestBody(){
        return AnimePostRequestBody.builder().nome(AnimeCreator.createAnimeToBeSaved().getNome()).build();
    }
}
