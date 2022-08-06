// Factory
// Copyright © 2022 Joel A Mussman. All rights reserved.
//

package com.wonderfulwidgets.thirtyminutestospringboot.service;

import com.wonderfulwidgets.thirtyminutestospringboot.api.CheckoutController;

public class Factory {

    public static CardAuthorizer getCardAuthorizer() {

        return new CardAuthorizer();
    }

    public static CheckoutController getCheckoutController() {

        return new CheckoutController();
    }

    public static CheckoutService getCheckoutService() {

        return new CheckoutService();
    }
}
