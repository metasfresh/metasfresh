package de.metas.gplr.report.model;

import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyPrecision;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
public class GPLRCurrencyInfo
{
	@NonNull CurrencyCode foreignCurrency;
	@NonNull BigDecimal currencyRate;
	@Nullable String fecDocumentNo;

	@Override
	public String toString()
	{
		return toRenderedString();
	}

	public String toRenderedString()
	{
		return foreignCurrency + " / " + CurrencyPrecision.FOUR.round(currencyRate)
				+ "\n FEC No. : " + StringUtils.nullToEmpty(fecDocumentNo);
	}
}
