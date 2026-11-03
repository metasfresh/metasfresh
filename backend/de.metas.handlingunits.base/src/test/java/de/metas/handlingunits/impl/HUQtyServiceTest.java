package de.metas.handlingunits.impl;

import de.metas.document.DocTypeId;
import de.metas.handlingunits.CreateHUFromInventoryRequest;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.inventory.InventoryTestHelper;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.handlingunits.model.I_M_InventoryLine_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.inventory.AggregationType;
import de.metas.inventory.HUAggregationType;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.AttributesTestHelper;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.TimeUtil;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

@ExtendWith(AdempiereTestWatcher.class)
class HUQtyServiceTest
{
	private static final ZonedDateTime MOVEMENT_DATE = ZonedDateTime.of(2026, 1, 15, 10, 30, 0, 0, ZoneId.of("Europe/Berlin"));

	private InventoryService inventoryService;
	private HUQtyService huQtyService;

	private I_C_UOM uomRecord;
	private LocatorId locatorId;
	private DocTypeId singleHUDocTypeId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		// the doc type of HU Manager's own stock changes (single-HU inventory); completion needs a real HU, so it is simulated below
		singleHUDocTypeId = InventoryTestHelper.createDocType(AggregationType.SINGLE_HU.getDocBaseAndSubType());

		inventoryService = Mockito.spy(InventoryService.newInstanceForUnitTesting());
		huQtyService = new HUQtyService(inventoryService);

		uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);

		final I_M_Warehouse warehouseRecord = newInstance(I_M_Warehouse.class);
		saveRecord(warehouseRecord);
		final I_M_Locator locatorRecord = newInstance(I_M_Locator.class);
		locatorRecord.setM_Warehouse_ID(warehouseRecord.getM_Warehouse_ID());
		saveRecord(locatorRecord);
		locatorId = LocatorId.ofRecord(locatorRecord);
	}

	/**
	 * Regression of the HU Manager "create from label" OK click: a new HU of a product has neither an HU id nor an HU QR code,
	 * so the line must not need one (it was rejected with "At least huId or huQRCode must be set").
	 * <p>
	 * Completing the inventory is what creates the HU (SyncInventoryQtyToHUsCommand, needs the full HU stack - module CLAUDE.md: JUnit is no proof of HU
	 * materialisation, the real path is covered by the Playwright E2E). So only the persistence of the completed line is simulated, exactly as
	 * InventoryLoaderAndSaver#saveInventoryLine does it: a SINGLE_HU line keeps the HU in M_InventoryLine.M_HU_ID, any other line in M_InventoryLine_HU.
	 */
	@Test
	void createHUFromInventory_newHUOfProduct_booksOneCountedSingleHULineWithAttributesAndReturnsTheCreatedHU()
	{
		final AttributesTestHelper attributesTestHelper = new AttributesTestHelper();
		final I_M_Attribute lotAttribute = attributesTestHelper.createM_Attribute("Lot", X_M_Attribute.ATTRIBUTEVALUETYPE_List, true);
		lotAttribute.setIsStorageRelevant(true);
		saveRecord(lotAttribute);
		final AttributeListValue lotValue = attributesTestHelper.createM_AttributeValue(lotAttribute, "L-4711");

		final I_M_HU createdHU = newInstance(I_M_HU.class);
		createdHU.setM_HU_PI_Version_ID(HuPackingInstructionsVersionId.VIRTUAL.getRepoId());
		createdHU.setHUStatus(X_M_HU.HUSTATUS_Active);
		saveRecord(createdHU);
		final HuId createdHUId = HuId.ofRepoId(createdHU.getM_HU_ID());
		doAnswer(invocation -> {
			final I_M_InventoryLine line = Services.get(IQueryBL.class).createQueryBuilder(I_M_InventoryLine.class).create().firstOnlyNotNull(I_M_InventoryLine.class);
			if (HUAggregationType.SINGLE_HU.getCode().equals(line.getHUAggregationType()))
			{
				line.setM_HU_ID(createdHUId.getRepoId());
				saveRecord(line);
			}
			else
			{
				final I_M_InventoryLine_HU lineHU = newInstance(I_M_InventoryLine_HU.class);
				lineHU.setM_InventoryLine_ID(line.getM_InventoryLine_ID());
				lineHU.setM_Inventory_ID(line.getM_Inventory_ID());
				lineHU.setM_HU_ID(createdHUId.getRepoId());
				lineHU.setQtyCount(line.getQtyCount());
				lineHU.setC_UOM_ID(line.getC_UOM_ID());
				saveRecord(lineHU);
			}
			return null;
		}).when(inventoryService).completeDocument(any());

		final HuId huId = huQtyService.createHUFromInventory(CreateHUFromInventoryRequest.builder()
				.date(MOVEMENT_DATE)
				.productId(ProductId.ofRepoId(12345))
				.qty(Quantity.of("12.345", uomRecord))
				.locatorId(locatorId)
				.attributes(ImmutableAttributeSet.builder().attributeValue(lotValue).build())
				.description("created from label")
				.build());

		assertThat(huId).isEqualTo(createdHUId);

		final I_M_InventoryLine line = Services.get(IQueryBL.class).createQueryBuilder(I_M_InventoryLine.class).create().firstOnlyNotNull(I_M_InventoryLine.class);
		assertThat(line.getM_Product_ID()).isEqualTo(12345);
		assertThat(line.getQtyCount()).isEqualByComparingTo("12.345");
		assertThat(line.getQtyBook()).isEqualByComparingTo("0");
		assertThat(line.isCounted()).isTrue();
		// same shape as the "Change Qty" path: a single-HU inventory line (M_HU_ID set, no M_InventoryLine_HU divergence)
		assertThat(line.getHUAggregationType()).isEqualTo(HUAggregationType.SINGLE_HU.getCode());
		assertThat(line.getM_HU_ID()).isEqualTo(createdHUId.getRepoId());
		// the attributes (lot) are carried on the line's ASI
		assertThat(AttributesKeys.createAttributesKeyFromASIStorageAttributes(AttributeSetInstanceId.ofRepoId(line.getM_AttributeSetInstance_ID())))
				.isPresent().get().isEqualTo(AttributesKey.ofAttributeValueIds(lotValue.getId()));
		// the document is the single-HU inventory doc type
		final I_M_Inventory header = Services.get(IQueryBL.class).createQueryBuilder(I_M_Inventory.class).create().firstOnlyNotNull(I_M_Inventory.class);
		assertThat(header.getC_DocType_ID()).isEqualTo(singleHUDocTypeId.getRepoId());
		// the movement date is the date the caller set
		assertThat(header.getMovementDate()).isEqualTo(TimeUtil.asTimestamp(MOVEMENT_DATE));
		assertThat(Services.get(IQueryBL.class).createQueryBuilder(I_M_InventoryLine_HU.class).create().count()).isZero();
	}
}
