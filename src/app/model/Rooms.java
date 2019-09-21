package app.model;

public class Rooms {
    private String rno;
    private String rtype;
    private String ravail;

    public Rooms(String rno, String rtype, String ravail) {
        this.rno = rno;
        this.rtype = rtype;
        this.ravail = ravail;
    }

    public String getRno() {
        return rno;
    }

    public void setRno(String rno) {
        this.rno = rno;
    }

    public String getRtype() {
        return rtype;
    }

    public void setRtype(String rtype) {
        this.rtype = rtype;
    }

    public String getRavail() {
        return ravail;
    }

    public void setRavail(String ravail) {
        this.ravail = ravail;
    }
}
