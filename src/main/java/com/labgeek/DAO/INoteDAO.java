package com.labgeek.DAO;

import java.sql.SQLException;
import java.util.List;

import com.labgeek.models.Note;
public interface INoteDAO {
	
	// Get all notes
	List<Note> getAll(int offset,int limit,String search) throws SQLException;
	
	// create new note
	
	int create(Note note) throws SQLException;
	// update note
	int update(Note note)throws SQLException;
	// delete note
	int delete(int id) throws SQLException;
	// get by id
	Note getById(int id) throws SQLException;

}
