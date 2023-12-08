import java.util.NoSuchElementException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;

public class Frontend implements FrontendInterface {
    private Scanner scanner;
    private Backend backend = new Backend();

    public Frontend(Scanner scanner, Backend backend) {
        this.scanner = scanner;
        this.backend = backend;
    }
    /**
     * Start running the main menu of the program, until it returns false.
     */
    @Override
    public void start() throws IOException {
        boolean status = true;
        while (status) {
            status = mainMenu();
        }
    }
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
    @Override
    public boolean mainMenu() throws IOException {
        System.out.println("Main Menu:");
        System.out.println("1 - Load Data File");
        System.out.println("2 - Show Statistics");
        System.out.println("3 - Find Closest Connection");
        System.out.println("4 - Exit");
        System.out.print("Enter your command: ");
        //this asks for the user's choice
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                loadDataFile();
                break;
            case "2":
                showStatisticsAboutDataset();
                break;
            case "3":
                findClosestConnection();
                break;
            case "4":
                showExitMessage();
                return false;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        return true;
    }
    /**
     * Load a data file into the backend. The backend should always accept this,
     * even if it has already loaded a data file. However, this method should
     * not be called by {@link #mainMenu()} if the backend has already loaded
     * a data file.
     */
    @Override
    public void loadDataFile() throws FileNotFoundException, IOException {
        System.out.print("Enter valid data file path: ");
        String filepath = scanner.nextLine();
        //checks if the filepath is valid
        if(filepath == null || filepath.isEmpty()) {
            System.out.println("Please enter a valid file path.");
            return;
        }else if(!filepath.endsWith(".dot")) {
            System.out.println("Please enter a valid .dot file.");
            return;
        }else {
            //reads the file
            if (backend.readDOTFile(filepath)) {
		System.out.println("Data file loaded.");
            } else {
                System.out.println("Error reading file. Please try again.");
            }
        }

    }

    /**
     * Show statistics about the dataset. If the backend has not been loaded, this
     * sub-menu should not be called--but if it is, it should display a helpful
     * error message.
     *
     * "statistics about the dataset that includes the number of participants
     * (nodes), the number of edges (friendships), and the average number of
     * friends"
     */
    @Override
    public void showStatisticsAboutDataset() {
        //uses the backend to get the stats
        String stats = backend.getAppStats();
        System.out.println(stats);
    }

    /**
     * Find the closest connection between two participants. Show a helpful message
     * that describes which, if any, of the participants are not in the dataset.
     *
     * "lists the closest connection between the two, including all intermediary
     * friends"
     */
    @Override
    public void findClosestConnection() {
        System.out.print("Enter the first person's name: ");
        //this asks for the first user
        String user1 = scanner.nextLine();
        System.out.print("Enter the second person's name: ");
        //this asks for the second user
        String user2 = scanner.nextLine();
	ShortestPathInterface<String> connectionPath = null;
	try {
          connectionPath = backend.getClosestConnection(user1, user2);
        } catch (NoSuchElementException e) {
	  System.out.println(e.getMessage());
	}
	if (connectionPath == null) {
            System.out.println("No connection found between the two users.");
        } else {
            System.out.println("Closest connection path: " + connectionPath.getFriendPath());
        }
    }

    /**
     * Prints an exit message. Exiting is handled by the main menu.
     */
    @Override
    public void showExitMessage() {
        System.out.println("Exiting the application. Goodbye!");
    }
}
