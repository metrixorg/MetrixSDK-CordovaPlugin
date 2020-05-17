package ir.metrix.sdk;

import java.util.Map;
import android.net.Uri;
import android.util.Log;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.app.Application;
import static ir.metrix.sdk.MetrixCordovaUtils.*;
import ir.metrix.sdk.network.model.AttributionModel;

public class MetrixCordova extends CordovaPlugin {

    private boolean shouldLaunchDeeplink;
    private CallbackContext attributionCallbackContext;
    private CallbackContext getSessionNumCallbackContext;
    private CallbackContext deeplinkCallbackContext;
    private CallbackContext userIdCallbackContext;
    private CallbackContext sessionIdCallbackContext;

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if (action.equals(COMMAND_CREATE)) {
            executeCreate(args);
        } else if (action.equals(COMMAND_SET_ATTRIBUTION_CHANGE_LISTENER)) {
            attributionCallbackContext = callbackContext;
        } else if (action.equals(COMMAND_GET_SESSION_NUMBER)) {
            getSessionNumCallbackContext = callbackContext;
            getSessionNum();
        } else if (action.equals(COMMAND_TRACK_SIMPLE_EVENT)) {
            String slug = args.getString(0);
            Metrix.getInstance().newEvent(slug);
        } else if (action.equals(COMMAND_TRACK_CUSTOM_EVENT)) {
            sendCustomEvent(args);
        } else if (action.equals(COMMAND_TRACK_SIMPLE_REVENUE)) {
            trackSimpleRevenue(args);
        } else if (action.equals(COMMAND_TRACK_FULL_REVENUE)) {
            trackFullRevenue(args);
        } else if (action.equals(COMMAND_SET_DEEPLINK_RESPONSE_CALLBACK)) {
            deeplinkCallbackContext = callbackContext;
        } else if (action.equals(COMMAND_ADD_USER_DEFAULT_ATTRIBUTES)) {
            addUserAttributes(args);
        } else if (action.equals(COMMAND_APP_WILL_OPEN_URL)) {
            String url = args.getString(0);
            final Uri uri = Uri.parse(url);
            Metrix.getInstance().appWillOpenUrl(uri);
        } else if (action.equals(COMMAND_SET_USER_ID_LISTENER)) {
            userIdCallbackContext = callbackContext;
        } else if (action.equals(COMMAND_SET_SESSION_ID_LISTENER)) {
            sessionIdCallbackContext = callbackContext;
        } else {
            callbackContext.error("\"" + action + "\" is not a recognized action.");
            return false;
        }
        return true;
    }

    private void executeCreate(final JSONArray args) throws JSONException {
        String metrixConfigString = args.getString(0);
        JSONObject metrixConfigObject = new JSONObject(metrixConfigString);
        Map<String, Object> config = jsonObjectToMap(metrixConfigObject);

        String appId = null;
        String defaultTrackerToken = null;
        String secretId = null;
        String info1 = null;
        String info2 = null;
        String info3 = null;
        String info4 = null;
        boolean shouldLaunchDeeplink = false;
        String firebaseAppId = null;
        boolean isLocationListeningEnable = false;
        String eventUploadThreshold = null;
        String eventUploadMaxBatchSize = null;
        String eventMaxCount = null;
        String eventUploadPeriodMillis = null;
        String sessionTimeoutMillis = null;
        String store = null;

        if (config.containsKey(KEY_APP_ID)) {
            appId = config.get(KEY_APP_ID).toString();
        }
        if (config.containsKey(KEY_SECRET_ID)) {
            secretId = config.get(KEY_SECRET_ID).toString();
        }
        if (config.containsKey(KEY_INFO_1)) {
            info1 = config.get(KEY_INFO_1).toString();
        }
        if (config.containsKey(KEY_INFO_2)) {
            info2 = config.get(KEY_INFO_2).toString();
        }
        if (config.containsKey(KEY_INFO_3)) {
            info3 = config.get(KEY_INFO_3).toString();
        }
        if (config.containsKey(KEY_INFO_4)) {
            info4 = config.get(KEY_INFO_4).toString();
        }

        if (config.containsKey(KEY_SHOULD_LAUNCH_DEEPLINK)) {
            shouldLaunchDeeplink = config.get(KEY_SHOULD_LAUNCH_DEEPLINK).toString() == "true" ? true : false;
        }

        if (config.containsKey(KEY_LOCATION_LISTENING_ENABLED)) {
            isLocationListeningEnable = config.get(KEY_LOCATION_LISTENING_ENABLED).toString() == "true" ? true : false;
        }

        if (config.containsKey(KEY_FIREBASE_ADD_ID)) {
            firebaseAppId = config.get(KEY_FIREBASE_ADD_ID).toString();
        }
        
        if (config.containsKey(KEY_EVENT_UPLOAD_THRESHOLD)) {
            eventUploadThreshold = config.get(KEY_EVENT_UPLOAD_THRESHOLD).toString();
        }

        if (config.containsKey(KEY_EVENT_UPLOAD_MAX_BATCH_SIZE)) {
            eventUploadMaxBatchSize = config.get(KEY_EVENT_UPLOAD_MAX_BATCH_SIZE).toString();
        }

        if (config.containsKey(KEY_EVENT_MAX_COUNT)) {
            eventMaxCount = config.get(KEY_EVENT_MAX_COUNT).toString();
        }

        if (config.containsKey(KEY_EVENT_UPLOAD_PERIOD)) {
            eventUploadPeriodMillis = config.get(KEY_EVENT_UPLOAD_PERIOD).toString();
        }

        if (config.containsKey(KEY_SESSION_TIMEOUT)) {
            sessionTimeoutMillis = config.get(KEY_SESSION_TIMEOUT).toString();
        }

        if (config.containsKey(KEY_STORE)) {
            store = config.get(KEY_STORE).toString();
        }

        if (config.containsKey(KEY_DEFAULT_TRACKER_TOKEN)) {
            defaultTrackerToken = config.get(KEY_DEFAULT_TRACKER_TOKEN).toString();
        }

        final MetrixConfig metrixConfig = new MetrixConfig(
            (Application) this.cordova.getActivity().getApplicationContext(),
            appId
        );

        this.shouldLaunchDeeplink = shouldLaunchDeeplink;

        if (isFieldValid(firebaseAppId)) {
            metrixConfig.setFirebaseAppId(firebaseAppId);
        }

        // Default tracker.
        if (isFieldValid(defaultTrackerToken)) {
            metrixConfig.setDefaultTrackerToken(defaultTrackerToken);
        }

        // App secret.
        if (isFieldValid(secretId) && isFieldValid(info1) && isFieldValid(info2) && isFieldValid(info3) && isFieldValid(info4)) {
            try {
                long lSecretId = Long.parseLong(secretId, 10);
                long lInfo1 = Long.parseLong(info1, 10);
                long lInfo2 = Long.parseLong(info2, 10);
                long lInfo3 = Long.parseLong(info3, 10);
                long lInfo4 = Long.parseLong(info4, 10);
                metrixConfig.setAppSecret(lSecretId, lInfo1, lInfo2, lInfo3, lInfo4);
            } catch(NumberFormatException ignored) {}
        }

        // Attribution callback.
        if (attributionCallbackContext != null) {
            metrixConfig.setOnAttributionChangedListener(new OnAttributionChangedListener() {
                @Override
                public void onAttributionChanged(AttributionModel attributionModel) {
                    JSONObject attributionJsonData = new JSONObject(getAttributionMap(attributionModel));
                    PluginResult pluginResult = new PluginResult(Status.OK, attributionJsonData);
                    pluginResult.setKeepCallback(true);
                    attributionCallbackContext.sendPluginResult(pluginResult);
                }
            });
        }

        // Deferred deeplink callback listener.
        if (deeplinkCallbackContext != null) {
            metrixConfig.setOnDeeplinkResponseListener(new OnDeeplinkResponseListener() {
                @Override
                public boolean launchReceivedDeeplink(Uri deeplink) {
                    PluginResult pluginResult = new PluginResult(Status.OK, deeplink.toString());
                    pluginResult.setKeepCallback(true);
                    deeplinkCallbackContext.sendPluginResult(pluginResult);

                    return MetrixCordova.this.shouldLaunchDeeplink;
                }
            });
        }

        // UserID listener
        if (userIdCallbackContext != null) {
            metrixConfig.setOnReceiveUserIdListener(new OnReceiveUserIdListener() {
                @Override
                public void onReceiveUserId(String metrixUserId) {
                    PluginResult pluginResult = new PluginResult(Status.OK, metrixUserId);
                    pluginResult.setKeepCallback(true);
                    userIdCallbackContext.sendPluginResult(pluginResult);
                }
            });
        }

        // SessionID listener
        if (sessionIdCallbackContext != null) {
            metrixConfig.setOnSessionIdListener(new OnSessionIdListener() {
                @Override
                public void onReceiveSessionId(String sessionId) {
                    PluginResult pluginResult = new PluginResult(Status.OK, sessionId);
                    pluginResult.setKeepCallback(true);
                    sessionIdCallbackContext.sendPluginResult(pluginResult);
                }
            });
        }

        metrixConfig.setLocationListening(isLocationListeningEnable);

        if (isFieldValid(eventUploadThreshold)) {
            try {
                int threshold = Integer.parseInt(eventUploadThreshold);
                metrixConfig.setEventUploadThreshold(threshold);
            } catch(NumberFormatException ignored) {}
        }

        if (isFieldValid(eventUploadMaxBatchSize)) {
            try {
                int size = Integer.parseInt(eventUploadMaxBatchSize);
                metrixConfig.setEventUploadMaxBatchSize(size);
            } catch(NumberFormatException ignored) {}
        }

        if (isFieldValid(eventMaxCount)) {
            try {
                int count = Integer.parseInt(eventMaxCount);
                metrixConfig.setEventMaxCount(count);
            } catch(NumberFormatException ignored) {}
        }

        if (isFieldValid(eventUploadPeriodMillis)) {
            try {
                long period = Long.parseLong(eventUploadPeriodMillis);
                metrixConfig.setEventUploadPeriodMillis(period);
            } catch(NumberFormatException ignored) {}
        }

        if (isFieldValid(sessionTimeoutMillis)) {
            try {
                long timeout = Long.parseLong(sessionTimeoutMillis);
                metrixConfig.setSessionTimeoutMillis(timeout);
            } catch(NumberFormatException ignored) {}
        }

        if (isFieldValid(store)) {
            metrixConfig.setStore(store);
        }


        // Start SDK.
        Metrix.onCreate(metrixConfig);
        // Needed because Cordova doesn't launch 'resume' event on app start.
        // It initializes it only when app comes back from the background.
        Metrix.getInstance().activityCreated(this.cordova.getActivity(), new Bundle());
        Metrix.getInstance().activityStarted(this.cordova.getActivity());
        Metrix.getInstance().activityResumed(this.cordova.getActivity());
    }

    private void getSessionNum() {
        if (getSessionNumCallbackContext != null) {
            final long sessionNum = Metrix.getInstance().getSessionNum();
            PluginResult pluginResult = new PluginResult(Status.OK, sessionNum);
            pluginResult.setKeepCallback(true);
            getSessionNumCallbackContext.sendPluginResult(pluginResult);
        }
    }
 
    private void sendCustomEvent(final JSONArray args) throws JSONException {
        String slug = args.getString(0);
        String attributesJson = args.getString(1);
        Map<String, String> attributes = jsonObjectToStringMap(new JSONObject(attributesJson));
        Metrix.getInstance().newEvent(slug, attributes);
    }

    private void addUserAttributes(final JSONArray args) throws JSONException {
        String attributesJson = args.getString(0);
        Map<String, String> attributes = jsonObjectToStringMap(new JSONObject(attributesJson));
        Metrix.getInstance().addUserAttributes(attributes);
    }

    private void trackSimpleRevenue(final JSONArray args) throws JSONException {
        String slug = args.getString(0);
        Double amount = args.getDouble(1);
        MetrixCurrency currency = MetrixCurrency.valueOf(args.getString(2));
        Metrix.getInstance().newRevenue(slug, amount, currency);
    }

    private void trackFullRevenue(final JSONArray args) throws JSONException {
        String slug = args.getString(0);
        Double amount = args.getDouble(1);
        MetrixCurrency currency = MetrixCurrency.valueOf(args.getString(2));
        String orderId = args.getString(3);
        Metrix.getInstance().newRevenue(slug, amount, currency, orderId);
    }
}