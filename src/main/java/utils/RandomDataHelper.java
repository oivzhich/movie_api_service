package utils;

import com.github.javafaker.Faker;

import java.util.Locale;

//Based on Faker lib https://github.com/DiUS/java-faker
public class RandomDataHelper {
    private RandomDataHelper() {

    }
    public static final Faker faker = new Faker(new Locale("en-GB"));

    public static Integer getId() {
        return Math.toIntExact(faker.number().randomNumber(7, true));
    }

    public static Integer getRandomYear() {
        return faker.number().numberBetween(2000, 2022);
    }

    public static String getRandomDirector() {
        return faker.name().fullName();
    }

    public static String getRandomTitle() {
        return faker.book().title();
    }
}
