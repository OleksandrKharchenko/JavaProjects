/**
 *
 *  @author Kharchenko Oleksandr S15638
 *
 */

package zad1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;

import sun.print.resources.serviceui;

public class Service {
	private String kraj;
	private String miasto;
	public Weather weather;
	public Rate rate;
	public String kod_waluty;

	public String getKraj() {
		return kraj;
	}

	public void setKraj(String kraj) {
		this.kraj = kraj;
	}

	public String getKod_waluty() {
		return kod_waluty;
	}

	public void setKod_waluty(String kod_waluty) {
		this.kod_waluty = kod_waluty;
	}

	public String getMiasto() {
		return miasto;
	}

	public void setMiasto(String miasto) {
		this.miasto = miasto;
	}

	public Service(String kraj) {
		this.kraj = kraj;
	}

	public String getJsonStringFromUrl(String url) {
		StringBuilder sb = new StringBuilder();
		try {
			URL myUrl = new URL(url);
			URLConnection myCon;
			myCon = myUrl.openConnection();
			myCon.connect();
			BufferedReader br = new BufferedReader(new InputStreamReader(myCon.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public String getWeather(String miasto) {
		this.miasto = miasto;
		String url = "http://api.openweathermap.org/data/2.5/weather?q=" + this.miasto
				+ "&APPID=ca11cb5d18c1daa995e904be779a1933";
		String textJsonWeather = getJsonStringFromUrl(url);
		this.weather = new Weather();
		weather.getDataFromJson(textJsonWeather);
		return textJsonWeather;
	}

	public double getRateFor(String kod_waluty) {
		this.kod_waluty = kod_waluty;
		String url = "http://api.fixer.io/latest?base=" + this.kod_waluty;
		String url1 = "http://restcountries.eu/rest/v2/name/" + this.kraj;
		String textJsonRate = getJsonStringFromUrl(url);
		String textJsonCurr = getJsonStringFromUrl(url1);
		this.rate = new Rate();
		rate.getCountryAndCurrancy(textJsonCurr);
		rate.getDataFromJson(textJsonRate);
		return Double.parseDouble(rate.getRateOfCountry());
	}

	public double getNBPRate() {
		String xmlNBP_A = getJsonStringFromUrl("http://www.nbp.pl/kursy/xml/a064z180330.xml");
		String xmlNBP_B = getJsonStringFromUrl("http://www.nbp.pl/kursy/xml/b013z180328.xml");
		String code = rate.getCountrycurrencyCode();
		double rateToPLN;
		// source https://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
		DOMParser parser = new DOMParser();
		try {
			parser.parse(new InputSource(new java.io.StringReader(xmlNBP_A)));
			Document doc = parser.getDocument();
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("pozycja");
			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					if (eElement.getElementsByTagName("kod_waluty").item(0).getTextContent().equals(code)) {
						rateToPLN = Double.parseDouble(eElement.getElementsByTagName("kurs_sredni").item(0)
								.getTextContent().replace(',', '.'));
						return rateToPLN;
					}

				}
			}

		} catch (SAXException e) {
			// handle SAXException
		} catch (IOException e) {
			// handle IOException
		}
		////////////////////////////////////////////////////////

		return 1.0;
	}

}
