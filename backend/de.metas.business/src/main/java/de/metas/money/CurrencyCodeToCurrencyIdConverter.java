package de.metas.money;

import de.metas.currency.CurrencyCode;
import lombok.NonNull;

@FunctionalInterface
public interface CurrencyCodeToCurrencyIdConverter
{
	CurrencyId getCurrencyIdByCurrencyCode(@NonNull final CurrencyCode currencyCode);
}
