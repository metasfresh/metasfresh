package de.metas.manufacturing.issue.plan;

import de.metas.product.ProductId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;

@Value
@Builder(access = AccessLevel.PRIVATE)
class AllocableHUsGroupingKey
{
	@NonNull ProductId productId;
	@Nullable LocatorId pickFromLocatorId;
	boolean sourceHUsOnly;

	@NonNull
	public static AllocableHUsGroupingKey of(@NonNull final ProductId productId, @NonNull final LocatorId locatorId)
	{
		return AllocableHUsGroupingKey.builder()
				.productId(productId)
				.pickFromLocatorId(locatorId)
				.build();
	}

	@NonNull
	public static AllocableHUsGroupingKey onlySourceHUs(@NonNull final ProductId productId)
	{
		return AllocableHUsGroupingKey.builder()
				.productId(productId)
				.sourceHUsOnly(true)
				.build();
	}
}
