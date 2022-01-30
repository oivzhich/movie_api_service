package utils;

import config.PropertiesConfiguration;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

public class HttpRequestSender {
    private static final String ERROR_MESSAGE = "this is a error causing call";
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestSender.class);
    protected static final PropertiesConfiguration conf =
            ConfigFactory.create(PropertiesConfiguration.class, System.getProperties());

    private HttpRequestSender() {

    }

    public static String getBaseURI() {
        return String.format("http://%s:%s@%s", conf.username(), conf.password(), conf.env());
    }

    public static RequestSpecification getMinimumRequestSpecification() {
        return new RequestSpecBuilder().setContentType(ContentType.JSON).setBaseUri(getBaseURI())
                .setAccept(ContentType.ANY).log(LogDetail.BODY).log(LogDetail.HEADERS).log(LogDetail.METHOD)
                .log(LogDetail.URI).build();
    }

    /***
     * Make a POST request
     * @param call - endpoint URL
     * @param parameters - body parameters as Object
     * @return - Response
     */
    public static Response post(String call, Object parameters) {
        if (call.contains("null")) {
            LOGGER.error(ERROR_MESSAGE);
        }
        Response r = null;
        try {
            r = given().spec(getMinimumRequestSpecification()).body(parameters).when().post(call).peek();
            onRequestSuccess("POST", r);
        } catch (Exception e) {
            onRequestFail("POST", e);
        }
        return r;
    }

    public static Response put(String call, Object parameters) {
        if (call.contains("null")) {
            LOGGER.error(ERROR_MESSAGE);
        }
        Response r = null;
        try {
            r = given().spec(getMinimumRequestSpecification()).body(parameters).when().put(call);
            onRequestSuccess("PUT", r);
        } catch (Exception e) {
            onRequestFail("PUT", e);
        }
        return r;
    }

    /***
     * Make a GET request
     * @param call - endpoint URL
     * @return - Response
     */
    public static Response get(String call) {
        if (call.contains("null"))
            LOGGER.error(ERROR_MESSAGE);
        Response r = null;
        try {
            r = given().spec(getMinimumRequestSpecification()).when().get(call);
            onRequestSuccess("GET", r);
        } catch (Exception e) {
            onRequestFail("GET", e);
        }
        return r;
    }

    private static void onRequestSuccess(String type, Response response) {
        LOGGER.info(String.format("Response headers: %n%s", response.headers().toString()));
        LOGGER.info("Response:\n");
        response.prettyPrint();
        LOGGER.info(String.format("---------------------------- SUCCESS Of %s ----------------", type));
    }

    public static void onRequestFail(String type, Exception e) {
        LOGGER.error(e.getMessage(), e);
        LOGGER.info(String.format("---------------------------- FAIL Of %s ----------------", type));
    }

    public static Response delete(String call) {
        if (call.contains("null"))
            LOGGER.error(ERROR_MESSAGE);
        Response r = null;
        try {
            r = given().spec(getMinimumRequestSpecification()).when().delete(call);
            onRequestSuccess("GET", r);
        } catch (Exception e) {
            onRequestFail("GET", e);
        }
        return r;
    }
}
