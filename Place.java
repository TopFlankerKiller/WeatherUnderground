package weatherUnderground;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import util.db.DBDetails;
import util.db.DBPool;
import Joker.Draw;

public class Place {
	
	private Integer id;
	private String name;
	private String location;
	private Double currentTemperature;
	private String lastUpdatedTime;
	private Double windCompassSpeed;
	private String currentCondition;
	private Double todayHighTemperture;
	private Double todayLowTemperture;
	private String sunRiseTime;
	private String sunSetTime;
	private Double feelsLikeTemp;
	
	
	public Place(String name){
		this.name=name;
	}
	
	
	
	public static Place findByID(int placeId) {
		String query = "SELECT `name`, `location`, `currentTemperature`,`lastUpdatedTime`,`windCompassSpeed`,`currentCondition`,`todayHighTemperture`,"
				+ "`todayLowTemperture`,`sunRiseTime`,`sunSetTime`,`feelsLikeTemp` FROM `todayweather` WHERE `id` = ?";

		Connection conn = DBPool.getInstance(DBDetails.weatherunderground).getConnection();
		Place place = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, placeId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				String location = rs.getString("location");
				Double currentTemperature=rs.getDouble("currentTemperature");
				String lastUpdatedTime = rs.getString("lastUpdatedTime");
				Double windCompassSpeed=rs.getDouble("windCompassSpeed");
				String currentCondition = rs.getString("currentCondition");
				Double todayHighTemperture=rs.getDouble("todayHighTemperture");
				Double todayLowTemperture=rs.getDouble("todayLowTemperture");
				String sunRiseTime = rs.getString("sunRiseTime");
				String sunSetTime = rs.getString("sunSetTime");
				Double feelsLikeTemp=rs.getDouble("feelsLikeTemp");


				place=new Place(name);
				place.setId(placeId);
				place.setLocation(location);
				place.setCurrentTemperature(currentTemperature);
				place.setLastUpdatedTime(lastUpdatedTime);
				place.setWindCompassSpeed(windCompassSpeed);
				place.setCurrentCondition(currentCondition);
				place.setTodayHighTemperture(todayHighTemperture);
				place.setTodayLowTemperture(todayLowTemperture);
				place.setSunRiseTime(sunRiseTime);
				place.setSunSetTime(sunSetTime);
				place.setFeelsLikeTemp(feelsLikeTemp);
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Query: " + query);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return place;
	}
	
	
	public void store() {
		if (id == null) {
			// new place
			Connection conn = DBPool.getInstance(DBDetails.weatherunderground)
					.getConnection();
			String query = "INSERT INTO `todayweather` (`name`, `location`, `currentTemperature`,`lastUpdatedTime`,`windCompassSpeed`,"
					+ "`currentCondition`,`todayHighTemperture`,`todayLowTemperture`,`sunRiseTime`,`sunSetTime`,`feelsLikeTemp`) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			try {
				PreparedStatement pstmt = conn.prepareStatement(query,
						Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, name);
				pstmt.setString(2, location);
				pstmt.setDouble(3, currentTemperature);
				pstmt.setString(4, lastUpdatedTime);
				pstmt.setDouble(5, windCompassSpeed);
				pstmt.setString(6, currentCondition);
				pstmt.setDouble(7, todayHighTemperture);
				pstmt.setDouble(8, todayLowTemperture);
				pstmt.setString(9, sunRiseTime);
				pstmt.setString(10, sunSetTime);
				pstmt.setDouble(11, feelsLikeTemp);


				
				pstmt.executeUpdate();
				ResultSet generated = pstmt.getGeneratedKeys();
				if (generated.next()) {
					id = generated.getInt(1);
				}
				if (id <= 0) {
					throw new SQLException("Insert failed for place "
							+ this.toString());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		} else {
			// update
			update();
		}

	}
	
	
	public void update() {
		Place found = findByID(id);
		if (found == null) {
			store();
		} else {
			Connection conn = DBPool.getInstance(DBDetails.weatherunderground)
					.getConnection();
			String query = "UPDATE `todayweather` SET `name` = ?, `location` = ?, `currentTemperature` = ?, `lastUpdatedTime` = ?,"
					+ " `windCompassSpeed` = ?, `currentCondition` = ?, `todayHighTemperture` = ?,`todayLowTemperture` = ?,"
					+ "`sunRiseTime` = ?,`sunSetTime` = ?,`feelsLikeTemp` = ? WHERE `id` = ?";
			try {
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, name);
				pstmt.setString(2, location);
				pstmt.setDouble(3, currentTemperature);
				pstmt.setString(4, lastUpdatedTime);
				pstmt.setDouble(5, windCompassSpeed);
				pstmt.setString(6, currentCondition);
				pstmt.setDouble(7, todayHighTemperture);
				pstmt.setDouble(8, todayLowTemperture);
				pstmt.setString(9, sunRiseTime);
				pstmt.setString(10, sunSetTime);
				pstmt.setDouble(11, feelsLikeTemp);

				pstmt.setInt(12, id); // WHERE clause
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				System.err.println("Query: " + query);
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static ArrayList<Place> selectByQuery(String sqlQuery) {

		String queryStart = "SELECT `name`, `location`, `currentTemperature`,`lastUpdatedTime`,`windCompassSpeed`,`currentCondition`,`todayHighTemperture`,"
				+ "`todayLowTemperture`,`sunRiseTime`,`sunSetTime`,`feelsLikeTemp` FROM `todayweather`";

		String query = null;
		if (StringUtils.containsIgnoreCase(sqlQuery, "WHERE ")) {
			query = queryStart
					+ sqlQuery.substring(
							StringUtils.indexOfIgnoreCase(sqlQuery, "WHERE "),
							sqlQuery.length());
		} else {
			query = queryStart + "WHERE " + sqlQuery;
		}

		ArrayList<Place> found = new ArrayList<Place>();
		Connection conn = DBPool.getInstance(DBDetails.weatherunderground).getConnection();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int id = -1;
				Double	currentTemperature = 0.0, windCompassSpeed = 0.0, todayHighTemperture = 0.0, todayLowTemperture = 0.0, feelsLikeTemp = 0.0;
				String name = null, location = null, lastUpdatedTime = null, currentCondition = null, sunRiseTime = null, sunSetTime = null;
				id = rs.getInt("id");
				name = rs.getString("name");
				 location = rs.getString("location");
				 currentTemperature=rs.getDouble("currentTemperature");
				 lastUpdatedTime = rs.getString("lastUpdatedTime");
				 windCompassSpeed=rs.getDouble("windCompassSpeed");
				 currentCondition = rs.getString("currentCondition");
				 todayHighTemperture=rs.getDouble("todayHighTemperture");
				 todayLowTemperture=rs.getDouble("todayLowTemperture");
				 sunRiseTime = rs.getString("sunRiseTime");
				 sunSetTime = rs.getString("sunSetTime");
				 feelsLikeTemp=rs.getDouble("feelsLikeTemp");

				if (name != null && location != null && lastUpdatedTime != null && currentCondition != null && sunRiseTime != null && sunSetTime != null) {
					Place place = new Place(name);
					if (id != -1) {
						place.setId(id);
					}
					if (currentTemperature != 0.0) {
						place.setCurrentTemperature(currentTemperature);
					}
					if (windCompassSpeed != 0.0) {
						place.setWindCompassSpeed(windCompassSpeed);
					}
					if (todayHighTemperture != 0.0) {
						place.setTodayHighTemperture(todayHighTemperture);
					}
					if (todayLowTemperture != 0.0) {
						place.setTodayLowTemperture(todayLowTemperture);
					}
					if (feelsLikeTemp != 0.0) {
						place.setFeelsLikeTemp(feelsLikeTemp);
					}
					
					found.add(place);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Query:\n" + query);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return found;
	}
	
	

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public Double getCurrentTemperature() {
		return currentTemperature;
	}


	public void setCurrentTemperature(Double currentTemperature) {
		this.currentTemperature = currentTemperature;
	}


	public String getLastUpdatedTime() {
		return lastUpdatedTime;
	}


	public void setLastUpdatedTime(String lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}


	public Double getWindCompassSpeed() {
		return windCompassSpeed;
	}


	public void setWindCompassSpeed(Double windCompassSpeed) {
		this.windCompassSpeed = windCompassSpeed;
	}


	public String getCurrentCondition() {
		return currentCondition;
	}


	public void setCurrentCondition(String currentCondition) {
		this.currentCondition = currentCondition;
	}


	public Double getTodayHighTemperture() {
		return todayHighTemperture;
	}


	public void setTodayHighTemperture(Double todayHighTemperture) {
		this.todayHighTemperture = todayHighTemperture;
	}


	public Double getTodayLowTemperture() {
		return todayLowTemperture;
	}


	public void setTodayLowTemperture(Double todayLowTemperture) {
		this.todayLowTemperture = todayLowTemperture;
	}


	public String getSunRiseTime() {
		return sunRiseTime;
	}


	public void setSunRiseTime(String sunRiseTime) {
		this.sunRiseTime = sunRiseTime;
	}


	public String getSunSetTime() {
		return sunSetTime;
	}


	public void setSunSetTime(String sunSetTime) {
		this.sunSetTime = sunSetTime;
	}


	public Double getFeelsLikeTemp() {
		return feelsLikeTemp;
	}


	public void setFeelsLikeTemp(Double feelsLikeTemp) {
		this.feelsLikeTemp = feelsLikeTemp;
	}


	@Override
	public String toString() {
		return "Place [id=" + id + ", name=" + name + ", location=" + location
				+ ", currentTemperature=" + currentTemperature
				+ ", lastUpdatedTime=" + lastUpdatedTime
				+ ", windCompassSpeed=" + windCompassSpeed
				+ ", currentCondition=" + currentCondition
				+ ", todayHighTemperture=" + todayHighTemperture
				+ ", todayLowTemperture=" + todayLowTemperture
				+ ", sunRiseTime=" + sunRiseTime + ", sunSetTime=" + sunSetTime
				+ ", feelsLikeTemp=" + feelsLikeTemp + "]";
	}
	
	
	
	
	

}
