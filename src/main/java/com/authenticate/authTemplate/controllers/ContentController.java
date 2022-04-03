package com.authenticate.authTemplate.controllers;

import com.authenticate.authTemplate.auth.User;
import com.authenticate.authTemplate.models.Content;
import com.authenticate.authTemplate.repositories.ContentRepository;
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
@Secured({"ROLE_CUSTOMER","ROLE_ADMIN"})
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @PostMapping
    public @ResponseBody
    ResponseEntity<Content> createContent(@RequestBody Content newContent) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User currentUser = userRepository.findById(userDetails.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        newContent.setAuthor(profileRepository.findByUser(currentUser));
        newContent.setAuthorName(profileRepository.findByUser(currentUser).getName());

        return new ResponseEntity<>(repository.save(newContent), HttpStatus.CREATED);
    }

    @GetMapping
    public @ResponseBody ResponseEntity<List<Content>> getAllContent() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<Content> getContentById(@PathVariable Long id) {
        return new ResponseEntity<>(repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public @ResponseBody ResponseEntity<Content> updateContent(@PathVariable Long id, @RequestBody Content update) {
        Content content = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (update.getTopic() != null) {
            content.setTopic(update.getTopic());
        }
        if (update.getText() != null){
            content.setText(update.getText());
        }
        if(update.getAuthor() != null){
            content.setAuthor(update.getAuthor());
        }

        return new ResponseEntity<>(repository.save(content), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<String> deleteProfile(@PathVariable Long id) {
        repository.deleteById(id);

        return new ResponseEntity<>("Content Deleted", HttpStatus.OK);
    }
}
