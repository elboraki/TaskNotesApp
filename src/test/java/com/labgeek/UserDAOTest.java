package com.labgeek;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.labgeek.models.User;

import persistance.UserDAO;

/**
 * Tests for TaskDAO using Mockito (no DB). NOTE: We stub getConn() on a Spy to
 * avoid touching DatabaseConnection singleton.
 */
@ExtendWith(MockitoExtension.class)
class UserDAOTest {

	@Mock
	private Connection connection;
	@Mock
	private PreparedStatement ps;
	@Mock
	private ResultSet rs;

	// Spy the real DAO so production code runs, but we replace getConn()
	@Spy
	private UserDAO dao = new UserDAO();

	@BeforeEach
	void setup() throws Exception {
		// When DAO asks for a connection, give it our mock
		doReturn(connection).when(dao).getConn();
		when(connection.prepareStatement(anyString())).thenReturn(ps);

	}

	@Test
	void authenticateUserandMapsRows() throws Exception {
		when(ps.executeQuery()).thenReturn(rs);
		// Arrange: two rows
		when(rs.next()).thenReturn(true);
		// Row 1
		when(rs.getInt("id")).thenReturn(1); // first call -> 1, second -> 2
		when(rs.getString("login")).thenReturn("user101");
		when(rs.getString("password")).thenReturn("password");
		when(rs.getDate("date_creation")).thenReturn(new Date(0));

		// Act
		User currentUser = dao.authenticate("user101", "password");

		// Assert: results mapped
		assertNotNull(currentUser);
		assertEquals("user101", currentUser.getLogin());

		// SQL contains WHERE when search present
		ArgumentCaptor<String> sqlCap = ArgumentCaptor.forClass(String.class);
		verify(connection).prepareStatement(sqlCap.capture());
		String sql = sqlCap.getValue().toUpperCase();
		assertTrue(sql.contains("WHERE"));

		// Parameter order for search branch
		verify(ps).setString(1, "user101");
		verify(ps).setString(2, "password");

		verify(ps, times(1)).executeQuery();
		verify(rs, times(1)).next(); // T, T, F
	}

}
