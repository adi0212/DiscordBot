package org.example.giphy;

import at.mukprojects.giphy4j.Giphy;
import at.mukprojects.giphy4j.entity.search.SearchFeed;
import at.mukprojects.giphy4j.entity.search.SearchGiphy;
import at.mukprojects.giphy4j.entity.search.SearchRandom;
import at.mukprojects.giphy4j.exception.GiphyException;
import org.example.parameters.eToken;

public class GipyAPI {
    Giphy giphy = new Giphy(eToken.tokenKiphy.botToken);

    public String giphy () throws GiphyException {
        SearchFeed feed = giphy.search("cat", 1, 0);
        return feed.getDataList().get(0).getImages().getOriginal().getUrl();
    }

    public static void main(String[] args) {
        try {
            GipyAPI gipyAPI = new GipyAPI();
            gipyAPI.giphy();
            System.out.println(gipyAPI.giphy());
        }catch (GiphyException giphyException){
        }

    }
}
