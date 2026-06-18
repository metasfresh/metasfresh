package de.metas.manufacturing.workflows_api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.qrcodes.service.HUQRCodeGenerateRequest;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.report.labels.HULabelConfigQuery;
import de.metas.handlingunits.report.labels.HULabelConfigService;
import de.metas.handlingunits.report.labels.HULabelSourceDocType;
import de.metas.i18n.TranslatableStrings;
import de.metas.manufacturing.config.MobileUIManufacturingConfigRepository;
import de.metas.manufacturing.job.model.FinishedGoodsReceive;
import de.metas.manufacturing.job.model.FinishedGoodsReceiveLine;
import de.metas.manufacturing.job.model.FinishedGoodsReceiveLineId;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.job.model.ManufacturingJobActivity;
import de.metas.manufacturing.job.model.ManufacturingJobActivityId;
import de.metas.manufacturing.workflows_api.rest_api.json.JsonFinishGoodsReceiveQRCodesGenerateRequest;
import de.metas.material.planning.pporder.PPAlwaysAvailableToUser;
import de.metas.material.planning.pporder.PPRoutingActivityType;
import de.metas.product.ProductId;
import de.metas.business.BusinessTestHelper;
import de.metas.quantity.Quantity;
import de.metas.report.PrintCopies;
import de.metas.resource.UserWorkstationService;
import de.metas.workflow.rest_api.model.WFProcessId;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderRoutingActivityId;
import org.eevolution.api.PPOrderRoutingActivityStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * RED test — proves that {@code generateFinishGoodsReceiveQRCodes} (Auszeichnung Fertigware)
 * ignores {@code M_HU_Label_Config}: the label-config service is never consulted on current code.
 *
 * <p>AC1 (RED — must FAIL until the fix lands):
 * When {@code generateFinishGoodsReceiveQRCodes} is invoked, it MUST call
 * {@code HULabelConfigService.getFirstMatching} with a query whose
 * {@code sourceDocType == HULabelSourceDocType.Manufacturing}.
 * On current code that call never happens → the verify below FAILS → RED.
 */
class FinishedGoodsLabelConfigTest
{
	// ── mocks ───────────────────────────────────────────────────────────────
	private MobileUIManufacturingConfigRepository configRepository;
	private ManufacturingRestService manufacturingRestService;
	private UserWorkstationService userWorkstationService;

	/**
	 * Fully mocked — no real print / Jasper / DB call happens.
	 */
	private HUQRCodesService huQRCodesService;

	/**
	 * The service that SHOULD be consulted when selecting which label process to use.
	 * Currently it is NOT injected into {@link ManufacturingMobileApplication} — that
	 * is the root of the bug this test documents.
	 */
	private HULabelConfigService huLabelConfigService;

	// ── system under test ────────────────────────────────────────────────────
	private ManufacturingMobileApplication app;

	@BeforeEach
	void setUp()
	{
		// Required so that AdempiereTestHelper-managed Services.get() initialisers succeed.
		AdempiereTestHelper.get().init();

		// Create mocks (Mockito 2.x — use Mockito.mock(), no annotation processor available).
		configRepository = mock(MobileUIManufacturingConfigRepository.class);
		manufacturingRestService = mock(ManufacturingRestService.class);
		userWorkstationService = mock(UserWorkstationService.class);
		huQRCodesService = mock(HUQRCodesService.class);
		huLabelConfigService = mock(HULabelConfigService.class);

		// Construct the app.
		// Note: HULabelConfigService is NOT a constructor parameter today — that is the bug.
		app = new ManufacturingMobileApplication(
				configRepository,
				manufacturingRestService,
				huQRCodesService,
				userWorkstationService);

		// Wire up manufacturingRestService to return a canned job so the method can
		// reach the print call without a NullPointerException.
		final ManufacturingJob cannedJob = buildCannedManufacturingJob();
		doReturn(cannedJob).when(manufacturingRestService).getJobById(any(PPOrderId.class));

		// Stub generate() — return an empty list (the exact content does not matter for this test).
		doReturn(ImmutableList.of()).when(huQRCodesService).generate(any(HUQRCodeGenerateRequest.class));
	}

	// ─── AC1 — RED: HULabelConfigService must be consulted ──────────────────

	/**
	 * AC1 (RED): When {@code generateFinishGoodsReceiveQRCodes} runs, it MUST consult
	 * {@code HULabelConfigService.getFirstMatching} with a query that has
	 * {@code sourceDocType == Manufacturing}.
	 *
	 * <p>On current code this verify FAILS with
	 * "Wanted but not invoked: huLabelConfigService.getFirstMatching(…)" because
	 * {@code ManufacturingMobileApplication} never calls {@code HULabelConfigService} at all.
	 * That is the RED signal.
	 */
	@Test
	void generateFinishGoodsQRCodes_mustConsultHULabelConfigService()
	{
		// when
		app.generateFinishGoodsReceiveQRCodes(buildRequest());

		// then — HULabelConfigService.getFirstMatching MUST have been called with
		//        a query whose sourceDocType is Manufacturing.
		//        On current code it is NEVER called → this verify FAILS → RED.
		final ArgumentCaptor<HULabelConfigQuery> queryCaptor = ArgumentCaptor.forClass(HULabelConfigQuery.class);
		verify(huLabelConfigService).getFirstMatching(queryCaptor.capture());

		assertThat(queryCaptor.getValue().getSourceDocType())
				.as("HULabelConfigQuery.sourceDocType must be Manufacturing")
				.isEqualTo(HULabelSourceDocType.Manufacturing);
	}

	// ─── helpers ────────────────────────────────────────────────────────────────

	private static JsonFinishGoodsReceiveQRCodesGenerateRequest buildRequest()
	{
		final PPOrderId ppOrderId = PPOrderId.ofRepoId(1001);
		return JsonFinishGoodsReceiveQRCodesGenerateRequest.builder()
				.wfProcessId(WFProcessId.ofIdPart(ManufacturingMobileApplication.APPLICATION_ID, ppOrderId))
				.finishedGoodsReceiveLineId(FinishedGoodsReceiveLineId.FINISHED_GOODS)
				.huPackingInstructionsId(HuPackingInstructionsId.ofRepoId(100))
				.numberOfHUs(1)
				.build();
	}

	private static ManufacturingJob buildCannedManufacturingJob()
	{
		final ProductId productId = ProductId.ofRepoId(501);
		final I_C_UOM uomKg = BusinessTestHelper.createUomKg();

		final FinishedGoodsReceiveLine receiveLine = FinishedGoodsReceiveLine.builder()
				.productId(productId)
				.productName(TranslatableStrings.anyLanguage("Finished Good"))
				.productValue("FG001")
				.attributes(ImmutableAttributeSet.EMPTY)
				.qtyToReceive(new Quantity(BigDecimal.TEN, uomKg))
				.qtyReceived(new Quantity(BigDecimal.ZERO, uomKg))
				.build();

		final FinishedGoodsReceive finishedGoodsReceive = FinishedGoodsReceive.builder()
				.linesById(ImmutableMap.of(FinishedGoodsReceiveLineId.FINISHED_GOODS, receiveLine))
				.build();

		final PPOrderRoutingActivityId routingActivityId = PPOrderRoutingActivityId.ofRepoId(
				PPOrderId.ofRepoId(1001), 1);

		final ManufacturingJobActivity activity = ManufacturingJobActivity.builder()
				.id(ManufacturingJobActivityId.ofRepoId(1))
				.name("Auszeichnung Fertigware")
				.type(PPRoutingActivityType.GenerateHUQRCodes)
				.finishedGoodsReceive(finishedGoodsReceive)
				.orderRoutingActivityId(routingActivityId)
				.routingActivityStatus(PPOrderRoutingActivityStatus.NOT_STARTED)
				.alwaysAvailableToUser(PPAlwaysAvailableToUser.YES)
				.build();

		return ManufacturingJob.builder()
				.ppOrderId(PPOrderId.ofRepoId(1001))
				.documentNo("MO-TEST-001")
				.dateStartSchedule(ZonedDateTime.now())
				.warehouseId(WarehouseId.ofRepoId(1))
				.activities(ImmutableList.of(activity))
				.build();
	}
}
