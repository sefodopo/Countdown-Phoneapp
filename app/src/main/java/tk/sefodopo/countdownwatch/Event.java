package tk.sefodopo.countdownwatch;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zandj on 8/24/2016.
 */
public class Event {
	private String title;
	private Date date;

	public Event(String title, Date date) {
		this.title = title;
		this.date = date;
	}

	public int getSeconds() {
		return (int) (date.getTime() / 1000);
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public String getTime() {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm MMM dd, yyyy");
		return df.format(date);
	}

	public Date getDate() {
		return date;
	}
}
