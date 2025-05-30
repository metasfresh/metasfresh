package de.metas.handlingunits.picking.job.shipment;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.service.CreateShipmentPolicy;
import de.metas.inout.ShipmentScheduleId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Delegate;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class PickingShipmentCandidate
{
	@NonNull @Delegate private final PickingShipmentCandidateKey key;
	@NonNull @Getter private final ImmutableSet<HuId> onlyLUIds;
	@NonNull @Getter private final CreateShipmentPolicy createShipmentPolicy;

	@NonNull private final HashSet<ShipmentScheduleId> shipmentScheduleIds = new HashSet<>();

	@Builder
	private PickingShipmentCandidate(
			@NonNull final PickingShipmentCandidateKey key,
			@Nullable final Set<HuId> onlyLUIds,
			@Nullable final CreateShipmentPolicy createShipmentPolicy)
	{
		this.key = key;
		this.onlyLUIds = onlyLUIds != null ? ImmutableSet.copyOf(onlyLUIds) : ImmutableSet.of();
		this.createShipmentPolicy = createShipmentPolicy != null ? createShipmentPolicy : CreateShipmentPolicy.CREATE_COMPLETE_CLOSE;
		if (!this.createShipmentPolicy.isCreateShipment())
		{
			throw new AdempiereException("Invalid create shipment policy option: " + this.createShipmentPolicy);
		}
	}

	public void addLine(@NonNull final PickingJobLine line)
	{
		shipmentScheduleIds.add(line.getShipmentScheduleId());
	}

	public ImmutableSet<ShipmentScheduleId> getShipmentScheduleIds()
	{
		return ImmutableSet.copyOf(shipmentScheduleIds);
	}
}
