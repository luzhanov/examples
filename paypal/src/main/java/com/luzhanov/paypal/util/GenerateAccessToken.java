package com.luzhanov.paypal.util;

import com.luzhanov.paypal.service.PayPalService;
import com.paypal.base.ConfigManager;
import com.paypal.base.Constants;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.base.rest.PayPalResource;

import java.io.InputStream;

public class GenerateAccessToken {

	private static boolean initialized = false;

	private static void init() {
		if (!initialized) {
			try {
                InputStream is = PayPalService.class.getResourceAsStream("/sdk_config.properties");
                PayPalResource.initConfig(is);
                initialized = true;
            } catch (PayPalRESTException e) {
                e.printStackTrace();
            }
		}
	}

	public static String getAccessToken() throws PayPalRESTException {
		init();

		// ###AccessToken
		// Retrieve the access token from
		// OAuthTokenCredential by passing in
		// ClientID and ClientSecret
		String clientID = ConfigManager.getInstance().getValue(Constants.CLIENT_ID);
		String clientSecret = ConfigManager.getInstance().getValue(Constants.CLIENT_SECRET);

		return new OAuthTokenCredential(clientID, clientSecret).getAccessToken();
	}
}
