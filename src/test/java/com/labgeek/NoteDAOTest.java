package com.labgeek;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.labgeek.models.Category;
import com.labgeek.models.Note;
import com.labgeek.models.User;

import persistance.NoteDAO;

/**
 * Tests for TaskDAO using Mockito (no DB). NOTE: We stub getConn() on a Spy to
 * avoid touching DatabaseConnection singleton.
 */
@ExtendWith(MockitoExtension.class)
class NoteDAOTest {

	@Mock
	private Connection connection;
	@Mock
	private PreparedStatement ps;
	@Mock
	private ResultSet rs;

	// Spy the real DAO so production code runs, but we replace getConn()
	@Spy
	private NoteDAO dao = new NoteDAO();

	@BeforeEach
	void setup() throws Exception {
		// When DAO asks for a connection, give it our mock
		doReturn(connection).when(dao).getConn();
		when(connection.prepareStatement(anyString())).thenReturn(ps);

	}

	@Test
	void test_get_notes_lists() throws SQLException {
		when(ps.executeQuery()).thenReturn(rs);
		when(rs.next()).thenReturn(true, true, false);
		when(rs.getInt("id")).thenReturn(1, 2);
		when(rs.getString("body")).thenReturn("Learning Java");
		when(rs.getString("name")).thenReturn("Software engineering");

		List<Note> noteList = dao.getAll(10, 5, "Push");

		assertEquals(noteList.size(), 2);
		assertEquals(noteList.get(0).getBody(), "Learning Java");
		assertEquals(noteList.get(0).getId(), 1);

		assertEquals(noteList.get(1).getId(), 2);

		ArgumentCaptor<String> sqlCap = ArgumentCaptor.forClass(String.class);
		verify(connection).prepareStatement(sqlCap.capture());
		String sql = sqlCap.getValue().toUpperCase();
		assertTrue(sql.contains("WHERE"), "Expected WHERE when search is provided");
		assertTrue(sql.contains("ORDER BY"), "Expected ORDER BY");

		// Parameter order for search branch
		verify(ps).setString(1, "%Push%");
		verify(ps).setInt(2, 5); // limit
		verify(ps).setInt(3, 10); // offset

		verify(ps, times(1)).executeQuery();
		verify(rs, times(3)).next(); // T, T, F

	}

	@Test
	void test_insert_note_success() throws SQLException {
		when(ps.executeUpdate()).thenReturn(1);

		Note mockedNote = new Note();

		User user = new User();
		user.setId(1);

		Category cat = new Category();
		cat.setId(2);

		mockedNote.setBody("lorem ipsum");
		mockedNote.setId(1);
		mockedNote.setUser(user);
		mockedNote.setCategory(cat);

		int expectedResult = dao.create(mockedNote);

		assertEquals(1, expectedResult);

		ArgumentCaptor<String> sqlCap = ArgumentCaptor.forClass(String.class);
		verify(connection).prepareStatement(sqlCap.capture());
		String sql = sqlCap.getValue().toUpperCase();
		assertTrue(sql.contains("INSERT"), "Expected inserT QUERY");

		// Parameter order for search branch
		verify(ps).setString(1, mockedNote.getBody());
		verify(ps).setInt(2, mockedNote.getUser().getId());
		verify(ps).setInt(3, mockedNote.getCategory().getId());
		verify(ps, times(1)).executeUpdate();
		verifyNoMoreInteractions(ps);

	}

	@Test
	void test_get_note_by_id() throws SQLException {

		when(ps.executeQuery()).thenReturn(rs);
		when(rs.next()).thenReturn(true, true, false);
		when(rs.getInt("id")).thenReturn(1);
		when(rs.getString("body")).thenReturn("Learning Java");

		Note noteExpected = dao.getById(1);

		assertEquals(noteExpected.getId(), 1);
		assertEquals(noteExpected.getBody(), "Learning Java");

		ArgumentCaptor<String> sqlCap = ArgumentCaptor.forClass(String.class);
		verify(connection).prepareStatement(sqlCap.capture());
		String sql = sqlCap.getValue().toUpperCase();
		assertTrue(sql.contains("WHERE"), "Expected WHERE when search is provided");

		// Parameter order for search branch
		verify(ps).setInt(1, 1); // limit

		verify(ps, times(1)).executeQuery();
		verify(rs, times(1)).next();
		verify(dao, times(1)).map(rs);

	}

	@Test
	void test_update_note_success() throws SQLException {
		when(ps.executeUpdate()).thenReturn(1);

		Note mockedNote = new Note();

		User user = new User();
		user.setId(1);

		Category cat = new Category();
		cat.setId(2);

		mockedNote.setBody("lorem ipsum");
		mockedNote.setId(1);
		mockedNote.setUser(user);
		mockedNote.setCategory(cat);

		int expectedResult = dao.update(mockedNote);

		assertEquals(1, expectedResult);

		ArgumentCaptor<String> sqlCap = ArgumentCaptor.forClass(String.class);
		verify(connection).prepareStatement(sqlCap.capture());
		String sql = sqlCap.getValue().toUpperCase();
		assertTrue(sql.contains("UPDATE"), "Expected inserT QUERY");

		// Parameter order for search branch
		verify(ps).setString(1, mockedNote.getBody());
		verify(ps).setInt(2, mockedNote.getCategory().getId());
		verify(ps).setInt(3, mockedNote.getId());
		verify(ps, times(1)).executeUpdate();
		verifyNoMoreInteractions(ps);

	}

	@Test
	void test_delete_note_successfully() throws SQLException {
		when(ps.executeUpdate()).thenReturn(1);

		Note mockedNote = new Note();

		User user = new User();
		user.setId(1);

		Category cat = new Category();
		cat.setId(2);

		mockedNote.setBody("lorem ipsum");
		mockedNote.setId(1);
		mockedNote.setUser(user);
		mockedNote.setCategory(cat);

		int row = dao.delete(mockedNote.getId());

		assertEquals(1, row);

		ArgumentCaptor<String> sqlCap = ArgumentCaptor.forClass(String.class);
		verify(connection).prepareStatement(sqlCap.capture());
		String sql = sqlCap.getValue().toUpperCase();
		assertTrue(sql.contains("DELETE"), "Expected DELETE QUERY");

		verify(ps).setInt(1, mockedNote.getId());
		verify(ps, times(1)).executeUpdate();
		verifyNoMoreInteractions(ps);

	}

}
