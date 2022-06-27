package com.example.springboot;

import com.arnaudpiroelle.marvel.api.objects.Character;
import com.arnaudpiroelle.marvel.api.objects.CharacterList;
import com.arnaudpiroelle.marvel.api.objects.CharacterSummary;
import com.arnaudpiroelle.marvel.api.objects.Comic;
import com.arnaudpiroelle.marvel.api.params.name.character.GetCharacterComicsParamName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MarvelService {
    private static final Logger logger = LoggerFactory.getLogger(MarvelService.class);

    @Autowired
    private MarvelOutboundService marvelOutboundService;
    @Autowired
    private MarvelPersistenceService marvelPersistenceService;

    //    public List<Character> listCharacter(String characterName) {
    public List<Character> investigateCharacter(String characterName) {
        logger.info("Listing characters with name: {}", characterName);

        // 1. Get the main character we are searching for
        List<Character> characters = marvelOutboundService.listCharacter(characterName);
        if (characters.size() > 1) {
            // TODO: need better error handling
            logger.error("How do we have more than one Spectrums?!");
            return new ArrayList<>();
        }

        // 2. Get all comics containing this character
        Character character = characters.get(0);
        int totalComics = character.getComics().getAvailable();
        logger.info("Found {} total comics.", totalComics);

        // 3. Get list of characters for each of these comics

        // keep a counter, so we know how much to offset
        int comicsProcessed = 0;
        Set<String> characterNames = new HashSet<>();
        while (comicsProcessed < totalComics) {
            Map<GetCharacterComicsParamName, String> optionsMap = new HashMap<>();
            optionsMap.put(GetCharacterComicsParamName.OFFSET, String.valueOf(comicsProcessed));

            // stay within their API limits to avoid issues
            int limit = Math.min(100, totalComics - comicsProcessed);
            optionsMap.put(GetCharacterComicsParamName.LIMIT, String.valueOf(limit));

            List<Comic> comics = marvelOutboundService.getComicsForCharacterId(character.getId(), optionsMap);
            logger.info("Found {} comics.", comics.size());
            comicsProcessed += comics.size();

            for (Comic comic : comics) {
                CharacterList characterList = comic.getCharacters();
                List<CharacterSummary> characterSummaries = characterList.getItems();
                for (CharacterSummary characterSummary : characterSummaries) {
                    String name = characterSummary.getName();
                    characterNames.add(name);
                }

                logger.debug("Found {} characters in comic: {}", characterSummaries.size(), comic.getTitle());
            }
        }
        logger.info("Found {} character names: {}", characterNames.size(), characterNames);

        // 5. Persist all data to database
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
