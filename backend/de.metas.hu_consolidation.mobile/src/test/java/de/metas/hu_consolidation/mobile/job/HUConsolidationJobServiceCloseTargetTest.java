package de.metas.hu_consolidation.mobile.job;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.slot.PickingSlotService;
import de.metas.handlingunits.report.labels.HULabelPrintRequest;
import de.metas.handlingunits.report.labels.HULabelService;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.hu_consolidation.mobile.job.scan.HUConsolidationGRAIPresence;
import de.metas.hu_consolidation.mobile.job.scan.HUConsolidationScannedCodeResolvers;
import de.metas.hu_consolidation.mobile.shipment.HUConsolidationShipmentService;
import de.metas.user.UserId;
import org.adempiere.exceptions.AdempiereException;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * "Schliessen" (close target) with a failing SSCC label print:
 * the operator gets the print error AND the pallet is closed anyway (shipment created as usual), like in picking.
 */
class HUConsolidationJobServiceCloseTargetTest
{
	private static final UserId CALLER = UserId.ofRepoId(100);
	private static final HuId LU_ID = HuId.ofRepoId(555);

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
		final HUConsolidationLabelPrinter labelPrinter = new HUConsolidationLabelPrinter(huLabelService);
		shipmentService = mock(HUConsolidationShipmentService.class);
		final HUConsolidationTargetCloser targetCloser = new HUConsolidationTargetCloser(labelPrinter, shipmentService);

		service = new HUConsolidationJobService(
				jobRepository,
				mock(PickingSlotService.class),
				mock(HUConsolidationGRAIPresence.class),
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
	}

	/**
	 * Models the real failure: the label print fails when it runs (e.g. SSCC18 / FNC1 error, missing report file).
	 * Old code: the print request is registered inside the job transaction via runAfterClose, its failure is swallowed (no error shown) while the pallet is closed.
	 * New code: the print runs after the close, so the failure reaches the caller.
	 */
	private void givenLabelPrintFailsWhenRun()
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		doAnswer(inv -> {
			// what HULabelPrintCommand.execute() does: defer the printing to after the trx was closed
			trxManager.runAfterClose(trx -> {
				throw new AdempiereException("SSCC label print failed");
			});
			return null;
		}).when(huLabelService).print(any(HULabelPrintRequest.class));
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
				.hasMessageContaining("SSCC label print failed");

		assertThat(jobRepository.getById(job.getId()).getCurrentTarget()).as("pallet closed anyway").isNull();
		verify(shipmentService).createShipmentForLUs(any(), eq(ImmutableSet.of(LU_ID)));
	}

	/** No LU label config: fails before the close, as today (no shipment, pallet stays open). */
	@Test
	void closeTarget_noLabelConfig_failsBeforeClose_nothingChanges()
	{
		final AdempiereException noConfig = new AdempiereException("No label config found");
		when(huLabelService.preparePrint(any(HULabelPrintRequest.class))).thenThrow(noConfig);
		doThrow(noConfig).when(huLabelService).print(any(HULabelPrintRequest.class));

		assertThatThrownBy(() -> service.closeTarget(job.getId(), CALLER))
				.hasMessageContaining("No label config found");

		assertThat(jobRepository.getById(job.getId()).getCurrentTarget()).as("pallet stays open").isNotNull();
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
