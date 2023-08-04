package de.metas.acct.gljournal_sap;

import de.metas.acct.Account;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.gljournal_sap.service.SAPGLJournalTaxProvider;
import de.metas.acct.open_items.FAOpenItemTrxInfo;
import de.metas.bpartner.BPartnerId;
import de.metas.document.dimension.Dimension;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.tax.api.TaxId;
import de.metas.util.lang.SeqNo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Optional;

@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
@Builder(toBuilder = true)
public class SAPGLJournalLine
{
	@Nullable private SAPGLJournalLineId id;
	@Getter @Setter(AccessLevel.PACKAGE) private boolean processed;

	@Nullable @Getter private final SAPGLJournalLineId parentId;

	@NonNull @Getter @Setter(AccessLevel.PACKAGE) private SeqNo line;
	@Nullable @Getter private final String description;

	@NonNull @Getter private final Account account;
	@NonNull @Getter private final PostingSign postingSign;
	@NonNull @Getter private final Money amount;
	@NonNull @Getter @Setter(AccessLevel.PACKAGE) private Money amountAcct;

	@Nullable @Getter private final TaxId taxId;

	@NonNull @Getter private final OrgId orgId;
	@Nullable @Getter private final BPartnerId bpartnerId;
	@NonNull @Getter private final Dimension dimension;
	@Getter private final boolean determineTaxBaseSAP;
	@Getter private final boolean isExplodeToNetAndTaxLines;

	@Nullable @Getter private final FAOpenItemTrxInfo openItemTrxInfo;

	@Getter private final boolean isFieldsReadOnlyInUI;

	public SAPGLJournalLineId getIdNotNull()
	{
		if (id == null)
		{
			throw new AdempiereException("Line ID not yet available: " + this);
		}
		return id;
	}

	@Nullable
	public SAPGLJournalLineId getIdOrNull()
	{
		return id;
	}

	public void assignId(@NonNull final SAPGLJournalLineId id)
	{
		if (this.id != null && !SAPGLJournalLineId.equals(this.id, id))
		{
			throw new AdempiereException("Line has already assigned a different ID: " + this);
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

	public boolean isTaxLineGeneratedForBaseLine(@NonNull final SAPGLJournalLine taxBaseLine)
	{
		return isTaxLine()
				&& parentId != null
				&& SAPGLJournalLineId.equals(parentId, taxBaseLine.getIdOrNull());
	}

	public boolean isBaseTaxLine()
	{
		return parentId == null && taxId != null && !determineTaxBaseSAP;
	}

	@NonNull
	public Optional<SAPGLJournalLine> getReverseChargeCounterPart(
			@NonNull final SAPGLJournalTaxProvider taxProvider,
			@NonNull final AcctSchemaId acctSchemaId)
	{
		return Optional.ofNullable(taxId)
				.filter(taxProvider::isReverseCharge)
				.map(reverseChargeTaxId -> toBuilder()
						.postingSign(getPostingSign().reverse())
						.account(taxProvider.getTaxAccount(reverseChargeTaxId, acctSchemaId, getPostingSign().reverse()))
						.build());
	}
}
