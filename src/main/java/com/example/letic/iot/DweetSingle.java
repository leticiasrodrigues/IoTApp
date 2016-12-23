package com.example.letic.iot;

import java.util.ArrayList;

/**
 * Created by letic on 11/12/2016.
 */

public class DweetSingle {

    private DweetContent with;

    private DweetContent getWith(){
        return with;
    }

    public String getObject(){
        return this.getWith().getObject();
    }

    public String getAction(){
        return this.getWith().getAction();
    }

    public String getValeur(){
        return this.getWith().getValeur();
    }
}
