package de.metas.vendor.gateway.api.availability;

import javax.annotation.Nullable;

import de.metas.vendor.gateway.api.ProductAndQuantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

@Value
public class AvailabilityRequestItem
{
	TrackingId trackingId;
	ProductAndQuantity productAndQuantity;
	int salesOrderLineId;
	int purchaseCandidateId;

	@Builder
	private AvailabilityRequestItem(
			final TrackingId trackingId,
			@NonNull final ProductAndQuantity productAndQuantity,
			@Nullable final int salesOrderLineId,
			final int purchaseCandidateId)
	{
		this.trackingId = trackingId != null ? trackingId : TrackingId.random();
		this.productAndQuantity = productAndQuantity;
		this.salesOrderLineId = salesOrderLineId > 0 ? salesOrderLineId : -1;
		this.purchaseCandidateId = purchaseCandidateId > 0 ? purchaseCandidateId : -1;
	}

}
