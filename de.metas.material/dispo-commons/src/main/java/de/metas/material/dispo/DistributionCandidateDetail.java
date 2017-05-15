package de.metas.material.dispo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DistributionCandidateDetail
{
	private final int productPlanningId;

	private final int plantId;
	
	private final int networkDistributionLineId;

	private final int shipperId;
	
	private final int ddOrderId;

	private final int ddOrderLineId;
	
	private final String ddOrderDocStatus;
}
