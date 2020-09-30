package pojo;

public class DeleteBookPayload {
    public String isbn;
    public String userId;

    public DeleteBookPayload(String isbn, String userId) {
        this.isbn = isbn;
        this.userId = userId;
    }
}
