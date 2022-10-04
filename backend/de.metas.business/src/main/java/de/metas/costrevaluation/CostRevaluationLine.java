package de.metas.costrevaluation;

import de.metas.costing.CostAmount;
import de.metas.costing.CostSegmentAndElement;
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
public class CostRevaluationLine
{
	@NonNull CostRevaluationLineId id;

	@NonNull CostSegmentAndElement costSegmentAndElement;

	@NonNull Quantity currentQty;
	@NonNull CostAmount currentCostPrice;
	@NonNull CostAmount newCostPrice;

	boolean isRevaluated;
	@NonNull CostAmount deltaAmountToBook;

	@Builder(toBuilder = true)
	private CostRevaluationLine(
			@NonNull final CostRevaluationLineId id,
			@NonNull final CostSegmentAndElement costSegmentAndElement,
			@NonNull final Quantity currentQty,
			@NonNull final CostAmount currentCostPrice,
			@NonNull final CostAmount newCostPrice,
			boolean isRevaluated,
			@NonNull final CostAmount deltaAmountToBook)
	{
		CostAmount.assertCurrencyMatching(currentCostPrice, newCostPrice, deltaAmountToBook);

		this.id = id;
		this.costSegmentAndElement = costSegmentAndElement;
		this.currentQty = currentQty;
		this.currentCostPrice = currentCostPrice;
		this.newCostPrice = newCostPrice;

		this.isRevaluated = isRevaluated;
		this.deltaAmountToBook = deltaAmountToBook;
	}

	public CostRevaluationLine markingAsEvaluated(@NonNull CostAmount deltaAmountToBook)
	{
		return toBuilder()
				.isRevaluated(true)
				.deltaAmountToBook(deltaAmountToBook)
				.build();
	}
}
