package de.metas.acct.gljournal_sap;

import de.metas.acct.api.AccountId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.sectionCode.SectionCodeId;
import de.metas.tax.api.TaxId;
import de.metas.util.lang.SeqNo;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Nullable;

@EqualsAndHashCode
@ToString
@Builder
public class SAPGLJournalLine
{
	@NonNull @Getter private final SAPGLJournalLineId id;

	@NonNull private final SeqNo line;

	@NonNull private final AccountId accountId;
	@NonNull @Getter private final PostingSign postingSign;
	@NonNull @Getter private final Money amount;
	@NonNull @Getter @Setter private Money amountAcct;

	@Nullable private final TaxId taxId;

	@Nullable private final SectionCodeId sectionCodeId;
	@Nullable private final ProductId productId;
	@Nullable private final OrderId orderId;
	@Nullable private final ActivityId activityId;
}
