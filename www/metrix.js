function callCordova(action) {
    var args = Array.prototype.slice.call(arguments, 1);

    cordova.exec(
        function callback(data) { },
        function errorHandler(err) { },
        'Metrix',
        action,
        args
    );
}

function callCordovaCallback(action, callback) {
    var args = Array.prototype.slice.call(arguments, 2);

    cordova.exec(callback,
        function errorHandler(err) { },
        'Metrix',
        action,
        args
    );
}

var Metrix = {
    create: function(metrixConfig) {
        if (metrixConfig.hasAttributionChangeListener()) {
            callCordovaCallback('setAttributionChangeListener', metrixConfig.getAttributionChangeListener());
        }

        if (metrixConfig.hasAttributionChangeListener()) {
            callCordovaCallback('setDeeplinkResponseListener', metrixConfig.getDeeplinkResponseListener());
        }

        if (metrixConfig.hasUserIdListener()) {
            callCordovaCallback('setOnReceiveUserIdListener', metrixConfig.getOnReceiveUserIdListener());
        }

        if (metrixConfig.hasSessionIdListener()) {
            callCordovaCallback('setOnSessionIdListener', metrixConfig.getOnSessionIdListener());
        }

        callCordova('create', metrixConfig);
    },

    getSessionNum: function(callback) {
        if (callback != null) {
            callCordovaCallback('getSessionNum', callback);
        }
    },

    newEvent: function(slug, attributes) {
        if(attributes) {
            callCordova('trackCustomEvent', slug, attributes);
        } else {
            callCordova('trackSimpleEvent', slug);
        }
    },

    addUserAttributes: function(attributes) {
        callCordova('addUserDefaultAttributes', attributes);
    },

    newRevenue: function(slug, amount, currency, orderId) {
        var cr = null;
        if (currency === 0) {
            cr = "IRR";
        } else if (currency === 1) {
            cr = "USD";
        } else if (currency === 2) {
            cr = "EUR";
        } else {
            cr = "IRR";
        }
        if (orderId) {
            callCordova('trackFullRevenue', slug, amount, cr, orderId);
        } else {
            callCordova('trackSimpleRevenue', slug, amount, cr);
        }       
    },

    appWillOpenUrl: function(url) {
        callCordova('appWillOpenUrl', url);
    },
};

module.exports = Metrix;