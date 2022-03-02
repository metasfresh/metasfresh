package de.metas.manufacturing.issue.plan;

import com.google.common.collect.ImmutableSet;
import de.metas.product.ProductId;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;

@Value(staticConstructor = "of")
class AllocableHUsGroupingKey
{
	@NonNull ProductId productId;
	@NonNull LocatorId pickFromLocatorId;
}
