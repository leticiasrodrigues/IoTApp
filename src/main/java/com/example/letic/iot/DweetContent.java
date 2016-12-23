package com.example.letic.iot;

import java.util.Date;

/**
 * Created by letic on 11/12/2016.
 */

public class DweetContent {

        private String thing;
        private Date created;
        private Content content;

        protected Content getContent(){
            return this.content;
        }

    protected String getObject(){
        return this.getContent().getObject();
    }

    protected String getAction(){
        return this.getContent().getAction();
    }

    protected String getValeur(){
        return this.getContent().getValeur();
    }

        private class Content {
            private String object;
            private String action;
            private String value;

            private String getObject(){
                return this.object.substring(0,1).toUpperCase().concat(this.object.substring(1));
            }

            private String getAction(){
                return this.action;
            }

            private String getValeur(){
                return this.value!=null?this.value:"";
            }
        }
}
