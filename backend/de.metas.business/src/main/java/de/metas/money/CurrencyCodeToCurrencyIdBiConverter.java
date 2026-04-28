package de.metas.money;

import de.metas.currency.CurrencyCode;
import lombok.NonNull;

public interface CurrencyCodeToCurrencyIdBiConverter extends CurrencyCodeToCurrencyIdConverter, CurrencyIdToCurrencyCodeConverter
{
	@Override
	CurrencyId getCurrencyIdByCurrencyCode(@NonNull final CurrencyCode currencyCode);

	@Override
	CurrencyCode getCurrencyCodeByCurrencyId(@NonNull final CurrencyId currencyId);
}
