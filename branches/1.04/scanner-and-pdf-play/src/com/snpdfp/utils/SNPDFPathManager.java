package com.snpdfp.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.os.Environment;

public class SNPDFPathManager {

	private static final String DATE_FORMAT = "yyyyMMddHHmmss";

	public static File getSavePDFPath(String fileName) {
		// Environment
		// .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
		File file = new File(Environment.getExternalStorageDirectory(), "snpdf");

		if (!file.exists()) {
			file.mkdirs();
		}

		file = getFile(file, fileName);

		return file;
	}

	private static File getFile(File file, String fileName) {
		return new File(
				file,
				fileName.substring(0, fileName.lastIndexOf("."))
						+ "_SNPDF_"
						+ new SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
								.format(new Date(System.currentTimeMillis()))
						+ fileName.substring(fileName.lastIndexOf(".")));
	}

	public static File getSavePDFPathWOTimestamp(String fileName) {
		File file = new File(Environment.getExternalStorageDirectory(), "snpdf");

		if (!file.exists()) {
			file.mkdirs();
		}

		file = new File(file, fileName);

		return file;
	}

	public static File getRootDirectory() {
		File dir = new File(Environment.getExternalStorageDirectory(), "snpdf");
		if (!dir.exists()) {
			dir.mkdirs();
		}

		return dir;
	}

	public static File getSNPDFPicFile() {
		return getSavePDFPathWOTimestamp("PIC.jpg");
	}

	public static File getTextFileForPDF(File pdffile) {
		File file = new File(Environment.getExternalStorageDirectory(), "snpdf");

		String pdfFileName = pdffile.getName();
		if (!file.exists()) {
			file.mkdirs();
		}

		file = getFile(file, getFileNameWithoutExtn(pdfFileName) + ".txt");

		return file;
	}

	public static String getFileNameWithoutExtn(String filename) {
		return filename.substring(0, filename.lastIndexOf("."));
	}

}