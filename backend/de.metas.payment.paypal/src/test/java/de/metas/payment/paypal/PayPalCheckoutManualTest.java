package de.metas.payment.paypal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;

import com.braintreepayments.http.HttpRequest;
import com.braintreepayments.http.HttpResponse;
import com.braintreepayments.http.exceptions.HttpException;
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
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersAuthorizeRequest;
import com.paypal.orders.OrdersCreateRequest;
import com.paypal.orders.OrdersGetRequest;
import com.paypal.orders.PurchaseUnitRequest;
import com.paypal.orders.ShippingDetails;
import com.paypal.payments.AuthorizationsCaptureRequest;
import com.paypal.payments.Capture;
import com.paypal.payments.CaptureRequest;
import com.paypal.payments.CapturesRefundRequest;
import com.paypal.payments.Refund;
import com.paypal.payments.RefundRequest;

import de.metas.email.templates.MailTemplateId;
import de.metas.payment.paypal.config.PayPalConfig;
import de.metas.payment.paypal.logs.PayPalCreateLogRequest;
import de.metas.payment.paypal.logs.PayPalCreateLogRequest.PayPalCreateLogRequestBuilder;
import de.metas.payment.paypal.logs.PayPalLogRepository;
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
		AdempiereTestHelper.get().init();

		new PayPalCheckoutManualTest().run();
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("Done");
	}

	private final PayPalConfig config;
	private final PayPalHttpClient client;
	private final PayPalLogRepository logsRepo;

	public PayPalCheckoutManualTest()
	{
		final TestPayPalConfigProvider configProvider = TestPayPalConfigProvider.builder()
				.approveMailTemplateId(MailTemplateId.ofRepoId(12345)) // dummy
				.build();

		config = configProvider.getConfig();
		System.out.println("Using " + config);

		this.client = createPayPalHttpClient(config);

		logsRepo = new PayPalLogRepository(Optional.empty());
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
			final HttpResponse<Order> response = createOrder();
			final Order order = response.result();
			final String approveUrl = extractApproveUrl(order);
			orderId = order.id();

			System.out.println("Created Successfully: orderId=" + orderId);
			System.out.println("-------------------------------------------------------------------------");
			System.out.println("Copy approve link and paste it in browser: " + approveUrl);
			System.out.println("Login with buyer account and follow the instructions.");
			System.out.println("Once approved hit enter...");
			System.out.println("-------------------------------------------------------------------------");
			System.in.read();
		}

		//
		// Authorizing created order
		String authId = null;
		{
			System.out.println("-------------------------------------------------------------------------");
			System.out.println("Checking before authorizing Order... orderId=" + orderId);
			getOrder(orderId);

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
			final HttpResponse<Capture> captureOrderResponse = captureOrder(authId, "200");
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
			System.out.println("-------------------------------------------------------------------------");
			System.out.println("Checking before refunding: ");
			getOrder(orderId);

			System.out.println();
			System.out.println("-------------------------------------------------------------------------");
			System.out.println("Refunding Order... captureId=" + captureId);
			final HttpResponse<Refund> refundHttpResponse = refundOrder(captureId, "20");
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

	private static String extractApproveUrl(final Order order)
	{
		return extractUrl(order, "approve");
	}

	private static String extractUrl(final Order order, final String rel)
	{
		for (final LinkDescription link : order.links())
		{
			if (rel.contentEquals(link.rel()))
			{
				return link.href();
			}
		}

		throw new AdempiereException("No URL found for `" + rel + "`");
	}

	/**
	 * Method to create complete order body with <b>AUTHORIZE</b> intent
	 *
	 * @return OrderRequest with created order request
	 */
	private OrderRequest buildCompleteRequestBody()
	{
		final String orderApproveCallbackUrl = config.getOrderApproveCallbackUrl("http://example.com");
		
		final OrderRequest orderRequest = new OrderRequest();
		orderRequest.intent("AUTHORIZE");

		final ApplicationContext applicationContext = new ApplicationContext()
				.brandName("metasfresh")
				.landingPage("BILLING")
				.cancelUrl(orderApproveCallbackUrl)
				.returnUrl(orderApproveCallbackUrl)
				.userAction("CONTINUE")
				.shippingPreference("SET_PROVIDED_ADDRESS");
		orderRequest.applicationContext(applicationContext);

		final List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
		final PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
				.referenceId("orderReferenceId")
				.description("order descriptions")
				.customId("customId")
				.softDescriptor("SoftDescription")
				.amount(new AmountWithBreakdown()
						.currencyCode("EUR")
						.value("220.00")
						.breakdown(new AmountBreakdown()
								.itemTotal(new Money().currencyCode("EUR").value("180.00"))
								.shipping(new Money().currencyCode("EUR").value("20.00"))
								.handling(new Money().currencyCode("EUR").value("10.00"))
								.taxTotal(new Money().currencyCode("EUR").value("20.00"))
								.shippingDiscount(new Money().currencyCode("EUR").value("10.00"))))
				.items(ImmutableList.of(
						new Item()
								.name("productName1")
								.description("Product description 1")
								.sku("sku01")
								.unitAmount(new Money().currencyCode("EUR").value("90.00"))
								.tax(new Money().currencyCode("EUR").value("10.00"))
								.quantity("1")
								.category("PHYSICAL_GOODS"),
						new Item().name("productName2")
								.description("product description 2")
								.sku("sku02")
								.unitAmount(new Money().currencyCode("EUR").value("45.00"))
								.tax(new Money().currencyCode("EUR").value("5.00"))
								.quantity("2")
								.category("PHYSICAL_GOODS")))
				.shipping(new ShippingDetails()
						.name(new Name().fullName("John Doe"))
						.addressPortable(new AddressPortable()
								.addressLine1("Franz Liszt 4")
								.addressLine2("ap. 5")
								.adminArea2("Timisoara")
								.adminArea1("CA")
								.postalCode("300081")
								.countryCode("RO")));

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
		final String orderApproveCallbackUrl = config.getOrderApproveCallbackUrl("http://example.com");
		
		final OrderRequest orderRequest = new OrderRequest();
		orderRequest.intent("AUTHORIZE");
		final ApplicationContext applicationContext = new ApplicationContext()
				.returnUrl(orderApproveCallbackUrl)
				.cancelUrl(orderApproveCallbackUrl);
		orderRequest.applicationContext(applicationContext);
		final List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
		final PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
				.amount(new AmountWithBreakdown().currencyCode("EUR").value("220.00"));
		purchaseUnitRequests.add(purchaseUnitRequest);
		orderRequest.purchaseUnits(purchaseUnitRequests);
		return orderRequest;
	}

	private Order getOrder(String orderId) throws IOException
	{
		final HttpResponse<Order> response = executeRequest(new OrdersGetRequest(orderId));
		return response.result();
	}

	/**
	 * Method to create order with complete payload
	 *
	 * @return HttpResponse<Order> response received from API
	 * @throws IOException Exceptions from API if any
	 */
	private HttpResponse<Order> createOrder() throws IOException
	{
		final OrdersCreateRequest request = new OrdersCreateRequest();
		request.header("prefer", "return=representation");
		request.requestBody(buildCompleteRequestBody());
		//
		final HttpResponse<Order> response = executeRequest(request);
		return response;

		// final boolean isReponseCodeOK = response.statusCode() == 201;
		// System.out.println(" ==> Order Create response: " + (isReponseCodeOK ? "OK" : "ERROR"));
		//
		// //
		// final Order responseOrder = response.result();
		// if (responseOrder != null)
		// {
		// System.out.println("Status: " + responseOrder.status());
		// System.out.println("Order ID: " + responseOrder.id());
		// System.out.println("Intent: " + responseOrder.intent());
		// System.out.println("Links: ");
		// for (final LinkDescription link : responseOrder.links())
		// {
		// System.out.println("\t" + link.rel() + ": " + link.href() + "\tCall Type: " + link.method());
		// }
		// System.out.println("Total Amount: " + responseOrder.purchaseUnits().get(0).amount().currencyCode()
		// + " " + responseOrder.purchaseUnits().get(0).amount().value());
		// }
		// else
		// {
		// System.out.println("NO RESPONSE ORDER");
		// }
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
		final HttpResponse<Order> response = executeRequest(request);

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
		}

		return response;
	}

	/**
	 * Method to authorize order after creation
	 *
	 * @param orderId Valid Approved Order ID from createOrder response
	 * @return HttpResponse<Order> response received from API
	 * @throws IOException Exceptions from API if any
	 */
	private HttpResponse<Order> authorizeOrder(final String orderId) throws IOException
	{
		final OrdersAuthorizeRequest request = new OrdersAuthorizeRequest(orderId);
		return executeRequest(request);

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
	}

	private <T> HttpResponse<T> executeRequest(final HttpRequest<T> request) throws IOException
	{
		final PayPalCreateLogRequestBuilder log = PayPalCreateLogRequest.builder();

		try
		{
			log.request(request);

			final HttpResponse<T> response = client.execute(request);
			log.response(response);
			return response;
		}
		catch (final HttpException ex)
		{
			log.response(ex);
			throw ex;
		}
		catch (final IOException ex)
		{
			log.response(ex);
			throw ex;
		}
		finally
		{
			logsRepo.log(log.build());
		}
	}

	/**
	 * Method to capture order after authorization
	 *
	 * @param authId Authorization ID from authorizeOrder response
	 * @return HttpResponse<Capture> response received from API
	 * @throws IOException Exceptions from API if any
	 */
	private HttpResponse<Capture> captureOrder(final String authId, String amount) throws IOException
	{
		final AuthorizationsCaptureRequest request = new AuthorizationsCaptureRequest(authId)
				.requestBody(new CaptureRequest()
						.amount(new com.paypal.payments.Money()
								.currencyCode("EUR")
								.value(amount))
						.finalCapture(Boolean.TRUE));

		final HttpResponse<Capture> response = executeRequest(request);
		return response;

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

	}

	/**
	 * Method to Refund the Capture. valid capture Id should be passed.
	 *
	 * @param captureId Capture ID from authorizeOrder response
	 * @param debug true = print response data
	 * @return HttpResponse<Capture> response received from API
	 * @throws IOException Exceptions from API if any
	 */
	public HttpResponse<Refund> refundOrder(final String captureId, final String amount) throws IOException
	{
		final CapturesRefundRequest request = new CapturesRefundRequest(captureId);
		request.prefer("return=representation");
		request.requestBody(new RefundRequest()
				.amount(new com.paypal.payments.Money()
						.value(amount)
						.currencyCode("EUR")));

		final HttpResponse<Refund> response = executeRequest(request);
		return response;

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
	}
}
