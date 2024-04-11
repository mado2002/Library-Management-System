package com.example.library.management.Controllers;

import com.example.library.management.Entities.Patron;
import com.example.library.management.Services.PatronService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    private final PatronService patronService;

    public PatronController(PatronService patronService){
        this.patronService = patronService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllPatrons(){
        List<Patron> patrons = patronService.getAllPatrons();
        return ResponseEntity.ok(patrons);
    }
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> addPatron(@Valid @RequestBody Patron patron){
        Patron newPatron = patronService.addPatron(patron);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPatron);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getPatronById(@PathVariable Long id){
        Patron patron = patronService.getPatronById(id);
        if(patron!=null){
            return ResponseEntity.ok(patron);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patron not found");
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updatePatron(@PathVariable Long id,@Valid @RequestBody Patron patron){
        Patron updatedPatron = patronService.updatePatron(id,patron);
        if(updatedPatron!=null){
            return ResponseEntity.ok(updatedPatron);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patron not found");
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deletePatron(@PathVariable Long id){
        Boolean isDeleted = patronService.deletePatron(id);
        if(isDeleted){
            return ResponseEntity.ok("Patron deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patron not found");
    }
}
