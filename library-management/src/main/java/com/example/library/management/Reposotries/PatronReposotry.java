package com.example.library.management.Reposotries;

import com.example.library.management.Entities.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatronReposotry extends JpaRepository<Patron, Long> {
}
