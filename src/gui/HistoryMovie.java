package gui;

import java.util.Comparator;

public class HistoryMovie {

    String title;
    String genre;
    double rating;
    int year;
    String watchedTime;
    String imagePath;

    public HistoryMovie(
            String title,
            String genre,
            double rating,
            int year,
            String watchedTime,
            String imagePath
    ) {

        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.year = year;
        this.watchedTime = watchedTime;
        this.imagePath = imagePath;
    }

    // Comparator for Rating
    public static Comparator<HistoryMovie> ratingComparator =
            (a, b) -> Double.compare(b.rating, a.rating);

    // Comparator for Year
    public static Comparator<HistoryMovie> yearComparator =
            (a, b) -> b.year - a.year;

    @Override
    public String toString() {

        return "<html>" +
                "Title: " + title + "<br>" +
                "Genre: " + genre + "<br>" +
                "Rating: " + rating + "<br>" +
                "Year: " + year + "<br>" +
                "Watched: " + watchedTime +
                "</html>";
    }
}