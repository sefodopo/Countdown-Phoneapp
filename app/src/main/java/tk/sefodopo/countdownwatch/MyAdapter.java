package tk.sefodopo.countdownwatch;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by zandj on 7/23/2016.
 */
public class MyAdapter implements ListAdapter {
	private Context context;
	private ArrayList<DataSetObserver> observers;
	private ArrayList<Event> events;

	public MyAdapter(Context context) {
		this.context = context;
		observers = new ArrayList<>();
		events = new ArrayList<>();
	}

	public void addEvent(Event item) {
		int i;
		for (i = 0; i < events.size(); i++) {
			if (events.get(i).getDate().compareTo(item.getDate()) == 0) {
				return;
			}
			if (events.get(i).getDate().compareTo(item.getDate()) > 0) {
				break;
			}
		}
		events.add(i, item);
		for (DataSetObserver observer: observers) {
			observer.onChanged();
		}
	}

	public void removeEvent(int item) {
		events.remove(item);
		for (DataSetObserver observer: observers) {
			observer.onChanged();
		}
	}

	public ArrayList<Event> getList() {
		return events;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public boolean isEnabled(int position) {
		return events.size() - 1 >= position;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver dataSetObserver) {
		observers.add(dataSetObserver);
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
		observers.remove(dataSetObserver);
	}

	@Override
	public int getCount() {
		return events.size();
	}

	@Override
	public Object getItem(int i) {
		if (events.size() - 1 < i) return null;
		else {
			return events.get(i);
		}
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		LayoutInflater inflator = LayoutInflater.from(context);
		view = inflator.inflate(R.layout.adapter_item, viewGroup, false);
		TextView title = (TextView) view.findViewById(R.id.title);
		TextView time = (TextView) view.findViewById(R.id.time);
		Event event = (Event) getItem(i);
		title.setText(event.getTitle());
		time.setText(event.getTime());
		return view;
	}

	@Override
	public int getItemViewType(int i) {
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean isEmpty() {
		return getCount() == 0;
	}
}
