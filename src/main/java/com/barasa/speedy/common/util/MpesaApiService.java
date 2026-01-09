package com.barasa.speedy.common.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class MpesaApiService implements InitializingBean {

        private final MpesaEnvConfig env;
        private final RestTemplate restTemplate;
        private final ObjectMapper objectMapper;

        public MpesaApiService(MpesaEnvConfig env) {
                this.env = env;
                this.restTemplate = new RestTemplate();
                this.objectMapper = new ObjectMapper();
        }

        @Override
        public void afterPropertiesSet() {
                System.out.println("MPESA Base URL: " + env.getBaseUrl());
                System.out.println("MPESA Shortcode: " + env.getShortcode());
                System.out.println("MPESA Callback URL: " + env.getCallbackUrl());
        }

        private String getAccessToken() throws Exception {

                String credentials = env.getConsumerKey() + ":" + env.getConsumerSecret();
                String encoded = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Basic " + encoded);

                HttpEntity<Void> entity = new HttpEntity<>(headers);

                String url = env.getBaseUrl() + "/oauth/v1/generate?grant_type=client_credentials";
                System.out.println("Requesting MPESA access token from URL: " + url);

                ResponseEntity<String> response = restTemplate.exchange(
                                url,
                                HttpMethod.GET,
                                entity,
                                String.class);

                JsonNode json = objectMapper.readTree(response.getBody());
                return json.get("access_token").asText();
        }

        private Map<String, String> generatePassword() {

                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                String raw = env.getShortcode() + env.getOnlinePasskey() + timestamp;
                String password = Base64.getEncoder().encodeToString(raw.getBytes(StandardCharsets.UTF_8));

                Map<String, String> map = new HashMap<>();
                map.put("password", password);
                map.put("timestamp", timestamp);
                return map;
        }

        public JsonNode initiateStkPush(String phone, String amount) throws Exception {

                String token = getAccessToken();
                Map<String, String> auth = generatePassword();

                Map<String, Object> payload = new HashMap<>();
                payload.put("BusinessShortCode", env.getShortcode());
                payload.put("Password", auth.get("password"));
                payload.put("Timestamp", auth.get("timestamp"));
                payload.put("TransactionType", "CustomerBuyGoodsOnline");
                payload.put("Amount", amount);
                payload.put("PartyA", phone);
                payload.put("PartyB", env.getShortcode());
                payload.put("PhoneNumber", phone);
                payload.put("CallBackURL", env.getCallbackUrl());
                payload.put("AccountReference", "UNIQUE_REFERENCE");
                payload.put("TransactionDesc", "Payment");

                System.out.println("STK PUSH PAYLOAD => " + objectMapper.writeValueAsString(payload));

                HttpHeaders headers = new HttpHeaders();
                headers.setBearerAuth(token);
                headers.setContentType(MediaType.APPLICATION_JSON);

                HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

                String url = env.getBaseUrl() + "/mpesa/stkpush/v1/processrequest";
                System.out.println("Sending STK Push POST to URL: " + url);

                ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

                System.out.println("MPESA RESPONSE => " + response.getBody());

                return objectMapper.readTree(response.getBody());
        }
}
