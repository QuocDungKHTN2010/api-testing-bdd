package resources;

// enum is special class in java which has collection of constants or methods
public enum APIResources {
    loginAPI("/Account/v1/GenerateToken"),
    getBooksAPI("/BookStore/v1/Books"),
    addBooksAPI("/BookStore/v1/Books"),
    addBooksAPI2("/BookStore/v1/Books"),
    deleteBookAPI("/BookStore/v1/Book"),
    createAccountAPI("/Account/v1/User"),
    getAccountAPI("/Account/v1/User"),
    deleteAllBooksAPI("/BookStore/v1/Books?UserId=");

    private final String resource;

    APIResources(String resource) {
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }
}
