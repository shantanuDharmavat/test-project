package com.slidingimages;

/**
 * Created by admin on 24-Mar-17.
 */
public class Global {
    MovieModel selectedMoview;
    static Global global = new Global();
    Global(){
    }
    public static Global getObject(){return global;}

    MovieModel selectedIncidentModel;
}
