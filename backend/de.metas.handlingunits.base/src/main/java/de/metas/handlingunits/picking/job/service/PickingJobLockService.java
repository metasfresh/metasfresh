package de.metas.handlingunits.picking.job.service;

import com.google.common.collect.ImmutableMap;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.ScheduledPackageableLocks;
import de.metas.handlingunits.picking.job_schedule.model.ShipmentScheduleAndJobScheduleId;
import de.metas.handlingunits.picking.job_schedule.model.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.inoutcandidate.lock.ShipmentScheduleLockRepository;
import de.metas.inoutcandidate.lock.ShipmentScheduleLockRequest;
import de.metas.inoutcandidate.lock.ShipmentScheduleLockType;
import de.metas.inoutcandidate.lock.ShipmentScheduleUnLockRequest;
import de.metas.lock.api.ILockManager;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PickingJobLockService
{
	@NonNull private final ILockManager lockManager = Services.get(ILockManager.class);
	@NonNull private final ShipmentScheduleLockRepository shipmentScheduleLockRepository;

	public ScheduledPackageableLocks getLocks(@NonNull final ShipmentScheduleAndJobScheduleIdSet scheduleIds)
	{
		if (scheduleIds.isEmpty()) {return ScheduledPackageableLocks.EMPTY;}

		final ImmutableMap<TableRecordReference, ShipmentScheduleAndJobScheduleId> scheduleIdsByRecordRef = scheduleIds.stream()
				.collect(ImmutableMap.toImmutableMap(
						ShipmentScheduleAndJobScheduleId::toTableRecordReference,
						scheduleId -> scheduleId
				));

		final TableRecordReferenceSet recordRefs = TableRecordReferenceSet.of(scheduleIdsByRecordRef.keySet());

		return ScheduledPackageableLocks.of(
				CollectionUtils.mapKeys(
						lockManager.getLockInfosByRecordIds(recordRefs),
						scheduleIdsByRecordRef::get
				)
		);
	}

	public void lockSchedules(
			final @NonNull ShipmentScheduleAndJobScheduleIdSet scheduleIds,
			final @NonNull UserId lockedBy)
	{
		shipmentScheduleLockRepository.lock(
				ShipmentScheduleLockRequest.builder()
						.shipmentScheduleIds(scheduleIds.getShipmentScheduleIds())
						.lockType(ShipmentScheduleLockType.PICKING)
						.lockedBy(lockedBy)
						.build());
	}

	public void unlockSchedules(@NonNull final PickingJob pickingJob)
	{
		if (pickingJob.getLockedBy() == null)
		{
			return;
		}

		this.unlockSchedules(pickingJob.getScheduleIds(), pickingJob.getLockedBy());
	}

	public void unlockSchedules(
			final @NonNull ShipmentScheduleAndJobScheduleIdSet scheduleIds,
			final @NonNull UserId lockedBy)
	{
		shipmentScheduleLockRepository.unlock(
				ShipmentScheduleUnLockRequest.builder()
						.shipmentScheduleIds(scheduleIds.getShipmentScheduleIds())
						.lockType(ShipmentScheduleLockType.PICKING)
						.lockedBy(lockedBy)
						.build());
	}

}
