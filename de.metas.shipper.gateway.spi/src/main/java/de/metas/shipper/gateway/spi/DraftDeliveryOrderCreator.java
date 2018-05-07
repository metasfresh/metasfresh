package de.metas.shipper.gateway.spi;

import java.time.LocalDate;
import java.util.Set;

import org.adempiere.util.Check;

import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.shipper.gateway.api
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

public interface DraftDeliveryOrderCreator
{
	String getShipperGatewayId();

	DeliveryOrder createDraftDeliveryOrder(CreateDraftDeliveryOrderRequest request);

	@Value
	@Builder
	public static class CreateDraftDeliveryOrderRequest
	{
		DeliveryOrderKey deliveryOrderKey;

		int grossWeightInKg;
		String packageContentDescription;
		Set<Integer> mpackageIds;
	}

	@Value
	public static final class DeliveryOrderKey
	{
		int shipperId;
		int shipperTransportationId;
		int fromOrgId;
		int deliverToBPartnerId;
		int deliverToBPartnerLocationId;
		LocalDate pickupDate;

		@Builder
		public DeliveryOrderKey(
				final int shipperId,
				final int shipperTransportationId,
				final int fromOrgId,
				final int deliverToBPartnerId,
				final int deliverToBPartnerLocationId,
				@NonNull final LocalDate pickupDate)
		{
			Check.assume(shipperId > 0, "shipperId > 0");
			Check.assume(shipperTransportationId > 0, "shipperTransportationId > 0");
			Check.assume(fromOrgId > 0, "fromOrgId > 0");
			Check.assume(deliverToBPartnerId > 0, "deliverToBPartnerId > 0");
			Check.assume(deliverToBPartnerLocationId > 0, "deliverToBPartnerLocationId > 0");

			this.shipperId = shipperId;
			this.shipperTransportationId = shipperTransportationId;
			this.fromOrgId = fromOrgId;
			this.deliverToBPartnerId = deliverToBPartnerId;
			this.deliverToBPartnerLocationId = deliverToBPartnerLocationId;
			this.pickupDate = pickupDate;
		}
	}
}
