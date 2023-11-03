package de.metas.acct.open_items;

import de.metas.acct.AccountConceptualName;
import de.metas.acct.api.impl.ElementValueId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class FAOpenItemTrxInfoComputeRequest
{
	@Nullable AccountConceptualName accountConceptualName;
	@NonNull ElementValueId elementValueId;

	@NonNull String tableName;
	int recordId;
	int lineId;
	int subLineId;
}
