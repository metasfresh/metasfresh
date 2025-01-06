package de.metas.costing;

import de.metas.money.CurrencyId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.UnaryOperator;

@Value(staticConstructor = "of")
public class CostAmountAndQty
{
	@NonNull CostAmount amt;
	@NonNull Quantity qty;

	public static CostAmountAndQty zero(@NonNull final CurrencyId currencyId, @NonNull final UomId uomId)
	{
		return of(CostAmount.zero(currencyId), Quantitys.zero(uomId));
	}

	public static CurrencyId getCommonCurrencyIdOfAll(@Nullable final CostAmountAndQty... values)
	{
		return CurrencyId.getCommonCurrencyIdOfAll(CostAmountAndQty::getCurrencyId, "amount", values);
	}

	public static UomId getCommonUomIdOfAll(@Nullable final CostAmountAndQty... values)
	{
		return UomId.getCommonUomIdOfAll(CostAmountAndQty::getUomId, "quantity", values);
	}

	public CurrencyId getCurrencyId() {return amt.getCurrencyId();}

	public UomId getUomId() {return qty.getUomId();}

	public boolean isZero() {return amt.isZero() && qty.isZero();}

	public CostAmountAndQty toZero() {return isZero() ? this : of(amt.toZero(), qty.toZero());}

	public CostAmountAndQty negate() {return isZero() ? this : of(amt.negate(), qty.negate());}

	public CostAmountAndQty mapQty(@NonNull final UnaryOperator<Quantity> qtyMapper)
	{
		if (qty.isZero())
		{
			return this;
		}

		final Quantity newQty = qtyMapper.apply(qty);
		if (Objects.equals(qty, newQty))
		{
			return this;
		}

		return of(amt, newQty);
	}

	public CostAmountAndQty add(@NonNull final CostAmountAndQty other)
	{
		if (other.isZero())
		{
			return this;
		}
		else if (this.isZero())
		{
			return other;
		}
		else
		{
			return of(this.amt.add(other.amt), this.qty.add(other.qty));
		}
	}
}
