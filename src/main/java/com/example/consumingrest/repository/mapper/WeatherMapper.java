package com.example.consumingrest.repository.mapper;

import com.example.consumingrest.responsemodel.SimpleWeatherResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class WeatherMapper implements RowMapper<SimpleWeatherResponse> {

    @Override
    public SimpleWeatherResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new SimpleWeatherResponse(
                rs.getString("city"),
                rs.getTimestamp("time_stamp"),
                rs.getDouble("feelsLike"),
                rs.getDouble("temp"),
                rs.getString("description")
        );
    }
}
