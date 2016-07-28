package tk.sefodopo.countdownwatch;

import java.util.Date;

/**
 * Created by Joshua on 7/27/2016.
 */
public class Item {
    private String title;
    private Date date;

    public Item() {

    }

    public Item(String title, Date date) {
        this.title = title;
        this.date = date;
    }

    @Override
    public String toString() {
        return title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }
}
