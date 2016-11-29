package com.vogella.android.testapp.rest;

import android.util.Log;

import retrofit.Retrofit;

/**
 * Created by Gio on 25-10-2015.
 */
public class Request<T> implements retrofit.Callback<Response<T>> {

	/**
	 *
	 */
	private Callback<T> listener;

	/**
	 *
	 * @param listener
	 */
	public Request(Callback<T> listener){
		this.listener = listener;
	}

	/**
	 *
	 * @param response
	 * @param retrofit
	 */
	@Override
	public void onResponse(retrofit.Response<Response<T>> response, Retrofit retrofit) {

        if(response.isSuccess()){
            Response<T> data = response.body();

            if(data.isError())
                this.listener.onError(data.getError());
            else
                this.listener.onResponse(data.getData());
        }
        else{
            Log.e("Request", response.message());
            throw new RuntimeException(response.message());
        }

	}

	/**
	 *
	 * @param t
	 */
	@Override
	public void onFailure(Throwable t) {
	    this.listener.onError(new Error(1, t.getMessage()));
	}
}