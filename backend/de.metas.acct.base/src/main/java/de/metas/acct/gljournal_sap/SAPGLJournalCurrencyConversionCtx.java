package de.metas.acct.gljournal_sap;

import de.metas.currency.FixedConversionRate;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.ClientAndOrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder
public class SAPGLJournalCurrencyConversionCtx
{
	@NonNull CurrencyId acctCurrencyId;
	@NonNull CurrencyId currencyId;
	@Nullable CurrencyConversionTypeId conversionTypeId;
	@NonNull Instant date;
	@Nullable FixedConversionRate fixedConversionRate;
	@NonNull ClientAndOrgId clientAndOrgId;
}
