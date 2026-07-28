/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.cucumber.stepdefs.distributionorder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Locator_StepDefData;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.shipmentschedule.M_ShipmentSchedule_StepDefData;
import de.metas.event.model.I_AD_EventLog;
import de.metas.event.model.I_AD_EventLog_Entry;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.X_DD_Order;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.distribution.ddorder.replenishment.alloc.DDOrderLineContributorRepository;
import de.metas.distribution.ddorder.replenishment.DDOrderPickingReplenishmentService;
import de.metas.distribution.ddorder.replenishment.event.DDOrderReplenishmentEventHandler;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Step definitions for the DD_Order picking-replenishment flow (see {@code DDOrderPickingReplenishmentService}) —
 * schedule-quantity change / picker-busy rejection, direct {@code reconcile}/{@code rebuildDrift} driving,
 * running the {@code DD_Order_Picking_Rebuild} process, and inspecting the reconcile
 * {@code AD_EventLog}/{@code AD_EventLog_Entry}/{@code AD_Issue} outcomes.
 */
@RequiredArgsConstructor
public class DDOrderPickingReplenishment_StepDef
{
	private static final Logger logger = LogManager.getLogger(DDOrderPickingReplenishment_StepDef.class);

	/** Topic / event name the replenishment flow publishes to (see {@code DDOrderReplenishmentEventPublisher}). */
	private static final String REPLENISHMENT_EVENT_NAME = "DDOrderPickingReconcile";

	/** Value of the AD_Process row backing the {@code DD_Order_Picking_Rebuild} JavaProcess. */
	private static final String PROCESS_VALUE_DDOrderPickingRebuild = "DD_Order_Picking_Rebuild";

	/** FQN of the replenishment event handler — stored as {@code AD_EventLog_Entry.Classname} by the event framework. */
	private static final String REPLENISHMENT_HANDLER_CLASSNAME = DDOrderReplenishmentEventHandler.class.getName();

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	@NonNull private final DDOrderPickingReplenishmentService replenishmentService = SpringContextHolder.instance.getBean(DDOrderPickingReplenishmentService.class);
	@NonNull private final DDOrderService ddOrderService = SpringContextHolder.instance.getBean(DDOrderService.class);
	@NonNull private final DDOrderLineContributorRepository contributorRepository = SpringContextHolder.instance.getBean(DDOrderLineContributorRepository.class);

	@NonNull private final M_ShipmentSchedule_StepDefData shipmentScheduleTable;
	@NonNull private final de.metas.cucumber.stepdefs.picking.M_Picking_Job_Schedule_StepDefData pickingJobScheduleTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_Locator_StepDefData locatorTable;
	@NonNull private final DD_Order_StepDefData ddOrderTable;
	@NonNull private final DD_OrderLine_StepDefData ddOrderLineTable;

	/**
	 * Directly invokes {@link DDOrderPickingReplenishmentService#reconcileGroupOf(PickingJobScheduleId)}, serving
	 * every contributor of the named assignment's product group.
	 *
	 * <p>Param: the identifier (from {@code M_Picking_Job_Schedule_StepDefData}) of the assignment to reconcile.</p>
	 */
	@When("^the reconcile event for M_Picking_Job_Schedule (.*) is processed$")
	public void process_reconcile_event(@NonNull final String pickingJobScheduleIdentifier)
	{
		final PickingJobScheduleId jobScheduleId = pickingJobScheduleTable.getId(pickingJobScheduleIdentifier);
		trxManager.runInThreadInheritedTrx(() -> replenishmentService.reconcileGroupOf(jobScheduleId));
	}

	/**
	 * Directly invokes {@link DDOrderPickingReplenishmentService#reconcileGroupOf(PickingJobScheduleId)} and asserts it
	 * FAILS with {@code ErrorCode = DDOrderPickingReconcile_PickerBusy} while the picker is busy, leaving the DD_Order
	 * unchanged.
	 *
	 * <p>Param: the identifier (from {@code M_Picking_Job_Schedule_StepDefData}) of the assignment.</p>
	 */
	@Then("^processing the reconcile event for M_Picking_Job_Schedule (.*) is rejected$")
	public void process_reconcile_event_is_rejected(@NonNull final String pickingJobScheduleIdentifier)
	{
		final PickingJobScheduleId jobScheduleId = pickingJobScheduleTable.getId(pickingJobScheduleIdentifier);

		assertThatThrownBy(() -> trxManager.runInThreadInheritedTrx(() -> replenishmentService.reconcileGroupOf(jobScheduleId)))
				.as("Reconcile must be rejected while the picker is busy")
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> assertThat(((AdempiereException)ex).getErrorCode())
						.as("AdempiereException.ErrorCode")
						.isEqualTo("DDOrderPickingReconcile_PickerBusy"));
	}

	/**
	 * Legacy-column seam: writes {@code QtyInTransit=1} on every {@code DD_OrderLine} the given picking job schedule
	 * contributes to. See the quantity-carrying form below for what this seam is and is NOT good for.
	 *
	 * <p>Param: the identifier (from {@code M_Picking_Job_Schedule_StepDefData}) of the assignment. For a different
	 * quantity, use the {@code seed the legacy QtyInTransit column of &lt;qty&gt; on ...} form below.</p>
	 */
	@When("^seed the legacy QtyInTransit column on DD_Order linked to picking job schedule (.*)$")
	public void seed_legacy_qty_in_transit(@NonNull final String pickingJobScheduleIdentifier)
	{
		seed_legacy_qty_in_transit(BigDecimal.ONE.toPlainString(), pickingJobScheduleIdentifier);
	}

	/**
	 * Legacy-column seam for the re-plan refusal guard ({@code DDOrderPickingReplenishmentService#assertCanChange}),
	 * whose input is {@code DD_OrderLine.QtyInTransit + QtyDelivered}. Use when a scenario asserts the refusal message
	 * REPORTS the moved quantity — pick a value that can't be mistaken for a record-id digit (e.g. {@code 7.5}).
	 *
	 * <p><b>This is NOT the mover's state.</b> {@code QtyInTransit} and {@code QtyDelivered} are written by no
	 * production flow whatsoever — the mobile mover records his progress on {@code DD_Order_MoveSchedule.Status}
	 * ({@code NS}&nbsp;→&nbsp;{@code IP} on the pick, {@code CO} on the drop). Never use this seam to reach the
	 * "goods are on their way" state a disposal decision keys on: drive the real
	 * {@code pick from the DD_Order linked to picking job schedule:} step instead. It exists only to keep this
	 * legacy-column guard covered exactly as it behaves today.</p>
	 *
	 * <p>Params: the quantity to write into the column, and the identifier (from
	 * {@code M_Picking_Job_Schedule_StepDefData}) of the assignment whose contributed DD_OrderLines are written.</p>
	 */
	@When("^seed the legacy QtyInTransit column of (.*) on DD_Order linked to picking job schedule (.*)$")
	public void seed_legacy_qty_in_transit(@NonNull final String qtyInTransit, @NonNull final String pickingJobScheduleIdentifier)
	{
		for (final I_DD_OrderLine line : contributedDDOrderLines(pickingJobScheduleIdentifier))
		{
			line.setQtyInTransit(new BigDecimal(qtyInTransit.trim()));
			InterfaceWrapperHelper.save(line);
		}
	}

	/**
	 * The {@code QtyDelivered} twin of {@code seed the legacy QtyInTransit column of ...}: the other half of the re-plan
	 * refusal guard's {@code QtyInTransit + QtyDelivered} input, left with {@code QtyInTransit} at zero. The same
	 * warning applies — neither column is ever written in production, so this seam covers that guard's arithmetic and
	 * nothing else.
	 *
	 * <p>Params: the quantity to write into the column, and the identifier (from
	 * {@code M_Picking_Job_Schedule_StepDefData}) of the assignment whose contributed DD_OrderLines are written.</p>
	 */
	@When("^seed the legacy QtyDelivered column of (.*) on DD_Order linked to picking job schedule (.*)$")
	public void seed_legacy_qty_delivered(@NonNull final String qtyDelivered, @NonNull final String pickingJobScheduleIdentifier)
	{
		for (final I_DD_OrderLine line : contributedDDOrderLines(pickingJobScheduleIdentifier))
		{
			line.setQtyDelivered(new BigDecimal(qtyDelivered.trim()));
			InterfaceWrapperHelper.save(line);
		}
	}

	/** The DD_OrderLines the given assignment contributes to; empty is a setup error, so the callers never have to check. */
	private List<I_DD_OrderLine> contributedDDOrderLines(@NonNull final String pickingJobScheduleIdentifier)
	{
		final PickingJobScheduleId jobScheduleId = pickingJobScheduleTable.getId(pickingJobScheduleIdentifier);

		final ImmutableSet<DDOrderLineId> lineIds = contributorRepository.getLineIdsByPickingJobScheduleId(jobScheduleId);
		final List<I_DD_OrderLine> lines = lineIds.isEmpty()
				? ImmutableList.of()
				: queryBL.createQueryBuilder(I_DD_OrderLine.class)
				.addInArrayFilter(I_DD_OrderLine.COLUMNNAME_DD_OrderLine_ID, lineIds)
				.create()
				.list(I_DD_OrderLine.class);

		assertThat(lines)
				.as("DD_OrderLines for picking job schedule %s (must exist before seeding the legacy movement columns)", pickingJobScheduleIdentifier)
				.isNotEmpty();

		return lines;
	}

	/** Runs the drift-rebuild that the {@code DD_Order_Picking_Rebuild} {@code AD_Process} performs. */
	@When("the DD_Order_Picking_Rebuild process is run")
	public void run_rebuild_process()
	{
		trxManager.runInThreadInheritedTrx(replenishmentService::rebuildDrift);
	}

	/**
	 * Asserts the drift rebuild recognises each of the given workstation assignments as ALREADY SERVED.
	 *
	 * <p>Param: a comma-separated list of {@code M_Picking_Job_Schedule} identifiers.</p>
	 *
	 * @cucumber.example
	 * <pre>
	 * Then the drift rebuild considers jobScheduleA, jobScheduleB already served
	 * </pre>
	 */
	@Then("^the drift rebuild considers (.*) already served$")
	public void assert_assignments_considered_served(@NonNull final String pickingJobScheduleIdentifiers)
	{
		final ImmutableSet<PickingJobScheduleId> unserved = assignmentIdsNeedingDDOrder(pickingJobScheduleIdentifiers);

		for (final StepDefDataIdentifier identifier : StepDefUtil.extractIdentifiers(pickingJobScheduleIdentifiers))
		{
			final PickingJobScheduleId jobScheduleId = pickingJobScheduleTable.getId(identifier);
			assertThat(unserved)
					.as("M_Picking_Job_Schedule %s (M_Picking_Job_Schedule_ID=%s) must NOT be in the drift rebuild's unserved set",
							identifier, jobScheduleId.getRepoId())
					.doesNotContain(jobScheduleId);
		}
	}

	/**
	 * The counterpart of {@code the drift rebuild considers ... already served}: asserts each of the given
	 * assignments IS in the rebuild's unserved set, so a pass will re-plan it.
	 *
	 * <p>Param: a comma-separated list of {@code M_Picking_Job_Schedule} identifiers.</p>
	 *
	 * @cucumber.example
	 * <pre>
	 * Then the drift rebuild considers jobScheduleA, jobScheduleB to still need a DD_Order
	 * </pre>
	 */
	@Then("^the drift rebuild considers (.*) to still need a DD_Order$")
	public void assert_assignments_considered_unserved(@NonNull final String pickingJobScheduleIdentifiers)
	{
		final ImmutableSet<PickingJobScheduleId> unserved = assignmentIdsNeedingDDOrder(pickingJobScheduleIdentifiers);

		for (final StepDefDataIdentifier identifier : StepDefUtil.extractIdentifiers(pickingJobScheduleIdentifiers))
		{
			final PickingJobScheduleId jobScheduleId = pickingJobScheduleTable.getId(identifier);
			assertThat(unserved)
					.as("M_Picking_Job_Schedule %s (M_Picking_Job_Schedule_ID=%s) must be in the drift rebuild's unserved set",
							identifier, jobScheduleId.getRepoId())
					.contains(jobScheduleId);
		}
	}

	private ImmutableSet<PickingJobScheduleId> assignmentIdsNeedingDDOrder(@NonNull final String pickingJobScheduleIdentifiers)
	{
		final ImmutableSet<PickingJobScheduleId> jobScheduleIds = StepDefUtil.extractIdentifiers(pickingJobScheduleIdentifiers)
				.stream()
				.map(pickingJobScheduleTable::getId)
				.collect(ImmutableSet.toImmutableSet());

		return trxManager.callInThreadInheritedTrx(() -> replenishmentService.retainAssignmentsNeedingDDOrder(jobScheduleIds));
	}

	/** Asserts the {@code DD_Order_Picking_Rebuild} {@code AD_Process} is registered (its Value resolves to an {@code AdProcessId}). */
	@Then("the DD_Order_Picking_Rebuild process exists")
	public void rebuild_process_exists()
	{
		// retrieveProcessIdByValue throws if the process does not exist — so a non-null result is the assertion.
		final AdProcessId processId = adProcessDAO.retrieveProcessIdByValue(PROCESS_VALUE_DDOrderPickingRebuild);
		assertThat(processId)
				.as("AD_Process with Value=%s (DD_Order_Picking_Rebuild) must exist", PROCESS_VALUE_DDOrderPickingRebuild)
				.isNotNull();
	}

	/**
	 * Runs one {@code rebuildDrift} pass and asserts how many reconcile requests it published for the given
	 * assignments; the count is a per-pass delta, so the pass and its before/after counts belong to one step.
	 *
	 * <p>Params: the expected request count, then a comma-separated list of {@code M_Picking_Job_Schedule}
	 * identifiers the published requests are counted over.</p>
	 *
	 * <p>Example:</p>
	 * <pre>
	 * Then one DD_Order_Picking_Rebuild pass publishes exactly 1 reconcile request for M_Picking_Job_Schedules jobScheduleA, jobScheduleB
	 * </pre>
	 */
	@Then("^one DD_Order_Picking_Rebuild pass publishes exactly (\\d+) reconcile requests? for M_Picking_Job_Schedules (.*)$")
	public void assert_reconcile_requests_published_by_one_pass(
			final int expectedRequestCount,
			@NonNull final String pickingJobScheduleIdentifiers)
	{
		final ImmutableSet<Integer> assignmentIds = StepDefUtil.extractIdentifiers(pickingJobScheduleIdentifiers)
				.stream()
				.map(pickingJobScheduleTable::getId)
				.map(PickingJobScheduleId::getRepoId)
				.collect(ImmutableSet.toImmutableSet());

		// AD_EventLog is written synchronously by the publish (EventBus.enqueueEvent -> EventLogService.saveEvent),
		// so the delta across the pass is the published-request count and needs no polling.
		final IQuery<I_AD_EventLog> reconcileEvents = reconcileEventsTriggeredBy(assignmentIds);
		final int countBefore = reconcileEvents.count();

		trxManager.runInThreadInheritedTrx(replenishmentService::rebuildDrift);

		assertThat(reconcileEvents.count() - countBefore)
				.as("Reconcile requests published by one rebuild pass for M_Picking_Job_Schedule_IDs=%s", assignmentIds)
				.isEqualTo(expectedRequestCount);
	}

	private IQuery<I_AD_EventLog> reconcileEventsTriggeredBy(@NonNull final ImmutableSet<Integer> pickingJobScheduleIds)
	{
		return queryBL.createQueryBuilder(I_AD_EventLog.class)
				.addEqualsFilter(I_AD_EventLog.COLUMNNAME_EventName, REPLENISHMENT_EVENT_NAME)
				.addInArrayFilter(I_AD_EventLog.COLUMNNAME_Record_ID, pickingJobScheduleIds)
				.create();
	}

	/**
	 * Polls for an {@code AD_EventLog_Entry} produced by the reconcile event handler for a SPECIFIC
	 * shipment schedule, with the expected error state and (optionally) a message fragment.
	 *
	 * <p>Columns:</p>
	 * <ul>
	 *   <li>{@code M_ShipmentSchedule_ID} — identifier of the schedule that triggered the reconcile (required).</li>
	 *   <li>{@code IsError} — {@code true} for the Error outcome, {@code false} for the Done outcome (required).</li>
	 *   <li>{@code MsgText} — optional substring the entry's message must contain (case-insensitive like-filter).</li>
	 * </ul>
	 */
	@Then("^after not more than (.*)s, an AD_EventLog_Entry for the replenishment event handler is found:$")
	public void assert_reconcile_event_log_entry(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final boolean expectedError = row.getAsBoolean(I_AD_EventLog_Entry.COLUMNNAME_IsError);
			final String msgTextFragment = row.getAsOptionalString(I_AD_EventLog_Entry.COLUMNNAME_MsgText).orElse(null);

			// Pinned via the parent AD_EventLog's source record reference (assignment for the new flow, shipment schedule for the legacy one).
			final IQuery<I_AD_EventLog> eventLogsSubQuery = row.getAsOptionalIdentifier(de.metas.inoutcandidate.model.I_M_Picking_Job_Schedule.COLUMNNAME_M_Picking_Job_Schedule_ID)
					.map(identifier -> eventLogsForPickingJobSchedule(identifier.lookupNotNullIdIn(pickingJobScheduleTable).getRepoId()))
					.orElseGet(() -> {
						final int shipmentScheduleId = row.getAsIdentifier(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID).lookupNotNullIn(shipmentScheduleTable).getM_ShipmentSchedule_ID();
						return eventLogsForSchedule(shipmentScheduleId);
					});

			final Supplier<Boolean> entryFound = () -> {
				final IQueryBuilder<I_AD_EventLog_Entry> queryBuilder = queryBL.createQueryBuilder(I_AD_EventLog_Entry.class)
						.addEqualsFilter(I_AD_EventLog_Entry.COLUMNNAME_Classname, REPLENISHMENT_HANDLER_CLASSNAME)
						.addEqualsFilter(I_AD_EventLog_Entry.COLUMNNAME_IsError, expectedError);
				if (msgTextFragment != null)
				{
					queryBuilder.addStringLikeFilter(I_AD_EventLog_Entry.COLUMNNAME_MsgText, msgTextFragment, /*ignoreCase*/ true);
				}
				queryBuilder.addInSubQueryFilter(
						I_AD_EventLog_Entry.COLUMNNAME_AD_EventLog_ID,
						I_AD_EventLog.COLUMNNAME_AD_EventLog_ID,
						eventLogsSubQuery);
				return queryBuilder.create().anyMatch();
			};

			try
			{
				StepDefUtil.tryAndWait(timeoutSec, 1000, entryFound, this::logCurrentEventLogEntries);
			}
			catch (final InterruptedException e)
			{
				Thread.currentThread().interrupt();
				throw new RuntimeException(e);
			}
		});
	}

	/** Sub-query selecting the {@code AD_EventLog} records whose source record reference points to the given workstation assignment. */
	private IQuery<I_AD_EventLog> eventLogsForPickingJobSchedule(final int pickingJobScheduleId)
	{
		final TableRecordReference ref =
				TableRecordReference.of(de.metas.inoutcandidate.model.I_M_Picking_Job_Schedule.Table_Name, pickingJobScheduleId);
		return queryBL.createQueryBuilder(I_AD_EventLog.class)
				.addEqualsFilter(I_AD_EventLog.COLUMNNAME_AD_Table_ID, ref.getAD_Table_ID())
				.addEqualsFilter(I_AD_EventLog.COLUMNNAME_Record_ID, ref.getRecord_ID())
				.create();
	}

	/** Sub-query selecting the {@code AD_EventLog} records whose source record reference points to the given shipment schedule. */
	private IQuery<I_AD_EventLog> eventLogsForSchedule(final int shipmentScheduleId)
	{
		final TableRecordReference scheduleRef =
				TableRecordReference.of(I_M_ShipmentSchedule.Table_Name, shipmentScheduleId);
		return queryBL.createQueryBuilder(I_AD_EventLog.class)
				.addEqualsFilter(I_AD_EventLog.COLUMNNAME_AD_Table_ID, scheduleRef.getAD_Table_ID())
				.addEqualsFilter(I_AD_EventLog.COLUMNNAME_Record_ID, scheduleRef.getRecord_ID())
				.create();
	}

	/**
	 * Polls for an Error {@code AD_EventLog_Entry} from the reconcile handler that has an {@code AD_Issue} attached,
	 * pinned to a SPECIFIC workstation assignment.
	 *
	 * <p>Param: the identifier (from {@code M_Picking_Job_Schedule_StepDefData}) of the assignment that triggered
	 * the reconcile (required).</p>
	 *
	 * <p>Example:</p>
	 * <pre>
	 * And after not more than 10s, an AD_Issue is logged for the replenishment network gap of M_Picking_Job_Schedule jobSchedule
	 * </pre>
	 */
	@Then("^after not more than (.*)s, an AD_Issue is logged for the replenishment network gap of M_Picking_Job_Schedule (.*)$")
	public void assert_reconcile_AD_Issue_logged(final int timeoutSec, @NonNull final String pickingJobScheduleIdentifier) throws InterruptedException
	{
		final int pickingJobScheduleId = pickingJobScheduleTable.getId(pickingJobScheduleIdentifier).getRepoId();

		final Supplier<Boolean> issueLogged = () -> queryBL.createQueryBuilder(I_AD_EventLog_Entry.class)
				.addEqualsFilter(I_AD_EventLog_Entry.COLUMNNAME_Classname, REPLENISHMENT_HANDLER_CLASSNAME)
				.addEqualsFilter(I_AD_EventLog_Entry.COLUMNNAME_IsError, true)
				.addNotNull(I_AD_EventLog_Entry.COLUMNNAME_AD_Issue_ID)
				.addInSubQueryFilter(
						I_AD_EventLog_Entry.COLUMNNAME_AD_EventLog_ID,
						I_AD_EventLog.COLUMNNAME_AD_EventLog_ID,
						eventLogsForPickingJobSchedule(pickingJobScheduleId))
				.create()
				.anyMatch();

		StepDefUtil.tryAndWait(timeoutSec, 1000, issueLogged, this::logCurrentEventLogEntries);
	}

	/**
	 * @cucumber.stepdef Polls until the COMPLETED DD_Orders of the product group source from EXACTLY the given
	 * locators, each line carrying the given quantity — the group's summed demand as the stock-aware split left it.
	 * @cucumber.columns
	 *   <b>M_Product_ID</b> — (required, identifier-ref) the group's product<br>
	 *   <b>M_LocatorTo_ID</b> — (required, identifier-ref) the group's target locator<br>
	 *   <b>M_Locator_ID</b> — (required, identifier-ref) the source locator this row's line sources from<br>
	 *   <b>QtyEntered</b> — (required) the quantity allocated to that source locator<br>
	 *   <b>DD_OrderLine_ID</b> — (optional) stores the matched line under this identifier<br>
	 * @cucumber.depends StepDefData: M_Product_StepDefData, M_Locator_StepDefData, DD_OrderLine_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then after not more than 120s, the product group's completed DD_Orders source from:
	 *   | M_Product_ID | M_LocatorTo_ID | M_Locator_ID | QtyEntered | DD_OrderLine_ID |
	 *   | product      | packingLocator | locatorA     | 8          | lineFromA       |
	 *   | product      | packingLocator | locatorB     | 4          | lineFromB       |
	 * </pre>
	 */
	@Then("^after not more than (.*)s, the product group's completed DD_Orders source from:$")
	public void assert_completed_DDOrders_of_product_group(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<DataTableRow> rows = DataTableRows.of(dataTable).toList();
		final DataTableRow firstRow = rows.get(0);
		final ProductId productId = firstRow.getAsIdentifier(I_DD_OrderLine.COLUMNNAME_M_Product_ID).lookupNotNullIdIn(productTable);
		final LocatorId locatorToId = firstRow.getAsIdentifier(I_DD_OrderLine.COLUMNNAME_M_LocatorTo_ID).lookupNotNullIdIn(locatorTable);

		final LinkedHashMap<Integer, BigDecimal> expectedQtyBySourceLocatorId = new LinkedHashMap<>();
		for (final DataTableRow row : rows)
		{
			expectedQtyBySourceLocatorId.put(sourceLocatorRepoId(row), row.getAsBigDecimal(I_DD_OrderLine.COLUMNNAME_QtyEntered));
		}

		StepDefUtil.tryAndWait(
				timeoutSec,
				1000,
				() -> matchesExpectedSplit(expectedQtyBySourceLocatorId, completedGroupLinesBySourceLocatorId(productId, locatorToId)),
				() -> logCurrentGroupLines(productId, locatorToId));

		final Map<Integer, List<I_DD_OrderLine>> lines = completedGroupLinesBySourceLocatorId(productId, locatorToId);
		for (final DataTableRow row : rows)
		{
			final I_DD_OrderLine line = lines.get(sourceLocatorRepoId(row)).get(0);
			row.getAsOptionalIdentifier(I_DD_OrderLine.COLUMNNAME_DD_OrderLine_ID)
					.ifPresent(identifier -> ddOrderLineTable.putOrReplace(identifier, line));
		}
	}

	private int sourceLocatorRepoId(@NonNull final DataTableRow row)
	{
		return row.getAsIdentifier(I_DD_OrderLine.COLUMNNAME_M_Locator_ID).lookupNotNullIdIn(locatorTable).getRepoId();
	}

	/** Stateless, so the poll can re-evaluate it on every pass. */
	private static boolean matchesExpectedSplit(
			@NonNull final Map<Integer, BigDecimal> expectedQtyBySourceLocatorId,
			@NonNull final Map<Integer, List<I_DD_OrderLine>> actualLinesBySourceLocatorId)
	{
		if (!actualLinesBySourceLocatorId.keySet().equals(expectedQtyBySourceLocatorId.keySet()))
		{
			return false;
		}

		for (final Map.Entry<Integer, BigDecimal> expected : expectedQtyBySourceLocatorId.entrySet())
		{
			final List<I_DD_OrderLine> lines = actualLinesBySourceLocatorId.get(expected.getKey());
			if (lines.size() != 1 || lines.get(0).getQtyEntered().compareTo(expected.getValue()) != 0)
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Keyed by source locator, with the lines as a LIST: an in-flight reconcile can transiently leave two completed
	 * orders on one locator, and the poll above must see that as "not settled yet" rather than fail.
	 */
	private Map<Integer, List<I_DD_OrderLine>> completedGroupLinesBySourceLocatorId(
			@NonNull final ProductId productId,
			@NonNull final LocatorId locatorToId)
	{
		final IQuery<I_DD_Order> completedDDOrders = queryBL.createQueryBuilder(I_DD_Order.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DD_Order.COLUMNNAME_DocStatus, X_DD_Order.DOCSTATUS_Completed)
				.addEqualsFilter(I_DD_Order.COLUMNNAME_IsPickingDisconnected, false)
				.create();

		final LinkedHashMap<Integer, List<I_DD_OrderLine>> linesBySourceLocatorId = new LinkedHashMap<>();
		queryBL.createQueryBuilder(I_DD_OrderLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DD_OrderLine.COLUMNNAME_M_Product_ID, productId)
				.addEqualsFilter(I_DD_OrderLine.COLUMNNAME_M_LocatorTo_ID, locatorToId)
				.addInSubQueryFilter(I_DD_OrderLine.COLUMNNAME_DD_Order_ID, I_DD_Order.COLUMNNAME_DD_Order_ID, completedDDOrders)
				.orderBy(I_DD_OrderLine.COLUMNNAME_DD_OrderLine_ID)
				.create()
				.stream(I_DD_OrderLine.class)
				.forEach(line -> linesBySourceLocatorId
						.computeIfAbsent(line.getM_Locator_ID(), sourceLocatorId -> new ArrayList<>())
						.add(line));

		return linesBySourceLocatorId;
	}

	private void logCurrentGroupLines(@NonNull final ProductId productId, @NonNull final LocatorId locatorToId)
	{
		final StringBuilder sb = new StringBuilder("Current completed DD_OrderLines of the product group:\n");
		completedGroupLinesBySourceLocatorId(productId, locatorToId)
				.forEach((sourceLocatorId, lines) -> lines.forEach(line -> sb
						.append(" source M_Locator_ID=").append(sourceLocatorId)
						.append(" DD_Order_ID=").append(line.getDD_Order_ID())
						.append(" DD_OrderLine_ID=").append(line.getDD_OrderLine_ID())
						.append(" QtyEntered=").append(line.getQtyEntered()).append("\n")));
		logger.error("*** Waiting for the product group's completed DD_Orders, current context:\n{}", sb);
	}

	/**
	 * Closes the shared DD_Order exactly as the mover's mobile completion does ({@code DistributionRestService.complete}
	 * → {@code DDOrderService.close}). Called directly because this scenario needs the give-up close on a quantity
	 * deliberately left unmoved, which no mobile workflow session here has picked against.
	 */
	@When("^the mover gives up the remaining quantity and completes the distribution job of DD_Order (.*)$")
	public void close_DDOrder_short(@NonNull final String ddOrderIdentifier)
	{
		final DDOrderId ddOrderId = ddOrderTable.getId(ddOrderIdentifier);
		trxManager.runInThreadInheritedTrx(() -> ddOrderService.close(ddOrderId));
	}

	private void logCurrentEventLogEntries()
	{
		final StringBuilder sb = new StringBuilder("Current AD_EventLog_Entry records for the reconcile handler:\n");
		queryBL.createQueryBuilder(I_AD_EventLog_Entry.class)
				.create()
				.stream(I_AD_EventLog_Entry.class)
				.forEach(entry -> sb.append(" Classname=").append(entry.getClassname())
						.append(" IsError=").append(entry.isError())
						.append(" Processed=").append(entry.isProcessed())
						.append(" AD_Issue_ID=").append(entry.getAD_Issue_ID())
						.append(" MsgText=").append(entry.getMsgText()).append("\n"));
		logger.error("*** Waiting for AD_EventLog_Entry, current context:\n{}", sb);
	}

}
