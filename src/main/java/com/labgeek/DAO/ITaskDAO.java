package com.labgeek.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.labgeek.models.Task;
import com.labgeek.models.TaskTotalStatus;

public interface ITaskDAO {

	List<Task> findAll(int offset, int limit, String search) throws SQLException;

	Task getTaskById(int id) throws SQLException;

	/** @return Generated ID of Task */

	int insert(Task task) throws SQLException;

	/** @return boolean if row was updated Task */

	int update(Task task) throws SQLException;

	/** @return boolean if row was removedd Task */

	int delete(int id) throws SQLException;

	int count(String Search) throws SQLException;

	List<TaskTotalStatus> getTotalTasksByStatus(int userId) throws SQLException;

}
