package de.metas.ui.web.pickingJobSchedule.process;

import de.metas.handlingunits.model.I_M_Picking_Job_Schedule;
import de.metas.handlingunits.picking.job_schedule.model.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.handlingunits.picking.job_schedule.service.commands.CreateOrUpdatePickingJobSchedulesRequest;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.workplace.WorkplaceId;
import org.adempiere.exceptions.AdempiereException;

import java.math.BigDecimal;

public class PickingJobScheduleView_Schedule extends PickingJobScheduleViewBasedProcess
{
	@Param(parameterName = I_M_Picking_Job_Schedule.COLUMNNAME_C_Workplace_ID, mandatory = true)
	private WorkplaceId workplaceId;

	@Param(parameterName = I_M_Picking_Job_Schedule.COLUMNNAME_QtyToPick, mandatory = true)
	private BigDecimal qtyToPickBD;

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
		final ShipmentScheduleAndJobScheduleIdSet ids = getShipmentScheduleAndJobScheduleIds();
		if (ids.isEmpty())
		{
			throw AdempiereException.noSelection();
		}

		pickingJobScheduleService.createOrUpdate(CreateOrUpdatePickingJobSchedulesRequest.builder()
				.shipmentScheduleAndJobScheduleIds(ids)
				.workplaceId(workplaceId)
				.qtyToPickBD(qtyToPickBD)
				.build());

		return MSG_OK;
	}
}
