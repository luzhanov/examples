package com.luzhanov.paypal.service;

import com.paypal.core.rest.OAuthTokenCredential;
import com.paypal.core.rest.PayPalRESTException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PayPalService {

    /*
    https://developer.paypal.com/developer/accounts
     */

    public static final String CLIENT_ID = "testddddd2_api1.test.com";
    public static final String CLIENT_SECRET = "QGEPSADKAVTMRCCB";

    public String getBalance() {

        try {
            Map<String, String> sdkConfig = new HashMap<>();
            sdkConfig.put("mode", "sandbox");

            String accessToken = new OAuthTokenCredential(CLIENT_ID, CLIENT_SECRET, sdkConfig).getAccessToken();
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }

        //todo: implement
        return null;
    }

}
