package de.metas.vertical.pharma.vendor.gateway.msv3;

import org.springframework.stereotype.Service;

import de.metas.vertical.pharma.msv3.protocol.order.v1.OrderJAXBConvertersV1;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.v1.StockAvailabilityJAXBConvertersV1;
import de.metas.vertical.pharma.vendor.gateway.msv3.availability.MSV3AvailiabilityClient;
import de.metas.vertical.pharma.vendor.gateway.msv3.availability.MSV3AvailiabilityClientImpl;
import de.metas.vertical.pharma.vendor.gateway.msv3.config.MSV3ClientConfig;
import de.metas.vertical.pharma.vendor.gateway.msv3.purchaseOrder.MSV3PurchaseOrderClient;
import de.metas.vertical.pharma.vendor.gateway.msv3.purchaseOrder.MSV3PurchaseOrderClientImpl;
import de.metas.vertical.pharma.vendor.gateway.msv3.purchaseOrder.SupportIdProvider;
import de.metas.vertical.pharma.vendor.gateway.msv3.testconnection.MSV3TestConnectionClient;
import de.metas.vertical.pharma.vendor.gateway.msv3.testconnection.MSV3TestConnectionClientImpl;
import de.metas.vertical.pharma.vendor.gateway.msv3.testconnection.v1.TestConnectionJAXBConvertersV1;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.msv3
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Service
public class MSV3ClientFactoryV1 implements MSV3ClientFactory
{
	private final MSV3ConnectionFactory connectionFactory;
	private final SupportIdProvider supportIdProvider;

	public MSV3ClientFactoryV1(
			@NonNull final MSV3ConnectionFactory connectionFactory,
			@NonNull final SupportIdProvider supportIdProvider)
	{
		this.connectionFactory = connectionFactory;
		this.supportIdProvider = supportIdProvider;
	}

	@Override
	public String getVersionId()
	{
		return MSV3ClientConfig.VERSION_1.getId();
	}

	@Override
	public MSV3TestConnectionClient newTestConnectionClient(@NonNull final MSV3ClientConfig config)
	{
		config.assertVersion(getVersionId());

		return MSV3TestConnectionClientImpl.builder()
				.connectionFactory(connectionFactory)
				.config(config)
				.jaxbConverters(TestConnectionJAXBConvertersV1.instance)
				.build();
	}

	@Override
	public MSV3AvailiabilityClient newAvailabilityClient(@NonNull final MSV3ClientConfig config)
	{
		config.assertVersion(getVersionId());

		return MSV3AvailiabilityClientImpl.builder()
				.connectionFactory(connectionFactory)
				.config(config)
				.jaxbConverters(StockAvailabilityJAXBConvertersV1.instance)
				.build();
	}

	@Override
	public MSV3PurchaseOrderClient newPurchaseOrderClient(@NonNull final MSV3ClientConfig config)
	{
		config.assertVersion(getVersionId());

		return MSV3PurchaseOrderClientImpl.builder()
				.connectionFactory(connectionFactory)
				.config(config)
				.supportIdProvider(supportIdProvider)
				.jaxbConverters(OrderJAXBConvertersV1.instance)
				.build();
	}
}
