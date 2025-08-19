package com.labgeek;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import persistance.TaskDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskDAOCountTest {

    @Mock private Connection connection;
    @Mock private PreparedStatement ps;
    @Mock private ResultSet rs;

    @Spy private TaskDAO dao = new TaskDAO();

    @Test
    void count_withoutSearch_returnsCount() throws Exception {
        // Arrange
        doReturn(connection).when(dao).getConn();
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt(1)).thenReturn(42);

        // Act
        int result = dao.count("");

        // Assert
        assertEquals(42, result);

        ArgumentCaptor<String> sqlCap = ArgumentCaptor.forClass(String.class);
        verify(connection).prepareStatement(sqlCap.capture());
        assertFalse(sqlCap.getValue().contains("WHERE"), "SQL should not contain WHERE when search is empty");
    }

    @Test
    void count_withSearch_returnsCount() throws Exception {
        // Arrange
        doReturn(connection).when(dao).getConn();
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt(1)).thenReturn(5);

        // Act
        int result = dao.count("Bug");

        // Assert
        assertEquals(5, result);

        ArgumentCaptor<String> sqlCap = ArgumentCaptor.forClass(String.class);
        verify(connection).prepareStatement(sqlCap.capture());
        assertTrue(sqlCap.getValue().contains("WHERE"), "SQL should contain WHERE when search is given");

        verify(ps).setString(1, "%Bug%");
        verify(ps).setString(2, "%Bug%");
    }
}
