package com.snpdfp.activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SNPDFSettings extends SNPDFActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_snpdfsettings);

		// Select page size
		mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		String pageSize = mPrefs.getString(snpdfPageSize, "A4");

		TextView textView = (TextView) findViewById(R.id.pageSize);
		textView.setText(pageSize);

		textView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectPageSize(v);
			}
		});

	}

	public void save(View view) {
		mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

		TextView textView = (TextView) findViewById(R.id.pageSize);
		String size = textView.getText().toString();
		setPageSize(size);

		SharedPreferences.Editor editor = mPrefs.edit();
		editor.putString(snpdfPageSize, size);
		editor.commit(); // Very important to save the preference

		Toast.makeText(this, "Settings saved!", Toast.LENGTH_SHORT).show();

		onBackPressed();
	}

	public void selectPageSize(View view) {

		final TextView textView = (TextView) findViewById(R.id.pageSize);

		final TypedArray pageSizes = getResources().obtainTypedArray(
				R.array.page_size_array);

		getAlertDialog()
				.setTitle("Select the page size")
				.setItems(R.array.page_size_array,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								textView.setText(pageSizes.getText(which));
								dialog.dismiss();
							}
						}).show();
	}

	public void cancel(View view) {
		onBackPressed();
	}
}