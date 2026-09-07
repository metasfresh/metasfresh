package de.metas.cucumber.stepdefs.picking;

import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.distributionorder.DD_Order_StepDefData;
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
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.eevolution.model.I_DD_Order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.Set;
import java.util.function.Supplier;
import java.util.regex.Pattern;

/**
 * Step definitions for {@code M_Picking_Job_Schedule} — workstation assignment lifecycle (create/update, quantity
 * change incl. picker-busy rejection, delete incl. refused-disposal, and polling for persisted records).
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
	/** Only needed to resolve the {@code Blocking_DD_Order_ID} column of the refusal-message assertion. */
	@NonNull private final DD_Order_StepDefData ddOrderTable;

	/**
	 * @cucumber.stepdef Creates or updates a {@code M_Picking_Job_Schedule} workstation assignment for the given
	 * shipment schedule and workplace, triggering the {@code afterNew}/{@code afterChange} interceptor.
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
	 * @cucumber.stepdef Changes an existing workstation assignment's {@code QtyToPick} in place, keyed by the
	 * assignment's stored identifier, triggering the {@code M_Picking_Job_Schedule} interceptor's picker-busy
	 * guard and after-commit reconcile.
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
	 * @cucumber.stepdef Re-assigns an existing workstation assignment to another workplace in place, keeping its
	 * {@code QtyToPick}, triggering the {@code M_Picking_Job_Schedule} interceptor's picker-busy guard and
	 * after-commit reconcile.
	 * @cucumber.columns
	 *   <b>M_Picking_Job_Schedule_ID</b> — (required, identifier-ref) the existing assignment to re-assign<br>
	 *   <b>C_Workplace_ID</b> — (required, identifier-ref) the workplace to move it to<br>
	 * @cucumber.depends StepDefData: M_Picking_Job_Schedule_StepDefData, C_Workplace_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When the picking job schedule workplace is changed:
	 *   | M_Picking_Job_Schedule_ID | C_Workplace_ID |
	 *   | jobScheduleA              | workplaceB     |
	 * </pre>
	 */
	@And("^the picking job schedule workplace is changed:$")
	public void changeWorkplace(final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::changeWorkplace);
	}

	private void changeWorkplace(final DataTableRow row)
	{
		final PickingJobSchedule jobSchedule = row.getAsIdentifier(I_M_Picking_Job_Schedule.COLUMNNAME_M_Picking_Job_Schedule_ID).lookupNotNullIn(jobScheduleTable);
		final WorkplaceId newWorkplaceId = row.getAsIdentifier(I_M_Picking_Job_Schedule.COLUMNNAME_C_Workplace_ID).lookupNotNullIdIn(workplaceTable);

		// Passing the existing PickingJobScheduleId takes createOrUpdate's UPDATE branch, so the workplace changes in place.
		pickingJobScheduleService.createOrUpdate(
				CreateOrUpdatePickingJobSchedulesRequest.builder()
						.shipmentScheduleAndJobScheduleIds(ShipmentScheduleAndJobScheduleIdSet.of(jobSchedule.getShipmentScheduleAndJobScheduleId()))
						.workplaceId(newWorkplaceId)
						.build());
	}

	/**
	 * @cucumber.stepdef Attempts to change an existing assignment's {@code QtyToPick} and asserts the
	 * {@code beforeChange} interceptor REJECTS the save, leaving the assignment unchanged.
	 * <p>
	 * Required columns:
	 * <ul>
	 *   <li>{@code M_Picking_Job_Schedule_ID} — identifier of the existing assignment</li>
	 *   <li>{@code QtyToPick} — the attempted new quantity</li>
	 *   <li>{@code ErrorCode} — expected {@code AdempiereException} error code (e.g. {@code DDOrderPickingReconcile_PickerBusy})</li>
	 * </ul>
	 * Optional columns — each asserts that its resolved value appears in the rendered (translated) exception message:
	 * <ul>
	 *   <li>{@code Blocking_M_Picking_Job_Schedule_ID} — identifier of the assignment named as blocking</li>
	 *   <li>{@code Blocking_M_ShipmentSchedule_ID} — identifier of that assignment's shipment schedule</li>
	 *   <li>{@code Blocking_DD_Order_ID} — identifier of the distribution order the refusal is about</li>
	 *   <li>{@code Blocking_QtyMoved} — the quantity already in transit or delivered</li>
	 * </ul>
	 * @cucumber.depends StepDefData: M_Picking_Job_Schedule_StepDefData, M_ShipmentSchedule_StepDefData,
	 * DD_Order_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then changing the picking job schedule quantity is rejected:
	 *   | M_Picking_Job_Schedule_ID | QtyToPick | ErrorCode                          | Blocking_M_Picking_Job_Schedule_ID | Blocking_M_ShipmentSchedule_ID | Blocking_DD_Order_ID |
	 *   | jobScheduleA              | 8         | DDOrderPickingReconcile_PickerBusy | jobScheduleB                       | shipmentScheduleB              | groupDDOrder         |
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
						.isEqualTo(expectedErrorCode))
				.satisfies(ex -> assertBlockingWorkIsNamed(row, ex.getLocalizedMessage()));

		assertThat(pickingJobScheduleService.getById(jobScheduleId).getQtyToPick().toBigDecimal())
				.as("M_Picking_Job_Schedule.QtyToPick must be unchanged after the rejected save")
				.isEqualByComparingTo(originalQty);
	}

	private static final String COLUMN_Blocking_QtyMoved = "Blocking_QtyMoved";
	private static final String COLUMN_Blocking_M_Picking_Job_Schedule_ID = "Blocking_" + I_M_Picking_Job_Schedule.COLUMNNAME_M_Picking_Job_Schedule_ID;
	private static final String COLUMN_Blocking_M_ShipmentSchedule_ID = "Blocking_" + I_M_Picking_Job_Schedule.COLUMNNAME_M_ShipmentSchedule_ID;
	private static final String COLUMN_Blocking_DD_Order_ID = "Blocking_" + I_DD_Order.COLUMNNAME_DD_Order_ID;

	private static final ImmutableSet<String> KNOWN_Blocking_COLUMNS = ImmutableSet.of(
			COLUMN_Blocking_M_Picking_Job_Schedule_ID,
			COLUMN_Blocking_M_ShipmentSchedule_ID,
			COLUMN_Blocking_DD_Order_ID,
			COLUMN_Blocking_QtyMoved);

	/** Asserts the rendered refusal message names each piece of blocking work the row asks for. */
	private void assertBlockingWorkIsNamed(@NonNull final DataTableRow row, @NonNull final String message)
	{
		final Set<String> unknownBlockingColumns = row.asMap().keySet().stream()
				.filter(columnName -> columnName.startsWith("Blocking_"))
				.filter(columnName -> !KNOWN_Blocking_COLUMNS.contains(columnName))
				.collect(ImmutableSet.toImmutableSet());
		assertThat(unknownBlockingColumns)
				.as("Unknown Blocking_* column(s) — nothing would assert them; known columns are %s", KNOWN_Blocking_COLUMNS)
				.isEmpty();

		row.getAsOptionalIdentifier(COLUMN_Blocking_M_Picking_Job_Schedule_ID)
				.map(identifier -> identifier.lookupNotNullIn(jobScheduleTable).getId().getRepoId())
				.ifPresent(blockingJobScheduleId -> assertMessageNames(
						message, String.valueOf(blockingJobScheduleId), "the blocking M_Picking_Job_Schedule_ID"));

		row.getAsOptionalIdentifier(COLUMN_Blocking_M_ShipmentSchedule_ID)
				.map(identifier -> identifier.lookupNotNullIdIn(shipmentScheduleTable).getRepoId())
				.ifPresent(blockingShipmentScheduleId -> assertMessageNames(
						message, String.valueOf(blockingShipmentScheduleId), "the blocking M_ShipmentSchedule_ID"));

		row.getAsOptionalIdentifier(COLUMN_Blocking_DD_Order_ID)
				.map(identifier -> identifier.lookupNotNullIn(ddOrderTable).getDD_Order_ID())
				.ifPresent(ddOrderId -> assertMessageNames(
						message, String.valueOf(ddOrderId), "the DD_Order the refusal is about"));

		row.getAsOptionalBigDecimal(COLUMN_Blocking_QtyMoved)
				.ifPresent(qtyMoved -> assertMessageNames(
						message, qtyMoved.stripTrailingZeros().toPlainString(), "the quantity already moved"));
	}

	/** Asserts {@code expected} occurs in the message as a standalone number, not as a digit run inside a longer one. */
	private static void assertMessageNames(@NonNull final String message, @NonNull final String expected, @NonNull final String what)
	{
		assertThat(message)
				.as("The refusal must name %s (%s)", what, expected)
				.containsPattern("(?<![0-9.])" + Pattern.quote(expected) + "(?![0-9])");
	}

	/** Updates in place: passing the existing {@link PickingJobScheduleId} into {@code createOrUpdate} takes the UPDATE branch instead of creating a second assignment. */
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
	 * @cucumber.stepdef Deletes all {@code M_Picking_Job_Schedule} records for the given shipment schedule,
	 * triggering the {@code afterDelete} interceptor which synchronously voids and unlinks any live DD_Orders.
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

		pickingJobScheduleService.deleteJobSchedulesById(jobScheduleIdsOf(shipmentScheduleId));
	}

	/**
	 * @cucumber.stepdef Attempts to delete the given shipment schedule's workstation assignments and asserts the
	 * {@code afterDelete} disposal REFUSES it, leaving the assignments unchanged.
	 * @cucumber.columns
	 *   <b>M_ShipmentSchedule_ID</b> — (required, identifier-ref) the shipment schedule whose assignments to delete<br>
	 *   <b>ErrorCode</b> — (required) expected {@code AdempiereException} error code, e.g.
	 *   {@code DDOrderPickingReconcile_PickerBusy}<br>
	 *   <b>Blocking_M_Picking_Job_Schedule_ID</b>, <b>Blocking_M_ShipmentSchedule_ID</b>,
	 *   <b>Blocking_DD_Order_ID</b>, <b>Blocking_QtyMoved</b> — (optional) the blocking work the refusal must NAME,
	 *   asserted exactly as documented on {@code changing the picking job schedule quantity is rejected}<br>
	 * @cucumber.depends StepDefData: M_Picking_Job_Schedule_StepDefData, M_ShipmentSchedule_StepDefData,
	 * DD_Order_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then deleting the picking job schedules is rejected:
	 *   | M_ShipmentSchedule_ID | ErrorCode                          | Blocking_M_Picking_Job_Schedule_ID | Blocking_M_ShipmentSchedule_ID | Blocking_DD_Order_ID |
	 *   | shipmentScheduleB     | DDOrderPickingReconcile_PickerBusy | jobScheduleB                       | shipmentScheduleB              | groupDDOrder         |
	 * </pre>
	 */
	@And("^deleting the picking job schedules is rejected:$")
	public void deleteIsRejected(final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::deleteIsRejected);
	}

	private void deleteIsRejected(final DataTableRow row)
	{
		final ShipmentScheduleId shipmentScheduleId = row.getAsIdentifier(I_M_Picking_Job_Schedule.COLUMNNAME_M_ShipmentSchedule_ID).lookupNotNullIdIn(shipmentScheduleTable);
		final ImmutableSet<PickingJobScheduleId> jobScheduleIds = jobScheduleIdsOf(shipmentScheduleId);
		assertThat(jobScheduleIds)
				.as("There must be at least one assignment to attempt deleting for M_ShipmentSchedule_ID=%s", shipmentScheduleId)
				.isNotEmpty();

		final String expectedErrorCode = row.getAsString("ErrorCode");

		assertThatThrownBy(() -> pickingJobScheduleService.deleteJobSchedulesById(jobScheduleIds))
				.as("Deleting the assignment must be refused while its replenishment is being picked")
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> assertThat(((AdempiereException)ex).getErrorCode())
						.as("AdempiereException.ErrorCode")
						.isEqualTo(expectedErrorCode))
				.satisfies(ex -> assertBlockingWorkIsNamed(row, ex.getLocalizedMessage()));

		assertThat(jobScheduleIdsOf(shipmentScheduleId))
				.as("The assignments must still exist after the rejected delete")
				.containsExactlyInAnyOrderElementsOf(jobScheduleIds);
	}

	private ImmutableSet<PickingJobScheduleId> jobScheduleIdsOf(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return pickingJobScheduleService.stream(PickingJobScheduleQuery.builder()
						.onlyShipmentScheduleId(shipmentScheduleId)
						.build())
				.map(PickingJobSchedule::getId)
				.collect(ImmutableSet.toImmutableSet());
	}

	/**
	 * @cucumber.stepdef Deactivates an existing workstation assignment ({@code IsActive=N}), keyed by the assignment's
	 * stored identifier, triggering the {@code beforeChange}/{@code afterChange} interceptor (not the
	 * {@code afterDelete} path of {@code delete picking job schedules}).
	 * @cucumber.columns
	 *   <b>M_Picking_Job_Schedule_ID</b> — (required, identifier-ref) the existing assignment to deactivate<br>
	 * @cucumber.depends StepDefData: M_Picking_Job_Schedule_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When the picking job schedule is deactivated:
	 *   | M_Picking_Job_Schedule_ID |
	 *   | jobScheduleA              |
	 * </pre>
	 */
	@And("^the picking job schedule is deactivated:$")
	public void deactivate(final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::deactivate);
	}

	private void deactivate(final DataTableRow row)
	{
		final PickingJobScheduleId jobScheduleId = row.getAsIdentifier(I_M_Picking_Job_Schedule.COLUMNNAME_M_Picking_Job_Schedule_ID)
				.lookupNotNullIn(jobScheduleTable)
				.getId();

		// Written through the record directly: the repository's updateRecord(...) copies only business columns, so it wouldn't deactivate.
		final I_M_Picking_Job_Schedule record = InterfaceWrapperHelper.load(jobScheduleId, I_M_Picking_Job_Schedule.class);
		record.setIsActive(false);
		InterfaceWrapperHelper.saveRecord(record);
	}

	/**
	 * @cucumber.stepdef Polls until a {@code M_Picking_Job_Schedule} with the given attributes is found, asserting
	 * it was persisted (e.g. after the interceptor fires asynchronously).
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
