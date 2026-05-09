
import java.util.*;

class Movie {
    String title;
    String genre;
    double rating;
    int year;
    String watchedDate;

    public Movie(String title, String genre, double rating, int year) {
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.year = year;
        this.watchedDate = "Not Watched";
    }

    // Comparator for rating
    public static Comparator<Movie> ratingComparator =
            (a, b) -> Double.compare(b.rating, a.rating);

    // Comparator for year
    public static Comparator<Movie> yearComparator =
            (a, b) -> b.year - a.year;

    @Override
    public String toString() {
        return title + " | " + genre + " | Rating: " + rating +
                " | Year: " + year +
                " | Watched: " + watchedDate;
    }
}

public class MovieSystem {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Movie> history = new ArrayList<>();
    static ArrayList<Movie> catalog = new ArrayList<>();

    public static void main(String[] args) {
        catalog.add(new Movie("Inception", "Sci-Fi", 8.8, 2010));
        catalog.add(new Movie("Interstellar", "Sci-Fi", 8.6, 2014));
        // Kannada
        catalog.add(new Movie("Kantara", "Action", 8.3, 2022));
        catalog.add(new Movie("KGF Chapter 1", "Action", 8.2, 2018));
        catalog.add(new Movie("KGF Chapter 2", "Action", 8.3, 2022));

        // Tamil
        catalog.add(new Movie("Vikram", "Action", 8.3, 2022));
        catalog.add(new Movie("Kaithi", "Action", 8.5, 2019));
        catalog.add(new Movie("Thuppakki", "Action", 8.4, 2012));
        catalog.add(new Movie("Doctor", "Comedy", 7.4, 2021));

        // Telugu
        catalog.add(new Movie("Pushpa: The Rise", "Action", 7.6, 2021));
        catalog.add(new Movie("RRR", "Action", 7.9, 2022));
        catalog.add(new Movie("Baahubali 2", "Action", 8.2, 2017));

        int choice;

        do {
            System.out.println("\n===== Movie Streaming Watch History System =====");
            System.out.println("1. Watch a Movie");
            System.out.println("2. Show Watch History");
            System.out.println("3. Sort by Rating");
            System.out.println("4. Sort by Year");
            System.out.println("5. Show Movies with Rating > 8");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    watchMovie();
                    break;

                case 2:
                    display(history);
                    break;

                case 3:
                    ArrayList<Movie> temp1 = new ArrayList<>(history);
                    temp1.sort(Movie.ratingComparator);
                    display(temp1);
                    break;

                case 4:
                    ArrayList<Movie> temp2 = new ArrayList<>(history);
                    temp2.sort(Movie.yearComparator);
                    display(temp2);
                    break;

                case 5:
                    filterMovies();
                    break;

                case 6:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 6);
    }

   
    static void watchMovie() {

        System.out.println("\nAvailable Movies:");

        for (int i = 0; i < catalog.size(); i++) {
            System.out.println((i + 1) + ". " + catalog.get(i));
        }

        System.out.print("Select movie number: ");
        int choice = sc.nextInt();

        if (choice >= 1 && choice <= catalog.size()) {

            Movie selected = catalog.get(choice - 1);

            String dateTime = java.time.LocalDateTime.now()
                    .format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

            // Update catalog
            selected.watchedDate = dateTime;

            // Remove if already exists
            history.removeIf(m -> m.title.equals(selected.title));

            // Create new updated object
            Movie newMovie = new Movie(
                    selected.title,
                    selected.genre,
                    selected.rating,
                    selected.year
            );

            newMovie.watchedDate = dateTime;

            
            history.add(0, newMovie);

            System.out.println(selected.title + " watched on " + dateTime);

        } else {
            System.out.println("Invalid selection!");
        }
    }

    static void display(List<Movie> list) {

        if (list.isEmpty()) {
            System.out.println("No movies in history.");
            return;
        }

        for (Movie m : list) {
            System.out.println(m);
        }
    }

    static void filterMovies() {

        boolean found = false;

        for (Movie m : history) {
            if (m.rating > 8.0) {
                System.out.println(m);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No movies found.");
        }
    }
}