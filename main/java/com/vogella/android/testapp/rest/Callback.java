package com.vogella.android.testapp.rest;

/**
 * Created by Gio on 2015-12-20.
 */
public interface Callback<T>{

    /**
     *
     * @param data
     */
    void onResponse(T data);

    /**
     *
     * @param error
     */
    void onError(Error error);
}
