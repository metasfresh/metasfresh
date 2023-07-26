package de.metas.costing;

import de.metas.money.CurrencyId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

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
public class CostDetailPreviousAmounts
{
	public static CostDetailPreviousAmounts of(@NonNull final CurrentCost currentCost)
	{
		return builder()
				.costPrice(currentCost.getCostPrice())
				.qty(currentCost.getCurrentQty())
				.cumulatedAmt(currentCost.getCumulatedAmt())
				.cumulatedQty(currentCost.getCumulatedQty())
				.build();
	}

	@NonNull UomId uomId;
	@NonNull CurrencyId currencyId;

	@NonNull CostPrice costPrice;
	@NonNull Quantity qty;

	@NonNull CostAmount cumulatedAmt;
	@NonNull Quantity cumulatedQty;

	@Builder
	private CostDetailPreviousAmounts(
			@NonNull final CostPrice costPrice,
			@NonNull final Quantity qty,
			@NonNull final CostAmount cumulatedAmt,
			@NonNull final Quantity cumulatedQty)
	{
		if (!CurrencyId.equals(costPrice.getCurrencyId(), cumulatedAmt.getCurrencyId()))
		{
			throw new AdempiereException("Currency not matching: costPrice=" + costPrice + ", cumulatedAmt=" + cumulatedAmt);
		}

		this.uomId = Quantity.getCommonUomIdOfAll(qty, cumulatedQty);
		this.currencyId = costPrice.getCurrencyId();

		this.costPrice = costPrice;
		this.qty = qty;
		this.cumulatedAmt = cumulatedAmt;
		this.cumulatedQty = cumulatedQty;
	}
}

