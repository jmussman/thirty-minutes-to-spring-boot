// ThirtyMinutesToSpringBootApplication
// Copyright © 2022 Joel A Mussman. All rights reserved.
//

package com.wonderfulwidgets.thirtyminutestospringboot;

import com.wonderfulwidgets.thirtyminutestospringboot.api.CheckoutController;
import com.wonderfulwidgets.thirtyminutestospringboot.service.CheckoutService;
import com.wonderfulwidgets.thirtyminutestospringboot.service.Factory;
import com.wonderfulwidgets.thirtyminutestospringboot.service.ICardAuthorizer;

public class ThirtyMinutesToSpringBootApplication {

    public static void main(String[] args) {

        ICardAuthorizer cardAuthorizer = Factory.getCardAuthorizer();
        CheckoutService checkoutService = Factory.getCheckoutService(cardAuthorizer);
        CheckoutController checkoutController = Factory.getCheckoutController(checkoutService);
    }
}
