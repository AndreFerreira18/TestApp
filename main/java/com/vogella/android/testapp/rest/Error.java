package com.vogella.android.testapp.rest;

import com.google.gson.annotations.Expose;

/**
 * Created by Gio on 2015-12-20.
 */
public class Error {

    /**
     *
     */
    @Expose
    private int code;

    /**
     *
     */
    @Expose
    private String message;

    /**
     *
     */
    public Error(){
        this(0, "");
    }

    /**
     *
     * @param code
     * @param message
     */
    public Error(int code, String message){

        this.code = code;

        this.message = message;
    }

    /**
     *
     * @return
     */
    public int getCode(){
        return this.code;
    }

    /**
     *
     * @return
     */
    public String getMessage(){
        return this.message;
    }
}