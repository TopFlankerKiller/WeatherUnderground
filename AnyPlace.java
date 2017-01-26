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

public class AnyPlace {

	private Integer id;
	private String name=null;
	private String date=null;
	private Double meanTemperature=null;
	private Double maxTemperature=null;
	private Double minTemperature=null;
	private Integer heatingDegreeDays=null;
	private Integer coolingDegreeDays=null;
	private Integer growingDegreeDays=null;
	private Double dewPoint=null;
	private Integer maximumHumidity=null;
	private Integer minimumHumidity=null;
	private Double windSpeed=null;
	private Double maxwindSpeed=null;
	private Double maxGustSpeed=null;
	private Double visibility=null;
	private Double precipitation=null;
	private Double seaLevelPreassure=null;


	
	
	public AnyPlace(String name){
		this.name=name;
	}
	
	
	public static AnyPlace findByID(int anyplaceId) {
		String query = "SELECT `name`, `date`, `meanTemperature`,`maxTemperature`,`minTemperature`,`heatingDegreeDays`,`coolingDegreeDays`,"
				+ "`growingDegreeDays`,`dewPoint`,`maximumHumidity`,`minimumHumidity`,"
				+ "`windSpeed`,`maxwindSpeed`,`maxGustSpeed`,`visibility`,`precipitation`,`seaLevelPreassure` FROM `anydayweather` WHERE `id` = ?";

		Connection conn = DBPool.getInstance(DBDetails.weatherunderground).getConnection();
		AnyPlace anyplace = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, anyplaceId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				String date = rs.getString("date");
				Double meanTemperature=rs.getDouble("meanTemperature");
				Double maxTemperature=rs.getDouble("maxTemperature");
				Double minTemperature=rs.getDouble("minTemperature");
				Integer heatingDegreeDays=rs.getInt("heatingDegreeDays");
				Integer coolingDegreeDays=rs.getInt("coolingDegreeDays");
				Integer growingDegreeDays=rs.getInt("growingDegreeDays");
				Double dewPoint=rs.getDouble("dewPoint");
				Integer maximumHumidity=rs.getInt("maximumHumidity");
				Integer minimumHumidity=rs.getInt("minimumHumidity");
				Double windSpeed=rs.getDouble("windSpeed");
				Double maxwindSpeed=rs.getDouble("maxwindSpeed");
				Double maxGustSpeed=rs.getDouble("maxGustSpeed");
				Double visibility=rs.getDouble("visibility");
				
				Double precipitation=rs.getDouble("precipitation");
				Double seaLevelPreassure=rs.getDouble("seaLevelPreassure");
				


				anyplace=new AnyPlace(name);
				anyplace.setId(anyplaceId);
				anyplace.setDate(date);
				anyplace.setMeanTemperature(meanTemperature);
				anyplace.setMaxTemperature(maxTemperature);
				anyplace.setMinTemperature(minTemperature);
				anyplace.setHeatingDegreeDays(heatingDegreeDays);
				anyplace.setCoolingDegreeDays(coolingDegreeDays);
				anyplace.setGrowingDegreeDays(growingDegreeDays);
				anyplace.setDewPoint(dewPoint);
				anyplace.setMaximumHumidity(maximumHumidity);
				anyplace.setMinimumHumidity(minimumHumidity);
				anyplace.setWindSpeed(windSpeed);
				anyplace.setMaxwindSpeed(maxwindSpeed);
				anyplace.setMaxGustSpeed(maxGustSpeed);
				anyplace.setVisibility(visibility);
				anyplace.setPrecipitation(precipitation);
				anyplace.setSeaLevelPreassure(seaLevelPreassure);
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
		return anyplace;
	}
	
	
	public void store() {
		if (id == null) {
			// new anyPlace
			Connection conn = DBPool.getInstance(DBDetails.weatherunderground)
					.getConnection();
			String query = "INSERT INTO `anydayweather` (`name`, `date`, `meanTemperature`,`maxTemperature`,`minTemperature`,"
					+ "`heatingDegreeDays`,`coolingDegreeDays`,`growingDegreeDays`,`dewPoint`,`maximumHumidity`,`minimumHumidity`,"
					+ "`windSpeed`,`maxwindSpeed`,`maxGustSpeed`,`visibility`,`precipitation`,`seaLevelPreassure`) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			try {
				PreparedStatement pstmt = conn.prepareStatement(query,
						Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, name);
				pstmt.setString(2, date);
				pstmt.setDouble(3, meanTemperature);
				pstmt.setDouble(4, maxTemperature);
				pstmt.setDouble(5, minTemperature);
				pstmt.setInt(6, heatingDegreeDays);
				pstmt.setInt(7, coolingDegreeDays);
				pstmt.setInt(8, growingDegreeDays);
				pstmt.setDouble(9, dewPoint);
				pstmt.setInt(10, maximumHumidity);
				pstmt.setInt(11, minimumHumidity);
				pstmt.setDouble(12, windSpeed);
				pstmt.setDouble(13, maxwindSpeed);
				pstmt.setDouble(14, maxGustSpeed);
				pstmt.setDouble(15, visibility);
				
				pstmt.setDouble(16, precipitation);
				pstmt.setDouble(17, seaLevelPreassure);



				
				pstmt.executeUpdate();
				ResultSet generated = pstmt.getGeneratedKeys();
				if (generated.next()) {
					id = generated.getInt(1);
				}
				if (id <= 0) {
					throw new SQLException("Insert failed for anyplace "
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
		AnyPlace found = findByID(id);
		if (found == null) {
			store();
		} else {
			Connection conn = DBPool.getInstance(DBDetails.weatherunderground)
					.getConnection();
			String query = "UPDATE `anydayweather` SET `name` = ?, `date` = ?, `meanTemperature` = ?, `maxTemperature` = ?,"
					+ " `minTemperature` = ?, `heatingDegreeDays` = ?, `coolingDegreeDays` = ?,`growingDegreeDays` = ?,"
					+ "`dewPoint` = ?,`maximumHumidity` = ?,`minimumHumidity` = ?,"
					+ "`windSpeed` = ?,`maxwindSpeed` = ?,`maxGustSpeed` = ?,`visibility` = ?,`precipitation` = ?,`seaLevelPreassure` = ? WHERE `id` = ?";
			try {
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, name);
				pstmt.setString(2, date);
				pstmt.setDouble(3, meanTemperature);
				pstmt.setDouble(4, maxTemperature);
				pstmt.setDouble(5, minTemperature);
				pstmt.setInt(6, heatingDegreeDays);
				pstmt.setInt(7, coolingDegreeDays);
				pstmt.setInt(8, growingDegreeDays);
				pstmt.setDouble(9, dewPoint);
				pstmt.setInt(10, maximumHumidity);
				pstmt.setInt(11, minimumHumidity);
				pstmt.setDouble(12, windSpeed);
				pstmt.setDouble(13, maxwindSpeed);
				pstmt.setDouble(14, maxGustSpeed);
				pstmt.setDouble(15, visibility);
				
				pstmt.setDouble(16, precipitation);
				pstmt.setDouble(17, seaLevelPreassure);

				

				pstmt.setInt(18, id); // WHERE clause
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
	
	
	public static ArrayList<AnyPlace> selectByQuery(String sqlQuery) {

		String queryStart = "SELECT `name`, `date`, `meanTemperature`,`maxTemperature`,`minTemperature`,`heatingDegreeDays`,`coolingDegreeDays`,"
				+ "`growingDegreeDays`,`dewPoint`,`maximumHumidity`,`minimumHumidity`,"
				+ "`windSpeed`,`maxwindSpeed`,`maxGustSpeed`,`visibility`,`precipitation`,`seaLevelPreassure` FROM `anydayweather`";

		String query = null;
		if (StringUtils.containsIgnoreCase(sqlQuery, "WHERE ")) {
			query = queryStart
					+ sqlQuery.substring(
							StringUtils.indexOfIgnoreCase(sqlQuery, "WHERE "),
							sqlQuery.length());
		} else {
			query = queryStart + "WHERE " + sqlQuery;
		}

		ArrayList<AnyPlace> found = new ArrayList<AnyPlace>();
		Connection conn = DBPool.getInstance(DBDetails.weatherunderground).getConnection();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				
				int id = -1,heatingDegreeDays=-1,coolingDegreeDays=-1,growingDegreeDays=-1,maximumHumidity=-1,minimumHumidity=-1;
				
				Double	meanTemperature = 0.0, maxTemperature = 0.0, minTemperature = 0.0, dewPoint = 0.0, maxwindSpeed = 0.0, windSpeed = 0.0, maxGustSpeed = 0.0, visibility = 0.0, precipitation=0.0,seaLevelPreassure=0.0 ;
				
				String name = null, date = null;
				id = rs.getInt("id");
				name = rs.getString("name");
				 date = rs.getString("date");
				 meanTemperature=rs.getDouble("meanTemperature");
				 maxTemperature=rs.getDouble("maxTemperature");
				 minTemperature=rs.getDouble("minTemperature");
				 heatingDegreeDays=rs.getInt("heatingDegreeDays");
				 coolingDegreeDays=rs.getInt("coolingDegreeDays");
				 growingDegreeDays=rs.getInt("growingDegreeDays");
				 dewPoint=rs.getDouble("dewPoint");
				 maximumHumidity=rs.getInt("maximumHumidity");
				 minimumHumidity=rs.getInt("minimumHumidity");
				 windSpeed=rs.getDouble("windSpeed");
				 maxwindSpeed=rs.getDouble("maxwindSpeed");
				 maxGustSpeed=rs.getDouble("maxGustSpeed");
				 visibility=rs.getDouble("visibility");
				 precipitation=rs.getDouble("precipitation");
				 seaLevelPreassure=rs.getDouble("seaLevelPreassure");

				if (name != null && date != null) {
					AnyPlace anyplace = new AnyPlace(name);
					if (id != -1) {
						anyplace.setId(id);
					}
					if (heatingDegreeDays != -1) {
						anyplace.setHeatingDegreeDays(heatingDegreeDays);
					}
					if (coolingDegreeDays != -1) {
						anyplace.setCoolingDegreeDays(coolingDegreeDays);
					}
					if (growingDegreeDays != -1) {
						anyplace.setGrowingDegreeDays(growingDegreeDays);
					}
					if (maximumHumidity != -1) {
						anyplace.setMaximumHumidity(maximumHumidity);
					}
					
					if (minimumHumidity != -1) {
						anyplace.setMinimumHumidity(minimumHumidity);
					}
					
					
					if (meanTemperature != 0.0) {
						anyplace.setMeanTemperature(meanTemperature);
					}
					if (maxTemperature != 0.0) {
						anyplace.setMaxTemperature(maxTemperature);
					}
					if (minTemperature != 0.0) {
						anyplace.setMinTemperature(minTemperature);
					}
					if (dewPoint != 0.0) {
						anyplace.setDewPoint(dewPoint);
					}
					if (maxwindSpeed != 0.0) {
						anyplace.setMaxwindSpeed(maxwindSpeed);
					}
					if (windSpeed != 0.0) {
						anyplace.setWindSpeed(windSpeed);
					}
					if (maxGustSpeed != 0.0) {
						anyplace.setMaxGustSpeed(maxGustSpeed);
					}
					if (visibility != 0.0) {
						anyplace.setVisibility(visibility);
					}
					
					if (visibility != 0.0) {
						anyplace.setPrecipitation(precipitation);
					}
					
					if (visibility != 0.0) {
						anyplace.setSeaLevelPreassure(seaLevelPreassure);
					}
					
					found.add(anyplace);
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


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public Double getMeanTemperature() {
		return meanTemperature;
	}


	public void setMeanTemperature(Double meanTemperature) {
		this.meanTemperature = meanTemperature;
	}


	public Double getMaxTemperature() {
		return maxTemperature;
	}


	public void setMaxTemperature(Double maxTemperature) {
		this.maxTemperature = maxTemperature;
	}


	public Double getMinTemperature() {
		return minTemperature;
	}


	public void setMinTemperature(Double minTemperature) {
		this.minTemperature = minTemperature;
	}


	public Integer getHeatingDegreeDays() {
		return heatingDegreeDays;
	}


	public void setHeatingDegreeDays(Integer heatingDegreeDays) {
		this.heatingDegreeDays = heatingDegreeDays;
	}


	public Integer getCoolingDegreeDays() {
		return coolingDegreeDays;
	}


	public void setCoolingDegreeDays(Integer coolingDegreeDays) {
		this.coolingDegreeDays = coolingDegreeDays;
	}


	public Integer getGrowingDegreeDays() {
		return growingDegreeDays;
	}


	public void setGrowingDegreeDays(Integer growingDegreeDays) {
		this.growingDegreeDays = growingDegreeDays;
	}


	public Double getDewPoint() {
		return dewPoint;
	}


	public void setDewPoint(Double dewPoint) {
		this.dewPoint = dewPoint;
	}


	public Integer getMaximumHumidity() {
		return maximumHumidity;
	}


	public void setMaximumHumidity(Integer maximumHumidity) {
		this.maximumHumidity = maximumHumidity;
	}


	public Integer getMinimumHumidity() {
		return minimumHumidity;
	}


	public void setMinimumHumidity(Integer minimumHumidity) {
		this.minimumHumidity = minimumHumidity;
	}


	public Double getWindSpeed() {
		return windSpeed;
	}


	public void setWindSpeed(Double windSpeed) {
		this.windSpeed = windSpeed;
	}


	public Double getMaxwindSpeed() {
		return maxwindSpeed;
	}


	public void setMaxwindSpeed(Double maxwindSpeed) {
		this.maxwindSpeed = maxwindSpeed;
	}


	public Double getMaxGustSpeed() {
		return maxGustSpeed;
	}


	public void setMaxGustSpeed(Double maxGustSpeed) {
		this.maxGustSpeed = maxGustSpeed;
	}


	public Double getVisibility() {
		return visibility;
	}


	public void setVisibility(Double visibility) {
		this.visibility = visibility;
	}
	
	
	
	


	public Double getPrecipitation() {
		return precipitation;
	}


	public void setPrecipitation(Double precipitation) {
		this.precipitation = precipitation;
	}


	public Double getSeaLevelPreassure() {
		return seaLevelPreassure;
	}


	public void setSeaLevelPreassure(Double seaLevelPreassure) {
		this.seaLevelPreassure = seaLevelPreassure;
	}


	@Override
	public String toString() {
		return "AnyPlace [id=" + id + ", name=" + name + ", date=" + date
				+ ", meanTemperature=" + meanTemperature + ", maxTemperature="
				+ maxTemperature + ", minTemperature=" + minTemperature
				+ ", heatingDegreeDays=" + heatingDegreeDays
				+ ", coolingDegreeDays=" + coolingDegreeDays
				+ ", growingDegreeDays=" + growingDegreeDays + ", dewPoint="
				+ dewPoint + ", maximumHumidity=" + maximumHumidity
				+ ", minimumHumidity=" + minimumHumidity + ", windSpeed="
				+ windSpeed + ", maxwindSpeed=" + maxwindSpeed
				+ ", maxGustSpeed=" + maxGustSpeed + ", visibility="
				+ visibility + ", precipitation=" + precipitation
				+ ", seaLevelPreassure=" + seaLevelPreassure + "]";
	}


	
		
	
	
	
	
	
	
	

}
