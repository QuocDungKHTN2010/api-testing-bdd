package resources;

import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import java.io.*;
import java.util.Properties;

import static io.restassured.config.HeaderConfig.headerConfig;

public class Utils {
    public static RequestSpecification request;
    public static String HEADER_ACCEPT = "application/json";
    public static String HEADER_CONTENT_TYPE = "application/json";


    public  String getGlobalValue(String key) throws IOException {
        Properties prop = new Properties();
        InputStream fis = getClass().getClassLoader().getResourceAsStream("global.properties");
        prop.load(fis);
        return prop.getProperty(key);
    }

    public RequestSpecification requestSpecification() throws IOException {
        RestAssured.baseURI = getGlobalValue("baseUrl");
        if (request == null) {
            PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
            request = RestAssured.given()
                    .filter(RequestLoggingFilter.logRequestTo(log))
                    .filter(ResponseLoggingFilter.logResponseTo(log))
                    .config(RestAssuredConfig.config().headerConfig(headerConfig().overwriteHeadersWithName("Authorization")));
            buildDefaultHeaders(request);
            return request;
        }
        return request;
    }

    private void buildDefaultHeaders(RequestSpecification request) {
        request.header("Content-Type", HEADER_CONTENT_TYPE);
        request.header("Accept", HEADER_ACCEPT);
    }
}
