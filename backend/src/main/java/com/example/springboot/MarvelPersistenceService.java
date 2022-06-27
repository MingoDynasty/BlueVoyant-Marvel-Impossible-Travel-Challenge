package com.example.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MarvelPersistenceService {
    private static final Logger logger = LoggerFactory.getLogger(MarvelPersistenceService.class);
    @Autowired
    private MarvelRepository marvelRepository;

    public List<Character> insertMarvelApiCharacters(List<com.arnaudpiroelle.marvel.api.objects.Character> characters) {
        List<Character> convertedList = new ArrayList<>();
        for (com.arnaudpiroelle.marvel.api.objects.Character character : characters) {
            Character convertedCharacter = new Character();
            convertedCharacter.setMarvelId(character.getId());
            convertedCharacter.setName(character.getName());
            convertedCharacter.setDescription(character.getDescription());
            convertedCharacter.setThumbnailPath(character.getThumbnail().getPath() + "." + character.getThumbnail().getExtension());
            convertedList.add(convertedCharacter);
        }
        return insertCharacters(convertedList);
    }

    public void deleteAll() {
        this.marvelRepository.deleteAll();
    }

    public List<Character> findAll() {
        return this.marvelRepository.findAll();
    }

    public Character insertCharacter(Character character) {
        return this.marvelRepository.save(character);
    }

    public List<Character> insertCharacters(List<Character> characters) {
        return this.marvelRepository.saveAll(characters);
    }
}
