package de.metas.distribution.ddorder.movement.schedule;

import de.metas.handlingunits.HuId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.mmovement.MovementId;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;

@Getter
@EqualsAndHashCode
@ToString
@Builder
public class DDOrderMoveSchedulePickedHU
{
	@Nullable private final HuId actualHUIdPicked;
	@NonNull private final Quantity qtyPicked;
	@NonNull private final MovementId pickFromMovementId;
	@Nullable private final LocatorId inTransitLocatorId;

	// Drop To status
	@Nullable MovementId dropToMovementId;

	public boolean isDroppedTo() {return dropToMovementId != null;}

	void setDropToMovementId(@NonNull final MovementId dropToMovementId)
	{
		this.dropToMovementId = dropToMovementId;
	}
}
