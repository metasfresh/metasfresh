package de.metas.acct.api;

import de.metas.acct.AccountConceptualName;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.acct.open_items.FAOpenItemKey;
import de.metas.acct.open_items.FAOpenItemTrxType;
import de.metas.bpartner.BPartnerId;
import de.metas.document.engine.DocStatus;
import de.metas.order.OrderId;
import de.metas.sectionCode.SectionCodeId;
import de.metas.util.InSetPredicate;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Set;

@Value
@Builder
public class FactAcctQuery
{
	@Nullable Set<FactAcctId> includeFactAcctIds;

	@Nullable AcctSchemaId acctSchemaId;
	@Nullable @Singular Set<AccountConceptualName> accountConceptualNames;
	@Nullable ElementValueId accountId;
	@Nullable PostingType postingType;

	@Nullable Instant dateAcct;

	@Nullable String tableName;
	int recordId;
	int lineId;

	@Nullable Boolean isOpenItem;
	@Nullable Boolean isOpenItemReconciled;
	@Nullable FAOpenItemKey openItemsKey;
	@Nullable FAOpenItemTrxType openItemTrxType;

	@Nullable DocStatus docStatus;
	@Nullable String documentNoLike;
	@Nullable String descriptionLike;

	@NonNull @Builder.Default InSetPredicate<BPartnerId> bpartnerIds = InSetPredicate.any();
	@Nullable SectionCodeId sectionCodeId;
	@Nullable OrderId salesOrderId;
	@Nullable String userElementString1Like;
	@Nullable String userElementString2Like;
	@Nullable String userElementString3Like;
	@Nullable String userElementString4Like;
	@Nullable String userElementString5Like;
	@Nullable String userElementString6Like;
	@Nullable String userElementString7Like;
}
