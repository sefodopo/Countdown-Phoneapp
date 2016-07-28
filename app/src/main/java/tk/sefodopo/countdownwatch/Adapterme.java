package tk.sefodopo.countdownwatch;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by zandj on 7/23/2016.
 */
public class Adapterme implements ListAdapter {
	private Context context;
	private ArrayList<DataSetObserver> observers;
	// TODO call observers
	private ArrayList<Item> items;

	public Adapterme(Context context) {
		this.context = context;
		observers = new ArrayList<>();
		items = new ArrayList<>();
	}

	public void addItem(Item item) {
		items.add(item);
		for (DataSetObserver observer: observers
			 ) {
			observer.onChanged();
		}
	}

	public void removeItem(Item item) {
		items.remove(item);
		for (DataSetObserver observer: observers)
		{
			observer.onChanged();
		}
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public boolean isEnabled(int position) {
		return items.size() - 1 >= position;
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
		return items.size();
	}

	@Override
	public Object getItem(int i) {
		if (items.size() - 1 < i) return null;
		else {
			return items.get(i);
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
		TextView date = (TextView) view.findViewById(R.id.date);
		TextView time = (TextView) view.findViewById(R.id.time);
		Item item = (Item) getItem(i);
		title.setText(item.getTitle());
		date.setText(item.getDate().toString());
		time.setText("");
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
