package com.example.library.management.Services;

import com.example.library.management.Entities.BorrowingRecord;
import com.example.library.management.Entities.Patron;
import com.example.library.management.Repositories.BorrowingRecordRepository;
import com.example.library.management.Repositories.PatronRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class PatronService {
    @Autowired
    private PatronRepository patronRepository;

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

@Transactional(readOnly = true)
    public List<Patron> getAllPatrons(){
        return patronRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "patrons", key = "#id")
     public Patron getPatronById(Long id){
          return patronRepository.findById(id).orElse(null);
     }

    @Transactional
     @CachePut(cacheNames = "patrons", key = "#patron.id")
     public Patron addPatron(Patron patron){
            return patronRepository.save(patron);
    }

    @Transactional
    @CachePut(cacheNames = "patrons", key = "#id")
    public Patron updatePatron(Long id, Patron patron){
        Patron existingPatron = patronRepository.findById(id).orElse(null);
        if(existingPatron!=null){
            existingPatron.setName(patron.getName());
            existingPatron.setAddress(patron.getAddress());
            existingPatron.setEmail(patron.getEmail());
            existingPatron.setPhoneNumber(patron.getPhoneNumber());
            return patronRepository.save(existingPatron);
        }
        return null;
    }

    @Transactional
    @CacheEvict(cacheNames = "patrons", key = "#id")
    public Boolean deletePatron(Long id){
        Patron existingPatron = patronRepository.findById(id).orElse(null);
        if(existingPatron!=null){
            List<BorrowingRecord> borrowingRecords= borrowingRecordRepository.findByPatronAndReturnDateIsNotNull(existingPatron);
            List<BorrowingRecord> borrowingRecords1 = borrowingRecordRepository.findByPatronAndReturnDateIsNull(existingPatron);
            if(!borrowingRecords.isEmpty() && borrowingRecords1.isEmpty()){
                borrowingRecordRepository.deleteAll(borrowingRecords);
            }
            patronRepository.delete(existingPatron);
            return true;
        }
        return false;
    }
}
