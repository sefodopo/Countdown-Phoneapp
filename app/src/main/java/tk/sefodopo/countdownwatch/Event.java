package tk.sefodopo.countdownwatch;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zandj on 8/24/2016.
 */
public class Event implements Parcelable {
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

	public Event(Parcel in) {
		title = in.readString();
		date = new Date(in.readInt());
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(title);
		parcel.writeLong(date.getTime());
	}
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public Event createFromParcel(Parcel in) {
			return new Event(in);
		}
		public Event[] newArray(int size) {
			return new Event[size];
		}
	};
}
