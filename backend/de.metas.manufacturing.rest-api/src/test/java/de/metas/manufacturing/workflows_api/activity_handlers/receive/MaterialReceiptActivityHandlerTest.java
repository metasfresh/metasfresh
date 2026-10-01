package de.metas.manufacturing.workflows_api.activity_handlers.receive;

import com.google.common.collect.ImmutableList;
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

import java.util.List;

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
}
