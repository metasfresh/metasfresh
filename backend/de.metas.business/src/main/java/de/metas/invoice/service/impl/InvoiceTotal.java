package de.metas.invoice.service.impl;

import de.metas.invoice.InvoiceAmtMultiplier;
import de.metas.money.Money;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

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
@ToString
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

	public boolean isZero() {return relativeValue.signum() == 0;}

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
}
