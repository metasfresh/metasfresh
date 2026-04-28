package de.metas.cucumber.stepdefs.picking;

import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.shipmentschedule.M_ShipmentSchedule_StepDefData;
import de.metas.cucumber.stepdefs.workplace.C_Workplace_StepDefData;
import de.metas.handlingunits.picking.job_schedule.service.PickingJobScheduleService;
import de.metas.handlingunits.picking.job_schedule.service.commands.CreateOrUpdatePickingJobSchedulesRequest;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_Picking_Job_Schedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.picking.job_schedule.model.PickingJobSchedule;
import de.metas.picking.job_schedule.model.PickingJobScheduleQuery;
import de.metas.util.Services;
import de.metas.workplace.Workplace;
import de.metas.workplace.WorkplaceId;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.SpringContextHolder;

import java.math.BigDecimal;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class M_Picking_Job_Schedule_StepDef
{
	@NonNull private final PickingJobScheduleService pickingJobScheduleService = SpringContextHolder.instance.getBean(PickingJobScheduleService.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	// @NonNull private final M_Picking_Job_Schedule_StepDefData jobScheduleTable;
	@NonNull private final M_ShipmentSchedule_StepDefData shipmentScheduleTable;
	@NonNull private final C_Workplace_StepDefData workplaceTable;

	@And("^create or update picking job schedules$")
	public void createOrUpdate(final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_M_Picking_Job_Schedule.COLUMNNAME_M_Picking_Job_Schedule_ID)
				.forEach(this::createOrUpdate);
	}

	private void createOrUpdate(final DataTableRow row)
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

	private void delete(final DataTableRow row)
	{
		final ShipmentScheduleId shipmentScheduleId = row.getAsIdentifier(I_M_Picking_Job_Schedule.COLUMNNAME_M_ShipmentSchedule_ID).lookupNotNullIdIn(shipmentScheduleTable);
		final ImmutableSet<PickingJobScheduleId> jobScheduleIds = pickingJobScheduleService.stream(PickingJobScheduleQuery.builder()
						.onlyShipmentScheduleId(shipmentScheduleId)
						.build())
				.map(PickingJobSchedule::getId)
				.collect(ImmutableSet.toImmutableSet());
		
		pickingJobScheduleService.deleteJobSchedulesById(jobScheduleIds);
	}

	@And("^after not more than (.*)s, picking job schedules are found:$")
	public void findPickingJobSchedules(final int timeoutSec, @NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.forEach(row -> findPickingJob(timeoutSec, row));
	}

	private void findPickingJob(final int timeoutSec, @NonNull final DataTableRow tableRow) throws InterruptedException
	{
		final StepDefDataIdentifier shipmentScheduleIdentifier = tableRow.getAsIdentifier(I_M_Picking_Job_Schedule.COLUMNNAME_M_ShipmentSchedule_ID);
		final I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleIdentifier.lookupNotNullIn(shipmentScheduleTable);
		final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID());

		final StepDefDataIdentifier workplaceIdentifier = tableRow.getAsIdentifier(I_M_Picking_Job_Schedule.COLUMNNAME_C_Workplace_ID);
		final Workplace workplace = workplaceIdentifier.lookupNotNullIn(workplaceTable);
		final WorkplaceId workplaceId = workplace.getId();

		final boolean isProcessed = tableRow.getAsOptionalBoolean(I_M_Picking_Job_Schedule.COLUMNNAME_Processed).orElse(false);
		final BigDecimal qtyToPick = tableRow.getAsBigDecimal(I_M_Picking_Job_Schedule.COLUMNNAME_QtyToPick);

		final Supplier<Boolean> isPickingJobScheduleFound = () -> queryBL.createQueryBuilder(I_M_Picking_Job_Schedule.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Job_Schedule.COLUMNNAME_M_ShipmentSchedule_ID, shipmentScheduleId)
				.addEqualsFilter(I_M_Picking_Job_Schedule.COLUMNNAME_C_Workplace_ID, workplaceId)
				.addEqualsFilter(I_M_Picking_Job_Schedule.COLUMNNAME_Processed, isProcessed)
				.addEqualsFilter(I_M_Picking_Job_Schedule.COLUMNNAME_QtyToPick, qtyToPick)
				.create()
				.firstOnlyOptional(I_M_Picking_Job_Schedule.class)
				.isPresent();

		StepDefUtil.tryAndWait(timeoutSec, 500, isPickingJobScheduleFound);
	}
}
