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
import org.compiere.acct.FactLine;

import javax.annotation.Nullable;

@Value
@Builder(toBuilder = true)
public class FactAcctChanges
{
	@NonNull FactLineMatchKey matchKey;

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
}
