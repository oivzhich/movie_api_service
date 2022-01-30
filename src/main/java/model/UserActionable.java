package model;

import io.restassured.response.Response;
import lombok.Data;
import utils.HttpRequestSender;

@Data
public class UserActionable {
    private static final String MOVIE_SERVICE = "/api/movieservice";

    public Response reset() {
        String call = MOVIE_SERVICE + "/reset";
        return HttpRequestSender.get(call);
    }

    public Response updateMovieInfo(final Movie movie) {
        String call = String.format("%s/%d", MOVIE_SERVICE, movie.getId());
        return HttpRequestSender.put(call, movie);
    }

    public Response getMovieInfo(final Integer movieId) {
        String call = String.format("%s/%d", MOVIE_SERVICE, movieId);
        return HttpRequestSender.get(call);
    }

    public Response createMovieInfo(final Movie movie) {
        return HttpRequestSender.post(MOVIE_SERVICE, movie);
    }

    public Response deleteMovieInfo(final Movie movie) {
        return HttpRequestSender.delete(String.format("%s/%s", MOVIE_SERVICE, movie.getId()));
    }
}
