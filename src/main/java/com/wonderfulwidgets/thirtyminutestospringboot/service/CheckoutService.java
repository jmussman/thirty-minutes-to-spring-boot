// CheckoutService
// Copyright © 2022 Joel A Mussman. All rights reserved.
//

package com.wonderfulwidgets.thirtyminutestospringboot.service;

import org.springframework.stereotype.Service;

@Service
public class CheckoutService {

    private ICardAuthorizer cardAuthorizer;

    public CheckoutService(ICardAuthorizer cardAuthorizer) {

        this.cardAuthorizer = cardAuthorizer;
    }

    public boolean checkout(String cardNumber) {

        return true;
    }
}
