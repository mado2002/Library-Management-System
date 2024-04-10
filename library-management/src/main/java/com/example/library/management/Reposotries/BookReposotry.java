package com.example.library.management.Reposotries;

import com.example.library.management.Entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReposotry extends JpaRepository<Book, Long> {
}
