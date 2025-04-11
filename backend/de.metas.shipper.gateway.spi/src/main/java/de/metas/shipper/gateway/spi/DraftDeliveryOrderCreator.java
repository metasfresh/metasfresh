package de.metas.shipper.gateway.spi;

import com.google.common.collect.ImmutableSet;
import de.metas.async.AsyncBatchId;
import de.metas.mpackage.PackageId;
import de.metas.shipper.gateway.api.ShipperGatewayId;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipping.ShipperId;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
	ShipperGatewayId getShipperGatewayId();

	DeliveryOrder createDraftDeliveryOrder(CreateDraftDeliveryOrderRequest request);

	@Value
	@Builder
	class CreateDraftDeliveryOrderRequest
	{
		@NonNull DeliveryOrderKey deliveryOrderKey;
		@NonNull Set<PackageInfo> packageInfos;

		public Set<PackageId> getPackageIds() {return packageInfos.stream().map(PackageInfo::getPackageId).collect(ImmutableSet.toImmutableSet());}

		public BigDecimal getAllPackagesGrossWeightInKg(@NonNull final BigDecimal minWeightKg)
		{
			final BigDecimal weightInKg = packageInfos.stream()
					.map(PackageInfo::getWeightInKg)
					.filter(weight -> weight != null && weight.signum() > 0)
					.reduce(BigDecimal.ZERO, BigDecimal::add);

			return weightInKg.max(minWeightKg);
		}

		@NonNull
		public Optional<String> getAllPackagesContentDescription()
		{
			final String description = packageInfos.stream()
					.map(PackageInfo::getDescription)
					.map(StringUtils::trimBlankToNull)
					.filter(Objects::nonNull)
					.collect(Collectors.joining(", "));
			return StringUtils.trimBlankToOptional(description);
		}

		@Value
		@Builder
		public static class PackageInfo
		{
			@NonNull PackageId packageId;
			@Nullable String poReference;
			@Nullable String description;
			@Nullable BigDecimal weightInKg;

			public BigDecimal getWeightInKgOr(BigDecimal minValue) {return weightInKg != null ? weightInKg.max(minValue) : minValue;}
		}
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
