package de.metas.manufacturing.order.weighting;

import com.google.common.collect.ImmutableList;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder
public class PPOrderWeightingRun
{
	@NonNull PPOrderWeightingRunId id;

	@NonNull PPOrderId ppOrderId;
	@Nullable PPOrderBOMLineId ppOrderBOMLineId;
	@NonNull Instant dateDoc;
	@Nullable String description;

	@NonNull ProductId productId;

	@NonNull WeightingSpecificationsId weightingSpecificationsId;
	@NonNull Percent tolerance;
	int weightChecksRequired;

	@NonNull Quantity targetWeight;
	@NonNull Quantity targetWeightMin;
	@NonNull Quantity targetWeightMax;

	boolean isToleranceExceeded;
	boolean isProcessed;

	@NonNull ImmutableList<PPOrderWeightingRunCheck> checks;
}
