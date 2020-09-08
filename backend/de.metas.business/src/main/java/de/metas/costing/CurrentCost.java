package de.metas.costing;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;

import de.metas.currency.CurrencyPrecision;
import de.metas.money.CurrencyId;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityUOMConverter;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

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
public final class CurrentCost
{
	@Setter
	private CurrentCostId id;

	private final CostSegment costSegment;
	private final CostElement costElement;

	private final CurrencyId currencyId;
	private final CurrencyPrecision precision;

	private final UomId uomId;

	private CostPrice costPrice;
	private Quantity currentQty;

	private CostAmount cumulatedAmt;
	private Quantity cumulatedQty;

	@Builder
	private CurrentCost(
			final CurrentCostId id,
			@NonNull final CostSegment costSegment,
			@NonNull final CostElement costElement,
			@NonNull final CurrencyId currencyId,
			@NonNull final CurrencyPrecision precision,
			@NonNull final I_C_UOM uom,
			final BigDecimal ownCostPrice,
			final BigDecimal componentsCostPrice,
			final BigDecimal currentQty,
			final BigDecimal cumulatedAmt,
			final BigDecimal cumulatedQty)
	{
		this.id = id;

		this.costSegment = costSegment;
		this.costElement = costElement;

		this.currencyId = currencyId;
		this.precision = precision;

		this.uomId = UomId.ofRepoId(uom.getC_UOM_ID());

		this.costPrice = CostPrice.builder()
				.ownCostPrice(ownCostPrice != null ? CostAmount.of(ownCostPrice, currencyId) : CostAmount.zero(currencyId))
				.componentsCostPrice(componentsCostPrice != null ? CostAmount.of(componentsCostPrice, currencyId) : CostAmount.zero(currencyId))
				.build();
		this.currentQty = currentQty != null ? Quantity.of(currentQty, uom) : Quantity.zero(uom);
		this.cumulatedAmt = cumulatedAmt != null ? CostAmount.of(cumulatedAmt, currencyId) : CostAmount.zero(currencyId);
		this.cumulatedQty = cumulatedQty != null ? Quantity.of(cumulatedQty, uom) : Quantity.zero(uom);
	}

	private CurrentCost(@NonNull final CurrentCost from)
	{
		this.id = from.id;

		this.costElement = from.costElement;
		this.costSegment = from.costSegment;

		this.currencyId = from.currencyId;
		this.precision = from.precision;

		this.uomId = from.uomId;

		this.costPrice = from.costPrice;
		this.currentQty = from.currentQty;

		this.cumulatedAmt = from.cumulatedAmt;
		this.cumulatedQty = from.cumulatedQty;
	}

	public CurrentCost copy()
	{
		return new CurrentCost(this);
	}

	public void setFrom(final CostDetailPreviousAmounts previousAmounts)
	{
		this.costPrice = previousAmounts.getCostPrice();
		this.currentQty = previousAmounts.getCumulatedQty();

		this.cumulatedAmt = previousAmounts.getCumulatedAmt();
		this.cumulatedQty = previousAmounts.getCumulatedQty();
	}

	public CostElementId getCostElementId()
	{
		return getCostElement().getId();
	}

	private void assertCostCurrency(@NonNull final CostAmount amt)
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
	public void addWeightedAverage(
			@NonNull final CostAmount amt,
			@NonNull final Quantity qty,
			@NonNull final QuantityUOMConverter uomConverter)
	{
		assertCostCurrency(amt);
		
		if(qty.signum() == 0 && amt.signum() != 0)
		{
			throw new AdempiereException("Qty shall not be zero when amount is non zero: "+amt);
		}

		final CostAmount currentAmt = costPrice.getOwnCostPrice().multiply(currentQty);
		final CostAmount newAmt = currentAmt.add(amt);

		final Quantity qtyConv = uomConverter.convertQuantityTo(qty, costSegment.getProductId(), uomId);
		final Quantity newQty = currentQty.add(qtyConv);
		if (newQty.signum() != 0)
		{
			final CostAmount ownCostPrice = newAmt.divide(newQty, getPrecision());
			this.costPrice = costPrice.withOwnCostPrice(ownCostPrice);
		}
		currentQty = newQty;

		addCumulatedAmtAndQty(amt, qtyConv);
	}

	private void addCumulatedAmtAndQty(@NonNull final CostAmount amt, @NonNull final Quantity qty)
	{
		assertCostCurrency(amt);

		cumulatedAmt = cumulatedAmt.add(amt);
		cumulatedQty = cumulatedQty.add(qty);
	}

	public void addToCurrentQtyAndCumulate(
			@NonNull final Quantity qtyToAdd,
			@NonNull final CostAmount amt,
			@NonNull final QuantityUOMConverter uomConverter)
	{
		final Quantity qtyToAddConv = uomConverter.convertQuantityTo(qtyToAdd, costSegment.getProductId(), uomId);

		currentQty = currentQty.add(qtyToAddConv).toZeroIfNegative();

		addCumulatedAmtAndQty(amt, qtyToAddConv);
	}

	public void setCostPrice(@NonNull final CostPrice costPrice)
	{
		this.costPrice = costPrice;
	}

	public void setOwnCostPrice(@NonNull final CostAmount ownCostPrice)
	{
		setCostPrice(getCostPrice().withOwnCostPrice(ownCostPrice));
	}

	public void addToOwnCostPrice(@NonNull final CostAmount ownCostPriceToAdd)
	{
		setCostPrice(getCostPrice().addToOwnCostPrice(ownCostPriceToAdd));
	}

	public void clearComponentsCostPrice()
	{
		setCostPrice(getCostPrice().withZeroComponentsCostPrice());
	}
}
