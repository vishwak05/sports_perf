package com.example.sports_perf.service;

import com.example.sports_perf.model.Admin;
import com.example.sports_perf.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    // Find an admin by username
    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    // Create or save an admin
    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    // Find admin by ID
    public Optional<Admin> findById(Long id) {
        return adminRepository.findById(id);
    }

    // Delete an admin by ID
    public void deleteById(Long id) {
        adminRepository.deleteById(id);
    }
}
