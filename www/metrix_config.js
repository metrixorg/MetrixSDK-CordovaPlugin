function MetrixConfig(appId) {
    console.log("metrix appId: " + appId)
    this.appId = appId;
    this.secretId = null;
    this.info1 = null;
    this.info2 = null;
    this.info3 = null;
    this.info4 = null;
    this.defaultTrackerToken = null;
    this.shouldLaunchDeeplink = false;
    this.attributionChangeListener = null;
    this.deeplinkResponseListener = null;
    this.firebaseAppId = null;
    this.isLocationListeningEnable = false;
    this.eventUploadThreshold = null;
    this.eventUploadMaxBatchSize = null;
    this.eventMaxCount = null;
    this.eventUploadPeriodMillis = null;
    this.sessionTimeoutMillis = null;
    this.store = null;
    this.userIdListener = null;
    this.sessionIdListener = null;
}

MetrixConfig.prototype.setDefaultTracker = function(defaultTrackerToken) {
    this.defaultTrackerToken = defaultTrackerToken;
};

MetrixConfig.prototype.setFirebaseAppId = function(firebaseAppId) {
    this.firebaseAppId = firebaseAppId;
};

MetrixConfig.prototype.setAppSecret = function(secretId, info1, info2, info3, info4) {
    if (secretId !== null) {
        this.secretId = secretId.toString();
    }
    if (info1 !== null) {
        this.info1 = info1.toString();
    }
    if (info2 !== null) {
        this.info2 = info2.toString();
    }
    if (info3 !== null) {
        this.info3 = info3.toString();
    }
    if (info4 !== null) {
        this.info4 = info4.toString();
    }
};

MetrixConfig.prototype.getAttributionChangeListener = function() {
    return this.attributionChangeListener;
};

MetrixConfig.prototype.getDeeplinkResponseListener = function() {
    return this.deeplinkResponseListener;
};

MetrixConfig.prototype.getOnReceiveUserIdListener = function() {
    return this.userIdListener;
};

MetrixConfig.prototype.getOnSessionIdListener = function() {
    return this.sessionIdListener;
};

MetrixConfig.prototype.setShouldLaunchDeeplink = function(shouldLaunchDeeplink) {
    this.shouldLaunchDeeplink = shouldLaunchDeeplink;
};

MetrixConfig.prototype.setOnAttributionChangeListener = function(attributionChangeListener) {
    this.attributionChangeListener = attributionChangeListener;
};

MetrixConfig.prototype.setOnDeeplinkResponseListener = function(deeplinkResponseListener) {
    this.deeplinkResponseListener = deeplinkResponseListener;
};

MetrixConfig.prototype.setOnReceiveUserIdListener = function(userIdListener) {
    this.userIdListener = userIdListener;
};

MetrixConfig.prototype.setOnSessionIdListener = function(sessionIdListener) {
    this.sessionIdListener = sessionIdListener;
};

MetrixConfig.prototype.hasAttributionChangeListener = function() {
    return this.attributionChangeListener !== null;
};

MetrixConfig.prototype.hasDeeplinkResponseListener = function() {
    return this.deeplinkResponseListener !== null;
};

MetrixConfig.prototype.hasUserIdListener = function() {
    return this.userIdListener !== null;
};

MetrixConfig.prototype.hasSessionIdListener = function() {
    return this.sessionIdListener !== null;
};

MetrixConfig.prototype.setLocationListening = function(isLocationListeningEnable) {
    this.isLocationListeningEnable = isLocationListeningEnable;
};

MetrixConfig.prototype.setEventUploadThreshold = function(threshold) {
    this.eventUploadThreshold = threshold;
};

MetrixConfig.prototype.setEventUploadMaxBatchSize = function(size) {
    this.eventUploadMaxBatchSize = size;
};

MetrixConfig.prototype.setEventMaxCount = function(count) {
    this.eventMaxCount = count;
};

MetrixConfig.prototype.setEventUploadPeriodMillis = function(period) {
    this.eventUploadPeriodMillis = period;
};

MetrixConfig.prototype.setSessionTimeoutMillis = function(timeout) {
    this.sessionTimeoutMillis = timeout;
};

MetrixConfig.prototype.setStore = function(store) {
    this.store = store;
};

module.exports = MetrixConfig;