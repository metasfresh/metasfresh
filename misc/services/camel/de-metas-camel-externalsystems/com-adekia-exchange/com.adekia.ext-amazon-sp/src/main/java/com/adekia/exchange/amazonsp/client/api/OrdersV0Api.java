package com.adekia.exchange.amazonsp.client.api;

import com.adekia.exchange.amazonsp.client.ApiException;
import com.adekia.exchange.amazonsp.client.ApiClient;
import com.adekia.exchange.amazonsp.client.Configuration;
import com.adekia.exchange.amazonsp.client.Pair;

import javax.ws.rs.core.GenericType;

import com.adekia.exchange.amazonsp.client.model.GetOrderAddressResponse;
import com.adekia.exchange.amazonsp.client.model.GetOrderBuyerInfoResponse;
import com.adekia.exchange.amazonsp.client.model.GetOrderItemsBuyerInfoResponse;
import com.adekia.exchange.amazonsp.client.model.GetOrderItemsResponse;
import com.adekia.exchange.amazonsp.client.model.GetOrderRegulatedInfoResponse;
import com.adekia.exchange.amazonsp.client.model.GetOrderResponse;
import com.adekia.exchange.amazonsp.client.model.GetOrdersResponse;
import com.adekia.exchange.amazonsp.client.model.UpdateVerificationStatusErrorResponse;
import com.adekia.exchange.amazonsp.client.model.UpdateVerificationStatusRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2022-06-28T13:53:07.940430682+02:00[Europe/Paris]")public class OrdersV0Api {
  private ApiClient apiClient;

  public OrdersV0Api() {
    this(Configuration.getDefaultApiClient());
  }

  public OrdersV0Api(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * 
   * Returns the order indicated by the specified order ID.  **Usage Plans:**  | Plan type | Rate (requests per second) | Burst | | ---- | ---- | ---- | |Default| 0.0055 | 20 | |Selling partner specific| Variable | Variable |  The x-amzn-RateLimit-Limit response header returns the usage plan rate limits that were applied to the requested operation. Rate limits for some selling partners will vary from the default rate and burst shown in the table above. For more information, see \&quot;Usage Plans and Rate Limits\&quot; in the Selling Partner API documentation.
   * @param orderId An Amazon-defined order identifier, in 3-7-7 format. (required)
   * @return GetOrderResponse
   * @throws ApiException if fails to make API call
   */
  public GetOrderResponse getOrder(String orderId) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'orderId' is set
    if (orderId == null) {
      throw new ApiException(400, "Missing the required parameter 'orderId' when calling getOrder");
    }
    // create path and map variables
    String localVarPath = "/orders/v0/orders/{orderId}"
      .replaceAll("\\{" + "orderId" + "\\}", apiClient.escapeString(orderId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<GetOrderResponse> localVarReturnType = new GenericType<GetOrderResponse>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * 
   * Returns the shipping address for the specified order.  **Usage Plans:**  | Plan type | Rate (requests per second) | Burst | | ---- | ---- | ---- | |Default| 0.0055 | 20 | |Selling partner specific| Variable | Variable |  The x-amzn-RateLimit-Limit response header returns the usage plan rate limits that were applied to the requested operation. Rate limits for some selling partners will vary from the default rate and burst shown in the table above. For more information, see \&quot;Usage Plans and Rate Limits\&quot; in the Selling Partner API documentation.
   * @param orderId An orderId is an Amazon-defined order identifier, in 3-7-7 format. (required)
   * @return GetOrderAddressResponse
   * @throws ApiException if fails to make API call
   */
  public GetOrderAddressResponse getOrderAddress(String orderId) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'orderId' is set
    if (orderId == null) {
      throw new ApiException(400, "Missing the required parameter 'orderId' when calling getOrderAddress");
    }
    // create path and map variables
    String localVarPath = "/orders/v0/orders/{orderId}/address"
      .replaceAll("\\{" + "orderId" + "\\}", apiClient.escapeString(orderId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<GetOrderAddressResponse> localVarReturnType = new GenericType<GetOrderAddressResponse>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * 
   * Returns buyer information for the specified order.  **Usage Plans:**  | Plan type | Rate (requests per second) | Burst | | ---- | ---- | ---- | |Default| 0.0055 | 20 | |Selling partner specific| Variable | Variable |  The x-amzn-RateLimit-Limit response header returns the usage plan rate limits that were applied to the requested operation. Rate limits for some selling partners will vary from the default rate and burst shown in the table above. For more information, see \&quot;Usage Plans and Rate Limits\&quot; in the Selling Partner API documentation.
   * @param orderId An orderId is an Amazon-defined order identifier, in 3-7-7 format. (required)
   * @return GetOrderBuyerInfoResponse
   * @throws ApiException if fails to make API call
   */
  public GetOrderBuyerInfoResponse getOrderBuyerInfo(String orderId) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'orderId' is set
    if (orderId == null) {
      throw new ApiException(400, "Missing the required parameter 'orderId' when calling getOrderBuyerInfo");
    }
    // create path and map variables
    String localVarPath = "/orders/v0/orders/{orderId}/buyerInfo"
      .replaceAll("\\{" + "orderId" + "\\}", apiClient.escapeString(orderId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<GetOrderBuyerInfoResponse> localVarReturnType = new GenericType<GetOrderBuyerInfoResponse>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * 
   * Returns detailed order item information for the order indicated by the specified order ID. If NextToken is provided, it&#x27;s used to retrieve the next page of order items.  Note: When an order is in the Pending state (the order has been placed but payment has not been authorized), the getOrderItems operation does not return information about pricing, taxes, shipping charges, gift status or promotions for the order items in the order. After an order leaves the Pending state (this occurs when payment has been authorized) and enters the Unshipped, Partially Shipped, or Shipped state, the getOrderItems operation returns information about pricing, taxes, shipping charges, gift status and promotions for the order items in the order.  **Usage Plans:**  | Plan type | Rate (requests per second) | Burst | | ---- | ---- | ---- | |Default| 0.0055 | 20 | |Selling partner specific| Variable | Variable |  The x-amzn-RateLimit-Limit response header returns the usage plan rate limits that were applied to the requested operation. Rate limits for some selling partners will vary from the default rate and burst shown in the table above. For more information, see \&quot;Usage Plans and Rate Limits\&quot; in the Selling Partner API documentation.
   * @param orderId An Amazon-defined order identifier, in 3-7-7 format. (required)
   * @param nextToken A string token returned in the response of your previous request. (optional)
   * @return GetOrderItemsResponse
   * @throws ApiException if fails to make API call
   */
  public GetOrderItemsResponse getOrderItems(String orderId, String nextToken) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'orderId' is set
    if (orderId == null) {
      throw new ApiException(400, "Missing the required parameter 'orderId' when calling getOrderItems");
    }
    // create path and map variables
    String localVarPath = "/orders/v0/orders/{orderId}/orderItems"
      .replaceAll("\\{" + "orderId" + "\\}", apiClient.escapeString(orderId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "NextToken", nextToken));


    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<GetOrderItemsResponse> localVarReturnType = new GenericType<GetOrderItemsResponse>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * 
   * Returns buyer information for the order items in the specified order.  **Usage Plans:**  | Plan type | Rate (requests per second) | Burst | | ---- | ---- | ---- | |Default| 0.0055 | 20 | |Selling partner specific| Variable | Variable |  The x-amzn-RateLimit-Limit response header returns the usage plan rate limits that were applied to the requested operation. Rate limits for some selling partners will vary from the default rate and burst shown in the table above. For more information, see \&quot;Usage Plans and Rate Limits\&quot; in the Selling Partner API documentation.
   * @param orderId An Amazon-defined order identifier, in 3-7-7 format. (required)
   * @param nextToken A string token returned in the response of your previous request. (optional)
   * @return GetOrderItemsBuyerInfoResponse
   * @throws ApiException if fails to make API call
   */
  public GetOrderItemsBuyerInfoResponse getOrderItemsBuyerInfo(String orderId, String nextToken) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'orderId' is set
    if (orderId == null) {
      throw new ApiException(400, "Missing the required parameter 'orderId' when calling getOrderItemsBuyerInfo");
    }
    // create path and map variables
    String localVarPath = "/orders/v0/orders/{orderId}/orderItems/buyerInfo"
      .replaceAll("\\{" + "orderId" + "\\}", apiClient.escapeString(orderId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "NextToken", nextToken));


    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<GetOrderItemsBuyerInfoResponse> localVarReturnType = new GenericType<GetOrderItemsBuyerInfoResponse>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * 
   * Returns regulated information for the order indicated by the specified order ID.  **Usage Plans:**  | Plan type | Rate (requests per second) | Burst | | ---- | ---- | ---- | |Default| 0.0055 | 20 | |Selling partner specific| Variable | Variable |  The x-amzn-RateLimit-Limit response header returns the usage plan rate limits that were applied to the requested operation. Rate limits for some selling partners will vary from the default rate and burst shown in the table above. For more information, see \&quot;Usage Plans and Rate Limits\&quot; in the Selling Partner API documentation.
   * @param orderId An orderId is an Amazon-defined order identifier, in 3-7-7 format. (required)
   * @return GetOrderRegulatedInfoResponse
   * @throws ApiException if fails to make API call
   */
  public GetOrderRegulatedInfoResponse getOrderRegulatedInfo(String orderId) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'orderId' is set
    if (orderId == null) {
      throw new ApiException(400, "Missing the required parameter 'orderId' when calling getOrderRegulatedInfo");
    }
    // create path and map variables
    String localVarPath = "/orders/v0/orders/{orderId}/regulatedInfo"
      .replaceAll("\\{" + "orderId" + "\\}", apiClient.escapeString(orderId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();



    final String[] localVarAccepts = {
      "application/json", "PendingOrder", "ApprovedOrder", "RejectedOrder"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<GetOrderRegulatedInfoResponse> localVarReturnType = new GenericType<GetOrderRegulatedInfoResponse>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * 
   * Returns orders created or updated during the time frame indicated by the specified parameters. You can also apply a range of filtering criteria to narrow the list of orders returned. If NextToken is present, that will be used to retrieve the orders instead of other criteria.  **Usage Plans:**  | Plan type | Rate (requests per second) | Burst | | ---- | ---- | ---- | |Default| 0.0055 | 20 | |Selling partner specific| Variable | Variable |  The x-amzn-RateLimit-Limit response header returns the usage plan rate limits that were applied to the requested operation. Rate limits for some selling partners will vary from the default rate and burst shown in the table above. For more information, see \&quot;Usage Plans and Rate Limits\&quot; in the Selling Partner API documentation.
   * @param marketplaceIds A list of MarketplaceId values. Used to select orders that were placed in the specified marketplaces.  See the [Selling Partner API Developer Guide](doc:marketplace-ids) for a complete list of marketplaceId values. (required)
   * @param createdAfter A date used for selecting orders created after (or at) a specified time. Only orders placed after the specified time are returned. Either the CreatedAfter parameter or the LastUpdatedAfter parameter is required. Both cannot be empty. The date must be in ISO 8601 format. (optional)
   * @param createdBefore A date used for selecting orders created before (or at) a specified time. Only orders placed before the specified time are returned. The date must be in ISO 8601 format. (optional)
   * @param lastUpdatedAfter A date used for selecting orders that were last updated after (or at) a specified time. An update is defined as any change in order status, including the creation of a new order. Includes updates made by Amazon and by the seller. The date must be in ISO 8601 format. (optional)
   * @param lastUpdatedBefore A date used for selecting orders that were last updated before (or at) a specified time. An update is defined as any change in order status, including the creation of a new order. Includes updates made by Amazon and by the seller. The date must be in ISO 8601 format. (optional)
   * @param orderStatuses A list of OrderStatus values used to filter the results. Possible values: PendingAvailability (This status is available for pre-orders only. The order has been placed, payment has not been authorized, and the release date of the item is in the future.); Pending (The order has been placed but payment has not been authorized); Unshipped (Payment has been authorized and the order is ready for shipment, but no items in the order have been shipped); PartiallyShipped (One or more, but not all, items in the order have been shipped); Shipped (All items in the order have been shipped); InvoiceUnconfirmed (All items in the order have been shipped. The seller has not yet given confirmation to Amazon that the invoice has been shipped to the buyer.); Canceled (The order has been canceled); and Unfulfillable (The order cannot be fulfilled. This state applies only to Multi-Channel Fulfillment orders.). (optional)
   * @param fulfillmentChannels A list that indicates how an order was fulfilled. Filters the results by fulfillment channel. Possible values: AFN (Fulfillment by Amazon); MFN (Fulfilled by the seller). (optional)
   * @param paymentMethods A list of payment method values. Used to select orders paid using the specified payment methods. Possible values: COD (Cash on delivery); CVS (Convenience store payment); Other (Any payment method other than COD or CVS). (optional)
   * @param buyerEmail The email address of a buyer. Used to select orders that contain the specified email address. (optional)
   * @param sellerOrderId An order identifier that is specified by the seller. Used to select only the orders that match the order identifier. If SellerOrderId is specified, then FulfillmentChannels, OrderStatuses, PaymentMethod, LastUpdatedAfter, LastUpdatedBefore, and BuyerEmail cannot be specified. (optional)
   * @param maxResultsPerPage A number that indicates the maximum number of orders that can be returned per page. Value must be 1 - 100. Default 100. (optional)
   * @param easyShipShipmentStatuses A list of EasyShipShipmentStatus values. Used to select Easy Ship orders with statuses that match the specified  values. If EasyShipShipmentStatus is specified, only Amazon Easy Ship orders are returned.Possible values: PendingPickUp (Amazon has not yet picked up the package from the seller). LabelCanceled (The seller canceled the pickup). PickedUp (Amazon has picked up the package from the seller). AtOriginFC (The packaged is at the origin fulfillment center). AtDestinationFC (The package is at the destination fulfillment center). OutForDelivery (The package is out for delivery). Damaged (The package was damaged by the carrier). Delivered (The package has been delivered to the buyer). RejectedByBuyer (The package has been rejected by the buyer). Undeliverable (The package cannot be delivered). ReturnedToSeller (The package was not delivered to the buyer and was returned to the seller). ReturningToSeller (The package was not delivered to the buyer and is being returned to the seller). (optional)
   * @param nextToken A string token returned in the response of your previous request. (optional)
   * @param amazonOrderIds A list of AmazonOrderId values. An AmazonOrderId is an Amazon-defined order identifier, in 3-7-7 format. (optional)
   * @param actualFulfillmentSupplySourceId Denotes the recommended sourceId where the order should be fulfilled from. (optional)
   * @param isISPU When true, this order is marked to be picked up from a store rather than delivered. (optional)
   * @param storeChainStoreId The store chain store identifier. Linked to a specific store in a store chain. (optional)
   * @return GetOrdersResponse
   * @throws ApiException if fails to make API call
   */
  public GetOrdersResponse getOrders(List<String> marketplaceIds, String createdAfter, String createdBefore, String lastUpdatedAfter, String lastUpdatedBefore, List<String> orderStatuses, List<String> fulfillmentChannels, List<String> paymentMethods, String buyerEmail, String sellerOrderId, Integer maxResultsPerPage, List<String> easyShipShipmentStatuses, String nextToken, List<String> amazonOrderIds, String actualFulfillmentSupplySourceId, Boolean isISPU, String storeChainStoreId) throws ApiException {
    Object localVarPostBody = null;
    // verify the required parameter 'marketplaceIds' is set
    if (marketplaceIds == null) {
      throw new ApiException(400, "Missing the required parameter 'marketplaceIds' when calling getOrders");
    }
    // create path and map variables
    String localVarPath = "/orders/v0/orders";

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "CreatedAfter", createdAfter));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "CreatedBefore", createdBefore));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "LastUpdatedAfter", lastUpdatedAfter));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "LastUpdatedBefore", lastUpdatedBefore));
    localVarQueryParams.addAll(apiClient.parameterToPairs("csv", "OrderStatuses", orderStatuses));
    localVarQueryParams.addAll(apiClient.parameterToPairs("csv", "MarketplaceIds", marketplaceIds));
    localVarQueryParams.addAll(apiClient.parameterToPairs("csv", "FulfillmentChannels", fulfillmentChannels));
    localVarQueryParams.addAll(apiClient.parameterToPairs("csv", "PaymentMethods", paymentMethods));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "BuyerEmail", buyerEmail));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "SellerOrderId", sellerOrderId));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "MaxResultsPerPage", maxResultsPerPage));
    localVarQueryParams.addAll(apiClient.parameterToPairs("csv", "EasyShipShipmentStatuses", easyShipShipmentStatuses));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "NextToken", nextToken));
    localVarQueryParams.addAll(apiClient.parameterToPairs("csv", "AmazonOrderIds", amazonOrderIds));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "ActualFulfillmentSupplySourceId", actualFulfillmentSupplySourceId));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "IsISPU", isISPU));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "StoreChainStoreId", storeChainStoreId));


    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<GetOrdersResponse> localVarReturnType = new GenericType<GetOrdersResponse>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
  }
  /**
   * 
   * Updates (approves or rejects) the verification status of an order containing regulated products.  **Usage Plans:**  | Plan type | Rate (requests per second) | Burst | | ---- | ---- | ---- | |Default| 0.0055 | 20 | |Selling partner specific| Variable | Variable |  The x-amzn-RateLimit-Limit response header returns the usage plan rate limits that were applied to the requested operation. Rate limits for some selling partners will vary from the default rate and burst shown in the table above. For more information, see \&quot;Usage Plans and Rate Limits\&quot; in the Selling Partner API documentation.
   * @param body Request to update the verification status of an order containing regulated products. (required)
   * @param orderId An orderId is an Amazon-defined order identifier, in 3-7-7 format. (required)
   * @throws ApiException if fails to make API call
   */
  public void updateVerificationStatus(UpdateVerificationStatusRequest body, String orderId) throws ApiException {
    Object localVarPostBody = body;
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateVerificationStatus");
    }
    // verify the required parameter 'orderId' is set
    if (orderId == null) {
      throw new ApiException(400, "Missing the required parameter 'orderId' when calling updateVerificationStatus");
    }
    // create path and map variables
    String localVarPath = "/orders/v0/orders/{orderId}/regulatedInfo"
      .replaceAll("\\{" + "orderId" + "\\}", apiClient.escapeString(orderId.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();



    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    apiClient.invokeAPI(localVarPath, "PATCH", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, null);
  }
}
