package racingcar.dto;

public class GameInfoDto {
    private final String names;
    private final String count;

    public GameInfoDto(String names, String count) {
        this.names = names;
        this.count = count;
    }

    public String getNames() {
        return names;
    }

    public String getCount() {
        return count;
    }
}
