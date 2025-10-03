package de.metas.inventory.mobileui.job;

import com.google.common.collect.ImmutableList;
import de.metas.inventory.InventoryLineId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;

@Value
@Builder
public class InventoryJob
{
	@NonNull InventoryJobId id;
	@Nullable @With UserId responsibleId;
	@NonNull WarehouseId warehouseId;

	@NonNull ImmutableList<InventoryJobLine> lines;

	public InventoryJobLine getLineById(final InventoryLineId lineId)
	{
		return lines.stream()
				.filter(line -> InventoryLineId.equals(line.getId(), lineId))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("No line with id " + lineId + " found"));
	}
}
