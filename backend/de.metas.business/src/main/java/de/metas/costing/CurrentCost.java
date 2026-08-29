package de.metas.costing;

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
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;

import java.math.BigDecimal;

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
				.uomId(uomId)
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
		assertCostCurrency(previousAmounts.getCostPrice());
		assertCostCurrency(previousAmounts.getCumulatedAmt());

		// resolve both quantities BEFORE mutating anything, so a rejected one leaves this cost untouched
		final Quantity qty = toCostUOM(previousAmounts.getQty());
		final Quantity cumulatedQty = toCostUOM(previousAmounts.getCumulatedQty());

		// The price's UOM tracks its own amounts — every producer of CostDetailPreviousAmounts builds price and
		// quantity from the same product UOM — so it is re-labelled exactly when the quantities were, never on its
		// own. A price tagged independently of its amounts is not something this method may silently rewrite.
		this.costPrice = UomId.equals(previousAmounts.getCostPrice().getUomId(), previousAmounts.getQty().getUomId())
				? relabelToCostUOM(previousAmounts.getCostPrice())
				: previousAmounts.getCostPrice();
		this.currentQty = qty;

		this.cumulatedAmt = previousAmounts.getCumulatedAmt();
		this.cumulatedQty = cumulatedQty;
	}

	/**
	 * Returns {@code qty} expressed in this cost's own UOM.
	 * <p>
	 * A <b>zero</b> quantity carries no unit information — zero metres is zero pieces — so it is adopted into
	 * this cost's UOM instead of being rejected, and needs no conversion rate. That matters because
	 * {@code CostRevaluationService#toCostAsOf} restates a live cost from the amounts it carried at an as-of
	 * date, and those historical amounts can predate a change of the product's stock UOM. Since
	 * {@code createLines} maps over every product in one pass, rejecting such a zero aborts the entire
	 * cost-revaluation seed for the client.
	 * <p>
	 * A <b>non-zero</b> mismatch is a real inconsistency — converting it would need a rate this class has no
	 * converter for — so it still throws.
	 */
	private Quantity toCostUOM(@NonNull final Quantity qty)
	{
		if (UomId.equals(qty.getUomId(), getUomId()))
		{
			return qty;
		}
		else if (qty.isZero())
		{
			return this.currentQty.toZero();
		}
		else
		{
			throw new AdempiereException("Invalid UOM for `" + qty + "`. Expected: " + getUomId());
		}
	}

	/**
	 * Returns {@code costPrice} labelled with this cost's own UOM. Unlike {@link #toCostUOM(Quantity)} this
	 * never rejects a mismatch — it is a pure re-label, which is why it is deliberately NOT an overload of the
	 * same name; the caller decides when re-labelling is legitimate.
	 * <p>
	 * {@code M_Cost} carries no separate UOM for its price: a current cost's price is <b>by definition</b>
	 * expressed in the cost row's UOM, which is why the constructor stamps {@link #uomId} onto the price on
	 * every load. A {@link CostDetailPreviousAmounts} however still carries the UOM its amounts were recorded
	 * under, so the price is re-labelled here to preserve that invariant.
	 * <p>
	 * Without this, {@code CostRevaluationRepository.updateRecordFromCopySource} would stamp the stale UOM onto
	 * {@code M_CostRevaluationLine.C_UOM_ID} while writing {@code CurrentQty} in this cost's UOM — a line
	 * labelled with one unit and quantified in another.
	 */
	private CostPrice relabelToCostUOM(@NonNull final CostPrice costPrice)
	{
		return UomId.equals(costPrice.getUomId(), getUomId())
				? costPrice
				: costPrice.convertAmounts(getUomId(), amount -> amount);
	}

	public CostElementId getCostElementId()
	{
		return getCostElement().getId();
	}

	private void assertCostCurrency(@NonNull final CostPrice costPrice)
	{
		if (!costPrice.getCurrencyId().equals(getCurrencyId()))
		{
			throw new AdempiereException("Invalid amount currency for `" + costPrice + "`. Expected: " + getCurrencyId());
		}
	}

	private void assertCostCurrency(@NonNull final CostAmount amt)
	{
		if (!amt.getCurrencyId().equals(getCurrencyId()))
		{
			throw new AdempiereException("Invalid amount currency for `" + amt + "`. Expected: " + getCurrencyId());
		}
	}

	private void assertCostUOM(final Quantity qty)
	{
		if (!UomId.equals(qty.getUomId(), getUomId()))
		{
			throw new AdempiereException("Invalid UOM for `" + qty + "`. Expected: " + getUomId());
		}
	}

	public void addWeightedAverage(
			@NonNull final CostAmountAndQty amtAndQty,
			@NonNull final QuantityUOMConverter uomConverter)
	{
		addWeightedAverage(amtAndQty.getAmt(), amtAndQty.getQty(), uomConverter);
	}

	/**
	 * Add Amt/Qty and calculate weighted average.
	 * ((OldAvg*OldQty)+(Price*Qty)) / (OldQty+Qty).
	 * <p>
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

	private void addCumulatedAmtAndQty(
			@NonNull final CostAmount amt,
			@NonNull final Quantity qty)
	{
		assertCostUOM(qty);

		addCumulatedAmt(amt);
		cumulatedQty = cumulatedQty.add(qty);
	}

	public void addCumulatedAmt(@NonNull final CostAmount amt)
	{
		assertCostCurrency(amt);
		cumulatedAmt = cumulatedAmt.add(amt);
	}

	public void addToCurrentQtyAndCumulate(
			@NonNull final Quantity qtyToAdd,
			@NonNull final CostAmount amt,
			@NonNull final QuantityUOMConverter uomConverter)
	{
		final Quantity qtyToAddConv = uomConverter.convertQuantityTo(qtyToAdd, costSegment.getProductId(), uomId);
		addToCurrentQtyAndCumulate(qtyToAddConv, amt);
	}

	public void addToCurrentQtyAndCumulate(
			@NonNull final Quantity qtyToAdd,
			@NonNull final CostAmount amt)
	{
		currentQty = currentQty.add(qtyToAdd);

		addCumulatedAmtAndQty(amt, qtyToAdd);
	}

	public void addToCurrentQtyAndCumulate(@NonNull final CostAmountAndQty amtAndQty)
	{
		addToCurrentQtyAndCumulate(amtAndQty.getQty(), amtAndQty.getAmt());
	}

	public void setCostPrice(@NonNull final CostPrice costPrice)
	{
		this.costPrice = costPrice;
	}

	public void setOwnCostPrice(@NonNull final CostAmount ownCostPrice)
	{
		setCostPrice(getCostPrice().withOwnCostPrice(ownCostPrice));
	}

	public void clearOwnCostPrice()
	{
		setCostPrice(getCostPrice().withZeroOwnCostPrice());
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
