package de.metas.costing;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

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
public final class CurrentCost
{
	private final int id;

	private final int currencyId;
	private final int precision;

	@Setter
	private CostAmount currentCostPrice;
	private final CostAmount currentCostPriceLL;
	@Setter
	private BigDecimal currentQty;

	private CostAmount cumulatedAmt;
	private BigDecimal cumulatedQty;

	@Builder
	private CurrentCost(
			final int id,
			final int currencyId,
			final int precision,
			@NonNull final BigDecimal currentCostPrice,
			@NonNull final BigDecimal currentCostPriceLL,
			@NonNull final BigDecimal currentQty,
			@NonNull final BigDecimal cumulatedAmt,
			@NonNull final BigDecimal cumulatedQty)
	{
		Check.assume(id > 0, "id > 0");
		Check.assume(currencyId > 0, "currencyId > 0");
		Check.assume(precision >= 0, "precision >= 0");

		this.id = id;
		this.currencyId = currencyId;
		this.precision = precision;
		this.currentCostPrice = CostAmount.of(currentCostPrice, currencyId);
		this.currentCostPriceLL = CostAmount.of(currentCostPriceLL, currencyId);
		this.currentQty = currentQty;
		this.cumulatedAmt = CostAmount.of(cumulatedAmt, currencyId);
		this.cumulatedQty = cumulatedQty;
	}

	@Deprecated
	public void add(@NonNull final BigDecimal amt, @NonNull final BigDecimal qty)
	{
		add(CostAmount.of(amt, getCurrencyId()), qty);
	}

	/**
	 * Add Cumulative Amt/Qty and Current Qty
	 *
	 * @param amt amt
	 * @param qty qty
	 */
	public void add(@NonNull final CostAmount amt, @NonNull final BigDecimal qty)
	{
		assertCostCurrency(amt);
		adjustCurrentQty(qty);
		addCumulatedAmtAndQty(amt, qty);
	}

	private void assertCostCurrency(@NonNull final CostAmount amt)
	{
		if (amt.getCurrencyId() != getCurrencyId())
		{
			throw new AdempiereException("Invalid amount currency for `" + amt + "`. Expected: " + getCurrencyId());
		}
	}

	@Deprecated
	public void addWeightedAverage(@NonNull final BigDecimal amt, @NonNull final BigDecimal qty)
	{
		addWeightedAverage(CostAmount.of(amt, getCurrencyId()), qty);
	}

	/**
	 * Add Amt/Qty and calculate weighted average.
	 * ((OldAvg*OldQty)+(Price*Qty)) / (OldQty+Qty)
	 *
	 * @param amt total amt (price * qty)
	 * @param qty qty
	 */
	public void addWeightedAverage(@NonNull final CostAmount amt, @NonNull final BigDecimal qty)
	{
		assertCostCurrency(amt);

		final CostAmount currentAmt = currentCostPrice.multiply(currentQty);
		final CostAmount newAmt = currentAmt.add(amt);
		final BigDecimal newQty = currentQty.add(qty);
		if (newQty.signum() != 0)
		{
			currentCostPrice = newAmt.divide(newQty, precision, RoundingMode.HALF_UP);
		}
		currentQty = newQty;

		addCumulatedAmtAndQty(amt, qty);
	}

	@Deprecated
	public void addCumulatedAmtAndQty(@NonNull final BigDecimal amt, @NonNull final BigDecimal qty)
	{
		addCumulatedAmtAndQty(CostAmount.of(amt, getCurrencyId()), qty);
	}

	public void addCumulatedAmtAndQty(@NonNull final CostAmount amt, @NonNull final BigDecimal qty)
	{
		assertCostCurrency(amt);

		cumulatedAmt = cumulatedAmt.add(amt);
		cumulatedQty = cumulatedQty.add(qty);
	}

	public void adjustCurrentQty(@NonNull final BigDecimal qtyToAdd)
	{
		currentQty = currentQty.add(qtyToAdd);
	}
}
