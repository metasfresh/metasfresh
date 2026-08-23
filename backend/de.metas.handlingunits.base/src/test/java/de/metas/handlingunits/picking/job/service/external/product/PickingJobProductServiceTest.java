package de.metas.handlingunits.picking.job.service.external.product;

import de.metas.ad_reference.ADReferenceService;
import de.metas.product.ProductId;
import de.metas.product.ProductType;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributesTestHelper;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_Attribute;
import org.compiere.model.X_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * Unit coverage for {@link PickingJobProductService#isSerialNoPickingEnabled(ProductId)} and
 * {@link PickingJobProductService#assertPickAllowed(ProductId)}.
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
		SpringContextHolder.registerJUnitBean(ADReferenceService.newMocked());
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

	private ProductId createProductWithLifeCycleStatus(final String lifeCycleStatus)
	{
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setValue("P1");
		product.setName("P1");
		product.setProductType(ProductType.Item.getCode());
		product.setProductLifeCycleStatus(lifeCycleStatus);
		InterfaceWrapperHelper.saveRecord(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	@Test
	void assertPickAllowed_blockedStatus_throws()
	{
		// "G" (Gesperrt / BLOCKED) blocks every ProductLifeCycleAction, including PICK.
		final ProductId productId = createProductWithLifeCycleStatus(X_M_Product.PRODUCTLIFECYCLESTATUS_Blocked);

		assertThatThrownBy(() -> pickingJobProductService.assertPickAllowed(productId))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	void assertPickAllowed_okStatus_doesNotThrow()
	{
		// "O" (OK) is fully permissive → picking is allowed.
		final ProductId productId = createProductWithLifeCycleStatus(X_M_Product.PRODUCTLIFECYCLESTATUS_OK);

		assertThatCode(() -> pickingJobProductService.assertPickAllowed(productId)).doesNotThrowAnyException();
	}
}
