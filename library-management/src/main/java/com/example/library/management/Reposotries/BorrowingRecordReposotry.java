package com.example.library.management.Reposotries;

import com.example.library.management.Entities.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRecordReposotry extends JpaRepository<BorrowingRecord, Long> {
}
