package persistance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.labgeek.models.Category
import com.labgeek.DAO.INoteDAO;
import com.labgeek.models.Note;

import com.labgeek.utils.DatabaseConnection;

public class NoteDAO implements INoteDAO{

	
	public Connection getConn() throws SQLException {
		return DatabaseConnection.getInstance().getConnection();
	}
	
	public Note map(ResultSet rs) {
		Note note=new Note();
		
		try {
			note.setId(rs.getInt("id"));
			note.setBody(rs.getString("body"));
			Category category= new Category();  
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return note;
	}
	@Override
	public List<Note> getAll(int offset, int limit, String search) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(Note note) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Note note) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Note getById(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	

}
