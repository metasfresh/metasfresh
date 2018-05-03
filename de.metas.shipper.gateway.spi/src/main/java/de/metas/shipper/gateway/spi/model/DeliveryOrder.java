package de.metas.shipper.gateway.spi.model;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.shipper.gateway.api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Builder(toBuilder = true)
@Value
public class DeliveryOrder
{
	@Nullable
	OrderId orderId;

	@Nullable
	CustomDeliveryData customDeliveryOrderData;

	@Nullable
	OrderStatus orderStatus;

	@NonNull
	private Address pickupAddress;

	@NonNull
	private PickupDate pickupDate;

	@Nullable
	private String pickupNote;

	@NonNull
	private Address deliveryAddress;

	@Nullable
	private ContactPerson deliveryContact;

	@Nullable
	private DeliveryDate deliveryDate;

	@Nullable
	private String deliveryNote;

	@Nullable
	private String customerReference;

	@NonNull
	@Singular
	private ImmutableList<DeliveryPosition> deliveryPositions;

	@NonNull
	private ServiceType serviceType;

	@NonNull
	private PaidMode paidMode;

	@NonNull
	private SelfDelivery selfDelivery;

	@NonNull
	private SelfPickup selfPickup;

	/** ID in external repository */
	private int repoId;
	private int shipperId;
}
