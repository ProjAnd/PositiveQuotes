package com.example.myapplication;

import kotlin.jvm.Synchronized;

public class Singleton {
    public static Singleton instance = null;


    private Singleton(){

    }
    public static Singleton getInstance(){
        if(instance==null){

            synchronized (Singleton.class){
                if(instance==null){
                   instance = new Singleton();
                }

            }
        }
        return instance;
    }
}
