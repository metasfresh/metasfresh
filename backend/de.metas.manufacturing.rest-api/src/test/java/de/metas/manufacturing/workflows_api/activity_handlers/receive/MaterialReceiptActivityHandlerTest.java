package de.metas.manufacturing.workflows_api.activity_handlers.receive;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.business.BusinessTestHelper;
import de.metas.common.util.time.SystemTime;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.DocumentNoBuilderFactory;
import de.metas.handlingunits.pporder.api.impl.HUPPOrderBL_LotNumberFromSequence_Test;
import de.metas.i18n.TranslatableStrings;
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
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.workflow.rest_api.controller.v2.json.JsonOpts;
import de.metas.workflow.rest_api.model.WFProcess;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderRoutingActivityId;
import org.eevolution.api.PPOrderRoutingActivityStatus;
import org.junit.jupiter.api.AfterEach;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.attribute.json.JsonAttribute;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.manufacturing.config.MobileUIManufacturingConfig;
import de.metas.manufacturing.config.MobileUIManufacturingConfigRepository;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonNewLUTargetsList;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonNewTUTarget;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonNewTUTargetList;
import de.metas.product.ProductId;
import de.metas.product.allergen.AllergenRepository;
import de.metas.product.allergen.ProductAllergensRepository;
import de.metas.product.allergen.ProductAllergensService;
import de.metas.product.hazard_symbol.HazardSymbolRepository;
import de.metas.product.hazard_symbol.ProductHazardSymbolRepository;
import de.metas.product.hazard_symbol.ProductHazardSymbolService;
import de.metas.scannable_code.format.service.ScannableCodeFormatService;
import de.metas.util.OptionalBoolean;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeSetMandatoryType;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_AttributeUse;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/** Covers the target OFFERING only; receiving itself is covered by the mobile Playwright suite. */
class MaterialReceiptActivityHandlerTest
{
	private static final String AD_LANGUAGE = "en_US";

	private MaterialReceiptActivityHandler handler;
	private ProductId productId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		// Real collaborators, not mocks: surefire runs on Java 17, where the pinned ByteBuddy cannot subclass.
		// Harmless here - the target-offering paths reach their own collaborators via Services.get(...) instead.
		handler = new MaterialReceiptActivityHandler(
				HUQRCodesService.newInstanceForUnitTesting(),
				new ProductHazardSymbolService(new ProductHazardSymbolRepository(), new HazardSymbolRepository()),
				new ProductAllergensService(new ProductAllergensRepository(), new AllergenRepository()),
				ScannableCodeFormatService.newInstanceForUnitTesting(),
				new MobileUIManufacturingConfigRepository(),
				new MaterialReceiptEditableAttributes());

		productId = createProduct();
		createVirtualPIItemProduct();
	}

	private static ProductId createProduct()
	{
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setValue("1000007");
		product.setName("Feldahorn H 4xv mDb");
		InterfaceWrapperHelper.save(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	/** The packing instruction retrieveTUs can never return, being HU_UnitType='V'. */
	private static void createVirtualPIItemProduct()
	{
		final I_M_HU_PI_Item_Product piip = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class);
		piip.setM_HU_PI_Item_Product_ID(HUPIItemProductId.VIRTUAL_HU.getRepoId());
		piip.setName("No Packing Item");
		InterfaceWrapperHelper.save(piip);
	}

	/** The reported case: the product has no physical TU packing at all. */
	private static List<I_M_HU_PI_Item_Product> noPhysicalTUs()
	{
		return ImmutableList.of();
	}

	@Nested
	class getNewTUTargets
	{
		@Test
		void flagOff_noPhysicalTU_offersNothingAndExplainsWhy()
		{
			final JsonNewTUTargetList result = handler.getNewTUTargets(noPhysicalTUs(), false, productId, AD_LANGUAGE);

			assertThat(result.getValues()).isEmpty();
			assertThat(result.getEmptyReason()).isNotBlank();
		}

		@Test
		void flagOn_noPhysicalTU_offersTheVirtualPackingInstruction()
		{
			final JsonNewTUTargetList result = handler.getNewTUTargets(noPhysicalTUs(), true, productId, AD_LANGUAGE);

			assertThat(result.getValues())
					.extracting(JsonNewTUTarget::getTuPIItemProductId)
					.containsExactly(HUPIItemProductId.VIRTUAL_HU);
		}

		@Test
		void flagOn_noPhysicalTU_carriesNoEmptyReason()
		{
			// The guidance says "fix the master data" — misleading while a target is on screen.
			final JsonNewTUTargetList result = handler.getNewTUTargets(noPhysicalTUs(), true, productId, AD_LANGUAGE);

			assertThat(result.getEmptyReason()).isNull();
		}
	}

	@Nested
	class getNewLUTargets
	{
		@Test
		void flagOff_noPhysicalTU_explainsThatNoGebindeCanBeOffered()
		{
			final JsonNewLUTargetsList result = handler.getNewLUTargets(noPhysicalTUs(), false, productId, null, AD_LANGUAGE);

			assertThat(result.getValues()).isEmpty();
			assertThat(result.getEmptyReason()).isNotBlank();
		}

		@Test
		void flagOn_noPhysicalTU_staysSilentBecauseTheTUListOffersATarget()
		{
			// Legitimately empty (no LU parent items), but a target exists — and the guidance must accompany
			// "no target at all" only.
			final JsonNewLUTargetsList result = handler.getNewLUTargets(noPhysicalTUs(), true, productId, null, AD_LANGUAGE);

			assertThat(result.getValues()).isEmpty();
			assertThat(result.getEmptyReason()).isNull();
		}
	}

	/**
	 * The per-line {@code editableAttributes} build — here only the restriction to the product's own
	 * {@code M_AttributeSet} (a configured code not on this product's set is excluded). The other facets
	 * (instance-level filtering, {@code SeqNo} ordering, per-line / co-product independence) are
	 * UI-observable and covered by the mobile Playwright suite, not here.
	 */
	@Nested
	class buildEditableAttributes
	{
		/**
		 * POJO-only, no-DB variant of the {@code de.metas.business} test fixture ({@code AttributesTestHelper}
		 * cannot be reused here: it goes through {@code InterfaceWrapperHelper.create(ctx, class, trxName)}, which
		 * needs a real DB connection for {@code POInfo} - this module's unit tests run fully in-memory).
		 */
		private I_M_Attribute createAttribute(final String code, final String valueTypeCode, final boolean isInstanceAttribute)
		{
			final I_M_Attribute record = InterfaceWrapperHelper.newInstance(I_M_Attribute.class);
			record.setValue(code);
			record.setName(code);
			record.setAttributeValueType(valueTypeCode);
			record.setIsInstanceAttribute(isInstanceAttribute);
			InterfaceWrapperHelper.save(record);
			return record;
		}

		private I_M_AttributeSet createAttributeSet(final I_M_Attribute... attributes)
		{
			final I_M_AttributeSet attributeSet = InterfaceWrapperHelper.newInstance(I_M_AttributeSet.class);
			attributeSet.setName("AttributeSet");
			attributeSet.setMandatoryType(AttributeSetMandatoryType.NotMandatory.getCode());
			InterfaceWrapperHelper.save(attributeSet);

			for (final I_M_Attribute attribute : attributes)
			{
				final I_M_AttributeUse attributeUse = InterfaceWrapperHelper.newInstance(I_M_AttributeUse.class);
				attributeUse.setM_AttributeSet_ID(attributeSet.getM_AttributeSet_ID());
				attributeUse.setM_Attribute_ID(attribute.getM_Attribute_ID());
				InterfaceWrapperHelper.save(attributeUse);
			}
			return attributeSet;
		}

		private ProductId createProductWithAttributeSet(final I_M_AttributeSet attributeSet)
		{
			final I_M_Product_Category category = InterfaceWrapperHelper.newInstance(I_M_Product_Category.class);
			category.setName("Category for AttributeSet " + attributeSet.getM_AttributeSet_ID());
			category.setM_AttributeSet_ID(attributeSet.getM_AttributeSet_ID());
			InterfaceWrapperHelper.save(category);

			final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
			product.setValue("PROD-" + attributeSet.getM_AttributeSet_ID() + "-" + System.nanoTime());
			product.setName("Product for AttributeSet " + attributeSet.getM_AttributeSet_ID());
			product.setM_Product_Category_ID(category.getM_Product_Category_ID());
			InterfaceWrapperHelper.save(product);
			return ProductId.ofRepoId(product.getM_Product_ID());
		}

		private MobileUIManufacturingConfig configWithEditableCodes(final AttributeCode... codes)
		{
			return MobileUIManufacturingConfig.builder()
					.isScanResourceRequired(OptionalBoolean.UNKNOWN)
					.isAllowIssuingAnyHU(OptionalBoolean.UNKNOWN)
					.receiveUnitType(null)
					.editableAttributeCodesInOrder(ImmutableList.copyOf(codes))
					.isAllowFinishedGoodsReceiveToLU(OptionalBoolean.UNKNOWN)
					.isAllowFinishedGoodsReceiveToTU(OptionalBoolean.UNKNOWN)
					.isSkipFinishedGoodsReceiveTargetStep(OptionalBoolean.UNKNOWN)
					.isCaptureCatchWeightAtReceipt(OptionalBoolean.UNKNOWN)
					.isAllowReceiveWithoutPackingItem(OptionalBoolean.UNKNOWN)
					.build();
		}

		@Test
		void configuredAttributeNotInProductAttributeSet_isExcluded()
		{
			final I_M_Attribute inSet = createAttribute("Color2", AttributeValueType.STRING.getCode(), true);
			final I_M_AttributeSet attributeSet = createAttributeSet(inSet);
			final ProductId productId = createProductWithAttributeSet(attributeSet);

			final List<JsonAttribute> result = handler.buildEditableAttributes(
					productId,
					configWithEditableCodes(AttributeCode.ofString("Color2"), AttributeCode.ofString("NotOnThisProductsSet")),
					AD_LANGUAGE);

			assertThat(result).extracting(JsonAttribute::getCode).containsExactly(AttributeCode.ofString("Color2"));
		}
	}

	/**
	 * The receive activity of a manufacturing order with a main finished good AND a co-product: every line gets its own defaults.
	 */
	@Nested
	class perLineDefaults
	{
		private MaterialReceiptActivityHandler handler;
		private I_C_UOM uom;
		private UomId catchWeightUomId;
		private PPOrderId ppOrderId;

		@BeforeEach
		void init()
		{
			Services.registerService(IDocumentNoBuilderFactory.class, DocumentNoBuilderFactory.newInstanceForUnitTesting());
			Services.get(ISysConfigBL.class).setValue("de.metas.document.seqNo.DateSequenceProvider.dateFormat", "yy-DDD", ClientId.SYSTEM, OrgId.ANY);
			SystemTime.setFixedTimeSource(ZonedDateTime.parse("2026-10-19T12:00:00Z"));

			uom = BusinessTestHelper.createUomPCE();
			catchWeightUomId = UomId.ofRepoId(BusinessTestHelper.createUomKg().getC_UOM_ID());
			ppOrderId = HUPPOrderBL_LotNumberFromSequence_Test.createPPOrderBOM(
					HUPPOrderBL_LotNumberFromSequence_Test.createPPOrder(AdempiereTestHelper.createOrgWithTimeZone(ZoneId.of("UTC"))),
					HUPPOrderBL_LotNumberFromSequence_Test.createDateLotSequence());

			final MobileUIManufacturingConfig config = MobileUIManufacturingConfig.builder()
					.isScanResourceRequired(OptionalBoolean.UNKNOWN)
					.isAllowIssuingAnyHU(OptionalBoolean.UNKNOWN)
					.editableAttributeCodesInOrder(ImmutableList.of(AttributeConstants.ATTR_BestBeforeDate, AttributeConstants.ATTR_LotNumber))
					.isAllowFinishedGoodsReceiveToLU(OptionalBoolean.UNKNOWN)
					.isAllowFinishedGoodsReceiveToTU(OptionalBoolean.UNKNOWN)
					.isSkipFinishedGoodsReceiveTargetStep(OptionalBoolean.UNKNOWN)
					.isCaptureCatchWeightAtReceipt(OptionalBoolean.UNKNOWN)
					.isAllowReceiveWithoutPackingItem(OptionalBoolean.UNKNOWN)
					.isFinishedGoodsCatchWeightScanRequired(OptionalBoolean.TRUE)
					.build();

			// A plain subclass instead of a mock: see the outer class's beforeEach.
			final MobileUIManufacturingConfigRepository configRepository = new MobileUIManufacturingConfigRepository()
			{
				@Override
				public MobileUIManufacturingConfig getConfig(@Nullable final UserId userId, @NonNull final ClientId clientId) {return config;}
			};

			handler = new MaterialReceiptActivityHandler(
					HUQRCodesService.newInstanceForUnitTesting(),
					new ProductHazardSymbolService(new ProductHazardSymbolRepository(), new HazardSymbolRepository()),
					new ProductAllergensService(new ProductAllergensRepository(), new AllergenRepository()),
					ScannableCodeFormatService.newInstanceForUnitTesting(),
					configRepository,
					new MaterialReceiptEditableAttributes());
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
			final ProductId mainProductId = createProductWithBestBeforeDays("main", 10);
			final ProductId coProductId = createProductWithBestBeforeDays("co", 3);
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

		@SuppressWarnings("unchecked")
		private List<JsonFinishedGoodsReceiveLine> getLines(final WFProcess wfProcess)
		{
			return (List<JsonFinishedGoodsReceiveLine>)handler.getUIComponent(wfProcess, wfProcess.getActivities().get(0), JsonOpts.ofAdLanguage(AD_LANGUAGE))
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

		private FinishedGoodsReceiveLine receiveLine(final ProductId productId, @Nullable final PPOrderBOMLineId coProductBOMLineId)
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

		private ProductId createProductWithBestBeforeDays(final String name, final int bestBeforeDays)
		{
			final ProductId productId = BusinessTestHelper.createProductId(name, uom);
			final I_M_Product product = load(productId, I_M_Product.class);
			product.setBestBeforeDays(bestBeforeDays);
			saveRecord(product);
			return productId;
		}
	}
}
