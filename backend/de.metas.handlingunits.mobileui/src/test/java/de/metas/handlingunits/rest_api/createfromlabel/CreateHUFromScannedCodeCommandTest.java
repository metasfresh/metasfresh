package de.metas.handlingunits.rest_api.createfromlabel;

import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.handlingunits.CreateHUFromInventoryRequest;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.impl.HUQtyService;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodePackingInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUniqueId;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUnitType;
import de.metas.handlingunits.qrcodes.service.HUQRCodesRepository;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.impl.PlainMsgBL;
import de.metas.organization.IOrgDAO;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.scannable_code.ScannedCode;
import de.metas.scannable_code.format.ScannableCodeFormatCreateRequest;
import de.metas.scannable_code.format.ScannableCodeFormatPartType;
import de.metas.scannable_code.format.service.ScannableCodeFormatService;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import de.metas.util.time.PatternedDateTimeFormatter;
import de.metas.workplace.WorkplaceRepository;
import de.metas.workplace.WorkplaceService;
import de.metas.workplace.WorkplaceUserAssignRepository;
import lombok.NonNull;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.api.IWarehouseDAO;
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

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * The create path through {@code CreateHUFromLabelService.create}: the request-to-command mapping and the server-side guards the UI
 * cannot produce (the handheld never opens a preview for a shared barcode and sends only the editable fields). The UI flow itself is
 * proven by the HU Manager Playwright specs (e2e/mobile-webui/tests/spec/humanager/createFromLabel.spec.js).
 */
class CreateHUFromScannedCodeCommandTest
{
	private static final ZonedDateTime BOOKING_DATE = ZonedDateTime.of(2026, 1, 15, 10, 30, 0, 0, ZoneId.of("Europe/Berlin"));

	private static final String SHARED_GTIN = "02001234567817";

	private RecordingHUQtyService huQtyService;
	private HUQRCodesService huQRCodesService;
	private I_M_Product product1;
	private I_M_Product product2;
	private LocatorQRCode locatorQRCode;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		Env.setClientId(Env.getCtx(), ClientId.METASFRESH);
		Env.setOrgId(Env.getCtx(), AdempiereTestHelper.createOrgWithTimeZone());
		huQRCodesService = HUQRCodesService.newInstanceForUnitTesting();
		huQtyService = new RecordingHUQtyService();

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

		product1 = newProduct("P1", SHARED_GTIN, uom);
		product2 = newProduct("P2", SHARED_GTIN, uom);

		final I_M_Warehouse warehouse = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class);
		warehouse.setValue("wh");
		warehouse.setName("wh");
		InterfaceWrapperHelper.saveRecord(warehouse);
		final I_M_Locator locator = InterfaceWrapperHelper.newInstance(I_M_Locator.class);
		locator.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
		locator.setValue("wh_l1");
		locator.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(locator);
		locatorQRCode = LocatorQRCode.ofLocator(locator);
	}

	private static I_M_Product newProduct(final String value, final String gtin, final I_C_UOM uom)
	{
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setIsActive(true);
		product.setValue(value);
		product.setName(value);
		product.setGTIN(gtin);
		product.setC_UOM_ID(uom.getC_UOM_ID());
		InterfaceWrapperHelper.saveRecord(product);
		return product;
	}

	private JsonCreateHUFromScannedCodeRequest readRequest(final String json) throws IOException
	{
		return JsonObjectMapperHolder.sharedJsonObjectMapper().readValue(json, JsonCreateHUFromScannedCodeRequest.class);
	}

	/** goes through the real service, so the request-to-command mapping of production code is what the tests exercise */
	private HuId create(final JsonCreateHUFromScannedCodeRequest request)
	{
		return create(request, "en_US");
	}

	private HuId create(final JsonCreateHUFromScannedCodeRequest request, final String adLanguage)
	{
		final CreateHUFromLabelService service = new CreateHUFromLabelService(huQRCodesService, huQtyService, new LabelProductResolver(huQRCodesService, Services.get(IProductBL.class)), new LabelTargetLocatorResolver(new WorkplaceService(new WorkplaceRepository(), new WorkplaceUserAssignRepository()), Services.get(IWarehouseDAO.class), Services.get(IOrgDAO.class)));
		return service.create(request, ClientId.METASFRESH, adLanguage, BOOKING_DATE).getHuId();
	}

	/** The command takes every service and the label resolver as builder parameters (it builds and looks up nothing itself). */
	@Test
	void commandBuiltWithItsServicesAndResolver_booksTheLabelProduct()
	{
		product2.setGTIN("02001234567893");
		InterfaceWrapperHelper.saveRecord(product2);

		final HuId huId = CreateHUFromScannedCodeCommand.builder()
				.handlingUnitsBL(Services.get(IHandlingUnitsBL.class))
				.uomConversionBL(Services.get(IUOMConversionBL.class))
				.warehouseDAO(Services.get(IWarehouseDAO.class))
				.attributeDAO(Services.get(IAttributeDAO.class))
				.huQtyService(huQtyService)
				.labelProductResolver(new LabelProductResolver(huQRCodesService, Services.get(IProductBL.class)))
				.clientId(ClientId.METASFRESH)
				.adLanguage("en_US")
				.scannedCode(ScannedCode.ofString("01" + SHARED_GTIN))
				.qty(BigDecimal.ONE)
				.locatorId(locatorQRCode.getLocatorId())
				.date(BOOKING_DATE)
				.build()
				.execute();

		assertThat(huId).isNotNull();
		assertThat(huQtyService.requests.get(0).getProductId()).isEqualTo(ProductId.ofRepoId(product1.getM_Product_ID()));
	}

	/**
	 * Failure scenario: a second product got the GTIN between the preview and OK (or a direct API call): the re-parse then yields two
	 * products and create must refuse instead of booking one of them.
	 */
	@Test
	void gtinFitsSeveralProducts_createRefused() throws IOException
	{
		final JsonCreateHUFromScannedCodeRequest request = readRequest("{\"scannedCode\":\"01" + SHARED_GTIN + "\",\"qty\":\"1\",\"locatorQRCode\":\"" + locatorQRCode.toGlobalQRCodeJsonString().replace("\"", "\\\"") + "\"}");

		assertThatThrownBy(() -> create(request))
				.isInstanceOf(LabelRejectionException.class)
				.hasFieldOrPropertyWithValue("messageKey", CreateFromLabelMessages.BARCODE_FITS_SEVERAL_PRODUCTS);
		assertThat(huQtyService.requests).isEmpty();
	}

	/**
	 * Failure scenario: a client (or a direct API call) sends a product: it is ignored, the product comes only from the re-parse of the
	 * scanned code; quantity and location are the client's.
	 */
	@Test
	void clientValuesOnlyForQtyLocator() throws IOException
	{
		// only product1 keeps the GTIN, so the re-parse yields exactly one product
		product2.setGTIN("02001234567893");
		InterfaceWrapperHelper.saveRecord(product2);

		final JsonCreateHUFromScannedCodeRequest request = readRequest("{\"scannedCode\":\"01" + SHARED_GTIN + "\",\"qty\":\"3\",\"locatorQRCode\":\"" + locatorQRCode.toGlobalQRCodeJsonString().replace("\"", "\\\"") + "\",\"productId\":" + product2.getM_Product_ID() + "}");
		create(request);

		assertThat(huQtyService.requests).hasSize(1);
		final CreateHUFromInventoryRequest booked = huQtyService.requests.get(0);
		assertThat(booked.getProductId()).isEqualTo(ProductId.ofRepoId(product1.getM_Product_ID()));
		assertThat(booked.getQty().toBigDecimal()).isEqualByComparingTo("3");
		assertThat(booked.getLocatorId()).isEqualTo(locatorQRCode.getLocatorId());
		assertThat(booked.getDate()).isEqualTo(BOOKING_DATE); // the date the caller set, not a clock read of the command
	}

	private static I_M_Attribute newAttribute(final String code, final String valueType)
	{
		final I_M_Attribute attribute = InterfaceWrapperHelper.newInstance(I_M_Attribute.class);
		attribute.setValue(code);
		attribute.setName(code);
		attribute.setAttributeValueType(valueType);
		InterfaceWrapperHelper.saveRecord(attribute);
		return attribute;
	}

	/** custom label: product code (2) + lot (6) + best-before yyMMdd (6) + production date yyMMdd (6) */
	private void newCustomLabelFormat()
	{
		ScannableCodeFormatService.newInstanceForUnitTesting().create(ScannableCodeFormatCreateRequest.builder()
				.name("test label")
				.parts(ImmutableList.of(
						ScannableCodeFormatCreateRequest.Part.builder().startPosition(1).endPosition(2).type(ScannableCodeFormatPartType.ProductCode).build(),
						ScannableCodeFormatCreateRequest.Part.builder().startPosition(3).endPosition(8).type(ScannableCodeFormatPartType.LotNo).build(),
						ScannableCodeFormatCreateRequest.Part.builder().startPosition(9).endPosition(14).type(ScannableCodeFormatPartType.BestBeforeDate).dateFormat(PatternedDateTimeFormatter.ofPattern("yyMMdd")).build(),
						ScannableCodeFormatCreateRequest.Part.builder().startPosition(15).endPosition(20).type(ScannableCodeFormatPartType.ProductionDate).dateFormat(PatternedDateTimeFormatter.ofPattern("yyMMdd")).build()))
				.build());
	}

	private JsonCreateHUFromScannedCodeRequest requestOf(final String label) throws IOException
	{
		return readRequest("{\"scannedCode\":\"" + label + "\",\"qty\":\"1\",\"locatorQRCode\":\"" + locatorQRCode.toGlobalQRCodeJsonString().replace("\"", "\\\"") + "\"}");
	}

	/**
	 * Failure scenario: the lot, best-before date and production date of the label must be on the inventory line (its ASI), so the
	 * inventory document and the stock carry the lot from the start and the new CU inherits it when the inventory completes.
	 */
	@Test
	void labelValues_areBookedAsInventoryAttributes() throws IOException
	{
		newAttribute(AttributeConstants.ATTR_LotNumber.getCode(), X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40);
		newAttribute(AttributeConstants.ATTR_BestBeforeDate.getCode(), X_M_Attribute.ATTRIBUTEVALUETYPE_Date);
		newAttribute(AttributeConstants.ProductionDate.getCode(), X_M_Attribute.ATTRIBUTEVALUETYPE_Date);
		newCustomLabelFormat();

		create(requestOf("P1LOT001271231251101"));

		assertThat(huQtyService.requests).hasSize(1);
		final ImmutableAttributeSet attributes = huQtyService.requests.get(0).getAttributes();
		assertThat(attributes.getValueAsString(AttributeConstants.ATTR_LotNumber)).isEqualTo("LOT001");
		assertThat(attributes.getValueAsLocalDate(AttributeConstants.ATTR_BestBeforeDate)).isEqualTo(LocalDate.of(2027, 12, 31));
		assertThat(attributes.getValueAsLocalDate(AttributeConstants.ProductionDate)).isEqualTo(LocalDate.of(2025, 11, 1));
	}

	/**
	 * Failure scenario: an attribute the installation does not define (here the production date) must not fail the booking: the
	 * preview does not show it either, so it is neither shown nor stored.
	 */
	@Test
	void attributeNotDefinedInInstallation_skipped_restBooked() throws IOException
	{
		newAttribute(AttributeConstants.ATTR_LotNumber.getCode(), X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40);
		newAttribute(AttributeConstants.ATTR_BestBeforeDate.getCode(), X_M_Attribute.ATTRIBUTEVALUETYPE_Date);
		newCustomLabelFormat();

		create(requestOf("P1LOT001271231251101"));

		final ImmutableAttributeSet attributes = huQtyService.requests.get(0).getAttributes();
		assertThat(attributes.hasAttribute(AttributeConstants.ATTR_LotNumber)).isTrue();
		assertThat(attributes.hasAttribute(AttributeConstants.ProductionDate)).isFalse();
	}

	/**
	 * Failure scenario: two {@code C_UOM} rows share the X12DE355 code; the booking must use the product's own stock unit, not
	 * whichever row the code lookup finds.
	 */
	@Test
	void booksInTheProductsStockUomId_evenWhenAnotherUomHasTheSameX12Code() throws IOException
	{
		final I_C_UOM otherUomSameCode = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		otherUomSameCode.setX12DE355(X12DE355.EACH.getCode());
		otherUomSameCode.setName("Each (second)");
		InterfaceWrapperHelper.saveRecord(otherUomSameCode);

		product2.setGTIN("02001234567893");
		product2.setC_UOM_ID(otherUomSameCode.getC_UOM_ID());
		InterfaceWrapperHelper.saveRecord(product2);
		product1.setGTIN("02001234567909");
		InterfaceWrapperHelper.saveRecord(product1);

		create(readRequest("{\"scannedCode\":\"0102001234567893\",\"qty\":\"2\",\"locatorQRCode\":\"" + locatorQRCode.toGlobalQRCodeJsonString().replace("\"", "\\\"") + "\"}"));

		assertThat(huQtyService.requests.get(0).getQty().getUomId()).isEqualTo(UomId.ofRepoId(otherUomSameCode.getC_UOM_ID()));
	}

	/** Failure scenario: a product code on the label that matches no product is "product not found", not "label not recognized" (a different mistake for the user). */
	@Test
	void productCodeOfNoProduct_productNotFound() throws IOException
	{
		newCustomLabelFormat();

		assertThatThrownBy(() -> create(requestOf("ZZLOT001271231251101")))
				.isInstanceOf(LabelRejectionException.class)
				.hasFieldOrPropertyWithValue("messageKey", CreateFromLabelMessages.PRODUCT_NOT_FOUND_FOR_PRODUCT_CODE);
		assertThat(huQtyService.requests).isEmpty();
	}

	/**
	 * Failure scenario: the origin marker stored on the inventory document (M_Inventory.Description, visible in the desktop) was a
	 * hard-coded English literal: it must be the AD_Message in the language of the user who creates the HU.
	 */
	@Test
	void inventoryDescription_isTheTranslatedMessageInTheUsersLanguage() throws IOException
	{
		// language made observable: the message text carries the language it was asked for
		Services.registerService(IMsgBL.class, new PlainMsgBL()
		{
			@Override
			public String getMsg(final String adLanguage, @NonNull final AdMessageKey message)
			{
				return adLanguage + ":" + message.toAD_Message();
			}
		});
		product2.setGTIN("02001234567893");
		InterfaceWrapperHelper.saveRecord(product2);

		create(readRequest("{\"scannedCode\":\"01" + SHARED_GTIN + "\",\"qty\":\"1\",\"locatorQRCode\":\"" + locatorQRCode.toGlobalQRCodeJsonString().replace("\"", "\\\"") + "\"}"), "de_DE");

		assertThat(huQtyService.requests).hasSize(1);
		assertThat(huQtyService.requests.get(0).getDescription())
				.isEqualTo("de_DE:" + CreateFromLabelMessages.INVENTORY_DESCRIPTION.toAD_Message())
				.isNotEqualTo("en_US:" + CreateFromLabelMessages.INVENTORY_DESCRIPTION.toAD_Message())
				.isNotEqualTo("HU Manager: created from scanned label"); // the former hard-coded literal
	}

	/** Server-side guard the UI cannot produce (OK is disabled at 0): its text is an AD_Message too, not a hard-coded literal. */
	@Test
	void quantityNotPositive_refusedWithTheMessage() throws IOException
	{
		product2.setGTIN("02001234567893");
		InterfaceWrapperHelper.saveRecord(product2);

		assertThatThrownBy(() -> create(readRequest("{\"scannedCode\":\"01" + SHARED_GTIN + "\",\"qty\":\"0\",\"locatorQRCode\":\"" + locatorQRCode.toGlobalQRCodeJsonString().replace("\"", "\\\"") + "\"}")))
				.hasMessageContaining(CreateFromLabelMessages.QTY_MUST_BE_POSITIVE.toAD_Message());
		assertThat(huQtyService.requests).isEmpty();
	}

	/** A user can scan a location that was deactivated meanwhile: the refusal text is an AD_Message, not a hard-coded literal. */
	@Test
	void inactiveLocator_refusedWithTheMessage() throws IOException
	{
		product2.setGTIN("02001234567893");
		InterfaceWrapperHelper.saveRecord(product2);
		final I_M_Locator locator = Services.get(IWarehouseDAO.class).getLocatorById(locatorQRCode.getLocatorId());
		locator.setIsActive(false);
		InterfaceWrapperHelper.saveRecord(locator);

		assertThatThrownBy(() -> create(readRequest("{\"scannedCode\":\"01" + SHARED_GTIN + "\",\"qty\":\"1\",\"locatorQRCode\":\"" + locatorQRCode.toGlobalQRCodeJsonString().replace("\"", "\\\"") + "\"}")))
				.hasMessageContaining(CreateFromLabelMessages.NOT_AN_ACTIVE_LOCATOR.toAD_Message());
		assertThat(huQtyService.requests).isEmpty();
	}

	private static class RecordingHUQtyService extends HUQtyService
	{
		final List<CreateHUFromInventoryRequest> requests = new ArrayList<>();

		RecordingHUQtyService()
		{
			super(InventoryService.newInstanceForUnitTesting());
		}

		/** the service reads the new HU's QR code afterwards; attach one so it does not need a full HU (packing instructions) to generate it */
		private static void attachQRCode(final HuId huId)
		{
			final HUQRCode qrCode = HUQRCode.builder()
					.id(HUQRCodeUniqueId.random())
					.packingInfo(HUQRCodePackingInfo.builder().huUnitType(HUQRCodeUnitType.VHU).packingInstructionsId(HuPackingInstructionsId.VIRTUAL).caption("CU").build())
					.attributes(ImmutableList.of())
					.build();
			new HUQRCodesRepository().createNew(qrCode, huId);
		}

		@Override
		public HuId createHUFromInventory(final CreateHUFromInventoryRequest request)
		{
			requests.add(request);
			final I_M_HU hu = InterfaceWrapperHelper.newInstance(I_M_HU.class); // the service reads its QR code afterwards
			InterfaceWrapperHelper.saveRecord(hu);
			final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());
			attachQRCode(huId);
			return huId;
		}
	}
}
