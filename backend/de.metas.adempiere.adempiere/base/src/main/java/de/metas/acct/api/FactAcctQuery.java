package de.metas.acct.api;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.AccountConceptualName;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.acct.open_items.FAOpenItemKey;
import de.metas.acct.open_items.FAOpenItemTrxType;
import de.metas.bpartner.BPartnerId;
import de.metas.document.engine.DocStatus;
import de.metas.money.CurrencyId;
import de.metas.order.OrderId;
import de.metas.sectionCode.SectionCodeId;
import de.metas.util.InSetPredicate;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Set;

@Value
@Builder
public class FactAcctQuery
{
	@Nullable Set<FactAcctId> includeFactAcctIds;

	@Nullable AcctSchemaId acctSchemaId;
	@Nullable @Singular ImmutableSet<AccountConceptualName> accountConceptualNames;
	@Nullable @Singular ImmutableSet<ElementValueId> accountIds;
	@Nullable PostingType postingType;
	@Nullable CurrencyId currencyId;

	@Nullable Instant dateAcct;
	@Nullable Instant dateAcctLessOrEqualsTo;
	@Nullable Instant dateAcctGreaterOrEqualsTo;

	@Nullable String tableName;
	int recordId;
	int lineId;

	@Nullable TableRecordReference excludeRecordRef;

	@Nullable Boolean isOpenItem;
	@Nullable Boolean isOpenItemReconciled;
	@Nullable @Singular Set<FAOpenItemKey> openItemsKeys;
	@Nullable FAOpenItemTrxType openItemTrxType;

	@Nullable @Singular Set<DocStatus> docStatuses;
	@Nullable String documentNoLike;
	@Nullable String descriptionLike;
	@Nullable String poReferenceLike;

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
