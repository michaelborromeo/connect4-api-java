package connect4;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

  @RequestMapping("/")
  public String index() {
    return "Greetings from Spring Boot...";
  }

}
