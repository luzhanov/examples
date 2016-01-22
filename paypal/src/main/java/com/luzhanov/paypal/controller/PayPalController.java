package com.luzhanov.paypal.controller;

import com.luzhanov.paypal.service.PayPalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class PayPalController {

    @Autowired
    private PayPalService payPalService;

    @RequestMapping("/api/balance")
    public ResponseEntity<String> getBalance() {
        return new ResponseEntity<>(payPalService.getBalance(), HttpStatus.OK);
    }

    @RequestMapping("/api/history")
    public ResponseEntity<List<String>> getHistory() {
        return new ResponseEntity<>(payPalService.getHistory(), HttpStatus.OK);
    }

    //todo: use POST here
    @RequestMapping("/api/card/create")
    public ResponseEntity<String> createCard() {
        return new ResponseEntity<>(payPalService.createCard(), HttpStatus.OK);
    }

    @RequestMapping("/api/card/list")
    public ResponseEntity<List<String>> listCards() {
        return new ResponseEntity<>(payPalService.listCards(), HttpStatus.OK);
    }

}
