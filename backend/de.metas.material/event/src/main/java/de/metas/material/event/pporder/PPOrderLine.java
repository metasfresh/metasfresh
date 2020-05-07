package de.metas.material.event.pporder;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.material.event.commons.ProductDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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
public class PPOrderLine
{
	String description;

	int productBomLineId;

	/**
	 * Can contain the {@code PP_Order_BOMLine_ID} of the production order document line this is all about, but also note that there might not yet exist one.
	 */
	int ppOrderLineId;

	/**
	 * Specifies whether this line is about a receipt (co-product or by-product) or about an issue.<br>
	 * Note that this is somewhat redundant with the {@link #getComponentType()} properties, but in material-dispo
	 * we don't want to depend on the BL to evaluate the component type.
	 */
	boolean receipt;

	Date issueOrReceiveDate;

	ProductDescriptor productDescriptor;

	BigDecimal qtyRequired;

	@JsonCreator
	@Builder(toBuilder = true)
	public PPOrderLine(
			@JsonProperty("description") final String description,
			@JsonProperty("productBomLineId") final int productBomLineId,
			@JsonProperty("ppOrderLineId") final int ppOrderLineId,
			@JsonProperty("receipt") @NonNull final Boolean receipt,
			@JsonProperty("productDescriptor") @NonNull final ProductDescriptor productDescriptor,
			@JsonProperty("issueOrReceiveDate") @NonNull final Date issueOrReceiveDate,
			@JsonProperty("qtyRequired") @NonNull final BigDecimal qtyRequired)
	{
		this.description = description;

		// don't assert that the ID is greater that zero, because this might not be the case with "handmade" PPOrders
		this.productBomLineId = productBomLineId;

		this.ppOrderLineId = ppOrderLineId;
		this.receipt = receipt;
		this.productDescriptor = productDescriptor;

		this.qtyRequired = qtyRequired;

		this.issueOrReceiveDate = issueOrReceiveDate;
	}
}
