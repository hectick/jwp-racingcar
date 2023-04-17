package racingcar.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import racingcar.dto.GameInformationDto;
import racingcar.dto.GameResultDto;
import racingcar.dto.RequestDto;
import racingcar.services.GameService;
import racingcar.util.ValueEditor;

import java.util.List;

@RestController
public class WebController {

    private final GameService gameService;

    public WebController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/plays")
    public GameResultDto play(@RequestBody RequestDto requestDto) {
        List<String> names = ValueEditor.splitByComma(requestDto.getNames());
        String count = requestDto.getCount();
        return gameService.play(new GameInformationDto(names, count));
    }

    @GetMapping("/plays")
    public List<GameResultDto> queryHistory() {
        return gameService.queryHistory();
    }

}
