package com.example.consumingrest.controller;

import com.example.consumingrest.responsemodel.SimpleWeatherResponse;
import com.example.consumingrest.service.WeatherServiceJdbc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {
    final WeatherServiceJdbc weatherService;
    Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    public WeatherController(WeatherServiceJdbc weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/api/weather")
    public SimpleWeatherResponse getWeather() {
        return weatherService.getSimpleWeatherResponse("Budapest");
    }

    @GetMapping("/api/weather/{city}")
    public SimpleWeatherResponse getLocalWeather(@PathVariable("city") String city) {
        logger.info("GET request received for city: " + city);
        return weatherService.getSimpleWeatherResponse(city);
    }

}
