package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Properties;

public class Connect {

	private Connection con;

	private String dataBaseName;
	private String dataBasePassword;
	private String dataBaseUserName;
	private String URL;
	private String DBURL;
	private String port;

	public Connect(String dataBaseName, String dataBasePassword, String dataBaseUserName, String URL, String port) {

		this.dataBaseName = dataBaseName;
		this.dataBasePassword = dataBasePassword;
		this.dataBaseUserName = dataBaseUserName;
		this.URL = URL;
		this.port = port;
	}

	public Connection makeConnection() throws ClassNotFoundException, SQLException {
		DBURL = "jdbc:mysql://" + URL + ":" + port + "/" + dataBaseName
				+ "?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=ConvertToNull&serverTimezone=GMT";
		Properties p = new Properties();
		p.setProperty("user", dataBaseUserName);
		p.setProperty("password", dataBasePassword);
		p.setProperty("useSSL", "false");
		p.setProperty("autoReconnect", "true");
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(DBURL, p);
		return con;
	}

}
