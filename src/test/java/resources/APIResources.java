package resources;

// enum is special class in java which has collection of constants or methods
public enum APIResources {
    loginAPI("/Account/v1/GenerateToken");

    private final String resource;

    APIResources(String resource) {
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }
}
