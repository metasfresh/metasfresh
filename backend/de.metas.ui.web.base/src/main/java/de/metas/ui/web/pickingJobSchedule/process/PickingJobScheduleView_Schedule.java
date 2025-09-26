package de.metas.ui.web.pickingJobSchedule.process;

import de.metas.handlingunits.picking.job_schedule.service.commands.CreateOrUpdatePickingJobSchedulesRequest;
import de.metas.inoutcandidate.model.I_M_Picking_Job_Schedule;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.quantity.Quantity;
import de.metas.workplace.WorkplaceId;
import org.adempiere.exceptions.AdempiereException;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;

public class PickingJobScheduleView_Schedule extends PickingJobScheduleViewBasedProcess implements IProcessDefaultParametersProvider
{
	@Param(parameterName = I_M_Picking_Job_Schedule.COLUMNNAME_C_Workplace_ID, mandatory = true)
	private WorkplaceId workplaceId;

	public static final String PARAM_QtyToPick = I_M_Picking_Job_Schedule.COLUMNNAME_QtyToPick;
	@Param(parameterName = PARAM_QtyToPick, mandatory = true)
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
	public @Nullable Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (parameter.getColumnName().equals(PARAM_QtyToPick))
		{
			final ShipmentScheduleAndJobScheduleId shipmentScheduleAndJobScheduleId = getShipmentScheduleAndJobScheduleIds().singleOrNull();
			if (shipmentScheduleAndJobScheduleId != null)
			{
				final Quantity qtyRemainingToScheduleForPicking = pickingJobScheduleService.getQtyRemainingToScheduleForPicking(shipmentScheduleAndJobScheduleId.getShipmentScheduleId());
				return qtyRemainingToScheduleForPicking.toBigDecimal();
			}
		}

		return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
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

	@Override
	protected void postProcess(final boolean success)
	{
		if (!success) {return;}

		getView().invalidateSelection();
	}
}
