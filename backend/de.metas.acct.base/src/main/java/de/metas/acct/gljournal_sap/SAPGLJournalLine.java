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
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
@Builder
public class SAPGLJournalLine
{
	@Nullable private SAPGLJournalLineId id;

	@NonNull @Getter private final SeqNo line;
	@Nullable @Getter private final String description;

	@NonNull @Getter private final AccountId accountId;
	@NonNull @Getter private final PostingSign postingSign;
	@NonNull @Getter private final Money amount;
	@NonNull @Getter @Setter private Money amountAcct;

	@Nullable @Getter private final TaxId taxId;

	@Nullable @Getter private final SectionCodeId sectionCodeId;
	@Nullable @Getter private final ProductId productId;
	@Nullable @Getter private final OrderId orderId;
	@Nullable @Getter private final ActivityId activityId;

	public SAPGLJournalLineId getIdNotNull()
	{
		if (id == null)
		{
			throw new AdempiereException("Line not saved: " + this);
		}
		return id;
	}

	@Nullable
	public SAPGLJournalLineId getIdOrNull()
	{
		return id;
	}

	public void markAsSaved(@NonNull final SAPGLJournalLineId id)
	{
		if (this.id != null && !SAPGLJournalLineId.equals(this.id, id))
		{
			throw new AdempiereException("Line already saved: " + this);
		}

		this.id = id;
	}

}
