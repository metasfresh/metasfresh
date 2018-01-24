package de.metas.vertical.pharma.vendor.gateway.mvs3;

import org.springframework.stereotype.Service;

import de.metas.vendor.gateway.api.VendorGatewayService;
import de.metas.vendor.gateway.api.model.AvailabilityRequest;
import de.metas.vendor.gateway.api.model.AvailabilityResponse;
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
	public AvailabilityResponse retrieveAvailability(@NonNull final AvailabilityRequest request)
	{
		final MSV3ClientConfig config = configRepo.retrieveByVendorId(request.getVendorId());
		final MSV3Client client = new MSV3Client(connectionFactory, config);

		return client.retrieveAvailability(request);
	}

	@Override
	public boolean isProvidedForVendor(final int vendorId)
	{
		return configRepo.getretrieveByVendorIdOrNull(vendorId) != null;
	}
}
