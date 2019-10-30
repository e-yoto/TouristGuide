package pie.edu.touristguide.Model;

public class Album {
    private String name;

    public Album(){
        //empty contructor
    }

    public Album(String albumName) {
        name = albumName;
    }

    /**
     * Getter for album name
     * @return album's name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for album name
     * @param name album's new name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Album: " + this.name;
    }
}
