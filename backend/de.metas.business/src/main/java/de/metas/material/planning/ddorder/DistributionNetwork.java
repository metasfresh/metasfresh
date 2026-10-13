package de.metas.material.planning.ddorder;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

	/**
	 * Returns the source warehouse of the highest-priority line (lowest {@code priorityNo}) whose target is the given warehouse,
	 * or empty if no such line exists.
	 *
	 * <p>Lines are pre-sorted ascending by {@code priorityNo} inside this class, so {@code findFirst} always returns the
	 * highest-priority line — behaviour-preserving equivalent of {@code getLinesByTargetWarehouse(...).get(0).getSourceWarehouseId()}.</p>
	 */
	public Optional<WarehouseId> getFirstSourceWarehouseIdByTargetWarehouse(@NonNull final WarehouseId targetWarehouseId)
	{
		return getLinesByTargetWarehouse(targetWarehouseId).stream()
				.findFirst()
				.map(DistributionNetworkLine::getSourceWarehouseId);
	}

	public List<DistributionNetworkLine> getLinesBySourceWarehouse(@NonNull final WarehouseId sourceWarehouseId)
	{
		return lines.stream()
				.filter(line -> WarehouseId.equals(line.getSourceWarehouseId(), sourceWarehouseId))
				.collect(ImmutableList.toImmutableList());
	}

	public DistributionNetworkLine getLineById(final DistributionNetworkLineId lineId)
	{
		return lines.stream()
				.filter(line -> DistributionNetworkLineId.equals(line.getId(), lineId))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No line found with id " + lineId + " in " + this));
	}
}
