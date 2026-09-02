package de.metas.money;

import de.metas.currency.CurrencyCode;
import lombok.NonNull;

@FunctionalInterface
public interface CurrencyIdToCurrencyCodeConverter
{
	CurrencyCode getCurrencyCodeByCurrencyId(@NonNull final CurrencyId currencyId);
}
