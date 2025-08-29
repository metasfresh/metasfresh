package de.metas.ui.web.pickingJobSchedule.process;

import de.metas.handlingunits.picking.job_schedule.model.PickingJobScheduleId;
import de.metas.process.ProcessPreconditionsResolution;

import java.util.Set;

public class PickingJobScheduleView_RemoveSchedule extends PickingJobScheduleViewBasedProcess
{
	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final Set<PickingJobScheduleId> jobScheduleIds = getShipmentScheduleAndJobScheduleIds().getJobScheduleIds();
		pickingJobScheduleService.deleteJobSchedulesById(jobScheduleIds);
		return MSG_OK;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		if (!success) {return;}

		getView().invalidateSelection();
	}
}
