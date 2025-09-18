package persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.labgeek.DAO.INoteDAO;
import com.labgeek.models.Category;
import com.labgeek.models.Note;
import com.labgeek.models.User;
import com.labgeek.utils.DatabaseConnection;

public class NoteDAO implements INoteDAO {

	public Connection getConn() throws SQLException {
		return DatabaseConnection.getInstance().getConnection();
	}

	public Note map(ResultSet rs) {
		Note note = null;

		try {
			note = new Note();
			note.setId(rs.getInt("id"));
			note.setBody(rs.getString("body"));

			Category category = new Category();
			category.setName(rs.getString("name"));
			category.setId(rs.getInt("category_id"));

			User user = new User();
			user.setId(rs.getInt("user_id"));
			user.setLogin(rs.getString("login"));

			note.setCategory(category);
			note.setUser(user);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return note;
	}

	@Override
	public List<Note> getAll(int offset, int limit, String search) throws SQLException {

		try {
			String sql = "SELECT n.id,n.body,n.category_id,n.user_id,c.name,u.id,u.login FROM notes AS n "
					+ "JOIN categorie AS c ON n.category_id=c.id " + "JOIN users AS u ON n.user_id=u.id";
			String whereSql = "WHERE body like ?";
			String orderSql = "ORDER BY n.id DESC limit ? offset ?";
			int idx = 1;
			List<Note> noteList = new ArrayList<>();
			String query = "";
			if (search != null && !search.isEmpty()) {
				query = sql + " " + whereSql + " " + orderSql;
				System.out.println("Query Note " + query);

			} else {
				query = sql + " " + orderSql;

			}
			System.out.println("Query Note==============> " + query);
			PreparedStatement ps = getConn().prepareStatement(query);
			if (search != null && !search.isEmpty()) {
				ps.setString(1, "%" + search + "%");
				ps.setInt(2, limit);
				ps.setInt(3, offset);

			} else {
				ps.setInt(1, limit);
				ps.setInt(2, offset);
			}

			ResultSet rs = ps.executeQuery();
			System.out.println("Column ABC");
			while (rs.next()) {
				noteList.add(map(rs));

			}
			return noteList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int create(Note note) throws SQLException {
		
		try {
			String query="INSERT INTO notes(body,user_id,category_id) VALUES(?,?,?)";
			PreparedStatement ps=getConn().prepareStatement(query);
			ps.setString(1, note.getBody());
			ps.setInt(2, note.getUser().getId());
			ps.setInt(3, note.getCategory().getId());
			int row=ps.executeUpdate();
			return row;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
		
	}

	@Override
	public int update(Note note) throws SQLException {

		try {
			String query="UPDATE notes SET body=?,user_id=?,category_id=? WHERE id=?";
			PreparedStatement ps=getConn().prepareStatement(query);
			ps.setString(1, note.getBody());
			ps.setInt(2, note.getUser().getId());
			ps.setInt(3, note.getCategory().getId());
			ps.setInt(4, note.getId());
			int row=ps.executeUpdate();
			return row;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int delete(int id) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Note getById(int id) throws SQLException {
		try {
			String sql = "SELECT n.id,n.body,n.category_id,n.user_id,c.name,u.id,u.login FROM notes AS n "
					+ "JOIN categorie AS c ON n.category_id=c.id " + "JOIN users AS u ON n.user_id=u.id";
			String whereSql = "WHERE n.id=?";
			String query = sql + " "+whereSql;
			System.out.println(query);
			PreparedStatement ps = getConn().prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			Note note = null;
			rs.next();
			note = map(rs);
			System.out.println(note.getBody().toString());
			getConn().close();

			return note;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public int count(String search) throws SQLException {
		String sql = "SELECT count(*) from notes";
		if (search != null && !search.isEmpty()) {
			sql += " WHERE body LIKE ?";
		}
		try {
			PreparedStatement ps = getConn().prepareStatement(sql);
			if (search != null && !search.isEmpty()) {
				ps.setString(1, "%" + search + "%");
			}
			ResultSet rs = ps.executeQuery();
			rs.next();

			return rs.getInt(1);
		} catch (SQLException e) {
			throw new SQLException(e);
		}
	}

}
