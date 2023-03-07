package org.compiere.acct;

import de.metas.money.CurrencyId;
import de.metas.money.Money;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * Mutable Balance (debit, credit)
 */
@EqualsAndHashCode(doNotUseGetters = true)
public final class Balance
{
	@Getter @NonNull private Money debit;
	@Getter @NonNull private Money credit;

	private Balance(@Nullable final Money debit, @Nullable final Money credit)
	{
		final CurrencyId currencyId = Money.getCommonCurrencyIdOfAll(debit, credit);
		this.debit = debit != null ? debit : Money.zero(currencyId);
		this.credit = credit != null ? credit : Money.zero(currencyId);
	}

	public static Balance of(@NonNull CurrencyId currencyId, @NonNull final BigDecimal dr, @NonNull final BigDecimal cr)
	{
		return new Balance(Money.of(dr, currencyId), Money.of(cr, currencyId));
	}

	public static Balance ofDebit(@NonNull final Money debit)
	{
		return new Balance(debit, null);
	}

	public static Balance ofCredit(@NonNull final Money credit)
	{
		return new Balance(null, credit);
	}

	@Override
	public String toString() {return "Balance[" + "DR=" + debit + "-CR=" + credit + " = " + getBalance() + "]";}

	public void add(@NonNull Money debit, @NonNull Money credit)
	{
		this.debit = this.debit.add(debit);
		this.credit = this.credit.add(credit);
	}

	public void add(@NonNull final Balance other) {add(other.debit, other.credit);}

	public Money getBalance() {return debit.subtract(credit);}

	/**
	 * @return absolute balance, negated if reversal
	 */
	public Money getPostBalance()
	{
		Money balanceAbs = getBalance().abs();
		if (isReversal())
		{
			return balanceAbs.negate();
		}
		return balanceAbs;
	}

	public boolean isZeroBalance() {return getBalance().signum() == 0;}

	/**
	 * @return true if both DR/CR are negative or zero
	 */
	public boolean isReversal() {return debit.signum() <= 0 && credit.signum() <= 0;}

	public CurrencyId getCurrencyId() {return Money.getCommonCurrencyIdOfAll(debit, credit);}

	public Balance negateAndInvert()
	{
		return new Balance(this.credit, this.debit);
	}

	public Balance negateAndInvertIf(final boolean condition)
	{
		return condition ? negateAndInvert() : this;
	}

}
