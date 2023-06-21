package de.metas.gplr.model;

import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyPrecision;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class ForexInfo
{
	@NonNull CurrencyCode foreignCurrency;
	@NonNull BigDecimal currencyRate;

	@Override
	public String toString()
	{
		return toRenderedString();
	}

	public String toRenderedString()
	{
		return foreignCurrency + " / " + CurrencyPrecision.FOUR.round(currencyRate);
	}
}
