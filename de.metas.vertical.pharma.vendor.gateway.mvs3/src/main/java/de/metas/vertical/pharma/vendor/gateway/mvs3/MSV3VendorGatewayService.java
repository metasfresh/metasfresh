package de.metas.vertical.pharma.vendor.gateway.mvs3;

import org.springframework.stereotype.Service;

import de.metas.vendor.gateway.api.VendorGatewayService;
import de.metas.vendor.gateway.api.availability.AvailabilityRequest;
import de.metas.vendor.gateway.api.availability.AvailabilityResponse;
import de.metas.vendor.gateway.api.order.PurchaseOrderRequest;
import de.metas.vendor.gateway.api.order.PurchaseOrderResponse;
import de.metas.vertical.pharma.vendor.gateway.mvs3.availability.MSV3AvailiabilityClient;
import de.metas.vertical.pharma.vendor.gateway.mvs3.config.MSV3ClientConfig;
import de.metas.vertical.pharma.vendor.gateway.mvs3.config.MSV3ClientConfigRepository;
import de.metas.vertical.pharma.vendor.gateway.mvs3.purchaseOrder.MSV3PurchaseOrderClient;
import lombok.NonNull;

/*
 * #%L
 * de.metas.vendor.gateway.mvs3
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
public class MSV3VendorGatewayService implements VendorGatewayService
{
	private final MSV3ClientConfigRepository configRepo;
	private final MSV3ConnectionFactory connectionFactory;

	public MSV3VendorGatewayService(
			@NonNull final MSV3ConnectionFactory connectionFactory,
			@NonNull final MSV3ClientConfigRepository configRepo)
	{
		this.configRepo = configRepo;
		this.connectionFactory = connectionFactory;
	}

	@Override
	public boolean isProvidedForVendor(final int vendorId)
	{
		return configRepo.getretrieveByVendorIdOrNull(vendorId) != null;
	}

	@Override
	public AvailabilityResponse retrieveAvailability(@NonNull final AvailabilityRequest request)
	{
		final MSV3ClientConfig config = configRepo.retrieveByVendorId(request.getVendorId());
		final MSV3AvailiabilityClient client = new MSV3AvailiabilityClient(connectionFactory, config);

		return client.retrieveAvailability(request);
	}

	@Override
	public PurchaseOrderResponse placePurchaseOrder(@NonNull final PurchaseOrderRequest request)
	{
		final MSV3ClientConfig config = configRepo.retrieveByVendorId(request.getVendorId());
		final MSV3PurchaseOrderClient client = MSV3PurchaseOrderClient.builder()
				.config(config)
				.connectionFactory(connectionFactory).build();

		return client.placePurchaseOrder(request);
	}
}
