package app.model;

public class Rooms {
    private String no;
    private String type;
    private float price;
    private String avail;

    public Rooms(String no, String type, String price, String avail) {
        this.no = no;
        this.type = type;
        this.price = Float.parseFloat(price);
        this.avail = avail;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
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
