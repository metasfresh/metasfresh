package de.metas.costing;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;

import de.metas.money.CurrencyId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

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
	private final CostSegment costSegment;
	private final CostElement costElement;

	private final CurrencyId currencyId;
	private final int precision;

	private CostAmount currentCostPrice;
	private CostAmount currentCostPriceLL;
	private Quantity currentQty;

	private CostAmount cumulatedAmt;
	private Quantity cumulatedQty;

	@Builder
	private CurrentCost(
			final int id,
			@NonNull final CostSegment costSegment,
			@NonNull final CostElement costElement,
			@NonNull final CurrencyId currencyId,
			final int precision,
			@NonNull final I_C_UOM uom,
			@NonNull final BigDecimal currentCostPrice,
			@NonNull final BigDecimal currentCostPriceLL,
			@NonNull final BigDecimal currentQty,
			@NonNull final BigDecimal cumulatedAmt,
			@NonNull final BigDecimal cumulatedQty)
	{
		Check.assume(id > 0, "id > 0");
		Check.assume(precision >= 0, "precision >= 0");

		this.id = id;
		this.costSegment = costSegment;
		this.costElement = costElement;

		this.currencyId = currencyId;
		this.precision = precision;

		this.currentCostPrice = CostAmount.of(currentCostPrice, currencyId);
		this.currentCostPriceLL = CostAmount.of(currentCostPriceLL, currencyId);
		this.currentQty = Quantity.of(currentQty, uom);
		this.cumulatedAmt = CostAmount.of(cumulatedAmt, currencyId);
		this.cumulatedQty = Quantity.of(cumulatedQty, uom);
	}

	private CurrentCost(@NonNull final CurrentCost from)
	{
		this.id = from.id;
		this.costSegment = from.costSegment;
		this.costElement = from.costElement;

		this.currencyId = from.currencyId;
		this.precision = from.precision;

		this.currentCostPrice = from.currentCostPrice;
		this.currentCostPriceLL = from.currentCostPriceLL;
		this.currentQty = from.currentQty;
		this.cumulatedAmt = from.cumulatedAmt;
		this.cumulatedQty = from.cumulatedQty;
	}

	public CurrentCost copy()
	{
		return new CurrentCost(this);
	}

	private final void assertCostCurrency(@NonNull final CostAmount amt)
	{
		if (!amt.getCurrencyId().equals(getCurrencyId()))
		{
			throw new AdempiereException("Invalid amount currency for `" + amt + "`. Expected: " + getCurrencyId());
		}
	}

	/**
	 * Add Amt/Qty and calculate weighted average.
	 * ((OldAvg*OldQty)+(Price*Qty)) / (OldQty+Qty).
	 * 
	 * Also calls {@link #addCumulatedAmtAndQty(CostAmount, Quantity)}.
	 *
	 * @param amt total amt (price * qty)
	 * @param qty qty
	 */
	public void addWeightedAverage(@NonNull final CostAmount amt, @NonNull final Quantity qty)
	{
		assertCostCurrency(amt);
		Check.assume(qty.signum() != 0, "qty not zero");

		final CostAmount currentAmt = currentCostPrice.multiply(currentQty);
		final CostAmount newAmt = currentAmt.add(amt);
		final Quantity newQty = currentQty.add(qty);
		if (newQty.signum() != 0)
		{
			currentCostPrice = newAmt.divide(newQty.getAsBigDecimal(), precision, RoundingMode.HALF_UP);
		}
		currentQty = newQty;

		addCumulatedAmtAndQty(amt, qty);
	}

	public void addCumulatedAmtAndQty(@NonNull final CostAmount amt, @NonNull final Quantity qty)
	{
		assertCostCurrency(amt);

		cumulatedAmt = cumulatedAmt.add(amt);
		cumulatedQty = cumulatedQty.add(qty);
	}

	public void adjustCurrentQty(@NonNull final Quantity qtyToAdd)
	{
		currentQty = currentQty.add(qtyToAdd);
	}

	public CostAmount getCurrentCostPriceTotal()
	{
		return currentCostPrice.add(currentCostPriceLL);
	}

	public void setCurrentCostPrice(@NonNull final CostAmount costPrice)
	{
		assertCostCurrency(costPrice);
		currentCostPrice = costPrice;
	}

	public void setCurrentCostPriceLL(@NonNull final CostAmount costPrice)
	{
		assertCostCurrency(costPrice);
		currentCostPriceLL = costPrice;
	}
}
