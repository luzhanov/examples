package com.luzhanov.paypal.service;

import com.luzhanov.paypal.util.GenerateAccessToken;
import com.paypal.api.payments.CreditCard;
import com.paypal.api.payments.CreditCardHistory;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentHistory;
import com.paypal.base.rest.APIContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class PayPalService {

    /*
    https://developer.paypal.com/developer/accounts
     */

    private Logger logger = Logger.getLogger(PayPalService.class);

    public String getBalance() {

//        try {
//            Map<String, String> sdkConfig = new HashMap<>();
//            sdkConfig.put("mode", "sandbox");
//
//            String accessToken = new OAuthTokenCredential(CLIENT_ID, CLIENT_SECRET, sdkConfig).getAccessToken();
//        } catch (PayPalRESTException e) {
//            logger.error(e);
//        }

        //todo: implement
        return null;
    }

    public List<String> getHistory() {
        try {
            String accessToken = GenerateAccessToken.getAccessToken();

            Map<String, String> containerMap = new HashMap<>();
            containerMap.put("count", "10");

            PaymentHistory paymentHistory = Payment.list(accessToken, containerMap);
            logger.info("Payment History = " + paymentHistory.toString());

            //todo: implement & return details

        } catch (com.paypal.base.rest.PayPalRESTException e) {
            logger.error(e);
        }

        return Collections.emptyList();
    }

    public String createCard() {
        CreditCard creditCard = new CreditCard();
        creditCard.setExpireMonth(11);
        creditCard.setExpireYear(2018);
        creditCard.setNumber("4417119669820331");
        creditCard.setType("visa");

        try {
            String accessToken = GenerateAccessToken.getAccessToken();

            APIContext apiContext = new APIContext(accessToken);
            CreditCard createdCreditCard = creditCard.create(apiContext);

            logger.info("Credit Card Created With ID: " + createdCreditCard.getId());
            return createdCreditCard.getId();
        } catch (com.paypal.base.rest.PayPalRESTException e) {
            logger.error(e);
        }

        return null;
    }

    public List<String> listCards() {
        try {
            String accessToken = GenerateAccessToken.getAccessToken();

            Map<String, String> containerMap = new HashMap<>();

            APIContext apiContext = new APIContext(accessToken);
            CreditCardHistory creditCardHistory = CreditCard.list(apiContext, containerMap);

            logger.info("Credit Card listing: " + creditCardHistory.toString());

            return creditCardHistory.getItems().stream().map(CreditCard::toString).collect(Collectors.toList());
        } catch (com.paypal.base.rest.PayPalRESTException e) {
            logger.error(e);
        }

        return null;
    }

}
