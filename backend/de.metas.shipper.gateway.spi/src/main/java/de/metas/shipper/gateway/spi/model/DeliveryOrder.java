package de.metas.shipper.gateway.spi.model;

import com.google.common.collect.ImmutableList;
import de.metas.shipper.gateway.spi.DeliveryOrderId;
import de.metas.shipping.ShipperId;
import de.metas.shipping.model.ShipperTransportationId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Objects;

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

@Value
@Builder(toBuilder = true)
@Jacksonized
public class DeliveryOrder
{
	/**
	 * ID in external repository
	 */
	@Nullable DeliveryOrderId id;
	/**
	 * No idea what this field does, as there's a {@link de.metas.shipper.gateway.spi.DeliveryOrderId} field as well}.
	 *
	 * @deprecated Not sure if it's correct to deprecate this, but i believe we should use {@link #id} instead when persisting the DeliveryOrder
	 */

	@Nullable @Deprecated OrderId orderId;
	@Nullable CustomDeliveryData customDeliveryData;
	@Nullable OrderStatus orderStatus;
	@NonNull Address pickupAddress;
	@NonNull PickupDate pickupDate;
	/**
	 * Not received from anywhere (in DraftDeliveryOrderCreator)
	 */
	@Nullable String pickupNote;
	@NonNull Address deliveryAddress;
	@Nullable ContactPerson deliveryContact;
	@Nullable DeliveryDate deliveryDate;
	/**
	 * Not received from anywhere (in DraftDeliveryOrderCreator)
	 */
	@Nullable String deliveryNote;

	/**
	 * Not received from anywhere (in DraftDeliveryOrderCreator)
	 */
	@Nullable String customerReference;

	/**
	 * @deprecated This class has a bad data structure and should not be used in the future. Please use instead {@link #deliveryOrderParcels}.
	 * <p>
	 * We should update GO, DerKurier and DHL to use deliveryOrderParcels as well if possible.
	 * <p>
	 * The big (and only) difference is that a DeliveryOrderParcel stores 1 package per line,
	 * whilst DeliveryPosition stores multiple packages per position, and there's no easy way to match the shipper "expected" packages
	 * (each with their own dimensions/size, content) with the specific package inside a delivery position.
	 */
	@NonNull @Singular @Deprecated ImmutableList<DeliveryPosition> deliveryPositions;

	/**
	 * 1 delivery lines represents 1 package
	 */
	@NonNull @Singular ImmutableList<DeliveryOrderParcel> deliveryOrderParcels;

	@NonNull ShipperId shipperId;
	@Nullable ShipperProduct shipperProduct;
	ShipperTransportationId shipperTransportationId;

	/**
	 * @deprecated An order would typically contain multiple packages to be shipped, each with its own tracking number.
	 * Such info should be stored in {@link DeliveryOrderParcel#getCustomDeliveryLineData()}
	 */
	@Nullable
	@Deprecated
	String trackingNumber;

	/**
	 * @deprecated An order would typically contain multiple packages to be shipped, each with its own tracking number.
	 * Such info should be stored in {@link DeliveryOrderParcel#getCustomDeliveryLineData()}
	 */
	@Nullable
	@Deprecated
	String trackingUrl;

	String shipperEORI;
	String receiverEORI;

	public DeliveryOrder withCustomDeliveryData(@NonNull final CustomDeliveryData customDeliveryData)
	{
		if (Objects.equals(this.customDeliveryData, customDeliveryData))
		{
			return this;
		}

		return this.toBuilder()
				.customDeliveryData(customDeliveryData)
				.build();
	}

	public DeliveryOrder withDeliveryOrderParcels(@NonNull final ImmutableList<DeliveryOrderParcel> deliveryOrderParcels)
	{
		if (Objects.equals(this.deliveryOrderParcels, deliveryOrderParcels))
		{
			return this;
		}
		return this.toBuilder()
				.deliveryOrderParcels(deliveryOrderParcels)
				.build();
	}
}

