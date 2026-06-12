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

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.shipmentschedule.M_ShipmentSchedule_StepDefData;
import de.metas.event.model.I_AD_EventLog;
import de.metas.event.model.I_AD_EventLog_Entry;
import org.adempiere.model.InterfaceWrapperHelper;
import org.eevolution.model.I_DD_OrderLine;
import de.metas.distribution.ddorder.replenishment.DDOrderPickingReplenishmentService;
import de.metas.distribution.ddorder.replenishment.event.DDOrderReplenishmentEventHandler;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
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
import java.util.List;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Step definitions specific to the DD_Order picking-replenishment flow
 * (see {@code DDOrderPickingReplenishmentService}).
 *
 * <p>Covers the reconcile-only assertions that have no home in a single domain step-def class:
 * changing a schedule quantity (and asserting the picker-busy rejection), directly driving the service
 * ({@code reconcile} / {@code rebuildDrift}) for deterministic race / watchdog scenarios, running the
 * {@code DD_Order_Picking_Rebuild} process, and inspecting the reconcile
 * {@code AD_EventLog} / {@code AD_EventLog_Entry} / {@code AD_Issue} outcomes.</p>
 *
 * <p>The picker is made busy / released through the REAL mobile picking workflow (REST: start the
 * wfProcess + scan the picking slot to create an in-progress {@code M_Picking_Job_Line}, then abort the
 * wfProcess to void it) — see the {@code DDOrderReplenishment_picker_busy.feature} for the end-to-end flow.</p>
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

	@NonNull private final M_ShipmentSchedule_StepDefData shipmentScheduleTable;
	@NonNull private final de.metas.cucumber.stepdefs.picking.M_Picking_Job_Schedule_StepDefData pickingJobScheduleTable;

	/**
	 * Directly invokes {@link DDOrderPickingReplenishmentService#reconcile(PickingJobScheduleId)} in
	 * {@code runInThreadInheritedTrx}, matching the transaction wrapping used by
	 * {@code DDOrderReplenishmentEventHandler}.
	 *
	 * <p>Real-world trigger: in production this reconcile runs asynchronously when the
	 * {@code M_Picking_Job_Schedule} interceptor publishes the after-commit {@code DDOrderPickingReconcile}
	 * event (on a new / changed / deleted assignment), or when the {@code DD_Order_Picking_Rebuild} watchdog
	 * reposts it. The step calls the service directly only to control ordering for the deterministic race
	 * scenario — driving it through the real async bus would make the picker-grabs-the-job-in-the-race-window
	 * timing non-deterministic and the test flaky.</p>
	 *
	 * <p>Param: the identifier (from {@code M_Picking_Job_Schedule_StepDefData}) of the assignment to reconcile.</p>
	 */
	@When("^the reconcile event for M_Picking_Job_Schedule (.*) is processed$")
	public void process_reconcile_event(@NonNull final String pickingJobScheduleIdentifier)
	{
		final PickingJobScheduleId jobScheduleId = pickingJobScheduleTable.getId(pickingJobScheduleIdentifier);
		trxManager.runInThreadInheritedTrx(() -> replenishmentService.reconcile(jobScheduleId));
	}

	/**
	 * Directly invokes {@link DDOrderPickingReplenishmentService#reconcile(PickingJobScheduleId)} and asserts it
	 * FAILS while the picker is busy (the service-side definitive guard). Asserts the thrown exception is an
	 * {@link AdempiereException} with {@code ErrorCode = DDOrderPickingReconcile_PickerBusy}.
	 * The DD_Order is left unchanged.
	 *
	 * <p>Real-world trigger: same async reconcile as {@code process_reconcile_event} — in production the
	 * {@code M_Picking_Job_Schedule} interceptor ({@code M_Picking_Job_Schedule_DDOrderPickingInterceptor#scheduleReconcileAfterCommit})
	 * publishes the after-commit reconcile event, the {@code DDOrderReplenishmentEventHandler} picks it up and calls
	 * {@code replenishmentService.reconcile(jobScheduleId)}; this step asserts the service-side picker-busy guard rejects
	 * that reconcile when a picker has grabbed the job in the meantime. The step calls the service directly only to
	 * control ordering for the deterministic race scenario — driving it through the real async bus would make the
	 * picker-grabs-the-job-in-the-race-window timing non-deterministic and the test flaky.</p>
	 *
	 * <p>Note: this step drives the service directly (not via the async event handler) so no
	 * {@code AD_EventLog_Entry} is produced. The handler-level error-recording path (IsError=true in
	 * AD_EventLog_Entry) is covered by the scenario that goes through the real async event flow.</p>
	 *
	 * <p>Param: the identifier (from {@code M_Picking_Job_Schedule_StepDefData}) of the assignment.</p>
	 */
	@Then("^processing the reconcile event for M_Picking_Job_Schedule (.*) is rejected$")
	public void process_reconcile_event_is_rejected(@NonNull final String pickingJobScheduleIdentifier)
	{
		final PickingJobScheduleId jobScheduleId = pickingJobScheduleTable.getId(pickingJobScheduleIdentifier);

		assertThatThrownBy(() -> trxManager.runInThreadInheritedTrx(() -> replenishmentService.reconcile(jobScheduleId)))
				.as("Reconcile must be rejected while the picker is busy")
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> assertThat(((AdempiereException)ex).getErrorCode())
						.as("AdempiereException.ErrorCode")
						.isEqualTo("DDOrderPickingReconcile_PickerBusy"));
	}

	/**
	 * Test seam: directly sets {@code QtyInTransit=1} on every {@code DD_OrderLine} linked to the given
	 * picking job schedule, simulating the state after a movement document has been dispatched from the
	 * DD_Order (goods are in transit from the source warehouse toward the target warehouse) without running
	 * the full movement-processing flow. Used to exercise the movement-started guard in
	 * {@code DDOrderPickingReplenishmentService.assertCanChange}.
	 *
	 * <p>Param: the identifier (from {@code M_Picking_Job_Schedule_StepDefData}) of the assignment whose
	 * DD_OrderLines should be marked in transit.</p>
	 */
	@When("^simulate goods in transit on DD_Order linked to picking job schedule (.*)$")
	public void simulate_goods_in_transit(@NonNull final String pickingJobScheduleIdentifier)
	{
		final PickingJobScheduleId jobScheduleId = pickingJobScheduleTable.getId(pickingJobScheduleIdentifier);

		final List<I_DD_OrderLine> lines = queryBL.createQueryBuilder(I_DD_OrderLine.class)
				.addEqualsFilter(I_DD_OrderLine.COLUMNNAME_M_Picking_Job_Schedule_ID, jobScheduleId)
				.create()
				.list(I_DD_OrderLine.class);

		assertThat(lines)
				.as("DD_OrderLines for picking job schedule %s (must exist before simulating goods-in-transit)", pickingJobScheduleIdentifier)
				.isNotEmpty();

		for (final I_DD_OrderLine line : lines)
		{
			line.setQtyInTransit(BigDecimal.ONE);
			InterfaceWrapperHelper.save(line);
		}
	}

	/**
	 * Runs the drift-rebuild that the {@code DD_Order_Picking_Rebuild} {@code AD_Process} performs.
	 *
	 * <p>Real-world trigger: a user (or a scheduler) runs the {@code DD_Order_Picking_Rebuild} process from the
	 * application; it republishes a reconcile event for every schedule that has drifted from its DD_Order. The
	 * step invokes {@code rebuildDrift} directly to keep the watchdog scenario deterministic.</p>
	 */
	@When("the DD_Order_Picking_Rebuild process is run")
	public void run_rebuild_process()
	{
		trxManager.runInThreadInheritedTrx(replenishmentService::rebuildDrift);
	}

	/**
	 * Asserts the {@code DD_Order_Picking_Rebuild} {@code AD_Process} is registered (its Value resolves to an
	 * {@code AdProcessId}).
	 *
	 * <p>Real-world trigger: this is the {@code AD_Process} backing the {@code DD_Order_Picking_Rebuild}
	 * {@code JavaProcess} that a warehouse supervisor runs manually from the WebUI (or a scheduler runs
	 * periodically) to re-reconcile schedules that have drifted from their DD_Order; its {@code doIt} calls
	 * {@code replenishmentService.rebuildDrift()}. The step guards against the process row going missing (a
	 * deleted/renamed AD_Process record would silently disable the watchdog in production).</p>
	 */
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
	 * Polls for an {@code AD_EventLog_Entry} produced by the reconcile event handler for a SPECIFIC
	 * shipment schedule, with the expected error state and (optionally) a message fragment
	 * (network gap ends the event in Error; also covers the Done outcome of the watchdog rebuild).
	 *
	 * <p>The entry is tied to its originating schedule via the parent {@code AD_EventLog}'s source record
	 * reference ({@code AD_Table_ID}=M_ShipmentSchedule + {@code Record_ID}=the schedule), which the
	 * {@code DDOrderReplenishmentEventPublisher} now sets. This prevents matching a stale entry left behind
	 * by a previous scenario (false-green isolation bug).</p>
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

			// The entry is pinned to its originating record via the parent AD_EventLog's source record reference
			// (set by DDOrderReplenishmentEventPublisher). The trigger record is the workstation assignment
			// (M_Picking_Job_Schedule) for the assignment-driven flow, or the shipment schedule for the legacy flow.
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

	/**
	 * Builds the sub-query selecting the {@code AD_EventLog} records whose source record reference points to the
	 * given workstation assignment (set by {@code DDOrderReplenishmentEventPublisher}).
	 */
	private IQuery<I_AD_EventLog> eventLogsForPickingJobSchedule(final int pickingJobScheduleId)
	{
		final TableRecordReference ref =
				TableRecordReference.of(de.metas.inoutcandidate.model.I_M_Picking_Job_Schedule.Table_Name, pickingJobScheduleId);
		return queryBL.createQueryBuilder(I_AD_EventLog.class)
				.addEqualsFilter(I_AD_EventLog.COLUMNNAME_AD_Table_ID, ref.getAD_Table_ID())
				.addEqualsFilter(I_AD_EventLog.COLUMNNAME_Record_ID, ref.getRecord_ID())
				.create();
	}

	/**
	 * Builds the sub-query selecting the {@code AD_EventLog} records whose source record reference points to the
	 * given shipment schedule (set by {@code DDOrderReplenishmentEventPublisher}).
	 */
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
	 * Polls for an Error {@code AD_EventLog_Entry} from the reconcile handler that has an {@code AD_Issue} attached
	 * (network-gap soft-fail logs an AD_Issue), pinned to a SPECIFIC workstation assignment.
	 *
	 * <p>The entry is tied to its originating assignment via the parent {@code AD_EventLog}'s source record
	 * reference ({@code AD_Table_ID}=M_Picking_Job_Schedule + {@code Record_ID}=the assignment), which the
	 * {@code DDOrderReplenishmentEventPublisher} sets. This prevents matching a stale Error+AD_Issue entry left
	 * behind by a previous scenario on a multi-scenario DB run (false-green isolation bug).</p>
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
