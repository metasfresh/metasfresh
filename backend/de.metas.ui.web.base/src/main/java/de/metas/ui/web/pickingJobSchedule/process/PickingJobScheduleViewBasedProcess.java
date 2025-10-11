package de.metas.ui.web.pickingJobSchedule.process;

import de.metas.handlingunits.picking.job_schedule.service.PickingJobScheduleService;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_Picking_Job_Schedule;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.IViewRow;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

import javax.annotation.Nullable;
import java.util.Objects;

public abstract class PickingJobScheduleViewBasedProcess extends ViewBasedProcessTemplate
{
	@NonNull protected final PickingJobScheduleService pickingJobScheduleService = SpringContextHolder.instance.getBean(PickingJobScheduleService.class);

	@SuppressWarnings("SameParameterValue")
	protected final ShipmentScheduleAndJobScheduleIdSet getShipmentScheduleAndJobScheduleIds()
	{
		return getView().streamByIds(getSelectedRowIds())
				.map(PickingJobScheduleViewBasedProcess::extractShipmentScheduleAndJobScheduleId)
				.filter(Objects::nonNull)
				.collect(ShipmentScheduleAndJobScheduleIdSet.collect());
	}

	@Nullable
	private static ShipmentScheduleAndJobScheduleId extractShipmentScheduleAndJobScheduleId(@NonNull final IViewRow row)
	{
		final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoIdOrNull(row.getFieldValueAsInt(I_M_Picking_Job_Schedule.COLUMNNAME_M_ShipmentSchedule_ID, -1));
		if (shipmentScheduleId == null)
		{
			return null;
		}

		final PickingJobScheduleId jobScheduleId = PickingJobScheduleId.ofRepoIdOrNull(row.getFieldValueAsInt(I_M_Picking_Job_Schedule.COLUMNNAME_M_Picking_Job_Schedule_ID, -1));
		return ShipmentScheduleAndJobScheduleId.of(shipmentScheduleId, jobScheduleId);
	}

}
