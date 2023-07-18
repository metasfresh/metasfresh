package de.metas.acct.gljournal_sap.select_open_items;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.FactAcctId;
import de.metas.acct.api.PostingType;
import de.metas.money.CurrencyId;
import de.metas.ui.web.document.filter.DocumentFilter;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Set;

@Value
@Builder
public class OIViewDataQuery
{
	@NonNull AcctSchema acctSchema;
	@NonNull PostingType postingType;
	@NonNull CurrencyId currencyId;
	@NonNull FutureClearingAmountMap futureClearingAmounts;
	@Nullable DocumentFilter filter;
	@Nullable Set<FactAcctId> includeFactAcctIds;

	public AcctSchemaId getAcctSchemaId() {return acctSchema.getId();}
}
