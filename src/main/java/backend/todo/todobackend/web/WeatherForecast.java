package backend.todo.todobackend.web;

import java.time.LocalDate;

public class WeatherForecast {
    private LocalDate date;
    private double    tempC;
    private String    description;
    private String    icon;  // ← this field

    public WeatherForecast() { }

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
    public double    getTempC() { return tempC; }
    public String    getDescription() { return description; }
    public String    getIcon() { return icon; }      // ← getter

    public void setDate(LocalDate date) { this.date = date; }
    public void setTempC(double tempC) { this.tempC = tempC; }
    public void setDescription(String description) { this.description = description; }
    public void setIcon(String icon) { this.icon = icon; }  // ← setter
}