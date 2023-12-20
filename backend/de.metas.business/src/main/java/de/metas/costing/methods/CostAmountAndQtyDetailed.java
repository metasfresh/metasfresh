package de.metas.costing.methods;

import de.metas.costing.CostAmount;
import de.metas.costing.CostAmountAndQty;
import de.metas.money.CurrencyId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class CostAmountAndQtyDetailed
{
	@NonNull CostAmountAndQty main;
	@NonNull CostAmountAndQty costAdjustment;
	@NonNull CostAmountAndQty alreadyShipped;

	@Builder
	private CostAmountAndQtyDetailed(
			@Nullable final CostAmountAndQty main,
			@Nullable final CostAmountAndQty costAdjustment,
			@Nullable final CostAmountAndQty alreadyShipped)
	{
		final CurrencyId currencyId = CostAmountAndQty.getCommonCurrencyIdOfAll(main, costAdjustment, alreadyShipped);
		final UomId uomId = CostAmountAndQty.getCommonUomIdOfAll(main, costAdjustment, alreadyShipped);
		this.main = main != null ? main : CostAmountAndQty.zero(currencyId, uomId);
		this.costAdjustment = costAdjustment != null ? costAdjustment : CostAmountAndQty.zero(currencyId, uomId);
		this.alreadyShipped = alreadyShipped != null ? alreadyShipped : CostAmountAndQty.zero(currencyId, uomId);
	}

	public static CostAmountAndQtyDetailed of(@NonNull final CostAmount amt, @NonNull final Quantity qty, @NonNull final CostAmountType type)
	{
		final CostAmountAndQty amtAndQty = CostAmountAndQty.of(amt, qty);
		return of(type, amtAndQty);
	}

	private static CostAmountAndQtyDetailed of(final @NonNull CostAmountType type, @NonNull final CostAmountAndQty amtAndQty)
	{
		return switch (type)
		{
			case MAIN -> builder().main(amtAndQty).build();
			case ADJUSTMENT -> builder().costAdjustment(amtAndQty).build();
			case ALREADY_SHIPPED -> builder().alreadyShipped(amtAndQty).build();
		};
	}

	public static CostAmountAndQtyDetailed zero(@NonNull final CurrencyId currencyId, @NonNull final UomId uomId)
	{
		final CostAmountAndQty zero = CostAmountAndQty.zero(currencyId, uomId);
		return new CostAmountAndQtyDetailed(zero, zero, zero);
	}

	public CostAmountAndQtyDetailed add(@NonNull final CostAmountAndQtyDetailed other)
	{
		return builder()
				.main(main.add(other.main))
				.costAdjustment(costAdjustment.add(other.costAdjustment))
				.alreadyShipped(alreadyShipped.add(other.alreadyShipped))
				.build();
	}

	public CostAmountAndQtyDetailed negateIf(final boolean condition)
	{
		return condition ? negate() : this;
	}

	public CostAmountAndQtyDetailed negate()
	{
		return builder()
				.main(main.negate())
				.costAdjustment(costAdjustment.negate())
				.alreadyShipped(alreadyShipped.negate())
				.build();
	}

	public CostAmountAndQty getAmtAndQty(final CostAmountType type)
	{
		return switch (type)
		{
			case MAIN -> main;
			case ADJUSTMENT -> costAdjustment;
			case ALREADY_SHIPPED -> alreadyShipped;
		};
	}

	public CostAmountDetailed getAmt()
	{
		return CostAmountDetailed.builder()
				.mainAmt(main.getAmt())
				.costAdjustmentAmt(costAdjustment.getAmt())
				.alreadyShippedAmt(alreadyShipped.getAmt())
				.build();
	}
}
