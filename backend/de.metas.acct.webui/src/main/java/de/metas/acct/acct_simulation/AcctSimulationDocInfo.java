package de.metas.acct.acct_simulation;

import de.metas.acct.api.AcctSchemaId;
import de.metas.currency.CurrencyConversionContext;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;

@Value
@Builder
class AcctSimulationDocInfo
{
	@NonNull TableRecordReference recordRef;
	@NonNull ClientId clientId;
	@NonNull OrgId orgId;
	@NonNull AcctSchemaId acctSchemaId;
	@NonNull CurrencyId documentCurrencyId;
	@NonNull CurrencyId localCurrencyId;
	@NonNull CurrencyConversionContext currencyConversionContext;
}
