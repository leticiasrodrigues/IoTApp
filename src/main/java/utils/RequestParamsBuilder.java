package utils;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by letic on 05/12/2016.
 */

public class RequestParamsBuilder {

    private String mObjectId;
    private String mObject;
    private String mAction;
    private int mMethod;
    private String mVoiceRequest;
    private boolean mNotFound = false;

    public RequestParamsBuilder(String voiceRequest){
        mVoiceRequest = voiceRequest;
        setAction();
        setObject();
        setMethod();

    };

    private void setAction(){
        if (mVoiceRequest.contains("allume")){
            mAction = Constants.ACTION_ALLUMER;
        }else if (mVoiceRequest.contains("affiche")){
            mAction = Constants.ACTION_AFFICHER;
        }else if (mVoiceRequest.contains("éteindre")){
            mAction = Constants.ACTION_ETEINDRE;
        }else {
            mNotFound = true;
        };
    }

    private void setObject(){
        if (mVoiceRequest.contains("lumière")){
            mObjectId = Constants.OBJECT_ID_LUMIERE;
            mObject = Constants.OBJECT_LUMIERE;
        }else if (mVoiceRequest.contains("température")){
            mObjectId = Constants.OBJECT_ID_TEMPERATURE;
            mObject = Constants.OBJECT_TEMPERATURE;
        }else if (mVoiceRequest.contains("pression")){
            mObjectId = Constants.OBJECT_ID_PRESSION;
            mObject = Constants.OBJECT_PRESSION;
        }else if (mVoiceRequest.contains("humidité")){
            mObjectId = Constants.OBJECT_ID_HUMIDITE;
            mObject = Constants.OBJECT_HUMIDITE;
        }else if (mVoiceRequest.contains("luminosité")){
            mObjectId = Constants.OBJECT_ID_LUMINOSITE;
            mObject = Constants.OBJECT_LUMINOSITE;
        }else {
            mNotFound = true;
        };
    }

    private void setMethod(){
      if (mAction == Constants.ACTION_AFFICHER){
          mMethod = Request.Method.GET;
      } else {
          mMethod = Request.Method.POST;
      }
    };

    public int getMethod(){
        return this.mMethod;
    };

    public String getURL(){
        String url = Constants.BASE_URL;
        if (this.mMethod == Request.Method.GET){
            url+=Constants.GET_URL_PATH;
        } else if (this.mMethod == Request.Method.POST) {
            url+=Constants.POST_URL_PATH;
        }

        return url + this.mObjectId;
    };

    public boolean isNotFound(){
        return mNotFound;
    }

    public JSONObject getJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.OBJECT, this.mObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObject.put(Constants.ACTION, this.mAction);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
