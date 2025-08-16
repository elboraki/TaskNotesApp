package com.labgeek.DAO;

import java.sql.SQLException;
import java.util.List;

import com.labgeek.models.Task;

public interface ITaskDAO {

	List<Task> findAll(int offset,int limit) throws SQLException;

	List<Task> findByUser() throws SQLException;

	List<Task> findByStatus() throws SQLException;

	/** @return Generated ID of Task */

	int insert(Task task) throws SQLException;

	/** @return boolean if row was updated Task */

	boolean update(Task task) throws SQLException;

	/** @return boolean if row was removedd Task */

	boolean delete(int id) throws SQLException;

	int count() throws SQLException;

}
