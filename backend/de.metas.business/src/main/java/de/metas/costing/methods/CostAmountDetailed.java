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
	// amount after correction
	// e.g. invoiced amount
	@NonNull CostAmount mainAmt;

	// i.e.
	// positive - invoiced price is greater than receipt price
	// negative - invoiced price is less than receipt price
	@NonNull CostAmount costAdjustmentAmt;

	@NonNull CostAmount alreadyShippedAmt;

	@Builder
	private CostAmountDetailed(
			@Nullable final CostAmount mainAmt,
			@Nullable final CostAmount costAdjustmentAmt,
			@Nullable final CostAmount alreadyShippedAmt)
	{
		final CurrencyId currencyId = CostAmount.getCommonCurrencyIdOfAll(mainAmt, costAdjustmentAmt, alreadyShippedAmt);
		this.mainAmt = mainAmt != null ? mainAmt : CostAmount.zero(currencyId);
		this.costAdjustmentAmt = costAdjustmentAmt != null ? costAdjustmentAmt : CostAmount.zero(currencyId);
		this.alreadyShippedAmt = alreadyShippedAmt != null ? alreadyShippedAmt : CostAmount.zero(currencyId);
	}

	public static CostAmountDetailed zero(@NonNull final CurrencyId currencyId)
	{
		final CostAmount zero = CostAmount.zero(currencyId);
		return new CostAmountDetailed(zero, zero, zero);
	}

	public static CostAmountDetailed ofAmtAndType(@NonNull final CostAmount amt, @NonNull final CostAmountType type)
	{
		return switch (type)
		{
			case MAIN -> builder().mainAmt(amt).build();
			case ADJUSTMENT -> builder().costAdjustmentAmt(amt).build();
			case ALREADY_SHIPPED -> builder().alreadyShippedAmt(amt).build();
		};
	}

	public CostAmountDetailed add(@NonNull final CostAmountDetailed amtToAdd)
	{
		return builder()
				.mainAmt(mainAmt.add(amtToAdd.mainAmt))
				.costAdjustmentAmt(costAdjustmentAmt.add(amtToAdd.costAdjustmentAmt))
				.alreadyShippedAmt(alreadyShippedAmt.add(amtToAdd.alreadyShippedAmt))
				.build();
	}

	public CostAmountDetailed negateIf(final boolean condition)
	{
		return condition ? negate() : this;
	}

	private CostAmountDetailed negate()
	{
		return builder()
				.mainAmt(mainAmt.negate())
				.costAdjustmentAmt(costAdjustmentAmt.negate())
				.alreadyShippedAmt(alreadyShippedAmt.negate())
				.build();
	}

	public CostAmount getAmountBeforeAdjustment() {return mainAmt.subtract(costAdjustmentAmt).subtract(alreadyShippedAmt);}
}



