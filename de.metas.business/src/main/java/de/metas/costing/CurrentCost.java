package de.metas.costing;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

	private final int precision;

	@NonNull
	@Setter
	private BigDecimal currentCostPrice;
	@NonNull
	private final BigDecimal currentCostPriceLL;
	@NonNull
	@Setter
	private BigDecimal currentQty;

	@NonNull
	private BigDecimal cumulatedAmt;
	@NonNull
	private BigDecimal cumulatedQty;

	@Builder
	private CurrentCost(
			final int id,
			final int precision,
			@NonNull final BigDecimal currentCostPrice,
			@NonNull final BigDecimal currentCostPriceLL,
			@NonNull final BigDecimal currentQty,
			@NonNull final BigDecimal cumulatedAmt,
			@NonNull final BigDecimal cumulatedQty)
	{
		Check.assume(id > 0, "id > 0");
		Check.assume(precision > 0, "precision > 0");

		this.id = id;
		this.precision = precision;
		this.currentCostPrice = currentCostPrice;
		this.currentCostPriceLL = currentCostPriceLL;
		this.currentQty = currentQty;
		this.cumulatedAmt = cumulatedAmt;
		this.cumulatedQty = cumulatedQty;
	}

	/**
	 * Add Cumulative Amt/Qty and Current Qty
	 *
	 * @param amt amt
	 * @param qty qty
	 */
	public void add(@NonNull final BigDecimal amt, @NonNull final BigDecimal qty)
	{
		adjustCurrentQty(qty);
		addCumulatedAmtAndQty(amt, qty);
	}

	/**
	 * Add Amt/Qty and calculate weighted average.
	 * ((OldAvg*OldQty)+(Price*Qty)) / (OldQty+Qty)
	 *
	 * @param amt total amt (price * qty)
	 * @param qty qty
	 */
	public void addWeightedAverage(@NonNull final BigDecimal amt, @NonNull final BigDecimal qty)
	{
		final BigDecimal currentAmt = currentCostPrice.multiply(currentQty);
		final BigDecimal newAmt = currentAmt.add(amt);
		final BigDecimal newQty = currentQty.add(qty);
		if (newQty.signum() != 0)
		{
			currentCostPrice = newAmt.divide(newQty, precision, RoundingMode.HALF_UP);
		}
		currentQty = newQty;

		addCumulatedAmtAndQty(amt, qty);
	}

	public void addCumulatedAmtAndQty(@NonNull final BigDecimal amt, @NonNull final BigDecimal qty)
	{
		cumulatedAmt = cumulatedAmt.add(amt);
		cumulatedQty = cumulatedQty.add(qty);
	}

	public void adjustCurrentQty(@NonNull final BigDecimal qtyToAdd)
	{
		currentQty = currentQty.add(qtyToAdd);
	}
}
