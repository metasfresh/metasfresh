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
	 * An <b>open</b> {@code M_ShipmentSchedule} (IsClosed='N') carrying a {@code QtyOrdered_Override} —
	 * the state the WebUI leaves behind after a packing correction, see
	 * {@link #packingCorrectionLoweringTheOrderedQty_flipsTheDesadvToSent_acceptedBehaviour()}.
	 * <p>
	 * <b>The override is hand-set here; the callout that computes it is NOT executed.</b> This method
	 * only reproduces the callout's <i>output</i> state. That the real callout genuinely produces this
	 * value on this column is proven separately and end-to-end by
	 * {@link DesadvBL_packingCorrectionCallout_Test}, which drives
	 * {@code de.metas.handlingunits.inoutcandidate.callout.M_ShipmentSchedule.updateShipmentScheduleQtys}
	 * itself.
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
	 * <b>Accepted behaviour, decided 2026-08-27 — deliberate, not an oversight.</b> Do not "fix" it by
	 * flipping the expectation back to {@code Pending}.
	 * <p>
	 * The terminal-status trigger is <i>"the effective ordered quantity has been reached by the delivered
	 * quantity"</i>, not <i>"a shipment schedule was closed"</i>: {@link DesadvBL#isDesadvLineDeliveryClosed}
	 * compares {@code QtyDeliveredInStockingUOM} against {@code COALESCE(QtyOrdered_Override, QtyOrdered)}.
	 * Closing an {@code M_ShipmentSchedule} is only one way to reach that state — a <b>packing-item
	 * correction</b> on a still-<b>open</b> schedule is another, because the packing callout
	 * {@code de.metas.handlingunits.inoutcandidate.callout.M_ShipmentSchedule#updateShipmentScheduleQtys}
	 * recomputes the ordered qty as {@code QtyOrdered_TU * CUsPerTU} into
	 * {@code M_ShipmentSchedule.QtyOrdered_Override}, and
	 * {@link DesadvBL#updateQtyOrdered_OverrideFromShipSchedAndSave} copies an open schedule's override
	 * through to the {@code EDI_DesadvLine} verbatim. That callout chain is driven for real, end-to-end,
	 * by {@link DesadvBL_packingCorrectionCallout_Test}; this test starts from its result and hand-sets
	 * the resulting override.
	 * <p>
	 * <b>Why the predicate was not narrowed to {@code M_ShipmentSchedule.IsClosed}.</b> The transmitted
	 * EDI payload already derives its per-line {@code IsDeliveryClosed} from the same quantity rule
	 * ({@code M_InOut_DesadvLine_V}, {@code get_desadv_packs_json_fn}). Narrowing only the header-status
	 * side would make the screen and the wire disagree permanently.
	 * <p>
	 * <b>It is reversible.</b> Reverting the packing change raises the override again, the same EDI
	 * interceptor {@code de.metas.edi.model.validator.M_ShipmentSchedule} re-fires, the predicate goes
	 * false and the header returns to {@code Pending} — the same mechanism as
	 * {@link #reopeningTheShipmentSchedule_returnsTheDesadvToPending_andNeverReTransmits()}.
	 * <p>
	 * <b>Concrete case pinned below.</b> 4 TU ordered at 5 kg/TU = 20 kg; 2 TU = 10 kg already shipped
	 * and Sent; the clerk corrects the packing item to a 2 kg TU, so the recomputed ordered qty is
	 * 4 * 2 = 8 kg. 10 delivered >= 8 "ordered" -> the header goes terminal at 50 % fulfilment. Note the
	 * {@code FulfillmentPercent} assertion: {@code EDI_Desadv.SumOrderedInStockingUOM} is adjusted only
	 * when an {@code EDI_DesadvLine} is attached or detached, from raw {@code C_OrderLine.QtyOrdered} and
	 * never from an override, so it stays at 50 — proving it is the {@code areAllDesadvLinesDeliveryClosed}
	 * arm of the gate that flips the header here, not the {@code fulfillmentPercent >= 100} arm.
	 * ({@code Processed} follows from the header-status interceptor, which a plain JUnit tier never runs.)
	 */
	@Test
	void packingCorrectionLoweringTheOrderedQty_flipsTheDesadvToSent_acceptedBehaviour()
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

		// The packing correction, SIMULATED: the override below is hand-set to the value the callout
		// computes (4 TU x 2 kg = 8 kg) onto the STILL-OPEN schedule. The callout itself is NOT run
		// here — DesadvBL_packingCorrectionCallout_Test drives it for real and asserts it lands on
		// exactly this column, so this literal is a faithful stand-in and not an invented state.
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
				.as("accepted behaviour: the effective ordered qty (8) has been reached by the delivered qty (10), "
						+ "so the header goes terminal even though no M_ShipmentSchedule was closed")
				.isEqualTo(EDIExportStatus.Sent.getCode());
		assertThat(desadv.getFulfillmentPercent())
				.as("and it does so at 50 percent fulfilment — FulfillmentPercent never sees the override, so this is "
						+ "the delivery-closed arm of the gate firing, not the fulfillmentPercent >= 100 arm")
				.isEqualByComparingTo("50");
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
