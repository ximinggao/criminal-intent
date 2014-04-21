package com.bignerdranch.android.criminalintent;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;

public class CriminalIntentJSONSerializer {
	
	private Context mContext;
	private String mFileName;
	
	public CriminalIntentJSONSerializer(Context context, String fileName) {
		super();
		mContext = context;
		mFileName = fileName;
	}
	
	public void saveCrimes(List<Crime> crimes) throws IOException, JSONException  {
		JSONArray array = new JSONArray();
		for (Crime c : crimes) {
			array.put(c.toJSON());
		}
		
		Writer writer = null;
		try {
			writer = new OutputStreamWriter(mContext.openFileOutput(mFileName, Context.MODE_PRIVATE));
			writer.write(array.toString());
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
	
	public List<Crime> loadCrimes() throws IOException, JSONException {
		List<Crime> crimes = new ArrayList<Crime>();
		BufferedReader reader = null;
		
		try {
			InputStream in = mContext.openFileInput(mFileName);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				jsonString.append(line);
			}
			
			JSONArray array = (JSONArray)new JSONTokener(jsonString.toString()).nextValue();
			for (int i = 0; i < array.length(); i++) {
				crimes.add(new Crime(array.getJSONObject(i)));
			}
		} catch (FileNotFoundException e) {
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		
		return crimes;
	}

}
