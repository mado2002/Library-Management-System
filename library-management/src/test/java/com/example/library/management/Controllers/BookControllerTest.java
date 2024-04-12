package com.example.library.management.Controllers;

import com.example.library.management.Entities.Book;
import com.example.library.management.Services.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testGetAllBooks() {
        // Mock BookService
        BookService bookService = mock(BookService.class);
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "Book 1", "Author 1",2024,"9780321856715"));
        when(bookService.getAllBooks()).thenReturn(books);
        BookController bookController = new BookController(bookService);
        ResponseEntity<?> responseEntity = bookController.getAllBooks();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(books, responseEntity.getBody());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void addBook() {
        BookService bookService = mock(BookService.class);
        Book book = new Book(1L, "Book 1", "Author 1",2024,"9780321856715");
        when(bookService.addBook(book)).thenReturn(book);
        BookController bookController = new BookController(bookService);
        ResponseEntity<?> responseEntity = bookController.addBook(book);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(book, responseEntity.getBody());
    }
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testAddBookWithInvalidInput() throws Exception {
        // Invalid book with empty title
        String requestBody = "{\"id\":1,\"title\":\"\",\"author\":\"Author 1\",\"publicationYear\":202,\"isbn\":\"9780321856715\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteBook() {
        BookService bookService = mock(BookService.class);
        when(bookService.deleteBook(1L)).thenReturn(true);
        BookController bookController = new BookController(bookService);
        ResponseEntity<?> responseEntity = bookController.deleteBook(1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Book deleted successfully", responseEntity.getBody());
    }
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteBook2() {
        BookService bookService = mock(BookService.class);
        when(bookService.deleteBook(1L)).thenReturn(false);
        BookController bookController = new BookController(bookService);
        ResponseEntity<?> responseEntity = bookController.deleteBook(1L);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getBookById() {
        BookService bookService = mock(BookService.class);
        Book book = new Book(1L, "Book 1", "Author 1",2024,"9780321856715");
        when(bookService.getBookById(1L)).thenReturn(book);
        BookController bookController = new BookController(bookService);
        ResponseEntity<?> responseEntity = bookController.getBookById(1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(book, responseEntity.getBody());
    }
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getBookById2() {
        BookService bookService = mock(BookService.class);
        when(bookService.getBookById(1L)).thenReturn(null);
        BookController bookController = new BookController(bookService);
        ResponseEntity<?> responseEntity = bookController.getBookById(1L);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void updateBook() {
        BookService bookService = mock(BookService.class);
        Book book = new Book(1L, "Book 1", "Author 1",2024,"9780321856715");
        when(bookService.updateBook(1L, book)).thenReturn(book);
        BookController bookController = new BookController(bookService);
        ResponseEntity<?> responseEntity = bookController.updateBook(1L, book);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(book, responseEntity.getBody());
    }
}