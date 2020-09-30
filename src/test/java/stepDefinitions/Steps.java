package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import pojo.AddBooksPayload;
import pojo.DeleteBookPayload;
import pojo.Isbn;
import pojo.LoginPayload;
import resources.APIResources;
import resources.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Steps extends Utils {

    private static LoginPayload loginPayload;
    private static AddBooksPayload addBooksPayload;
    private static DeleteBookPayload deleteBookPayload;
    private static String jsonString;
    RequestSpecification request;
    Response response;

    @Given("Username {string} and Password {string}")
    public void username_and_Password(String username, String password) {
        loginPayload = new LoginPayload(username, password);
    }

    @When("user call {string} with {string} http request")
    public void user_call_with_http_request(String resource, String method) throws IOException {
        APIResources resourceAPI = APIResources.valueOf(resource);
        request = requestSpecification();
        if (method.equalsIgnoreCase("GET")) {
            switch (resource) {
                case "getBooksAPI":
//                    request.body(new byte[]{});
                    response = request.get(resourceAPI.getResource());
                    break;
                default:
                    System.out.println("no match");
            }

        } else if (method.equalsIgnoreCase("POST")) {
            switch (resource) {
                case "loginAPI":
                    request.body(loginPayload);
                    response = request.post(resourceAPI.getResource());
                    request.header("Authorization", "Bearer " + extractToken(response.asString()));
                    break;
                case "addBooksAPI":
                    request.body(addBooksPayload);
                    response = request.post(resourceAPI.getResource());
                    break;
                default:
                    System.out.println("no match");
            }
        } else if (method.equalsIgnoreCase("DELETE")) {
            switch (resource) {
                case "deleteBookAPI":
                    request.body(deleteBookPayload);
                    response = request.delete(resourceAPI.getResource());
                    break;
                default:
                    System.out.println("no match");
            }
        }

        jsonString = response.asString();
    }

    private String extractToken(String responseData) {
        return JsonPath.from(responseData).get("token").toString();
    }

    @Then("the API call got success with status code is {int}")
    public void the_API_call_got_success_with_status_code_is(int statusCode) {
        Assert.assertEquals(statusCode, response.getStatusCode());
    }

    @Given("a list isbn of books")
    public void aListIsbnOfBooks(DataTable dataTable) throws IOException {
        addBooksPayload = new AddBooksPayload();
        addBooksPayload.userId = getGlobalValue("userId");
        addBooksPayload.collectionOfIsbns = new ArrayList<>();
        List<Map<String, String>> tableList = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> tableItem : tableList) {
            addBooksPayload.collectionOfIsbns.add(new Isbn(tableItem.get("isbn")));
        }
    }

    @Given("an isbn of book {string}")
    public void anIsbnOfBook(String isbn) throws IOException {
        deleteBookPayload = new DeleteBookPayload(isbn, getGlobalValue("userId"));
    }

}
