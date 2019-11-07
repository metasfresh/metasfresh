package de.metas.shipper.gateway.spi.model;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipping.ShipperId;
import de.metas.shipping.api.ShipperTransportationId;
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
	/**
	 * No idea what this field does, as there's a {@link de.metas.shipper.gateway.spi.DeliveryOrderId} field as well}
	 */
	@Nullable
	OrderId orderId;

	@Nullable
	CustomDeliveryData customDeliveryData;

	@Nullable
	OrderStatus orderStatus;

	@NonNull
	private Address pickupAddress;

	@NonNull
	private PickupDate pickupDate;

	/**
	 * Not received from anywhere (in DraftDeliveryOrderCreator)
	 */
	@Nullable
	private String pickupNote;

	@NonNull
	private Address deliveryAddress;

	@Nullable
	private ContactPerson deliveryContact;

	@Nullable
	private DeliveryDate deliveryDate;

	/**
	 * Not received from anywhere (in DraftDeliveryOrderCreator)
	 */
	@Nullable
	private String deliveryNote;

	/**
	 * Not received from anywhere (in DraftDeliveryOrderCreator)
	 */
	@Nullable
	private String customerReference;

	/**
	 * @deprecated This class has a bad data structure and should not be used in the future. Please use instead {@link #deliveryOrderLines}.
	 * <p>
	 * We should update GO, DerKurier and DHL to use deliveryOrderLines as well if possible.
	 */
	@NonNull
	@Singular
	@Deprecated
	private ImmutableList<DeliveryPosition> deliveryPositions;

	/**
	 * 1 delivery lines represents 1 package
	 */
	@NonNull
	@Singular
	private ImmutableList<DeliveryOrderLine> deliveryOrderLines;

	/**
	 * The Shipper Product
	 */
	@NonNull
	private ServiceType serviceType;

	/**
	 * ID in external repository
	 */
	private DeliveryOrderId repoId;

	private ShipperId shipperId;

	/**
	 * Transportation Order id
	 */
	private ShipperTransportationId shipperTransportationId;

	/**
	 * Not necessarily useful since we already store the weight for each package in deliveryOrderLine
	 * Maybe should be deprecated/removed.
	 */
	int allPackagesGrossWeightInKg;
}
