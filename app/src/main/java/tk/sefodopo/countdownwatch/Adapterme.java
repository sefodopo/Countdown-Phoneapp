package tk.sefodopo.countdownwatch;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import java.util.ArrayList;

/**
 * Created by zandj on 7/23/2016.
 */
public class Adapterme implements ListAdapter {
	private Context context;
	private ArrayList<DataSetObserver> observers;
	// TODO call observers

	public class Item {

	}

	public Adapterme(Context context) {
		this.context = context;
		observers = new ArrayList<>();
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public boolean isEnabled(int position) {
		// TODO check if is enabled
		return false;
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
		// TODO count object in an array for the count
		return 0;
	}

	@Override
	public Object getItem(int i) {
		// TODO get object from array for getItem
		return null;
	}

	@Override
	public long getItemId(int i) {
		// TODO look up what this is supposed to do
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO look this up to find out what to do with this.
		return false;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		// TODO look up and create a view to be shown probably
		return null;
	}

	@Override
	public int getItemViewType(int i) {
		// TODO look this up and set it up properly
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		// TODO look this up and set it up properly.
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return getCount() == 0;
	}
}
