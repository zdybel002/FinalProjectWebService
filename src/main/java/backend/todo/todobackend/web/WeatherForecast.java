package backend.todo.todobackend.web;

import java.time.LocalDate;

public class WeatherForecast {
    private LocalDate date;
    private double    tempC;
    private String    description;
    private String    icon;        // e.g. "04d"

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

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getTempC() {
        return tempC;
    }
    public void setTempC(double tempC) {
        this.tempC = tempC;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
}