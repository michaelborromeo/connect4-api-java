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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:clearTestDb.sql")
public class GameControllerIT {

  @Autowired
  private TestRestTemplate template;

  @LocalServerPort
  private int port;

  private URL base;

  private Game game1;
  private Game game2;

  @Before
  public void setup() throws Exception {
    base = new URL("http://localhost:" + port + "/api");

    game1 = new Game();
    game1.setNumberOfTurns(2);
    game1.setTimestamp(new Date());
    game1.setWinningPlayer(1);
    game2 = new Game();
    game2.setNumberOfTurns(4);
    game2.setTimestamp(new Date());
    game2.setWinningPlayer(0);

    // start each test with at least one game in the db
    Game tempGame = template.postForObject(base + "/game", new HttpEntity<>(game1), Game.class);
    // need to save the id for this game since it might not always be the same
    game1.setId(tempGame.getId());
  }

  @Test
  public void testGetAllGames() throws Exception {
    ResponseEntity<Game[]> response = template.getForEntity(base + "/games", Game[].class);
    Game[] responseGames = response.getBody();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseGames).isNotNull();
    assertThat(responseGames.length).isEqualTo(1);
    assertThat(responseGames[0].getId()).isEqualTo(game1.getId());
    assertThat(responseGames[0].getNumberOfTurns()).isEqualTo(game1.getNumberOfTurns());
    assertThat(responseGames[0].getTimestamp()).isEqualTo(game1.getTimestamp());
    assertThat(responseGames[0].getWinningPlayer()).isEqualTo(game1.getWinningPlayer());
  }

  @Test
  public void testGetGame() throws Exception {
    ResponseEntity<Game> response = template.getForEntity(base + "/game/" + game1.getId(), Game.class);
    Game responseGame = response.getBody();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseGame).isNotNull();
    assertThat(responseGame.getId()).isEqualTo(game1.getId());
    assertThat(responseGame.getNumberOfTurns()).isEqualTo(game1.getNumberOfTurns());
    assertThat(responseGame.getTimestamp()).isEqualTo(game1.getTimestamp());
    assertThat(responseGame.getWinningPlayer()).isEqualTo(game1.getWinningPlayer());
  }

  @Test
  public void testAddGame() throws Exception {
    HttpEntity<Game> request = new HttpEntity<>(game2);
    ResponseEntity<Game> response = template.postForEntity(base + "/game", request, Game.class);
    Game responseGame = response.getBody();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseGame).isNotNull();
    assertThat(responseGame.getId()).isEqualTo(2L);
    assertThat(responseGame.getNumberOfTurns()).isEqualTo(game2.getNumberOfTurns());
    assertThat(responseGame.getTimestamp()).isEqualTo(game2.getTimestamp());
    assertThat(responseGame.getWinningPlayer()).isEqualTo(game2.getWinningPlayer());
  }

  @Test
  public void testDeleteGame() throws Exception {
    template.delete(base + "/game/" + game1.getId());

    ResponseEntity<Game> response = template.getForEntity(base + "/game/" + game1.getId(), Game.class);
    Game responseGame = response.getBody();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }
}
