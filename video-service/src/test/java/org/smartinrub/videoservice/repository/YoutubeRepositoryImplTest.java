package org.smartinrub.videoservice.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;

@ExtendWith(SpringExtension.class)
@RestClientTest(YoutubeRepositoryImpl.class)
class YoutubeRepositoryImplTest {

    @Autowired
    private YoutubeRepositoryImpl youtubeRepository;

    @Autowired
    private MockRestServiceServer server;

    @Test
    void getYoutubeLinksByMovieTitle() {
//        server.expect(requestTo("https://api.themoviedb.org/3/search/movie?api_key=28a5b67e6420000653b42520a245e476&query=test"))
//                .andRespond(withSuccess(expectedMovies, MediaType.APPLICATION_JSON));
//        movies = movieService.getMoviesByTitle("test").get();
    }

}