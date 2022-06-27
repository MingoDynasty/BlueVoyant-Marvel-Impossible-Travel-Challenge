package com.example.springboot;

import com.arnaudpiroelle.marvel.api.MarvelApi;
import com.arnaudpiroelle.marvel.api.exceptions.AuthorizationException;
import com.arnaudpiroelle.marvel.api.exceptions.QueryException;
import com.arnaudpiroelle.marvel.api.objects.Character;
import com.arnaudpiroelle.marvel.api.objects.ref.DataContainer;
import com.arnaudpiroelle.marvel.api.objects.ref.DataWrapper;
import com.arnaudpiroelle.marvel.api.params.name.character.ListCharacterParamName;
import com.arnaudpiroelle.marvel.api.services.sync.CharactersService;
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
public class MarvelService {
    private static final Logger logger = LoggerFactory.getLogger(MarvelService.class);
    private CharactersService charactersService;
    @Value("${marvel.api.public.key}")
    private String MARVEL_API_PUBLIC_KEY;
    @Value("${marvel.api.private.key}")
    private String MARVEL_API_PRIVATE_KEY;

    @PostConstruct
    public void postConstruct() {
        MarvelApi.configure()
                .withApiKeys(MARVEL_API_PUBLIC_KEY, MARVEL_API_PRIVATE_KEY)

                // no need to log anything, though maybe we may want to expose this to our Spring config file
                .withLogLevel(RestAdapter.LogLevel.NONE)
                .init();

        // Synchronous version
        charactersService = MarvelApi.getService(CharactersService.class);
    }

    public List<Character> listCharacter(String characterName) {
        logger.info("Listing characters with name: {}", characterName);
        Map<ListCharacterParamName, String> optionsMap = new HashMap<>();
        optionsMap.put(ListCharacterParamName.NAME, characterName);

        try {
            DataWrapper<Character> dataWrapper = charactersService.listCharacter(optionsMap);
            DataContainer<Character> dataContainer = dataWrapper.getData();
            List<Character> characters = dataContainer.getResults();
            logger.info("Found {} characters.", characters.size());
            logger.info("{}", characters);
            return characters;
        } catch (QueryException e) {
            // TODO: need a longer investigation on how we want to handle errors
            throw new RuntimeException(e);
        } catch (AuthorizationException e) {
            throw new RuntimeException(e);
        }
    }
}
