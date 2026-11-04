package de.metas.handlingunits.rest_api;

import de.metas.ad_reference.ADReferenceService;
import de.metas.ad_reference.AdRefListRepositoryMocked;
import de.metas.ad_reference.AdRefTableRepositoryMocked;
import de.metas.common.handlingunits.JsonGetSingleHUResponse;
import de.metas.handlingunits.grai.HUGraiService;
import de.metas.handlingunits.grai.HUPIGraiRepository;
import de.metas.handlingunits.impl.HUQtyService;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.report.labels.HULabelService;
import de.metas.inventory.InventoryCandidateRepository;
import de.metas.inventory.InventoryCandidateService;
import de.metas.scannable_code.ScannedCode;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class HandlingUnitsServiceGetByQRCodeTest
{
	private HandlingUnitsService service;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		Env.setClientId(Env.getCtx(), ClientId.METASFRESH);
		Env.setOrgId(Env.getCtx(), AdempiereTestHelper.createOrgWithTimeZone());
		// the lookup also tries the code as an ExternalBarcode attribute value
		final I_M_Attribute externalBarcode = InterfaceWrapperHelper.newInstance(I_M_Attribute.class);
		externalBarcode.setValue(AttributeConstants.ATTR_ExternalBarcode.getCode());
		externalBarcode.setName("ExternalBarcode");
		externalBarcode.setAttributeValueType(X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40);
		InterfaceWrapperHelper.saveRecord(externalBarcode);

		service = new HandlingUnitsService(
				HUQRCodesService.newInstanceForUnitTesting(),
				new HUQtyService(InventoryService.newInstanceForUnitTesting(), HUQRCodesService.newInstanceForUnitTesting()),
				HULabelService.newInstanceForUnitTesting(),
				new InventoryCandidateService(new ADReferenceService(new AdRefListRepositoryMocked(), new AdRefTableRepositoryMocked()), new InventoryCandidateRepository()),
				new HUGraiService(new HUPIGraiRepository()));
	}

	/**
	 * A Leich+Mehl label is a global QR code (LMQ#...) but no HU QR code: the shared HU lookup keeps the answer it had before the
	 * HU Manager "create from label" feature - "invalid QR code" (400), the same as for any other non-HU global code.
	 */
	@Test
	void leichUndMehlCode_staysInvalidQRCode_badRequest()
	{
		final ResponseEntity<JsonGetSingleHUResponse> response = service.getByQRCode(GetByQRCodeRequest.builder()
				.qrCode(ScannedCode.ofString("LMQ#1#25.5#31.12.2025#L2311"))
				.build());

		assertThat(response.getStatusCode()).as(String.valueOf(response.getBody())).isEqualTo(HttpStatus.BAD_REQUEST);
		// the body is the invalid-QR-code error (not another failure that happens to be a 400)
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getError()).isNotNull();
		assertThat(response.getBody().getError().getMessage()).contains("INVALID_QR_CODE_ERROR_MSG");
	}

	/**
	 * Another metasfresh QR code that is no HU code (a shelf label) gets the same "invalid QR code" answer (400) as a Leich+Mehl code:
	 * the lookup names no code type.
	 */
	@Test
	void locatorCode_staysInvalidQRCode_badRequest()
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

		final ResponseEntity<JsonGetSingleHUResponse> response = service.getByQRCode(GetByQRCodeRequest.builder()
				.qrCode(ScannedCode.ofString(LocatorQRCode.ofLocator(locator).toGlobalQRCodeJsonString()))
				.build());

		assertThat(response.getStatusCode()).as(String.valueOf(response.getBody())).isEqualTo(HttpStatus.BAD_REQUEST);
		// the body is the invalid-QR-code error (not another failure that happens to be a 400)
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getError()).isNotNull();
		assertThat(response.getBody().getError().getMessage()).contains("INVALID_QR_CODE_ERROR_MSG");
	}
}
