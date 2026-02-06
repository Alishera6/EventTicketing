package config;

public final class AppConfig {

    private static AppConfig instance;

    private final String url;
    private final String user;
    private final String password;

    private AppConfig() {
        this.url = read("DB_URL");
        this.user = read("DB_USER");
        this.password = read("DB_PASSWORD");
    }

    public static AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    private String read(String key) {
        String value = System.getenv(key);
        if (value == null || value.isBlank()) {
            throw new RuntimeException("Missing environment variable: " + key);
        }
        return value;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
