package de.metas.material.planning.ddorder;

import de.metas.organization.OrgId;
import de.metas.shipping.ShipperId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import java.util.List;

@Value
@Builder
public class CreateDistributionNetworkRequest
{
	@NonNull OrgId orgId;
	@NonNull String name;
	@NonNull @Singular List<Line> lines;
	/** Flags the network as THE client's empties network ({@code DD_NetworkDistribution.IsHUDestroyed}). */
	@Builder.Default boolean huDestroyed = false;

	//
	//
	//
	//
	//

	@Value
	@Builder
	public static class Line
	{
		@NonNull WarehouseId sourceWarehouseId;
		@NonNull WarehouseId targetWarehouseId;
		@NonNull ShipperId shipperId;
	}
}
