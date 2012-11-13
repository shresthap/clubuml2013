package Repository;

 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Domain.User;

public class UserDAO {
	
	/** Add an user into DB */
	public static boolean addUser (User user) {
		ResultSet rs;
		try {
			Connection conn = DbManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
			"INSERT into user(userName, password, email, project_Id, securityQuestion, securityAnswer) VALUES(?,?,?,?,?,?);");
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			pstmt.setInt(4, user.getProjectId());
			pstmt.setString(5, user.getSecurityQuestion());
			pstmt.setString(6, user.getSecurityAnswer());

			// Execute the SQL statement and update database accordingly.
			pstmt.executeUpdate();
			
			// Get userId generated by DB back, and set it in user object
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
			  int newId = rs.getInt(1);
			  user.setUserId(newId);
			}
			rs.close();
			pstmt.close();
		    conn.close();
		} 
		catch (SQLException e) {
			throw new IllegalArgumentException (e.getMessage(), e);
		}
		 

		return true;
	}
	
	/** Get an user from DB by name and password */
	public static User getUser(String username, String password)
	{
		try {
			Connection conn = DbManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
			"SELECT * FROM user where userName = ? and password = ?;");
			pstmt.setString(1, username);
			pstmt.setString(2, password);
		 
			// Execute the SQL statement and store result into the ResultSet
			ResultSet rs = pstmt.executeQuery();
			 
			if (!rs.next()) {
				return null;
			}
			
			User user = new User(rs.getInt("user_Id"), username, password, rs.getString("email"), rs.getString("securityQuestion"), rs.getString("securityAnswer"),rs.getInt("project_Id"));
			System.out.println("User Id"+rs.getInt("user_Id") );
			rs.close();
			pstmt.close();
		    conn.close();
			return user;
		} catch (SQLException e) {
			System.out.println("Using default model.");
		}

		return null;
	}
	
	/** Remove a user from DB */
	public static boolean removeUser(User user) 
	{
		try {
			Connection conn = DbManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
			"DELETE FROM user where user_Id = ?;");
			pstmt.setInt(1, user.getUserId());

			// Execute the SQL statement and update database accordingly.
			pstmt.executeUpdate();
		 
			pstmt.close();
		    conn.close(); 
			return true;
		} catch (SQLException e) {
			throw new IllegalArgumentException (e.getMessage(), e);
		}
	}
	
	/** Update user in DB */
	public static boolean updateUser(User user) 
	{
		try {
			Connection conn = DbManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
			"UPDATE user SET userName=? , password=?, email=?, securityQuestion =?, securityAnswer=? where user_Id = ?;");
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getSecurityQuestion());
			pstmt.setString(5, user.getSecurityAnswer());
			pstmt.setInt(6, user.getUserId());
			// Execute the SQL statement and update database accordingly.
			pstmt.executeUpdate();
		 
			pstmt.close();
		    conn.close(); 
			return true;
		} catch (SQLException e) {
			throw new IllegalArgumentException (e.getMessage(), e);
		}
	}
	 
	 
}
