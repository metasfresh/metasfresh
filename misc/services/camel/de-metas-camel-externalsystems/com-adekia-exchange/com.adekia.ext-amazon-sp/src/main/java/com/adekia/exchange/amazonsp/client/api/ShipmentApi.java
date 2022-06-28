package com.adekia.exchange.amazonsp.client.api;

import com.adekia.exchange.amazonsp.client.ApiException;
import com.adekia.exchange.amazonsp.client.ApiClient;
import com.adekia.exchange.amazonsp.client.Configuration;
import com.adekia.exchange.amazonsp.client.Pair;

import javax.ws.rs.core.GenericType;

import com.adekia.exchange.amazonsp.client.model.UpdateShipmentStatusErrorResponse;
import com.adekia.exchange.amazonsp.client.model.UpdateShipmentStatusRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2022-06-28T13:53:07.940430682+02:00[Europe/Paris]")public class ShipmentApi {
  private ApiClient apiClient;

  public ShipmentApi() {
    this(Configuration.getDefaultApiClient());
  }

  public ShipmentApi(ApiClient apiClient) {
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
   * Update the shipment status.
   * @param body Request to update the shipment status. (required)
   * @param orderId An Amazon-defined order identifier, in 3-7-7 format. (required)
   * @throws ApiException if fails to make API call
   */
  public void updateShipmentStatus(UpdateShipmentStatusRequest body, String orderId) throws ApiException {
    Object localVarPostBody = body;
    // verify the required parameter 'body' is set
    if (body == null) {
      throw new ApiException(400, "Missing the required parameter 'body' when calling updateShipmentStatus");
    }
    // verify the required parameter 'orderId' is set
    if (orderId == null) {
      throw new ApiException(400, "Missing the required parameter 'orderId' when calling updateShipmentStatus");
    }
    // create path and map variables
    String localVarPath = "/orders/v0/orders/{orderId}/shipment"
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

    apiClient.invokeAPI(localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, null);
  }
}
