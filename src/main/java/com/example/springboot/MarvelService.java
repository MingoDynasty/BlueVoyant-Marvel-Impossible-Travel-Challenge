package com.example.springboot;

import com.arnaudpiroelle.marvel.api.objects.Character;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarvelService {
    private static final Logger logger = LoggerFactory.getLogger(MarvelService.class);

    @Autowired
    private MarvelOutboundService marvelOutboundService;
    @Autowired
    private MarvelPersistenceService marvelPersistenceService;

    public List<Character> listCharacter(String characterName) {
        logger.info("Listing characters with name: {}", characterName);
        List<Character> characters = marvelOutboundService.listCharacter(characterName);

        if (!characters.isEmpty()) {
            // TODO: in the future, probably want to build some sort of mapping instead so don't need to delete
            // delete existing characters
            marvelPersistenceService.deleteAll();

            logger.info("Persisting {} characters to database.", characters.size());
            marvelPersistenceService.insertMarvelApiCharacters(characters);
        }
        return characters;
    }
}
