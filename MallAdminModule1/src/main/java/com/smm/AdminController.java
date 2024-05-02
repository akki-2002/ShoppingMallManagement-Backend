package com.smm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	// GET method to fetch all admins
	
	@GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> adminList = adminService.listAll();
        return new ResponseEntity<>(adminList, HttpStatus.OK);
    }
	
	// GET method to fetch an admin by ID
	
    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        Admin admin = adminService.get(id);
        if (admin != null) {
            return new ResponseEntity<>(admin, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
     
        }
    }
    
	 // POST method to add a new admin
    
	 @PostMapping
	 public ResponseEntity<Admin> addAdmin(@RequestBody Admin admin) {
	        adminService.save(admin);
	        return new ResponseEntity<>(admin, HttpStatus.CREATED);
	    }
	 
	// PUT method to update an existing admin
	 
	    @PutMapping("/{id}")
	    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @RequestBody Admin admin) {
	        Admin existingAdmin = adminService.get(id);
	        if (existingAdmin != null) {
	            // Update the fields of existingAdmin with the values from the provided admin
	            existingAdmin.setName(admin.getName());
	            existingAdmin.setPassword(admin.getPassword());
	            existingAdmin.setPhone(admin.getPhone());
	            
	            adminService.save(existingAdmin);
	            return new ResponseEntity<>(existingAdmin, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
	    
	// DELETE method to delete an admin by ID
	    
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
	        Admin existingAdmin = adminService.get(id);
	        if (existingAdmin != null) {
	            adminService.delete(id);
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
	    
	    
	 // POST method for admin login
	    
	    @PostMapping("/admins/login")
	    public ResponseEntity<?> login(@RequestBody Admin admin) {
	        Admin loggedInAdmin = adminService.login(admin.getName(), admin.getPassword());
	        if (loggedInAdmin != null) {
	            return ResponseEntity.ok(loggedInAdmin); 
	        } else {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed"); 
	        }
	    }

	    // POST method for admin logout
	    
	    @PostMapping("/logout")
	    public ResponseEntity<String> logout(@RequestBody Admin admin) {
	        boolean result = adminService.logout(admin.getName(), admin.getPassword());
	        if (result) {
	            return new ResponseEntity<>("Logout successful", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Logout failed", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	    
	    
}
