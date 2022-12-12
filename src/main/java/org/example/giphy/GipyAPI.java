package org.example.giphy;

import at.mukprojects.giphy4j.exception.GiphyException;
import org.example.giphy.search.GipySearch;

public class GipyAPI {
    GipySearch gipySearch = new GipySearch();

    public String giphy(String wordToSearch) throws GiphyException {
        return gipySearch.giphy(wordToSearch);
    }
}
