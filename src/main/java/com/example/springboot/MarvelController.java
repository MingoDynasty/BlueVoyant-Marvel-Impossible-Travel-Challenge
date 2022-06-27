package com.example.springboot;

import com.arnaudpiroelle.marvel.api.objects.Character;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class MarvelController {
    private static final Logger logger = LoggerFactory.getLogger(MarvelController.class);

    @Autowired
    private MarvelService marvelService;
    @Autowired
    private MarvelPersistenceService marvelPersistenceService;

    @GetMapping("/characters")
    public ResponseEntity<Object> getCharacter(@RequestParam String name) {
        List<Character> characters = marvelService.listCharacter(name);
        return new ResponseEntity<>(characters, HttpStatus.OK);
    }

    @GetMapping("/persistedCharacters")
    public ResponseEntity<Object> getPersistedCharacters() {
        List<com.example.springboot.Character> characters = marvelPersistenceService.findAll();
        return new ResponseEntity<>(characters, HttpStatus.OK);
    }

}
