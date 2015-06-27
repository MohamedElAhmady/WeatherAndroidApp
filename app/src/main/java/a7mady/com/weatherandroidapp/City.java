package a7mady.com.weatherandroidapp;

/**
 * Created by A7mady on 6/27/2015.
 */
public class City {

    private String cityName;
    private float temperature;


    public City() {

    }

    public City(String cityName, float temperature) {

        this.cityName = cityName;
        this.temperature = temperature;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }
}
