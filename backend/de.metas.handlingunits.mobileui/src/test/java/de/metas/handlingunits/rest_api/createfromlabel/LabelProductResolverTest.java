package de.metas.handlingunits.rest_api.createfromlabel;

import de.metas.gs1.GTIN;
import de.metas.gs1.ean13.EAN13;
import de.metas.handlingunits.qrcodes.ean13.EAN13HUQRCode;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.handlingunits.qrcodes.model.ProductLabelCodes;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.scannable_code.ScannedCode;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import javax.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;

/** The resolver is stateless: its services come through the constructor, the client through {@code resolve(...)}. */
class LabelProductResolverTest
{
	private static final String GTIN_VALUE = "02001234567817";
	/** an invented variable-weight EAN-13 (GS1 does not accept it, so the parser falls through to the EAN-13 reader) */
	private static final String EAN13_VALUE = "2859414004825";

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
		product.setGTIN(GTIN_VALUE);
		product.setC_UOM_ID(uom.getC_UOM_ID());
		InterfaceWrapperHelper.saveRecord(product);

		resolver = new LabelProductResolver(HUQRCodesService.newInstanceForUnitTesting(), Services.get(IProductBL.class));
	}

	private I_M_Product createEAN13Product()
	{
		final I_M_Product ean13Product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		ean13Product.setIsActive(true);
		ean13Product.setValue("59414");
		ean13Product.setName("P2");
		ean13Product.setC_UOM_ID(product.getC_UOM_ID());
		InterfaceWrapperHelper.saveRecord(ean13Product);
		return ean13Product;
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
		final LabelProduct labelProduct = resolver.resolve(ScannedCode.ofString("01" + GTIN_VALUE), ClientId.METASFRESH);

		assertThat(labelProduct.getProductId()).isEqualTo(ProductId.ofRepoId(product.getM_Product_ID()));
	}

	@Test
	void ean13Code_resolvesTheProductWithThatEAN13()
	{
		final I_M_Product ean13Product = createEAN13Product();

		assertThat(HUQRCodesService.newInstanceForUnitTesting().parse(ScannedCode.ofString(EAN13_VALUE))).isInstanceOf(EAN13HUQRCode.class);

		final LabelProduct labelProduct = resolver.resolve(ScannedCode.ofString(EAN13_VALUE), ClientId.METASFRESH);

		assertThat(labelProduct.getProductId()).isEqualTo(ProductId.ofRepoId(ean13Product.getM_Product_ID()));
	}

	@Test
	void ean13CodeOfNoProduct_isRefusedWithTheEAN13()
	{
		assertThat(HUQRCodesService.newInstanceForUnitTesting().parse(ScannedCode.ofString(EAN13_VALUE))).isInstanceOf(EAN13HUQRCode.class);

		assertThatThrownBy(() -> resolver.resolve(ScannedCode.ofString(EAN13_VALUE), ClientId.METASFRESH))
				.isInstanceOf(LabelRejectionException.class)
				.hasFieldOrPropertyWithValue("messageKey", CreateFromLabelMessages.PRODUCT_NOT_FOUND_FOR_GTIN);
	}

	@Test
	void globalQRCodeOfAnotherType_keepsTheParsersWrongTypeException()
	{
		final I_M_Warehouse warehouse = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class);
		warehouse.setValue("wh");
		warehouse.setName("wh");
		InterfaceWrapperHelper.saveRecord(warehouse);
		final I_M_Locator locator = InterfaceWrapperHelper.newInstance(I_M_Locator.class);
		locator.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
		locator.setValue("wh_l1");
		locator.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(locator);
		final ScannedCode locatorCode = ScannedCode.ofString(LocatorQRCode.ofLocator(locator).toGlobalQRCodeJsonString());

		final Throwable parserException = catchThrowable(() -> HUQRCodesService.newInstanceForUnitTesting().parse(locatorCode));
		assertThat(parserException).isNotNull();

		assertThatThrownBy(() -> resolver.resolve(locatorCode, ClientId.METASFRESH))
				.isNotInstanceOf(LabelRejectionException.class)
				.isExactlyInstanceOf(parserException.getClass())
				.hasMessage(parserException.getMessage());
	}

	@Test
	void unparseableString_isLabelNotRecognized()
	{
		assertThatThrownBy(() -> resolver.resolve(ScannedCode.ofString("hello"), ClientId.METASFRESH))
				.isInstanceOf(LabelRejectionException.class)
				.hasFieldOrPropertyWithValue("messageKey", CreateFromLabelMessages.LABEL_NOT_RECOGNIZED);
	}

	/** none of the real scanned-code classes: proves the resolver reads the code only through {@link IHUQRCode} */
	@RequiredArgsConstructor
	private static class TestCode implements IHUQRCode
	{
		@Nullable private final ProductLabelCodes productLabelCodes;
		@Nullable private final LocalDate expirationDate;

		@Override
		public String getAsString() {return "TEST";}

		@Override
		public Optional<BigDecimal> getWeightInKg() {return Optional.empty();}

		@Override
		public Optional<LocalDate> getBestBeforeDate() {return Optional.empty();}

		@Override
		public Optional<String> getLotNumber() {return Optional.empty();}

		@Override
		public Optional<ProductLabelCodes> getProductLabelCodes() {return Optional.ofNullable(productLabelCodes);}

		@Override
		public Optional<LocalDate> getExpirationDate() {return Optional.ofNullable(expirationDate);}
	}

	private LabelProduct resolve(final IHUQRCode code)
	{
		return resolver.resolve(code, ScannedCode.ofString(code.getAsString()), ClientId.METASFRESH);
	}

	@Test
	void anyCodeCarryingTheGTINOfAProduct_resolvesThatProduct()
	{
		final ProductLabelCodes codes = ProductLabelCodes.builder().gtin(GTIN.ofString(GTIN_VALUE)).build();

		assertThat(resolve(new TestCode(codes, null)).getProductId()).isEqualTo(ProductId.ofRepoId(product.getM_Product_ID()));
	}

	@Test
	void expirationDateOnly_isTheBestBeforeDate()
	{
		final LocalDate date = LocalDate.of(2031, 5, 17);

		assertThat(LabelValues.of(new TestCode(null, date)).getBestBeforeDate()).isEqualTo(date);
	}

	@Test
	void codeWithoutProductLabelCodes_isNotAProductLabel()
	{
		assertThatThrownBy(() -> resolve(new TestCode(null, null)))
				.isInstanceOf(LabelRejectionException.class)
				.hasFieldOrPropertyWithValue("messageKey", CreateFromLabelMessages.NOT_A_PRODUCT_LABEL);
	}

	@Test
	void productLabelCodesWithoutAnyCode_isLabelHasNoProduct()
	{
		assertThatThrownBy(() -> resolve(new TestCode(ProductLabelCodes.builder().build(), null)))
				.isInstanceOf(LabelRejectionException.class)
				.hasFieldOrPropertyWithValue("messageKey", CreateFromLabelMessages.LABEL_HAS_NO_PRODUCT);
	}

	@Test
	void unknownProductNumber_isNotRetriedWithTheGTIN()
	{
		final ProductLabelCodes codes = ProductLabelCodes.builder().productNo("NO-SUCH-PRODUCT").gtin(GTIN.ofString(GTIN_VALUE)).build();

		assertThatThrownBy(() -> resolve(new TestCode(codes, null)))
				.isInstanceOf(LabelRejectionException.class)
				.hasFieldOrPropertyWithValue("messageKey", CreateFromLabelMessages.PRODUCT_NOT_FOUND_FOR_PRODUCT_CODE);
	}

	@Test
	void ean13Only_isResolvedThroughTheEAN13Lookup()
	{
		final I_M_Product ean13Product = createEAN13Product();
		final ProductLabelCodes codes = ProductLabelCodes.builder().ean13(EAN13.ofString(EAN13_VALUE).orElseThrow()).build();

		assertThat(resolve(new TestCode(codes, null)).getProductId()).isEqualTo(ProductId.ofRepoId(ean13Product.getM_Product_ID()));
	}

	@Test
	void productNumber_isLookedUpInTheGivenClient_notInTheAmbientOne()
	{
		final ProductLabelCodes codes = ProductLabelCodes.builder().productNo(product.getValue()).build();
		final ClientId otherClientId = ClientId.ofRepoId(ClientId.METASFRESH.getRepoId() + 1);

		// P1 belongs to the ambient client (Env), not to the given one
		assertThatThrownBy(() -> resolver.resolve(new TestCode(codes, null), ScannedCode.ofString("TEST"), otherClientId))
				.isInstanceOf(LabelRejectionException.class)
				.hasFieldOrPropertyWithValue("messageKey", CreateFromLabelMessages.PRODUCT_NOT_FOUND_FOR_PRODUCT_CODE);

		// and a product of the given client is found although the ambient client is another one
		final I_M_Product otherClientProduct = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		InterfaceWrapperHelper.setValue(otherClientProduct, I_M_Product.COLUMNNAME_AD_Client_ID, otherClientId.getRepoId());
		otherClientProduct.setIsActive(true);
		otherClientProduct.setValue("P-OTHER");
		otherClientProduct.setName("P-OTHER");
		otherClientProduct.setC_UOM_ID(product.getC_UOM_ID());
		InterfaceWrapperHelper.saveRecord(otherClientProduct);
		final ProductLabelCodes otherCodes = ProductLabelCodes.builder().productNo("P-OTHER").build();

		assertThat(resolver.resolve(new TestCode(otherCodes, null), ScannedCode.ofString("TEST"), otherClientId).getProductId())
				.isEqualTo(ProductId.ofRepoId(otherClientProduct.getM_Product_ID()));
	}
}
