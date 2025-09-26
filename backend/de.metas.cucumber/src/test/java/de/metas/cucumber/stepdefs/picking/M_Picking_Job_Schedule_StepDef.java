package de.metas.cucumber.stepdefs.picking;

import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.shipmentschedule.M_ShipmentSchedule_StepDefData;
import de.metas.cucumber.stepdefs.workplace.C_Workplace_StepDefData;
import de.metas.handlingunits.picking.job_schedule.service.PickingJobScheduleService;
import de.metas.handlingunits.picking.job_schedule.service.commands.CreateOrUpdatePickingJobSchedulesRequest;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_Picking_Job_Schedule;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.picking.job_schedule.model.PickingJobSchedule;
import de.metas.picking.job_schedule.model.PickingJobScheduleQuery;
import de.metas.workplace.WorkplaceId;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.SpringContextHolder;

@RequiredArgsConstructor
public class M_Picking_Job_Schedule_StepDef
{
	@NonNull private final PickingJobScheduleService pickingJobScheduleService = SpringContextHolder.instance.getBean(PickingJobScheduleService.class);

	@NonNull private final M_Picking_Job_Schedule_StepDefData jobScheduleTable;
	@NonNull private final M_ShipmentSchedule_StepDefData shipmentScheduleTable;
	@NonNull private final C_Workplace_StepDefData workplaceTable;

	@And("^create or update picking job schedules$")
	public void createOrUpdate(final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_M_Picking_Job_Schedule.COLUMNNAME_M_Picking_Job_Schedule_ID)
				.forEach(this::createOrUpdate);
	}

	private void createOrUpdate(DataTableRow row)
	{
		final ShipmentScheduleId shipmentScheduleId = row.getAsIdentifier(I_M_Picking_Job_Schedule.COLUMNNAME_M_ShipmentSchedule_ID).lookupNotNullIdIn(shipmentScheduleTable);
		final WorkplaceId workplaceId = row.getAsIdentifier(I_M_Picking_Job_Schedule.COLUMNNAME_C_Workplace_ID).lookupNotNullIdIn(workplaceTable);

		pickingJobScheduleService.createOrUpdate(
				CreateOrUpdatePickingJobSchedulesRequest.builder()
						.shipmentScheduleAndJobScheduleIds(ShipmentScheduleAndJobScheduleIdSet.of(shipmentScheduleId))
						.workplaceId(workplaceId)
						.qtyToPickBD(row.getAsBigDecimal(I_M_Picking_Job_Schedule.COLUMNNAME_QtyToPick))
						.build()
		);
	}

	@And("^delete picking job schedules$")
	public void delete(final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_M_Picking_Job_Schedule.COLUMNNAME_M_Picking_Job_Schedule_ID)
				.forEach(this::delete);
	}

	private void delete(DataTableRow row)
	{
		final ShipmentScheduleId shipmentScheduleId = row.getAsIdentifier(I_M_Picking_Job_Schedule.COLUMNNAME_M_ShipmentSchedule_ID).lookupNotNullIdIn(shipmentScheduleTable);
		final ImmutableSet<PickingJobScheduleId> jobScheduleIds = pickingJobScheduleService.stream(PickingJobScheduleQuery.builder()
						.onlyShipmentScheduleId(shipmentScheduleId)
						.build())
				.map(PickingJobSchedule::getId)
				.collect(ImmutableSet.toImmutableSet());
		
		pickingJobScheduleService.deleteJobSchedulesById(jobScheduleIds);
	}

}
