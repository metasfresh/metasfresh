/*
 * #%L
 * metasfresh-material-event
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.material.event.pporder;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.material.event.commons.MinMaxDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

@Value
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class PPOrderLineData
{
	String description;

	int productBomLineId;

	/**
	 * Specifies whether this line is about a receipt (co-product or by-product) or about an issue.
	 */
	boolean receipt;

	Instant issueOrReceiveDate;

	ProductDescriptor productDescriptor;

	@Nullable
	MinMaxDescriptor minMaxDescriptor;

	/** qty in stocking UOM */
	BigDecimal qtyRequired;

	/** qty in stocking UOM */
	BigDecimal qtyDelivered;

	@JsonCreator
	@Builder(toBuilder = true)
	public PPOrderLineData(
			@JsonProperty("description") final String description,
			@JsonProperty("productBomLineId") final int productBomLineId,
			@JsonProperty("receipt") @NonNull final Boolean receipt,
			@JsonProperty("issueOrReceiveDate") @NonNull final Instant issueOrReceiveDate,
			@JsonProperty("productDescriptor") @NonNull final ProductDescriptor productDescriptor,
			@JsonProperty("minMaxDescriptor") @Nullable final MinMaxDescriptor minMaxDescriptor,
			@JsonProperty("qtyRequired") @NonNull final BigDecimal qtyRequired,
			@JsonProperty("qtyDelivered") @Nullable final BigDecimal qtyDelivered)
	{
		this.description = description;

		// don't assert that the ID is greater that zero, because this might not be the case with "handmade" PPOrders
		this.productBomLineId = productBomLineId;

		this.receipt = receipt;
		this.issueOrReceiveDate = issueOrReceiveDate;
		this.productDescriptor = productDescriptor;
		this.minMaxDescriptor = minMaxDescriptor;
		this.qtyRequired = qtyRequired;
		this.qtyDelivered = qtyDelivered;
	}

	public BigDecimal getQtyOpen()
	{
		return getQtyRequired().subtract(getQtyDelivered());
	}

	public BigDecimal getQtyOpenNegateIfReceipt()
	{
		if (isReceipt())
		{
			return getQtyOpen().negate();
		}
		else
		{
			return getQtyOpen();
		}
	}
}