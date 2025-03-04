package de.metas.shipper.gateway.spi.model;

import com.google.common.collect.ImmutableList;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipping.ShipperId;
import de.metas.shipping.model.ShipperTransportationId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;

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
	 * No idea what this field does, as there's a {@link de.metas.shipper.gateway.spi.DeliveryOrderId} field as well}.
	 *
	 * @deprecated Not sure if it's correct to deprecate this, but i believe we should use {@link #id} instead when persisting the DeliveryOrder
	 */
	@Deprecated
	@Nullable
	OrderId orderId;

	@Nullable
	CustomDeliveryData customDeliveryData;

	@Nullable
	OrderStatus orderStatus;

	@NonNull
	Address pickupAddress;

	@NonNull
	PickupDate pickupDate;

	/**
	 * Not received from anywhere (in DraftDeliveryOrderCreator)
	 */
	@Nullable
	String pickupNote;

	@NonNull
	Address deliveryAddress;

	@Nullable
	ContactPerson deliveryContact;

	@Nullable
	DeliveryDate deliveryDate;

	/**
	 * Not received from anywhere (in DraftDeliveryOrderCreator)
	 */
	@Nullable
	String deliveryNote;

	/**
	 * Not received from anywhere (in DraftDeliveryOrderCreator)
	 */
	@Nullable
	String customerReference;

	/**
	 * @deprecated This class has a bad data structure and should not be used in the future. Please use instead {@link #deliveryOrderLines}.
	 * <p>
	 * We should update GO, DerKurier and DHL to use deliveryOrderLines as well if possible.
	 * <p>
	 * The big (and only) difference is that a DeliveryOrderLine stores 1 package per line,
	 * whilst DeliveryPosition stores multiple packages per position, and there's no easy way to match the shipper "expected" packages
	 * (each with their own dimensions/size, content) with the specific package inside a delivery position.
	 */
	@NonNull
	@Singular
	@Deprecated
	ImmutableList<DeliveryPosition> deliveryPositions;

	/**
	 * 1 delivery lines represents 1 package
	 */
	@NonNull
	@Singular
	ImmutableList<DeliveryOrderLine> deliveryOrderLines;

	/**
	 * The Shipper Product
	 */
	@NonNull
	ShipperProduct shipperProduct;

	/**
	 * ID in external repository
	 */
	DeliveryOrderId id;

	ShipperId shipperId;

	/**
	 * Transportation Order id
	 */
	ShipperTransportationId shipperTransportationId;

	@Nullable
	String trackingNumber;

	@Nullable
	String trackingUrl;


	public DeliveryOrder withCustomDeliveryData(@Nullable final CustomDeliveryData customDeliveryData)
	{
		if (customDeliveryData == null)
		{
			return this;
		}
		return this.toBuilder()
				.customDeliveryData(customDeliveryData)
				.build();
	}
}
