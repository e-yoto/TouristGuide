package pie.edu.touristguide.Model;


import java.util.concurrent.ExecutionException;

import pie.edu.touristguide.Controller.APICall.GetAdjustedTimeTask;

/**
 * @author Botao Yu
 * Contact POJO object
 */
public class Contact {

    private String name;        //Name of contact
    private long phoneNumber;    //phone number of contact
    private String location;
    private long rawOffSet;
    private byte[] bitMapBytes;

    public Contact(String name, long phoneNumber, String location, long rawOffSet, byte[] bitMapBytes) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.rawOffSet = rawOffSet;
        this.bitMapBytes = bitMapBytes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getRawOffSet() {
        return rawOffSet;
    }

    public void setRawOffSet(long rawOffSet) {
        this.rawOffSet = rawOffSet;
    }

    public byte[] getBitMapBytes() {
        return bitMapBytes;
    }

    public void setBitMapBytes(byte[] bitMapBytes) {
        this.bitMapBytes = bitMapBytes;
    }
}

