package com.authenticate.authTemplate.controllers;

import com.authenticate.authTemplate.auth.User;
import com.authenticate.authTemplate.models.Profile;
import com.authenticate.authTemplate.repositories.ProfileRepository;
import com.authenticate.authTemplate.repositories.UserRepository;
import com.authenticate.authTemplate.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private ProfileRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Secured("ROLE_CUSTOMER")
    @PostMapping
    public @ResponseBody
    ResponseEntity<Profile> createProfile(@RequestBody Profile newProfile) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User currentUser = userRepository.findById(userDetails.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        newProfile.setUser(currentUser);

        return new ResponseEntity<>(repository.save(newProfile), HttpStatus.CREATED);
    }

    @GetMapping
    public @ResponseBody ResponseEntity<List<Profile>> getAllProfiles() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<Profile> getProfileById(@PathVariable Long id) {
        return new ResponseEntity<>(repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public @ResponseBody ResponseEntity<Profile> updateProfile(@PathVariable Long id, @RequestBody Profile update) {
        Profile profile = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (update.getName() != null) {
            profile.setName(update.getName());
        }
        if (update.getDescription() != null){
            profile.setDescription(update.getDescription());
        }

        return new ResponseEntity<>(repository.save(profile), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<String> deleteProfile(@PathVariable Long id) {
        repository.deleteById(id);

        return new ResponseEntity<>("Profile Deleted", HttpStatus.OK);
    }
}
