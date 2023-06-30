package de.metas.money;

import de.metas.currency.CurrencyCode;
import lombok.NonNull;

public interface CurrencyCodeToCurrencyIdBiConverter
{
	CurrencyId getCurrencyIdByCurrencyCode(@NonNull final CurrencyCode currencyCode);

	CurrencyCode getCurrencyCodeByCurrencyId(@NonNull final CurrencyId currencyId);
}
