package de.metas.manufacturing.job;

import de.metas.i18n.ITranslatableString;
import de.metas.manufacturing.order.PPOrderIssueSchedule;
import de.metas.manufacturing.order.PPOrderIssueScheduleId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class RawMaterialsIssueStep
{
	PPOrderIssueScheduleId id;

	@NonNull ProductId productId;
	@NonNull ITranslatableString productName;
	@NonNull Quantity qtyToIssue;

	//
	// Issue From
	@NonNull LocatorInfo issueFromLocator;
	@NonNull HUInfo issueFromHU;

	//
	// Issued
	@Nullable PPOrderIssueSchedule.Issued issued;
}
