package franklinit.com.br.service;

import franklinit.com.br.domain.Anime;
import franklinit.com.br.requests.AnimePostRequestBody;
import franklinit.com.br.requests.AnimePutRequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface AnimeService {
  List<Anime> listAll();
  Page<Anime> listAllPage(Pageable pageable);
  List<Anime> findByNome(String nome);

  Anime findByIdOrThrowBadRequestException(long id);

  Anime save(AnimePostRequestBody anime);

  void delete(long id);

  void replace(AnimePutRequestBody anime);
}
