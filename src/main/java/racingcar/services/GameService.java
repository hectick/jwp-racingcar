package racingcar.services;

import org.springframework.stereotype.Service;
import racingcar.dao.RacingGameRepository;
import racingcar.dto.CarDto;
import racingcar.dto.GameInformationDto;
import racingcar.dto.GameResultDto;
import racingcar.model.MoveCount;
import racingcar.model.RacingGame;
import racingcar.model.car.Cars;
import racingcar.model.manager.ThresholdCarMoveManager;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    private final RacingGameRepository racingGameRepository;

    public GameService(RacingGameRepository racingGameRepository) {
        this.racingGameRepository = racingGameRepository;
    }

    public GameResultDto play(GameInformationDto gameInformationDto) {
        RacingGame racingGame = createRacingGame(gameInformationDto);
        racingGame.play();
        GameResultDto gameResultDto = new GameResultDto(racingGame.getWinners(), createCarDto(racingGame));
        racingGameRepository.saveGame(racingGame.getMoveCount(), gameResultDto);
        return gameResultDto;
    }

    private RacingGame createRacingGame(GameInformationDto gameInformationDto){
        Cars cars = Cars.from(gameInformationDto.getNames());
        MoveCount moveCount = MoveCount.from(gameInformationDto.getCount());
        return new RacingGame(new ThresholdCarMoveManager(), cars, moveCount);
    }

    private List<CarDto> createCarDto(RacingGame racingGame) {
        return racingGame.getCars()
                .stream()
                .map(car -> new CarDto(car.getName(), car.getPosition()))
                .collect(Collectors.toUnmodifiableList());
    }

    public List<GameResultDto> queryHistory() {
        return racingGameRepository.selectAllGames();
    }
}
