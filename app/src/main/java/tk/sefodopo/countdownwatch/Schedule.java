package tk.sefodopo.countdownwatch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class Schedule extends AppCompatActivity {

	private MyAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule);
		setTitle("Schedule");
		ListView listView = (ListView) findViewById(R.id.listView);
		adapter = new MyAdapter(this);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
				builder.setMessage("Remove?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						adapter.removeEvent(i);
					}
				}).setNegativeButton("No", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						dialogInterface.cancel();
					}
				}).show();
			}
		});
	}
}
