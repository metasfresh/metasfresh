package de.metas.manufacturing.workflows_api.activity_handlers.receive;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.business.BusinessTestHelper;
import de.metas.common.util.time.SystemTime;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.DocumentNoBuilderFactory;
import de.metas.handlingunits.pporder.api.impl.HUPPOrderBL_LotNumberFromSequence_Test;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.i18n.TranslatableStrings;
import de.metas.manufacturing.config.MobileUIManufacturingConfig;
import de.metas.manufacturing.config.MobileUIManufacturingConfigRepository;
import de.metas.manufacturing.job.model.FinishedGoodsReceive;
import de.metas.manufacturing.job.model.FinishedGoodsReceiveLine;
import de.metas.manufacturing.job.model.FinishedGoodsReceiveLineId;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.job.model.ManufacturingJobActivity;
import de.metas.manufacturing.job.model.ManufacturingJobActivityId;
import de.metas.manufacturing.workflows_api.ManufacturingRestService;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonFinishedGoodsReceiveLine;
import de.metas.material.planning.pporder.PPAlwaysAvailableToUser;
import de.metas.material.planning.pporder.PPRoutingActivityType;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.allergen.ProductAllergensService;
import de.metas.product.hazard_symbol.ProductHazardSymbolService;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.scannable_code.format.service.ScannableCodeFormatService;
import de.metas.util.OptionalBoolean;
import de.metas.util.Services;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import de.metas.workflow.rest_api.model.WFProcess;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderRoutingActivityId;
import org.eevolution.api.PPOrderRoutingActivityStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The receive activity of a manufacturing order with a main finished good AND a co-product: every line gets its own defaults.
 */
class MaterialReceiptActivityHandlerTest
{
	private MaterialReceiptActivityHandler handler;
	private I_C_UOM uom;
	private UomId catchWeightUomId;
	private PPOrderId ppOrderId;
	private MobileUIManufacturingConfigRepository configRepository;
	private MobileUIManufacturingConfig config;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		Services.registerService(IDocumentNoBuilderFactory.class, DocumentNoBuilderFactory.newInstanceForUnitTesting());
		Services.get(ISysConfigBL.class).setValue("de.metas.document.seqNo.DateSequenceProvider.dateFormat", "yy-DDD", ClientId.SYSTEM, OrgId.ANY);
		SystemTime.setFixedTimeSource(ZonedDateTime.parse("2026-10-19T12:00:00Z"));

		uom = BusinessTestHelper.createUomPCE();
		catchWeightUomId = UomId.ofRepoId(BusinessTestHelper.createUomKg().getC_UOM_ID());
		ppOrderId = HUPPOrderBL_LotNumberFromSequence_Test.createPPOrderBOM(
				HUPPOrderBL_LotNumberFromSequence_Test.createPPOrder(AdempiereTestHelper.createOrgWithTimeZone(ZoneId.of("UTC"))),
				HUPPOrderBL_LotNumberFromSequence_Test.createDateLotSequence());

		configRepository = mock(MobileUIManufacturingConfigRepository.class);
		config = MobileUIManufacturingConfig.builder()
				.isScanResourceRequired(OptionalBoolean.UNKNOWN)
				.isAllowIssuingAnyHU(OptionalBoolean.UNKNOWN)
				.isBestBeforeDateEditable(OptionalBoolean.TRUE)
				.isLotNumberEditable(OptionalBoolean.TRUE)
				.isAllowFinishedGoodsReceiveToLU(OptionalBoolean.UNKNOWN)
				.isAllowFinishedGoodsReceiveToTU(OptionalBoolean.UNKNOWN)
				.isSkipFinishedGoodsReceiveTargetStep(OptionalBoolean.UNKNOWN)
				.isCaptureCatchWeightAtReceipt(OptionalBoolean.UNKNOWN)
				.isFinishedGoodsCatchWeightScanRequired(OptionalBoolean.TRUE)
				.build();
		when(configRepository.getConfig(any(), any())).thenReturn(config);

		handler = new MaterialReceiptActivityHandler(
				mock(HUQRCodesService.class),
				mock(ProductHazardSymbolService.class),
				mock(ProductAllergensService.class),
				ScannableCodeFormatService.newInstanceForUnitTesting(),
				configRepository);
	}

	@AfterEach
	void afterEach()
	{
		SystemTime.resetTimeSource();
	}

	// Both products are catch weight: the scan requirement differs per line only because one is a co-product.
	@Test
	void mainFinishedGoodAndCoProduct_perLineDefaults()
	{
		final ProductId mainProductId = createProduct("main", 10);
		final ProductId coProductId = createProduct("co", 3);
		final WFProcess wfProcess = ManufacturingRestService.toWFProcess(createJob(
				receiveLine(mainProductId, null),
				receiveLine(coProductId, PPOrderBOMLineId.ofRepoId(1))));

		final List<JsonFinishedGoodsReceiveLine> lines = getLines(wfProcess);

		assertThat(lines).hasSize(2);
		final JsonFinishedGoodsReceiveLine mainLine = lines.get(0);
		assertThat(mainLine.isCoproduct()).isFalse();
		assertThat(mainLine.getDefaultBestBeforeDate()).isEqualTo(LocalDate.of(2026, 10, 29)); // 2026-10-19 + 10 days
		assertThat(mainLine.isCatchWeightScanRequired()).isTrue();
		assertThat(mainLine.getDefaultLotNo()).isEqualTo("26-292");

		final JsonFinishedGoodsReceiveLine coProductLine = lines.get(1);
		assertThat(coProductLine.isCoproduct()).isTrue();
		assertThat(coProductLine.getDefaultBestBeforeDate()).isEqualTo(LocalDate.of(2026, 10, 22)); // 2026-10-19 + 3 days
		assertThat(coProductLine.isCatchWeightScanRequired()).isFalse();
		assertThat(coProductLine.getDefaultLotNo()).isEqualTo("26-292"); // the lot is per order, not per line
	}

	/**
	 * Weight captured at picking, not at receipt: the main finished good still requires the scan (for lot and best-before date),
	 * but no weight UOM is sent, so the client neither shows nor sends a weight.
	 */
	@Test
	void catchWeightNotCapturedAtReceipt_scanStillRequired_noWeightUom()
	{
		when(configRepository.getConfig(any(), any())).thenReturn(config.toBuilder()
				.isCaptureCatchWeightAtReceipt(OptionalBoolean.FALSE)
				.build());
		final ProductId mainProductId = createProduct("main", 10);
		final ProductId coProductId = createProduct("co", 3);
		final WFProcess wfProcess = ManufacturingRestService.toWFProcess(createJob(
				receiveLine(mainProductId, null),
				receiveLine(coProductId, PPOrderBOMLineId.ofRepoId(1))));

		final List<JsonFinishedGoodsReceiveLine> lines = getLines(wfProcess);

		final JsonFinishedGoodsReceiveLine mainLine = lines.get(0);
		assertThat(mainLine.isCatchWeightScanRequired()).isTrue();
		assertThat(mainLine.getCatchWeightUomSymbol()).isNull();

		final JsonFinishedGoodsReceiveLine coProductLine = lines.get(1);
		assertThat(coProductLine.isCatchWeightScanRequired()).isFalse();
		assertThat(coProductLine.getCatchWeightUomSymbol()).isNull();
	}

	@SuppressWarnings("unchecked")
	private List<JsonFinishedGoodsReceiveLine> getLines(final WFProcess wfProcess)
	{
		return (List<JsonFinishedGoodsReceiveLine>)handler.getUIComponent(wfProcess, wfProcess.getActivities().get(0), JsonOpts.ofAdLanguage("en_US"))
				.getProperties()
				.getParameterAsObject("lines");
	}

	private ManufacturingJob createJob(final FinishedGoodsReceiveLine... lines)
	{
		final ImmutableMap.Builder<FinishedGoodsReceiveLineId, FinishedGoodsReceiveLine> linesById = ImmutableMap.builder();
		for (final FinishedGoodsReceiveLine line : lines)
		{
			linesById.put(line.getId(), line);
		}

		final PPOrderRoutingActivityId routingActivityId = PPOrderRoutingActivityId.ofRepoId(ppOrderId, 1);
		return ManufacturingJob.builder()
				.ppOrderId(ppOrderId)
				.documentNo("PP1")
				.dateStartSchedule(SystemTime.asZonedDateTime())
				.warehouseId(WarehouseId.ofRepoId(1))
				.activities(ImmutableList.of(ManufacturingJobActivity.builder()
						.id(ManufacturingJobActivityId.of(routingActivityId))
						.name("Receive")
						.type(PPRoutingActivityType.MaterialReceipt)
						.finishedGoodsReceive(FinishedGoodsReceive.builder().linesById(linesById.build()).build())
						.orderRoutingActivityId(routingActivityId)
						.routingActivityStatus(PPOrderRoutingActivityStatus.NOT_STARTED)
						.alwaysAvailableToUser(PPAlwaysAvailableToUser.DEFAULT)
						.build()))
				.build();
	}

	private FinishedGoodsReceiveLine receiveLine(final ProductId productId, final PPOrderBOMLineId coProductBOMLineId)
	{
		return FinishedGoodsReceiveLine.builder()
				.productId(productId)
				.productName(TranslatableStrings.anyLanguage("product"))
				.productValue("P")
				.attributes(ImmutableAttributeSet.EMPTY)
				.qtyToReceive(Quantity.of(BigDecimal.TEN, uom))
				.qtyReceived(Quantity.zero(uom))
				.coProductBOMLineId(coProductBOMLineId)
				.catchWeightUOMId(catchWeightUomId)
				.build();
	}

	private ProductId createProduct(final String name, final int bestBeforeDays)
	{
		final ProductId productId = BusinessTestHelper.createProductId(name, uom);
		final I_M_Product product = load(productId, I_M_Product.class);
		product.setBestBeforeDays(bestBeforeDays);
		saveRecord(product);
		return productId;
	}
}
