package de.metas.handlingunits.inventory;

import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocTypeId;
import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.handlingunits.model.I_M_InventoryLine_HU;
import de.metas.inventory.AggregationType;
import de.metas.inventory.HUAggregationType;
import de.metas.inventory.InventoryId;
import de.metas.inventory.InventoryLineId;
import de.metas.material.event.commons.AttributesKey;
import de.metas.organization.OrgId;
import de.metas.organization.StoreCreditCardNumberMode;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJONextIdSuppliers;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, seef
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@ExtendWith(AdempiereTestWatcher.class)
class InventoryRepositoryTest
{
	private static final ZoneId orgTimeZone = ZoneId.of("UTC-8");
	private OrgId orgId;

	private InventoryRepository inventoryLineRepository;
	private I_C_UOM uomRecord;
	private I_M_Locator locatorRecord;

	private AttributeSetInstanceId asiId;

	@BeforeAll
	static void beforeAll()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG);
	}

	@AfterAll
	static void afterAll()
	{
		validateSnapshots();
	}

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		POJOLookupMap.setNextIdSupplier(POJONextIdSuppliers.newPerTableSequence());

		orgId = createOrg(orgTimeZone);

		uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);

		final I_M_Warehouse warehouseRecord = newInstance(I_M_Warehouse.class);
		saveRecord(warehouseRecord);
		locatorRecord = newInstance(I_M_Locator.class);
		locatorRecord.setM_Warehouse(warehouseRecord);
		saveRecord(locatorRecord);

		final I_M_AttributeSetInstance asi = InventoryTestHelper.createAsi();
		asiId = AttributeSetInstanceId.ofRepoId(asi.getM_AttributeSetInstance_ID());

		inventoryLineRepository = new InventoryRepository();
	}

	private OrgId createOrg(final ZoneId timeZone)
	{
		final I_AD_Org org = newInstance(I_AD_Org.class);
		saveRecord(org);
		final OrgId orgId = OrgId.ofRepoId(org.getAD_Org_ID());

		final I_AD_OrgInfo orgInfo = newInstance(I_AD_OrgInfo.class);
		orgInfo.setAD_Org_ID(orgId.getRepoId());
		orgInfo.setTimeZone(timeZone.getId());
		orgInfo.setStoreCreditCardData(StoreCreditCardNumberMode.DONT_STORE.getCode());
		saveRecord(orgInfo);

		return orgId;
	}

	@Builder(builderMethodName = "inventoryRecord", builderClassName = "$InventoryRecordBuilder")
	private InventoryId createInventoryRecord(
			@NonNull final DocBaseAndSubType docBaseAndSubType,
			@NonNull final String movementDate)
	{
		final DocTypeId docTypeId = InventoryTestHelper.createDocType(docBaseAndSubType);

		final I_M_Inventory inventoryRecord = newInstance(I_M_Inventory.class);
		inventoryRecord.setAD_Org_ID(orgId.getRepoId());
		inventoryRecord.setC_DocType_ID(docTypeId.getRepoId());
		inventoryRecord.setDocStatus(DocStatus.Drafted.getCode());
		inventoryRecord.setMovementDate(TimeUtil.asTimestamp(LocalDate.parse("2020-06-15"), orgTimeZone));
		inventoryRecord.setDocumentNo("documentNo");
		saveRecord(inventoryRecord);
		return InventoryId.ofRepoId(inventoryRecord.getM_Inventory_ID());
	}

	@Test
	void toInventory()
	{
		final InventoryId inventoryId = inventoryRecord()
				.docBaseAndSubType(AggregationType.SINGLE_HU.getDocBaseAndSubType())
				.movementDate("2020-06-15")
				.build();

		final Inventory inventory = inventoryLineRepository.getById(inventoryId);
		assertThat(inventory.getMovementDate())
				.isEqualTo(LocalDate.parse("2020-06-15").atStartOfDay(orgTimeZone));
		assertThat(inventory.getDocStatus()).isEqualTo(DocStatus.Drafted);
	}

	@Test
	void saveInventoryLine()
	{
		final InventoryId inventoryId = inventoryRecord()
				.docBaseAndSubType(AggregationType.SINGLE_HU.getDocBaseAndSubType())
				.movementDate("2020-06-15")
				.build();

		final InventoryLineId inventoryLineId;
		{
			final I_M_InventoryLine inventoryLineRecord = newInstance(I_M_InventoryLine.class);
			inventoryLineRecord.setM_Inventory_ID(inventoryId.getRepoId());
			saveRecord(inventoryLineRecord);
			inventoryLineId = InventoryLineId.ofRepoId(inventoryLineRecord.getM_InventoryLine_ID());
		}

		final AttributesKey storageAttributesKey = AttributesKeys.createAttributesKeyFromASIStorageAttributes(asiId).orElse(AttributesKey.NONE);

		final InventoryLine inventoryLine = InventoryLine.builder()
				.orgId(orgId)
				.id(inventoryLineId)
				.locatorId(LocatorId.ofRecord(locatorRecord))
				.productId(ProductId.ofRepoId(40))
				.asiId(asiId)
				.storageAttributesKey(storageAttributesKey)
				.huAggregationType(HUAggregationType.MULTI_HU)
				.counted(true)
				.qtyBookFixed(Quantity.of("30", uomRecord))
				.qtyCountFixed(Quantity.of("3", uomRecord))
				.inventoryLineHU(InventoryLineHU
						.builder()
						.huId(HuId.ofRepoId(100))
						.qtyCount(Quantity.of(ONE, uomRecord))
						.qtyBook(Quantity.of(TEN, uomRecord))
						.build())
				.inventoryLineHU(InventoryLineHU
						.builder()
						.huId(HuId.ofRepoId(200))
						.qtyCount(Quantity.of("2", uomRecord))
						.qtyBook(Quantity.of("20", uomRecord))
						.build())
				.build();

		inventoryLine.getInventoryLineHUs()
				.forEach(lineHU -> InventoryTestHelper.createStorageFor(inventoryLine.getProductId(), lineHU.getQtyBook(), lineHU.getHuId()));

		// invoke the method under test
		inventoryLineRepository.saveInventoryLine(inventoryLine, inventoryId);

		final Inventory reloadedResult = inventoryLineRepository.getById(inventoryId);
		expect(reloadedResult).toMatchSnapshot();

		assertThat(reloadedResult.getLineById(inventoryLineId)).isEqualTo(inventoryLine);
	}

	@Test
	void getById_multiHU_empty()
	{
		final InventoryId inventoryId = inventoryRecord()
				.docBaseAndSubType(AggregationType.MULTIPLE_HUS.getDocBaseAndSubType())
				.movementDate("2020-06-15")
				.build();
		final InventoryLineId inventoryLineId;
		{
			final I_M_InventoryLine inventoryLineRecord = newInstance(I_M_InventoryLine.class);
			inventoryLineRecord.setM_Inventory_ID(inventoryId.getRepoId());
			inventoryLineRecord.setHUAggregationType(AggregationType.MULTIPLE_HUS.getHuAggregationTypeCode());
			inventoryLineRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
			inventoryLineRecord.setM_Locator_ID(locatorRecord.getM_Locator_ID());
			inventoryLineRecord.setM_Product_ID(40);
			saveRecord(inventoryLineRecord);
			inventoryLineId = InventoryLineId.ofRepoId(inventoryLineRecord.getM_InventoryLine_ID());
		}

		final InventoryLine result = inventoryLineRepository
				.getById(inventoryId)
				.getLineById(inventoryLineId);

		final Quantity zero = Quantity.zero(uomRecord);
		assertThat(result.getInventoryLineHUs())
				.extracting("huId", "qtyBook", "qtyCount")
				.containsOnly(tuple(null, zero, zero));
	}

	@Test
	void getById_multiHU_nullHuId()
	{
		final InventoryId inventoryId = inventoryRecord()
				.docBaseAndSubType(AggregationType.MULTIPLE_HUS.getDocBaseAndSubType())
				.movementDate("2020-06-15")
				.build();

		final InventoryLineId inventoryLineId;
		{
			final I_M_InventoryLine inventoryLineRecord = newInstance(I_M_InventoryLine.class);
			inventoryLineRecord.setM_Inventory_ID(inventoryId.getRepoId());
			inventoryLineRecord.setHUAggregationType(AggregationType.MULTIPLE_HUS.getHuAggregationTypeCode());
			inventoryLineRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
			inventoryLineRecord.setM_Locator_ID(locatorRecord.getM_Locator_ID());
			inventoryLineRecord.setM_Product_ID(40);
			saveRecord(inventoryLineRecord);
			inventoryLineId = InventoryLineId.ofRepoId(inventoryLineRecord.getM_InventoryLine_ID());

			final I_M_InventoryLine_HU inventoryLineHURecord = newInstance(I_M_InventoryLine_HU.class);
			inventoryLineHURecord.setM_Inventory_ID(inventoryId.getRepoId());
			inventoryLineHURecord.setM_InventoryLine_ID(inventoryLineRecord.getM_InventoryLine_ID());
			inventoryLineHURecord.setQtyBook(new BigDecimal("2"));
			inventoryLineHURecord.setQtyCount(new BigDecimal("10"));
			inventoryLineHURecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
			saveRecord(inventoryLineHURecord);
		}

		final InventoryLine result = inventoryLineRepository
				.getById(inventoryId)
				.getLineById(inventoryLineId);
		assertThat(result.getInventoryLineHUs())
				.extracting("huId", "qtyBook", "qtyCount")
				.containsOnly(tuple(null, Quantity.of("2", uomRecord), Quantity.of("10", uomRecord)));
	}

}
