package ir.metrix.sdk;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import ir.metrix.sdk.network.model.AttributionModel;

public class MetrixCordovaUtils {
	public static final String KEY_APP_ID = "appId";
    public static final String KEY_DEFAULT_TRACKER_TOKEN = "defaultTrackerToken";
    public static final String KEY_SHOULD_LAUNCH_DEEPLINK = "shouldLaunchDeeplink";
    public static final String KEY_FIREBASE_ADD_ID = "firebaseAppId";
    public static final String KEY_SECRET_ID = "secretId";
    public static final String KEY_INFO_1 = "info1";
    public static final String KEY_INFO_2 = "info2";
    public static final String KEY_INFO_3 = "info3";
    public static final String KEY_INFO_4 = "info4";
    public static final String KEY_LOCATION_LISTENING_ENABLED = "isLocationListeningEnable";
    public static final String KEY_EVENT_UPLOAD_THRESHOLD = "eventUploadThreshold";
    public static final String KEY_EVENT_UPLOAD_MAX_BATCH_SIZE = "eventUploadMaxBatchSize";
    public static final String KEY_EVENT_MAX_COUNT = "eventMaxCount";
    public static final String KEY_EVENT_UPLOAD_PERIOD = "eventUploadPeriodMillis";
    public static final String KEY_SESSION_TIMEOUT = "sessionTimeoutMillis";
    public static final String KEY_STORE = "store";

    public static final String COMMAND_CREATE = "create";
    public static final String COMMAND_SET_ATTRIBUTION_CHANGE_LISTENER = "setAttributionChangeListener";
    public static final String COMMAND_GET_SESSION_NUMBER = "getSessionNum";
    public static final String COMMAND_TRACK_SIMPLE_EVENT = "trackSimpleEvent";
    public static final String COMMAND_TRACK_CUSTOM_EVENT = "trackCustomEvent";
    public static final String COMMAND_TRACK_FULL_REVENUE = "trackFullRevenue";
    public static final String COMMAND_TRACK_SIMPLE_REVENUE = "trackSimpleRevenue";
    public static final String COMMAND_ADD_USER_DEFAULT_ATTRIBUTES = "addUserDefaultAttributes";
    public static final String COMMAND_SET_DEEPLINK_RESPONSE_CALLBACK = "setDeeplinkResponseListener";
    public static final String COMMAND_APP_WILL_OPEN_URL = "appWillOpenUrl";
    public static final String COMMAND_SET_USER_ID_LISTENER = "setOnReceiveUserIdListener";
    public static final String COMMAND_SET_SESSION_ID_LISTENER = "setOnSessionIdListener";

    public static void addValueOrEmpty(Map<String, String> map, String key, Object value){
        if (value != null) {
            map.put(key, value.toString());
        } else {
            map.put(key, "");
        }
    }

    public static boolean isFieldValid(String field) {
        return field != null && !field.equals("") && !field.equals("null");
    }

    public static String[] jsonArrayToArray(JSONArray jsonArray) throws JSONException {
        if (jsonArray == null) {
            return null;
        }

        String[] array = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            array[i] = jsonArray.get(i).toString();
        }

        return array;
    }

    public static Map<String, Object> jsonObjectToMap(JSONObject jsonObject) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>(jsonObject.length());
        @SuppressWarnings("unchecked")
        Iterator<String> jsonObjectIterator = jsonObject.keys();

        while (jsonObjectIterator.hasNext()) {
            String key = jsonObjectIterator.next();
            map.put(key, jsonObject.get(key));
        }

        return map;
    }

    public static Map<String, String> jsonObjectToStringMap(JSONObject jsonObject) throws JSONException {
        Map<String, String> map = new HashMap<String, String>(jsonObject.length());
        @SuppressWarnings("unchecked")
        Iterator<String> jsonObjectIterator = jsonObject.keys();

        while (jsonObjectIterator.hasNext()) {
            String key = jsonObjectIterator.next();
            map.put(key, jsonObject.get(key).toString());
        }

        return map;
    }

    public static Map<String, String> getAttributionMap(AttributionModel attributionModel) {
        Map<String, String> map = new HashMap<String, String>();
        if (attributionModel.getAcquisitionAd() != null) {
            map.put("acquisitionAd", attributionModel.getAcquisitionAd());
        }
        if (attributionModel.getAcquisitionAdSet() != null) {
            map.put("acquisitionAdSet", attributionModel.getAcquisitionAdSet());
        }
        if (attributionModel.getAcquisitionCampaign() != null) {
            map.put("acquisitionCampaign ", attributionModel.getAcquisitionCampaign());
        }
        if (attributionModel.getAcquisitionSource() != null) {
            map.put("acquisitionSource", attributionModel.getAcquisitionSource());
        }
        if (attributionModel.getAttributionStatus() != null) {
            map.put("attributionStatus", attributionModel.getAttributionStatus().name());
        }

        return map;
    }
}