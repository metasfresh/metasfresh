package de.metas.manufacturing.workflows_api.activity_handlers.receive;

import com.google.common.collect.ImmutableList;
import de.metas.manufacturing.config.MobileUIManufacturingConfig;
import de.metas.product.ProductId;
import de.metas.util.OptionalBoolean;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeSetMandatoryType;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.mm.attributes.api.AttributeConstants;
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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit coverage for the receive-time editable-attribute allow-list and the two fail-loud guards: the generic-channel
 * allow-list check and the dual-channel conflict check. The full receive flow is covered by the mobile Playwright
 * suite and the {@code Receive_with_CatchWeight.feature} cucumber scenarios; here we pin the pure guard logic.
 */
class MaterialReceiptEditableAttributesTest
{
	private static final AttributeCode CODE_GENERIC = AttributeCode.ofString("GenericTestAttr");
	private static final AttributeCode CODE_NOT_ON_SET = AttributeCode.ofString("NotOnThisProductsSet");

	private MaterialReceiptEditableAttributes editableAttributes;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		editableAttributes = new MaterialReceiptEditableAttributes();
	}

	private static I_M_Attribute createAttribute(final AttributeCode code, final boolean isInstanceAttribute)
	{
		final I_M_Attribute record = InterfaceWrapperHelper.newInstance(I_M_Attribute.class);
		record.setValue(code.getCode());
		record.setName(code.getCode());
		record.setAttributeValueType(AttributeValueType.STRING.getCode());
		record.setIsInstanceAttribute(isInstanceAttribute);
		InterfaceWrapperHelper.save(record);
		return record;
	}

	private static I_M_AttributeSet createAttributeSet(final I_M_Attribute... attributes)
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

	/** getAttributeSetId resolves the set from the product's CATEGORY, so the set must be wired on the category. */
	private static ProductId createProductWithAttributeSet(final I_M_AttributeSet attributeSet)
	{
		final I_M_Product_Category category = InterfaceWrapperHelper.newInstance(I_M_Product_Category.class);
		category.setName("Category " + attributeSet.getM_AttributeSet_ID());
		category.setM_AttributeSet_ID(attributeSet.getM_AttributeSet_ID());
		InterfaceWrapperHelper.save(category);

		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setValue("PROD-" + System.nanoTime());
		product.setName("Product " + attributeSet.getM_AttributeSet_ID());
		product.setM_Product_Category_ID(category.getM_Product_Category_ID());
		InterfaceWrapperHelper.save(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	private static MobileUIManufacturingConfig configWithEditableCodes(final AttributeCode... codes)
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

	@Nested
	class assertOnlyEditableAttributesSubmitted
	{
		@Test
		void configuredInstanceAttributeOnProductSet_isAccepted()
		{
			final I_M_Attribute generic = createAttribute(CODE_GENERIC, true);
			final ProductId productId = createProductWithAttributeSet(createAttributeSet(generic));
			final MobileUIManufacturingConfig config = configWithEditableCodes(CODE_GENERIC);

			assertThatCode(() -> editableAttributes.assertOnlyEditableAttributesSubmitted(
					productId, config, Collections.singleton(CODE_GENERIC)))
					.doesNotThrowAnyException();
		}

		@Test
		void notConfiguredCode_isRejected()
		{
			final I_M_Attribute generic = createAttribute(CODE_GENERIC, true);
			final ProductId productId = createProductWithAttributeSet(createAttributeSet(generic));
			// GenericTestAttr is an instance attribute of the product's set, but the config lists ONLY Lot-Nummer.
			final MobileUIManufacturingConfig config = configWithEditableCodes(AttributeConstants.ATTR_LotNumber);

			assertThatThrownBy(() -> editableAttributes.assertOnlyEditableAttributesSubmitted(
					productId, config, Collections.singleton(CODE_GENERIC)))
					.isInstanceOf(AdempiereException.class);
		}

		@Test
		void codeNotOnProductSet_isRejected()
		{
			final I_M_Attribute generic = createAttribute(CODE_GENERIC, true);
			final ProductId productId = createProductWithAttributeSet(createAttributeSet(generic));
			// Configured, but NotOnThisProductsSet is not part of the product's attribute set.
			final MobileUIManufacturingConfig config = configWithEditableCodes(CODE_GENERIC, CODE_NOT_ON_SET);

			assertThatThrownBy(() -> editableAttributes.assertOnlyEditableAttributesSubmitted(
					productId, config, Collections.singleton(CODE_NOT_ON_SET)))
					.isInstanceOf(AdempiereException.class);
		}

		@Test
		void emptySubmission_isAccepted()
		{
			final ProductId productId = createProductWithAttributeSet(createAttributeSet());
			final MobileUIManufacturingConfig config = configWithEditableCodes(CODE_GENERIC);

			assertThatCode(() -> editableAttributes.assertOnlyEditableAttributesSubmitted(
					productId, config, Collections.emptySet()))
					.doesNotThrowAnyException();
		}

		@Test
		void getEditableAttributeCodes_isConfiguredIntersectInstanceSet()
		{
			final I_M_Attribute generic = createAttribute(CODE_GENERIC, true);
			final ProductId productId = createProductWithAttributeSet(createAttributeSet(generic));
			final MobileUIManufacturingConfig config = configWithEditableCodes(CODE_GENERIC, CODE_NOT_ON_SET);

			assertThat(editableAttributes.getEditableAttributeCodes(productId, config))
					.containsExactly(CODE_GENERIC);
		}
	}

	@Nested
	class assertNoDualChannelConflict
	{
		private Map<AttributeCode, String> genericMap(final AttributeCode code, final String value)
		{
			final Map<AttributeCode, String> map = new HashMap<>();
			map.put(code, value);
			return map;
		}

		@Test
		void lotInBothChannels_isRejected()
		{
			assertThatThrownBy(() -> MaterialReceiptEditableAttributes.assertNoDualChannelConflict(
					"DedicatedLot", null, null, genericMap(AttributeConstants.ATTR_LotNumber, "MapLot")))
					.isInstanceOf(AdempiereException.class);
		}

		@Test
		void bestBeforeInBothChannels_isRejected()
		{
			assertThatThrownBy(() -> MaterialReceiptEditableAttributes.assertNoDualChannelConflict(
					null, "2025-03-03", null, genericMap(AttributeConstants.ATTR_BestBeforeDate, "2025-09-09")))
					.isInstanceOf(AdempiereException.class);
		}

		@Test
		void productionDateInBothChannels_isRejected()
		{
			assertThatThrownBy(() -> MaterialReceiptEditableAttributes.assertNoDualChannelConflict(
					null, null, "2025-01-10", genericMap(AttributeConstants.ProductionDate, "2025-11-20")))
					.isInstanceOf(AdempiereException.class);
		}

		@Test
		void dedicatedFieldOnly_isAccepted()
		{
			assertThatCode(() -> MaterialReceiptEditableAttributes.assertNoDualChannelConflict(
					"DedicatedLot", null, null, Collections.emptyMap()))
					.doesNotThrowAnyException();
		}

		@Test
		void genericOnly_isAccepted()
		{
			assertThatCode(() -> MaterialReceiptEditableAttributes.assertNoDualChannelConflict(
					null, null, null, genericMap(AttributeConstants.ATTR_LotNumber, "MapLot")))
					.doesNotThrowAnyException();
		}

		@Test
		void blankGenericValue_isNotAConflict()
		{
			assertThatCode(() -> MaterialReceiptEditableAttributes.assertNoDualChannelConflict(
					"DedicatedLot", null, null, genericMap(AttributeConstants.ATTR_LotNumber, "  ")))
					.doesNotThrowAnyException();
		}
	}
}
