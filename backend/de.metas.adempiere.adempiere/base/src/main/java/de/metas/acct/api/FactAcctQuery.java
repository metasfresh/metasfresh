package de.metas.acct.api;

import de.metas.acct.AccountConceptualName;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class FactAcctQuery
{
	@Nullable AcctSchemaId acctSchemaId;
	@Nullable AccountConceptualName accountConceptualName;

	@Nullable String tableName;
	int recordId;
	int lineId;
}
