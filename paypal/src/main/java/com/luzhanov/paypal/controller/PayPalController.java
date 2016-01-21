package com.luzhanov.paypal.controller;

import com.luzhanov.paypal.service.PayPalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PayPalController {

    @Autowired
    private PayPalService payPalService;

    @RequestMapping("/api/balance")
    public ResponseEntity<String> getBalance() {
        return new ResponseEntity<>(payPalService.getBalance(), HttpStatus.OK);
    }

}
