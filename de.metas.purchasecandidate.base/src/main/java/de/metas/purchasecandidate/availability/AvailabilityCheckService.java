package de.metas.purchasecandidate.availability;

import org.springframework.stereotype.Service;

import de.metas.vendor.gateway.api.VendorGatewayRegistry;
import lombok.NonNull;

/*
 * #%L
 * de.metas.purchasecandidate.base
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
public class AvailabilityCheckService
{
	private final VendorGatewayRegistry vendorGatewayRegistry;

	public AvailabilityCheckService(@NonNull final VendorGatewayRegistry vendorGatewayRegistry)
	{
		this.vendorGatewayRegistry = vendorGatewayRegistry;
	}

	public AvailabilityMultiResult checkAvailability(@NonNull final PurchaseCandidatesAvailabilityRequest request)
	{
		return newAvailabilityCheckCommand(request).checkAvailability();
	}

	public void checkAvailabilityAsync(@NonNull final PurchaseCandidatesAvailabilityRequest request, @NonNull final AvailabilityCheckCallback callback)
	{
		newAvailabilityCheckCommand(request).checkAvailabilityAsync(callback);
	}

	private AvailabilityCheckCommand newAvailabilityCheckCommand(final PurchaseCandidatesAvailabilityRequest request)
	{
		return AvailabilityCheckCommand.builder()
				.vendorGatewayRegistry(vendorGatewayRegistry)
				.request(request)
				.build();
	}

}
