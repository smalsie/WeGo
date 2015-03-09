package uk.ac.aston.smalljh.wego.utils;

/**
 * Created by joshuahugh on 09/03/15.
 */
public class PlaceDetail {

    //@Key
    private GooglePlace result;

    public GooglePlace getResult() {
        return result;
    }

    public void setResult(GooglePlace result) {
        this.result = result;
    }

    @Override
    public String toString() {
        if (result!=null) {
            return result.getName();
        }
        return super.toString();
    }
}
