package franklinit.com.br.controller;

import franklinit.com.br.domain.Anime;
import franklinit.com.br.requests.AnimePostRequestBody;
import franklinit.com.br.requests.AnimePutRequestBody;
import franklinit.com.br.service.AnimeService;
import franklinit.com.br.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("animes")
@RequiredArgsConstructor
public class AnimeController {

    @Autowired
    private  final DateUtil dateUtil;

    @Autowired
    private final AnimeService animeService;

    @GetMapping(path = "/all")
    public ResponseEntity<List<Anime>> listAll(){
      //  log.info(dateUtil.formatLocalDateTime(LocalDateTime.now()));
        return ResponseEntity.ok(animeService.listAll());


    }
    @GetMapping
    public ResponseEntity<Page<Anime>> listAllPage(Pageable pageable){
      //  log.info(dateUtil.formatLocalDateTime(LocalDateTime.now()));
        return ResponseEntity.ok(animeService.listAllPage(pageable));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Anime> findById(@PathVariable long id){
       // log.info(dateUtil.formatLocalDateTime(LocalDateTime.now()));
        return ResponseEntity.ok(animeService.findByIdOrThrowBadRequestException(id));
    }
    @GetMapping(path = "/find")
    public ResponseEntity<List<Anime>> findByNome(@RequestParam String nome){
       // log.info(dateUtil.formatLocalDateTime(LocalDateTime.now()));
        return ResponseEntity.ok(animeService.findByNome(nome));
    }
    @PostMapping
    public ResponseEntity<Anime> save(@RequestBody @Valid AnimePostRequestBody anime){
       // log.info(dateUtil.formatLocalDateTime(LocalDateTime.now()));
        return new ResponseEntity<>(animeService.save(anime), HttpStatus.CREATED);
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
       // log.info(dateUtil.formatLocalDateTime(LocalDateTime.now()));
        animeService.delete(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody AnimePutRequestBody anime){
       // log.info(dateUtil.formatLocalDateTime(LocalDateTime.now()));
        animeService.replace(anime);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
