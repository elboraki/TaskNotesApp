package persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.labgeek.DAO.ITaskDAO;
import com.labgeek.models.Task;
import com.labgeek.utils.DatabaseConnection;

public class TaskDAO implements ITaskDAO {
	private Connection getConn() throws SQLException {
		return DatabaseConnection.getInstance().getConnection();
	}

	private Task map(ResultSet rs) throws SQLException {
		Task task = new Task();
		task.setId(rs.getInt("id"));
		task.setTitle(rs.getString("title"));
		task.setDescription(rs.getString("description"));
		task.setStatus(rs.getString("status"));
		task.setUserId(rs.getInt("user_id"));
		return task;

	}

	@Override
	public List<Task> findAll(int offset, int limit) throws SQLException {
		final String sql = "SELECT id,title,description,status,user_id from tasks ORDER BY id DESC limit ? offset ?";
		List<Task> taskList = new ArrayList<Task>();
		try {
			PreparedStatement ps = getConn().prepareStatement(sql);
			ps.setInt(1, limit);
			ps.setInt(2, offset);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				taskList.add(map(rs));
			}
			return taskList;
		} catch (SQLException e) {

		}
		return null;
	}

	@Override
	public List<Task> findByUser() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Task> findByStatus() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(Task task) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean update(Task task) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int count() throws SQLException {
		final String sql = "SELECT count(*) from tasks";
		try {
			PreparedStatement ps = getConn().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
