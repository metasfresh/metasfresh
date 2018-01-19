package de.metas.vendor.gateway.api;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import lombok.NonNull;

/*
 * #%L
 * de.metas.vendor.gateway.api
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
public class VendorGatewayRegistry
{
	private static final Logger logger = LoggerFactory.getLogger(VendorGatewayRegistry.class);

	private final List<VendorGatewayService> services;

	public VendorGatewayRegistry(@NonNull final Optional<List<VendorGatewayService>> services)
	{
		this.services = ImmutableList.copyOf(services.orElse(ImmutableList.of()));

		logger.info("Services: {}", services);
	}

	public List<VendorGatewayService> getVendorGatewayService(final int vendorId)
	{
		return services.stream()
				.filter(service -> service.isProvidedForVendor(vendorId))
				.collect(ImmutableList.toImmutableList());

	}
}
