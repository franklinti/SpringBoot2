package franklinit.com.br.service;

import franklinit.com.br.domain.Anime;
import franklinit.com.br.exception.BadRequestExeception;
import franklinit.com.br.mapper.AnimeMapper;
import franklinit.com.br.repository.AnimeRepository;
import franklinit.com.br.requests.AnimePostRequestBody;
import franklinit.com.br.requests.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeServiceImpl implements AnimeService {

    @Autowired
    private final AnimeRepository animeRepository;

    //    private static List<Anime> animes;
//
//    static {
//        animes = new ArrayList<>(List.of(new Anime(1L,"DBZ"), new Anime(2L,"Saitama")));
//    }
    @Override
    public List<Anime> listAll() {
        return animeRepository.findAll();
    }
    @Override
    public Page<Anime> listAllPage(Pageable pageable) {
        return animeRepository.findAll(pageable);
    }
    @Override
    public List<Anime> findByNome(String nome) {
        return animeRepository.findByNome(nome);
    }

    @Override
    public Anime findByIdOrThrowBadRequestException(long id) {
        return animeRepository.findById(id)
                .orElseThrow(()-> new BadRequestExeception("Anime not found"));
    }

    @Transactional
    @Override
    public Anime save(AnimePostRequestBody anime) {
        //anime.setId(ThreadLocalRandom.current().nextLong(3,100000));

        return animeRepository.save(AnimeMapper.INSTANCE.toAnime(anime));
    }

    @Override
    public void delete(long id) {
        animeRepository.deleteById(findByIdOrThrowBadRequestException(id).getId());
    }

    @Override
    public void replace(AnimePutRequestBody animePutRequestBody) {
       Anime savedAnime = findByIdOrThrowBadRequestException(animePutRequestBody.getId());
       Anime anime = AnimeMapper.INSTANCE.toAnime(animePutRequestBody);
       anime.setId(savedAnime.getId());
//       Anime anime = Anime.builder()
//                .id(savedAnime.getId())
//                .nome(animePutRequestBody.getNome())
//                .build();
       animeRepository.save(anime);
    }
}
