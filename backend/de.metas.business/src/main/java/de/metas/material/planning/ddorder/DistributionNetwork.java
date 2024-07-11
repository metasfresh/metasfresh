package de.metas.material.planning.ddorder;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import java.util.List;

@Value
@Builder
public class DistributionNetwork
{
	@NonNull DistributionNetworkId id;
	@NonNull String name;
	@NonNull ImmutableList<DistributionNetworkLine> lines;
	boolean isEmptiesDistributionNetwork;

	public List<DistributionNetworkLine> getLinesByTargetWarehouse(@NonNull final WarehouseId targetWarehouseId)
	{
		return lines.stream()
				.filter(line -> WarehouseId.equals(line.getTargetWarehouseId(), targetWarehouseId))
				.collect(ImmutableList.toImmutableList());
	}

	public List<DistributionNetworkLine> getLinesBySourceWarehouse(@NonNull final WarehouseId sourceWarehouseId)
	{
		return lines.stream()
				.filter(line -> WarehouseId.equals(line.getSourceWarehouseId(), sourceWarehouseId))
				.collect(ImmutableList.toImmutableList());
	}

}
