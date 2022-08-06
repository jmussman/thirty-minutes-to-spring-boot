// Factory
// Copyright © 2022 Joel A Mussman. All rights reserved.
//

package com.wonderfulwidgets.thirtyminutestospringboot.service;

import com.wonderfulwidgets.thirtyminutestospringboot.api.CheckoutController;

public class Factory {

    public static ICardAuthorizer getCardAuthorizer() {

        return new CardAuthorizer();
    }

    public static CheckoutController getCheckoutController(CheckoutService checkoutService) {

        return new CheckoutController(checkoutService);
    }

    public static CheckoutService getCheckoutService(ICardAuthorizer cardAuthorizer) {

        return new CheckoutService(cardAuthorizer);
    }
}
