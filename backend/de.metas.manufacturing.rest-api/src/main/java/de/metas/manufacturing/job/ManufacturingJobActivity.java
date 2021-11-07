package de.metas.manufacturing.job;

import de.metas.material.planning.pporder.PPRoutingActivityType;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.api.PPOrderRoutingActivityId;
import org.eevolution.api.PPOrderRoutingActivityStatus;

import javax.annotation.Nullable;

@Value
@Builder
public class ManufacturingJobActivity
{
	@NonNull ManufacturingJobActivityId id;
	@NonNull String name;
	@NonNull PPRoutingActivityType type;

	@Nullable RawMaterialsIssue rawMaterialsIssue;
	@Nullable FinishedGoodsReceive finishedGoodsReceive;

	@NonNull PPOrderRoutingActivityId orderRoutingActivityId;
	@NonNull PPOrderRoutingActivityStatus status;
}
