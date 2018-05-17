/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlazure;

import java.sql.*;
/**
 *
 * @author Alex Macas
 */
public class App {
    // Replace server name, username, and password with your credentials
		public static void main(String[] args) {
			String connectionString =
				"jdbc:sqlserver://sampledatabase.database.windows.net:1433;"
				+ "database=MyDataBase;"
				+ "user=git;"
				+ "password=Prueba12345;"
				+ "encrypt=true;"
				+ "trustServerCertificate=false;"
				+ "hostNameInCertificate=*.database.windows.net;"
				+ "loginTimeout=30;";

			// Declare the JDBC objects.
			Connection connection = null;
			Statement statement = null;
			ResultSet resultSet = null;
			ResultSet resultSet2 = null;
			PreparedStatement prepsInsertProduct = null;
			
			try {
				connection = DriverManager.getConnection(connectionString);

				// Create and execute a SELECT SQL statement.
				String selectSql = "SELECT TOP 10 Title, FirstName, LastName from SalesLT.Customer";
				statement = connection.createStatement();
				resultSet = statement.executeQuery(selectSql);

				// Print results from select statement
				while (resultSet.next()) {
					System.out.println(resultSet.getString(2) + " "
						+ resultSet.getString(3));
					}

				// Create and execute an INSERT SQL prepared statement.
				String insertSql = "INSERT INTO SalesLT.Product (Name, ProductNumber, Color, StandardCost, ListPrice, SellStartDate) VALUES "
					+ "('Bike', 'B1', 'Blue', 50, 120, '2016-01-01');";

				prepsInsertProduct = connection.prepareStatement(
					insertSql,
					Statement.RETURN_GENERATED_KEYS);
				prepsInsertProduct.execute();
				// Retrieve the generated key from the insert.
				resultSet2 = prepsInsertProduct.getGeneratedKeys();
				// Print the ID of the inserted row.
				while (resultSet2.next()) {
					System.out.println("Generated: " + resultSet2.getString(1));
					}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				// Close the connections after the data has been handled.
				if (prepsInsertProduct != null) try { prepsInsertProduct.close(); } catch(Exception e) {}
				if (resultSet != null) try { resultSet.close(); } catch(Exception e) {}
				if (resultSet2 != null) try { resultSet2.close(); } catch(Exception e) {}
				if (statement != null) try { statement.close(); } catch(Exception e) {}
				if (connection != null) try { connection.close(); } catch(Exception e) {}
			}
		}
}
