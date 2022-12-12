package org.example.giphy.search;

import at.mukprojects.giphy4j.Giphy;
import at.mukprojects.giphy4j.entity.search.SearchRandom;
import at.mukprojects.giphy4j.exception.GiphyException;
import org.example.parameters.eTokens;

public class GipySearch {
    Giphy giphy = new Giphy(eTokens.tokenKiphy.botToken);

    public String giphy (String wordToSearch) throws GiphyException {
        SearchRandom feed = giphy.searchRandom(wordToSearch);
        return feed.getData().getUrl();
    }
}
