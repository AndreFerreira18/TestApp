package com.vogella.android.testapp.rest;

import java.util.Date;
import java.util.List;

import com.vogella.android.testapp.rest.models.AthleteCheckRest;
import com.vogella.android.testapp.rest.models.AthleteRest;
import com.vogella.android.testapp.rest.models.EventRest;
import com.vogella.android.testapp.rest.models.ReportRest;
import com.vogella.android.testapp.rest.models.UpdateTimeRest;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Gio on 24-10-2015.
 */
public interface Api {

    /**
     *
     * @param eventId
     * @param token
     * @return
     */
    @GET("athletes/{event_id}")
    Call<Response<List<AthleteRest>>> getAthletes(@Path("event_id") int eventId, @Query("token") String token);

    /**
     *
     * @return
     */
    @GET("events")
    Call<Response<List<EventRest>>> getEvents(@Query("exclude") String exclude);

    /**
     *
     * @param rfid
     * @param timestamp
     * @param checkpoint
     * @return
     */
    @FormUrlEncoded
    @POST("report")
    Call<Response<ReportRest>> reportTime(@Field("rfid") String rfid,  @Field("timestamp") String timestamp, @Field("checkpoint") int checkpoint, @Field("token") String token);

    /**
     *
     * @return
     */
    @FormUrlEncoded
    @POST("event/{id}")
    Call<Response<EventRest>> getEvent(@Path("id") int id, @Field("password") String password, @Field("imei") String imei, @Field("manufacturer") String manufacturer, @Field("model") String model);

    /**
     *
     * @param event_id
     * @param from
     * @param token
     * @return
     */
    @GET("times/{event}")
    Call<Response<UpdateTimeRest>> getTimes(@Path("event") int event_id, @Query("from") String from,  @Query("token") String token);

    /**
     *
     * @param athlete_id
     * @param term
     * @return
     */
    @GET("check/{athlete}/{term}")
    Call<Response<AthleteCheckRest>> isAthleteChecked(@Path("athlete") int athlete_id, @Path("term") String term);

    /**
     *
     * @param athlete_id
     * @param term
     * @return
     */
    @GET("mark/{athlete}/{term}")
    Call<Response<AthleteCheckRest>> setAthleteChecked(@Path("athlete") int athlete_id, @Path("term") String term);
}