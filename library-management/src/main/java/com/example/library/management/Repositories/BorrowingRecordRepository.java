package com.example.library.management.Repositories;

import com.example.library.management.Entities.Book;
import com.example.library.management.Entities.BorrowingRecord;
import com.example.library.management.Entities.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {
   BorrowingRecord findByBookAndPatronAndReturnDateIsNull(Book book, Patron patron);
   BorrowingRecord findByBookAndReturnDateIsNull(Book book);
   List<BorrowingRecord> findByPatronAndReturnDateIsNull(Patron patron);
   List<BorrowingRecord> findByPatronAndReturnDateIsNotNull(Patron patron);
    List<BorrowingRecord> findByBookAndReturnDateIsNotNull(Book book);
}
