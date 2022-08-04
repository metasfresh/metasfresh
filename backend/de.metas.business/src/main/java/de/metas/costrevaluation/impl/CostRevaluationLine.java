package de.metas.costrevaluation.impl;

import de.metas.costing.CostPrice;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Nullable;

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

@Getter
@ToString
public final class CostRevaluationLine
{
	private final CostRevaluationId costRevaluationId;
	private final ProductId productId;
	@Setter
	private CostRevaluationLineId id;
	private CostPrice currentCostPrice;
	@Nullable private CostPrice newCostPrice;
	private Quantity currentQty;

	@Builder
	private CostRevaluationLine(
			final CostRevaluationLineId id,
			@NonNull final CostRevaluationId costRevaluationId,
			@NonNull final ProductId productId,
			@NonNull final CostPrice currentCostPrice,
			@NonNull final Quantity currentQty,
			final CostPrice newCostPrice
	)
	{
		this.id = id;

		this.costRevaluationId = costRevaluationId;
		this.productId = productId;

		this.currentCostPrice = currentCostPrice;
		this.currentQty = currentQty;
		this.newCostPrice = newCostPrice;
	}

	public void setNewCostPrice(@NonNull final CostPrice costPrice)
	{
		this.newCostPrice = costPrice;
	}

}
