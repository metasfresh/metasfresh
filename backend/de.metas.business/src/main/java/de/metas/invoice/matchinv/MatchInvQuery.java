package de.metas.invoice.matchinv;

import com.google.common.collect.ImmutableSet;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.InvoiceId;
import de.metas.order.costs.inout.InOutCostId;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeSetInstanceId;

import javax.annotation.Nullable;

@Value
@Builder
public class MatchInvQuery
{
	@Builder.Default boolean onlyActive = true;
	@Nullable MatchInvType type;
	@Nullable InvoiceId invoiceId;
	@Nullable InvoiceAndLineId invoiceAndLineId;
	@Nullable @Singular ImmutableSet<InOutId> inoutIds;
	@Nullable InOutLineId inoutLineId;
	@Nullable InOutCostId inoutCostId;

	@Nullable AttributeSetInstanceId asiId;
}
