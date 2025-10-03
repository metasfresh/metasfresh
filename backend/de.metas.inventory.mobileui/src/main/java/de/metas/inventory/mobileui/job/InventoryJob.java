package de.metas.inventory.mobileui.job;

import com.google.common.collect.ImmutableList;
import de.metas.inventory.InventoryLineId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.time.LocalDate;

@Value
@Builder(toBuilder = true)
public class InventoryJob
{
	@NonNull InventoryJobId id;
	@Nullable UserId responsibleId;
	@NonNull String documentNo;
	@NonNull LocalDate movementDate;
	@NonNull WarehouseId warehouseId;
	@NonNull String warehouseName;

	@NonNull ImmutableList<InventoryJobLine> lines;

	public InventoryJobLine getLineById(final InventoryLineId lineId)
	{
		return lines.stream()
				.filter(line -> InventoryLineId.equals(line.getId(), lineId))
				.findFirst()
				.orElseThrow(() -> new RuntimeException("No line with id " + lineId + " found"));
	}

	public InventoryJob assigningTo(@NonNull final UserId newResponsibleId)
	{
		return assigningTo(newResponsibleId, false);
	}

	public InventoryJob reassigningTo(@NonNull final UserId newResponsibleId)
	{
		return assigningTo(newResponsibleId, true);
	}

	private InventoryJob assigningTo(@NonNull final UserId newResponsibleId, boolean allowReassignment)
	{
		if (UserId.equals(responsibleId, newResponsibleId))
		{
			return this;
		}

		if (!allowReassignment && responsibleId != null && !UserId.equals(responsibleId, newResponsibleId))
		{
			throw new AdempiereException("Inventory is already assigned");
		}

		return toBuilder().responsibleId(newResponsibleId).build();
	}
}
