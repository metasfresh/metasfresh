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
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.function.Supplier;

/**
 * Step definitions for {@code M_Picking_Job_Schedule} — workstation assignment lifecycle.
 *
 * <p>Covers creating/updating assignments, changing or attempting to change quantities (including
 * the picker-busy rejection path), deleting assignments, and polling for persisted assignment records.</p>
 *
 * @see de.metas.handlingunits.picking.job_schedule.service.PickingJobScheduleService
 */
@RequiredArgsConstructor
public class M_Picking_Job_Schedule_StepDef
{
	@NonNull private final PickingJobScheduleService pickingJobScheduleService = SpringContextHolder.instance.getBean(PickingJobScheduleService.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final M_Picking_Job_Schedule_StepDefData jobScheduleTable;
	@NonNull private final M_ShipmentSchedule_StepDefData shipmentScheduleTable;
	@NonNull private final C_Workplace_StepDefData workplaceTable;

	/**
	 * @cucumber.stepdef Creates or updates a {@code M_Picking_Job_Schedule} workstation assignment for the given
	 * shipment schedule and workplace. If a matching assignment already exists it is updated in place; otherwise a new
	 * one is created. Triggers the {@code afterNew}/{@code afterChange} interceptor (async reconcile → DD_Order create
	 * or void+recreate).
	 * <p>
	 * Required columns:
	 * <ul>
	 *   <li>{@code M_ShipmentSchedule_ID} — identifier of the shipment schedule to assign</li>
	 *   <li>{@code C_Workplace_ID} — identifier of the target workplace</li>
	 *   <li>{@code QtyToPick} — quantity to pick at this workplace</li>
	 * </ul>
	 * @cucumber.example
	 * <pre>
	 * And create or update picking job schedules
	 *   | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
	 *   | shipmentSchedule      | workplace      | 5         |
	 * </pre>
	 */
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

		// Store the just-created/updated assignment under its row identifier so later steps can reference it.
		row.getAsOptionalIdentifier().ifPresent(identifier -> {
			final PickingJobSchedule jobSchedule = pickingJobScheduleService.stream(PickingJobScheduleQuery.builder()
							.onlyShipmentScheduleId(shipmentScheduleId)
							.workplaceId(workplaceId)
							.build())
					.findFirst()
					.orElseThrow(() -> new RuntimeException("No M_Picking_Job_Schedule found for shipmentScheduleId=" + shipmentScheduleId + ", workplaceId=" + workplaceId));
			jobScheduleTable.putOrReplace(identifier, jobSchedule);
		});
	}

	/**
	 * @cucumber.stepdef Changes an existing workstation assignment's {@code QtyToPick} in place (keyed by the
	 * assignment's stored identifier), so the {@code M_Picking_Job_Schedule} interceptor fires
	 * ({@code beforeChange} picker-busy guard + {@code afterChange} after-commit reconcile). On a packing
	 * warehouse with no busy picker the reconcile voids the old DD_Order and recreates a fresh one with the new
	 * quantity (single-locator RECREATE); {@code QtyToPick=0} downgrades to a VOID with no replacement.
	 * <p>
	 * Required columns:
	 * <ul>
	 *   <li>{@code M_Picking_Job_Schedule_ID} — identifier of the existing assignment to change</li>
	 *   <li>{@code QtyToPick} — the new quantity</li>
	 * </ul>
	 * @cucumber.example
	 * <pre>
	 * When the picking job schedule quantity is changed:
	 *   | M_Picking_Job_Schedule_ID | QtyToPick |
	 *   | jobSchedule               | 8         |
	 * </pre>
	 */
	@And("^the picking job schedule quantity is changed:$")
	public void changeQty(final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::changeQty);
	}

	private void changeQty(final DataTableRow row)
	{
		updateQtyInPlace(row.getAsIdentifier(I_M_Picking_Job_Schedule.COLUMNNAME_M_Picking_Job_Schedule_ID).lookupNotNullIn(jobScheduleTable),
				row.getAsBigDecimal(I_M_Picking_Job_Schedule.COLUMNNAME_QtyToPick));
	}

	/**
	 * @cucumber.stepdef Attempts to change an existing assignment's {@code QtyToPick} and asserts the
	 * {@code beforeChange} interceptor REJECTS the save. Asserts the thrown exception is an
	 * {@link org.adempiere.exceptions.AdempiereException} whose {@code ErrorCode} matches the expected value.
	 * The assignment is reloaded and asserted unchanged.
	 * <p>
	 * Required columns:
	 * <ul>
	 *   <li>{@code M_Picking_Job_Schedule_ID} — identifier of the existing assignment</li>
	 *   <li>{@code QtyToPick} — the attempted new quantity</li>
	 *   <li>{@code ErrorCode} — expected {@code AdempiereException} error code (e.g. {@code DDOrderPickingReconcile_PickerBusy})</li>
	 * </ul>
	 * @cucumber.example
	 * <pre>
	 * Then changing the picking job schedule quantity is rejected:
	 *   | M_Picking_Job_Schedule_ID | QtyToPick | ErrorCode                           |
	 *   | jobSchedule               | 8         | DDOrderPickingReconcile_PickerBusy  |
	 * </pre>
	 */
	@And("^changing the picking job schedule quantity is rejected:$")
	public void changeQtyIsRejected(final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::changeQtyIsRejected);
	}

	private void changeQtyIsRejected(final DataTableRow row)
	{
		final PickingJobSchedule jobSchedule = row.getAsIdentifier(I_M_Picking_Job_Schedule.COLUMNNAME_M_Picking_Job_Schedule_ID).lookupNotNullIn(jobScheduleTable);
		final PickingJobScheduleId jobScheduleId = jobSchedule.getId();
		final BigDecimal originalQty = pickingJobScheduleService.getById(jobScheduleId).getQtyToPick().toBigDecimal();
		final BigDecimal newQty = row.getAsBigDecimal(I_M_Picking_Job_Schedule.COLUMNNAME_QtyToPick);
		final String expectedErrorCode = row.getAsString("ErrorCode");

		assertThatThrownBy(() -> updateQtyInPlace(jobSchedule, newQty))
				.as("Changing the assignment must be rejected by the beforeChange interceptor")
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> assertThat(((AdempiereException)ex).getErrorCode())
						.as("AdempiereException.ErrorCode")
						.isEqualTo(expectedErrorCode));

		// Reload and assert the persisted QtyToPick is unchanged (the rolled-back save left no mark).
		assertThat(pickingJobScheduleService.getById(jobScheduleId).getQtyToPick().toBigDecimal())
				.as("M_Picking_Job_Schedule.QtyToPick must be unchanged after the rejected save")
				.isEqualByComparingTo(originalQty);
	}

	/**
	 * Updates an existing assignment's QtyToPick in place by passing its {@link PickingJobScheduleId} (not just
	 * the shipment-schedule id) into {@code createOrUpdate}, so the command takes the UPDATE branch
	 * ({@code updateByIds}) rather than creating a second assignment.
	 */
	private void updateQtyInPlace(@NonNull final PickingJobSchedule jobSchedule, @NonNull final BigDecimal newQtyToPick)
	{
		pickingJobScheduleService.createOrUpdate(
				CreateOrUpdatePickingJobSchedulesRequest.builder()
						.shipmentScheduleAndJobScheduleIds(ShipmentScheduleAndJobScheduleIdSet.of(jobSchedule.getShipmentScheduleAndJobScheduleId()))
						.workplaceId(jobSchedule.getWorkplaceId())
						.qtyToPickBD(newQtyToPick)
						.build());
	}

	/**
	 * @cucumber.stepdef Deletes all {@code M_Picking_Job_Schedule} records for the given shipment schedule. Triggers
	 * the {@code afterDelete} interceptor which synchronously voids and unlinks any live DD_Orders linked to the
	 * deleted assignments (satisfying the deferrable FK constraint within the same transaction).
	 * <p>
	 * Required columns:
	 * <ul>
	 *   <li>{@code M_ShipmentSchedule_ID} — identifier of the shipment schedule whose assignments to delete</li>
	 * </ul>
	 * @cucumber.example
	 * <pre>
	 * And delete picking job schedules
	 *   | M_ShipmentSchedule_ID |
	 *   | shipmentSchedule      |
	 * </pre>
	 */
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

	/**
	 * @cucumber.stepdef Polls until a {@code M_Picking_Job_Schedule} with the given attributes is found, or the
	 * timeout is exceeded. Used to assert that an assignment has been persisted (e.g. after the interceptor fires
	 * asynchronously).
	 * <p>
	 * Required columns:
	 * <ul>
	 *   <li>{@code M_ShipmentSchedule_ID} — identifier of the shipment schedule</li>
	 *   <li>{@code C_Workplace_ID} — identifier of the workplace</li>
	 *   <li>{@code QtyToPick} — expected quantity</li>
	 * </ul>
	 * Optional columns: {@code Processed}.
	 * @cucumber.example
	 * <pre>
	 * And after not more than 5s, picking job schedules are found:
	 *   | M_ShipmentSchedule_ID | C_Workplace_ID | QtyToPick |
	 *   | shipmentSchedule      | workplace      | 5         |
	 * </pre>
	 */
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
