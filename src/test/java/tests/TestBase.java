package tests;

import config.PropertiesConfiguration;
import io.restassured.response.Response;
import model.UserActionable;
import org.aeonbits.owner.ConfigFactory;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.lang.reflect.Method;

public class TestBase {
    protected static final PropertiesConfiguration conf =
            ConfigFactory.create(PropertiesConfiguration.class, System.getProperties());
    private static final Logger LOGGER = LoggerFactory.getLogger(TestBase.class);
    protected Response response;
    protected UserActionable user;

    @BeforeClass(alwaysRun = true, description = "Prepare test environment")
    public void beforeClass() {
        LOGGER.info("-------------------------------- Test suite '" + this.getClass().getSimpleName() +
                "' started ------------------------------");
        user = new UserActionable();
        response = user.reset();
        response.then().assertThat().statusCode(HttpStatus.SC_OK);
    }

    @BeforeTest(alwaysRun = true)
    public void setParameters() {
        LOGGER.info("-------------------------------- Test '" + this.getClass().getSimpleName() +
                "' started ------------------------------");

        //        RestAssured.config = RestAssuredConfig.config()
        //                .objectMapperConfig(objectMapperConfig().jackson2ObjectMapperFactory((type, s) -> {
        //                    ObjectMapper om = new ObjectMapper().findAndRegisterModules();
        //                    om.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        //                    return om;
        //                }));
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method) {
        String testName = method.getName();
        LOGGER.info("-------------------------------- Test method'" + testName +
                "' started ------------------------------");
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        String status;
        switch (result.getStatus()) {
            case 1:
                status = "SUCCESS";
                break;
            case 2:
                status = "FAIL";
                break;
            case 3:
                status = "SKIPPED";
                break;
            default:
                status = null;
        }
        LOGGER.info("-------------------------------- Test method'" + result.getName() + "' finished with status " +
                status + " ------------------------------");
    }
}
