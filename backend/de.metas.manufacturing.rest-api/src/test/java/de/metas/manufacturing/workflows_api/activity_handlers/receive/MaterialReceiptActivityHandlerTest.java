package de.metas.manufacturing.workflows_api.activity_handlers.receive;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.attribute.json.JsonAttribute;
import de.metas.handlingunits.attribute.json.JsonAttributeListValue;
import de.metas.handlingunits.attribute.json.JsonAttributeValueType;
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
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_AttributeUse;
import org.compiere.model.I_M_AttributeValue;
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
				new MobileUIManufacturingConfigRepository());

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

	/** Covers AC3, AC8, AC9, AC10, AC11 — the per-line {@code editableAttributes} build (issue #31771 Task 6). */
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
			attributeSet.setMandatoryType(org.adempiere.mm.attributes.AttributeSetMandatoryType.NotMandatory.getCode());
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

		private void createAttributeValue(final I_M_Attribute attribute, final String value)
		{
			final I_M_AttributeValue record = InterfaceWrapperHelper.newInstance(I_M_AttributeValue.class);
			record.setM_Attribute_ID(attribute.getM_Attribute_ID());
			record.setValue(value);
			record.setName("Name_" + value);
			InterfaceWrapperHelper.save(record);
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
		void configuredAttributeInProductAttributeSet_instanceLevel_isIncluded()
		{
			final I_M_Attribute attribute = createAttribute("Color", AttributeValueType.STRING.getCode(), true);
			final I_M_AttributeSet attributeSet = createAttributeSet(attribute);
			final ProductId productId = createProductWithAttributeSet(attributeSet);

			final List<JsonAttribute> result = handler.buildEditableAttributes(
					productId,
					configWithEditableCodes(AttributeCode.ofString("Color")),
					AD_LANGUAGE);

			assertThat(result).extracting(JsonAttribute::getCode).containsExactly(AttributeCode.ofString("Color"));
			assertThat(result.get(0).getCaption()).isEqualTo("Color");
			assertThat(result.get(0).getValueType()).isEqualTo(JsonAttributeValueType.STRING);
			assertThat(result.get(0).getValue()).isNull(); // AC4: no value has been entered yet at this stage
		}

		@Test
		void configuredAttributeNotInProductAttributeSet_isExcluded() // AC8
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

		@Test
		void configuredAttributeNotInstanceLevel_isExcluded() // AC10
		{
			final I_M_Attribute instanceAttr = createAttribute("Color3", AttributeValueType.STRING.getCode(), true);
			final I_M_Attribute productLevelAttr = createAttribute("Weight3", AttributeValueType.NUMBER.getCode(), false);
			final I_M_AttributeSet attributeSet = createAttributeSet(instanceAttr, productLevelAttr);
			final ProductId productId = createProductWithAttributeSet(attributeSet);

			final List<JsonAttribute> result = handler.buildEditableAttributes(
					productId,
					configWithEditableCodes(AttributeCode.ofString("Color3"), AttributeCode.ofString("Weight3")),
					AD_LANGUAGE);

			assertThat(result).extracting(JsonAttribute::getCode).containsExactly(AttributeCode.ofString("Color3"));
		}

		@Test
		void orderedByConfigSeqNoOrder_notByAttributeSetInsertionOrder() // AC11
		{
			final I_M_Attribute attr1 = createAttribute("Attr1x", AttributeValueType.STRING.getCode(), true);
			final I_M_Attribute attr2 = createAttribute("Attr2x", AttributeValueType.STRING.getCode(), true);
			// attribute-set child rows created in reverse ("Attr2x, Attr1x") order - the config's SeqNo order must still win
			final I_M_AttributeSet attributeSet = createAttributeSet(attr2, attr1);
			final ProductId productId = createProductWithAttributeSet(attributeSet);

			final List<JsonAttribute> result = handler.buildEditableAttributes(
					productId,
					configWithEditableCodes(AttributeCode.ofString("Attr1x"), AttributeCode.ofString("Attr2x")),
					AD_LANGUAGE);

			assertThat(result).extracting(JsonAttribute::getCode)
					.containsExactly(AttributeCode.ofString("Attr1x"), AttributeCode.ofString("Attr2x"));
		}

		@Test
		void coProductLine_differentProduct_alsoGetsOwnEditableAttributes() // AC9
		{
			final I_M_Attribute colorAttr = createAttribute("Color4", AttributeValueType.STRING.getCode(), true);

			final I_M_AttributeSet mainSet = createAttributeSet(colorAttr);
			final ProductId mainProductId = createProductWithAttributeSet(mainSet);

			final I_M_AttributeSet coProductSet = createAttributeSet(colorAttr);
			final ProductId coProductId = createProductWithAttributeSet(coProductSet);

			final MobileUIManufacturingConfig config = configWithEditableCodes(AttributeCode.ofString("Color4"));

			assertThat(handler.buildEditableAttributes(mainProductId, config, AD_LANGUAGE))
					.extracting(JsonAttribute::getCode).containsExactly(AttributeCode.ofString("Color4"));
			assertThat(handler.buildEditableAttributes(coProductId, config, AD_LANGUAGE))
					.extracting(JsonAttribute::getCode).containsExactly(AttributeCode.ofString("Color4"));
		}

		@Test
		void listAttribute_carriesAllowedListValues()
		{
			final I_M_Attribute sizeAttr = createAttribute("Size5", AttributeValueType.LIST.getCode(), true);
			createAttributeValue(sizeAttr, "S");
			createAttributeValue(sizeAttr, "M");
			final I_M_AttributeSet attributeSet = createAttributeSet(sizeAttr);
			final ProductId productId = createProductWithAttributeSet(attributeSet);

			final List<JsonAttribute> result = handler.buildEditableAttributes(
					productId,
					configWithEditableCodes(AttributeCode.ofString("Size5")),
					AD_LANGUAGE);

			assertThat(result).hasSize(1);
			final JsonAttribute jsonAttribute = result.get(0);
			assertThat(jsonAttribute.getValueType()).isEqualTo(JsonAttributeValueType.LIST);
			assertThat(jsonAttribute.getListValues())
					.extracting(JsonAttributeListValue::getValue)
					.containsExactlyInAnyOrder("S", "M");
		}

		@Test
		void noConfiguredAttributes_returnsEmpty()
		{
			final I_M_Attribute attribute = createAttribute("Color6", AttributeValueType.STRING.getCode(), true);
			final I_M_AttributeSet attributeSet = createAttributeSet(attribute);
			final ProductId productId = createProductWithAttributeSet(attributeSet);

			final List<JsonAttribute> result = handler.buildEditableAttributes(productId, configWithEditableCodes(), AD_LANGUAGE);

			assertThat(result).isEmpty();
		}

		@Test
		void productWithNoAttributeSet_returnsEmpty()
		{
			final List<JsonAttribute> result = handler.buildEditableAttributes(
					productId, // the outer class's plain product, created without any M_AttributeSet
					configWithEditableCodes(AttributeCode.ofString("Color7")),
					AD_LANGUAGE);

			assertThat(result).isEmpty();
		}
	}
}
