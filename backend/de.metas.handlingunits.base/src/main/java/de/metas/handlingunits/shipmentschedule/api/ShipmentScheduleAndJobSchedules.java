package de.metas.handlingunits.shipmentschedule.api;

import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.picking.job_schedule.model.PickingJobSchedule;
import de.metas.handlingunits.picking.job_schedule.model.PickingJobScheduleCollection;
import de.metas.inout.ShipmentScheduleId;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.model.InterfaceWrapperHelper;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Set;

@Value
public class ShipmentScheduleAndJobSchedules
{
	@NonNull I_M_ShipmentSchedule shipmentSchedule;
	@NonNull PickingJobScheduleCollection jobSchedules;

	@NonNull ShipmentScheduleId shipmentScheduleId;

	private ShipmentScheduleAndJobSchedules(@NonNull final I_M_ShipmentSchedule shipmentSchedule, @Nullable Collection<PickingJobSchedule> jobSchedules)
	{
		this.shipmentSchedule = shipmentSchedule;
		this.shipmentScheduleId = ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID());

		if (jobSchedules == null || jobSchedules.isEmpty())
		{
			this.jobSchedules = PickingJobScheduleCollection.EMPTY;
		}
		else
		{
			this.jobSchedules = PickingJobScheduleCollection.ofCollection(jobSchedules);
			this.jobSchedules.assertSingleShipmentScheduleId(this.shipmentScheduleId);
		}
	}

	public static ShipmentScheduleAndJobSchedules of(@NonNull final I_M_ShipmentSchedule shipmentSchedule, @Nullable Collection<PickingJobSchedule> jobSchedules)
	{
		return new ShipmentScheduleAndJobSchedules(shipmentSchedule, jobSchedules);
	}

	public static ShipmentScheduleAndJobSchedules ofShipmentSchedule(@NonNull final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule)
	{
		return new ShipmentScheduleAndJobSchedules(InterfaceWrapperHelper.create(shipmentSchedule, I_M_ShipmentSchedule.class), null);
	}

	public ShipmentScheduleAndJobScheduleIdSet toScheduleIds()
	{
		return jobSchedules.isEmpty()
				? ShipmentScheduleAndJobScheduleIdSet.of(getShipmentScheduleId())
				: jobSchedules.toShipmentScheduleAndJobScheduleIdSet();
	}

	public boolean isProcessed()
	{
		return shipmentSchedule.isProcessed();
	}

	public boolean hasJobSchedules()
	{
		return !jobSchedules.isEmpty();
	}

	public Set<PickingJobScheduleId> getJobScheduleIds() {return jobSchedules.getJobScheduleIds();}

	public String getHeaderAggregationKey() {return StringUtils.trimBlankToNull(shipmentSchedule.getHeaderAggregationKey());}
}
