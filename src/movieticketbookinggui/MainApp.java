package movieticketbookinggui;

/**
 * Main application class to launch the GUI frame.
 */
public class MainApp {

    public static void main(String[] args) {

        // Launch the GUI on the Event Dispatch Thread (EDT)
        javax.swing.SwingUtilities.invokeLater(() -> {

            BookingFrame frame = new BookingFrame();

            // Centers the window on the screen
            frame.setLocationRelativeTo(null);

            // Makes the window visible
            frame.setVisible(true);
        });
    }
}