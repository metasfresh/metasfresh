package de.metas.hu_consolidation.mobile.job;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuUnitType;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.picking.slot.PickingSlotService;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.report.HUToReport;
import de.metas.handlingunits.report.labels.HULabelConfig;
import de.metas.handlingunits.report.labels.HULabelPrintRequest;
import de.metas.handlingunits.report.labels.HULabelService;
import de.metas.handlingunits.report.labels.HULabelSourceDocType;
import de.metas.hu_consolidation.mobile.job.scan.HUConsolidationGRAIScanPolicy;
import de.metas.hu_consolidation.mobile.job.scan.HUConsolidationScannedCodeResolvers;
import de.metas.hu_consolidation.mobile.shipment.HUConsolidationShipmentService;
import de.metas.i18n.ExplainedOptional;
import de.metas.process.AdProcessId;
import de.metas.report.PrintCopies;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * "Schliessen" (close target) and "Fertigstellen" (complete) with a failing SSCC label print:
 * the operator gets the print error AND the pallet is closed anyway (shipment created as usual), like in picking.
 */
class HUConsolidationJobServiceCloseTargetTest
{
	private static final UserId CALLER = UserId.ofRepoId(100);
	private static final HuId LU_ID = HuId.ofRepoId(555);
	private static final BPartnerId CUSTOMER = BPartnerId.ofRepoId(2);
	/** the operator message says the pallet was closed and shipped, and only its label failed */
	private static final String MSG_PALLET_SHIPPED_LABEL_NOT_PRINTED = "de.metas.hu_consolidation.mobile.job.PALLET_SHIPPED_LABEL_NOT_PRINTED";
	/** the short reasons shown to the operator instead of the technical error, which stays in the cause */
	private static final String MSG_REASON_NO_LABEL_CONFIG = "de.metas.hu_consolidation.mobile.job.LABEL_NOT_PRINTED_REASON_NO_LABEL_CONFIG";
	private static final String MSG_REASON_PRINT_FAILED = "de.metas.hu_consolidation.mobile.job.LABEL_NOT_PRINTED_REASON_PRINT_FAILED";

	private HUConsolidationJobRepository jobRepository;
	private HULabelService huLabelService;
	private HUConsolidationShipmentService shipmentService;
	private HUConsolidationJobService service;
	private HUConsolidationJob job;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		jobRepository = new HUConsolidationJobRepository();
		huLabelService = mock(HULabelService.class);
		when(huLabelService.preparePrint(any(HULabelPrintRequest.class))).thenReturn(() -> {});
		givenLULabelConfigured(false);
		final HUConsolidationLabelPrinter labelPrinter = new HUConsolidationLabelPrinter(huLabelService);
		shipmentService = mock(HUConsolidationShipmentService.class);
		final HUConsolidationTargetCloser targetCloser = new HUConsolidationTargetCloser(labelPrinter, shipmentService);

		service = new HUConsolidationJobService(
				jobRepository,
				mock(PickingSlotService.class),
				mock(HUConsolidationGRAIScanPolicy.class),
				mock(HUQRCodesService.class),
				mock(HUConsolidationAvailableTargetsFinder.class),
				targetCloser,
				labelPrinter,
				mock(HUConsolidationScannedCodeResolvers.class));

		final HUConsolidationJob newJob = jobRepository.create(
				HUConsolidationJobReference.builder()
						.bpartnerLocationId(BPartnerLocationId.ofRepoId(1, 2))
						.pickingSlotIds(ImmutableSet.of())
						.build(),
				CALLER);
		job = jobRepository.updateById(newJob.getId(), j -> j.withCurrentTarget(
				HUConsolidationTarget.builder().caption("LU").luId(LU_ID).build()));

		// a real pallet: its packing instruction version says LU (the label lookup reads the unit type from it)
		final I_M_HU_PI_Version luPIVersion = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Version.class);
		luPIVersion.setHU_UnitType(X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		InterfaceWrapperHelper.saveRecord(luPIVersion);

		final I_M_HU lu = InterfaceWrapperHelper.newInstance(I_M_HU.class);
		lu.setM_HU_ID(LU_ID.getRepoId());
		lu.setM_HU_PI_Version_ID(luPIVersion.getM_HU_PI_Version_ID());
		lu.setC_BPartner_ID(CUSTOMER.getRepoId());
		InterfaceWrapperHelper.saveRecord(lu);
	}

	/** Whether a label config applies to the pallet (the LU of the customer, source document type picking), as the label print looks it up. */
	private void givenLULabelConfigured(final boolean configured)
	{
		when(huLabelService.getLabelConfig(any(HULabelSourceDocType.class), any(HUToReport.class))).thenReturn(ExplainedOptional.emptyBecause("no label config"));
		if (configured)
		{
			when(huLabelService.getLabelConfig(eq(HULabelSourceDocType.Picking), argThat(hu -> hu != null && LU_ID.equals(hu.getHUId()) && hu.getHUUnitType() == HuUnitType.LU && CUSTOMER.equals(hu.getBPartnerId()))))
					.thenReturn(ExplainedOptional.of(HULabelConfig.builder()
							.printFormatProcessId(AdProcessId.ofRepoId(584998))
							.autoPrint(true)
							.autoPrintCopies(PrintCopies.ONE)
							.build()));
		}
	}

	/** Models the real failure: the label print fails when it runs (e.g. SSCC18 / FNC1 error, missing report file). */
	private void givenLabelPrintFailsWhenRun()
	{
		when(huLabelService.preparePrint(any(HULabelPrintRequest.class))).thenReturn(() -> {
			throw new AdempiereException("SSCC label print failed");
		});
	}

	@Test
	void closeTarget_labelPrintFails_errorReachesCaller_butTargetIsClosedAndShipmentCreated()
	{
		givenLabelPrintFailsWhenRun();

		assertThatThrownBy(() -> service.closeTarget(job.getId(), CALLER))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(MSG_PALLET_SHIPPED_LABEL_NOT_PRINTED)
				.hasMessageContaining(MSG_REASON_PRINT_FAILED)
				.hasMessageNotContaining("SSCC label print failed")
				.cause().hasMessage("SSCC label print failed");

		assertThat(jobRepository.getById(job.getId()).getCurrentTarget()).as("pallet closed anyway").isNull();
		verify(shipmentService).createShipmentForLUs(any(), eq(ImmutableSet.of(LU_ID)));
	}

	/** No LU label config: the label cannot even be prepared; the operator gets that error, and the pallet is closed anyway (shipment created as usual). */
	@Test
	void closeTarget_noLabelConfig_errorReachesCaller_butTargetIsClosedAndShipmentCreated()
	{
		final AdempiereException noConfig = new AdempiereException("No label config found");
		when(huLabelService.preparePrint(any(HULabelPrintRequest.class))).thenThrow(noConfig);

		assertThatThrownBy(() -> service.closeTarget(job.getId(), CALLER))
				.hasMessageContaining(MSG_PALLET_SHIPPED_LABEL_NOT_PRINTED)
				.hasMessageContaining(MSG_REASON_NO_LABEL_CONFIG)
				.hasMessageNotContaining(MSG_REASON_PRINT_FAILED)
				.hasMessageNotContaining("No label config found")
				.cause().hasMessage("No label config found");

		assertThat(jobRepository.getById(job.getId()).getCurrentTarget()).as("pallet closed anyway").isNull();
		verify(shipmentService).createShipmentForLUs(any(), eq(ImmutableSet.of(LU_ID)));
	}

	/** The label print cannot be prepared although a label config applies to the pallet: the generic reason, not "no label set up". */
	@Test
	void closeTarget_labelPrepareFails_labelConfigured_reasonPrintFailed()
	{
		givenLULabelConfigured(true);
		when(huLabelService.preparePrint(any(HULabelPrintRequest.class))).thenThrow(new AdempiereException("Process not found"));

		assertThatThrownBy(() -> service.closeTarget(job.getId(), CALLER))
				.hasMessageContaining(MSG_PALLET_SHIPPED_LABEL_NOT_PRINTED)
				.hasMessageContaining(MSG_REASON_PRINT_FAILED)
				.hasMessageNotContaining(MSG_REASON_NO_LABEL_CONFIG)
				.cause().hasMessage("Process not found");

		assertThat(jobRepository.getById(job.getId()).getCurrentTarget()).as("pallet closed anyway").isNull();
	}

	/** A database error while preparing the label means a broken transaction: the close is aborted, nothing is shipped. */
	@Test
	void closeTarget_labelPrepareFailsWithDBException_closeAborted()
	{
		givenLULabelConfigured(true);
		when(huLabelService.preparePrint(any(HULabelPrintRequest.class))).thenThrow(new DBException("connection lost"));

		assertThatThrownBy(() -> service.closeTarget(job.getId(), CALLER))
				.isInstanceOf(DBException.class)
				.isNotInstanceOf(HUConsolidationLabelNotPrintedException.class)
				.hasMessageContaining("connection lost");

		assertThat(jobRepository.getById(job.getId()).getCurrentTarget()).as("pallet still open").isNotNull();
		verify(shipmentService, never()).createShipmentForLUs(any(), any());
	}

	@Test
	void closeTarget_labelPrintOk_targetClosedShipmentCreatedLabelPrinted()
	{
		final AtomicBoolean printed = new AtomicBoolean();
		when(huLabelService.preparePrint(any(HULabelPrintRequest.class))).thenReturn(() -> printed.set(true));

		final HUConsolidationJob result = service.closeTarget(job.getId(), CALLER);

		assertThat(result.getCurrentTarget()).isNull();
		assertThat(printed).isTrue();
		verify(shipmentService).createShipmentForLUs(any(), eq(ImmutableSet.of(LU_ID)));
	}

	/** Only the pallet's own label must be configured: crates on it without a label config are skipped, as in picking. */
	@Test
	void closeTarget_labelRequest_failsOnlyOnTheMissingPalletLabelConfig()
	{
		service.closeTarget(job.getId(), CALLER);

		final ArgumentCaptor<HULabelPrintRequest> request = ArgumentCaptor.forClass(HULabelPrintRequest.class);
		verify(huLabelService).preparePrint(request.capture());
		assertThat(request.getValue().getHuIds()).containsExactly(LU_ID);
		assertThat(request.getValue().isFailOnMissingLabelConfig()).isTrue();
		assertThat(request.getValue().isSkipIncludedHUsWithoutLabelConfig()).isTrue();
	}

	@Test
	void complete_noLabelConfig_errorReachesCaller_butJobIsCompletedAndShipmentCreated()
	{
		when(huLabelService.preparePrint(any(HULabelPrintRequest.class))).thenThrow(new AdempiereException("No label config found"));

		assertThatThrownBy(() -> service.complete(jobRepository.getById(job.getId())))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(MSG_PALLET_SHIPPED_LABEL_NOT_PRINTED)
				.hasMessageContaining(MSG_REASON_NO_LABEL_CONFIG)
				.hasMessageNotContaining(MSG_REASON_PRINT_FAILED)
				.hasMessageNotContaining("No label config found")
				.cause().hasMessage("No label config found");

		final HUConsolidationJob jobAfter = jobRepository.getById(job.getId());
		assertThat(jobAfter.isProcessed()).as("job completed anyway").isTrue();
		assertThat(jobAfter.getCurrentTarget()).as("pallet closed").isNull();
		verify(shipmentService).createShipmentForLUs(any(), eq(ImmutableSet.of(LU_ID)));
	}

	/** The job repository is not transactional: the job may be saved as completed only after the shipment was committed. */
	@Test
	void complete_jobSavedAsCompletedOnlyAfterTheCommit()
	{
		final AtomicBoolean completedAtCommit = new AtomicBoolean();
		doAnswer(invocation -> {
			Services.get(ITrxManager.class).runAfterCommit(() -> completedAtCommit.set(jobRepository.getById(job.getId()).isProcessed()));
			return null;
		}).when(shipmentService).createShipmentForLUs(any(), any());

		final HUConsolidationJob result = service.complete(jobRepository.getById(job.getId()));

		assertThat(completedAtCommit).as("job not yet saved as completed when the shipment is committed").isFalse();
		assertThat(result.isProcessed()).isTrue();
		assertThat(jobRepository.getById(job.getId()).isProcessed()).as("saved after the commit").isTrue();
	}

	@Test
	void complete_labelPrintOk_jobCompletedLabelPrinted()
	{
		final AtomicBoolean printed = new AtomicBoolean();
		when(huLabelService.preparePrint(any(HULabelPrintRequest.class))).thenReturn(() -> printed.set(true));

		final HUConsolidationJob result = service.complete(jobRepository.getById(job.getId()));

		assertThat(result.isProcessed()).isTrue();
		assertThat(printed).isTrue();
		verify(shipmentService).createShipmentForLUs(any(), eq(ImmutableSet.of(LU_ID)));
	}

	@Test
	void closeTarget_noTargetAnymore_nothingPrinted()
	{
		service.closeTarget(job.getId(), CALLER); // closes it
		final AtomicBoolean printedAgain = new AtomicBoolean();
		when(huLabelService.preparePrint(any(HULabelPrintRequest.class))).thenReturn(() -> printedAgain.set(true));

		service.closeTarget(job.getId(), CALLER); // e.g. concurrent second click

		assertThat(printedAgain).isFalse();
		verify(shipmentService).createShipmentForLUs(any(), any()); // only once
	}
}
