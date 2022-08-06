// CheckoutController
// Copyright © 2022 Joel A Mussman. All rights reserved.
//

package com.wonderfulwidgets.thirtyminutestospringboot.api;

import com.wonderfulwidgets.thirtyminutestospringboot.service.CheckoutService;
import com.wonderfulwidgets.thirtyminutestospringboot.service.Factory;

public class CheckoutController {

    private CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {

        this.checkoutService = checkoutService;
    }
}
