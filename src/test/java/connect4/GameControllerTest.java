package connect4;

import connect4.model.Game;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class GameControllerTest {
  @Mock
  private GameRepository repository;

  @InjectMocks
  private GameController controller;

  private Game game1;
  private Game game2;

  public GameControllerTest() {
    game1 = new Game();
    game1.setId(1L);
    game1.setNumberOfTurns(2);
    game1.setTimestamp(new Date());
    game1.setWinningPlayer(1);
    game2 = new Game();
    game2.setId(2L);
    game2.setNumberOfTurns(4);
    game2.setTimestamp(new Date());
    game2.setWinningPlayer(0);
  }

  @Test
  public void testGetAllGames() {
    List<Game> games = new ArrayList<>();
    games.add(game1);
    games.add(game2);
    given(repository.findAll()).willReturn(games);

    ResponseEntity<Iterable<Game>> response = controller.getAllGames();
    List<Game> responseGames = Lists.newArrayList(response.getBody());

    verify(repository, times(1)).findAll();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseGames.size()).isEqualTo(2);
  }

  @Test
  public void testGetGameSuccess() {
    given(repository.findOne(1L)).willReturn(game1);

    ResponseEntity<Game> response = controller.getGame(1L);
    Game responseGame = response.getBody();

    verify(repository, times(1)).findOne(1L);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseGame).isNotNull();
    assertThat(responseGame.getId()).isEqualTo(game1.getId());
    assertThat(responseGame.getNumberOfTurns()).isEqualTo(game1.getNumberOfTurns());
    assertThat(responseGame.getTimestamp()).isEqualTo(game1.getTimestamp());
    assertThat(responseGame.getWinningPlayer()).isEqualTo(game1.getWinningPlayer());
  }

  @Test
  public void testGetGameFailure() {
    given(repository.findOne(1L)).willReturn(null);

    ResponseEntity<Game> response = controller.getGame(1L);

    verify(repository, times(1)).findOne(1L);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  public void testAddGame() {
    given(repository.save(game1)).willReturn(game1);

    ResponseEntity<Game> response = controller.addGame(game1);
    Game responseGame = response.getBody();

    verify(repository, times(1)).save(game1);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseGame).isNotNull();
    assertThat(responseGame.getId()).isEqualTo(game1.getId());
    assertThat(responseGame.getNumberOfTurns()).isEqualTo(game1.getNumberOfTurns());
    assertThat(responseGame.getTimestamp()).isEqualTo(game1.getTimestamp());
    assertThat(responseGame.getWinningPlayer()).isEqualTo(game1.getWinningPlayer());
  }

  @Test
  public void testDeleteGameSuccess() {
    given(repository.exists(1L)).willReturn(true);

    ResponseEntity<Game> response = controller.deleteGame(1L);
    Game responseGame = response.getBody();

    verify(repository, times(1)).exists(1L);
    verify(repository, times(1)).delete(1L);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  @Test
  public void testDeleteGameFailure() {
    given(repository.exists(1L)).willReturn(false);

    ResponseEntity<Game> response = controller.deleteGame(1L);
    Game responseGame = response.getBody();

    verify(repository, times(1)).exists(1L);
    verify(repository, times(0)).delete(1L);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }
}
