package de.metas.handlingunits.picking.job.service;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.lock.ShipmentScheduleLockRepository;
import de.metas.inoutcandidate.lock.ShipmentScheduleLockRequest;
import de.metas.inoutcandidate.lock.ShipmentScheduleLockType;
import de.metas.inoutcandidate.lock.ShipmentScheduleUnLockRequest;
import de.metas.user.UserId;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class PickingJobLockService
{
	private final ShipmentScheduleLockRepository shipmentScheduleLockRepository;

	public PickingJobLockService(final ShipmentScheduleLockRepository shipmentScheduleLockRepository) {this.shipmentScheduleLockRepository = shipmentScheduleLockRepository;}

	public void lockShipmentSchedules(
			final @NonNull ImmutableSet<ShipmentScheduleId> shipmentScheduleIds,
			final @NonNull UserId lockedBy)
	{
		shipmentScheduleLockRepository.lock(
				ShipmentScheduleLockRequest.builder()
						.shipmentScheduleIds(shipmentScheduleIds)
						.lockType(ShipmentScheduleLockType.PICKING)
						.lockedBy(lockedBy)
						.build());
	}

	public void unlockShipmentSchedules(@NonNull final PickingJob pickingJob)
	{
		if (pickingJob.getLockedBy() == null)
		{
			return;
		}

		unlockShipmentSchedules(pickingJob.getShipmentScheduleIds(), pickingJob.getLockedBy());
	}

	public void unlockShipmentSchedules(
			final @NonNull ImmutableSet<ShipmentScheduleId> shipmentScheduleIds,
			final @NonNull UserId lockedBy)
	{
		shipmentScheduleLockRepository.unlock(
				ShipmentScheduleUnLockRequest.builder()
						.shipmentScheduleIds(shipmentScheduleIds)
						.lockType(ShipmentScheduleLockType.PICKING)
						.lockedBy(lockedBy)
						.build());
	}

}
