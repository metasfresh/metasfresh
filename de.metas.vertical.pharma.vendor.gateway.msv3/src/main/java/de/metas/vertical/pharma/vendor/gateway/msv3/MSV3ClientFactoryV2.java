package de.metas.vertical.pharma.vendor.gateway.msv3;

import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;

import de.metas.vertical.pharma.msv3.protocol.order.v2.OrderJAXBConvertersV2;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.v2.StockAvailabilityJAXBConvertersV2;
import de.metas.vertical.pharma.msv3.protocol.types.FaultInfo;
import de.metas.vertical.pharma.msv3.protocol.util.v2.MiscJAXBConvertersV2;
import de.metas.vertical.pharma.vendor.gateway.msv3.availability.MSV3AvailiabilityClient;
import de.metas.vertical.pharma.vendor.gateway.msv3.availability.MSV3AvailiabilityClientImpl;
import de.metas.vertical.pharma.vendor.gateway.msv3.config.MSV3ClientConfig;
import de.metas.vertical.pharma.vendor.gateway.msv3.purchaseOrder.MSV3PurchaseOrderClient;
import de.metas.vertical.pharma.vendor.gateway.msv3.purchaseOrder.MSV3PurchaseOrderClientImpl;
import de.metas.vertical.pharma.vendor.gateway.msv3.purchaseOrder.SupportIdProvider;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.Msv3FaultInfo;
import de.metas.vertical.pharma.vendor.gateway.msv3.testconnection.MSV3TestConnectionClient;
import de.metas.vertical.pharma.vendor.gateway.msv3.testconnection.MSV3TestConnectionClientImpl;
import de.metas.vertical.pharma.vendor.gateway.msv3.testconnection.v2.TestConnectionJAXBConvertersV2;
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
public class MSV3ClientFactoryV2 implements MSV3ClientFactory
{
	private final MSV3ConnectionFactory connectionFactory;
	private final SupportIdProvider supportIdProvider;

	public MSV3ClientFactoryV2(
			@NonNull final MSV3ConnectionFactory connectionFactory,
			@NonNull final SupportIdProvider supportIdProvider)
	{
		this.connectionFactory = connectionFactory;
		this.supportIdProvider = supportIdProvider;
	}

	@Override
	public String getVersionId()
	{
		return MSV3ClientConfig.VERSION_2.getId();
	}

	@Override
	public MSV3TestConnectionClient newTestConnectionClient(@NonNull final MSV3ClientConfig config)
	{
		config.assertVersion(getVersionId());

		return MSV3TestConnectionClientImpl.builder()
				.connectionFactory(connectionFactory)
				.config(config)
				.jaxbConverters(TestConnectionJAXBConvertersV2.instance)
				.build();
	}

	@Override
	public MSV3AvailiabilityClient newAvailabilityClient(@NonNull final MSV3ClientConfig config)
	{
		config.assertVersion(getVersionId());

		return MSV3AvailiabilityClientImpl.builder()
				.connectionFactory(connectionFactory)
				.config(config)
				.jaxbConverters(StockAvailabilityJAXBConvertersV2.instance)
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
				.jaxbConverters(OrderJAXBConvertersV2.instance)
				.build();
	}

	@VisibleForTesting
	public static FaultInfo extractFaultInfoOrNull(final Object value)
	{
		if (value instanceof Msv3FaultInfo)
		{
			final Msv3FaultInfo msv3FaultInfo = (Msv3FaultInfo)value;
			return MiscJAXBConvertersV2.fromJAXB(msv3FaultInfo);
		}
		else
		{
			return null;
		}
	}
}
