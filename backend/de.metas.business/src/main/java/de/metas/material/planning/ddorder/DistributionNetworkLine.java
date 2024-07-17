package de.metas.material.planning.ddorder;

import de.metas.shipping.ShipperId;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.time.Duration;

@Value
public class DistributionNetworkLine
{
	@NonNull DistributionNetworkLineId id;
	@NonNull WarehouseId sourceWarehouseId;
	@NonNull WarehouseId targetWarehouseId;
	int priorityNo;
	@NonNull ShipperId shipperId;

	@NonNull Percent transferPercent;
	@NonNull Duration transferDuration;

	boolean isAllowPush;
	boolean isKeepTargetPlant;

	@Builder
	private DistributionNetworkLine(
			@NonNull final DistributionNetworkLineId id,
			@NonNull final WarehouseId sourceWarehouseId,
			@NonNull final WarehouseId targetWarehouseId,
			final int priorityNo,
			@NonNull final ShipperId shipperId,
			@Nullable final Percent transferPercent,
			@NonNull final Duration transferDuration,
			final boolean isAllowPush,
			final boolean isKeepTargetPlant)
	{
		if (transferPercent != null && transferPercent.signum() < 0)
		{
			throw new AdempiereException("Transfer percent cannot be negative");
		}

		this.id = id;
		this.sourceWarehouseId = sourceWarehouseId;
		this.targetWarehouseId = targetWarehouseId;
		this.priorityNo = priorityNo;
		this.shipperId = shipperId;
		this.transferPercent = transferPercent != null ? transferPercent : Percent.ONE_HUNDRED;
		this.transferDuration = transferDuration;
		this.isAllowPush = isAllowPush;
		this.isKeepTargetPlant = isKeepTargetPlant;
	}
}
