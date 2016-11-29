package com.vogella.android.testapp.rest;

import android.os.Build;
import android.telephony.TelephonyManager;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.vogella.android.testapp.rest.models.AthleteCheckRest;
import com.vogella.android.testapp.rest.models.AthleteRest;
import com.vogella.android.testapp.rest.models.CompetitionRest;
import com.vogella.android.testapp.rest.models.EventRest;
import com.vogella.android.testapp.rest.models.ReportRest;
import com.vogella.android.testapp.rest.models.UpdateTimeRest;
import retrofit.Retrofit;

/**
 * Created by Gio on 24-10-2015.
 */
@Singleton
public class Rest{

	/**
	 *
	 */
	private Api api;

	/**
	 *
	 */
	private Retrofit.Builder retrofit;

	/**
	 *
	 */
	private TelephonyManager telephonyManager;

	/**
	 *
	 */
	@Inject
	public Rest(Retrofit.Builder retrofit, TelephonyManager telephonyManager){

		this.api = null;

		this.retrofit = retrofit;

		this.telephonyManager = telephonyManager;
	}

	/**
	 *
	 * @param eventId
	 * @param token
	 * @param callback
	 */
	public void getAthletes(int eventId, String token, Callback<List<AthleteRest>> callback){
		this.api.getAthletes(eventId, token).enqueue(new Request(callback));
	}

	/**
	 *
	 * @param eventId
	 * @param from
	 * @param token
	 */
	public void getTimes(int eventId, String from, String token, Callback<UpdateTimeRest> callback){
		this.api.getTimes(eventId, from, token).enqueue(new Request(callback));
	}

	/**
	 *
	 * @param eventId
	 * @param password
	 * @param listener
	 */
	public void getEvent(int eventId, String password, Callback<EventRest> listener){
		this.api.getEvent(eventId, password, this.telephonyManager.getDeviceId(), Build.MANUFACTURER, Build.MODEL).enqueue(new Request(listener));
	}

	/**
	 *
	 * @param exclude
	 * @param listener
	 */
	public void getEvents(String exclude, Callback<List<EventRest>> listener){
		this.api.getEvents(exclude).enqueue(new Request(listener));
	}

	/**
	 *
	 * @param rfid
	 * @param timestamp
	 * @param checkpoint
	 * @param token
	 * @param listener
	 */
	public void reportTime(String rfid,  String timestamp, int checkpoint, String token, Callback<ReportRest> listener){
		this.api.reportTime(rfid, timestamp, checkpoint, token).enqueue(new Request(listener));
	}

	/**
	 *
	 * @param athlete_id
	 * @param term
	 * @return
	 */
	public void isAthleteChecked(int athlete_id, String term, Callback<AthleteCheckRest> listener){
		this.api.isAthleteChecked(athlete_id, term).enqueue(new Request(listener));
	}

	/**
	 *
	 * @param athlete_id
	 * @param term
	 * @return
	 */
	public void setAthleteChecked(int athlete_id, String term, Callback<AthleteCheckRest> listener){
		this.api.setAthleteChecked(athlete_id, term).enqueue(new Request(listener));
	}

	/**
	 *
	 * @param baseUrl
	 */
	public void setApi(String baseUrl){
		// "http://apus.uma.pt/~a2061105/v2.2/index.php/api/"
		this.retrofit.baseUrl(baseUrl);
		this.api = this.retrofit.build().create(Api.class);
	}
}