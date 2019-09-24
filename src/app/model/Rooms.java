package app.model;

public class Rooms {
    private int no;
    private String type;
    private float price;
    private String avail;
    private String dummy;

    public Rooms(int no, String type, float price, String avail) {
        this.no = no;
        this.type = type;
        this.price = price;
        this.avail = avail;
        this.dummy = "dummy";
    }

    public String getDummy() {
        return dummy;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getAvail() {
        return avail;
    }

    public void setAvail(String avail) {
        this.avail = avail;
    }
}
