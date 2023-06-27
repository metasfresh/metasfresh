package de.metas.gplr.source;

import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyConversionContext;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
public class SourceCurrencyInfo
{
	@NonNull BigDecimal currencyRate;
	@NonNull CurrencyCode foreignCurrencyCode;
	@NonNull CurrencyCode localCurrencyCode;
	@Nullable String fecDocumentNo;

	@NonNull CurrencyConversionContext conversionCtx;
}
