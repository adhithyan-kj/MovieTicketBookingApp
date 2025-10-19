package movieticketbookinggui;

import java.util.HashMap;
import java.util.Map;

public class BookingManager {
    // Map to hold all showtimes, key is a unique ID (Movie Title + Time)
    private static final Map<String, Showtime> SHOWTIMES = new HashMap<>();

    static {
        // Initialize sample data (dummy movies and showtimes)
        Movie m1 = new Movie("KGF: Chapter 3", "10:00 AM");
        Movie m2 = new Movie("Interstellar 2", "1:30 PM");
        
        // Create 5 rows, 10 columns theater for each
        SHOWTIMES.put(m1.toString(), new Showtime(m1, 5, 10));
        SHOWTIMES.put(m2.toString(), new Showtime(m2, 5, 10));
        
        // Pre-book one seat for testing
        SHOWTIMES.get(m1.toString()).bookSeat("A1");
    }
    
    public static Map<String, Showtime> getAllShowtimes() {
        return SHOWTIMES;
    }
    
    public static Showtime getShowtime(String key) {
        return SHOWTIMES.get(key);
    }
}