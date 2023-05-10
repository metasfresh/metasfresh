/*
 * #%L
 * ext-amazon-sp
 * %%
 * Copyright (C) 2022 metas GmbH
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

package com.adekia.exchange.amazonsp.sender;

import com.adekia.exchange.amazonsp.client.shipments.ApiClient;
import com.adekia.exchange.amazonsp.client.shipments.api.ShippingApi;
import com.adekia.exchange.amazonsp.client.shipments.model.CreateShipmentRequest;
import com.adekia.exchange.amazonsp.client.shipments.model.CreateShipmentResponse;
import com.adekia.exchange.context.Ctx;
import com.adekia.exchange.sender.ShipmentSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(prefix = "shipment", name = "sender", havingValue = "amazon-mock")
public class AmazonShipmentSenderMockImpl implements ShipmentSender
{
	@Override
	public String send(Ctx ctx, final Object shipment) throws Exception
	{
		ShippingApi shippingApi = new ShippingApi();
		ApiClient apiClient = shippingApi.getApiClient().setBasePath("http://localhost:3101/sp-api");
		//apiClient.apiType="mock"; todo

		CreateShipmentResponse response = shippingApi.createShipment((CreateShipmentRequest)shipment);

		return "  --> sent to Amazon (Mock) : "+shippingApi.getApiClient().getBasePath() + "/shipping/v1/shipments [" + response.getPayload().getShipmentId() + "] "
				+ ((response.getErrors() != null) ? response.getErrors().toString() : "");

	}
}
