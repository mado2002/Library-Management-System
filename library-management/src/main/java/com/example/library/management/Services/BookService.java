package com.example.library.management.Services;

import com.example.library.management.Entities.Book;
import com.example.library.management.Entities.BorrowingRecord;
import com.example.library.management.Repositories.BookRepository;
import com.example.library.management.Repositories.BorrowingRecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.ReadOnlyBufferException;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;

@Service
@Slf4j
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

   @Transactional(readOnly = true)
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "books", key = "#id")
    public Book getBookById(Long id){
        return bookRepository.findById(id).orElse(null);
    }
    @Transactional
    @CachePut(cacheNames = "books", key = "#book.id")
    public Book addBook(Book book){
        return bookRepository.save(book);
    }
    @Transactional
    @CachePut(cacheNames = "books", key = "#id")
    public Book updateBook(Long id, Book book){
        Book existingBook = bookRepository.findById(id).orElse(null);
        if(existingBook!=null){
            existingBook.setAuthor(book.getAuthor());
            existingBook.setTitle(book.getTitle());
            existingBook.setIsbn(book.getIsbn());
            existingBook.setPublicationYear(book.getPublicationYear());
            return bookRepository.save(existingBook);
        }
        return null;
    }
    @Transactional
    @CacheEvict(cacheNames = "books", key = "#id")
    public Boolean deleteBook(Long id){
        Book existingBook = bookRepository.findById(id).orElse(null);
        if(existingBook!=null){
            List<BorrowingRecord> borrowingRecords = borrowingRecordRepository.findByBookAndReturnDateIsNotNull(existingBook);
            BorrowingRecord borrowingRecord = borrowingRecordRepository.findByBookAndReturnDateIsNull(existingBook);
            if(!borrowingRecords.isEmpty() && borrowingRecord==null){
                borrowingRecordRepository.deleteAll(borrowingRecords);
            }
            bookRepository.delete(existingBook);
            return true;
        }
        return false;
    }


}
