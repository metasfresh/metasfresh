package de.metas.material.planning.ddorder;

import de.metas.shipping.ShipperId;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import java.time.Duration;

@Value
@Builder
public class DistributionNetworkLine
{
	@NonNull DistributionNetworkLineId id;
	@NonNull WarehouseId sourceWarehouseId;
	@NonNull WarehouseId targetWarehouseId;
	int priorityNo;
	@NonNull ShipperId shipperId;
	
	@NonNull @Builder.Default Percent transferPercent = Percent.ONE_HUNDRED;
	@NonNull Duration transferDuration;
	
	boolean isAllowPush;
	boolean isKeepTargetPlant;
}
