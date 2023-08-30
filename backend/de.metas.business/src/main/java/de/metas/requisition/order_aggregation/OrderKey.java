package de.metas.requisition.order_aggregation;

import de.metas.bpartner.BPartnerId;
import de.metas.pricing.PriceListId;
import de.metas.requisition.RequisitionId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder
class OrderKey
{
	@NonNull BPartnerId vendorId;
	@NonNull Instant datePromised;
	@Nullable PriceListId priceListId;
	@Nullable RequisitionId requisitionId;
	@Nullable String requisitionDocumentNo;
	@Nullable UserId requestorId;
}
