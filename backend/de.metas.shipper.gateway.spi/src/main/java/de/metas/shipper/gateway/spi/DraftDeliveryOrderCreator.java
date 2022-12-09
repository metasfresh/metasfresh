package de.metas.shipper.gateway.spi;

import de.metas.async.AsyncBatchId;
import de.metas.mpackage.PackageId;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipping.ShipperId;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

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
	class CreateDraftDeliveryOrderRequest
	{
		DeliveryOrderKey deliveryOrderKey;

		int allPackagesGrossWeightInKg;
		String allPackagesContentDescription;
		Set<PackageId> mpackageIds;
	}

	@Value
  	class DeliveryOrderKey
	{
		ShipperId shipperId;
		ShipperTransportationId shipperTransportationId;
		int fromOrgId;				
		int deliverToBPartnerId;
		int deliverToBPartnerLocationId;
		LocalDate pickupDate;
		LocalTime timeFrom;
		LocalTime timeTo;
		AsyncBatchId asyncBatchId;

		@Builder
		public DeliveryOrderKey(
				final ShipperId shipperId,
				final ShipperTransportationId shipperTransportationId,
				final int fromOrgId,
				final int deliverToBPartnerId,
				final int deliverToBPartnerLocationId,
				@NonNull final LocalDate pickupDate,
				@NonNull final LocalTime timeFrom,
				@NonNull final LocalTime timeTo,
				@Nullable final AsyncBatchId asyncBatchId)
		{
			Check.assume(shipperId != null, "shipperId != null");
			Check.assume(shipperTransportationId != null, "shipperTransportationId != null");
			Check.assume(fromOrgId > 0, "fromOrgId > 0");
			Check.assume(deliverToBPartnerId > 0, "deliverToBPartnerId > 0");
			Check.assume(deliverToBPartnerLocationId > 0, "deliverToBPartnerLocationId > 0");

			this.shipperId = shipperId;
			this.shipperTransportationId = shipperTransportationId;
			this.fromOrgId = fromOrgId;
			this.deliverToBPartnerId = deliverToBPartnerId;
			this.deliverToBPartnerLocationId = deliverToBPartnerLocationId;
			this.pickupDate = pickupDate;
			this.timeFrom = timeFrom;
			this.timeTo = timeTo;

			this.asyncBatchId = asyncBatchId;
		}
	}
}
