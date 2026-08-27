package de.metas.edi.api.impl;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.edi.api.EDIDesadvId;
import de.metas.edi.api.EDIExportStatus;
import de.metas.edi.api.impl.pack.EDIDesadvPackService;
import de.metas.edi.async.spi.impl.EDIWorkpackageProcessor;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.esb.edi.model.I_EDI_Desadv_M_InOut;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.organization.OrgId;
import de.metas.product.asidata.ProductASIDataRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import de.metas.edi.model.I_C_OrderLine;
import de.metas.edi.model.I_M_InOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

class DesadvBL_recomputeDesadvStatusFromInOuts_Test
{
	private DesadvBL desadvBL;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		// DesadvBL.isOneDesadvPerShipment reads this via the no-context sysConfigBL.getBooleanValue(name, default)
		// overload, which is scoped to ClientAndOrgId.SYSTEM (AD_Client_ID=0) regardless of the current test
		// client set up by AdempiereTestHelper — so the value must be written at ClientId.SYSTEM, not the test's
		// own client (and not ClientId.METASFRESH, which is a different, real client with repoId 1000000).
		Services.get(ISysConfigBL.class).setValue(
				EDIWorkpackageProcessor.SYS_CONFIG_OneDesadvPerShipment,
				true,
				ClientId.SYSTEM,
				OrgId.ANY);
		desadvBL = DesadvBL.newInstanceForUnitTesting();
	}

	private I_EDI_Desadv createDesadv(final String fulfillmentPercent, final String exportStatus)
	{
		final I_EDI_Desadv desadv = newInstance(I_EDI_Desadv.class);
		desadv.setFulfillmentPercent(new BigDecimal(fulfillmentPercent));
		desadv.setEDI_ExportStatus(exportStatus);
		saveRecord(desadv);
		return desadv;
	}

	/** @param qtyOrderedOverride {@code null} means the column stays SQL NULL — the case that must NOT be read as zero */
	private void createDesadvLine(
			final I_EDI_Desadv desadv,
			final String qtyOrdered,
			final String qtyOrderedOverride,
			final String qtyDeliveredInStockingUOM)
	{
		createDesadvLineRecord(desadv, qtyOrdered, qtyOrderedOverride, qtyDeliveredInStockingUOM);
	}

	/** Same as {@link #createDesadvLine(I_EDI_Desadv, String, String, String)} but returns the created record. */
	private I_EDI_DesadvLine createDesadvLineRecord(
			final I_EDI_Desadv desadv,
			final String qtyOrdered,
			final String qtyOrderedOverride,
			final String qtyDeliveredInStockingUOM)
	{
		final I_EDI_DesadvLine line = newInstance(I_EDI_DesadvLine.class);
		line.setEDI_Desadv_ID(desadv.getEDI_Desadv_ID());
		line.setQtyOrdered(new BigDecimal(qtyOrdered));
		line.setQtyOrdered_Override(qtyOrderedOverride == null ? null : new BigDecimal(qtyOrderedOverride));
		line.setQtyDeliveredInStockingUOM(new BigDecimal(qtyDeliveredInStockingUOM));
		saveRecord(line);
		return line;
	}

	private I_M_ShipmentSchedule createShipmentSchedule(
			final I_C_OrderLine orderLine,
			final String qtyDelivered,
			final boolean closed)
	{
		final I_M_ShipmentSchedule schedule = newInstance(I_M_ShipmentSchedule.class);
		schedule.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
		schedule.setQtyDelivered(new BigDecimal(qtyDelivered));
		schedule.setIsClosed(closed);
		saveRecord(schedule);
		return schedule;
	}

	/**
	 * An <b>open</b> {@code M_ShipmentSchedule} (IsClosed='N') that carries a {@code QtyOrdered_Override} —
	 * the state the WebUI leaves behind after a packing correction, see
	 * {@link #packingCorrectionLoweringTheOrderedQty_flipsTheDesadvToSent_althoughNothingWasClosed()}.
	 */
	private I_M_ShipmentSchedule createOpenShipmentScheduleWithQtyOrderedOverride(
			final I_C_OrderLine orderLine,
			final String qtyDelivered,
			final String qtyOrderedOverride)
	{
		final I_M_ShipmentSchedule schedule = createShipmentSchedule(orderLine, qtyDelivered, false);
		schedule.setQtyOrdered_Override(new BigDecimal(qtyOrderedOverride));
		saveRecord(schedule);
		return schedule;
	}

	private I_M_InOut createLinkedInOut(final I_EDI_Desadv desadv, final EDIExportStatus inOutStatus)
	{
		final I_M_InOut inOut = newInstance(I_M_InOut.class);
		inOut.setEDI_ExportStatus(inOutStatus.getCode());
		inOut.setEDI_Desadv_ID(desadv.getEDI_Desadv_ID());
		saveRecord(inOut);

		final I_EDI_Desadv_M_InOut junction = newInstance(I_EDI_Desadv_M_InOut.class);
		junction.setEDI_Desadv_ID(desadv.getEDI_Desadv_ID());
		junction.setM_InOut_ID(inOut.getM_InOut_ID());
		saveRecord(junction);
		return inOut;
	}

	@Test
	void allLinesDeliveryClosed_and_allInOutsSent_flipsToSent()
	{
		// under-delivery: ordered 100, the closed shipment schedule wrote QtyOrdered_Override=70, delivered 70
		final I_EDI_Desadv desadv = createDesadv("70", EDIExportStatus.Pending.getCode());
		createDesadvLine(desadv, "100", "70", "70");
		createLinkedInOut(desadv, EDIExportStatus.Sent);

		desadvBL.recomputeDesadvStatusFromInOuts(EDIDesadvId.ofRepoId(desadv.getEDI_Desadv_ID()));

		InterfaceWrapperHelper.refresh(desadv);
		assertThat(desadv.getEDI_ExportStatus()).isEqualTo(EDIExportStatus.Sent.getCode());
		assertThat(desadv.getEDIErrorMsg()).isNull();
	}

	@Test
	void oneLineStillOpen_staysPending()
	{
		final I_EDI_Desadv desadv = createDesadv("70", EDIExportStatus.Pending.getCode());
		createDesadvLine(desadv, "100", "70", "70");   // delivery-closed
		createDesadvLine(desadv, "100", null, "70");   // still open: no override, delivered 70 < ordered 100
		createLinkedInOut(desadv, EDIExportStatus.Sent);

		desadvBL.recomputeDesadvStatusFromInOuts(EDIDesadvId.ofRepoId(desadv.getEDI_Desadv_ID()));

		InterfaceWrapperHelper.refresh(desadv);
		assertThat(desadv.getEDI_ExportStatus()).isEqualTo(EDIExportStatus.Pending.getCode());
	}

	@Test
	void allLinesDeliveryClosed_and_nothingSent_flipsToDontSend()
	{
		final I_EDI_Desadv desadv = createDesadv("0", EDIExportStatus.Pending.getCode());
		createDesadvLine(desadv, "100", "0", "0");
		createLinkedInOut(desadv, EDIExportStatus.DontSend);

		desadvBL.recomputeDesadvStatusFromInOuts(EDIDesadvId.ofRepoId(desadv.getEDI_Desadv_ID()));

		InterfaceWrapperHelper.refresh(desadv);
		assertThat(desadv.getEDI_ExportStatus()).isEqualTo(EDIExportStatus.DontSend.getCode());
	}

	/**
	 * Guards the nullability trap: a NULL QtyOrdered_Override must fall back to QtyOrdered,
	 * NOT be read as zero (X_EDI_DesadvLine.getQtyOrdered_Override returns ZERO for NULL).
	 * Without the guard, delivered 0 >= "override" 0 would make this line delivery-closed
	 * and the DESADV would auto-close although nothing was delivered.
	 */
	@Test
	void nullQtyOrderedOverride_isNotTreatedAsZero_staysPending()
	{
		final I_EDI_Desadv desadv = createDesadv("0", EDIExportStatus.Pending.getCode());
		createDesadvLine(desadv, "100", null, "0");
		createLinkedInOut(desadv, EDIExportStatus.Sent);

		desadvBL.recomputeDesadvStatusFromInOuts(EDIDesadvId.ofRepoId(desadv.getEDI_Desadv_ID()));

		InterfaceWrapperHelper.refresh(desadv);
		assertThat(desadv.getEDI_ExportStatus()).isEqualTo(EDIExportStatus.Pending.getCode());
	}

	@Test
	void isDesadvStatusChanged_detectsEqualStatusAndErrorMsg()
	{
		final I_EDI_Desadv desadv = createDesadv("100", EDIExportStatus.Sent.getCode());
		desadv.setEDIErrorMsg(null);
		saveRecord(desadv);

		assertThat(DesadvBL.isDesadvStatusChanged(desadv, EDIExportStatus.Sent, null))
				.as("same status, same (null) error message -> nothing to write")
				.isFalse();
		assertThat(DesadvBL.isDesadvStatusChanged(desadv, EDIExportStatus.Pending, null))
				.as("different status -> must write")
				.isTrue();
		assertThat(DesadvBL.isDesadvStatusChanged(desadv, EDIExportStatus.Sent, "boom"))
				.as("same status but a new error message -> must write")
				.isTrue();
	}

	@Test
	void recompute_isIdempotent_whenAlreadySent()
	{
		final I_EDI_Desadv desadv = createDesadv("70", EDIExportStatus.Pending.getCode());
		createDesadvLine(desadv, "100", "70", "70");
		createLinkedInOut(desadv, EDIExportStatus.Sent);
		final EDIDesadvId desadvId = EDIDesadvId.ofRepoId(desadv.getEDI_Desadv_ID());

		desadvBL.recomputeDesadvStatusFromInOuts(desadvId);
		desadvBL.recomputeDesadvStatusFromInOuts(desadvId);
		desadvBL.recomputeDesadvStatusFromInOuts(desadvId);

		InterfaceWrapperHelper.refresh(desadv);
		assertThat(desadv.getEDI_ExportStatus()).isEqualTo(EDIExportStatus.Sent.getCode());
		assertThat(desadv.getEDIErrorMsg()).isNull();
	}

	/**
	 * Pins the empty-lines guard in {@code areAllDesadvLinesDeliveryClosed}. {@code Stream.allMatch}
	 * is vacuously {@code true} on an empty stream, so dropping that guard would auto-close every
	 * freshly created, still-empty DESADV — and would do so while passing every other test here.
	 */
	@Test
	void desadvWithoutLines_isNotDeliveryClosed_staysPending()
	{
		final I_EDI_Desadv desadv = createDesadv("0", EDIExportStatus.Pending.getCode());
		// deliberately no EDI_DesadvLine at all
		createLinkedInOut(desadv, EDIExportStatus.Sent);

		desadvBL.recomputeDesadvStatusFromInOuts(EDIDesadvId.ofRepoId(desadv.getEDI_Desadv_ID()));

		InterfaceWrapperHelper.refresh(desadv);
		assertThat(desadv.getEDI_ExportStatus()).isEqualTo(EDIExportStatus.Pending.getCode());
	}

	@Test
	void closingTheShipmentSchedule_flipsTheDesadvToSent()
	{
		final I_EDI_Desadv desadv = createDesadv("70", EDIExportStatus.Pending.getCode());
		final I_EDI_DesadvLine desadvLine = createDesadvLineRecord(desadv, "100", null, "70");
		createLinkedInOut(desadv, EDIExportStatus.Sent);

		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setEDI_DesadvLine_ID(desadvLine.getEDI_DesadvLine_ID());
		saveRecord(orderLine);

		// nothing has flipped yet: the line is still open
		desadvBL.recomputeDesadvStatusFromInOuts(EDIDesadvId.ofRepoId(desadv.getEDI_Desadv_ID()));
		InterfaceWrapperHelper.refresh(desadv);
		assertThat(desadv.getEDI_ExportStatus())
				.as("precondition: still Pending while the schedule is open")
				.isEqualTo(EDIExportStatus.Pending.getCode());

		final I_M_ShipmentSchedule closedSchedule = createShipmentSchedule(orderLine, "70", true);
		desadvBL.updateQtyOrdered_OverrideFromShipSchedAndSave(closedSchedule);

		InterfaceWrapperHelper.refresh(desadvLine);
		assertThat(desadvLine.getQtyOrdered_Override()).isEqualByComparingTo("70");
		InterfaceWrapperHelper.refresh(desadv);
		assertThat(desadv.getEDI_ExportStatus())
				.as("closing the schedule must flip the DESADV without any further call")
				.isEqualTo(EDIExportStatus.Sent.getCode());
	}

	/**
	 * Reopening a closed shipment schedule must return the DESADV to Pending, and reaching a terminal
	 * status must not re-send anything.
	 * <p>
	 * The assertions below pin what this tier can actually observe: the recompute's own code path
	 * writes only the {@code EDI_Desadv}, so it leaves the shipment's {@code EDI_ExportStatus} alone
	 * and enqueues no export work package. They do <b>not</b> cover interceptor wiring — a plain JUnit
	 * test never runs the {@code ModelValidationEngine}, so no {@code @ModelChange} handler fires here.
	 * Whether a status change can re-trigger an export through
	 * {@code EDI_Desadv.onDesadvStatusChanged} is an integration-tier statement and is covered by the
	 * Cucumber scenarios instead.
	 */
	@Test
	void reopeningTheShipmentSchedule_returnsTheDesadvToPending_andNeverReTransmits()
	{
		final I_EDI_Desadv desadv = createDesadv("70", EDIExportStatus.Pending.getCode());
		final I_EDI_DesadvLine desadvLine = createDesadvLineRecord(desadv, "100", null, "70");
		final I_M_InOut inOut = createLinkedInOut(desadv, EDIExportStatus.Sent);

		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setEDI_DesadvLine_ID(desadvLine.getEDI_DesadvLine_ID());
		saveRecord(orderLine);

		final I_M_ShipmentSchedule schedule = createShipmentSchedule(orderLine, "70", true);
		desadvBL.updateQtyOrdered_OverrideFromShipSchedAndSave(schedule);
		InterfaceWrapperHelper.refresh(desadv);
		assertThat(desadv.getEDI_ExportStatus()).isEqualTo(EDIExportStatus.Sent.getCode());

		schedule.setIsClosed(false);
		saveRecord(schedule);
		desadvBL.updateQtyOrdered_OverrideFromShipSchedAndSave(schedule);

		InterfaceWrapperHelper.refresh(desadvLine);
		// Probed via isNull, not getQtyOrdered_Override() == null: the generated getter maps SQL NULL
		// to BigDecimal.ZERO, so an .isNull() assertion here could never hold and a .isZero() one
		// could not tell "override cleared" from "override set to zero" — the distinction the whole
		// delivery-closed predicate turns on.
		assertThat(InterfaceWrapperHelper.isNull(desadvLine, I_EDI_DesadvLine.COLUMNNAME_QtyOrdered_Override))
				.as("reopening clears the override back to SQL NULL, which is what re-opens the line")
				.isTrue();

		InterfaceWrapperHelper.refresh(desadv);
		assertThat(desadv.getEDI_ExportStatus())
				.as("reopening must return the DESADV to Pending — more may ship after all")
				.isEqualTo(EDIExportStatus.Pending.getCode());

		InterfaceWrapperHelper.refresh(inOut);
		assertThat(inOut.getEDI_ExportStatus())
				.as("the shipment's own export status must be untouched — nothing is re-sent")
				.isEqualTo(EDIExportStatus.Sent.getCode());
		assertThat(POJOLookupMap.get().getRecords(I_C_Queue_WorkPackage.class))
				.as("no export work package may be enqueued by a close or a reopen")
				.isEmpty();
	}

	/**
	 * <b>KNOWN-FAILING — it pins the customer contract, not today's behaviour.</b> It documents that the
	 * shipped terminal-status predicate is wider than what the customer was told, and it is expected to
	 * stay RED until a human decides how to resolve that (accept-and-document, or narrow the predicate).
	 * Do not "fix" it by relaxing the assertion.
	 * <p>
	 * <b>What the customer was promised.</b> "Der fehlende Teil ist nur das automatisch geschlossen wird,
	 * wenn alle Lieferdispos <i>geschlossen</i> sind im Fall von Unterlieferung"
	 * (the originating customer request). The trigger is <i>closing</i> a shipment disposition.
	 * Here nothing is closed.
	 * <p>
	 * <b>The path this reproduces.</b> A disposition clerk corrects the <i>Packing Item</i> on a
	 * partly-shipped {@code M_ShipmentSchedule} in the Lieferdisposition window
	 * ({@code AD_Window_ID} 542156):
	 * <ol>
	 *   <li>the annotated callout {@code de.metas.handlingunits.inoutcandidate.callout.M_ShipmentSchedule
	 *       .updateShipmentScheduleQtys} (registered in {@code de.metas.handlingunits.model.validator.Main:204})
	 *       watches {@code M_HU_PI_Item_Product_Override_ID} among others ({@code M_ShipmentSchedule.java:41-49});</li>
	 *   <li>it calls {@code HUPackingAwareBL.setQtyCUFromQtyTU} ({@code M_ShipmentSchedule.java:59}), which
	 *       recomputes the ordered CU qty as {@code QtyOrdered_TU * CUsPerTU} of the <b>new</b> packing item
	 *       and writes it through {@code ShipmentScheduleHUPackingAware.setQty}
	 *       ({@code ShipmentScheduleHUPackingAware.java:85-88}) into {@code M_ShipmentSchedule.QtyOrdered_Override};</li>
	 *   <li>on save, the EDI interceptor {@code de.metas.edi.model.validator.M_ShipmentSchedule}
	 *       ({@code :43-46}, {@code ifColumnsChanged={QtyOrdered_Override, IsClosed}}) fires
	 *       {@link DesadvBL#updateQtyOrdered_OverrideFromShipSchedAndSave} — which is what this test calls.</li>
	 * </ol>
	 * {@code getQtyOrdered_Override} ({@code DesadvBL.java:308-330}) substitutes {@code QtyDelivered} only for a
	 * <b>closed</b> schedule; for this open one it copies the packing-recomputed value verbatim onto the
	 * {@code EDI_DesadvLine}. {@code isDesadvLineDeliveryClosed} ({@code DesadvBL.java:1112-1120}) then reads
	 * {@code 10 >= 8} as "this line has received everything it will ever receive" and the header goes terminal.
	 * <p>
	 * <b>Why {@code FulfillmentPercent} does not save us.</b> {@code EDI_Desadv.SumOrderedInStockingUOM} is only
	 * ever adjusted when an {@code EDI_DesadvLine} is attached or detached, from raw {@code C_OrderLine.QtyOrdered}
	 * ({@code DesadvBL.java:224} / {@code :728}) — never from an override. So the pre-existing
	 * {@code fulfillmentPercent >= 100} arm of the gate stays false and the {@code areAllDesadvLinesDeliveryClosed}
	 * arm added by the DESADV auto-close feature is the only thing that flips the header.
	 * <p>
	 * <b>Concrete case</b> (the customer's own "Änderung von Pilzmischung von 4 auf 2 TU" shape): 4 TU ordered at
	 * 5 kg/TU = 20 kg; 2 TU = 10 kg already shipped and Sent; the clerk corrects the packing item to a 2 kg TU, so
	 * the recomputed ordered qty is 4 * 2 = 8 kg. 10 delivered >= 8 "ordered" -> the DESADV silently reaches
	 * {@code Sent} + {@code Processed} and drops out of the still-owed worklist, although 2 TU are still owed and
	 * nobody closed anything.
	 */
	@Test
	void packingCorrectionLoweringTheOrderedQty_flipsTheDesadvToSent_althoughNothingWasClosed()
	{
		// 20 kg ordered (4 TU x 5 kg), 10 kg delivered (2 TU) and already Sent -> 50 %
		final I_EDI_Desadv desadv = createDesadv("50", EDIExportStatus.Pending.getCode());
		final I_EDI_DesadvLine desadvLine = createDesadvLineRecord(desadv, "20", null, "10");
		createLinkedInOut(desadv, EDIExportStatus.Sent);

		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setEDI_DesadvLine_ID(desadvLine.getEDI_DesadvLine_ID());
		saveRecord(orderLine);

		desadvBL.recomputeDesadvStatusFromInOuts(EDIDesadvId.ofRepoId(desadv.getEDI_Desadv_ID()));
		InterfaceWrapperHelper.refresh(desadv);
		assertThat(desadv.getEDI_ExportStatus())
				.as("precondition: half-delivered and nothing closed -> still Pending")
				.isEqualTo(EDIExportStatus.Pending.getCode());

		// the packing correction: the callout recomputes ordered as 4 TU x 2 kg = 8 kg onto the STILL-OPEN schedule
		final I_M_ShipmentSchedule openSchedule =
				createOpenShipmentScheduleWithQtyOrderedOverride(orderLine, "10", "8");
		assertThat(openSchedule.isClosed())
				.as("precondition: the shipment disposition is NOT closed — that is the whole point")
				.isFalse();

		// exactly what the EDI interceptor does on the AFTER_CHANGE of QtyOrdered_Override
		desadvBL.updateQtyOrdered_OverrideFromShipSchedAndSave(openSchedule);

		InterfaceWrapperHelper.refresh(desadvLine);
		assertThat(desadvLine.getQtyOrdered_Override())
				.as("mechanism: an OPEN schedule's override is copied through verbatim, delivered qty is NOT substituted")
				.isEqualByComparingTo("8");

		InterfaceWrapperHelper.refresh(desadv);
		assertThat(desadv.getEDI_ExportStatus())
				.as("no M_ShipmentSchedule was closed, so per the customer contract the DESADV must stay Pending; "
						+ "2 of 4 TU are still owed")
				.isEqualTo(EDIExportStatus.Pending.getCode());
	}

	/**
	 * Pins the per-transaction dedupe of the close route: closing every {@code M_ShipmentSchedule} of one
	 * order inside a single transaction must re-derive the DESADV header exactly <b>once</b>, not once per
	 * schedule.
	 * <p>
	 * The {@code M_ShipmentSchedule} interceptor fires per record and each recompute costs three to four
	 * uncached round-trips (the header, the per-shipment routing config, the linked-shipment junction, and
	 * all DESADV lines), so an un-deduped close of an N-line order pays N times over for a verdict only the
	 * last pass can reach. What is counted here is recompute <em>invocations</em>: {@code POJOLookupMap}
	 * exposes no query counter, and {@code setDesadvStatusAndSaveIfChanged} guards the write only — so the
	 * repetition is invisible to every record-state assertion, which is exactly why it needs its own test.
	 */
	@Test
	void closingEveryScheduleOfOneDesadv_inOneTrx_recomputesTheHeaderOnlyOnce()
	{
		final I_EDI_Desadv desadv = createDesadv("70", EDIExportStatus.Pending.getCode());
		createLinkedInOut(desadv, EDIExportStatus.Sent);

		// three lines, each under-delivered 70 of 100, each with its own order line and shipment schedule
		final List<I_M_ShipmentSchedule> closedSchedules = new ArrayList<>();
		for (int i = 0; i < 3; i++)
		{
			final I_EDI_DesadvLine desadvLine = createDesadvLineRecord(desadv, "100", null, "70");
			final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
			orderLine.setEDI_DesadvLine_ID(desadvLine.getEDI_DesadvLine_ID());
			saveRecord(orderLine);
			closedSchedules.add(createShipmentSchedule(orderLine, "70", true));
		}

		final CountingDesadvBL countingDesadvBL = new CountingDesadvBL();

		// one transaction = one close operation, the way M_ShipmentSchedule_CloseShipmentSchedules runs it
		Services.get(ITrxManager.class).runInThreadInheritedTrx(
				() -> closedSchedules.forEach(countingDesadvBL::updateQtyOrdered_OverrideFromShipSchedAndSave));

		assertThat(countingDesadvBL.recomputeInvocations)
				.as("three schedules of the same DESADV, closed in one transaction, must cost ONE recompute")
				.isEqualTo(1);

		InterfaceWrapperHelper.refresh(desadv);
		assertThat(desadv.getEDI_ExportStatus())
				.as("and the single deduped recompute must still reach the same terminal status")
				.isEqualTo(EDIExportStatus.Sent.getCode());
	}

	/**
	 * Counts how often the recompute entry point actually runs. Constructed the same way
	 * {@link DesadvBL#newInstanceForUnitTesting()} does, but as a subclass rather than via
	 * {@code SpringContextHolder} so that the override is in place: a Mockito spy would not see
	 * {@code DesadvBL}'s own internal call to the method.
	 */
	private static final class CountingDesadvBL extends DesadvBL
	{
		private int recomputeInvocations = 0;

		private CountingDesadvBL()
		{
			super(EDIDesadvPackService.newInstanceForUnitTesting(),
					EDIDesadvInOutLineDAO.newInstanceForUnitTesting(),
					EDIBPartnerConfigService.newInstanceForUnitTesting(),
					new ProductASIDataRepository(Services.get(IQueryBL.class)),
					new EDIDesadvInOutRepository());
		}

		@Override
		public void recomputeDesadvStatusFromInOuts(@NonNull final EDIDesadvId desadvId)
		{
			recomputeInvocations++;
			super.recomputeDesadvStatusFromInOuts(desadvId);
		}
	}
}
