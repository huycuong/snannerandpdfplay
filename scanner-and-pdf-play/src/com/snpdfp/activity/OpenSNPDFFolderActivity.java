package com.snpdfp.activity;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.snpdfp.utils.SNPDFArrayAdapter;
import com.snpdfp.utils.SNPDFCContstants;
import com.snpdfp.utils.SNPDFPathManager;

public class OpenSNPDFFolderActivity extends SNPDFActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_open_snpdffolder);

    // Show message for from Split options
    String message = getIntent().getStringExtra(SNPDFCContstants.TEXT);
    if (message != null) {
      Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    File root = SNPDFPathManager.getRootDirectory();
    File[] snpdfFiles = root.listFiles(new FilenameFilter() {
      @Override
      public boolean accept(File dir, String filename) {
        if (filename.toLowerCase().endsWith(".pdf") || filename.toLowerCase().endsWith(".txt"))
          return true;
        return false;
      }
    });

    if (snpdfFiles != null) {
      List<File> files = Arrays.asList(snpdfFiles);
      Collections.sort(files, new Comparator<File>() {
        @Override
        public int compare(File lhs, File rhs) {
          if (lhs.lastModified() > rhs.lastModified()) {
            return -1;
          } else if (lhs.lastModified() < rhs.lastModified()) {
            return 1;
          }

          return 0;
        }
      });

      ListView lv = (ListView) findViewById(R.id.list_snpdf_files);
      if (files.size() == 0) {
        List<String> none = new ArrayList<String>();
        none.add("NONE");
        lv.setAdapter(new ArrayAdapter<String>(this, R.layout.row, none));

      } else {
        lv.setAdapter(new SNPDFArrayAdapter(this, files));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            mainFile = new File(SNPDFPathManager.getRootDirectory(), (String) ((TextView) v.findViewById(R.id.rowtext))
                .getText());

            showIntentForPickedPDF();

          }

        });
      }

    }

  }

  private void showIntentForPickedPDF() {
    Intent filePick = new Intent(this, PickedPDFActivity.class);
    filePick.putExtra(SNPDFCContstants.FILE_URI, mainFile.getAbsolutePath());
    startActivity(filePick);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.snpdf_options_menu_folder, menu);
    return true;
  }

}
