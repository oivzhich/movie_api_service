package tests;

import model.Movie;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MovieInfoTests extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestBase.class);
    private Movie randomMovie;
    private Movie updatedMovie;

    @Test
    public void createMovieInfoTest() {
        randomMovie = new Movie();
        response = user.createMovieInfo(randomMovie);
        response.then().assertThat().statusCode(HttpStatus.SC_OK);

        String body = response.then().extract().asString();

        assertThat(body).isNotEmpty();
        assertThat(Integer.parseInt(body)).isPositive();

        randomMovie.setId(Integer.parseInt(body));
        LOGGER.info(String.format("Movie info successfully created with id=%s", randomMovie.getId()));
    }

    @Test(dependsOnMethods = {"createMovieInfoTest"})
    public void getMovieInfoTest() {
        response = user.getMovieInfo(randomMovie.getId());
        response.then().assertThat().statusCode(HttpStatus.SC_OK).body("year", Matchers.equalTo(randomMovie.getYear()))
                .body("director", Matchers.equalTo(randomMovie.getDirector()))
                .body("title", Matchers.equalTo(randomMovie.getTitle()));
    }

    @Test(dependsOnMethods = {"getMovieInfoTest"})
    public void updateMovieInfoTest() {
        updatedMovie = new Movie();
        updatedMovie.setId(randomMovie.getId());
        response = user.updateMovieInfo(updatedMovie);
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
    }

    @Test(dependsOnMethods = {"updateMovieInfoTest"})
    public void verifyMovieInfoUpdated() {
        response = user.getMovieInfo(randomMovie.getId());
        response.then().assertThat().statusCode(HttpStatus.SC_OK).body("year", Matchers.equalTo(updatedMovie.getYear()))
                .body("director", Matchers.equalTo(updatedMovie.getDirector()))
                .body("title", Matchers.equalTo(updatedMovie.getTitle()));
    }

    @Test(dependsOnMethods = {"verifyMovieInfoUpdated"})
    public void deleteMovieInfoTest() {
        response = user.deleteMovieInfo(updatedMovie);
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
    }

    @Test(dependsOnMethods = {"deleteMovieInfoTest"})
    public void verifyMovieInfoDeleted() {
        response = user.getMovieInfo(randomMovie.getId());
        response.then().assertThat().statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
