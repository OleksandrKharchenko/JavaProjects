package zad1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class GUInterface extends JFrame implements ActionListener {
	Service service;
	public JButton jbut;
	public JLabel jl;
	public JLabel jl1;
	public JLabel jl2;
	public JLabel jl3;
	public JLabel jl4;
	public JLabel jl5;
	public JLabel jl6;
	public JLabel jl7;
	public JLabel jl8;
	public JLabel jl9;
	public JLabel jl10;
	public JLabel jlCurr;
	public JLabel jlNBP;
	public String[] arrCity = { "Warsaw", "London", "Odesa", "Berlin", "Hong Kong", "Milan", "Liverpool", "Antananarivo", "New York", "Kiev", "Rome", "Dublin", "Madrid", "Krakow",
			"Prague", "Venice", "Stockholm" };
	public String[] arrCountry = { "Poland", "Bulgaria", "Portugal", "Brasil", "China", "France", "Spain", "Italy", "Germany", "Russia", "Ireland", "Switzerland", "USA",
			"Sweden" };
	public String[] arrCurr = { "USD", "AUD", "BGN", "BRL", "CAD", "CHF", "CNY", "CZK", "DKK", "EUR", "GBP", "HKD",
			"HRK", "HUF", "IDR", "ILS", "INR", "ISK", "JPY", "KRW", "MXN", "MYR", "NOK", "NZD", "PHP", "PLN", "RON",
			"RUB", "SEK", "SGD", "THB", "TRY", "ZAR" };
	public JComboBox<String> cityBox = new JComboBox<>(arrCity);
	public JComboBox<String> countryBox = new JComboBox<>(arrCountry);
	public JComboBox<String> currencyBox = new JComboBox<>(arrCurr);
	public JButton OKButton = new JButton("OK");
	JPanel leftPanel = new JPanel(); // weather and other
	JPanel funcPanel = new JPanel(); // func buttons
	JPanel rightPanel = new JPanel(); // browser
	JPanel textPanel = new JPanel(); // weather
	JPanel ratePanel = new JPanel(); // rate
	JPanel plnratePanel = new JPanel(); // pln rate
	private WebEngine webEngine = null;

	public GUInterface(Service s) {
		super("TPO2");
		this.service = s;
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		this.setVisible(true);
		this.setSize(1024, 600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container mainPanel = this.getContentPane();
		mainPanel.setLayout(new GridLayout(1, 0, 0, 0));
		mainPanel.setBackground(Color.BLACK);

		// funcPanel.setLayout(new GridLayout(4, 10, 50, 0));
		mainPanel.add(leftPanel);
		mainPanel.add(rightPanel);

		funcPanel.setBackground(Color.BLUE);
		funcPanel.add(countryBox);
		funcPanel.add(cityBox);
		funcPanel.add(OKButton);
		ratePanel.add(currencyBox);
		textPanel.setBackground(Color.WHITE);
		// mainPanel.add(textPanel);

		ratePanel.setBackground(Color.GREEN);
		plnratePanel.setBackground(Color.LIGHT_GRAY);

		leftPanel.setLayout(new GridLayout(4, 0, 0, 0));
		leftPanel.add(funcPanel);
		leftPanel.add(textPanel);
		leftPanel.add(ratePanel);
		leftPanel.add(plnratePanel);

		/////////////////////////////////////////

		JFXPanel fxPanel = new JFXPanel();
		rightPanel.add(fxPanel, BorderLayout.CENTER);
		Platform.runLater(() -> {
			StackPane root = new StackPane();
			Scene scene = new Scene(root, rightPanel.getWidth(), rightPanel.getHeight());
			WebView webView = new WebView();
			webEngine = webView.getEngine();
			webEngine.load("https://wikipedia.org/wiki/" + service.getMiasto());
			root.getChildren().add(webView);
			fxPanel.setScene(scene);
		});

		/////////////////////////////////////
		jlCurr = new JLabel("Exchange:   " + "1.0 " + service.getKod_waluty() + " = " + service.rate.getRateOfCountry() + " "
				+ service.rate.getCountrycurrencyCode());
		ratePanel.add(jlCurr);
		jlNBP = new JLabel("Kurs NBP:   " + "1.0 " + service.rate.getCountrycurrencyCode() + " = " + service.getNBPRate() + " PLN");
		plnratePanel.add(jlNBP);
		jl = new JLabel("Miasto: " + service.getMiasto());
		textPanel.add(jl);
		jl1 = new JLabel("Longitude: " + String.valueOf(service.weather.getLon()) + "\u00B0");
		textPanel.add(jl1);
		jl2 = new JLabel("Latitude: " + String.valueOf(service.weather.getLat()) + "\u00B0");
		textPanel.add(jl2);
		jl3 = new JLabel(
				"Temperatura: " + String.valueOf(String.format("%.1f", service.weather.getTemp()) + "\u00B0C"));
		textPanel.add(jl3);
		jl4 = new JLabel(
				"Min.temp: " + String.valueOf(String.format("%.1f", service.weather.getTemp_min()) + "\u00B0C"));
		textPanel.add(jl4);
		jl5 = new JLabel(
				"Max.temp: " + String.valueOf(String.format("%.1f", service.weather.getTemp_max()) + "\u00B0C"));
		textPanel.add(jl5);
		jl6 = new JLabel("Ciśnienie: " + String.valueOf(service.weather.getPressure()) + " hPa");
		textPanel.add(jl6);
		jl7 = new JLabel("Humidity: " + String.valueOf(service.weather.getHumidity()) + " %");
		textPanel.add(jl7);
		jl8 = new JLabel("Zachmurzenie: " + String.valueOf(service.weather.getClouds()) + " %");
		textPanel.add(jl8);
		// jl9 = new JLabel("Prędkość wiatru: " +
		// String.valueOf(service.weather.getWind_speed()) + " m/s");
		// textPanel.add(jl9);
		jl10 = new JLabel("Widoczność: " + String.valueOf(service.weather.getVisibility()) + "");
		textPanel.add(jl10);

		OKButton.addActionListener(this);

	}

	public void refreshBrowser() {
		String url = "https://en.wikipedia.org/wiki/" + service.getMiasto();
		Platform.runLater(() -> {
			webEngine.load(url);
		});
	}

	public void refreshCurrency() {
		this.jlCurr.setText("Exchange:   " + "1.0 " + service.getKod_waluty() + " = " + service.rate.getRateOfCountry() + " "
				+ service.rate.getCountrycurrencyCode());
	}

	public void refreshPLN() {
		this.jlNBP.setText("Kurs NBP:   " + "1.0 " + service.rate.getCountrycurrencyCode() + " = " + service.getNBPRate() + " PLN");
	}

	public void refreshWeatherData() {
		this.jl.setText("Miasto: " + service.getMiasto());
		this.jl1.setText("Longitude: " + String.valueOf(service.weather.getLon()) + "\u00B0");
		this.jl2.setText("Latitude: " + String.valueOf(service.weather.getLat()) + "\u00B0");
		this.jl3.setText(
				"Temperatura: " + String.valueOf(String.format("%.1f", service.weather.getTemp()) + "\u00B0C"));
		this.jl4.setText(
				"Min.temp: " + String.valueOf(String.format("%.1f", service.weather.getTemp_min()) + "\u00B0C"));
		this.jl5.setText(
				"Max.temp: " + String.valueOf(String.format("%.1f", service.weather.getTemp_max()) + "\u00B0C"));
		this.jl6.setText("Ciśnienie: " + String.valueOf(service.weather.getPressure()) + " hPa");
		this.jl7.setText("Humidity: " + String.valueOf(service.weather.getHumidity()) + " %");
		this.jl8.setText("Zachmurzenie: " + String.valueOf(service.weather.getClouds()) + " %");
		// this.jl9.setText("Prędkość wiatru: " +
		// String.valueOf(service.weather.getWind_speed()) + " m/s");
		this.jl10.setText("Widoczność: " + String.valueOf(service.weather.getVisibility()) + "");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == OKButton) {
			service.getWeather(cityBox.getSelectedItem().toString());
			service.setMiasto(cityBox.getSelectedItem().toString());
			service.setKraj(countryBox.getSelectedItem().toString());
			service.getRateFor(currencyBox.getSelectedItem().toString());
			service.setKod_waluty(currencyBox.getSelectedItem().toString());
			refreshCurrency();
			refreshWeatherData();
			refreshBrowser();
			refreshPLN();
		}

	}

}
