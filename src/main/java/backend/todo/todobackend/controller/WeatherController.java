package backend.todo.todobackend.controller;

import backend.todo.todobackend.service.WeatherService;
import backend.todo.todobackend.web.WeatherForecast;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * GET /weather?loc={cityName or "lat,lon"}
     */
    @GetMapping("/weather")
    public WeatherForecast weather(@RequestParam("loc") String loc) {
        return weatherService.getForecast(loc);
    }
}
