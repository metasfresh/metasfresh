/*
 * #%L
 * de.metas.shipper.gateway.spi
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.shipper.gateway.spi.model;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.CoalesceUtil;
import de.metas.shipping.mpackage.PackageId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * 1 DeliveryOrderParcel represents 1 Package
 */
@Value
public class DeliveryOrderParcel
{
	@Nullable DeliveryOrderParcelId id;
	@Nullable String content;
	@NonNull BigDecimal grossWeightKg;
	@NonNull PackageDimensions packageDimensions;
	@Nullable CustomDeliveryLineData customDeliveryLineData;
	@NonNull PackageId packageId;
	@Nullable String awb;
	@Nullable String trackingUrl;
	@Nullable byte[] labelPdfBase64;
	@NonNull ImmutableList<DeliveryOrderItem> items;

	@Builder(toBuilder = true)
	@Jacksonized
	private DeliveryOrderParcel(
			@Nullable final DeliveryOrderParcelId id,
			@Nullable final String content,
			@NonNull final BigDecimal grossWeightKg,
			@NonNull final PackageDimensions packageDimensions,
			@Nullable final CustomDeliveryLineData customDeliveryLineData,
			@NonNull final PackageId packageId,
			@Nullable final String awb,
			@Nullable final String trackingUrl,
			@Nullable final byte[] labelPdfBase64,
			@Nullable final ImmutableList<DeliveryOrderItem> items)
	{
		this.awb = awb;
		this.trackingUrl = trackingUrl;
		this.labelPdfBase64 = labelPdfBase64;
		this.items = CoalesceUtil.coalesceNotNull(items, ImmutableList.of());

		Check.assume(grossWeightKg.signum() > 0, "grossWeightKg > 0");
		this.id = id;
		this.grossWeightKg = grossWeightKg;
		this.content = content;
		this.packageDimensions = packageDimensions;
		this.customDeliveryLineData = customDeliveryLineData;
		this.packageId = packageId;
	}

	public DeliveryOrderParcel withCustomDeliveryData(@NonNull final CustomDeliveryLineData customDeliveryLineData)
	{
		if (Objects.equals(this.customDeliveryLineData, customDeliveryLineData))
		{
			return this;
		}
		return this.toBuilder()
				.customDeliveryLineData(customDeliveryLineData)
				.build();
	}
}
