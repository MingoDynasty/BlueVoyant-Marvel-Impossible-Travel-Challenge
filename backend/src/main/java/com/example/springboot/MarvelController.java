package com.example.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class MarvelController {
    private static final Logger logger = LoggerFactory.getLogger(MarvelController.class);

    @Autowired
    private MarvelService marvelService;
    @Autowired
    private MarvelPersistenceService marvelPersistenceService;

    @GetMapping("/character/investigate")
    public ResponseEntity<Object> getCharacter(@RequestParam String name) {
        List<Character> characters = marvelService.investigateCharacter(name);
        return new ResponseEntity<>(characters, HttpStatus.OK);
    }

    @GetMapping("/character/persisted")
    public ResponseEntity<Object> getPersistedCharacters() {
        List<Character> characters = marvelPersistenceService.findAll();
        return new ResponseEntity<>(characters, HttpStatus.OK);
    }

    @DeleteMapping("/character/persisted")
    public ResponseEntity<Object> deletePersistedCharacters() {
        marvelPersistenceService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
