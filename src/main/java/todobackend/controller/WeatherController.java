package backend.todo.todobackend.controller;

import backend.todo.todobackend.service.WeatherService;
import backend.todo.todobackend.web.WeatherForecast;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * WeatherController provides weather forecast data based on location.
 *
 * GET /weather?loc= — fetches forecast for a city or coordinates
 *
 * Uses WeatherService to retrieve data from external API.
 */
@RestController
class WeatherController {

    private final WeatherService weatherService;

    /**
     * Constructor with injected WeatherService.
     */
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * Returns weather forecast for a given location.
     * Location can be a city name or "latitude,longitude".
     * → 200 OK with WeatherForecast
     */
    @GetMapping("/weather")
    public WeatherForecast weather(@RequestParam("loc") String loc) {
        return weatherService.getForecast(loc);
    }
}
