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

import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.order.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.order.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.shipmentschedule.M_ShipmentSchedule_StepDefData;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.event.model.I_AD_EventLog_Entry;
import de.metas.handlingunits.model.I_M_Picking_Job;
import de.metas.handlingunits.model.I_M_Picking_Job_Line;
import de.metas.handlingunits.picking.dd_order.reconcile.DDOrderPickingReconcileBL;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.user.UserId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.X_DD_Order;
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
public class DDOrderPickingReconcile_StepDef
{
	private static final Logger logger = LogManager.getLogger(DDOrderPickingReconcile_StepDef.class);

	/** Topic / event name the reconcile flow publishes to (see {@code DDOrderReconciliationEventPublisher}). */
	private static final String RECONCILE_EVENT_NAME = "DDOrderPickingReconcile";

	/** Value of the AD_Process row backing the {@code DD_Order_Picking_Rebuild} JavaProcess. */
	private static final String PROCESS_VALUE_DDOrderPickingRebuild = "DD_Order_Picking_Rebuild";

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);

	@NonNull private final M_ShipmentSchedule_StepDefData shipmentScheduleTable;
	@NonNull private final C_Order_StepDefData orderTable;
	@NonNull private final C_OrderLine_StepDefData orderLineTable;
	@NonNull private final C_BPartner_StepDefData bpartnerTable;
	@NonNull private final C_BPartner_Location_StepDefData bpartnerLocationTable;
	@NonNull private final M_Product_StepDefData productTable;

	/**
	 * Deactivates (cancels) a shipment schedule, mirroring a sales-order line cancellation.
	 * The save fires {@code M_ShipmentSchedule.afterSave}, which publishes a reconcile event whose consumer
	 * voids the live DD_Order (no new one is created — see REQUIREMENTS.md TC3).
	 *
	 * <p>Column: {@code M_ShipmentSchedule_ID} — identifier of the schedule to deactivate.</p>
	 */
	@When("^the M_ShipmentSchedule identified by (.*) is deactivated$")
	public void deactivate_M_ShipmentSchedule(@NonNull final String shipmentScheduleIdentifier)
	{
		final I_M_ShipmentSchedule schedule = shipmentScheduleTable.get(shipmentScheduleIdentifier);
		schedule.setIsActive(false);
		InterfaceWrapperHelper.saveRecord(schedule);
	}

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
				.forEach(row -> {
					final I_M_ShipmentSchedule schedule = shipmentScheduleTable.get(row.getAsIdentifier().getAsString());
					final BigDecimal newQty = row.getAsBigDecimal(I_M_ShipmentSchedule.COLUMNNAME_QtyOrdered_Override);
					schedule.setQtyOrdered_Override(newQty);
					InterfaceWrapperHelper.saveRecord(schedule);
				});
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
	 * Creates a minimal but valid {@code M_Picking_Job} + {@code M_Picking_Job_Line} linked to a shipment schedule,
	 * making the picker "busy" on that schedule's DD_Order (the busy-check matches on {@code M_ShipmentSchedule_ID}).
	 *
	 * <p>Required columns:</p>
	 * <ul>
	 *   <li>{@code M_ShipmentSchedule_ID} — schedule the picking-job line references (identifier).</li>
	 *   <li>{@code C_OrderLine_ID} — the sales-order line (identifier); provides C_Order/BPartner context.</li>
	 *   <li>{@code M_Product_ID} — product (identifier).</li>
	 *   <li>{@code QtyToPick} — quantity to pick.</li>
	 *   <li>{@code C_UOM_ID} — unit of measure repo id (int).</li>
	 * </ul>
	 */
	@And("metasfresh contains M_Picking_Job_Line:")
	public void create_M_Picking_Job_Line(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::create_M_Picking_Job_Line);
	}

	private void create_M_Picking_Job_Line(@NonNull final DataTableRow row)
	{
		final I_M_ShipmentSchedule schedule = shipmentScheduleTable.get(row.getAsIdentifier(I_M_Picking_Job_Line.COLUMNNAME_M_ShipmentSchedule_ID).getAsString());
		final I_C_OrderLine orderLine = orderLineTable.get(row.getAsIdentifier(I_M_Picking_Job_Line.COLUMNNAME_C_OrderLine_ID).getAsString());
		final I_C_Order order = InterfaceWrapperHelper.load(orderLine.getC_Order_ID(), I_C_Order.class);
		final I_M_Product product = productTable.get(row.getAsIdentifier(I_M_Picking_Job_Line.COLUMNNAME_M_Product_ID).getAsString());
		final BigDecimal qtyToPick = row.getAsBigDecimal(I_M_Picking_Job_Line.COLUMNNAME_QtyToPick);
		final int uomId = row.getAsInt(I_M_Picking_Job_Line.COLUMNNAME_C_UOM_ID);

		final int bpartnerId = order.getC_BPartner_ID();
		final int bpartnerLocationId = order.getC_BPartner_Location_ID();

		final I_M_Picking_Job pickingJob = InterfaceWrapperHelper.newInstance(I_M_Picking_Job.class);
		pickingJob.setC_BPartner_ID(bpartnerId);
		pickingJob.setC_BPartner_Location_ID(bpartnerLocationId);
		pickingJob.setC_Order_ID(order.getC_Order_ID());
		pickingJob.setDeliveryToAddress("cucumber-picking-job");
		pickingJob.setDocStatus(X_DD_Order.DOCSTATUS_Drafted);
		pickingJob.setPicking_User_ID(UserId.METASFRESH.getRepoId());
		pickingJob.setPreparationDate(TimeUtil.asTimestamp(Env.getDate(Env.getCtx())));
		pickingJob.setDeliveryDate(TimeUtil.asTimestamp(Env.getDate(Env.getCtx())));
		InterfaceWrapperHelper.saveRecord(pickingJob);

		final I_M_Picking_Job_Line line = InterfaceWrapperHelper.newInstance(I_M_Picking_Job_Line.class);
		line.setM_Picking_Job_ID(pickingJob.getM_Picking_Job_ID());
		line.setM_Product_ID(product.getM_Product_ID());
		line.setC_Order_ID(order.getC_Order_ID());
		line.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
		line.setC_BPartner_ID(bpartnerId);
		line.setC_BPartner_Location_ID(bpartnerLocationId);
		line.setQtyToPick(qtyToPick);
		line.setC_UOM_ID(uomId);
		line.setM_ShipmentSchedule_ID(schedule.getM_ShipmentSchedule_ID());
		InterfaceWrapperHelper.saveRecord(line);
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
				});
	}

	/**
	 * Directly invokes {@code DDOrderPickingReconcileBL.reconcile(scheduleId)} in a new transaction
	 * (mirrors what the async event consumer does — see {@code DDOrderReconciliationEventHandler}).
	 * Used for the controlled-timing race scenario (REQUIREMENTS.md TC5) so the test is deterministic.
	 *
	 * <p>Column: {@code M_ShipmentSchedule_ID} — identifier of the schedule to reconcile.</p>
	 */
	@When("^the reconcile event for M_ShipmentSchedule (.*) is processed$")
	public void process_reconcile_event(@NonNull final String shipmentScheduleIdentifier)
	{
		final I_M_ShipmentSchedule schedule = shipmentScheduleTable.get(shipmentScheduleIdentifier);
		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID());
		final DDOrderPickingReconcileBL reconcileBL = SpringContextHolder.instance.getBean(DDOrderPickingReconcileBL.class);
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
		final DDOrderPickingReconcileBL reconcileBL = SpringContextHolder.instance.getBean(DDOrderPickingReconcileBL.class);

		assertThatThrownBy(() -> trxManager.runInNewTrx(() -> reconcileBL.reconcile(scheduleId)))
				.as("Reconcile must be rejected while the picker is busy")
				.isInstanceOf(AdempiereException.class)
				// PickerBusy AD_Message resolves in the system base language (de_DE): "... die Kommissionierung läuft bereits ...".
				.hasMessageContaining("Kommissionierung läuft bereits");
	}

	/**
	 * Voids the single live DD_Order linked to a schedule directly via the document engine, WITHOUT going through
	 * the reconcile flow — simulating the "DD_Order was never created / got lost between commit and publish" state
	 * that the drift watchdog is designed to heal (REQUIREMENTS.md §3.5 / TC7).
	 *
	 * <p>Column: {@code M_ShipmentSchedule_ID} — identifier of the schedule whose DD_Order is voided.</p>
	 */
	@When("^the DD_Order linked to M_ShipmentSchedule (.*) is voided directly$")
	public void void_DD_Order_directly(@NonNull final String shipmentScheduleIdentifier)
	{
		final I_M_ShipmentSchedule schedule = shipmentScheduleTable.get(shipmentScheduleIdentifier);

		final I_DD_Order liveDDOrder = queryBL.createQueryBuilder(I_DD_Order.class)
				.addEqualsFilter(I_DD_Order.COLUMNNAME_M_ShipmentSchedule_ID, schedule.getM_ShipmentSchedule_ID())
				.addNotEqualsFilter(I_DD_Order.COLUMNNAME_DocStatus, X_DD_Order.DOCSTATUS_Voided)
				.create()
				.firstOnlyNotNull(I_DD_Order.class);

		documentBL.processEx(liveDDOrder, IDocument.ACTION_Void, IDocument.STATUS_Voided);
	}

	/**
	 * Runs the {@code DD_Order_Picking_Rebuild} drift-watchdog by invoking the BL the JavaProcess delegates to
	 * ({@code DDOrderPickingReconcileBL.rebuildDrift()}). Used for the manual-rebuild scenario (REQUIREMENTS.md TC7).
	 *
	 * <p>The JavaProcess itself ({@code DD_Order_Picking_Rebuild}) is a thin glue shell that only calls
	 * {@code rebuildDrift()}; driving the BL directly keeps the cucumber test deterministic and avoids the
	 * process-engine plumbing while exercising the exact same code path.</p>
	 */
	@When("the DD_Order_Picking_Rebuild process is run")
	public void run_rebuild_process()
	{
		final DDOrderPickingReconcileBL reconcileBL = SpringContextHolder.instance.getBean(DDOrderPickingReconcileBL.class);
		trxManager.runInNewTrx(reconcileBL::rebuildDrift);
	}

	/**
	 * Asserts that the {@code AD_Process} backing the {@code DD_Order_Picking_Rebuild} JavaProcess exists (used by
	 * the hourly {@code AD_Scheduler}). For REQUIREMENTS.md TC8 — the scheduler engine cannot be driven directly in
	 * cucumber, so we assert the process the scheduler points at exists and that running it (the rebuild) self-heals
	 * drift (asserted by the subsequent DD_Order assertion in the scenario).
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
	 * Polls for the (single live) DD_Order linked to a schedule and asserts it is Voided
	 * (REQUIREMENTS.md TC3 — deactivate → void only).
	 *
	 * <p>Column: {@code M_ShipmentSchedule_ID} — identifier of the schedule.</p>
	 */
	@Then("^after not more than (.*)s, the DD_Order linked to M_ShipmentSchedule (.*) is Voided$")
	public void assert_DD_Order_voided(final int timeoutSec, @NonNull final String shipmentScheduleIdentifier) throws InterruptedException
	{
		final I_M_ShipmentSchedule schedule = shipmentScheduleTable.get(shipmentScheduleIdentifier);
		final int scheduleId = schedule.getM_ShipmentSchedule_ID();

		final Supplier<Boolean> isVoided = () -> {
			final boolean liveExists = queryBL.createQueryBuilder(I_DD_Order.class)
					.addEqualsFilter(I_DD_Order.COLUMNNAME_M_ShipmentSchedule_ID, scheduleId)
					.addNotEqualsFilter(I_DD_Order.COLUMNNAME_DocStatus, X_DD_Order.DOCSTATUS_Voided)
					.create()
					.anyMatch();

			final boolean voidedExists = queryBL.createQueryBuilder(I_DD_Order.class)
					.addEqualsFilter(I_DD_Order.COLUMNNAME_M_ShipmentSchedule_ID, scheduleId)
					.addEqualsFilter(I_DD_Order.COLUMNNAME_DocStatus, X_DD_Order.DOCSTATUS_Voided)
					.create()
					.anyMatch();

			// A voided DD_Order exists and there is no live one remaining for the schedule.
			return voidedExists && !liveExists;
		};

		StepDefUtil.tryAndWait(timeoutSec, 1000, isVoided, () -> logCurrentDDOrders(scheduleId));
	}

	/**
	 * Asserts no live (non-voided) DD_Order exists for the schedule (REQUIREMENTS.md TC6/TC9 — nothing created).
	 *
	 * <p>Column: {@code M_ShipmentSchedule_ID} — identifier of the schedule.</p>
	 */
	@Then("^there is no live DD_Order for M_ShipmentSchedule (.*)$")
	public void assert_no_live_DD_Order(@NonNull final String shipmentScheduleIdentifier)
	{
		final I_M_ShipmentSchedule schedule = shipmentScheduleTable.get(shipmentScheduleIdentifier);

		final boolean liveExists = queryBL.createQueryBuilder(I_DD_Order.class)
				.addEqualsFilter(I_DD_Order.COLUMNNAME_M_ShipmentSchedule_ID, schedule.getM_ShipmentSchedule_ID())
				.addNotEqualsFilter(I_DD_Order.COLUMNNAME_DocStatus, X_DD_Order.DOCSTATUS_Voided)
				.create()
				.anyMatch();

		assertThat(liveExists)
				.as("No live DD_Order must exist for M_ShipmentSchedule %s", shipmentScheduleIdentifier)
				.isFalse();
	}

	/**
	 * Asserts no DD_Order at all (live or voided) is linked to any schedule of the given order — i.e. the new
	 * reconcile flow did not fire (REQUIREMENTS.md TC9 — non-packing warehouse untouched by the new flow).
	 *
	 * <p>Column: identifier of the C_Order.</p>
	 */
	@Then("^there is no reconcile DD_Order for the C_Order (.*)$")
	public void assert_no_reconcile_DD_Order_for_order(@NonNull final String orderIdentifier)
	{
		final I_C_Order order = orderTable.get(orderIdentifier);

		// All schedules of the order; assert none has a reconcile DD_Order (M_ShipmentSchedule_ID linkage).
		queryBL.createQueryBuilder(I_M_ShipmentSchedule.class)
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_C_Order_ID, order.getC_Order_ID())
				.create()
				.listIds()
				.forEach(scheduleId -> {
					final boolean ddOrderExists = queryBL.createQueryBuilder(I_DD_Order.class)
							.addEqualsFilter(I_DD_Order.COLUMNNAME_M_ShipmentSchedule_ID, scheduleId)
							.create()
							.anyMatch();
					assertThat(ddOrderExists)
							.as("No reconcile DD_Order must exist for schedule %s of order %s (non-packing warehouse)", scheduleId, orderIdentifier)
							.isFalse();
				});
	}

	/** FQN of the reconcile event handler — stored as {@code AD_EventLog_Entry.Classname} by the event framework. */
	private static final String RECONCILE_HANDLER_CLASSNAME =
			"de.metas.handlingunits.picking.dd_order.reconcile.event.DDOrderReconciliationEventHandler";

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
	@Then("^after not more than (.*)s, an AD_EventLog_Entry for the reconcile handler is found:$")
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
	@Then("^after not more than (.*)s, an AD_Issue is logged for the reconcile network gap$")
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

	private void logCurrentDDOrders(final int scheduleId)
	{
		final StringBuilder sb = new StringBuilder("DD_Orders linked to M_ShipmentSchedule_ID=").append(scheduleId).append(":\n");
		queryBL.createQueryBuilder(I_DD_Order.class)
				.addEqualsFilter(I_DD_Order.COLUMNNAME_M_ShipmentSchedule_ID, scheduleId)
				.create()
				.stream(I_DD_Order.class)
				.forEach(ddOrder -> sb.append(" DD_Order_ID=").append(ddOrder.getDD_Order_ID())
						.append(" DocStatus=").append(ddOrder.getDocStatus()).append("\n"));
		logger.error("*** Waiting for DD_Order to be Voided, current context:\n{}", sb);
	}
}
