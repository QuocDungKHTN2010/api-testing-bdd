package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import pojo.LoginPayload;
import resources.APIResources;
import resources.Utils;

import java.io.IOException;

public class Steps extends Utils {

    private static LoginPayload loginPayload;
    RequestSpecification request;
    Response response;
    private static String jsonString;


    @Given("Username {string} and Password {string}")
    public void username_and_Password(String username, String password) {
        loginPayload = new LoginPayload(username, password);
    }

    @When("user call {string} with {string} http request")
    public void user_call_with_http_request(String resource, String method) throws IOException {
        APIResources resourceAPI = APIResources.valueOf(resource);
        request = requestSpecification();
        if(method.equalsIgnoreCase("GET")){

        }else if(method.equalsIgnoreCase("POST")){
            switch (resource) {
                case "loginAPI":
                    request.body(loginPayload);
                    response = request.post(resourceAPI.getResource());
                    break;
                default:
                    System.out.println("no match");
            }
        }

        jsonString = response.asString();
    }

    @Then("the API call got success with status code is {int}")
    public void the_API_call_got_success_with_status_code_is(Integer statusCode) {
        Assert.assertEquals(200, response.getStatusCode());
    }
}
