package tk.sefodopo.countdownwatch;

import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, ActionAddFragment.ActionAddListener {

	private Adapterme adapter1;
	private Adapterme adapter2;
	private Adapterme adapter3;
	private Drawable[] imageViewActives;
	private Drawable[] imageViewWhites;
	private short currentAdapterNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle(R.string.title_main_activity);
		ListViewCompat listView = (ListViewCompat) findViewById(R.id.listView);
		adapter1 = new Adapterme(this);
		adapter2 = new Adapterme(this);
		adapter3 = new Adapterme(this);
		listView.setAdapter(adapter1);
		currentAdapterNumber = 1;
		Resources resources = getResources();
		imageViewActives = new Drawable[] {
				resources.getDrawable(R.drawable.ic_first_accent_48dp),
				resources.getDrawable(R.drawable.ic_second_accent_48dp),
				resources.getDrawable(R.drawable.ic_third_accent_48dp)
		};
		imageViewWhites = new Drawable[] {
				resources.getDrawable(R.drawable.ic_first_white_48dp),
				resources.getDrawable(R.drawable.ic_second_white_48dp),
				resources.getDrawable(R.drawable.ic_third_white_48dp)
		};
	}

	private Adapterme getCurrentAdapter() {
		switch (currentAdapterNumber) {
			case 1:
				return adapter1;
			case 2:
				return adapter2;
			case 3:
				return adapter3;
			default:
				return null;
		}
	}

	private void setCurrentAdapter(int number) {
		currentAdapterNumber = (short) number;
		ListViewCompat listView = (ListViewCompat) findViewById(R.id.listView);
		listView.setAdapter(getCurrentAdapter());
	}

	public void onClickImageView1(View view) {
		onClickAllImageViews(view, 1);
	}

	public void onClickImageView2(View view) {
		onClickAllImageViews(view, 2);
	}

	public void onClickImageView3(View view) {
		onClickAllImageViews(view, 3);
	}

	private void onClickAllImageViews(View imageViewNew, int i) {
		if (currentAdapterNumber != i) {
			ImageView imageViewOld = getImageView(currentAdapterNumber);
			setImageViewActive(imageViewOld, currentAdapterNumber, false);
			setImageViewActive((ImageView) imageViewNew, i, true);
			setCurrentAdapter(i);
		}
	}

	private void setImageViewActive(ImageView view, int i, boolean bool) {
		if (bool) {
			view.setImageDrawable(imageViewActives[i - 1]);
		} else {
			view.setImageDrawable(imageViewWhites[i - 1]);
		}
	}

	@Nullable
	private ImageView getImageView(int i) {
		switch (i) {
			case 1:
				return (ImageView) findViewById(R.id.imageView_first);
			case 2:
				return (ImageView) findViewById(R.id.imageView_second);
			case 3:
				return (ImageView) findViewById(R.id.imageView_third);
			default:
				return null;
		}
	}

	public void onClickMore(View view) {
		PopupMenu popup = new PopupMenu(this, view);
		popup.setOnMenuItemClickListener(this);
		popup.inflate(R.menu.activity_main);
		popup.show();
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {

	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_add:
				new ActionAddFragment().show(getFragmentManager(), "action_add_fragment_main_" + currentAdapterNumber);
				return true;
			case R.id.action_settings:
				return true;
			default:
				return false;
		}
	}
}
