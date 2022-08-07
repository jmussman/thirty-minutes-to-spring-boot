![](.common/joels-private-stock.png?raw=true)

# Thirty Minutes to Spring Boot

This repository contains the initial and completed project that builds a controller, service and
dependencies from nothing up to a Spring Boot API.
The principles, patterns, and practices that apply are all addressed along the way.

This is a live thirty-minute demonstration.
It will take you a tad longer to follow the
instructions here if you do it yourself, because you need to read the instructions
and understand the commentary :)

The full solution is on the *main* branch.
The solution for each part are on feature branches:

* feature/factory
* feature/dependencyinjection
* feature/springbootapi

## License

The code is licensed under the MIT license. You may use and modify all or part of it as you choose, as long as attribution to the source is provided per the license. See the details in the [license file](./LICENSE.md) or at the [Open Source Initiative](https://opensource.org/licenses/MIT)

## Software Configuration

The project uses Gradle for the build.
The library code targets Java 17 and above, and uses Spring Boot 2.7.2.
The JUnit tests are built with JUnit-5 (A.K.A. Jupiter).

# Activity Instructions

## Part 0 - Review

In the live demonstration there is a quick review of principles and patterns:

* *SOLID, DRY,* and *GRASP (specifically "information expert")* software design principles.
* The three major benefits of *Dependency Injection* in order of increasing importance:
	* DI supports injecting test doubles, making testing in isolation possible.
	* DI supports dynamic decision-making, production dependencies can be selected at runtime.
	* DI provides a flexible, dynamic, and robust architecture (via the Open/Closed principle).


## Part 1 - Application classes and factory

This part of the activity builds a checkout controller which delegates to a checkout
service, which leverages a credit card authorizer for a purchase.
main will leverage a factory class which provides instances of the three classes:

![](.common/factory.png?raw=true)

1. Open the project in your favorite IDE.
1. Switch to the *develop* branch, it is the clean, empty starting point.
1. Expand *main/src/java/com.wonderfulwidgets.thirtyminutestospringboot*.
1. Create a new package *service*.
1. Create a new Java class *CheckoutService*.
1. CheckoutService needs a credit card authorization component, add the instance variable:
	```
	public class CheckoutService {

		private CardAuthorizer cardAuthorizer;
	}
	```
1. Add the CheckoutAuthorizer Java class in the service package.
This is a minimal example, so there is zero functionality:
	```
	public class CardAuthorizer {

	}
	```
1. Back in the CheckoutService class add a constructor that uses a factory class with
static factory methods to get the CheckoutAuthorizer:
	```
	public CheckoutService() {

		cardAuthorizer = Factory.getCardAuthorizer();
	}
	```
1. Create the *Factory* class and method in the service package.
All of the functionality (dynamically selecting the card authorizer to return)
is not being shown to keep the example simple and focused:
	```
	public class Factory {

		public static CardAuthorizer getCardAuthorizer() {

			return new CardAuthorizer();
		}
	}
	```
1. Create a *api* package parallel to the *service* package.
1. In the api package create a new class CheckoutController.
1. CheckoutController needs a CheckoutService, so add an instance variable and the import:
	```
	import com.wonderfulwidgets.thirtyminutestospringboot.service.CheckoutService;

	public class CheckoutController {

    	private CheckoutService checkoutService;
	```
1. Set up the controller to call the factory to get the CheckoutService instance, and don't
forget to import the Factory:
	```
    public CheckoutController() {

        checkoutService = Factory.getCheckoutService();
    }
	```
1. Add the method to the factory:
	```
	public static CheckoutService getCheckoutService() {
        
        return new CheckoutService();
    }
	```
1. Use the factory in *main* to get the CheckoutService:
	```
    public static void main(String[] args) {

        CheckoutController checkoutController = Factory.getCheckoutController();
    }
	```
1. Add the factory method getCheckoutController, and don't forget to import the class into
the factory:
	```
	public static CheckoutController getCheckoutController() {

        return new CheckoutController();
    }
	```

## Part 2 - Leverage Dependency Injection

The factory should not dictate the dependencies, that blocks unit testing and dynamic
selection of objects at production runtime.
And, the classes should not have hardwired dependency on the factory!

The CheckoutService and CheckoutController are not likely to have multiple versions.
The factory is very likely to see different implementations of card authorizers, so there
should be an interface in front of those classes and the client code should use
that abstraction instead of concretions (the *dependency inversion* principle!).

17. In the *service* package create an interface ICardAuthorizer:
	```
	package com.wonderfulwidgets.thirtyminutestospringboot.service;

	public interface ICardAuthorizer {
	}
	```
1. Refactor CardAuthorizer to implement the interface:
	```
	public class CardAuthorizer implements ICardAuthorizer {
	```
1. Change the factory to return an instance of the interface:
	```
	public static ICardAuthorizer getCardAuthorizer() {
	```
1. Refactor CheckoutService to use the interface:
	```
	private ICardAuthorizer cardAuthorizer;
	```
1. Implement DI by injecting ICardAuthorizer into CheckoutService:
	```
	public CheckoutService(ICardAuthorizer cardAuthorizer) {

		this.cardAuthorizer = cardAuthorizer;
	}
	```
1. Update the factory to inject a passed ICardAuthorizer:
	```
	public static CheckoutService getCheckoutService(ICardAuthorizer cardAuthorizer) {

        return new CheckoutService(cardAuthorizer);
    }
	```
1. Inject the CheckoutService into the CheckoutController:
	```
	public CheckoutController(CheckoutService checkoutService) {

        this.checkoutService = checkoutService;
    }
	```
1. Fix the factory method to support the CardService injection:
	```
	public static CheckoutController getCheckoutController(CheckoutService checkoutService) {

        return new CheckoutController(checkoutService);
    }
	```
1. Refactor main to be a container for all the dependencies and handle the choices:
	```
	public static void main(String[] args) {

        ICardAuthorizer cardAuthorizer = Factory.getCardAuthorizer();
        CheckoutService checkoutService = Factory.getCheckoutService(cardAuthorizer);
        CheckoutController checkoutController = Factory.getCheckoutController(checkoutService);
    }
	```

## Part 3 - Build the API with Spring Boot

The Spring Boot framework is designed to do the heavy lifting of creating an API.
Spring encompases a factory, container, and a controller to manage them *and* the
HTTP requests.

The controller scans the app classes looking for classes, methods, and fields with
Spring annotations: @Bean, @Component, @Service, @RestController, @Autowired, etc.
Once Spring knows what everything is, when something is needed it can create it and link all
the required dependencies.

Most Spring beans, components, and services, etc. are single instances which are shared.
When something is needed the Spring controller checks the container first for previously created and cached components, and makes it from its scanned knowledge if it does not find it.

![](.common/springboot.png?raw=true)

Once everything is established the Spring controller (a *front-end controller pattern*)
will listen for HTTP requests and route them to the proper endpoints based on the path.

Components annotated with @RestController are not shared, a new clean component is created on each request.
Other components, such as thosed marked with @Service, are shared and will be injected from the cache or created as necessary to set up the rest controller and its dependencies.

26. Annotate CardAuthorizer to be a Spring commponent, and import the annotation class.
The annotation allows Spring to locate or build an instance when it needs to be injected:
	```
	import org.springframework.stereotype.Component;

	@Component
	public class CardAuthorizer implements ICardAuthorizer {

	}
	```
1. Annote CheckoutService to be a Spring service.
A service is a *singleton*, one copy is shared by every controller:
	```
	@Service
	public class CheckoutService {
	```
1. Annote CheckoutController to be a Spring REST controller.
A brand-new controller is instantiated on every request, it is not shared.
	```
	@RestController
	public class CheckoutController {
	```
1. Replace the factory calls in main, with a single call that launches Spring.
Remove any unnecessary import statements. 
This starts the scan of the application for annotations, builds the components, and
handles the dependency injection:
	```
	public static void main(String[] args) {

        SpringApplication.run(ThirtyMinutesToSpringBootApplication.class, args);
    }
	```
1. Delete the Factory class, it isn't needed anymore.
Make sure to remove any remaining import statements in the other classes that referenced the Factory.
1. Add a (dummy example) checkout method to the CheckoutService:
	```
    public boolean checkout(String cardNumber) {
        
        return true;
    }
	```
1. Use the @RequestMapping annotation to add the API context path to CheckoutController:
	```
	@RestController
	@RequestMapping("/api/v1.0")
	public class CheckoutController {
	```
1. Add the API endpoint to CheckoutController.
Spring will route the matching HTTP requests to this controller method.
	```
	@PostMapping("/checkout/{cardnumber}")
    public boolean checkout() {

    }
	```
1. Use the @PathVariable annotation in the checkout method parameter list to get the cardNumber value from the path, and use it to call the checkout method in the service:
	```
	@PostMapping("/checkout/{cardnumber}")
    public ResponseEntity<String> checkout(@PathVariable("cardnumber") String cardNumber) {

        boolean result = checkoutService.checkout(cardNumber);
    }
	```
1. Handle the result and return an appropriate HTTP response:
	```
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
	```

Unit test Spring REST controllers by mocking the dependencies for the controller, creating it,
and looking at the results of calling the endpoint methods.
The Spring annotations are irrelevant and will not interfere with the unit tests.

Integration tests are performed by using a Spring test runner and load a mock context
before the test starts, and it would look something like this in the test class:

```
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ApplicationConfig.class })
@WebAppConfiguration
public class CheckoutIntegrationTest {
```

This topic is outside the scope of this activity, but this article at Baeldung would
be a good place to start: https://www.baeldung.com/integration-testing-in-spring.

<hr>

## Support

If you found this project helpful, and and you would like to see more free projects like this,
then please consider
a contribution to *Joel's Coffee Fund* at **Smallrock Internet** to help keep the good stuff coming :)<br />

[![Donate](./.common/Donate-Paypal.svg)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=XPUGVGZZ8RUAA)


Copyright © 2022 Joel Mussman. All rights reserved.