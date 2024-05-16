package de.metas.handlingunits.picking.job.service.commands;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.lock.ShipmentScheduleLock;
import de.metas.inoutcandidate.lock.ShipmentScheduleLockRepository;
import de.metas.inoutcandidate.lock.ShipmentScheduleLockRequest;
import de.metas.inoutcandidate.lock.ShipmentScheduleLocksMap;
import de.metas.inoutcandidate.lock.ShipmentScheduleUnLockRequest;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.slf4j.Logger;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Set;

public class InMemoryShipmentScheduleLockRepository implements ShipmentScheduleLockRepository
{
	private static final Logger logger = LogManager.getLogger(InMemoryShipmentScheduleLockRepository.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final LinkedHashMap<ShipmentScheduleId, ShipmentScheduleLock> locks = new LinkedHashMap<>();

	@Override
	public void lock(@NonNull final ShipmentScheduleLockRequest request)
	{
		trxManager.runInNewTrx(() -> lockInTrx(request));
	}

	@Override
	public void lockInTrx(@NonNull final ShipmentScheduleLockRequest request)
	{
		final ShipmentScheduleLocksMap existingLocks = getByShipmentScheduleIds(request.getShipmentScheduleIds());
		existingLocks.assertLockedBy(request.getLockedBy());
		existingLocks.assertLockType(request.getLockType());

		final Set<ShipmentScheduleId> shipmentScheduleIdsToLock = existingLocks.getShipmentScheduleIdsNotLocked(request.getShipmentScheduleIds());
		if (!shipmentScheduleIdsToLock.isEmpty())
		{
			insertLocks(request.withShipmentScheduleIds(shipmentScheduleIdsToLock));
		}
	}

	private void insertLocks(@NonNull final ShipmentScheduleLockRequest request)
	{
		final Instant created = SystemTime.asInstant();

		for (final ShipmentScheduleId shipmentScheduleId : request.getShipmentScheduleIds())
		{
			locks.put(shipmentScheduleId, ShipmentScheduleLock.builder()
					.shipmentScheduleId(shipmentScheduleId)
					.lockedBy(request.getLockedBy())
					.lockType(request.getLockType())
					.created(created)
					.build());
		}
	}

	@Override
	public void unlockNoFail(@NonNull final ShipmentScheduleUnLockRequest request)
	{
		try
		{
			unlock(request);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed to unlock {}. Ignored", request, ex);
		}
	}

	@Override
	public void unlock(@NonNull final ShipmentScheduleUnLockRequest request)
	{
		final ShipmentScheduleLocksMap existingLocks = getByShipmentScheduleIds(request.getShipmentScheduleIds());
		if (existingLocks.isEmpty())
		{
			return;
		}

		if (request.getLockedBy() != null)
		{
			existingLocks.assertLockedBy(request.getLockedBy());
		}
		existingLocks.assertLockType(request.getLockType());

		existingLocks.getShipmentScheduleIdsLocked().forEach(locks::remove);
	}

	@Override
	public ShipmentScheduleLocksMap getByShipmentScheduleIds(@NonNull final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		return ShipmentScheduleLocksMap.of(
				shipmentScheduleIds.stream()
						.map(locks::get)
						.filter(Objects::nonNull)
						.collect(ImmutableList.toImmutableList())
		);
	}
}
