package de.metas.material.dispo.candidate;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DistributionDetail
{
	int productPlanningId;

	int plantId;
	
	int networkDistributionLineId;

	int shipperId;
	
	int ddOrderId;

	int ddOrderLineId;
	
	String ddOrderDocStatus;
}
