package org.compiere.acct;

import de.metas.money.CurrencyId;
import de.metas.money.Money;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Collector;

/**
 * Immutable Balance (debit, credit)
 */
@EqualsAndHashCode(doNotUseGetters = true)
public final class Balance
{
	@Getter @NonNull private final Money debit;
	@Getter @NonNull private final Money credit;

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

	public static Balance zero(@NonNull final CurrencyId currencyId)
	{
		final Money zero = Money.zero(currencyId);
		return new Balance(zero, zero);
	}

	public static Collector<Balance, ?, Optional<Balance>> sum()
	{
		return Collector.of(BalanceBuilder::new, BalanceBuilder::add, BalanceBuilder::combine, BalanceBuilder::build);
	}

	@Override
	public String toString() {return "Balance[" + "DR=" + debit + "-CR=" + credit + " = " + toMoney() + "]";}

	private Balance add(@NonNull Money debitToAdd, @NonNull Money creditToAdd)
	{
		final Money debitNew = this.debit.add(debitToAdd);
		final Money creditNew = this.credit.add(creditToAdd);

		if (Money.equals(this.debit, debitNew)
				&& Money.equals(this.credit, creditNew))
		{
			return this;
		}
		else
		{
			return new Balance(debitNew, creditNew);
		}
	}

	public Balance add(@NonNull final Balance other) {return add(other.debit, other.credit);}

	/**
	 * @return debit - credit
	 */
	public Money toMoney() {return debit.subtract(credit);}

	public BigDecimal toBigDecimal()
	{
		return toMoney().toBigDecimal();
	}

	public int signum() {return toMoney().signum();}

	/**
	 * @return absolute balance, negated if reversal
	 */
	public Money getPostBalance()
	{
		Money balanceAbs = toMoney().abs();
		if (isReversal())
		{
			return balanceAbs.negate();
		}
		return balanceAbs;
	}

	/**
	 * @return true if the balance (debit - credit) is zero
	 */
	public boolean isBalanced() {return signum() == 0;}

	/**
	 * @return true if both DR/CR are negative or zero
	 */
	public boolean isReversal() {return debit.signum() <= 0 && credit.signum() <= 0;}

	public boolean isDebit()
	{
		if (isReversal())
		{
			return toMoney().signum() <= 0;
		}
		else
		{
			return toMoney().signum() >= 0;
		}
	}

	public CurrencyId getCurrencyId() {return Money.getCommonCurrencyIdOfAll(debit, credit);}

	public Balance negateAndInvert()
	{
		return new Balance(this.credit.negate(), this.debit.negate());
	}

	public Balance negateAndInvertIf(final boolean condition)
	{
		return condition ? negateAndInvert() : this;
	}

	public Balance toSingleSide()
	{
		final Money min = debit.min(credit);
		if (min.isZero())
		{
			return this;
		}

		return new Balance(this.debit.subtract(min), this.credit.subtract(min));
	}

	public Balance computeDiffToBalance()
	{
		final Money diff = toMoney();
		if (isReversal())
		{
			return diff.signum() < 0 ? ofCredit(diff) : ofDebit(diff.negate());
		}
		else
		{
			return diff.signum() < 0 ? ofDebit(diff.negate()) : ofCredit(diff);
		}
	}

	public Balance invert()
	{
		return new Balance(this.credit, this.debit);
	}

	//
	//
	//
	//
	//

	@ToString
	private static class BalanceBuilder
	{
		private Money debit;
		private Money credit;

		public void add(@NonNull Balance balance)
		{
			add(balance.getDebit(), balance.getCredit());
		}

		public BalanceBuilder combine(@NonNull BalanceBuilder balanceBuilder)
		{
			add(balanceBuilder.debit, balanceBuilder.credit);
			return this;
		}

		public void add(@Nullable Money debitToAdd, @Nullable Money creditToAdd)
		{
			if (debitToAdd != null)
			{
				this.debit = this.debit != null ? this.debit.add(debitToAdd) : debitToAdd;
			}
			if (creditToAdd != null)
			{
				this.credit = this.credit != null ? this.credit.add(creditToAdd) : creditToAdd;
			}
		}

		public Optional<Balance> build()
		{
			return debit != null || credit != null
					? Optional.of(new Balance(debit, credit))
					: Optional.empty();
		}
	}
}
