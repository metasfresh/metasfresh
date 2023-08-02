package de.metas.acct.factacct_userchanges;

import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.sectionCode.SectionCodeId;
import de.metas.tax.api.TaxId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
@Builder(toBuilder = true)
public class FactAcctChanges
{
	@NonNull FactAcctChangesType type;
	@Nullable FactLineMatchKey matchKey;

	@NonNull AcctSchemaId acctSchemaId;
	@NonNull PostingSign postingSign;
	@Nullable ElementValueId accountId;
	@NonNull Money amount_DC;
	@NonNull Money amount_LC;
	@Nullable TaxId taxId;
	@Nullable String description;
	@Nullable SectionCodeId sectionCodeId;
	@Nullable ProductId productId;
	@Nullable String userElementString1;
	@Nullable OrderId salesOrderId;
	@Nullable ActivityId activityId;

	@Builder
	private FactAcctChanges(
			@NonNull final FactAcctChangesType type,
			@Nullable final FactLineMatchKey matchKey,
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final PostingSign postingSign,
			@Nullable final ElementValueId accountId,
			@NonNull final Money amount_DC,
			@NonNull final Money amount_LC,
			@Nullable final TaxId taxId,
			@Nullable final String description,
			@Nullable final SectionCodeId sectionCodeId,
			@Nullable final ProductId productId,
			@Nullable final String userElementString1,
			@Nullable final OrderId salesOrderId,
			@Nullable final ActivityId activityId)
	{
		if (type.isChangeOrDelete() && matchKey == null)
		{
			throw new AdempiereException("MatchKey is mandatory when type is " + type);
		}

		this.type = type;
		this.matchKey = matchKey;
		this.acctSchemaId = acctSchemaId;
		this.postingSign = postingSign;
		this.accountId = accountId;
		this.amount_DC = amount_DC;
		this.amount_LC = amount_LC;
		this.taxId = taxId;
		this.description = description;
		this.sectionCodeId = sectionCodeId;
		this.productId = productId;
		this.userElementString1 = userElementString1;
		this.salesOrderId = salesOrderId;
		this.activityId = activityId;
	}

	public ElementValueId getAccountIdNotNull()
	{
		final ElementValueId accountId = getAccountId();
		if (accountId == null)
		{
			throw new AdempiereException("accountId shall be set for " + this);
		}
		return accountId;
	}

	@Nullable
	public Money getAmtSourceDr() {return postingSign.isDebit() ? amount_DC : null;}

	@Nullable
	public Money getAmtSourceCr() {return postingSign.isCredit() ? amount_DC : null;}

	@Nullable
	public Money getAmtAcctDr() {return postingSign.isDebit() ? amount_LC : null;}

	@Nullable
	public Money getAmtAcctCr() {return postingSign.isCredit() ? amount_LC : null;}
}
