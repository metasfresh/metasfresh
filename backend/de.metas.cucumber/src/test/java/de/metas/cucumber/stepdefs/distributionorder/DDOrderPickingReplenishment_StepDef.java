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

import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.shipmentschedule.M_ShipmentSchedule_StepDef;
import de.metas.cucumber.stepdefs.shipmentschedule.M_ShipmentSchedule_StepDefData;
import de.metas.event.model.I_AD_EventLog_Entry;
import de.metas.handlingunits.ddorder.replenishment.DDOrderPickingReplenishmentService;
import de.metas.handlingunits.ddorder.replenishment.event.DDOrderReplenishmentEventHandler;
import de.metas.handlingunits.model.I_M_Picking_Job;
import de.metas.handlingunits.model.I_M_Picking_Job_Line;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Step definitions specific to the DD_Order picking-reconcile flow
 * (see {@code DDOrderPickingReconcileBL}, REQUIREMENTS.md §3).
 *
 * <p>Covers the reconcile-only assertions that have no home in a single domain step-def class:
 * deactivating / re-quantifying a schedule, simulating a busy picker via a {@code M_Picking_Job_Line},
 * directly driving the BL ({@code reconcile} / {@code rebuildDrift}) for deterministic race / watchdog
 * scenarios, running the {@code DD_Order_Picking_Rebuild} process, and inspecting the reconcile
 * {@code AD_EventLog} / {@code AD_EventLog_Entry} / {@code AD_Issue} outcomes.</p>
 */
@RequiredArgsConstructor
public class DDOrderPickingReplenishment_StepDef
{
	private static final Logger logger = LogManager.getLogger(DDOrderPickingReplenishment_StepDef.class);

	/** Topic / event name the reconcile flow publishes to (see {@code DDOrderReplenishmentEventPublisher}). */
	private static final String RECONCILE_EVENT_NAME = "DDOrderPickingReconcile";

	/** Value of the AD_Process row backing the {@code DD_Order_Picking_Rebuild} JavaProcess. */
	private static final String PROCESS_VALUE_DDOrderPickingRebuild = "DD_Order_Picking_Rebuild";

	/** FQN of the replenishment event handler — stored as {@code AD_EventLog_Entry.Classname} by the event framework. */
	private static final String RECONCILE_HANDLER_CLASSNAME = DDOrderReplenishmentEventHandler.class.getName();

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	@NonNull private final M_ShipmentSchedule_StepDef shipmentScheduleStepDef;
	@NonNull private final M_ShipmentSchedule_StepDefData shipmentScheduleTable;

	/**
	 * Changes a shipment schedule's effective quantity by setting {@code QtyOrdered_Override} and saving.
	 * The save fires the {@code M_ShipmentSchedule} interceptor (sync picker-busy guard + after-commit reconcile),
	 * which on a packing warehouse with no busy picker voids the old DD_Order and recreates a fresh one with the
	 * new quantity (REQUIREMENTS.md TC2).
	 *
	 * <p>Columns: {@code M_ShipmentSchedule_ID} (identifier), {@code QtyOrdered_Override} (new quantity).</p>
	 */
	@When("the M_ShipmentSchedule quantity is changed:")
	public void change_M_ShipmentSchedule_qty(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID)
				.forEach(shipmentScheduleStepDef::alterShipmentSchedule);
	}

	/**
	 * Attempts to change a shipment schedule quantity and asserts the {@code beforeSave} interceptor REJECTS the
	 * save (picker-busy guard, REQUIREMENTS.md TC4). Asserts the thrown exception is an
	 * {@link AdempiereException} containing the word "picking" (the picker-busy AD_Message text).
	 * The schedule record is reloaded and asserted unchanged.
	 *
	 * <p>Columns: {@code M_ShipmentSchedule_ID} (identifier), {@code QtyOrdered_Override} (attempted quantity).</p>
	 */
	@Then("changing the M_ShipmentSchedule quantity is rejected:")
	public void change_M_ShipmentSchedule_qty_is_rejected(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID)
				.forEach(row -> {
					final I_M_ShipmentSchedule schedule = shipmentScheduleTable.get(row.getAsIdentifier().getAsString());
					final BigDecimal originalQtyOverride = schedule.getQtyOrdered_Override();
					final BigDecimal newQty = row.getAsBigDecimal(I_M_ShipmentSchedule.COLUMNNAME_QtyOrdered_Override);

					assertThatThrownBy(() -> {
								schedule.setQtyOrdered_Override(newQty);
								InterfaceWrapperHelper.saveRecord(schedule);
							})
							.as("Changing the schedule while the picker is busy must be rejected by the beforeSave interceptor")
							.isInstanceOf(AdempiereException.class)
							// PickerBusy AD_Message resolves in the system base language (de_DE): "... die Kommissionierung läuft bereits ...".
							.hasMessageContaining("Kommissionierung läuft bereits");

					// Reload and assert the persisted value is unchanged (the rolled-back save left no mark).
					final I_M_ShipmentSchedule reloaded = InterfaceWrapperHelper.load(schedule.getM_ShipmentSchedule_ID(), I_M_ShipmentSchedule.class);
					assertThat(reloaded.getQtyOrdered_Override())
							.as("M_ShipmentSchedule.QtyOrdered_Override must be unchanged after the rejected save")
							.isEqualByComparingTo(originalQtyOverride == null ? BigDecimal.ZERO : originalQtyOverride);
				});
	}

	/**
	 * Deactivates every {@code M_Picking_Job_Line} that references the given schedule, releasing the picker
	 * (so that a subsequent repost / reconcile can proceed — REQUIREMENTS.md TC5).
	 *
	 * <p>Column: {@code M_ShipmentSchedule_ID} — identifier of the schedule.</p>
	 */
	@When("^the M_Picking_Job_Line for M_ShipmentSchedule (.*) is removed$")
	public void remove_M_Picking_Job_Line(@NonNull final String shipmentScheduleIdentifier)
	{
		final I_M_ShipmentSchedule schedule = shipmentScheduleTable.get(shipmentScheduleIdentifier);
		queryBL.createQueryBuilder(I_M_Picking_Job_Line.class)
				.addEqualsFilter(I_M_Picking_Job_Line.COLUMNNAME_M_ShipmentSchedule_ID, schedule.getM_ShipmentSchedule_ID())
				.create()
				.list(I_M_Picking_Job_Line.class)
				.forEach(line -> {
					line.setIsActive(false);
					InterfaceWrapperHelper.saveRecord(line);

					// If the parent job has no more active lines, deactivate it too so it
					// doesn't appear in picking-workflow tests that query for active M_Picking_Job records.
					final boolean jobHasActiveLines = queryBL.createQueryBuilder(I_M_Picking_Job_Line.class)
							.addEqualsFilter(I_M_Picking_Job_Line.COLUMNNAME_M_Picking_Job_ID, line.getM_Picking_Job_ID())
							.addEqualsFilter(I_M_Picking_Job_Line.COLUMNNAME_IsActive, true)
							.create()
							.anyMatch();
					if (!jobHasActiveLines)
					{
						final I_M_Picking_Job job = InterfaceWrapperHelper.load(line.getM_Picking_Job_ID(), I_M_Picking_Job.class);
						job.setIsActive(false);
						InterfaceWrapperHelper.saveRecord(job);
					}
				});
	}

	/**
	 * Directly invokes {@code DDOrderPickingReconcileBL.reconcile(scheduleId)} in a new transaction
	 * (mirrors what the async event consumer does — see {@code DDOrderReplenishmentEventHandler}).
	 * Used for the controlled-timing race scenario (REQUIREMENTS.md TC5) so the test is deterministic.
	 *
	 * <p>Column: {@code M_ShipmentSchedule_ID} — identifier of the schedule to reconcile.</p>
	 */
	@When("^the reconcile event for M_ShipmentSchedule (.*) is processed$")
	public void process_reconcile_event(@NonNull final String shipmentScheduleIdentifier)
	{
		final I_M_ShipmentSchedule schedule = shipmentScheduleTable.get(shipmentScheduleIdentifier);
		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID());
		final DDOrderPickingReplenishmentService reconcileBL = SpringContextHolder.instance.getBean(DDOrderPickingReplenishmentService.class);
		trxManager.runInNewTrx(() -> reconcileBL.reconcile(scheduleId));
	}

	/**
	 * Directly invokes {@code DDOrderPickingReconcileBL.reconcile(scheduleId)} and asserts it FAILS while the
	 * picker is busy (the consumer-side definitive guard — REQUIREMENTS.md TC5). Asserts the thrown exception is
	 * an {@link AdempiereException} containing the word "picking" (the picker-busy AD_Message text).
	 * The DD_Order is left unchanged.
	 *
	 * <p>Note: this step drives the BL directly (not via the async event handler) so no {@code AD_EventLog_Entry}
	 * is produced. The handler-level error-recording path (IsError=true in AD_EventLog_Entry) is covered by TC6,
	 * which goes through the real async event flow.</p>
	 *
	 * <p>Column: {@code M_ShipmentSchedule_ID} — identifier of the schedule.</p>
	 */
	@Then("^processing the reconcile event for M_ShipmentSchedule (.*) is rejected$")
	public void process_reconcile_event_is_rejected(@NonNull final String shipmentScheduleIdentifier)
	{
		final I_M_ShipmentSchedule schedule = shipmentScheduleTable.get(shipmentScheduleIdentifier);
		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID());
		final DDOrderPickingReplenishmentService reconcileBL = SpringContextHolder.instance.getBean(DDOrderPickingReplenishmentService.class);

		assertThatThrownBy(() -> trxManager.runInNewTrx(() -> reconcileBL.reconcile(scheduleId)))
				.as("Reconcile must be rejected while the picker is busy")
				.isInstanceOf(AdempiereException.class)
				// PickerBusy AD_Message resolves in the system base language (de_DE): "... die Kommissionierung läuft bereits ...".
				.hasMessageContaining("Kommissionierung läuft bereits");
	}

	@When("the DD_Order_Picking_Rebuild process is run")
	public void run_rebuild_process()
	{
		final DDOrderPickingReplenishmentService reconcileBL = SpringContextHolder.instance.getBean(DDOrderPickingReplenishmentService.class);
		trxManager.runInNewTrx(reconcileBL::rebuildDrift);
	}

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
	 * Polls for an {@code AD_EventLog_Entry} produced by the reconcile event handler with the expected error state
	 * and (optionally) a message fragment (REQUIREMENTS.md TC6 — network gap ends the event in Error;
	 * also covers the Done outcome of the watchdog rebuild in TC7).
	 *
	 * <p>Columns:</p>
	 * <ul>
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

			final Supplier<Boolean> entryFound = () -> {
				final org.adempiere.ad.dao.IQueryBuilder<I_AD_EventLog_Entry> queryBuilder = queryBL.createQueryBuilder(I_AD_EventLog_Entry.class)
						.addEqualsFilter(I_AD_EventLog_Entry.COLUMNNAME_Classname, RECONCILE_HANDLER_CLASSNAME)
						.addEqualsFilter(I_AD_EventLog_Entry.COLUMNNAME_IsError, expectedError);
				if (msgTextFragment != null)
				{
					queryBuilder.addStringLikeFilter(I_AD_EventLog_Entry.COLUMNNAME_MsgText, msgTextFragment, /*ignoreCase*/ true);
				}
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
	 * Polls for an Error {@code AD_EventLog_Entry} from the reconcile handler that has an {@code AD_Issue} attached
	 * (REQUIREMENTS.md TC6 — the network-gap soft-fail logs an AD_Issue).
	 */
	@Then("^after not more than (.*)s, an AD_Issue is logged for the replenishment network gap$")
	public void assert_reconcile_AD_Issue_logged(final int timeoutSec) throws InterruptedException
	{
		final Supplier<Boolean> issueLogged = () -> queryBL.createQueryBuilder(I_AD_EventLog_Entry.class)
				.addEqualsFilter(I_AD_EventLog_Entry.COLUMNNAME_Classname, RECONCILE_HANDLER_CLASSNAME)
				.addEqualsFilter(I_AD_EventLog_Entry.COLUMNNAME_IsError, true)
				.addNotNull(I_AD_EventLog_Entry.COLUMNNAME_AD_Issue_ID)
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
