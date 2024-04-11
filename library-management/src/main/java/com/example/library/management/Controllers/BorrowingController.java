package com.example.library.management.Controllers;

import com.example.library.management.Entities.BorrowingRecord;
import com.example.library.management.Services.BorrowingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrow")
public class BorrowingController {

    private final BorrowingService borrowingService;

    public BorrowingController(BorrowingService borrowingService){
        this.borrowingService = borrowingService;
    }
    @PostMapping("/{bookId}/patron/{patronId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId){
        BorrowingRecord borrowingRecord = borrowingService.borrowBook(bookId,patronId);
        if(borrowingRecord!=null){
            return ResponseEntity.ok(borrowingRecord);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found or patron not found or book already borrowed by the patron");
    }
    @PutMapping("/{bookId}/patron/{patronId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> returnBook(@PathVariable Long bookId, @PathVariable Long patronId){
        BorrowingRecord borrowingRecord = borrowingService.returnBook(bookId,patronId);
        if(borrowingRecord!=null){
            return ResponseEntity.ok(borrowingRecord);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found or patron not found or book not borrowed by the patron");
    }
}
