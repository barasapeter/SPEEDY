package com.barasa.speedy.common.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mpesa")
@org.springframework.context.annotation.Primary
public class MpesaEnvConfig {

    private String consumerKey;
    private String consumerSecret;
    private String shortcode;
    private String onlinePasskey;
    private String baseUrl;
    private String callbackUrl;

    // getters and setters
    public String getConsumerKey() {
        return consumerKey;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    public String getShortcode() {
        return shortcode;
    }

    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }

    public String getOnlinePasskey() {
        return onlinePasskey;
    }

    public void setOnlinePasskey(String onlinePasskey) {
        this.onlinePasskey = onlinePasskey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}
