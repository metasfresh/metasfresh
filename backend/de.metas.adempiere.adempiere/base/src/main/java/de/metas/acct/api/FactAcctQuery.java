package de.metas.acct.api;

import de.metas.acct.AccountConceptualName;
import de.metas.acct.api.impl.ElementValueId;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class FactAcctQuery
{
	@Nullable AcctSchemaId acctSchemaId;
	@Nullable AccountConceptualName accountConceptualName;
	@Nullable ElementValueId accountId;
	@Nullable PostingType postingType;

	@Nullable String tableName;
	int recordId;
	int lineId;

	@Nullable String openItemsKey;
}
