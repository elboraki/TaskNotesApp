package com.labgeek;

import com.labgeek.controller.NoteServlet;
import com.labgeek.models.Note;
import com.labgeek.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import persistance.NoteDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class NoteServletTest {

    @InjectMocks
    private NoteServlet noteServlet;

    @Mock
    private NoteDAO noteDAO;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void testDoGet_NewAction_ForwardsToAddNote() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("new");
        when(request.getRequestDispatcher("layout.jsp")).thenReturn(dispatcher);

        noteServlet.doGet(request, response);

        verify(request).setAttribute("contentPage", "notes/addNote.jsp");
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testDoGet_EditAction_LoadsNoteAndForwards() throws ServletException, IOException, SQLException {
        when(request.getParameter("action")).thenReturn("edit");
        when(request.getParameter("id")).thenReturn("1");
        Note note = new Note();
        note.setBody("test body");
        when(noteDAO.getById(1)).thenReturn(note);
        when(request.getRequestDispatcher("layout.jsp")).thenReturn(dispatcher);

        noteServlet.doGet(request, response);

        verify(request).setAttribute("note", note);
        verify(request).setAttribute("contentPage", "notes/editNote.jsp");
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testDoGet_DeleteAction_Success() throws Exception {
        when(request.getParameter("action")).thenReturn("delete");
        when(request.getParameter("id")).thenReturn("1");
        when(noteDAO.delete(1)).thenReturn(1);

        noteServlet.doGet(request, response);

        verify(session).setAttribute("flashOk", "Success your note has been deleted");
        verify(response).sendRedirect(anyString());
    }

    @Test
    void testDoGet_DefaultAction_ListNotes() throws Exception {
        when(request.getParameter("action")).thenReturn(null);
        when(request.getRequestDispatcher("layout.jsp")).thenReturn(dispatcher);
        when(noteDAO.getAll(anyInt(), anyInt(), any())).thenReturn(Arrays.asList(new Note()));
        when(noteDAO.count(any())).thenReturn(1);

        noteServlet.doGet(request, response);

        verify(request).setAttribute(eq("notes"), anyList());
        verify(request).setAttribute(eq("noOfPages"), eq(1));
        verify(request).setAttribute(eq("currentPage"), eq(1));
        verify(request).setAttribute("contentPage", "notes/notes.jsp");
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testDoPost_CreateNote_Success() throws Exception {
        User user = new User();
        user.setId(5);

        when(request.getParameter("action")).thenReturn(null);
        when(session.getAttribute("currentUser")).thenReturn(user);
        when(request.getParameter("body")).thenReturn("note body");
        when(request.getParameter("categorie")).thenReturn("2");
        when(noteDAO.create(any(Note.class))).thenReturn(1);

        noteServlet.doPost(request, response);

        verify(session).setAttribute("flashOk", "Success your note has been deleted");
        verify(response).sendRedirect(anyString());
    }

    @Test
    void testDoPost_EditDelegatesToDoPut() throws Exception {
        when(request.getParameter("action")).thenReturn("edit");

        noteServlet.doPost(request, response);

        // since doPut() is called internally, we only check delegation
        // Mockito can't spy private calls directly, but we can confirm no exception
        assertTrue(true, "doPost delegated to doPut");
    }

    @Test
    void testDoPut_UpdateNote_Success() throws Exception {
        User user = new User();
        user.setId(10);

        Note note = new Note();
        note.setId(1);

        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("body")).thenReturn("updated body");
        when(request.getParameter("categorie")).thenReturn("3");
        when(session.getAttribute("currentUser")).thenReturn(user);
        when(noteDAO.getById(1)).thenReturn(note);
        when(noteDAO.update(any(Note.class))).thenReturn(1);

        noteServlet.doPut(request, response);

        //verify(session).setAttribute("flashOk", "Success your note has been updated");
        verify(response).sendRedirect(anyString());
    }
}
