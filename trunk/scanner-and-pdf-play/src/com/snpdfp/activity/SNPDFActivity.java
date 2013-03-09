package com.snpdfp.activity;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.snpdfp.menu.About;
import com.snpdfp.menu.FAQActivity;
import com.snpdfp.utils.SAPDFCContstants;

public class SNPDFActivity extends Activity {

	protected File pdffile = null;

	/** Called when the user clicks the SCAN button */
	public void openPDF(View view) {
		Uri path = Uri.fromFile(pdffile);
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(path, "application/pdf");
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		try {
			startActivity(Intent.createChooser(intent, "Open PDF with..."));
		} catch (ActivityNotFoundException e) {
			Toast.makeText(this, "No Application Available to View PDF",
					Toast.LENGTH_SHORT).show();
		}
	}

	public void sharePDF(View view) {
		Uri path = Uri.fromFile(pdffile);
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("application/pdf");
		String shareBody = "Sharing file:" + pdffile.getName();
		intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
		intent.putExtra(android.content.Intent.EXTRA_STREAM, path);
		try {
			startActivity(Intent.createChooser(intent, "Share via..."));
		} catch (ActivityNotFoundException e) {
			Toast.makeText(this, "No Application Available to share PDF",
					Toast.LENGTH_SHORT).show();
		}
	}

	public void protectPDF(View view) {
		Intent pdfintent = new Intent(this, ProtectPDFActivity.class);
		pdfintent
				.putExtra(SAPDFCContstants.FILE_URI, pdffile.getAbsolutePath());
		startActivity(pdfintent);
	}

	public void deletePDF(View view) {
		getAlertDialog()
				.setTitle("Delete PDF?")
				.setMessage("Are you sure to delete file: " + pdffile.getName())
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						delete();
					}

				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).show();

	}

	private void delete() {
		pdffile.delete();
		startActivity(new Intent(this, MainActivity.class));
	}

	public android.app.AlertDialog.Builder getAlertDialog() {
		return new AlertDialog.Builder(new ContextThemeWrapper(this,
				android.R.style.Theme_Dialog));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.snpdf_options_menu, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.about:
			startActivity(new Intent(this, About.class));
			return true;
		case R.id.sndpf_location:
			startActivity(new Intent(this, OpenSNPDFFolderActivity.class));
			return true;
		case R.id.faq:
			startActivity(new Intent(this, FAQActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
		finish();
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
}
