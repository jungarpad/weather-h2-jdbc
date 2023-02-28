package com.example.consumingrest.responsemodel.openweathermap;

import com.example.consumingrest.responsemodel.Main;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherResponse(
        @JsonProperty("weather") List<Weather> weathers,
        Main main,
        Wind wind,
        @JsonProperty("name") String city,
        @JsonProperty("dt") Timestamp time_stamp
) {
}
