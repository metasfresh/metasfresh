package de.metas.acct.gljournal_sap;

import de.metas.acct.Account;
import de.metas.document.dimension.Dimension;
import de.metas.money.Money;
import de.metas.organization.OrgId;
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
@Builder(toBuilder = true)
public class SAPGLJournalLine
{
	@Nullable private SAPGLJournalLineId id;

	@Nullable @Getter private final SAPGLJournalLineId parentId;

	@NonNull @Getter @Setter private SeqNo line;
	@Nullable @Getter private final String description;

	@NonNull @Getter private final Account account;
	@NonNull @Getter private final PostingSign postingSign;
	@NonNull @Getter private final Money amount;
	@NonNull @Getter @Setter private Money amountAcct;

	@Nullable @Getter private final TaxId taxId;

	@NonNull @Getter private final OrgId orgId;
	@NonNull @Getter private final Dimension dimension;
	@Getter private final boolean determineTaxBaseSAP;
	@Getter @Setter private boolean isTaxIncluded;

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

	public boolean isGeneratedTaxLine()
	{
		return parentId != null && taxId != null;
	}

	public boolean isTaxLine()
	{
		return taxId != null && (determineTaxBaseSAP || parentId != null);
	}

	public boolean isBaseTaxLine()
	{
		return parentId == null && taxId != null && !determineTaxBaseSAP;
	}

}
