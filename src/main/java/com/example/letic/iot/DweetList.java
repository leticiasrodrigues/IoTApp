package com.example.letic.iot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by letic on 08/12/2016.
 */

public class DweetList {

    private ArrayList<DweetContent> with = new ArrayList<>();

    private DweetContent getWith(){
        if (this.with!=null && this.with.size()!=0)
            return with.get(0);
        else
            return new DweetContent();
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
