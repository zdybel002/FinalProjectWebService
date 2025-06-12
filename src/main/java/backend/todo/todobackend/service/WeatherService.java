package backend.todo.todobackend.service;

import backend.todo.todobackend.web.WeatherForecast;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;

@Service
public class WeatherService {

    private final RestTemplate rest;
    private final String apiKey;
    private final String apiUrl;

    public WeatherService(
            @Value("${weather.api.key}") String apiKey,
            @Value("${weather.api.url}") String apiUrl
    ) {
        this.rest   = new RestTemplate();
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
    }

    /**
     * Fetches current weather for a city name or "lat,lon".
     */
    public WeatherForecast getForecast(String loc) {
        // build URI: ?q={loc}&units=metric&appid={key}
        String uri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam(loc.contains(",") ? "lat" : "q", loc.contains(",") ? loc.split(",")[0] : loc)
                .queryParam(loc.contains(",") ? "lon" : "q", loc.contains(",") ? loc.split(",")[1] : null)
                .queryParam("units", "metric")
                .queryParam("appid", apiKey)
                .build(true)
                .toUriString();

        // call OpenWeatherMap
        @SuppressWarnings("unchecked")
        Map<String,Object> resp = rest.getForObject(uri, Map.class);

        // parse temperature
        Map<String,Object> main = (Map<String,Object>) resp.get("main");
        double temp = ((Number)main.get("temp")).doubleValue();

        // parse description
        @SuppressWarnings("unchecked")
        Map<String,Object> weather0 = ((java.util.List<Map<String,Object>>)resp.get("weather")).get(0);
        String desc = (String) weather0.get("description");

        // parse date (from UNIX timestamp)
        long dt   = ((Number)resp.get("dt")).longValue();
        LocalDate date = Instant.ofEpochSecond(dt)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        return new WeatherForecast(date, temp, desc);
    }
}
