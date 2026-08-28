/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.handlingunits.impl;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.organization.OrgId;
import de.metas.shipping.ShipperId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalTime;

@Value
@Builder
public class CreateShipperTransportationRequest
{
	public static final LocalTime DEFAULT_PICKUP_TIME_FROM = LocalTime.of(8, 0);
	public static final LocalTime DEFAULT_PICKUP_TIME_TO = LocalTime.of(17, 0);
	@NonNull
	OrgId orgId;

	@NonNull
	ShipperId shipperId;

	@NonNull
	BPartnerLocationId shipperBPartnerAndLocationId;

	@NonNull
	LocalDate shipDate;

	@NonNull @Builder.Default LocalTime pickupTimeFrom = DEFAULT_PICKUP_TIME_FROM;

	@NonNull @Builder.Default LocalTime pickupTimeTo = DEFAULT_PICKUP_TIME_TO;

	/**
	 * Should be {@code false} if metasfresh on-the-fly-picked HUs, but the user doesn't need to know which ones.
	 * Should be {@code true} if the user is supposed to physically in the real world find and put into the package exactly those HUs that metasfresh picked on the fly.
	 */
	boolean assignAnonymouslyPickedHUs;

	/**
	 * Whether the shipment/receipt this transport order is created for is a sales transaction ({@code true}) or a
	 * purchase transaction ({@code false}). Drives the created record's {@code TransportDirection}.
	 * <p>
	 * Boxed on purpose: {@code M_ShipperTransportation.TransportDirection} lost its database default so that no
	 * direction is ever invented, and a primitive {@code boolean} would reinstate that default one layer up - a
	 * caller omitting {@code .isSOTrx(...)} would silently get {@code false} -> {@code Incoming}. As a
	 * {@code @NonNull Boolean} the omission fails loudly in {@code build()} instead.
	 */
	@NonNull Boolean isSOTrx;
}
