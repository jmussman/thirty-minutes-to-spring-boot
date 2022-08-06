// CheckoutService
// Copyright © 2022 Joel A Mussman. All rights reserved.
//

package com.wonderfulwidgets.thirtyminutestospringboot.service;

public class CheckoutService {

    private CardAuthorizer cardAuthorizer;

    public CheckoutService() {

        cardAuthorizer = Factory.getCardAuthorizer();
    }
}
