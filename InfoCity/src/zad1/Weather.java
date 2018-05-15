package zad1;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Weather {
	private double lon;
	private double lat;
	private double temp;
	private double temp_max;
	private double temp_min;
	private long pressure;
	private long humidity;
	private double wind_speed;
	private long clouds;
	private long visibility;

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getTemp_max() {
		return temp_max;
	}

	public void setTemp_max(double temp_max) {
		this.temp_max = temp_max - 273.15;
	}

	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp - 273.15;
	}

	public double getTemp_min() {
		return temp_min;
	}

	public void setTemp_min(double temp_min) {
		this.temp_min = temp_min - 273.15;
	}

	public long getPressure() {
		return pressure;
	}

	public void setPressure(long pressure) {
		this.pressure = pressure;
	}

	public long getHumidity() {
		return humidity;
	}

	public void setHumidity(long humidity) {
		this.humidity = humidity;
	}

	public double getWind_speed() {
		return wind_speed;
	}

	public void setWind_speed(double wind_speed) {
		this.wind_speed = wind_speed;
	}

	public long getClouds() {
		return clouds;
	}

	public void setClouds(long clouds) {
		this.clouds = clouds;
	}

	public void toStringWeather() {
		System.out.println(getLat() + " " + getLon() + " " + getTemp() + " " + getTemp_max() + " " + getTemp_min() + " "
				+ getClouds() + " " + getHumidity() + " " + getPressure() + " " + getVisibility() + " "
				+ getWind_speed());
	}

	public void getDataFromJson(String jsonData) {
		if (jsonData != null) {
			JSONParser parser = new JSONParser();
			try {
				JSONObject json = (JSONObject) parser.parse(jsonData);
				JSONObject jsonCoord = (JSONObject) json.get("coord");
				JSONObject jsonMain = (JSONObject) json.get("main");
				JSONObject jsonClouds = (JSONObject) json.get("clouds");
				//JSONObject jsonWind = (JSONObject) json.get("wind");

				setVisibility((long) json.get("visibility"));
				setLon((double) jsonCoord.get("lon"));
				setLat((double) jsonCoord.get("lat"));
				setTemp((double) jsonMain.get("temp"));
				setTemp_max((double) jsonMain.get("temp_max"));
				setTemp_min((double) jsonMain.get("temp_min"));
				setHumidity((long) jsonMain.get("humidity"));
				setPressure((long) jsonMain.get("pressure"));
				setClouds((long) jsonClouds.get("all"));
				//setWind_speed((double) jsonWind.get("speed"));
				// setPressure(Double.parseDouble((String)json.get("main")));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	public long getVisibility() {
		return visibility;
	}

	public void setVisibility(long visibility) {
		this.visibility = visibility;
	}

}
