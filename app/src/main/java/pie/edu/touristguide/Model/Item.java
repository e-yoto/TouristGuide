package pie.edu.touristguide.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Emmanuel
 * Item objects
 */
public class Item implements Parcelable {
    private String title;
    private Double weight;
    private Boolean liquid;
    private int isLiquid;
    private String type;

    public Item(){

    }

    public Item(String title, double weight, Boolean liquid) {
        this.title = title;
        this.weight = weight;
        this.liquid = liquid;
    }

    public Item(String title, double weight, int isLiquid, String type) {
        this.title = title;
        this.weight = weight;
        this.isLiquid = isLiquid;
        this.type = type;
    }

    protected Item(Parcel in) {
        title = in.readString();
        if (in.readByte() == 0) {
            weight = null;
        } else {
            weight = in.readDouble();
        }
        byte tmpLiquid = in.readByte();
        liquid = tmpLiquid == 0 ? null : tmpLiquid == 1;
        isLiquid = in.readInt();
        type = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIsLiquid() {
        return isLiquid;
    }

    public void setIsLiquid(int isLiquid) {
        this.isLiquid = isLiquid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Boolean getLiquid() {
        return liquid;
    }

    public void setLiquid(Boolean liquid) {
        this.liquid = liquid;
    }

    @Override
    public String toString() {
        return "Item{" +
                "title='" + title + '\'' +
                ", weight='" + weight + '\'' +
                ", liquid='" + liquid + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        if (weight == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(weight);
        }
        dest.writeByte((byte) (liquid == null ? 0 : liquid ? 1 : 2));
        dest.writeInt(isLiquid);
        dest.writeString(type);
    }
}
