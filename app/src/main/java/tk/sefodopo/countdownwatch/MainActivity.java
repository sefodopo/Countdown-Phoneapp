package tk.sefodopo.countdownwatch;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;

import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, ActionAddFragment.ActionAddListener {

	private Adapterme adapter1;
	private Adapterme adapter2;
	private Adapterme adapter3;
	private Drawable[] imageViewActives;
	private Drawable[] imageViewWhites;
	private short currentAdapterNumber;
	private static final String STATE_LIST = "State list";

	final UUID appUuid = UUID.fromString("cedd426f-f98b-4bce-94e1-4a4f38668378");
	final int AppKeyLayersEnabled = 0;
	final int AppKeyDateLayer = 1;
	final int AppKeyFirstEventTitle = 2;
	final int AppKeyFirstEventTime = 3;
	final int AppKeySecondEventTitle = 4;
	final int AppKeySecondEventTime = 5;
	final int AppKeyThirdEventTitle = 6;
	final int AppKeyThirdEventTime = 7;
	final int AppKeyEventsEnabled = 8;
	final int AppKeyStartFontSize = 9;
	final int AppKeyFirstEventCount = 16;
	final int AppKeySecondEventCount = 17;
	final int AppKeyThirdEventCount = 18;
	final int AppKeyStartFirstEvents = 100;
	final int AppKeyStartSecondEvents = 140;
	final int AppKeyStartThirdEvents = 160;

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
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
				AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
				builder.setMessage("Remove?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int ii) {
						getCurrentAdapter().removeEvent(i);
					}
				}).setNegativeButton("No", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						dialogInterface.cancel();
					}
				}).show();
			}
		});
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
		load();
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
		EditText title = (EditText) dialog.getDialog().findViewById(R.id.title);
		EditText time = (EditText) dialog.getDialog().findViewById(R.id.time);
		EditText date = (EditText) dialog.getDialog().findViewById(R.id.date);
		Adapterme adapter = getCurrentAdapter();
		Date date1;
		SimpleDateFormat df = new SimpleDateFormat("HH:mmMM/dd/yyyy");
		try {
			date1 = df.parse(time.getText().toString() + date.getText().toString());
			adapter.addEvent(new Event(title.getText().toString(), date1));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void onClickDinner() {
//		PebbleDictionary dict = new PebbleDictionary();
//		dict.addUint8(AppKeyLayersEnabled, (byte) 3);
//		dict.addUint8(AppKeyDateLayer, (byte) 2);
//		dict.addUint8(AppKeyFirstEventTime, (byte) 1);
//		dict.addUint8(AppKeyFirstEventTitle, (byte) 0);
//		dict.addUint8(AppKeyEventsEnabled, (byte) 1);
//		dict.addUint8(AppKeyStartFontSize, (byte) 1);
//		dict.addUint8(AppKeyStartFontSize + 1, (byte) 2);
//		dict.addUint8(AppKeyStartFontSize + 2, (byte) 0);
//		dict.addUint8(AppKeyFirstEventCount, (byte) event.length);
//		int temp = 0;
//		for (int i = 0; i < event.length; i++) {
//			dict.addString(AppKeyStartFirstEvents + temp, event[i].title);
//			temp++;
//			dict.addUint32(AppKeyStartFirstEvents + temp, (int) event[i].seconds);
//			temp++;
//		}
//		PebbleKit.sendDataToPebble(getApplicationContext(), appUuid, dict);
	}

	private void send() {
		PebbleDictionary dict = new PebbleDictionary();
		dict.addUint8(AppKeyLayersEnabled, (byte)3);
		dict.addUint8(AppKeyDateLayer, (byte)2);
		dict.addUint8(AppKeyFirstEventTime, (byte)1);
		dict.addUint8(AppKeyFirstEventTitle, (byte)0);
		dict.addUint8(AppKeyEventsEnabled, (byte)1);
		dict.addUint8(AppKeyStartFontSize, (byte)1);
		dict.addUint8(AppKeyStartFontSize + 1, (byte)2);
		dict.addUint8(AppKeyStartFontSize + 2, (byte)0);
		dict.addUint8(AppKeyFirstEventCount, (byte) getCurrentAdapter().getCount());
		int temp = 0;
		for (int i = 0; i < 20 && i < getCurrentAdapter().getCount(); i++) {
			dict.addString(AppKeyStartFirstEvents + temp, ((Event)getCurrentAdapter().getItem(i)).getTitle());
			temp++;
			dict.addUint32(AppKeyStartFirstEvents + temp, ((Event)getCurrentAdapter().getItem(i)).getSeconds());
			temp++;
		}
		PebbleKit.sendDataToPebble(getApplicationContext(), appUuid, dict);
	}

	private void save() {
		Document dom;
		Element e = null, ee = null;

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.newDocument();
			Element rootEle = dom.createElement("root");
			for (int i = 1; i < 4; i++) {
				e = dom.createElement("adapter");
				ArrayList<Event> ale;
				switch (i) {
					case 1:
						ale = adapter1.getList();
						break;
					case 2:
						ale = adapter2.getList();
						break;
					case 3:
						ale = adapter3.getList();
						break;
					default:
						return;
				}
				for (Event event : ale) {
					ee = dom.createElement("event");
					Element eee = dom.createElement("title");
					eee.setTextContent(event.getTitle());
					ee.appendChild(eee);
					eee = dom.createElement("time");
					SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
					eee.setTextContent(df.format(event.getDate()));
					ee.appendChild(eee);
					e.appendChild(ee);
				}
				rootEle.appendChild(e);
			}
			dom.appendChild(rootEle);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(dom);
			StreamResult result = new StreamResult(new File(Environment.getExternalStorageDirectory() + "/mainadapter.xml"));
			transformer.transform(source, result);
		} catch (Exception ec) {
			ec.printStackTrace();
		}
	}

	private void load() {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new File(Environment.getExternalStorageDirectory() + "/mainadapter.xml"));
			doc.getDocumentElement().normalize();
			NodeList nl = doc.getElementsByTagName("adapter");
			for (int i = 0; i < nl.getLength() && i < 4; i++) {
				currentAdapterNumber = (short)(i + 1);
				Element eAdapter = (Element) nl.item(i);
				NodeList nlEvents = eAdapter.getElementsByTagName("event");
				for (int ii = 0; ii < nlEvents.getLength(); ii++) {
					Element eEvent = (Element) nlEvents.item(ii);
					NodeList nlT = eEvent.getElementsByTagName("title");
					Element eT = (Element) nlT.item(0);
					nlT = eEvent.getElementsByTagName("time");
					Element eTT = (Element) nlT.item(0);
					SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
					getCurrentAdapter().addEvent(new Event(eT.getTextContent(), df.parse(eTT.getTextContent())));
				}
			}
			currentAdapterNumber = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_add:
				new ActionAddFragment().show(getFragmentManager(), "action_add_fragment_main_" + currentAdapterNumber);
				return true;
			case R.id.action_settings:
				return true;
			case R.id.action_send:
				send();
				return true;
			case R.id.action_save:
				save();
				return true;
			case R.id.action_load:
				load();
				return true;
			default:
				return false;
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		//outState.putParcelableArrayList(STATE_LIST, getCurrentAdapter().getList());
	}
}
