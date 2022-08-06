// CheckoutService
// Copyright © 2022 Joel A Mussman. All rights reserved.
//

package com.wonderfulwidgets.thirtyminutestospringboot.service;

public class CheckoutService {

    private ICardAuthorizer cardAuthorizer;

    public CheckoutService(ICardAuthorizer cardAuthorizer) {

        this.cardAuthorizer = cardAuthorizer;
    }
}
