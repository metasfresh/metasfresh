package de.metas.inoutcandidate.lock;

import de.metas.inout.ShipmentScheduleId;
import lombok.NonNull;

import java.util.Set;

public interface ShipmentScheduleLockRepository
{
	void lock(@NonNull ShipmentScheduleLockRequest request);

	void lockInTrx(@NonNull ShipmentScheduleLockRequest request);

	void unlockNoFail(@NonNull ShipmentScheduleUnLockRequest request);

	void unlock(@NonNull ShipmentScheduleUnLockRequest request);

	ShipmentScheduleLocksMap getByShipmentScheduleIds(@NonNull Set<ShipmentScheduleId> shipmentScheduleIds);
}
