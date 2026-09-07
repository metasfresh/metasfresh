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
