package racingcar.dao;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import racingcar.dto.CarDto;
import racingcar.dto.GameResultDto;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CarDao {
    private final JdbcTemplate jdbcTemplate;

    public CarDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<CarDto> carDtoRowMapper = (resultSet, rowNum) -> {
        CarDto carDto = new CarDto(
                resultSet.getString("name"),
                resultSet.getInt("position")
        );
        return carDto;
    };

    public void insert(GameResultDto gameResultDto, long gameId) {
        String sql = "INSERT INTO car(game_id, name, position) VALUES (?,?,?)";
        List<CarDto> racingCars = gameResultDto.getRacingCars();
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, gameId);
                ps.setString(2, racingCars.get(i).getName());
                ps.setInt(3, racingCars.get(i).getPosition());
            }

            @Override
            public int getBatchSize() {
                return racingCars.size();
            }
        });
    }

    public List<CarDto> selectByGameId(long gameId){
        String sql = "SELECT * FROM car WHERE game_id = ?";
        return jdbcTemplate.query(sql, carDtoRowMapper, gameId);
    }
}
