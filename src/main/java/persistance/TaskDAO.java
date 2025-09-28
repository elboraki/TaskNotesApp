package persistance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.labgeek.DAO.ITaskDAO;
import com.labgeek.models.Task;
import com.labgeek.models.TaskTotalStatus;
import com.labgeek.utils.DatabaseConnection;

public class TaskDAO implements ITaskDAO {
	public Connection getConn() throws SQLException {
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
	public List<Task> findAll(int offset, int limit, String search) throws SQLException {
		String sql = "SELECT id,title,description,status,user_id from tasks";
		String whereSql = "WHERE title like ? OR description like ?";
		String orderSql = "ORDER BY id DESC limit ? offset ?";
		int idx = 1;
		List<Task> taskList = new ArrayList<>();
		if (search != null && !search.isEmpty()) {
			sql = sql + " " + whereSql + " " + orderSql;
			System.out.println("Column full " + sql);

		} else {
			sql = sql + " " + orderSql;

		}
		try {
			PreparedStatement ps = getConn().prepareStatement(sql);
			if (search != null && !search.isEmpty()) {
				ps.setString(1, "%" + search + "%");
				ps.setString(2, "%" + search + "%");
				ps.setInt(3, limit);
				ps.setInt(4, offset);
			} else {
				ps.setInt(1, limit);
				ps.setInt(2, offset);
			}

			ResultSet rs = ps.executeQuery();
			System.out.println("Column ABC");
			while (rs.next()) {
				taskList.add(map(rs));

			}
			return taskList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public Task getTaskById(int id) throws SQLException {
		Task currentTask = new Task();
		String sql = "SELECT id,title,description,status,user_id from tasks where id=?";
		PreparedStatement ps = getConn().prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			currentTask = map(rs);

		}

		System.out.println("Column Task selected " + currentTask.getTitle());

		return currentTask;
	}

	@Override
	public int insert(Task task) throws SQLException {

		String query = "INSERT INTO tasks(title,description,status,user_id) VALUES(?,?,?,?)";
		try {
			PreparedStatement ps = getConn().prepareStatement(query);
			ps.setString(1, task.getTitle());
			ps.setString(2, task.getDescription());
			ps.setString(3, task.getStatus());
			ps.setInt(4, 1);
			int row = ps.executeUpdate();
			return row;
		} catch (Exception e) {
			throw new SQLException(e);

		}

	}

	@Override
	public int update(Task task) throws SQLException {
		String query = "UPDATE tasks SET title=?,description=?,status=? WHERE id=?";
		try {
			PreparedStatement ps = getConn().prepareStatement(query);
			ps.setString(1, task.getTitle());
			ps.setString(2, task.getDescription());
			ps.setString(3, task.getStatus());
			ps.setInt(4, task.getId());
			int row = ps.executeUpdate();
			return row;
		} catch (Exception e) {
			throw new SQLException(e);

		}
	}

	@Override
	public int delete(int id) throws SQLException {
		String query = "DELETE FROM tasks WHERE id=?";
		try {
			PreparedStatement ps = getConn().prepareStatement(query);
			ps.setInt(1, id);
			int row = ps.executeUpdate();
			return row;
		} catch (Exception e) {
			throw new SQLException(e);

		}
	}

	@Override
	public int count(String search) throws SQLException {
		String sql = "SELECT count(*) from tasks";
		if (search != null && !search.isEmpty()) {
			sql += " WHERE title LIKE ? OR description LIKE ?";
		}
		try {
			PreparedStatement ps = getConn().prepareStatement(sql);
			if (search != null && !search.isEmpty()) {
				ps.setString(1, "%" + search + "%");
				ps.setString(2, "%" + search + "%");
			}
			ResultSet rs = ps.executeQuery();
			rs.next();

			return rs.getInt(1);
		} catch (SQLException e) {
			throw new SQLException(e);
		}
	}

	@Override
	public List<TaskTotalStatus> getTotalTasksByStatus(int userId) throws SQLException {
		String query="SELECT count(*) as total,status FROM tasks JOIN users ON tasks.user_id=users.id where tasks.user_id=? GROUP by status";
		PreparedStatement ps=getConn().prepareStatement(query);
		ps.setInt(1, userId);
		ResultSet rs=ps.executeQuery();
		List<TaskTotalStatus> list=new ArrayList<TaskTotalStatus>();
		while(rs.next()) {
			TaskTotalStatus item=new TaskTotalStatus();
			item.setTotal(rs.getInt("total"));
			item.setStatus(rs.getString("status"));
			list.add(item);
		}

		return list;
	}
	
	// 		// SELECT count(*),status FROM `tasks` JOIN users ON tasks.user_id=users.id where tasks.user_id=1 GROUP by status 


}
