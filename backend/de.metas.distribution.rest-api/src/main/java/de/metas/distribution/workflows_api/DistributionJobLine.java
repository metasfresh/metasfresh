package de.metas.distribution.workflows_api;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.api.DDOrderLineId;

@Value
@Builder
public class DistributionJobLine
{
	@NonNull DDOrderLineId ddOrderLineId;

	@NonNull ProductInfo product;
	@NonNull LocatorInfo pickFromLocator;
	@NonNull LocatorInfo dropToLocator;

	@NonNull ImmutableList<DistributionJobStep> steps;
}
