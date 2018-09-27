package org.smartinrub.videoservice.repository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.smartinrub.videoservice.util.Youtube;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ExtendWith(SpringExtension.class)
@RestClientTest(YoutubeRepositoryImpl.class)
class YoutubeRepositoryImplTest {

    @Autowired
    private YoutubeRepositoryImpl youtubeRepository;

    @Autowired
    private MockRestServiceServer server;

    @MockBean
    private Youtube youtubeProperties;

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("getYoutubeLinksByMovieTitle given movie title when remote Youtube Api returns list of videos then return links")
    class getYoutubeLinksByMovieTitle_givenMovieTitle_whenRemoteYoutubeApiReturnsListOfVideos_thenReturnLinks {

        String expectedLinks;

        List<String> links;

        @BeforeAll
        void initAll() throws IOException {
            expectedLinks = "{\"items\": [\n" +
                    "{\n" +
                    "\"kind\": \"youtube#searchResult\",\n" +
                    "\"etag\": \"XI7nbFXulYBIpL0ayR_gDh3eu1k/BX1kYJFsNfvB5ELJQyQi8rO-Zag\",\n" +
                    "\"id\": {\n" +
                    "\"kind\": \"youtube#video\",\n" +
                    "\"videoId\": \"iVbopWyBR5Y\"\n" +
                    "}\n" +
                    "}]}";
            server.expect(requestTo("https://www.googleapis.com/youtube/v3/search?key=null&part=id&q=test&maxResults=0&type=video"))
                    .andRespond(withSuccess(expectedLinks, MediaType.APPLICATION_JSON));
            links = youtubeRepository.getYoutubeLinksByMovieTitle("test");
        }

        @Nested
        class ListOfLinksIsNotNullOrEmpty {

            @Test
            void listIsNotNull() {
                assertThat(links).isNotNull();
            }

            @Test
            void listIsNotEmpty() {
                assertThat(links).isNotEmpty();
            }

            @Nested
            class ListContainsMovies {

                @Test
                void listContainsThreeMovies () {
                    assertThat(links.size()).isEqualTo(1);
                }

                @Test
                void firstMovieIsCorrect() {
                    assertThat(links.get(0)).isEqualTo("https://www.youtube.com/watch?v=iVbopWyBR5Y");
                }
            }
        }
    }
}