package de.metas.acct.acct_simulation;

import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRate;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import lombok.Builder;
import lombok.NonNull;

import java.math.BigDecimal;

class AcctRowCurrencyRate
{
	@NonNull private final BigDecimal conversionRate;
	@NonNull private final CurrencyId documentCurrencyId;
	@NonNull private final CurrencyId localCurrencyId;
	@NonNull private final CurrencyPrecision localCurrencyPrecision;

	@Builder
	private AcctRowCurrencyRate(
			final @NonNull BigDecimal conversionRate,
			final @NonNull CurrencyId documentCurrencyId,
			final @NonNull CurrencyId localCurrencyId,
			final @NonNull CurrencyPrecision localCurrencyPrecision)
	{

		this.conversionRate = conversionRate;
		this.documentCurrencyId = documentCurrencyId;
		this.localCurrencyId = localCurrencyId;
		this.localCurrencyPrecision = localCurrencyPrecision;
	}

	public static AcctRowCurrencyRate ofCurrencyRate(@NonNull final CurrencyRate currencyRate)
	{
		return builder()
				.conversionRate(currencyRate.getConversionRate())
				.documentCurrencyId(currencyRate.getFromCurrencyId())
				.localCurrencyId(currencyRate.getToCurrencyId())
				.localCurrencyPrecision(currencyRate.getToCurrencyPrecision())
				.build();
	}

	public Money convertToLocalCurrency(final Money amount)
	{
		amount.assertCurrencyId(documentCurrencyId);

		BigDecimal amountConv = conversionRate.multiply(amount.toBigDecimal());
		amountConv = localCurrencyPrecision.roundIfNeeded(amountConv);
		return Money.of(amountConv, localCurrencyId);
	}
}
