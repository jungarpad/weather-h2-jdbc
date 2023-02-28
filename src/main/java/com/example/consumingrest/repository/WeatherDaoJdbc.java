package com.example.consumingrest.repository;

import com.example.consumingrest.repository.mapper.WeatherMapper;
import com.example.consumingrest.responsemodel.SimpleWeatherResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class WeatherDaoJdbc {
    JdbcTemplate jdbcTemplate;

    public WeatherDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean addWeather(SimpleWeatherResponse simpleWeatherResponse) {
        String SQL = "INSERT INTO weather (city, time_stamp, feelsLike, temp, description) VALUES (?, ? , ?, ?, ?);";
        return jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(SQL);
                    ps.setString(1, simpleWeatherResponse.getCity());
                    ps.setTimestamp(2, simpleWeatherResponse.getTimeStamp());
                    ps.setDouble(3, simpleWeatherResponse.getFeelsLike());
                    ps.setDouble(4, simpleWeatherResponse.getTemp());
                    ps.setString(5, simpleWeatherResponse.getDescription());
                    return ps;
                }
        ) == 1;
    }

    public SimpleWeatherResponse getWeather(String city) {
        String SQL = "SELECT city, time_stamp, feelsLike, temp, description FROM weather WHERE city = ?;";
        return jdbcTemplate.query(
                SQL,
                new WeatherMapper(),
                city
        ).stream().findFirst().orElse(null);
    }

    public boolean deleteWeather(String city) {
        String SQL = "DELETE FROM weather WHERE city = ?;";
        return jdbcTemplate.update(SQL, city) == 1;
    }

    public List<String> getCityList() {
        String SQL = "SELECT city FROM weather;";
        return jdbcTemplate.query(
                SQL,
                (rs, rowNum) -> rs.getString("city")
        );
    }

    public boolean deleteAll() {
        String SQL = "DELETE FROM weather;";
        return jdbcTemplate.update(SQL) >= 1;
    }


}
