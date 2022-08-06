// ThirtyMinutesToSpringBootApplication
// Copyright © 2022 Joel A Mussman. All rights reserved.
//

package com.wonderfulwidgets.thirtyminutestospringboot;

import com.wonderfulwidgets.thirtyminutestospringboot.api.CheckoutController;
import com.wonderfulwidgets.thirtyminutestospringboot.service.Factory;

public class ThirtyMinutesToSpringBootApplication {

    public static void main(String[] args) {

        CheckoutController checkoutController = Factory.getCheckoutController();
    }
}
