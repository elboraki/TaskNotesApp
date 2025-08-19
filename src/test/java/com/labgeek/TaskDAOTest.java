package com.labgeek;


import com.labgeek.models.Task;

import persistance.TaskDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Tests for TaskDAO using Mockito (no DB).
 * NOTE: We stub getConn() on a Spy to avoid touching DatabaseConnection singleton.
 */
@ExtendWith(MockitoExtension.class)
class TaskDAOTest {

    @Mock private Connection connection;
    @Mock private PreparedStatement ps;
    @Mock private ResultSet rs;

    // Spy the real DAO so production code runs, but we replace getConn()
    @Spy private TaskDAO dao = new TaskDAO();

    @BeforeEach
    void setup() throws Exception {
        // When DAO asks for a connection, give it our mock
        doReturn(connection).when(dao).getConn();
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
    }

    @Test
    void findAll_withSearch_buildsWhere_setsParams_andMapsRows() throws Exception {
        // Arrange: two rows
        when(rs.next()).thenReturn(true, true, false);
        // Row 1
        when(rs.getInt("id")).thenReturn(1, 2); // first call -> 1, second -> 2
        when(rs.getString("title")).thenReturn("A", "B");
        when(rs.getString("description")).thenReturn("d1", "d2");
        when(rs.getString("status")).thenReturn("OPEN", "DONE");
        when(rs.getInt("user_id")).thenReturn(10, 11);

        // Act
        List<Task> out = dao.findAll(10, 5, "Push");

        // Assert: results mapped
        assertNotNull(out);
        assertEquals(2, out.size());
        assertEquals(1, out.get(0).getId());
        assertEquals(2, out.get(1).getId());

        // SQL contains WHERE when search present
        ArgumentCaptor<String> sqlCap = ArgumentCaptor.forClass(String.class);
        verify(connection).prepareStatement(sqlCap.capture());
        String sql = sqlCap.getValue().toUpperCase();
        assertTrue(sql.contains("WHERE"), "Expected WHERE when search is provided");
        assertTrue(sql.contains("ORDER BY"), "Expected ORDER BY");

        // Parameter order for search branch
        verify(ps).setString(1, "%Push%");
        verify(ps).setString(2, "%Push%");
        verify(ps).setInt(3, 5);   // limit
        verify(ps).setInt(4, 10);  // offset

        verify(ps, times(1)).executeQuery();
        verify(rs, times(3)).next(); // T, T, F
    }

    @Test
    void findAll_withoutSearch_noWhere_setsParams_andMapsRows() throws Exception {
        // Arrange: one row
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt("id")).thenReturn(7);
        when(rs.getString("title")).thenReturn("X");
        when(rs.getString("description")).thenReturn("dx");
        when(rs.getString("status")).thenReturn("OPEN");
        when(rs.getInt("user_id")).thenReturn(99);

        // Act
        List<Task> out = dao.findAll(0, 10,"");

        // Assert
        assertNotNull(out);
        assertEquals(1, out.size());
        assertEquals(7, out.get(0).getId());

        // SQL has no WHERE in empty-search branch
        ArgumentCaptor<String> sqlCap = ArgumentCaptor.forClass(String.class);
        verify(connection).prepareStatement(sqlCap.capture());
        String sql = sqlCap.getValue().toUpperCase();
        assertFalse(sql.contains(" WHERE "), "Should NOT contain WHERE when search empty");
        assertTrue(sql.contains("ORDER BY"), "Expected ORDER BY");

        // Parameter order for no-search branch
        verify(ps).setInt(1, 10); // limit
        verify(ps).setInt(2, 0);  // offset
        verify(ps, times(1)).executeQuery();
    }

    @Test
    void count_withoutSearch_returnsCount() throws Exception {
        // Arrange
        when(rs.next()).thenReturn(true);
        when(rs.getInt(1)).thenReturn(42);

        // Act
        int result = dao.count("");

        // Assert
        assertEquals(42, result);

        ArgumentCaptor<String> sqlCap = ArgumentCaptor.forClass(String.class);
        verify(connection).prepareStatement(sqlCap.capture());
        assertFalse(sqlCap.getValue().toUpperCase().contains("WHERE"));
        verify(ps, never()).setString(anyInt(), anyString());
    }

    @Test
    void count_withSearch_bindsParams_andReturnsCount() throws Exception {
        // Arrange
        when(rs.next()).thenReturn(true);
        when(rs.getInt(1)).thenReturn(5);

        // Act
        int result = dao.count("Bug");

        // Assert
        assertEquals(5, result);

        ArgumentCaptor<String> sqlCap = ArgumentCaptor.forClass(String.class);
        verify(connection).prepareStatement(sqlCap.capture());
        assertTrue(sqlCap.getValue().toUpperCase().contains("WHERE"));

        verify(ps).setString(1, "%Bug%");
        verify(ps).setString(2, "%Bug%");
    }
}
