// CheckoutController
// Copyright © 2022 Joel A Mussman. All rights reserved.
//

package com.wonderfulwidgets.thirtyminutestospringboot.api;

import com.wonderfulwidgets.thirtyminutestospringboot.service.CheckoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.0")
public class CheckoutController {

    private CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {

        this.checkoutService = checkoutService;
    }

    @PostMapping("/checkout/{cardnumber}")
    public ResponseEntity<String> checkout(@PathVariable("cardnumber") String cardNumber) {

        boolean result = checkoutService.checkout(cardNumber);
        ResponseEntity<String> response;

        if (result) {

            response = ResponseEntity.ok("Payment accepted");

        } else {

            response = ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body("Payment unauthorized");
        }

        return response;
    }
}
