package de.metas.handlingunits.rest_api.createfromlabel;

import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.scannable_code.ScannedCode;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/** The resolver is stateless: its services come through the constructor, the client through {@code resolve(...)}. */
class LabelProductResolverTest
{
	private static final String GTIN = "02001234567817";

	private LabelProductResolver resolver;
	private I_M_Product product;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		Env.setClientId(Env.getCtx(), ClientId.METASFRESH);

		// the parser also looks a code up as an HU value / external barcode
		final I_M_Attribute externalBarcode = InterfaceWrapperHelper.newInstance(I_M_Attribute.class);
		externalBarcode.setValue(AttributeConstants.ATTR_ExternalBarcode.getCode());
		externalBarcode.setName("ExternalBarcode");
		externalBarcode.setAttributeValueType(X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40);
		InterfaceWrapperHelper.saveRecord(externalBarcode);

		final I_C_UOM uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		uom.setX12DE355(X12DE355.EACH.getCode());
		uom.setName("Each");
		InterfaceWrapperHelper.saveRecord(uom);

		product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setIsActive(true);
		product.setValue("P1");
		product.setName("P1");
		product.setGTIN(GTIN);
		product.setC_UOM_ID(uom.getC_UOM_ID());
		InterfaceWrapperHelper.saveRecord(product);

		resolver = new LabelProductResolver(HUQRCodesService.newInstanceForUnitTesting(), Services.get(IProductBL.class));
	}

	@Test
	void pickOnTheFlyCode_isNotAProductLabel()
	{
		assertThatThrownBy(() -> resolver.resolve(ScannedCode.ofString("PICK_ON_THE_FLY"), ClientId.METASFRESH))
				.isInstanceOf(LabelRejectionException.class)
				.hasFieldOrPropertyWithValue("messageKey", CreateFromLabelMessages.NOT_A_PRODUCT_LABEL);
	}

	@Test
	void gs1CodeWithGtinOfOneProduct_returnsThatProduct()
	{
		final LabelProduct labelProduct = resolver.resolve(ScannedCode.ofString("01" + GTIN), ClientId.METASFRESH);

		assertThat(labelProduct.getProductId()).isEqualTo(ProductId.ofRepoId(product.getM_Product_ID()));
	}
}
