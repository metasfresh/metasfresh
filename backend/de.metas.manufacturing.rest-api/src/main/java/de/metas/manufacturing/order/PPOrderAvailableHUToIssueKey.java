package de.metas.manufacturing.order;

import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.handlingunits.HuId;
import de.metas.product.ProductId;
import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
class PPOrderAvailableHUToIssueKey
{
	@NonNull HuId huId;
	@NonNull ProductId productId;
}
