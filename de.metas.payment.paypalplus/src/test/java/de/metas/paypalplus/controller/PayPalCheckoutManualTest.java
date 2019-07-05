package de.metas.paypalplus.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.json.JSONObject;

import com.braintreepayments.http.Headers;
import com.braintreepayments.http.HttpRequest;
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

import de.metas.paypalplus.PayPalConfig;
import lombok.NonNull;

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

public class PayPalCheckoutManualTest
{
	public static void main(final String[] args) throws Exception
	{
		new PayPalCheckoutManualTest().run();
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("Done");
	}

	private final PayPalHttpClient client;

	public PayPalCheckoutManualTest()
	{
		final TestPayPalConfigProvider configProvider = new TestPayPalConfigProvider();
		final PayPalConfig config = configProvider.getConfig();
		System.out.println("Using " + config);

		this.client = createPayPalHttpClient(config);
	}

	private static PayPalHttpClient createPayPalHttpClient(final PayPalConfig config)
	{
		final PayPalEnvironment environment = createPayPalEnvironment(config);
		return new PayPalHttpClient(environment);
	}

	private static PayPalEnvironment createPayPalEnvironment(@NonNull final PayPalConfig config)
	{
		if (!config.isSandbox())
		{
			return new PayPalEnvironment(
					config.getClientId(),
					config.getClientSecret(),
					config.getBaseUrl(),
					config.getWebUrl());
		}
		else
		{
			return new PayPalEnvironment.Sandbox(
					config.getClientId(),
					config.getClientSecret());
		}
	}

	private void run() throws IOException
	{
		//
		// Creating an order
		String orderId = null;
		{
			System.out.println("Creating Order...");
			HttpResponse<Order> orderResponse = createOrder();
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
			System.out.println("-------------------------------------------------------------------------");
			System.out.println("Copy approve link and paste it in browser. Login with buyer account and follow the instructions.\nOnce approved hit enter...");
			System.out.println("-------------------------------------------------------------------------");
			System.in.read();
		}

		//
		// Authorizing created order
		String authId = null;
		{
			System.out.println("-------------------------------------------------------------------------");
			System.out.println("Authorizing Order... orderId=" + orderId);
			HttpResponse<Order> orderResponse = authorizeOrder(orderId);
			if (orderResponse.statusCode() == 201)
			{
				System.out.println("Authorized Successfully\n");
				authId = orderResponse.result().purchaseUnits().get(0).payments().authorizations().get(0).id();
			}
		}

		//
		// Capturing authorized order
		String captureId = null;
		{
			System.out.println("-------------------------------------------------------------------------");
			System.out.println("Capturing Order... authId=" + authId);
			final HttpResponse<Capture> captureOrderResponse = captureOrder(authId);
			if (captureOrderResponse.statusCode() == 201)
			{
				captureId = captureOrderResponse.result().id();

				System.out.println("Captured Successfully");
				System.out.println("Status Code: " + captureOrderResponse.statusCode());
				System.out.println("Status: " + captureOrderResponse.result().status());
				System.out.println("Capture ID: " + captureId);
				System.out.println("Links: ");
				for (final com.paypal.payments.LinkDescription link : captureOrderResponse.result().links())
				{
					System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
				}
			}
		}

		//
		// Refunding the order
		{
			System.out.println();
			System.out.println("-------------------------------------------------------------------------");
			System.out.println("Refunding Order... captureId=" + captureId);
			final HttpResponse<Refund> refundHttpResponse = refundOrder(captureId);
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
	}

	/**
	 * Method to create complete order body with <b>AUTHORIZE</b> intent
	 *
	 * @return OrderRequest with created order request
	 */
	private static OrderRequest buildCompleteRequestBody()
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
	private static OrderRequest buildMinimumRequestBody()
	{
		final OrderRequest orderRequest = new OrderRequest();
		orderRequest.intent("AUTHORIZE");
		final ApplicationContext applicationContext = new ApplicationContext()
				.cancelUrl("https://www.example.com")
				.returnUrl("https://www.example.com");
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
	private HttpResponse<Order> createOrder() throws IOException
	{
		final OrdersCreateRequest request = new OrdersCreateRequest();
		request.header("prefer", "return=representation");
		final OrderRequest requestBody = buildCompleteRequestBody();
		request.requestBody(requestBody);
		dumpHttpRequest(request);

		//
		final HttpResponse<Order> response = client.execute(request);
		final boolean isReponseCodeOK = response.statusCode() == 201;
		System.out.println("Order Create response: " + (isReponseCodeOK ? "OK" : "ERROR"));
		dumpHttpResponse(response);

		//
		final Order responseOrder = response.result();
		if (responseOrder != null)
		{
			System.out.println("Status: " + responseOrder.status());
			System.out.println("Order ID: " + responseOrder.id());
			System.out.println("Intent: " + responseOrder.intent());
			System.out.println("Links: ");
			for (final LinkDescription link : responseOrder.links())
			{
				System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
			}
			System.out.println("Total Amount: " + responseOrder.purchaseUnits().get(0).amount().currencyCode()
					+ " " + responseOrder.purchaseUnits().get(0).amount().value());
			System.out.println("Full response body:");
			System.out.println(toJsonString(responseOrder));
		}
		else
		{
			System.out.println("NO RESPONSE ORDER");
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
	private HttpResponse<Order> createOrderWithMinimumPayload() throws IOException
	{
		final OrdersCreateRequest request = new OrdersCreateRequest();
		request.header("prefer", "return=representation");
		request.requestBody(buildMinimumRequestBody());
		final HttpResponse<Order> response = client.execute(request);

		if (response.statusCode() == 201)
		{
			final Order responseOrder = response.result();

			System.out.println("Order with Minimum Payload: ");
			System.out.println("Status Code: " + response.statusCode());
			System.out.println("Status: " + responseOrder.status());
			System.out.println("Order ID: " + responseOrder.id());
			System.out.println("Intent: " + responseOrder.intent());
			System.out.println("Links: ");
			for (final LinkDescription link : responseOrder.links())
			{
				System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
			}
			System.out.println("Total Amount:"
					+ " " + responseOrder.purchaseUnits().get(0).amount().currencyCode()
					+ " " + responseOrder.purchaseUnits().get(0).amount().value());
			System.out.println("Full response body:");
			System.out.println(toJsonString(responseOrder));
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
	private HttpResponse<Order> authorizeOrder(final String orderId) throws IOException
	{
		final OrdersAuthorizeRequest request = new OrdersAuthorizeRequest(orderId);
		request.requestBody(new OrderActionRequest());
		dumpHttpRequest(request);

		final HttpResponse<Order> response = client.execute(request);
		dumpHttpResponse(response);

		// System.out.println("Authorization Ids:");
		// final Order responseOrder = response.result();
		// responseOrder.purchaseUnits().forEach(purchaseUnit -> purchaseUnit.payments().authorizations().stream()
		// .map(authorization -> authorization.id()).forEach(System.out::println));
		// System.out.println("Link Descriptions: ");
		// for (final LinkDescription link : responseOrder.links())
		// {
		// System.out.println("\t" + link.rel() + ": " + link.href());
		// }
		// System.out.println("Full response body:");
		// System.out.println(toJsonString(responseOrder));

		return response;
	}

	/**
	 * Method to capture order after authorization
	 *
	 * @param authId Authorization ID from authorizeOrder response
	 * @param debug true = print response data
	 * @return HttpResponse<Capture> response received from API
	 * @throws IOException Exceptions from API if any
	 */
	private HttpResponse<Capture> captureOrder(final String authId) throws IOException
	{
		final AuthorizationsCaptureRequest request = new AuthorizationsCaptureRequest(authId);
		request.requestBody(new OrderRequest());
		dumpHttpRequest(request);

		final HttpResponse<Capture> response = client.execute(request);
		dumpHttpResponse(response);

		// System.out.println("Status Code: " + response.statusCode());
		// System.out.println("Status: " + response.result().status());
		// System.out.println("Capture ID: " + response.result().id());
		// System.out.println("Links: ");
		// for (final com.paypal.payments.LinkDescription link : response.result().links())
		// {
		// System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
		// }
		// System.out.println("Full response body:");
		// System.out.println(toJsonString(response.result()));

		return response;
	}

	/**
	 * Creating empty body for Refund request. This request body can be created with
	 * correct values as per the need.
	 *
	 * @return OrderRequest request with empty body
	 */
	private static RefundRequest buildRefundRequest()
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
	public HttpResponse<Refund> refundOrder(final String captureId) throws IOException
	{
		final CapturesRefundRequest request = new CapturesRefundRequest(captureId);
		request.prefer("return=representation");
		request.requestBody(buildRefundRequest());
		dumpHttpRequest(request);

		final HttpResponse<Refund> response = client.execute(request);
		dumpHttpResponse(response);

		// System.out.println("Status Code: " + response.statusCode());
		// System.out.println("Status: " + response.result().status());
		// System.out.println("Refund Id: " + response.result().id());
		// System.out.println("Links: ");
		// for (final com.paypal.payments.LinkDescription link : response.result().links())
		// {
		// System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
		// }
		// System.out.println("Full response body:");
		// System.out.println(toJsonString(response.result()));

		return response;
	}

	private static void dumpHttpRequest(final HttpRequest<?> request)
	{
		System.out.println("Request Path: " + request.path());
		System.out.println("Request Method: " + request.verb());
		System.out.println("Request - expected response type: " + request.responseClass());

		final Headers headers = request.headers();
		for (final String header : headers)
		{
			System.out.println("Request Header: " + header + "=" + headers.header(header));
		}
		System.out.println("Request Body: " + toJsonString(request.requestBody()));
	}

	private static void dumpHttpResponse(final HttpResponse<?> response)
	{
		System.out.println("Response Status: " + response.statusCode());
		final Headers headers = response.headers();
		for (final String header : headers)
		{
			System.out.println("Response Header: " + header + "=" + headers.header(header));
		}
		System.out.println("Response Body: " + toJsonString(response.result()));
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
}
