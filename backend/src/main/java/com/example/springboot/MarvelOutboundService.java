package com.example.springboot;

import com.arnaudpiroelle.marvel.api.MarvelApi;
import com.arnaudpiroelle.marvel.api.exceptions.AuthorizationException;
import com.arnaudpiroelle.marvel.api.exceptions.EntityNotFoundException;
import com.arnaudpiroelle.marvel.api.exceptions.QueryException;
import com.arnaudpiroelle.marvel.api.exceptions.RateLimitException;
import com.arnaudpiroelle.marvel.api.objects.Character;
import com.arnaudpiroelle.marvel.api.objects.Comic;
import com.arnaudpiroelle.marvel.api.objects.ref.DataContainer;
import com.arnaudpiroelle.marvel.api.objects.ref.DataWrapper;
import com.arnaudpiroelle.marvel.api.params.name.character.GetCharacterComicsParamName;
import com.arnaudpiroelle.marvel.api.params.name.character.ListCharacterParamName;
import com.arnaudpiroelle.marvel.api.services.sync.CharactersService;
import com.arnaudpiroelle.marvel.api.services.sync.ComicsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit.RestAdapter;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MarvelOutboundService {
    private static final Logger logger = LoggerFactory.getLogger(MarvelOutboundService.class);
    private CharactersService charactersService;
    private ComicsService comicsService;
    @Value("${marvel.api.public.key}")
    private String MARVEL_API_PUBLIC_KEY;
    @Value("${marvel.api.private.key}")
    private String MARVEL_API_PRIVATE_KEY;

    @PostConstruct
    public void postConstruct() {
        MarvelApi.configure()
                .withApiKeys(MARVEL_API_PUBLIC_KEY, MARVEL_API_PRIVATE_KEY)

                // TODO: maybe want to expose this to our Spring config file
                // limit the logging so it's not so noisy, but some logging is useful
                // to know when we are spending some of our daily API allowance.
                .withLogLevel(RestAdapter.LogLevel.BASIC)
                .init();

        // Synchronous versions
        charactersService = MarvelApi.getService(CharactersService.class);
        comicsService = MarvelApi.getService(ComicsService.class);
    }

    public List<Character> getCharactersForComicId(int comicId) {
        try {
            DataWrapper<Character> dataWrapper = comicsService.getComicCharacters(comicId);
            DataContainer<Character> dataContainer = dataWrapper.getData();
            return dataContainer.getResults();
        } catch (AuthorizationException e) {
            throw new RuntimeException(e);
        } catch (QueryException e) {
            throw new RuntimeException(e);
        } catch (RateLimitException e) {
            throw new RuntimeException(e);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Comic> getComicsForCharacterId(int characterId, Map<GetCharacterComicsParamName, String> optionsMap) {
        try {
            DataWrapper<Comic> dataWrapper = charactersService.getCharacterComics(characterId, optionsMap);
            DataContainer<Comic> dataContainer = dataWrapper.getData();
            return dataContainer.getResults();
        } catch (AuthorizationException e) {
            throw new RuntimeException(e);
        } catch (QueryException e) {
            throw new RuntimeException(e);
        } catch (RateLimitException e) {
            throw new RuntimeException(e);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Character> listCharacter(String characterName) {
        Map<ListCharacterParamName, String> optionsMap = new HashMap<>();
        optionsMap.put(ListCharacterParamName.NAME, characterName);

        try {
            DataWrapper<Character> dataWrapper = charactersService.listCharacter(optionsMap);
            DataContainer<Character> dataContainer = dataWrapper.getData();
            List<Character> characters = dataContainer.getResults();
            return characters;
        } catch (QueryException e) {
            // TODO: need a longer investigation on how we want to handle errors
            throw new RuntimeException(e);
        } catch (AuthorizationException e) {
            throw new RuntimeException(e);
        }
    }
}
