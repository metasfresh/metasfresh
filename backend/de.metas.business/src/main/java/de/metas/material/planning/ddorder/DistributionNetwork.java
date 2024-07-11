package de.metas.material.planning.ddorder;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Value
public class DistributionNetwork
{
	@NonNull DistributionNetworkId id;
	@NonNull String name;
	@NonNull ImmutableList<DistributionNetworkLine> lines;
	boolean isEmptiesDistributionNetwork;

	@Builder
	private DistributionNetwork(
			@NonNull final DistributionNetworkId id,
			@NonNull final String name,
			@NonNull final Collection<DistributionNetworkLine> lines,
			final boolean isEmptiesDistributionNetwork)
	{
		this.id = id;
		this.name = name;
		this.lines = lines.stream()
				.sorted(Comparator.comparing(DistributionNetworkLine::getPriorityNo)
						.thenComparing(DistributionNetworkLine::getShipperId)
						.thenComparing(DistributionNetworkLine::getId))
				.collect(ImmutableList.toImmutableList());
		this.isEmptiesDistributionNetwork = isEmptiesDistributionNetwork;
	}

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
