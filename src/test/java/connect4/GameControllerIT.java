package connect4;

import connect4.model.Game;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class GameControllerIT {

  @Autowired
  private TestRestTemplate template;

  @LocalServerPort
  private int port;

  private URL base;

  @Before
  public void setup() throws Exception {
    base = new URL("http://localhost:" + port + "/api");
  }

  @Test
  public void testGetGame() throws Exception {
//    Game game = new Game();
//    game.setNumberOfTurns(2);
//    game.setTimestamp(new Date());
//    game.setWinningPlayer(1);
//
//    HttpEntity<Game> request = new HttpEntity<>(game);
//    Game responseGame = template.postForObject(base + "/game", request, Game.class);
//    assertThat(responseGame).isNotNull();
//    assertThat(responseGame.getId()).isEqualTo(1L);
//    assertThat(responseGame.getNumberOfTurns()).isEqualTo(game.getNumberOfTurns());
//    assertThat(responseGame.getTimestamp()).isEqualTo(game.getTimestamp());
//    assertThat(responseGame.getWinningPlayer()).isEqualTo(game.getWinningPlayer());

    // todo
    // write the tests such that it doesn't matter what order they run
    // and such that the database doesn't need to be cleared after each test
  }

  @Test
  public void testAddGame() throws Exception {
    Game game = new Game();
    game.setNumberOfTurns(2);
    game.setTimestamp(new Date());
    game.setWinningPlayer(1);

    HttpEntity<Game> request = new HttpEntity<>(game);
    Game responseGame = template.postForObject(base + "/game", request, Game.class);
    assertThat(responseGame).isNotNull();
    assertThat(responseGame.getId()).isEqualTo(1L);
    assertThat(responseGame.getNumberOfTurns()).isEqualTo(game.getNumberOfTurns());
    assertThat(responseGame.getTimestamp()).isEqualTo(game.getTimestamp());
    assertThat(responseGame.getWinningPlayer()).isEqualTo(game.getWinningPlayer());
  }
}
