package uk.ac.aston.smalljh.wego;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joshuahugh on 09/11/14.
 */
public class TripItem {

    private String title, location;
    private List<String> companions;
    private String startDate, endDate;

    private List<Note> notes;

    private int pic;

    public TripItem(String title, String location, String startDate, String endDate) {
        this.title = title;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;

        notes = new ArrayList<Note>();
        companions = new ArrayList<String>();

    }

    public String getLocation() {
        return location;
    }

    public List<String> getCompanions() {

       return companions;
    }

    public String getTitle() {
        return title;
    }

    public int getPic() { return pic; }

    public void addCompanion(String companion) {
        companions.add(companion);
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public String getEndDate() {
        return endDate;
    }

    public String getStartDate() {
        return startDate;
    }
}
