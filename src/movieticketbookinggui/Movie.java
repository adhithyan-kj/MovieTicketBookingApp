package movieticketbookinggui;

public class Movie {
    private final String title;
    private final String timing;

    public Movie(String title, String timing) {
        this.title = title;
        this.timing = timing;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getTiming() {
        return timing;
    }

    @Override
    public String toString() {
        return title + " - " + timing;
    }
}