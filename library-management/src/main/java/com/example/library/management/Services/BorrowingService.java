package com.example.library.management.Services;

import com.example.library.management.Entities.Book;
import com.example.library.management.Entities.BorrowingRecord;
import com.example.library.management.Entities.Patron;
import com.example.library.management.Repositories.BookRepository;
import com.example.library.management.Repositories.BorrowingRecordRepository;
import com.example.library.management.Repositories.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class BorrowingService {

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PatronRepository patronRepository;

    @Transactional
    public BorrowingRecord borrowBook(Long bookId,Long patronId){
        Book book=bookRepository.findById(bookId).orElse(null);
        Patron patron=patronRepository.findById(patronId).orElse(null);
        BorrowingRecord existingBorrowingRecord = borrowingRecordRepository.findByBookAndReturnDateIsNull(book);
        //Check if the book is available and the patron is not already borrowing the book
        if(book!=null && patron!=null && existingBorrowingRecord==null){
            BorrowingRecord borrowingRecord = new BorrowingRecord(book,patron,new Date());
            return borrowingRecordRepository.save(borrowingRecord);
        }

        return null;
    }

    @Transactional
    public BorrowingRecord returnBook(Long bookId,Long patronId){
        Book book=bookRepository.findById(bookId).orElse(null);
        Patron patron=patronRepository.findById(patronId).orElse(null);
        if(book!=null && patron!=null){
            return getBorrowingRecord(book, patron);
        }
        return null;
    }

    private BorrowingRecord getBorrowingRecord(Book book, Patron patron) {
        BorrowingRecord borrowingRecord = borrowingRecordRepository.findByBookAndPatronAndReturnDateIsNull(book, patron);
        if(borrowingRecord!=null){
            borrowingRecord.setReturnDate(new Date());
            return borrowingRecordRepository.save(borrowingRecord);
        }
        return null;
    }
}
