package de.metas.handlingunits.picking.job.service.external.product;

import de.metas.product.ProductId;
import de.metas.product.ProductType;
import org.adempiere.mm.attributes.AttributesTestHelper;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_Attribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit coverage for {@link PickingJobProductService#isSerialNoPickingEnabled(ProductId)}.
 * <p>
 * The serial-no picking prompt is driven by the {@code M_Product.IsSerialNoPicked} checkbox alone (plus the
 * defensive system-wide {@code SerialNo}-attribute-defined guard). The product's own attribute set is irrelevant
 * to whether the prompt appears — the picked HU's ability to store the {@code SerialNo} comes from the PI wiring,
 * not from the product attribute set.
 */
@ExtendWith(AdempiereTestWatcher.class)
class PickingJobProductServiceTest
{
	private AttributesTestHelper attributesTestHelper;
	private PickingJobProductService pickingJobProductService;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		attributesTestHelper = new AttributesTestHelper();
		pickingJobProductService = PickingJobProductService.newInstanceForUnitTesting();
	}

	private ProductId createProduct(final boolean serialNoPicked)
	{
		// mirrors CreateProductCommand — no attribute set is assigned, by design.
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setValue("P1");
		product.setName("P1");
		product.setProductType(ProductType.Item.getCode());
		product.setIsSerialNoPicked(serialNoPicked);
		InterfaceWrapperHelper.saveRecord(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	/** Ensures the system-wide {@code SerialNo} attribute is defined and active (the defensive guard's precondition). */
	private void givenSerialNoAttributeDefined()
	{
		attributesTestHelper.createM_Attribute(
				AttributeConstants.ATTR_SerialNo_String,
				X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40,
				true);
	}

	@Test
	void flagSet_noAttributeSet_enabled()
	{
		givenSerialNoAttributeDefined();
		// Product flagged IsSerialNoPicked=Y but with NO attribute set (M_AttributeSet_ID stays None).
		final ProductId productId = createProduct(true);

		assertThat(pickingJobProductService.isSerialNoPickingEnabled(productId)).isTrue();
	}

	@Test
	void flagNotSet_disabled()
	{
		givenSerialNoAttributeDefined();
		final ProductId productId = createProduct(false);

		assertThat(pickingJobProductService.isSerialNoPickingEnabled(productId)).isFalse();
	}

	@Test
	void flagSet_serialNoAttributeUndefined_disabled()
	{
		// No SerialNo M_Attribute defined system-wide → the defensive guard short-circuits to false.
		final ProductId productId = createProduct(true);

		assertThat(pickingJobProductService.isSerialNoPickingEnabled(productId)).isFalse();
	}
}
