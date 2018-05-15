package zad1;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Rate {
	private String baseRate;
	private String rateOfCountry;
	private String fullnameOfCountry;
	private String countrycurrencyCode;

	public String getRateOfCountry() {
		return rateOfCountry;
	}

	public void setRateOfCountry(String rateOfCountry) {
		this.rateOfCountry = rateOfCountry;
	}

	public String getBaseRate() {
		return baseRate;
	}

	public void setBaseRate(String baseRate) {
		this.baseRate = baseRate;
	}

	public void getCountryAndCurrancy(String jsonData) {
		if (jsonData != null) {
			JSONParser parser = new JSONParser();
			try {
				JSONArray jsonarr = (JSONArray) parser.parse(jsonData);
				JSONObject jsonarr1 = (JSONObject) jsonarr.get(0);
				JSONArray jsonarr2 = (JSONArray) jsonarr1.get("currencies");
				JSONObject jsonarr3 = (JSONObject) jsonarr2.get(0);
				setCountrycurrencyCode(jsonarr3.get("code").toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

	}

	public void getDataFromJson(String jsonData) {

		if (jsonData != null) {
			JSONParser parser = new JSONParser();
			try {
				JSONObject json = (JSONObject) parser.parse(jsonData);
				JSONObject jsonRates = (JSONObject) json.get("rates");

				setBaseRate(json.get("base").toString());
				if ((json.get("base").toString().equals(this.countrycurrencyCode)) == false) {
					setRateOfCountry(jsonRates.get(this.countrycurrencyCode).toString());
				} else {
					setBaseRate(json.get("base").toString());
					setRateOfCountry("1.0");
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	public String getFullnameOfCountry() {
		return fullnameOfCountry;
	}

	public void setFullnameOfCountry(String fullnameOfCountry) {
		this.fullnameOfCountry = fullnameOfCountry;
	}

	public String getCountrycurrencyCode() {
		return countrycurrencyCode;
	}

	public void setCountrycurrencyCode(String countrycurrencyCode) {
		this.countrycurrencyCode = countrycurrencyCode;
	}

}
