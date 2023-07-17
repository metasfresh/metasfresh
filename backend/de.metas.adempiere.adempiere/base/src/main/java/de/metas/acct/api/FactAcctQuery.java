package de.metas.acct.api;

import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class FactAcctQuery
{
	@Nullable AcctSchemaId acctSchemaId;
	@Nullable String accountConceptualName;

	@Nullable String tableName;
	int recordId;
	int lineId;
}
