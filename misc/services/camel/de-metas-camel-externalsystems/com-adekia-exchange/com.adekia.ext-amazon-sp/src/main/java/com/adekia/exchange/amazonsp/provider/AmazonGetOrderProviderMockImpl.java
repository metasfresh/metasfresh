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

import com.adekia.exchange.amazonsp.client.orders.ApiException;
import com.adekia.exchange.amazonsp.client.orders.api.OrdersV0Api;
import com.adekia.exchange.amazonsp.client.orders.model.GetOrderResponse;
import com.adekia.exchange.amazonsp.util.AmazonOrder;
import com.adekia.exchange.amazonsp.util.AmazonOrderApiHelper;
import com.adekia.exchange.context.Ctx;
import oasis.names.specification.ubl.schema.xsd.order_23.OrderType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(prefix = "order", name = "provider", havingValue = "amazon-mock")
public class AmazonGetOrderProviderMockImpl implements AmazonGetOrderProvider {
    @Override
    public OrderType getOrder(Ctx ctx) throws Exception {
        OrdersV0Api ordersApi = AmazonOrderApiHelper.getOrdersAPI(ctx);
        //ApiClient apiClient = ordersApi.getApiClient().setBasePath("http://localhost:3101/sp-api");

        try {
            // /orders/v0/orders/{orderId}
            GetOrderResponse result = ordersApi.getOrder("1");

            if (result != null && result.getPayload() != null)
                return AmazonOrder.toOrderType(result.getPayload(), null);
        } catch (ApiException apie) {
            throw new RuntimeException(apie);
        }
        return null;

    }
}
