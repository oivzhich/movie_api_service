package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import utils.RandomDataHelper;

@Data
@JsonIgnoreProperties(value = {"id"})
public class Movie {
    private Integer id;
    private Integer year;
    private String director;
    private String title;

    public Movie() {
        this.year = RandomDataHelper.getRandomYear();
        this.director = RandomDataHelper.getRandomDirector();
        this.title = RandomDataHelper.getRandomTitle();
    }
}
