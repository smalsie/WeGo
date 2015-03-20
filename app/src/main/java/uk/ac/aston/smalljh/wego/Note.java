package uk.ac.aston.smalljh.wego;

/**
 * Created by joshuahugh on 19/03/15.
 */
public class Note {
    private String title;
    private String note;

    public Note(String title, String note) {
        this.title = title;
        this.note = note;
    }

    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
    }
}
