package com.example.library.management.Services;

import com.example.library.management.Entities.Book;
import com.example.library.management.Reposotries.BookReposotry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookReposotry bookRepository;

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }
    public Book getBookById(Long id){
        return bookRepository.findById(id).orElse(null);
    }
    public Book addBook(Book book){
        return bookRepository.save(book);
    }
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
    public Boolean deleteBook(Long id){
        Book existingBook = bookRepository.findById(id).orElse(null);
        if(existingBook!=null){
            bookRepository.delete(existingBook);
            return true;
        }
        return false;
    }


}
