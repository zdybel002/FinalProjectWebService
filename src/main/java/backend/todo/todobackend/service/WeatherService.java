package backend.todo.todobackend.service;

import backend.todo.todobackend.web.WeatherForecast;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService {

    private final RestTemplate rest = new RestTemplate();
    private final String apiKey;
    private final String apiUrl;

    public WeatherService(
            @Value("${weather.api.key}") String apiKey,
            @Value("${weather.api.url}") String apiUrl
    ) {
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
    }

    @SuppressWarnings("unchecked")
    public WeatherForecast getForecast(String loc) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(apiUrl)
                .queryParam("units", "metric")
                .queryParam("appid", apiKey);

        if (loc.contains(",")) {
            String[] parts = loc.split(",");
            builder.queryParam("lat",  parts[0].trim())
                    .queryParam("lon",  parts[1].trim());
        } else {
            builder.queryParam("q", loc.trim());
        }

        String uri = builder.build(true).toUriString();

        Map<String,Object> resp = rest.getForObject(uri, Map.class);

        Map<String,Object> main   = (Map<String,Object>) resp.get("main");
        double             temp   = ((Number)main.get("temp")).doubleValue();

        List<Map<String,Object>> weatherList =
                (List<Map<String,Object>>) resp.get("weather");
        Map<String,Object> weather0 = weatherList.get(0);

        String description = (String) weather0.get("description");
        String icon        = (String) weather0.get("icon");

        long dt = ((Number)resp.get("dt")).longValue();
        LocalDate date = Instant.ofEpochSecond(dt)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        return new WeatherForecast(date, temp, description, icon);
    }
}