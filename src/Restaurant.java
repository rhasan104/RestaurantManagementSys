public class Restaurant {

    public String name;
    public int id;
    public String location;
    public String genre;
    public double distance;


    public Restaurant(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public Restaurant(int id, String name, String location, String genre, double distance) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.genre = genre;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

}
