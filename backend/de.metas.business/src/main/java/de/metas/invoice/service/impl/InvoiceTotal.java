package de.metas.invoice.service.impl;

import de.metas.invoice.InvoiceAmtMultiplier;
import de.metas.money.Money;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * Invoice Amount together with
 * <ul>
 *     <li>Sales/Purchase(AP) and Credit Memo(CM) flags</li>
 *     <li>is the amount Sales/Purchase(AP) adjusted flag</li>
 *     <li>is the amount Credit Memo(CM) adjusted</li>
 * </ul>
 * <p>
 */
@EqualsAndHashCode
public class InvoiceTotal
{
	@NonNull Money relativeValue;
	@NonNull InvoiceAmtMultiplier multiplier;

	private InvoiceTotal(@NonNull final Money relativeValue, @NonNull final InvoiceAmtMultiplier multiplier)
	{
		this.relativeValue = relativeValue;
		this.multiplier = multiplier;
	}

	public static InvoiceTotal ofRelativeValue(@NonNull final Money relativeValue, @NonNull final InvoiceAmtMultiplier multiplier)
	{
		return new InvoiceTotal(relativeValue, multiplier);
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(relativeValue);
		final String multiplierStr = multiplier.toString();
		if (!multiplierStr.isEmpty())
		{
			sb.append(", ").append(multiplierStr);
		}
		return sb.toString();
	}

	public boolean isZero() {return relativeValue.signum() == 0;}

	public InvoiceTotal subtract(@NonNull final InvoiceTotal other)
	{
		if (other.isZero()) {return this;}

		final Money otherValue = other.withAPAdjusted(isAPAdjusted()).withCMAdjusted(isCMAdjusted()).toMoney();
		return ofRelativeValue(this.relativeValue.subtract(otherValue), multiplier);
	}

	public InvoiceTotal subtractRealValue(@Nullable final Money realValueToSubtract)
	{
		if (realValueToSubtract == null || realValueToSubtract.signum() == 0)
		{
			return this;
		}

		final Money resultRealValue = toRealValueAsMoney().subtract(realValueToSubtract);
		final Money resultRelativeValue = multiplier.convertToRelativeValue(resultRealValue);
		return InvoiceTotal.ofRelativeValue(resultRelativeValue, multiplier);
	}

	public Money toMoney() {return relativeValue;}

	public @NonNull BigDecimal toBigDecimal() {return toMoney().toBigDecimal();}

	public Money toRealValueAsMoney() {return multiplier.convertToRealValue(relativeValue);}

	public BigDecimal toRealValueAsBigDecimal() {return toRealValueAsMoney().toBigDecimal();}

	public boolean isAP() {return multiplier.isAP();}

	public boolean isAPAdjusted() {return multiplier.isAPAdjusted();}

	public boolean isCreditMemo() {return multiplier.isCreditMemo();}

	public boolean isCMAdjusted() {return multiplier.isCreditMemoAdjusted();}

	public InvoiceTotal withAPAdjusted(final boolean isAPAdjusted)
	{
		return isAPAdjusted ? withAPAdjusted() : withoutAPAdjusted();
	}

	public InvoiceTotal withAPAdjusted()
	{
		if (multiplier.isAPAdjusted())
		{
			return this;
		}
		else if (multiplier.isAP())
		{
			return new InvoiceTotal(relativeValue.negate(), multiplier.withAPAdjusted(true));
		}
		else
		{
			return new InvoiceTotal(relativeValue, multiplier.withAPAdjusted(true));
		}
	}

	public InvoiceTotal withoutAPAdjusted()
	{
		if (!multiplier.isAPAdjusted())
		{
			return this;
		}
		else if (multiplier.isAP())
		{
			return new InvoiceTotal(relativeValue.negate(), multiplier.withAPAdjusted(false));
		}
		else
		{
			return new InvoiceTotal(relativeValue, multiplier.withAPAdjusted(false));
		}
	}

	public InvoiceTotal withCMAdjusted(final boolean isCMAdjusted)
	{
		return isCMAdjusted ? withCMAdjusted() : withoutCMAdjusted();
	}

	public InvoiceTotal withCMAdjusted()
	{
		if (multiplier.isCreditMemoAdjusted())
		{
			return this;
		}
		else if (multiplier.isCreditMemo())
		{
			return new InvoiceTotal(relativeValue.negate(), multiplier.withCMAdjusted(true));
		}
		else
		{
			return new InvoiceTotal(relativeValue, multiplier.withCMAdjusted(true));
		}
	}

	public InvoiceTotal withoutCMAdjusted()
	{
		if (!multiplier.isCreditMemoAdjusted())
		{
			return this;
		}
		else if (multiplier.isCreditMemo())
		{
			return new InvoiceTotal(relativeValue.negate(), multiplier.withCMAdjusted(false));
		}
		else
		{
			return new InvoiceTotal(relativeValue, multiplier.withCMAdjusted(false));
		}
	}
}
