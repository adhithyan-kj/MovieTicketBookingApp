package movieticketbookinggui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A pure code-based JFrame for the Movie Ticket Booking System.
 * All UI components are created and managed programmatically.
 */
public class BookingFrame extends JFrame {

    // --- 1. DECLARE COMPONENTS AND LOGGER ---
    private JComboBox<String> cmbShowtime;
    private JTextArea txtSeatMap;
    private JTextField txtSeatInput;
    private JButton btnBook;
    private JLabel lblStatus;
    private static final Logger logger = Logger.getLogger(BookingFrame.class.getName());

    // --- 2. CONSTRUCTOR ---
    public BookingFrame() {
        // Set Look and Feel (Optional, but better appearance)
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            logger.log(Level.WARNING, "Nimbus LookAndFeel not found. Using default.", ex);
        }

        // Initialize and assemble all GUI elements
        initializeComponents(); // <--- This is the method the compiler couldn't find

        // Load data and refresh display
        loadShowtimes();
        updateSeatMap();

        // Final Frame Setup
        setTitle("Movie Booking System");
        setSize(800, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // --- 3. COMPONENT ASSEMBLY METHOD ---
    private void initializeComponents() {
        // Main layout uses BorderLayout (North, Center, South)
        setLayout(new BorderLayout(10, 10));

        // --- NORTH PANEL (Title & Selector) ---
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JLabel titleLabel = new JLabel("Movie Ticket Booking System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 102));

        cmbShowtime = new JComboBox<>();
        cmbShowtime.addActionListener(this::cmbShowtimeActionPerformed);

        northPanel.add(titleLabel);
        northPanel.add(new JLabel("Select Movie:"));
        northPanel.add(cmbShowtime);

        add(northPanel, BorderLayout.NORTH);

        // --- CENTER PANEL (Seat Map) ---
        txtSeatMap = new JTextArea(25, 60);
        txtSeatMap.setEditable(false);
        txtSeatMap.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(txtSeatMap);
        add(scrollPane, BorderLayout.CENTER);

        // --- SOUTH PANEL (Input, Button, Status) ---
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));

        lblStatus = new JLabel("System ready.");
        lblStatus.setPreferredSize(new Dimension(300, 30));

        txtSeatInput = new JTextField(5);

        btnBook = new JButton("BOOK SEAT");
        btnBook.setBackground(new Color(0, 153, 0));
        btnBook.setForeground(Color.WHITE);
        btnBook.addActionListener(this::btnBookActionPerformed);

        southPanel.add(new JLabel("Enter Seat ID (e.g., A5):"));
        southPanel.add(txtSeatInput);
        southPanel.add(btnBook);
        southPanel.add(lblStatus);

        add(southPanel, BorderLayout.SOUTH);
    }

    // --- 4. DATA/LOGIC METHODS ---

    private void loadShowtimes() {
        cmbShowtime.removeAllItems();
        for (String key : BookingManager.getAllShowtimes().keySet()) {
            cmbShowtime.addItem(key);
        }
        if (cmbShowtime.getItemCount() > 0) {
            cmbShowtime.setSelectedIndex(0);
        }
    }

    private void updateSeatMap() {
        String selectedKey = (String) cmbShowtime.getSelectedItem();

        if (selectedKey == null) {
            txtSeatMap.setText("Please select a showtime to view seats.");
            return;
        }

        Showtime showtime = BookingManager.getShowtime(selectedKey);
        if (showtime == null) {
            txtSeatMap.setText("Error: Movie data missing.");
            return;
        }

        StringBuilder map = new StringBuilder(showtime.getMovie().getTitle() + "\n");
        map.append("------------------------------------------------------------------\n");
        map.append("                    S C R E E N HERE\n");
        map.append("------------------------------------------------------------------\n");

        Seat[][] seats = showtime.getSeats();
        for (Seat[] row : seats) {
            map.append(row[0].getSeatId().charAt(0)).append(" | ");
            for (Seat seat : row) {
                map.append(String.format(" %s[%s] ", seat.getSeatId().substring(1), seat.isBooked() ? "X" : " "));
            }
            map.append("|\n");
        }
        map.append("\n X = Booked, Space = Available\n");

        txtSeatMap.setText(map.toString());
        txtSeatMap.setCaretPosition(0);
    }

    // --- 5. ACTION LISTENERS ---

    private void cmbShowtimeActionPerformed(ActionEvent evt) {
        updateSeatMap();
    }

    private void btnBookActionPerformed(ActionEvent evt) {
        String selectedKey = (String) cmbShowtime.getSelectedItem();
        String seatId = txtSeatInput.getText().trim().toUpperCase();

        if (selectedKey == null || seatId.isEmpty()) {
            lblStatus.setForeground(new Color(204, 0, 0));
            lblStatus.setText("Error: Select movie and enter seat ID.");
            return;
        }

        Showtime showtime = BookingManager.getShowtime(selectedKey);

        if (showtime.bookSeat(seatId)) {
            lblStatus.setForeground(new Color(0, 153, 0));
            lblStatus.setText("SUCCESS! Seat " + seatId + " booked for " + showtime.getMovie().getTitle());
            updateSeatMap();
        } else {
            lblStatus.setForeground(new Color(204, 0, 0));
            lblStatus.setText("FAILURE! Seat " + seatId + " is already booked or invalid.");
        }
        txtSeatInput.setText("");
    }

    // --- 6. MAIN METHOD (Used for direct testing) ---
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new BookingFrame().setVisible(true);
        });
    }
}