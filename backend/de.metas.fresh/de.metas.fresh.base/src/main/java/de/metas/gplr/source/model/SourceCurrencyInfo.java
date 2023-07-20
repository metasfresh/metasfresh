package de.metas.gplr.source.model;

import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRate;
import de.metas.money.CurrencyCodeToCurrencyIdBiConverter;
import de.metas.money.CurrencyId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class SourceCurrencyInfo
{
	@NonNull CurrencyRate currencyRate;
	@NonNull CurrencyCode foreignCurrencyCode;
	@NonNull CurrencyCode localCurrencyCode;
	@Nullable String fecDocumentNo;

	public Amount convertToLocal(@NonNull final Amount amount, @NonNull CurrencyCodeToCurrencyIdBiConverter currencyCodeConverter)
	{
		final CurrencyId localCurrencyId = currencyCodeConverter.getCurrencyIdByCurrencyCode(localCurrencyCode);
		return currencyRate.convertAmount(
						amount.toMoney(currencyCodeConverter::getCurrencyIdByCurrencyCode),
						localCurrencyId)
				.toAmount(currencyCodeConverter::getCurrencyCodeByCurrencyId);
	}

}
