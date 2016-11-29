package com.vogella.android.testapp.rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Gio on 24-10-2015.
 */
public class Response<M> {

	/**
	 *
	 */
    @SerializedName("data")
	@Expose
	private M data;

	/**
	 *
	 */
	 @SerializedName("error")
	 @Expose
	private Error error;

	/**
	 *
	 * @return
	 */
	public M getData(){
		return this.data;
	}

	public boolean isError(){
	    return this.error.getCode() > 0;
	}

	/**
	 *
	 * @return
	 */
	public Error getError(){
		return this.error;
	}
}
