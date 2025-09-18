package persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.labgeek.models.User;
import com.labgeek.utils.DatabaseConnection;

public class UserDAO {
	public Connection getConn() throws SQLException {
		return DatabaseConnection.getInstance().getConnection();
	}

	private User map(ResultSet rs) throws SQLException {
		User user = new User();
		user.setLogin(rs.getString("login"));
		user.setPassword(rs.getString("password"));
		user.setId(rs.getInt("id"));
		user.setDateCreation(rs.getDate("date_creation"));

		return user;

	}

	public User authenticate(String username, String password) throws SQLException {

		try {
			String query = "SELECT * FROM users WHERE login=? and password=?";
			PreparedStatement ps = getConn().prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				return this.map(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
