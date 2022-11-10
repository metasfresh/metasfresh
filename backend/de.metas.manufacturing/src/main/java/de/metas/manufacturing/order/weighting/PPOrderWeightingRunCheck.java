package de.metas.manufacturing.order.weighting;

import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class PPOrderWeightingRunCheck
{
	int lineNo;
	@NonNull Quantity weight;
	@Nullable String description;
}
