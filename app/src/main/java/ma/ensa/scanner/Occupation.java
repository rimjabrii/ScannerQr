package ma.ensa.scanner;

public class Occupation {
    private String date;
    private String namesalle;
    private String crenauhr;
    public Occupation(String date, String namesalle, String crenauhr ) {
        this.date = date;
        this.namesalle = namesalle;
        this.crenauhr = crenauhr;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNamesalle() {
        return namesalle;
    }

    public void setNamesalle(String namesalle) {
        this.namesalle = namesalle;
    }

    public String getCrenauhr() {
        return crenauhr;
    }

    public void setCrenauhr(String crenauhr) {
        this.crenauhr = crenauhr;
    }
}
