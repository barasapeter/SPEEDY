package com.barasa.speedy.common.util;

import org.springframework.stereotype.Component;

@Component
public class MpesaEnvConfig {

    public String consumerKey() {
        return System.getenv("MPESA_CONSUMER_KEY");
    }

    public String consumerSecret() {
        return System.getenv("MPESA_CONSUMER_SECRET");
    }

    public String shortcode() {
        return System.getenv("MPESA_SHORTCODE");
    }

    public String onlinePasskey() {
        return System.getenv("MPESA_ONLINE_PASSKEY");
    }

    public String baseUrl() {
        return System.getenv("MPESA_BASE_URL");
    }

    public String callbackUrl() {
        return System.getenv("MPESA_CALLBACK_URL");
    }
}
