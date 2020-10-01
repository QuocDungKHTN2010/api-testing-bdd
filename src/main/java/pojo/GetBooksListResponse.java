package pojo;

import com.google.gson.Gson;

import java.util.List;

public class GetBooksListResponse {
    public List<Book> books;

    public static GetBooksListResponse parse(String json) {
        return new Gson().fromJson(json, GetBooksListResponse.class);
    }

    public List<Book> getBooks() {
        return books;
    }
}
