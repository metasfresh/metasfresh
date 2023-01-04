/*
 * #%L
 * ext-amazon-sp
 * %%
 * Copyright (C) 2022 Adekia
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

package com.adekia.exchange.amazonsp.provider;

import com.adekia.exchange.amazonsp.client.orders.ApiClient;
import com.adekia.exchange.amazonsp.client.orders.ApiException;
import com.adekia.exchange.amazonsp.client.orders.api.OrdersV0Api;
import com.adekia.exchange.amazonsp.client.orders.model.GetOrderItemsResponse;
import com.adekia.exchange.amazonsp.client.orders.model.GetOrdersResponse;
import com.adekia.exchange.amazonsp.client.orders.model.OrderItemList;
import com.adekia.exchange.amazonsp.util.AmazonOrder;
import com.adekia.exchange.amazonsp.util.AmazonOrderApiHelper;
import com.adekia.exchange.context.Ctx;
import oasis.names.specification.ubl.schema.xsd.order_23.OrderType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@ConditionalOnProperty(prefix = "order", name = "provider", havingValue = "amazon", matchIfMissing = true)
public class AmazonGetOrdersProviderImpl implements AmazonGetOrdersProvider {

    @Override
    public List<OrderType> getOrders(Ctx ctx) throws Exception
    {
        OrdersV0Api ordersApi = AmazonOrderApiHelper.getOrdersAPI(ctx);
        ApiClient apiClient = ordersApi.getApiClient();
        ;
        /* Parameters used for selection */
        List<String> marketplaceIds = Arrays.asList("A1PA6795UKMFR9");//Arrays.asList("marketplaceIds_example"); // List<String> | A list of MarketplaceId values. Used to select orders that were placed in the specified marketplaces.  See the [Selling Partner API Developer Guide](doc:marketplace-ids) for a complete list of marketplaceId values.
        String createdAfter = "2018-08-23T08:22:30.737Z"; // String | A date used for selecting orders created after (or at) a specified time. Only orders placed after the specified time are returned. Either the CreatedAfter parameter or the LastUpdatedAfter parameter is required. Both cannot be empty. The date must be in ISO 8601 format.
        // Technical one
        Integer maxResultsPerPage = 100; // Integer | A number that indicates the maximum number of orders that can be returned per page. Value must be 1 - 100. Default 100.
        // todo : take this one from parameter of the route ? For the moment we consider only first 100 Orders in time intervalle
        String nextToken = null; // String | A string token returned in the response of your previous request.

        /* Have a look to this ? Do we consider all Amazon statuses ?*/
        List<String> orderStatuses = null; // List<String> | A list of OrderStatus values used to filter the results. Possible values: PendingAvailability (This status is available for pre-orders only. The order has been placed, payment has not been authorized, and the release date of the item is in the future.); Pending (The order has been placed but payment has not been authorized); Unshipped (Payment has been authorized and the order is ready for shipment, but no items in the order have been shipped); PartiallyShipped (One or more, but not all, items in the order have been shipped); Shipped (All items in the order have been shipped); InvoiceUnconfirmed (All items in the order have been shipped. The seller has not yet given confirmation to Amazon that the invoice has been shipped to the buyer.); Canceled (The order has been canceled); and Unfulfillable (The order cannot be fulfilled. This state applies only to Multi-Channel Fulfillment orders.).

        /* Seems i will not use this */
        String lastUpdatedAfter = null; // String | A date used for selecting orders that were last updated after (or at) a specified time. An update is defined as any change in order status, including the creation of a new order. Includes updates made by Amazon and by the seller. The date must be in ISO 8601 format.
        String createdBefore = null; // String | A date used for selecting orders created before (or at) a specified time. Only orders placed before the specified time are returned. The date must be in ISO 8601 format.
        String lastUpdatedBefore = null; // String | A date used for selecting orders that were last updated before (or at) a specified time. An update is defined as any change in order status, including the creation of a new order. Includes updates made by Amazon and by the seller. The date must be in ISO 8601 format.
        List<String> fulfillmentChannels = null; // List<String> | A list that indicates how an order was fulfilled. Filters the results by fulfillment channel. Possible values: AFN (Fulfillment by Amazon); MFN (Fulfilled by the seller).
        List<String> paymentMethods = null; // List<String> | A list of payment method values. Used to select orders paid using the specified payment methods. Possible values: COD (Cash on delivery); CVS (Convenience store payment); Other (Any payment method other than COD or CVS).
        String buyerEmail = null; // String | The email address of a buyer. Used to select orders that contain the specified email address.
        String sellerOrderId = null; // String | An order identifier that is specified by the seller. Used to select only the orders that match the order identifier. If SellerOrderId is specified, then FulfillmentChannels, OrderStatuses, PaymentMethod, LastUpdatedAfter, LastUpdatedBefore, and BuyerEmail cannot be specified.
        List<String> easyShipShipmentStatuses = null; // List<String> | A list of EasyShipShipmentStatus values. Used to select Easy Ship orders with statuses that match the specified  values. If EasyShipShipmentStatus is specified, only Amazon Easy Ship orders are returned.Possible values: PendingPickUp (Amazon has not yet picked up the package from the seller). LabelCanceled (The seller canceled the pickup). PickedUp (Amazon has picked up the package from the seller). AtOriginFC (The packaged is at the origin fulfillment center). AtDestinationFC (The package is at the destination fulfillment center). OutForDelivery (The package is out for delivery). Damaged (The package was damaged by the carrier). Delivered (The package has been delivered to the buyer). RejectedByBuyer (The package has been rejected by the buyer). Undeliverable (The package cannot be delivered). ReturnedToSeller (The package was not delivered to the buyer and was returned to the seller). ReturningToSeller (The package was not delivered to the buyer and is being returned to the seller).
        List<String> amazonOrderIds = null; // List<String> | A list of AmazonOrderId values. An AmazonOrderId is an Amazon-defined order identifier, in 3-7-7 format.
        String actualFulfillmentSupplySourceId = null; // String | Denotes the recommended sourceId where the order should be fulfilled from.
        Boolean isISPU = null; // Boolean | When true, this order is marked to be picked up from a store rather than delivered.
        String storeChainStoreId = null; // String | The store chain store identifier. Linked to a specific store in a store chain.

        try
        {
            GetOrdersResponse getOrdersResponse = ordersApi.getOrders(marketplaceIds, createdAfter, createdBefore, lastUpdatedAfter, lastUpdatedBefore, orderStatuses, fulfillmentChannels, paymentMethods, buyerEmail, sellerOrderId, maxResultsPerPage, easyShipShipmentStatuses, nextToken, amazonOrderIds, actualFulfillmentSupplySourceId, isISPU, storeChainStoreId);
            if (getOrdersResponse != null && getOrdersResponse.getPayload() != null && getOrdersResponse.getPayload().getOrders() != null && getOrdersResponse.getPayload().getOrders().size() > 0)
            {
                List<OrderType> retValue = getOrdersResponse.getPayload().getOrders().stream()
                        .map(order -> AmazonOrder.toOrderType(order, getItems(ordersApi, order.getAmazonOrderId(), null)))
                        .collect(Collectors.toList());
                return retValue;
            }
        }
        catch (final ApiException apie)
        {
            throw new RuntimeException(apie);
        }
        return new ArrayList<OrderType>();
    }

    private OrderItemList getItems(OrdersV0Api ordersApi, String amazonOrderId, String nextToken)
    {
        OrderItemList retValue = null;
        try {
            GetOrderItemsResponse response = ordersApi.getOrderItems(amazonOrderId, nextToken);
            if (response != null && response.getPayload() != null)
                retValue = response.getPayload().getOrderItems();
        }
        catch (final ApiException apie)
        {
            throw new RuntimeException(apie);
        }
        return retValue;
    }

}
