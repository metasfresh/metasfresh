/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.costing.methods;

import de.metas.costing.CostAmount;
import de.metas.money.CurrencyId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

import javax.annotation.Nullable;

@Value

@EqualsAndHashCode
@ToString
public class CostAmountDetailed
{
	@NonNull CostAmount mainAmt;  // => // 1388,52 EUR
	@NonNull CostAmount costAdjustmentAmt; // => P_Asset // 768.28 EUR
	@NonNull CostAmount alreadyShippedAmt; // => P_COGS // 264.20 EUR

	@Builder
	private CostAmountDetailed(
			@NonNull final CostAmount mainAmt,
			@Nullable final CostAmount costAdjustmentAmt,
			@Nullable final CostAmount alreadyShippedAmt)
	{
		final CurrencyId currencyId = CostAmount.getCommonCurrencyIdOfAll(mainAmt, costAdjustmentAmt, alreadyShippedAmt);
		this.mainAmt = mainAmt;
		this.costAdjustmentAmt = costAdjustmentAmt != null ? costAdjustmentAmt : CostAmount.zero(currencyId);
		this.alreadyShippedAmt = alreadyShippedAmt != null ? alreadyShippedAmt : CostAmount.zero(currencyId);
	}

	public CostAmountDetailed add(@NonNull final CostAmountDetailed amtToAdd)
	{

		CostAmount.assertCurrencyMatching(mainAmt, costAdjustmentAmt, alreadyShippedAmt,
										  amtToAdd.mainAmt, amtToAdd.costAdjustmentAmt, amtToAdd.alreadyShippedAmt);

		return new CostAmountDetailed(mainAmt.add(amtToAdd.mainAmt),
									  costAdjustmentAmt.add(amtToAdd.costAdjustmentAmt),
									  alreadyShippedAmt.add(amtToAdd.alreadyShippedAmt));
	}

	public CostAmountDetailed negateMainAmount()
	{
		if (mainAmt.signum() == 0)
		{
			return this;
		}
		else
		{
			return new CostAmountDetailed(mainAmt.negate(), costAdjustmentAmt, alreadyShippedAmt);
		}
	}
}



