package de.metas.acct.acct_simulation;

import de.metas.acct.api.impl.ElementValueId;
import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.currency.Amount;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.sectionCode.SectionCodeId;
import de.metas.tax.api.TaxId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder(toBuilder = true)
public class FactLineChanges
{
	@NonNull PostingSign postingSign;
	@Nullable ElementValueId accountId;
	@NonNull Amount amount_DC;
	@NonNull Amount amount_LC;
	@Nullable TaxId taxId;
	@Nullable String description;
	@Nullable SectionCodeId sectionCodeId;
	@Nullable ProductId productId;
	@Nullable String userElementString1;
	@Nullable OrderId salesOrderId;
	@Nullable ActivityId activityId;
}
