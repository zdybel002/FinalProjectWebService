package backend.todo.todobackend.web;

import java.time.LocalDate;

/**
 * DTO representing a weather forecast response.
 *
 * Contains basic information about the weather:
 * date, temperature, description, and icon code.
 */
public class WeatherForecast {

    /** Forecast date */
    private LocalDate date;

    /** Temperature in Celsius */
    private double tempC;

    /** Weather description (e.g., "clear sky") */
    private String description;

    /** Weather icon code for UI display */
    private String icon;

    /** Default constructor */
    public WeatherForecast() { }

    /**
     * Full constructor.
     *
     * @param date        the forecast date
     * @param tempC       the temperature in Celsius
     * @param description the weather description
     * @param icon        the icon code
     */
    public WeatherForecast(LocalDate date,
                           double tempC,
                           String description,
                           String icon) {
        this.date        = date;
        this.tempC       = tempC;
        this.description = description;
        this.icon        = icon;
    }

    public LocalDate getDate() { return date; }

    public double getTempC() { return tempC; }

    public String getDescription() { return description; }

    public String getIcon() { return icon; }

    public void setDate(LocalDate date) { this.date = date; }

    public void setTempC(double tempC) { this.tempC = tempC; }

    public void setDescription(String description) { this.description = description; }

    public void setIcon(String icon) { this.icon = icon; }
}
