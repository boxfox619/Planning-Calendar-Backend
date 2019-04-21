package com.boxfox.calendar.data.model;


public abstract class Model {

    public abstract void assertFields() throws IllegalArgumentException;

    protected void valid(String str, String msg){
        if(str == null || str.isEmpty()){
            throw new AssertionError();
        }
    }

}
