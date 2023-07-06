package de.metas.acct.api;

import de.metas.acct.AccountConceptualName;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.bpartner.BPartnerId;
import de.metas.util.InSetPredicate;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder
public class FactAcctQuery
{
	@Nullable AcctSchemaId acctSchemaId;
	@Nullable AccountConceptualName accountConceptualName;
	@Nullable ElementValueId accountId;
	@Nullable PostingType postingType;

	@Nullable Instant dateAcct;

	@Nullable String tableName;
	int recordId;
	int lineId;

	@Nullable Boolean isOpenItem;
	@Nullable Boolean isOpenItemReconciled;
	@Nullable String openItemsKey;

	@Nullable String documentNoLike;
	@Nullable String descriptionLike;

	@NonNull @Builder.Default InSetPredicate<BPartnerId> bpartnerIds = InSetPredicate.any();
}
