package de.metas.costing;

import java.math.BigDecimal;

import org.adempiere.util.Check;

import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
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
public class CostDetailEvent
{
	CostSegment costSegment;
	int costElementId;
	CostingMethod costingMethod;
	CostingDocumentRef documentRef;

	Quantity qty;
	CostAmount amt;
	CostAmount price;

	int currencyId;
	int precision;

	@Builder
	private CostDetailEvent(
			@NonNull final CostSegment costSegment,
			final int costElementId,
			final CostingMethod costingMethod,
			@NonNull final CostingDocumentRef documentRef,
			@NonNull final BigDecimal amt,
			@NonNull final BigDecimal price,
			@NonNull final Quantity qty,
			final int currencyId,
			final int precision)
	{
		Check.assume(currencyId > 0, "currencyId > 0");
		Check.assume(precision >= 0, "precision >= 0");

		this.costSegment = costSegment;
		this.costElementId = costElementId;
		this.costingMethod = costingMethod;
		this.documentRef = documentRef;
		this.qty = qty;
		this.amt = CostAmount.of(amt, currencyId);
		this.price = CostAmount.of(price, currencyId);
		this.currencyId = currencyId;
		this.precision = precision;
	}
}
