package de.metas.currency;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;

import java.util.Collection;
import java.util.LinkedHashMap;

public class MutableMultiCurrencyAmount
{
	private final LinkedHashMap<CurrencyCode, Amount> amountsByCurrencyCode = new LinkedHashMap<>();

	private MutableMultiCurrencyAmount()
	{
	}

	public static MutableMultiCurrencyAmount empty() {return new MutableMultiCurrencyAmount();}

	public static MutableMultiCurrencyAmount zero(@NonNull final CurrencyCode currencyCode)
	{
		final MutableMultiCurrencyAmount result = new MutableMultiCurrencyAmount();
		result.add(Amount.zero(currencyCode));
		return result;
	}

	public static MutableMultiCurrencyAmount of(@NonNull final Collection<Amount> amounts)
	{
		final MutableMultiCurrencyAmount result = new MutableMultiCurrencyAmount();
		result.addAll(amounts);
		return result;
	}

	public MutableMultiCurrencyAmount add(@NonNull final Amount amountToAdd)
	{
		amountsByCurrencyCode.compute(
				amountToAdd.getCurrencyCode(),
				(currencyCode, existingAmount) -> existingAmount == null ? amountToAdd : existingAmount.add(amountToAdd)
		);
		return this;
	}

	public MutableMultiCurrencyAmount addAll(@NonNull final Collection<Amount> amountsToAdd)
	{
		amountsToAdd.forEach(this::add);
		return this;
	}

	public MutableMultiCurrencyAmount subtract(@NonNull final Amount amountToSubtract)
	{
		add(amountToSubtract.negate());
		return this;
	}

	public MutableMultiCurrencyAmount subtractAll(@NonNull final Collection<Amount> amountsToSubtract)
	{
		amountsToSubtract.forEach(this::subtract);
		return this;
	}

	public ImmutableSet<Amount> toSet()
	{
		return ImmutableSet.copyOf(amountsByCurrencyCode.values());
	}

	public ImmutableMap<CurrencyCode, Amount> toMap()
	{
		return ImmutableMap.copyOf(amountsByCurrencyCode);
	}

	public void removeIfZero(@NonNull final CurrencyCode currencyCode)
	{
		amountsByCurrencyCode.compute(
				currencyCode,
				(k, existingAmount) -> existingAmount == null || existingAmount.isZero() ? null : existingAmount);
	}
}
