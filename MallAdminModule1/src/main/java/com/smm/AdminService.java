package com.smm;


import java.util.List;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AdminService {
	

	// Map to store logged-in users' sessions
    private Map<String, String> loggedInUsers = new HashMap<>();

	@Autowired
	private AdminRepository adminRepository;

    public List<Admin> listAll() {
        return adminRepository.findAll();
    }

    public void save(Admin admin) {
        adminRepository.save(admin);
    }

    public Admin get(Long id) {
        return adminRepository.findById(id).get();
    }

    public void delete(Long id) {
        adminRepository.deleteById(id);
    }
    
    
    public Admin login(String name, String password) {
        Admin admin = adminRepository.findByName(name);
        if (admin != null && admin.getPassword().equals(password)) {
            // Add user to logged-in users map
            loggedInUsers.put(name, password);
            return admin;
        } else {
            return null;
        }
    }

    public boolean logout(String name, String password) {
        // Check if user is logged in
        if (loggedInUsers.containsKey(name) && loggedInUsers.get(name).equals(password)) {
            // Remove user from logged-in users map
            loggedInUsers.remove(name);
            // Logout successful
            return true;
        } else {
            // User not logged in or credentials mismatch
            return false;
        }
    }


   
}
