package de.metas.cucumber.stepdefs.picking;

import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.picking.job_schedule.model.PickingJobSchedule;

public class M_Picking_Job_Schedule_StepDefData extends StepDefData<PickingJobSchedule> implements StepDefDataGetIdAware<PickingJobScheduleId, PickingJobSchedule>
{
	public M_Picking_Job_Schedule_StepDefData()
	{
		super(PickingJobSchedule.class);
	}

	@Override
	public PickingJobScheduleId extractIdFromRecord(final PickingJobSchedule record)
	{
		return record.getId();
	}
}
