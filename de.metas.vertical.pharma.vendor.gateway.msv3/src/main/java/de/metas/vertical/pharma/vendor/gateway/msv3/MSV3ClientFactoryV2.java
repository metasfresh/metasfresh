package de.metas.vertical.pharma.vendor.gateway.msv3;

import org.springframework.stereotype.Service;

import de.metas.vertical.pharma.vendor.gateway.msv3.availability.MSV3AvailiabilityClient;
import de.metas.vertical.pharma.vendor.gateway.msv3.availability.MSV3AvailiabilityClientV2;
import de.metas.vertical.pharma.vendor.gateway.msv3.config.MSV3ClientConfig;
import de.metas.vertical.pharma.vendor.gateway.msv3.purchaseOrder.MSV3PurchaseOrderClient;
import de.metas.vertical.pharma.vendor.gateway.msv3.purchaseOrder.MSV3PurchaseOrderClientV2;
import de.metas.vertical.pharma.vendor.gateway.msv3.testconnection.MSV3TestConnectionClient;
import de.metas.vertical.pharma.vendor.gateway.msv3.testconnection.MSV3TestConnectionClientV2;
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

	public MSV3ClientFactoryV2(@NonNull final MSV3ConnectionFactory connectionFactory)
	{
		this.connectionFactory = connectionFactory;
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
		return new MSV3TestConnectionClientV2(connectionFactory, config);
	}

	@Override
	public MSV3AvailiabilityClient newAvailabilityClient(@NonNull final MSV3ClientConfig config)
	{
		config.assertVersion(getVersionId());
		return new MSV3AvailiabilityClientV2(connectionFactory, config);
	}

	@Override
	public MSV3PurchaseOrderClient newPurchaseOrderClient(@NonNull final MSV3ClientConfig config)
	{
		config.assertVersion(getVersionId());
		return MSV3PurchaseOrderClientV2.builder()
				.connectionFactory(connectionFactory)
				.config(config)
				.build();
	}

}
