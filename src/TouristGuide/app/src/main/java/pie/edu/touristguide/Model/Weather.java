package pie.edu.touristguide.Model;

/**
 * @author Sebastien El-Hamaoui
 * Weather POJO object.
 */

public class Weather {
    //Define the variables.
    private String weatherLocation;
    private String weatherDate;
    private String weatherWind;
    private String weatherHumidity;
    private String weatherTemperature;
    private String weatherCondition;
    private String weatherCountryCode;

    public Weather() {
        //Empty Constructor
    }

    //Constructor for the current weather call.
    public Weather(String weatherLocation, String weatherDate, String weatherWind, String weatherHumidity, String weatherTemperature, String weatherCondition, String weatherCountryCode) {
        this.weatherLocation = weatherLocation;
        this.weatherDate = weatherDate;
        this.weatherWind = weatherWind;
        this.weatherHumidity = weatherHumidity;
        this.weatherTemperature = weatherTemperature;
        this.weatherCondition = weatherCondition;
        this.weatherCountryCode = weatherCountryCode;
    }

    //Constructor accepting the 3 values for the forecast calls.
    public Weather(String weatherDate, String weatherTemperature, String weatherCondition) {
        this.weatherDate = weatherDate;
        this.weatherTemperature = weatherTemperature;
        this.weatherCondition = weatherCondition;
    }

    //Getters and Setters.
    public String getWeatherDate() {
        return weatherDate;
    }

    public void setWeatherDate(String weatherDate) {
        this.weatherDate = weatherDate;
    }

    public String getWeatherTemperature() {
        return weatherTemperature;
    }

    public void setWeatherTemperature(String weatherTemperature) {
        this.weatherTemperature = weatherTemperature;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public String getWeatherLocation() {
        return weatherLocation;
    }

    public void setWeatherLocation(String weatherLocation) {
        this.weatherLocation = weatherLocation;
    }

    public String getWeatherWind() {
        return weatherWind;
    }

    public void setWeatherWind(String weatherWind) {
        this.weatherWind = weatherWind;
    }

    public String getWeatherHumidity() {
        return weatherHumidity;
    }

    public void setWeatherHumidity(String weatherHumidity) {
        this.weatherHumidity = weatherHumidity;
    }

    public String getWeatherCountryCode() {
        return weatherCountryCode;
    }

    public void setWeatherCountryCode(String weatherCountryCode) {
        this.weatherCountryCode = weatherCountryCode;
    }
}
