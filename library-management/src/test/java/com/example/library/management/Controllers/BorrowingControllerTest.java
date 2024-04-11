package com.example.library.management.Controllers;

import com.example.library.management.Entities.BorrowingRecord;
import com.example.library.management.Services.BorrowingService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BorrowingControllerTest {

    @Test
    void borrowBook_Success() throws Exception {
        // Mock BorrowingService
        BorrowingService borrowingService = mock(BorrowingService.class);
        BorrowingRecord borrowingRecord = new BorrowingRecord(); // Mocked borrowing record
        when(borrowingService.borrowBook(anyLong(), anyLong())).thenReturn(borrowingRecord);

        // Create BookController and inject the mocked BorrowingService
        BorrowingController borrowingController = new BorrowingController(borrowingService);

        // Call the method under test
        ResponseEntity<?> responseEntity = borrowingController.borrowBook(1L, 2L);

        // Assert the response status and body
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(borrowingRecord, responseEntity.getBody());
    }

    @Test
    void borrowBook_NotFound() throws Exception {
        // Mock BorrowingService
        BorrowingService borrowingService = mock(BorrowingService.class);
        when(borrowingService.borrowBook(anyLong(), anyLong())).thenReturn(null);

        // Create BookController and inject the mocked BorrowingService
        BorrowingController borrowingController = new BorrowingController(borrowingService);

        // Call the method under test
        ResponseEntity<?> responseEntity = borrowingController.borrowBook(1L, 2L);

        // Assert the response status and body
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Book not found or patron not found or book already borrowed by the patron", responseEntity.getBody());
    }

    @Test
    void returnBook_Success() throws Exception {
        // Mock BorrowingService
        BorrowingService borrowingService = mock(BorrowingService.class);
        BorrowingRecord borrowingRecord = new BorrowingRecord(); // Mocked borrowing record
        when(borrowingService.returnBook(anyLong(), anyLong())).thenReturn(borrowingRecord);

        // Create BookController and inject the mocked BorrowingService
        BorrowingController borrowingController = new BorrowingController(borrowingService);

        // Call the method under test
        ResponseEntity<?> responseEntity = borrowingController.returnBook(1L, 2L);

        // Assert the response status and body
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(borrowingRecord, responseEntity.getBody());
    }

    @Test
    void returnBook_NotFound() throws Exception {
        // Mock BorrowingService
        BorrowingService borrowingService = mock(BorrowingService.class);
        when(borrowingService.returnBook(anyLong(), anyLong())).thenReturn(null);

        // Create BookController and inject the mocked BorrowingService
        BorrowingController borrowingController = new BorrowingController(borrowingService);

        // Call the method under test
        ResponseEntity<?> responseEntity = borrowingController.returnBook(1L, 2L);

        // Assert the response status and body
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Book not found or patron not found or book not borrowed by the patron", responseEntity.getBody());
    }
}