package de.metas.material.event.pporder;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
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

/*
 * #%L
 * metasfresh-material-planning
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

/**
 * Not needed, because it can be taken directly from the parent ppOrder:
 * <ul>
 * <li>orgId</li>
 * <li>warehouseId</li>
 * <li>locatorId</li>
 * </ul>
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Value
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class PPOrderLine
{
	String description;

	int productBomLineId;

	/**
	 * Can contain the {@code PP_Order_BOMLine_ID} of the production order document line this is all about, but also note that there might not yet exist one.
	 */
	int ppOrderLineId;

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
	public PPOrderLine(
			@JsonProperty("description") final String description,
			@JsonProperty("productBomLineId") final int productBomLineId,
			@JsonProperty("ppOrderLineId") final int ppOrderLineId,
			@JsonProperty("receipt") @NonNull final Boolean receipt,
			@JsonProperty("productDescriptor") @NonNull final ProductDescriptor productDescriptor,
			@JsonProperty("minMaxDescriptor") @Nullable final MinMaxDescriptor minMaxDescriptor,
			@JsonProperty("issueOrReceiveDate") @NonNull final Instant issueOrReceiveDate,
			@JsonProperty("qtyRequired") @NonNull final BigDecimal qtyRequired,
			@JsonProperty("qtyDelivered") @Nullable final BigDecimal qtyDelivered)
	{
		this.description = description;

		// don't assert that the ID is greater that zero, because this might not be the case with "handmade" PPOrders
		this.productBomLineId = productBomLineId;

		this.ppOrderLineId = ppOrderLineId;
		this.receipt = receipt;
		this.productDescriptor = productDescriptor;
		this.minMaxDescriptor = minMaxDescriptor;

		this.qtyRequired = qtyRequired;
		this.qtyDelivered = qtyDelivered;

		this.issueOrReceiveDate = issueOrReceiveDate;
	}

	public PPOrderLine withQtyRequired(final BigDecimal qtyRequired)
	{
		return toBuilder().qtyRequired(qtyRequired).build();
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
