package de.metas.handlingunits.picking.job.service;

import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;
import de.metas.handlingunits.picking.job.service.commands.PickingJobTestHelper;
import de.metas.handlingunits.qrcodes.custom.CustomHUQRCode;
import de.metas.handlingunits.test.misc.builders.HUPIAttributeBuilder;
import de.metas.product.ProductId;
import de.metas.scannable_code.ScannedCode;
import de.metas.scannable_code.format.ParsedScannedCode;
import org.adempiere.mm.attributes.AttributesTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The picking lookup of a scanned custom label ({@code HU_QRCode} attribute) is limited to the HUs of the picking job's warehouse picking group.
 * Failure scenario: two warehouses hold an HU with the same label code (same product, same lot) - the picker must be offered only the HU
 * of his own picking group, never the one in a foreign warehouse.
 */
@ExtendWith(AdempiereTestWatcher.class)
class PickingJobHUServiceQRCodeAttributeLookupTest
{
	private static final String LABEL = "P1LOT001271231251101";

	private PickingJobTestHelper helper;
	private ProductId productId;

	@BeforeEach
	void beforeEach()
	{
		helper = new PickingJobTestHelper();
		productId = ProductId.ofRepoId(BusinessTestHelper.createProduct("P1", helper.uomEach).getM_Product_ID());

		final I_M_Attribute qrCodeAttribute = new AttributesTestHelper().createM_Attribute(HUAttributeConstants.ATTR_QRCode.getCode(), X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, true);
		final I_M_HU_PI_Attribute piAttribute = helper.getHuTestHelper().createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(qrCodeAttribute)
				.setM_HU_PI_Version(HuPackingInstructionsVersionId.VIRTUAL)
				.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation));
		assertThat(piAttribute).isNotNull();
	}

	private HuId createHUWithLabel(final LocatorId locatorId)
	{
		final HuId huId = helper.createVHU(productId, "10", locatorId);
		final I_M_HU hu = helper.getHuTestHelper().handlingUnitsBL().getById(huId);
		final IAttributeStorage attributes = helper.getHuTestHelper().createMutableHUContext().getHUAttributeStorageFactory().getAttributeStorage(hu);
		attributes.setSaveOnChange(true);
		attributes.setValue(HUAttributeConstants.ATTR_QRCode, LABEL);
		return huId;
	}

	private static CustomHUQRCode label()
	{
		return CustomHUQRCode.ofParsedScannedCode(ParsedScannedCode.builder().scannedCode(ScannedCode.ofString(LABEL)).build());
	}

	@Test
	void huInAnotherPickingGroup_notFound()
	{
		final LocatorId foreignLocator = helper.createWarehouseAndLocator("foreign", "foreign_loc");
		createHUWithLabel(foreignLocator);

		assertThat(helper.huService.getFirstHUIdByQRCodeAttribute(label(), productId, helper.shipFromLocatorId.getWarehouseId())).isEmpty();
	}

	@Test
	void huInTheSamePickingGroup_found()
	{
		final LocatorId otherWarehouseLocator = helper.createWarehouseAndLocator("sameGroup", "sameGroup_loc");
		final int groupId = helper.createPickingGroupWithWarehouse(helper.shipFromLocatorId.getWarehouseId());
		helper.assignWarehouseToPickingGroup(otherWarehouseLocator.getWarehouseId(), groupId);
		final HuId huId = createHUWithLabel(otherWarehouseLocator);

		assertThat(helper.huService.getFirstHUIdByQRCodeAttribute(label(), productId, helper.shipFromLocatorId.getWarehouseId())).contains(huId);
	}

	@Test
	void huInOwnWarehouse_found()
	{
		final HuId huId = createHUWithLabel(helper.shipFromLocatorId);

		assertThat(helper.huService.getFirstHUIdByQRCodeAttribute(label(), productId, helper.shipFromLocatorId.getWarehouseId())).contains(huId);
	}
}
