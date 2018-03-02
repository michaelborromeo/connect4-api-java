package connect4;

import connect4.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameController {
  private GameRepository repository;

  @Autowired
  public GameController(GameRepository repository) {
    this.repository = repository;
  }

  @RequestMapping("/")
  public String index() {
    return "Greetings from Spring Boot...";
  }

  @RequestMapping(value = "/api/game/{id}", method = RequestMethod.GET)
  public ResponseEntity<Game> getGame(@PathVariable Long id) {
    Game game = repository.findOne(id);

    if (game != null) {
      return new ResponseEntity<Game>(game, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @RequestMapping(value = "/api/games", method = RequestMethod.GET)
  public ResponseEntity<Iterable<Game>> getAllGames() {
    Iterable<Game> games = repository.findAll();
    return new ResponseEntity<>(games, HttpStatus.OK);
  }

  @RequestMapping(value = "/api/game", method = RequestMethod.POST)
  public ResponseEntity<Game> addGame(@RequestBody Game game) {
    Game savedGame = repository.save(game);
    return new ResponseEntity<>(savedGame, HttpStatus.OK);
  }

  @RequestMapping(value = "/api/game/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Game> deleteGame(@PathVariable Long id) {
    if (repository.exists(id)) {
      repository.delete(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
