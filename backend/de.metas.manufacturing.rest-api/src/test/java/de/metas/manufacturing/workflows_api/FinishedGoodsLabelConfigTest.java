package de.metas.manufacturing.workflows_api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuUnitType;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.qrcodes.service.HUQRCodeGenerateRequest;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.report.labels.HULabelConfig;
import de.metas.handlingunits.report.labels.HULabelConfigQuery;
import de.metas.handlingunits.report.labels.HULabelConfigRepository;
import de.metas.handlingunits.report.labels.HULabelSourceDocType;
import de.metas.i18n.ExplainedOptional;
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
import de.metas.process.AdProcessId;
import de.metas.product.ProductId;
import de.metas.business.BusinessTestHelper;
import de.metas.quantity.Quantity;
import de.metas.report.PrintCopies;
import de.metas.resource.UserWorkstationService;
import de.metas.util.Services;
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
 * GREEN test — proves that {@code generateFinishGoodsReceiveQRCodes} (Auszeichnung Fertigware)
 * consults {@code M_HU_Label_Config} and uses the configured process when one matches.
 *
 * <p>AC1: when a matching rule exists, the print call uses the rule's process id.
 * <p>AC2: when no rule matches, the print call passes {@code null} process id → the
 * existing global default (584977) path in {@code GlobalQRCodeService} runs unchanged.
 */
class FinishedGoodsLabelConfigTest
{
	private static final HuPackingInstructionsId TEST_PI_ID = HuPackingInstructionsId.ofRepoId(100);

	// ── mocks ───────────────────────────────────────────────────────────────
	private MobileUIManufacturingConfigRepository configRepository;
	private ManufacturingRestService manufacturingRestService;
	private UserWorkstationService userWorkstationService;

	/**
	 * Fully mocked — no real print / Jasper / DB call happens.
	 */
	private HUQRCodesService huQRCodesService;

	/**
	 * Repository-level label config lookup (no sysconfig fallback — AC2 requirement).
	 */
	private HULabelConfigRepository huLabelConfigRepository;

	/**
	 * Mocked so getHU_UnitType(piId) does not hit the DB.
	 */
	private IHandlingUnitsBL handlingUnitsBL;

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
		huLabelConfigRepository = mock(HULabelConfigRepository.class);
		handlingUnitsBL = mock(IHandlingUnitsBL.class);

		// Register handlingUnitsBL mock so Services.get(IHandlingUnitsBL.class) returns it.
		Services.registerService(IHandlingUnitsBL.class, handlingUnitsBL);
		// Stub getHU_UnitType for the test PI id (no DB access needed).
		doReturn(HuUnitType.TU.getCode()).when(handlingUnitsBL).getHU_UnitType(TEST_PI_ID);

		app = new ManufacturingMobileApplication(
				configRepository,
				manufacturingRestService,
				huQRCodesService,
				userWorkstationService,
				huLabelConfigRepository);

		// Wire up manufacturingRestService to return a canned job so the method can
		// reach the print call without a NullPointerException.
		final ManufacturingJob cannedJob = buildCannedManufacturingJob();
		doReturn(cannedJob).when(manufacturingRestService).getJobById(any(PPOrderId.class));

		// Stub generate() — return an empty list (the exact content does not matter for this test).
		doReturn(ImmutableList.of()).when(huQRCodesService).generate(any(HUQRCodeGenerateRequest.class));
	}

	// ─── AC1 — matching rule → configured process id is passed to print ─────────────

	@Test
	void generateFinishGoodsQRCodes_withMatchingLabelConfig_printsConfiguredProcess()
	{
		// given — a matching M_HU_Label_Config rule returns process 999001
		final AdProcessId configuredProcessId = AdProcessId.ofRepoId(999001);
		final HULabelConfig matchingConfig = HULabelConfig.builder()
				.printFormatProcessId(configuredProcessId)
				.autoPrint(true)
				.autoPrintCopies(PrintCopies.ONE)
				.build();
		doReturn(ExplainedOptional.of(matchingConfig))
				.when(huLabelConfigRepository).getFirstMatching(any(HULabelConfigQuery.class));

		// when
		app.generateFinishGoodsReceiveQRCodes(buildRequest());

		// then — repository must be called with sourceDocType == Manufacturing
		final ArgumentCaptor<HULabelConfigQuery> queryCaptor = ArgumentCaptor.forClass(HULabelConfigQuery.class);
		verify(huLabelConfigRepository).getFirstMatching(queryCaptor.capture());
		assertThat(queryCaptor.getValue().getSourceDocType())
				.as("HULabelConfigQuery.sourceDocType must be Manufacturing")
				.isEqualTo(HULabelSourceDocType.Manufacturing);

		// and — print must be called with the configured process id
		final ArgumentCaptor<AdProcessId> processCaptor = ArgumentCaptor.forClass(AdProcessId.class);
		verify(huQRCodesService).print(
				any(),
				processCaptor.capture(),
				any(PrintCopies.class));
		assertThat(processCaptor.getValue())
				.as("print must use the configured LabelReport_Process_ID=999001")
				.isEqualTo(configuredProcessId);
	}

	// ─── AC2 — no matching rule → null processId → existing global default path ────

	@Test
	void generateFinishGoodsQRCodes_withNoMatchingLabelConfig_printsWithNullProcess()
	{
		// given — no M_HU_Label_Config rule matches
		doReturn(ExplainedOptional.emptyBecause("no rule"))
				.when(huLabelConfigRepository).getFirstMatching(any(HULabelConfigQuery.class));

		// when
		app.generateFinishGoodsReceiveQRCodes(buildRequest());

		// then — print must be called with null processId (→ GlobalQRCodeService default 584977)
		final ArgumentCaptor<AdProcessId> processCaptor = ArgumentCaptor.forClass(AdProcessId.class);
		verify(huQRCodesService).print(
				any(),
				processCaptor.capture(),
				any(PrintCopies.class));
		assertThat(processCaptor.getValue())
				.as("print must pass null processId when no label config rule matches (→ global default path)")
				.isNull();
	}

	// ─── helpers ────────────────────────────────────────────────────────────────

	private static JsonFinishGoodsReceiveQRCodesGenerateRequest buildRequest()
	{
		final PPOrderId ppOrderId = PPOrderId.ofRepoId(1001);
		return JsonFinishGoodsReceiveQRCodesGenerateRequest.builder()
				.wfProcessId(WFProcessId.ofIdPart(ManufacturingMobileApplication.APPLICATION_ID, ppOrderId))
				.finishedGoodsReceiveLineId(FinishedGoodsReceiveLineId.FINISHED_GOODS)
				.huPackingInstructionsId(TEST_PI_ID)
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
