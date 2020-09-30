package stepDefinitions;

import io.cucumber.java.Before;

import java.io.IOException;

public class Hooks {
    @Before(value = "@GetToken")
    public void getToken() throws IOException {
        Steps steps = new Steps();
        steps.username_and_Password(steps.getGlobalValue("userName"), steps.getGlobalValue("password"));
        steps.user_call_with_http_request("loginAPI", "post");
    }
}
