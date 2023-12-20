package de.metas.acct.acct_simulation;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.MutableMultiCurrencyAmount;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Collection;
import java.util.Comparator;

@Value
class AmountMultiBalance
{
	@NonNull ImmutableSet<Amount> debit;
	@NonNull ImmutableSet<Amount> credit;
	@NonNull ImmutableMap<CurrencyCode, Amount> balance;

	@Builder
	private AmountMultiBalance(
			@NonNull final Collection<Amount> debit,
			@NonNull final Collection<Amount> credit)
	{
		this.debit = ImmutableSet.copyOf(debit);
		this.credit = ImmutableSet.copyOf(credit);

		this.balance = MutableMultiCurrencyAmount.of(this.debit)
				.subtractAll(this.credit)
				.toMap();
	}

	public ITranslatableString getDebitAsTrlString() {return toTrlString(debit);}

	public ITranslatableString getCreditAsTrlString() {return toTrlString(credit);}

	public ITranslatableString getBalanceAsTrlString() {return toTrlString(balance.values());}

	private static ITranslatableString toTrlString(final Collection<Amount> amounts)
	{
		if (amounts.isEmpty())
		{
			return TranslatableStrings.empty();
		}
		else if (amounts.size() == 1)
		{
			return TranslatableStrings.amount(amounts.iterator().next());
		}
		else
		{
			final TranslatableStringBuilder builder = TranslatableStrings.builder();
			amounts.stream()
					.sorted(Comparator.comparing(Amount::getCurrencyCode))
					.forEach(amount -> {
						if (!builder.isEmpty())
						{
							builder.append(", ");
						}
						builder.append(TranslatableStrings.amount(amount));
					});
			return builder.build();
		}
	}

	public boolean isBalanced()
	{
		if (balance.isEmpty())
		{
			return true;
		}

		return balance.values().stream().allMatch(Amount::isZero);
	}

	@NonNull
	public Amount getBalance(@NonNull final CurrencyCode currencyCode)
	{
		final Amount amount = balance.get(currencyCode);
		return amount != null ? amount : Amount.zero(currencyCode);
	}

	public AmountMultiBalance removing(@NonNull final CurrencyCode currencyCode)
	{
		final ImmutableSet<Amount> newDebit = this.debit.stream()
				.filter(amount -> !CurrencyCode.equals(amount.getCurrencyCode(), currencyCode))
				.collect(ImmutableSet.toImmutableSet());
		final ImmutableSet<Amount> newCredit = this.credit.stream()
				.filter(amount -> !CurrencyCode.equals(amount.getCurrencyCode(), currencyCode))
				.collect(ImmutableSet.toImmutableSet());

		if (this.debit.size() == newDebit.size() && this.credit.size() == newCredit.size())
		{
			return this;
		}

		return new AmountMultiBalance(newDebit, newCredit);
	}

}
