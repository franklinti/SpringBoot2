package franklinit.com.br.util;

import franklinit.com.br.domain.Anime;

public class AnimeCreator {

    public static Anime createAnimeToBeSaved(){
        return Anime.builder().nome("KKKK").build();
    }
    public static Anime createAnimeValidAnime(){
        return Anime.builder().nome("KKKK").id(1L).build();
    }
    public static Anime createAnimeValidUpdateAnime(){
        return Anime.builder().nome("DBZ").id(1L).build();
    }

}
