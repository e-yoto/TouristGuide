package pie.edu.touristguide.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Memory {
    private String album;
    private byte[] image;
    private String description;
    private int id;

    public Memory() {
        //empty constructor
    }

    public Memory(String album, byte[] imageArray, String description) {
        this.album = album;
        image = imageArray;
        this.description = description;
    }

    /**
     * Getters and setters.
     */
    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get image that is saved as a byte array into a bitmap.
     */
    public Bitmap getByteArrayToBitmap(){
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
