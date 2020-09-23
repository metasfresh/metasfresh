package de.metas.material.event.ddorder;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import de.metas.material.event.commons.MinMaxDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/*
 * #%L
 * metasfresh-mrp
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
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class DDOrderLine
{
	int salesOrderLineId;

	@NonNull
	ProductDescriptor productDescriptor;

	@Nullable
	MinMaxDescriptor fromWarehouseMinMaxDescriptor;

	@NonNull
	BigDecimal qty;

	/**
	 * {@link DDOrder#getDatePromised()} minus this number of days tells us when the distribution for this particular line needs to start
	 */
	int durationDays;

	int networkDistributionLineId;

	int ddOrderLineId;

	int bPartnerId;

	@JsonCreator
	@Builder
	public DDOrderLine(
			@JsonProperty("salesOrderLineId") final int salesOrderLineId,
			@JsonProperty("productDescriptor") @NonNull final ProductDescriptor productDescriptor,
			@JsonProperty("fromWarehouseMinMaxDescriptor") @Nullable final MinMaxDescriptor fromWarehouseMinMaxDescriptor,
			@JsonProperty("bPartnerId") final int bPartnerId,
 			@JsonProperty("qty") @NonNull final BigDecimal qty,
			@JsonProperty("durationDays") final int durationDays,
			@JsonProperty("networkDistributionLineId") final int networkDistributionLineId,
			@JsonProperty("ddOrderLineId") final int ddOrderLineId)
	{
		Preconditions.checkArgument(durationDays >= 0, "The Given parameter durationDays=%s needs to be > 0", "durationDays");

		this.salesOrderLineId = salesOrderLineId;

		this.productDescriptor = productDescriptor;
		this.fromWarehouseMinMaxDescriptor = fromWarehouseMinMaxDescriptor;

		this.bPartnerId = bPartnerId;

		this.qty = qty;

		this.durationDays = durationDays;

		this.networkDistributionLineId = networkDistributionLineId; // can be <= 0 if the DD_Order was created "manually"

		this.ddOrderLineId = ddOrderLineId;
	}
}
