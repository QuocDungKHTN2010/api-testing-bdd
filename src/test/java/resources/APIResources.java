package resources;

// enum is special class in java which has collection of constants or methods
public enum APIResources {
    loginAPI("/Account/v1/GenerateToken"),
    getBooksAPI("/BookStore/v1/Books"),
    addBooksAPI("/BookStore/v1/Books"),
    deleteBookAPI("/BookStore/v1/Book");

    private final String resource;

    APIResources(String resource) {
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }
}
