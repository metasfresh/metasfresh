package de.metas.paypalplus.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.json.JSONObject;

import com.braintreepayments.http.HttpResponse;
import com.braintreepayments.http.serializer.Json;
import com.google.common.collect.ImmutableList;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.AddressPortable;
import com.paypal.orders.AmountBreakdown;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.ApplicationContext;
import com.paypal.orders.Item;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Money;
import com.paypal.orders.Name;
import com.paypal.orders.Order;
import com.paypal.orders.OrderActionRequest;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersAuthorizeRequest;
import com.paypal.orders.OrdersCreateRequest;
import com.paypal.orders.PurchaseUnitRequest;
import com.paypal.orders.ShippingDetails;
import com.paypal.payments.AuthorizationsCaptureRequest;
import com.paypal.payments.Capture;
import com.paypal.payments.CapturesRefundRequest;
import com.paypal.payments.Refund;
import com.paypal.payments.RefundRequest;

/*
 * #%L
 * de.metas.payment.paypalplus
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class PayPalCheckoutTest
{
	public static void main(final String[] args)
	{
		new PayPalCheckoutTest().run();
	}

	/**
	 * Setting up PayPal SDK environment with PayPal Access credentials. For demo
	 * purpose, we are using SandboxEnvironment. In production this will be
	 * LiveEnvironment.
	 */
	private final PayPalEnvironment environment = new PayPalEnvironment.Sandbox(
			System.getProperty("PAYPAL_CLIENT_ID") != null ? System.getProperty("PAYPAL_CLIENT_ID")
					: "AbrU-xbGF2BJHOaAaF8yC9GZRHCSiNA6UY61kI8P7Ipz5ZZTvXBHTY-nzeIl9eh7xFtsoua1brYcNlQx",
			System.getProperty("PAYPAL_CLIENT_SECRET") != null ? System.getProperty("PAYPAL_CLIENT_SECRET")
					: "EObqX1HbD-LhiHQ-oI3ZdAGnDSluIekyjT2ZHxvL0L924d_c3DA3gH0Qzh8KFpShQemTo3A-qS-5X7oT");

	private final PayPalHttpClient client = new PayPalHttpClient(environment);

	private void run()
	{
		try
		{
			// Creating an order
			HttpResponse<Order> orderResponse = createOrder(false);
			String orderId = "";
			System.out.println("Creating Order...");
			if (orderResponse.statusCode() == 201)
			{
				orderId = orderResponse.result().id();
				System.out.println("Order ID: " + orderId);
				System.out.println("Links:");
				for (final LinkDescription link : orderResponse.result().links())
				{
					System.out.println("\t" + link.rel() + ": " + link.href());
				}
			}
			System.out.println("Created Successfully\n");
			System.out.println("Copy approve link and paste it in browser. Login with buyer account and follow the instructions.\nOnce approved hit enter...");
			System.in.read();

			// Authorizing created order
			System.out.println("Authorizing Order...");
			orderResponse = authorizeOrder(orderId, false);
			String authId = "";
			if (orderResponse.statusCode() == 201)
			{
				System.out.println("Authorized Successfully\n");
				authId = orderResponse.result().purchaseUnits().get(0).payments().authorizations().get(0).id();
			}

			// Capturing authorized order
			System.out.println("Capturing Order...");
			final HttpResponse<Capture> captureOrderResponse = captureOrder(authId, false);
			if (orderResponse.statusCode() == 201)
			{
				System.out.println("Captured Successfully");
				System.out.println("Status Code: " + captureOrderResponse.statusCode());
				System.out.println("Status: " + captureOrderResponse.result().status());
				System.out.println("Capture ID: " + captureOrderResponse.result().id());
				System.out.println("Links: ");
				for (final com.paypal.payments.LinkDescription link : captureOrderResponse.result().links())
				{
					System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
				}
			}
			System.out.println();
			System.out.println("Refunding Order...");
			final HttpResponse<Refund> refundHttpResponse = refundOrder(captureOrderResponse.result().id(), false);
			if (refundHttpResponse.statusCode() == 201)
			{
				System.out.println("Refunded Successfully");
				System.out.println("Status Code: " + refundHttpResponse.statusCode());
				System.out.println("Status: " + refundHttpResponse.result().status());
				System.out.println("Order ID: " + refundHttpResponse.result().id());
				System.out.println("Links: ");
				for (final com.paypal.payments.LinkDescription link : refundHttpResponse.result().links())
				{
					System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
				}
			}

		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Method to create complete order body with <b>AUTHORIZE</b> intent
	 *
	 * @return OrderRequest with created order request
	 */
	private OrderRequest buildCompleteRequestBody()
	{
		final OrderRequest orderRequest = new OrderRequest();
		orderRequest.intent("AUTHORIZE");

		final ApplicationContext applicationContext = new ApplicationContext()
				.brandName("metasfresh")
				.landingPage("BILLING")
				.cancelUrl("https://www.example.com")
				.returnUrl("https://www.example.com")
				.userAction("CONTINUE")
				.shippingPreference("SET_PROVIDED_ADDRESS");
		orderRequest.applicationContext(applicationContext);

		final List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
		final PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
				.referenceId("PUHF")
				.description("Sporting Goods")
				.customId("CUST-HighFashions")
				.softDescriptor("HighFashions")
				.amount(new AmountWithBreakdown()
						.currencyCode("USD")
						.value("220.00")
						.breakdown(new AmountBreakdown().itemTotal(new Money().currencyCode("USD").value("180.00"))
								.shipping(new Money().currencyCode("USD").value("20.00"))
								.handling(new Money().currencyCode("USD").value("10.00"))
								.taxTotal(new Money().currencyCode("USD").value("20.00"))
								.shippingDiscount(new Money().currencyCode("USD").value("10.00"))))
				.items(ImmutableList.of(
						new Item()
								.name("T-shirt")
								.description("Green XL")
								.sku("sku01")
								.unitAmount(new Money().currencyCode("USD").value("90.00"))
								.tax(new Money().currencyCode("USD").value("10.00"))
								.quantity("1")
								.category("PHYSICAL_GOODS"),
						new Item().name("Shoes")
								.description("Running, Size 10.5")
								.sku("sku02")
								.unitAmount(new Money().currencyCode("USD").value("45.00"))
								.tax(new Money().currencyCode("USD").value("5.00"))
								.quantity("2")
								.category("PHYSICAL_GOODS")))
				.shipping(new ShippingDetails()
						.name(new Name().fullName("John Doe"))
						.addressPortable(new AddressPortable()
								.addressLine1("123 Townsend St")
								.addressLine2("Floor 6")
								.adminArea2("San Francisco")
								.adminArea1("CA")
								.postalCode("94107")
								.countryCode("US")));

		purchaseUnitRequests.add(purchaseUnitRequest);
		orderRequest.purchaseUnits(purchaseUnitRequests);

		return orderRequest;
	}

	/**
	 * Method to create minimum required order body with <b>AUTHORIZE</b> intent
	 *
	 * @return OrderRequest with created order request
	 */
	private OrderRequest buildMinimumRequestBody()
	{
		final OrderRequest orderRequest = new OrderRequest();
		orderRequest.intent("AUTHORIZE");
		final ApplicationContext applicationContext = new ApplicationContext()
				.cancelUrl("https://www.example.com").returnUrl("https://www.example.com");
		orderRequest.applicationContext(applicationContext);
		final List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
		final PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
				.amount(new AmountWithBreakdown().currencyCode("USD").value("220.00"));
		purchaseUnitRequests.add(purchaseUnitRequest);
		orderRequest.purchaseUnits(purchaseUnitRequests);
		return orderRequest;
	}

	/**
	 * Method to create order with complete payload
	 *
	 * @param debug true = print response data
	 * @return HttpResponse<Order> response received from API
	 * @throws IOException Exceptions from API if any
	 */
	private HttpResponse<Order> createOrder(final boolean debug) throws IOException
	{
		final OrdersCreateRequest request = new OrdersCreateRequest();
		request.header("prefer", "return=representation");
		request.requestBody(buildCompleteRequestBody());
		final HttpResponse<Order> response = client.execute(request);
		if (debug)
		{
			if (response.statusCode() == 201)
			{
				System.out.println("Order with Complete Payload: ");
				System.out.println("Status Code: " + response.statusCode());
				System.out.println("Status: " + response.result().status());
				System.out.println("Order ID: " + response.result().id());
				System.out.println("Intent: " + response.result().intent());
				System.out.println("Links: ");
				for (final LinkDescription link : response.result().links())
				{
					System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
				}
				System.out.println("Total Amount: " + response.result().purchaseUnits().get(0).amount().currencyCode()
						+ " " + response.result().purchaseUnits().get(0).amount().value());
				System.out.println("Full response body:");
				System.out.println(toJsonString(response.result()));
			}
		}
		return response;
	}

	/**
	 * Method to create order with minimum required payload
	 *
	 * @param debug true = print response data
	 * @return HttpResponse<Order> response received from API
	 * @throws IOException Exceptions from API if any
	 */
	private HttpResponse<Order> createOrderWithMinimumPayload(final boolean debug) throws IOException
	{
		final OrdersCreateRequest request = new OrdersCreateRequest();
		request.header("prefer", "return=representation");
		request.requestBody(buildMinimumRequestBody());
		final HttpResponse<Order> response = client.execute(request);
		if (debug)
		{
			if (response.statusCode() == 201)
			{
				System.out.println("Order with Minimum Payload: ");
				System.out.println("Status Code: " + response.statusCode());
				System.out.println("Status: " + response.result().status());
				System.out.println("Order ID: " + response.result().id());
				System.out.println("Intent: " + response.result().intent());
				System.out.println("Links: ");
				for (final LinkDescription link : response.result().links())
				{
					System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
				}
				System.out.println("Total Amount: " + response.result().purchaseUnits().get(0).amount().currencyCode()
						+ " " + response.result().purchaseUnits().get(0).amount().value());
				System.out.println("Full response body:");
				System.out.println(toJsonString(response.result()));
			}
		}
		return response;
	}

	/**
	 * Method to authorize order after creation
	 *
	 * @param orderId Valid Approved Order ID from createOrder response
	 * @param debug true = print response data
	 * @return HttpResponse<Order> response received from API
	 * @throws IOException Exceptions from API if any
	 */
	private HttpResponse<Order> authorizeOrder(final String orderId, final boolean debug) throws IOException
	{
		final OrdersAuthorizeRequest request = new OrdersAuthorizeRequest(orderId);
		request.requestBody(new OrderActionRequest());
		final HttpResponse<Order> response = client.execute(request);
		if (debug)
		{
			System.out.println("Authorization Ids:");
			response.result().purchaseUnits().forEach(purchaseUnit -> purchaseUnit.payments().authorizations().stream()
					.map(authorization -> authorization.id()).forEach(System.out::println));
			System.out.println("Link Descriptions: ");
			for (final LinkDescription link : response.result().links())
			{
				System.out.println("\t" + link.rel() + ": " + link.href());
			}
			System.out.println("Full response body:");
			System.out.println(toJsonString(response.result()));
		}
		return response;
	}

	private static String toJsonString(final Object obj)
	{
		try
		{
			return new JSONObject(new Json().serialize(obj)).toString(4);
		}
		catch (final Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	/**
	 * Method to capture order after authorization
	 *
	 * @param authId Authorization ID from authorizeOrder response
	 * @param debug true = print response data
	 * @return HttpResponse<Capture> response received from API
	 * @throws IOException Exceptions from API if any
	 */
	private HttpResponse<Capture> captureOrder(final String authId, final boolean debug) throws IOException
	{
		final AuthorizationsCaptureRequest request = new AuthorizationsCaptureRequest(authId);
		request.requestBody(new OrderRequest());
		final HttpResponse<Capture> response = client.execute(request);
		if (debug)
		{
			System.out.println("Status Code: " + response.statusCode());
			System.out.println("Status: " + response.result().status());
			System.out.println("Capture ID: " + response.result().id());
			System.out.println("Links: ");
			for (final com.paypal.payments.LinkDescription link : response.result().links())
			{
				System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
			}
			System.out.println("Full response body:");
			System.out.println(toJsonString(response.result()));
		}
		return response;
	}

	/**
	 * Creating empty body for Refund request. This request body can be created with
	 * correct values as per the need.
	 *
	 * @return OrderRequest request with empty body
	 */
	private RefundRequest buildRefundRequest()
	{
		final RefundRequest refundRequest = new RefundRequest();
		final com.paypal.payments.Money money = new com.paypal.payments.Money();
		money.currencyCode("USD");
		money.value("20.00");
		refundRequest.amount(money);
		return refundRequest;
	}

	/**
	 * Method to Refund the Capture. valid capture Id should be passed.
	 *
	 * @param captureId Capture ID from authorizeOrder response
	 * @param debug true = print response data
	 * @return HttpResponse<Capture> response received from API
	 * @throws IOException Exceptions from API if any
	 */
	public HttpResponse<Refund> refundOrder(final String captureId, final boolean debug) throws IOException
	{
		final CapturesRefundRequest request = new CapturesRefundRequest(captureId);
		request.prefer("return=representation");
		request.requestBody(buildRefundRequest());
		final HttpResponse<Refund> response = client.execute(request);
		if (debug)
		{
			System.out.println("Status Code: " + response.statusCode());
			System.out.println("Status: " + response.result().status());
			System.out.println("Refund Id: " + response.result().id());
			System.out.println("Links: ");
			for (final com.paypal.payments.LinkDescription link : response.result().links())
			{
				System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
			}
			System.out.println("Full response body:");
			System.out.println(toJsonString(response.result()));
		}
		return response;
	}

}
