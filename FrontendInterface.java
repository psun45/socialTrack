import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Implementations should have one constructor that takes:
 * <ul>
 * <li>Scanner</li>
 * <li>System.out</li>
 * <li>BackendInterface</li>
 * </ul>
 * 
 * The frontend should only use the provided Scanner and System.out objects.
 */
public interface FrontendInterface {

    // public FrontendInterface(Scanner scanner, PrintStream out, BackendInterface
    // backend);

    /**
     * Start running the main menu of the program, until it returns false.
     */
    public void start() throws IOException;

    /**
     * Display the main menu of the program, take the user's selection, call
     * the corresponding function, and return true if the program should
     * continue running, or false if it should exit.
     * 
     * The main menu should only display the options that are currently available,
     * based on the backend's state. For example, if the backend has not loaded a
     * data file yet, the main menu should not display the options to find the
     * closest connection or show statistics about the dataset. If the backend has
     * loaded a data file, then the main menu should not display the option to load
     * a data file.
     * 
     * Options should be selected by entering the first letter of the option name,
     * case-insensitive. Sub-menus shouldn't be called if the option is not
     * available.
     * 
     * @return true if the program should continue running, or false if it has
     *         exited.
     */
    public boolean mainMenu() throws IOException;

    // sub menu functions, called by mainMenu as needed.

    /**
     * Load a data file into the backend. The backend should always accept this,
     * even if it has already loaded a data file. However, this method should
     * not be called by {@link #mainMenu()} if the backend has already loaded
     * a data file.
     */
    public void loadDataFile() throws FileNotFoundException, IOException;
    /**
     * Show statistics about the dataset. If the backend has not been loaded, this
     * sub-menu should not be called--but if it is, it should display a helpful
     * error message.
     * 
     * "statistics about the dataset that includes the number of participants
     * (nodes), the number of edges (friendships), and the average number of
     * friends"
     */
    public void showStatisticsAboutDataset();

    /**
     * Find the closest connection between two participants. Show a helpful message
     * that describes which, if any, of the participants are not in the dataset.
     * 
     * "lists the closest connection between the two, including all intermediary
     * friends"
     */
    public void findClosestConnection();

    /**
     * Prints an exit message. Exiting is handled by the main menu.
     */
    public void showExitMessage();
}
