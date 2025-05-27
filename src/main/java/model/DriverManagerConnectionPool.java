package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DriverManagerConnectionPool {
	private static List<Connection> freeDbConnections;
	
	static {
		freeDbConnections = new LinkedList<Connection>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("DataBase driver not found: " + e.getMessage());
		}
	}
	//Metodo privato per creare una nuova connessione
	private static synchronized Connection createDBConnection() throws SQLException {
		Connection newConnection = null;
		
		String ip = "localhost";
		String port = "3306";
		String db = "LaVitaJewerly";
		String username = "root";
		String password = "Lafhu782#";
		

        String url = "jdbc:mysql://" + ip + ":" + port + "/" + db +
                "?useUnicode=true&useJDBCCompliantTimezoneShift=true" +
                "&useLegacyDatetimeCode=false&serverTimezone=UTC";
        
        newConnection = DriverManager.getConnection(url,username,password);
        newConnection.setAutoCommit(false);
        
        return newConnection;
	}
	//Metodo pubblico per ottenere una connessione
	public static synchronized Connection getConnection() throws SQLException {
		Connection connection;
		
		if (!freeDbConnections.isEmpty()) {
			connection = freeDbConnections.remove(0);
			
			try {
				if (connection.isClosed()) {
					connection = getConnection();
				}
			} catch (SQLException e) {
				connection.close();
				connection = getConnection();
			  }
		} else {
			connection = createDBConnection();
		  }
		
		return connection;
	}
	//Metodo pubblico per rilasciare una connessione
	public static synchronized void releaseConnection(Connection connection) throws SQLException {
		if (connection != null)
			freeDbConnections.add(connection);
	}
}
