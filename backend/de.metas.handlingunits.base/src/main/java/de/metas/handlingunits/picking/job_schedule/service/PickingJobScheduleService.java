package de.metas.handlingunits.picking.job_schedule.service;

import de.metas.handlingunits.picking.job_schedule.model.PickingJobScheduleId;
import de.metas.handlingunits.picking.job_schedule.repository.PickingJobScheduleRepository;
import de.metas.handlingunits.picking.job_schedule.service.commands.CreateOrUpdatePickingJobSchedulesCommand;
import de.metas.handlingunits.picking.job_schedule.service.commands.CreateOrUpdatePickingJobSchedulesRequest;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class PickingJobScheduleService
{
	@NonNull private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	@NonNull private final PickingJobScheduleRepository pickingJobScheduleRepository;

	public void createOrUpdate(@NonNull final CreateOrUpdatePickingJobSchedulesRequest request)
	{
		CreateOrUpdatePickingJobSchedulesCommand.builder()
				.pickingJobScheduleRepository(pickingJobScheduleRepository)
				.shipmentScheduleBL(shipmentScheduleBL)
				//
				.request(request)
				//
				.build().execute();
	}

	public void deleteJobSchedulesById(@NonNull final Set<PickingJobScheduleId> jobScheduleIds)
	{
		pickingJobScheduleRepository.deleteJobSchedulesById(jobScheduleIds);
	}

}
