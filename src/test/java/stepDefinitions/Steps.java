package stepDefinitions;

import data.DataDriven;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.compress.utils.Lists;
import org.junit.Assert;
import pojo.*;
import resources.APIResources;
import resources.Utils;

import java.io.IOException;
import java.util.*;


public class Steps extends Utils {

    static String jsonString;
    private static LoginPayload loginPayload;
    private static AddBooksPayload addBooksPayload;
    private static HashMap<String, Object> addBooksHashmapPayload;
    private static DeleteBookPayload deleteBookPayload;
    private static String USER_ID;
    GetBooksListResponse getBooksListResponse;
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
                    response = request.get(resourceAPI.getResource());
                    break;
                case "getAccountAPI":
                    response = request.get(resourceAPI.getResource() + "/" + USER_ID);
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
                case "addBooksAPI2":
                    request.body(addBooksHashmapPayload);
                    response = request.post(resourceAPI.getResource());
                    break;
                case "createAccountAPI":
                    request.body(loginPayload);
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
                case "deleteAllBooksAPI":
                    request.body(new byte[]{});
                    response = request.delete(resourceAPI.getResource() + USER_ID);
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

    @Given("UserId {string}")
    public void userid(String userId) {
        USER_ID = userId;
    }

    @And("Response body has an item with key {string} and value {string} in a list")
    public void responseBodyHasAnItemWithKeyAndValueInAList(String keyValue, String expectedValue) {
        List<String> listValues = JsonPath.from(jsonString).get(keyValue);
        Assert.assertTrue(listValues.contains(expectedValue));
    }


    @And("Response body has an item with key {string} and value {string} in a sub-list {string}")
    public void responseBodyHasAnItemWithKeyAndValueInASubList(String keyValue, String expectedValue,
                                                               String parentKeyValue) {
        List<List<LinkedHashMap>> listParentValues = JsonPath.from(jsonString).get(parentKeyValue);
        boolean contains = false;
        for (int i = 0; i < listParentValues.size(); i++) {
            List<LinkedHashMap> listChildValues = listParentValues.get(i);
            for (int j = 0; j < listChildValues.size(); j++) {
                LinkedHashMap map = (LinkedHashMap) listChildValues.get(j);
                if (map.get(keyValue).equals(expectedValue)) {
                    contains = true;
                    break;
                }

            }
            if (contains) {
                break;
            }
        }
        Assert.assertTrue(contains);
    }

    @When("user collects all ISBN of books")
    public void userCollectsAllISBNOfBooks() throws IOException {
        getBooksListResponse = GetBooksListResponse.parse(jsonString);
        List<Book> books = getBooksListResponse.getBooks();
        addBooksPayload = new AddBooksPayload();
        addBooksPayload.userId = getGlobalValue("userId");
        addBooksPayload.collectionOfIsbns = new ArrayList<>();
        for (Book book : books) {
            addBooksPayload.collectionOfIsbns.add(new Isbn(book.isbn));
        }
    }

    @When("user prepares payload from excel file")
    public void userPreparesPayloadFromExcelFile() throws IOException {
        DataDriven dataDriven = new DataDriven();
        ArrayList<String> data = dataDriven.getData("Books", "AddBook");

        addBooksHashmapPayload = new HashMap<>();
        addBooksHashmapPayload.put("userId", getGlobalValue("userId"));

        List isbnList = Lists.newArrayList();
        HashMap<String, Object> book1 = new HashMap<>();
        book1.put("isbn", data.get(1));
//        HashMap<String, Object> book2 = new HashMap<>();
//        book2.put("isbn", data.get(2));
        isbnList.add(book1);
//        isbnList.add(book2);

        addBooksHashmapPayload.put("collectionOfIsbns", isbnList);
    }

}
