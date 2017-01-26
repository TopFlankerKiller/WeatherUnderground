package weatherUnderground;

import java.awt.List;
import java.util.ArrayList;
import java.util.Scanner;

import nasdaq100.ParseIndividualNasdaq100;
import nasdaq100.Stock;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import Parser.AbstractParserTask;

public class SearchPage extends AbstractParserTask {
	private String url;
	private static String generalOption;

	public static final ArrayList<Place> placelist = new ArrayList<Place>();
	public static final ArrayList<AnyPlace> anyplacelist = new ArrayList<AnyPlace>();

	static Place currentPlace;
	static AnyPlace currentAnyPlace;

	public SearchPage(String url) {
		this.url = url;

	}

	static WebDriver driver = new FirefoxDriver();

	@Override
	protected String getUrl() {
		// TODO Auto-generated method stub
		return url;
	}

	@Override
	protected void before() {
	}

	@Override
	protected void after() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void parseDocument(Document doc) {

		// TODO Auto-generated method stub

	}

	public static void main(String[] args) throws Exception {

		// Tirana,Albania",
		// "Yerevan,Armenia",
		// "Vienna,Austria",
		// "Baku,Azerbaijan"
		// "Brussels,Belgium",
		// "Sofia,Bulgaria",
		// "Zagreb,Croatia",
		// "Nicosia,Cyprus",
		// "Prague,Czech Republic",
		// "Copenhagen,Denmark",
		// "Tallinn,Estonia",
		// "Helsinki,Finland",
		// "Paris,France",
		// "Berlin,Germany",
		// "Athens,Greece",
		// "Budapest,Hungary",
		// "Rome,Italy","Riga,Latvia",
		// "Vaduz,Liechtenstein",
		// "Vilnius,Lithuania","Luxembourg,Luxembourg","Skopje,Macedonia","Valletta,Malta",
		// "Podgorica,Montenegro","Amsterdam,Netherlands",
		// "Oslo,Norway","Warsaw,Poland",
		// "Dublin,Ireland","Bucharest,Romania",
		// "Bratislava,Slovakia",
		// "Madrid,Spain","Stockholm,Sweden",
		// "London,United Kingdom",

		String[] europeanCountriesAndCapitals = {

		         "Tirana,Albania",
				 "Yerevan,Armenia",
				 "Vienna,Austria",
				 "Baku,Azerbaijan",
				 "Brussels,Belgium",
				 "Sofia,Bulgaria",
				 "Zagreb,Croatia",
				 "Prague,Czech Republic",
				 "Copenhagen,Denmark",
				 "Tallinn,Estonia",
				 "Helsinki,Finland",
				 "Paris,France",
				 "Berlin,Germany",
				 "Athens,Greece",
				 "Budapest,Hungary",
				 "Rome,Italy","Riga,Latvia",
				 "Vaduz,Liechtenstein",
				 "Vilnius,Lithuania","Luxembourg,Luxembourg","Skopje,Macedonia","Valletta,Malta",
				 "Podgorica,Montenegro","Amsterdam,Netherlands",
				 "Oslo,Norway","Warsaw,Poland",
				 "Dublin,Ireland","Bucharest,Romania",
				 "Bratislava,Slovakia",
				 "Madrid,Spain","Stockholm,Sweden",
				 "London,United Kingdom" };
		
		String[] daysOfmonth={ "1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30"};

		// System.out.println("The size of the european countries and capital array is :"
		// + europeanCountriesAndCapitals.length);
		// System.exit(0);
		
		for(int f=0;f<=daysOfmonth.length;f++){

		for (int k = 0; k <= europeanCountriesAndCapitals.length; k++) {

			System.out.println(europeanCountriesAndCapitals[k]);

			System.out
					.println("Please type the location...in the format : City,Country");

			// Scanner console = new Scanner(System.in);
			String name = europeanCountriesAndCapitals[k]; // console.nextLine();
			System.out.println("you type the location : " + name);

			System.out
					.println("if you want to search for the current weather of your city type Y....else if you want to search for another date type N");
			// Scanner console2 = new Scanner(System.in);
			String option = "N"; // console2.nextLine();
			System.out.println("your option was : " + option);

			driver.get("http://www.wunderground.com/history/");

			WebElement locationSearchBox = driver.findElement(By
					.id("histSearch"));
			
			
			
			
			

			if (option.equals("Y")) {
				generalOption = option;

				locationSearchBox.clear();
				locationSearchBox.sendKeys(name);
				locationSearchBox.submit();

				// WebElement
				// getCurrentWeatherHref=driver.findElement(By.id("current_weather"));
				// WebElement
				// myReference=driver.findElement(By.partialLinkText("findweather"));
				// myReference.click();
				WebElement getCurrentWeatherLink = driver.findElement(By
						.partialLinkText("Get Current Weather"));
				getCurrentWeatherLink.click();

				WebElement currentTempElement = driver.findElement(By
						.id("curTemp"));
				String currentTemperature2 = currentTempElement.getText()
						.replace("°C", "");
				Double currentTemperature = Double
						.parseDouble(currentTemperature2);

				// System.out.println(currentTemperature);

				WebElement lastUpdate = driver
						.findElement(By.id("update-time"));
				String lastUpdatedTime = lastUpdate.getText();

				// System.out.println(lastUpdatedTime);
				// System.out.println(currentTemperature);

				WebElement windCompassSpeedElement = driver.findElement(By
						.id("windCompassSpeed"));
				String windCompassSpeed2 = windCompassSpeedElement.getText();
				Double windCompassSpeed = Double.parseDouble(windCompassSpeed2);
				// System.out.println(windCompassSpeed);

				WebElement currentConditionElement = driver.findElement(By
						.id("curCond"));
				String currentCondition = currentConditionElement.getText();
				// System.out.println(currentCondition);

				WebElement todayHighTemp = driver.findElement(By
						.className("high"));
				String todayHighTemperture2 = todayHighTemp.getText();
				Double todayHighTemperture = Double
						.parseDouble(todayHighTemperture2);
				// System.out.println(todayHighTemperture);

				WebElement todayLowTemp = driver.findElement(By
						.className("low"));
				String todayLowTemperture2 = todayLowTemp.getText();
				Double todayLowTemperture = Double
						.parseDouble(todayLowTemperture2);
				// .out.println(todayLowTemperture);

				WebElement sunRiseElement = driver.findElement(By
						.id("cc-sun-rise"));
				String sunRiseTime = sunRiseElement.getText() + "am";
				// System.out.println(sunRiseTime);

				WebElement sunSetElement = driver.findElement(By
						.id("cc-sun-set"));
				String sunSetTime = sunSetElement.getText() + "pm";
				// System.out.println(sunSetTime);

				WebElement placeElement = driver.findElement(By.id("location"))
						.findElement(By.tagName("h1"));
				String location = placeElement.getText().replace(" ", "");
				// System.out.println(location);

				WebElement feelsLikeTempElement = driver.findElement(
						By.id("curFeel")).findElement(By.className("wx-value"));
				String feelsLikeTemp2 = feelsLikeTempElement.getText();
				Double feelsLikeTemp = Double.parseDouble(feelsLikeTemp2);
				// System.out.println(feelsLikeTemp);

				currentPlace = new Place(name);
				currentPlace.setLocation(location);
				currentPlace.setCurrentTemperature(currentTemperature);
				currentPlace.setLastUpdatedTime(lastUpdatedTime);
				currentPlace.setWindCompassSpeed(windCompassSpeed);
				currentPlace.setCurrentCondition(currentCondition);
				currentPlace.setTodayHighTemperture(todayHighTemperture);
				currentPlace.setTodayLowTemperture(todayLowTemperture);
				currentPlace.setSunRiseTime(sunRiseTime);
				currentPlace.setSunSetTime(sunSetTime);
				currentPlace.setFeelsLikeTemp(feelsLikeTemp);

				placelist.add(currentPlace);
				currentPlace.store();
				System.out.println("The place " + currentPlace.toString()
						+ " was stored");

			} else if (option.equals("N")) {

				System.out.println("Please give the History Date...");

				System.out.println("give the month....ex: March");
				// Scanner console3 = new Scanner(System.in);
				String monthOption = "January"; // console.nextLine();
				System.out.println("you type the month : " + monthOption);

				System.out.println("give the day....ex: 20");
				// Scanner console4 = new Scanner(System.in);
			    String dayOption =daysOfmonth[f]; // console.nextLine();
				System.out.println("you type the day : " + dayOption);

				System.out.println("give the year....ex: 2013");
				// Scanner console5 = new Scanner(System.in);
				String yearOption = "2015"; // console.nextLine();
				System.out.println("you type the year : " + yearOption);

				WebElement month = driver.findElement(By.className("month"));
				//month.clear();
				month.sendKeys(monthOption);

				WebElement day = driver.findElement(By.className("day"));
			//	day.clear();
				day.sendKeys(dayOption);

				WebElement year = driver.findElement(By.className("year"));
			//	year.clear();
				year.sendKeys(yearOption);

//				month.sendKeys(monthOption);
//				day.sendKeys(dayOption);
//				year.sendKeys(yearOption);
				locationSearchBox.sendKeys(name);




				
				
				locationSearchBox.submit();
				System.out
						.println("-------------------------------------------");

				String date = dayOption + " " + monthOption + " " + yearOption;
				Double meanTemperature = null;
				Double maxTemperature = null;
				Double minTemperature = null;
				Integer heatingDegreeDays = null;
				Integer coolingDegreeDays = null;
				Integer growingDegreeDays = null;
				Double dewPoint = null;
				Integer averageHumidity = null;
				Integer maximumHumidity = null;
				Integer minimumHumidity = null;
				Double windSpeed = null;
				Double maxwindSpeed = null;
				Double maxGustSpeed = null;
				Double visibility = null;

				Double precipitation = null;
				Double seaLevelPreassure = null;

				// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				WebElement table_element = driver.findElement(By
						.id("historyTable"));

				java.util.List<WebElement> tr_collection = table_element
						.findElements(By.xpath("id('historyTable')/tbody/tr"));

				// System.out.println("NUMBER OF ROWS IN THIS TABLE = "+tr_collection.size());

				String meantemperature2 = null;
				String maxtemperature2 = null;
				String mintemperature2 = null;
				String heatingDegreeDays2 = null;
				String coolingDegreeDays2 = null;
				String growingDegreeDays2 = null;
				String dewPoint2 = null;
				String averageHumidity2 = null;
				String maximumHumidity2 = null;
				String minimumHumidity2 = null;
				String windSpeed2 = null;
				String maxwindSpeed2 = null;
				String maxGustSpeed2 = null;
				String visibility2 = null;

				String precipitation2 = null;
				String seaLevelPreasure2 = null;

				Integer counter1 = 0;
				Integer counter2 = 0;

				for (WebElement trElement : tr_collection) {

					java.util.List<WebElement> td_collection = trElement
							.findElements(By.xpath("td"));

					for (WebElement tdElement : td_collection) {

						if (tdElement.getText().equals("Mean Temperature")) {
							WebElement meanTemperatureElement = td_collection
									.get(1);
							meantemperature2 = meanTemperatureElement.getText()
									.replace("°C", "");

							if (meantemperature2.equals("-")) {
								String tempvalue = "1000.0";
								meanTemperature = Double.parseDouble(tempvalue);
							} else {
								meanTemperature = Double
										.parseDouble(meantemperature2);
								System.out.println(meanTemperature);
							}

						}

						else if (tdElement.getText().equals("Max Temperature")) {
							WebElement maxTemperatureElement = td_collection
									.get(1);
							maxtemperature2 = maxTemperatureElement.getText()
									.replace("°C", "");

							if (maxtemperature2.equals("-")) {
								String tempvalue = "1000.0";
								maxTemperature = Double.parseDouble(tempvalue);
							} else {

								maxTemperature = Double
										.parseDouble(maxtemperature2);
								System.out.println(maxTemperature);
							}

						}

						else if (tdElement.getText().equals("Min Temperature")) {
							WebElement minTemperatureElement = td_collection
									.get(1);
							mintemperature2 = minTemperatureElement.getText()
									.replace("°C", "");

							if (mintemperature2.equals("-")) {
								String tempvalue = "1000.0";
								minTemperature = Double.parseDouble(tempvalue);
							} else {

								minTemperature = Double
										.parseDouble(mintemperature2);
								System.out.println(minTemperature);
							}

						}

						// String heatingDegreeDays2 = null;
						else if (tdElement.getText().equals(
								"Heating Degree Days")) {
							WebElement heatingDegreeDaysElement = td_collection
									.get(1);
							heatingDegreeDays2 = heatingDegreeDaysElement
									.getText();

							heatingDegreeDays = Integer
									.parseInt(heatingDegreeDays2);
							System.out.println(heatingDegreeDays);

						}

						else if (tdElement.getText().equals(
								"Cooling Degree Days")) {
							WebElement coolingDegreeDaysElement = td_collection
									.get(1);
							coolingDegreeDays2 = coolingDegreeDaysElement
									.getText();

							coolingDegreeDays = Integer
									.parseInt(coolingDegreeDays2);
							System.out.println(coolingDegreeDays);

						}

						// else if(coolingDegreeDays2==null){
						// coolingDegreeDays=0;
						// // System.out.println(heatingDegreeDays);
						// }

						else if (tdElement.getText().equals(
								"Growing Degree Days")) {
							WebElement growingDegreeDaysElement = td_collection
									.get(1);
							growingDegreeDays2 = growingDegreeDaysElement
									.getText().replace(" (Base 50)", "");

							growingDegreeDays = Integer
									.parseInt(growingDegreeDays2);

							System.out.println(growingDegreeDays);

						}

						else if (tdElement.getText().equals("Dew Point")) {
							WebElement dewPointElement = td_collection.get(1);
							dewPoint2 = dewPointElement.getText().replace("°C",
									"");
							dewPoint = Double.parseDouble(dewPoint2);
							System.out.println(dewPoint);

						}

						else if (tdElement.getText().equals("Average Humidity")) {
							WebElement averageHumidityElement = td_collection
									.get(1);
							averageHumidity2 = averageHumidityElement.getText();

							averageHumidity = Integer
									.parseInt(averageHumidity2);
							System.out.println(averageHumidity);

						}

						else if (tdElement.getText().equals("Maximum Humidity")) {
							WebElement maximumHumidityElement = td_collection
									.get(1);
							maximumHumidity2 = maximumHumidityElement.getText();

							maximumHumidity = Integer
									.parseInt(maximumHumidity2);
							System.out.println(maximumHumidity);

						}

						else if (tdElement.getText().equals("Minimum Humidity")) {
							WebElement minimumHumidityElement = td_collection
									.get(1);
							minimumHumidity2 = minimumHumidityElement.getText();

							minimumHumidity = Integer
									.parseInt(minimumHumidity2);
							System.out.println(minimumHumidity);

						}

						else if (tdElement.getText().equals("Precipitation")) {
							// System.out.println("i am in the first precipitation element");
							counter1++;

							if (counter1 == 2) {
								// System.out.println("i am in the second precipitation element");

								WebElement precipitationElement = td_collection
										.get(1);
								precipitation2 = precipitationElement.getText()
										.replace("mm", "");

								// precipitation2 is String value
								precipitation = Double
										.parseDouble(precipitation2);
								System.out.println(precipitation);

							}
						}

						else if (tdElement.getText().equals(
								"Sea Level Pressure")) {
//							System.out
//									.println("i am in the first Sea Level Pressure element");
							counter2++;

							if (counter2 == 2) {
								// System.out.println("i am in the second Sea Level Pressure element");

								WebElement seaLevelPressureElement = td_collection
										.get(1);
								seaLevelPreasure2 = seaLevelPressureElement
										.getText().replace("hPa", "");
//								System.out.println("-----------"
//										+ seaLevelPreassure2 + "---------");
								seaLevelPreassure = Double
										.parseDouble(seaLevelPreasure2);
								System.out.println(seaLevelPreassure);

							}
						}

						else if (tdElement.getText().equals("Wind Speed")) {
							WebElement windSpeedElement = td_collection.get(1);
							windSpeed2 = windSpeedElement.getText().replace(
									"km/h ()", "");
							windSpeed = Double.parseDouble(windSpeed2);
							System.out.println(windSpeed);

						}

						else if (tdElement.getText().equals("Max Wind Speed")) {
							WebElement maxWindSpeedElement = td_collection
									.get(1);
							maxwindSpeed2 = maxWindSpeedElement.getText()
									.replace("km/h", "");
							maxwindSpeed = Double.parseDouble(maxwindSpeed2);
							System.out.println(maxwindSpeed);

						}

						else if (tdElement.getText().equals("Max Gust Speed")) {
							WebElement maxGustSpeedElement = td_collection
									.get(1);

							maxGustSpeed2 = maxGustSpeedElement.getText();
							if (maxGustSpeed2.equals("-")) {
								maxGustSpeed2 = "1000.0";
								Double c = Double.parseDouble(maxGustSpeed2);

								maxGustSpeed = c;
								System.out.println(maxGustSpeed);
							} else {
								String current = maxGustSpeed2.replace("km/h",
										"");
								maxGustSpeed = Double.parseDouble(current);
								System.out.println(maxGustSpeed);
							}

						}

						else if (tdElement.getText().equals("Visibility")) {
							WebElement visibilityElement = td_collection.get(1);
							visibility2 = visibilityElement.getText().replace(
									"kilometers", "");
							visibility = Double.parseDouble(visibility2);
							System.out.println(visibility);

						}

					}

				}

				if (heatingDegreeDays == null) {
					heatingDegreeDays = 0;
				}
				if (coolingDegreeDays == null) {
					coolingDegreeDays = 0;
				}
				if (growingDegreeDays == null) {
					growingDegreeDays = 0;
				}

				if (meanTemperature == null) {
					meanTemperature = 0.0;
				}
				if (maxTemperature == null) {
					maxTemperature = 0.0;
				}
				if (minTemperature == null) {
					minTemperature = 0.0;
				}
				if (dewPoint == null) {
					dewPoint = 0.0;
				}
				if (maximumHumidity == null) {
					maximumHumidity = 0;
				}
				if (minimumHumidity == null) {
					minimumHumidity = 0;
				}
				if (windSpeed == null) {
					windSpeed = 0.0;
				}
				if (maxwindSpeed == null) {
					maxwindSpeed = 0.0;
				}
				if (maxGustSpeed == null) {
					maxGustSpeed = 0.0;
				}
				if (visibility == null) {
					visibility = 0.0;
				}
				
				if (precipitation == null) {
					precipitation = 0.0;
				}
				
				if (seaLevelPreassure == null) {
					seaLevelPreassure = 0.0;
				}

				currentAnyPlace = new AnyPlace(name);
				currentAnyPlace.setDate(date);
				currentAnyPlace.setMeanTemperature(meanTemperature);
				currentAnyPlace.setMaxTemperature(maxTemperature);
				currentAnyPlace.setMinTemperature(minTemperature);
				currentAnyPlace.setHeatingDegreeDays(heatingDegreeDays);
				currentAnyPlace.setCoolingDegreeDays(coolingDegreeDays);
				currentAnyPlace.setGrowingDegreeDays(growingDegreeDays);
				currentAnyPlace.setDewPoint(dewPoint);
				currentAnyPlace.setMaximumHumidity(maximumHumidity);
				currentAnyPlace.setMinimumHumidity(minimumHumidity);
				currentAnyPlace.setWindSpeed(windSpeed);
				currentAnyPlace.setMaxwindSpeed(maxwindSpeed);
				currentAnyPlace.setMaxGustSpeed(maxGustSpeed);
				currentAnyPlace.setVisibility(visibility);
				currentAnyPlace.setPrecipitation(precipitation);
				currentAnyPlace.setSeaLevelPreassure(seaLevelPreassure);

				anyplacelist.add(currentAnyPlace);
				currentAnyPlace.store();
				System.out.println("The anyplace " + currentAnyPlace.toString()
						+ " was stored");

			}
		}
	//	}

	}// here the for iteration for the europeans countries and capitals array
		// comes to its end!!

}
}
