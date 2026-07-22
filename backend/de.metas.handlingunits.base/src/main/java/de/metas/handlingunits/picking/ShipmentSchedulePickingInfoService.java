package de.metas.handlingunits.picking;

import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.spi.IShipmentSchedulePickingInfoService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Resolves {@link IShipmentSchedulePickingInfoService} for {@code de.metas.swat.base}, which does not depend on
 * this module (that's where {@code M_Picking_Job} lives).
 */
@Service
@RequiredArgsConstructor
public class ShipmentSchedulePickingInfoService implements IShipmentSchedulePickingInfoService
{
	@NonNull private final PickingJobRepository pickingJobRepository;

	@Override
	public Set<ShipmentScheduleId> retrieveScheduleIdsWithUnfinishedPicking(@NonNull final Set<ShipmentScheduleId> scheduleIds)
	{
		return pickingJobRepository.getScheduleIdsWithDraftedPickingJob(scheduleIds);
	}
}
