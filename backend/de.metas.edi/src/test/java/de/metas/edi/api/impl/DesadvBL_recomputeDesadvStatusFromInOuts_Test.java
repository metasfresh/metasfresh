package de.metas.edi.api.impl;

import de.metas.edi.api.EDIDesadvId;
import de.metas.edi.api.EDIExportStatus;
import de.metas.edi.async.spi.impl.EDIWorkpackageProcessor;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.esb.edi.model.I_EDI_Desadv_M_InOut;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
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
		final I_EDI_DesadvLine line = newInstance(I_EDI_DesadvLine.class);
		line.setEDI_Desadv_ID(desadv.getEDI_Desadv_ID());
		line.setQtyOrdered(new BigDecimal(qtyOrdered));
		line.setQtyOrdered_Override(qtyOrderedOverride == null ? null : new BigDecimal(qtyOrderedOverride));
		line.setQtyDeliveredInStockingUOM(new BigDecimal(qtyDeliveredInStockingUOM));
		saveRecord(line);
	}

	private void createLinkedInOut(final I_EDI_Desadv desadv, final EDIExportStatus inOutStatus)
	{
		final I_M_InOut inOut = newInstance(I_M_InOut.class);
		inOut.setEDI_ExportStatus(inOutStatus.getCode());
		inOut.setEDI_Desadv_ID(desadv.getEDI_Desadv_ID());
		saveRecord(inOut);

		final I_EDI_Desadv_M_InOut junction = newInstance(I_EDI_Desadv_M_InOut.class);
		junction.setEDI_Desadv_ID(desadv.getEDI_Desadv_ID());
		junction.setM_InOut_ID(inOut.getM_InOut_ID());
		saveRecord(junction);
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
}
