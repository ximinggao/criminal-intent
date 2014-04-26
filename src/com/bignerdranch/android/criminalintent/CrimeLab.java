package com.bignerdranch.android.criminalintent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.util.Log;

public class CrimeLab {
	private static final String TAG = "CrimeLab";
	private static final String FILE_NAME = "crimes.json";
	private static CrimeLab sCrimeLab;
	private Context mAppContext;
	private List<Crime> mCrimes;
	private CriminalIntentJSONSerializer mSerializer;
	
	private CrimeLab(Context appContext) {
		mAppContext = appContext;
		mCrimes = new ArrayList<Crime>();
		
		mSerializer = new CriminalIntentJSONSerializer(mAppContext, FILE_NAME);
		try {
			mCrimes = mSerializer.loadCrimes();
		} catch (Exception e) {
			mCrimes = new ArrayList<>();
			Log.e(TAG, "Error loading crimes: ", e);
		}
	}
	
	public static CrimeLab get(Context c) {
		if (sCrimeLab == null) {
			sCrimeLab = new CrimeLab(c.getApplicationContext());
		}
		return sCrimeLab;
	}
	
	public void addCrime(Crime c) {
		mCrimes.add(c);
	}
	
	public void deleteCrime(Crime c) {
		mCrimes.remove(c);
	}
	
	public List<Crime> getCrimes() {
		return mCrimes;
	}
	
	public Crime getCrime(UUID id) {
		if (id != null) {
			for (Crime c : mCrimes) {
				if (id.equals(c.getId()))
					return c;
			}
		}
		return null;
	}
	
	public boolean saveCrimes() {
		try {
			mSerializer.saveCrimes(mCrimes);
			Log.d(TAG, "Crimes saved to file");
			return true;
		} catch (Exception e) {
			Log.e(TAG, "Error saving crimes: ", e);
			return false;
		}
	}
}
