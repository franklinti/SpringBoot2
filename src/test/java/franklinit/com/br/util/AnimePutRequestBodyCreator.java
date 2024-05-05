package franklinit.com.br.util;

import franklinit.com.br.requests.AnimePostRequestBody;
import franklinit.com.br.requests.AnimePutRequestBody;

public class AnimePutRequestBodyCreator {

    public static AnimePutRequestBody createAnimePutRequestBody(){
        return AnimePutRequestBody.builder()
                .id(AnimeCreator.createAnimeValidUpdateAnime().getId())
                .nome(AnimeCreator.createAnimeValidUpdateAnime().getNome())
                .build();
    }
}
