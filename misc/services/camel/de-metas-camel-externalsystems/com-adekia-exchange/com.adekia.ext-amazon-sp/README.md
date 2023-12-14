# swagger-java-client

Selling Partner API for Orders

- API version: v0
    - Build date: 2022-04-07T15:12:54.191139789+02:00[Europe/Paris]

The Selling Partner API for Orders helps you programmatically retrieve order information. These APIs let you develop
fast, flexible, custom applications in areas like order synchronization, order research, and demand-based decision
support tools.

For more information, please
visit [https://sellercentral.amazon.com/gp/mws/contactus.html](https://sellercentral.amazon.com/gp/mws/contactus.html)

*Automatically generated by the [Swagger Codegen](https://github.com/swagger-api/swagger-codegen)*

## Requirements

Building the API client library requires:

1. Java 1.7+
2. Maven/Gradle

## Installation

To install the API client library to your local Maven repository, simply execute:

```shell
mvn clean install
```

To deploy it to a remote Maven repository instead, configure the settings of the repository and execute:

```shell
mvn clean deploy
```

Refer to the [OSSRH Guide](http://central.sonatype.org/pages/ossrh-guide.html) for more information.

### Maven users

Add this dependency to your project's POM:

```xml

<dependency>
    <groupId>io.swagger</groupId>
    <artifactId>swagger-java-client</artifactId>
    <version>1.0.0</version>
    <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile "io.swagger:swagger-java-client:1.0.0"
```

### Others

At first generate the JAR by executing:

```shell
mvn clean package
```

Then manually install the following JARs:

* `target/swagger-java-client-1.0.0.jar`
* `target/lib/*.jar`

## Getting Started

Please follow the [installation](#installation) instruction and execute the following Java code:

```java
import com.adekia.exchange.amazonsp.client.ApiException;
import com.adekia.exchange.amazonsp.client.model.GetOrderAddressResponse;
import com.adekia.exchange.amazonsp.client.model.GetOrderBuyerInfoResponse;
import com.adekia.exchange.amazonsp.client.model.GetOrderItemsBuyerInfoResponse;
import com.adekia.exchange.amazonsp.client.model.GetOrderItemsResponse;
import com.adekia.exchange.amazonsp.client.model.GetOrderRegulatedInfoResponse;
import com.adekia.exchange.amazonsp.client.model.GetOrderResponse;
import com.adekia.exchange.amazonsp.client.model.GetOrdersResponse;
import com.adekia.exchange.amazonsp.client.model.UpdateVerificationStatusRequest;
import com.externalsystems.com.client.*;
import com.adekia.exchange.amazonsp.client.api.OrdersV0Api;

import java.util.*;

public class OrdersV0ApiExample
{

	public static void main(String[] args)
	{

		OrdersV0Api apiInstance = new OrdersV0Api();
		String orderId = "orderId_example"; // String | An Amazon-defined order identifier, in 3-7-7 format.
		try
		{
			GetOrderResponse result = apiInstance.getOrder(orderId);
			System.out.println(result);
		}
		catch (ApiException e)
		{
			System.err.println("Exception when calling OrdersV0Api#getOrder");
			e.printStackTrace();
		}
	}
}
import com.adekia.amazonsp.client.*;
		import com.adekia.amazonsp.client.auth.*;
		import com.adekia.amazonsp.client.model.*;
		import com.adekia.amazonsp.client.api.OrdersV0Api;

		import java.io.File;
		import java.util.*;

public class OrdersV0ApiExample
{

	public static void main(String[] args)
	{

		OrdersV0Api apiInstance = new OrdersV0Api();
		String orderId = "orderId_example"; // String | An orderId is an Amazon-defined order identifier, in 3-7-7 format.
		try
		{
			GetOrderAddressResponse result = apiInstance.getOrderAddress(orderId);
			System.out.println(result);
		}
		catch (ApiException e)
		{
			System.err.println("Exception when calling OrdersV0Api#getOrderAddress");
			e.printStackTrace();
		}
	}
}
import com.adekia.amazonsp.client.*;
		import com.adekia.amazonsp.client.auth.*;
		import com.adekia.amazonsp.client.model.*;
		import com.adekia.amazonsp.client.api.OrdersV0Api;

		import java.io.File;
		import java.util.*;

public class OrdersV0ApiExample
{

	public static void main(String[] args)
	{

		OrdersV0Api apiInstance = new OrdersV0Api();
		String orderId = "orderId_example"; // String | An orderId is an Amazon-defined order identifier, in 3-7-7 format.
		try
		{
			GetOrderBuyerInfoResponse result = apiInstance.getOrderBuyerInfo(orderId);
			System.out.println(result);
		}
		catch (ApiException e)
		{
			System.err.println("Exception when calling OrdersV0Api#getOrderBuyerInfo");
			e.printStackTrace();
		}
	}
}
import com.adekia.amazonsp.client.*;
		import com.adekia.amazonsp.client.auth.*;
		import com.adekia.amazonsp.client.model.*;
		import com.adekia.amazonsp.client.api.OrdersV0Api;

		import java.io.File;
		import java.util.*;

public class OrdersV0ApiExample
{

	public static void main(String[] args)
	{

		OrdersV0Api apiInstance = new OrdersV0Api();
		String orderId = "orderId_example"; // String | An Amazon-defined order identifier, in 3-7-7 format.
		String nextToken = "nextToken_example"; // String | A string token returned in the response of your previous request.
		try
		{
			GetOrderItemsResponse result = apiInstance.getOrderItems(orderId, nextToken);
			System.out.println(result);
		}
		catch (ApiException e)
		{
			System.err.println("Exception when calling OrdersV0Api#getOrderItems");
			e.printStackTrace();
		}
	}
}
import com.adekia.amazonsp.client.*;
		import com.adekia.amazonsp.client.auth.*;
		import com.adekia.amazonsp.client.model.*;
		import com.adekia.amazonsp.client.api.OrdersV0Api;

		import java.io.File;
		import java.util.*;

public class OrdersV0ApiExample
{

	public static void main(String[] args)
	{

		OrdersV0Api apiInstance = new OrdersV0Api();
		String orderId = "orderId_example"; // String | An Amazon-defined order identifier, in 3-7-7 format.
		String nextToken = "nextToken_example"; // String | A string token returned in the response of your previous request.
		try
		{
			GetOrderItemsBuyerInfoResponse result = apiInstance.getOrderItemsBuyerInfo(orderId, nextToken);
			System.out.println(result);
		}
		catch (ApiException e)
		{
			System.err.println("Exception when calling OrdersV0Api#getOrderItemsBuyerInfo");
			e.printStackTrace();
		}
	}
}
import com.adekia.amazonsp.client.*;
		import com.adekia.amazonsp.client.auth.*;
		import com.adekia.amazonsp.client.model.*;
		import com.adekia.amazonsp.client.api.OrdersV0Api;

		import java.io.File;
		import java.util.*;

public class OrdersV0ApiExample
{

	public static void main(String[] args)
	{

		OrdersV0Api apiInstance = new OrdersV0Api();
		String orderId = "orderId_example"; // String | An orderId is an Amazon-defined order identifier, in 3-7-7 format.
		try
		{
			GetOrderRegulatedInfoResponse result = apiInstance.getOrderRegulatedInfo(orderId);
			System.out.println(result);
		}
		catch (ApiException e)
		{
			System.err.println("Exception when calling OrdersV0Api#getOrderRegulatedInfo");
			e.printStackTrace();
		}
	}
}
import com.adekia.amazonsp.client.*;
		import com.adekia.amazonsp.client.auth.*;
		import com.adekia.amazonsp.client.model.*;
		import com.adekia.amazonsp.client.api.OrdersV0Api;

		import java.io.File;
		import java.util.*;

public class OrdersV0ApiExample
{

	public static void main(String[] args)
	{

		OrdersV0Api apiInstance = new OrdersV0Api();
		List<String> marketplaceIds = Arrays.asList("marketplaceIds_example"); // List<String> | A list of MarketplaceId values. Used to select orders that were placed in the specified marketplaces.  See the [Selling Partner API Developer Guide](doc:marketplace-ids) for a complete list of marketplaceId values.
		String createdAfter = "createdAfter_example"; // String | A date used for selecting orders created after (or at) a specified time. Only orders placed after the specified time are returned. Either the CreatedAfter parameter or the LastUpdatedAfter parameter is required. Both cannot be empty. The date must be in ISO 8601 format.
		String createdBefore = "createdBefore_example"; // String | A date used for selecting orders created before (or at) a specified time. Only orders placed before the specified time are returned. The date must be in ISO 8601 format.
		String lastUpdatedAfter = "lastUpdatedAfter_example"; // String | A date used for selecting orders that were last updated after (or at) a specified time. An update is defined as any change in order status, including the creation of a new order. Includes updates made by Amazon and by the seller. The date must be in ISO 8601 format.
		String lastUpdatedBefore = "lastUpdatedBefore_example"; // String | A date used for selecting orders that were last updated before (or at) a specified time. An update is defined as any change in order status, including the creation of a new order. Includes updates made by Amazon and by the seller. The date must be in ISO 8601 format.
		List<String> orderStatuses = Arrays.asList(
				"orderStatuses_example"); // List<String> | A list of OrderStatus values used to filter the results. Possible values: PendingAvailability (This status is available for pre-orders only. The order has been placed, payment has not been authorized, and the release date of the item is in the future.); Pending (The order has been placed but payment has not been authorized); Unshipped (Payment has been authorized and the order is ready for shipment, but no items in the order have been shipped); PartiallyShipped (One or more, but not all, items in the order have been shipped); Shipped (All items in the order have been shipped); InvoiceUnconfirmed (All items in the order have been shipped. The seller has not yet given confirmation to Amazon that the invoice has been shipped to the buyer.); Canceled (The order has been canceled); and Unfulfillable (The order cannot be fulfilled. This state applies only to Multi-Channel Fulfillment orders.).
		List<String> fulfillmentChannels = Arrays.asList("fulfillmentChannels_example"); // List<String> | A list that indicates how an order was fulfilled. Filters the results by fulfillment channel. Possible values: AFN (Fulfillment by Amazon); MFN (Fulfilled by the seller).
		List<String> paymentMethods = Arrays.asList("paymentMethods_example"); // List<String> | A list of payment method values. Used to select orders paid using the specified payment methods. Possible values: COD (Cash on delivery); CVS (Convenience store payment); Other (Any payment method other than COD or CVS).
		String buyerEmail = "buyerEmail_example"; // String | The email address of a buyer. Used to select orders that contain the specified email address.
		String sellerOrderId = "sellerOrderId_example"; // String | An order identifier that is specified by the seller. Used to select only the orders that match the order identifier. If SellerOrderId is specified, then FulfillmentChannels, OrderStatuses, PaymentMethod, LastUpdatedAfter, LastUpdatedBefore, and BuyerEmail cannot be specified.
		Integer maxResultsPerPage = 56; // Integer | A number that indicates the maximum number of orders that can be returned per page. Value must be 1 - 100. Default 100.
		List<String> easyShipShipmentStatuses = Arrays.asList(
				"easyShipShipmentStatuses_example"); // List<String> | A list of EasyShipShipmentStatus values. Used to select Easy Ship orders with statuses that match the specified  values. If EasyShipShipmentStatus is specified, only Amazon Easy Ship orders are returned.Possible values: PendingPickUp (Amazon has not yet picked up the package from the seller). LabelCanceled (The seller canceled the pickup). PickedUp (Amazon has picked up the package from the seller). AtOriginFC (The packaged is at the origin fulfillment center). AtDestinationFC (The package is at the destination fulfillment center). OutForDelivery (The package is out for delivery). Damaged (The package was damaged by the carrier). Delivered (The package has been delivered to the buyer). RejectedByBuyer (The package has been rejected by the buyer). Undeliverable (The package cannot be delivered). ReturnedToSeller (The package was not delivered to the buyer and was returned to the seller). ReturningToSeller (The package was not delivered to the buyer and is being returned to the seller).
		String nextToken = "nextToken_example"; // String | A string token returned in the response of your previous request.
		List<String> amazonOrderIds = Arrays.asList("amazonOrderIds_example"); // List<String> | A list of AmazonOrderId values. An AmazonOrderId is an Amazon-defined order identifier, in 3-7-7 format.
		String actualFulfillmentSupplySourceId = "actualFulfillmentSupplySourceId_example"; // String | Denotes the recommended sourceId where the order should be fulfilled from.
		Boolean isISPU = true; // Boolean | When true, this order is marked to be picked up from a store rather than delivered.
		String storeChainStoreId = "storeChainStoreId_example"; // String | The store chain store identifier. Linked to a specific store in a store chain.
		try
		{
			GetOrdersResponse result = apiInstance.getOrders(marketplaceIds, createdAfter, createdBefore, lastUpdatedAfter, lastUpdatedBefore, orderStatuses, fulfillmentChannels, paymentMethods, buyerEmail, sellerOrderId, maxResultsPerPage, easyShipShipmentStatuses, nextToken, amazonOrderIds, actualFulfillmentSupplySourceId, isISPU, storeChainStoreId);
			System.out.println(result);
		}
		catch (ApiException e)
		{
			System.err.println("Exception when calling OrdersV0Api#getOrders");
			e.printStackTrace();
		}
	}
}
import com.adekia.amazonsp.client.*;
		import com.adekia.amazonsp.client.auth.*;
		import com.adekia.amazonsp.client.model.*;
		import com.adekia.amazonsp.client.api.OrdersV0Api;

		import java.io.File;
		import java.util.*;

public class OrdersV0ApiExample
{

	public static void main(String[] args)
	{

		OrdersV0Api apiInstance = new OrdersV0Api();
		UpdateVerificationStatusRequest body = new UpdateVerificationStatusRequest(); // UpdateVerificationStatusRequest | Request to update the verification status of an order containing regulated products.
		String orderId = "orderId_example"; // String | An orderId is an Amazon-defined order identifier, in 3-7-7 format.
		try
		{
			apiInstance.updateVerificationStatus(body, orderId);
		}
		catch (ApiException e)
		{
			System.err.println("Exception when calling OrdersV0Api#updateVerificationStatus");
			e.printStackTrace();
		}
	}
}
```

## Documentation for API Endpoints

All URIs are relative to *https://sellingpartnerapi-na.amazon.com/*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*OrdersV0Api* | [**getOrder**](docs/OrdersV0Api.md#getOrder) | **GET** /orders/v0/orders/{orderId} |
*OrdersV0Api* | [**getOrderAddress**](docs/OrdersV0Api.md#getOrderAddress) | **
GET** /orders/v0/orders/{orderId}/address |
*OrdersV0Api* | [**getOrderBuyerInfo**](docs/OrdersV0Api.md#getOrderBuyerInfo) | **
GET** /orders/v0/orders/{orderId}/buyerInfo |
*OrdersV0Api* | [**getOrderItems**](docs/OrdersV0Api.md#getOrderItems) | **
GET** /orders/v0/orders/{orderId}/orderItems |
*OrdersV0Api* | [**getOrderItemsBuyerInfo**](docs/OrdersV0Api.md#getOrderItemsBuyerInfo) | **
GET** /orders/v0/orders/{orderId}/orderItems/buyerInfo |
*OrdersV0Api* | [**getOrderRegulatedInfo**](docs/OrdersV0Api.md#getOrderRegulatedInfo) | **
GET** /orders/v0/orders/{orderId}/regulatedInfo |
*OrdersV0Api* | [**getOrders**](docs/OrdersV0Api.md#getOrders) | **GET** /orders/v0/orders |
*OrdersV0Api* | [**updateVerificationStatus**](docs/OrdersV0Api.md#updateVerificationStatus) | **
PATCH** /orders/v0/orders/{orderId}/regulatedInfo |
*ShipmentApi* | [**updateShipmentStatus**](docs/ShipmentApi.md#updateShipmentStatus) | **
POST** /orders/v0/orders/{orderId}/shipment |

## Documentation for Models

- [Address](docs/Address.md)
- [AutomatedShippingSettings](docs/AutomatedShippingSettings.md)
- [BuyerCustomizedInfoDetail](docs/BuyerCustomizedInfoDetail.md)
- [BuyerInfo](docs/BuyerInfo.md)
- [BuyerRequestedCancel](docs/BuyerRequestedCancel.md)
- [BuyerTaxInfo](docs/BuyerTaxInfo.md)
- [BuyerTaxInformation](docs/BuyerTaxInformation.md)
- [Error](docs/Error.md)
- [ErrorList](docs/ErrorList.md)
- [FulfillmentInstruction](docs/FulfillmentInstruction.md)
- [GetOrderAddressResponse](docs/GetOrderAddressResponse.md)
- [GetOrderBuyerInfoResponse](docs/GetOrderBuyerInfoResponse.md)
- [GetOrderItemsBuyerInfoResponse](docs/GetOrderItemsBuyerInfoResponse.md)
- [GetOrderItemsResponse](docs/GetOrderItemsResponse.md)
- [GetOrderRegulatedInfoResponse](docs/GetOrderRegulatedInfoResponse.md)
- [GetOrderResponse](docs/GetOrderResponse.md)
- [GetOrdersResponse](docs/GetOrdersResponse.md)
- [ItemBuyerInfo](docs/ItemBuyerInfo.md)
- [MarketplaceTaxInfo](docs/MarketplaceTaxInfo.md)
- [Money](docs/Money.md)
- [Order](docs/Order.md)
- [OrderAddress](docs/OrderAddress.md)
- [OrderBuyerInfo](docs/OrderBuyerInfo.md)
- [OrderItem](docs/OrderItem.md)
- [OrderItemBuyerInfo](docs/OrderItemBuyerInfo.md)
- [OrderItemBuyerInfoList](docs/OrderItemBuyerInfoList.md)
- [OrderItemList](docs/OrderItemList.md)
- [OrderItems](docs/OrderItems.md)
- [OrderItemsBuyerInfoList](docs/OrderItemsBuyerInfoList.md)
- [OrderItemsInner](docs/OrderItemsInner.md)
- [OrderItemsList](docs/OrderItemsList.md)
- [OrderList](docs/OrderList.md)
- [OrderRegulatedInfo](docs/OrderRegulatedInfo.md)
- [OrdersList](docs/OrdersList.md)
- [PaymentExecutionDetailItem](docs/PaymentExecutionDetailItem.md)
- [PaymentExecutionDetailItemList](docs/PaymentExecutionDetailItemList.md)
- [PaymentMethodDetailItemList](docs/PaymentMethodDetailItemList.md)
- [PointsGrantedDetail](docs/PointsGrantedDetail.md)
- [ProductInfoDetail](docs/ProductInfoDetail.md)
- [PromotionIdList](docs/PromotionIdList.md)
- [RegulatedInformation](docs/RegulatedInformation.md)
- [RegulatedInformationField](docs/RegulatedInformationField.md)
- [RegulatedOrderVerificationStatus](docs/RegulatedOrderVerificationStatus.md)
- [RejectionReason](docs/RejectionReason.md)
- [ShipmentStatus](docs/ShipmentStatus.md)
- [TaxClassification](docs/TaxClassification.md)
- [TaxCollection](docs/TaxCollection.md)
- [UpdateShipmentStatusErrorResponse](docs/UpdateShipmentStatusErrorResponse.md)
- [UpdateShipmentStatusRequest](docs/UpdateShipmentStatusRequest.md)
- [UpdateVerificationStatusErrorResponse](docs/UpdateVerificationStatusErrorResponse.md)
- [UpdateVerificationStatusRequest](docs/UpdateVerificationStatusRequest.md)
- [UpdateVerificationStatusRequestBody](docs/UpdateVerificationStatusRequestBody.md)

## Documentation for Authorization

All endpoints do not require authorization.
Authentication schemes defined for the API:

## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential
issues.

## Author

