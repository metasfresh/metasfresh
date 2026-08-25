package de.metas.edi.api.impl;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.edi.api.EDIDesadvId;
import de.metas.edi.api.EDIExportStatus;
import de.metas.edi.async.spi.impl.EDIWorkpackageProcessor;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.esb.edi.model.I_EDI_Desadv_M_InOut;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.organization.OrgId;
import de.metas.util.Services;
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
}
