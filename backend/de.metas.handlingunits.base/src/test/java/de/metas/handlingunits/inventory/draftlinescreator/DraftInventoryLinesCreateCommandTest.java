package de.metas.handlingunits.inventory.draftlinescreator;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inventory.Inventory;
import de.metas.handlingunits.inventory.InventoryHeaderCreateRequest;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.inventory.InventoryTestHelper;
import de.metas.handlingunits.inventory.draftlinescreator.HuForInventoryLine.HuForInventoryLineBuilder;
import de.metas.inventory.AggregationType;
import de.metas.material.event.commons.AttributesKey;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJONextIdSuppliers;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.stream.Stream;

import static de.metas.handlingunits.inventory.InventoryTestHelper.AV1_ID;
import static de.metas.handlingunits.inventory.InventoryTestHelper.AV2_ID;
import static de.metas.handlingunits.inventory.InventoryTestHelper.AV3_ID;
import static de.metas.handlingunits.inventory.InventoryTestHelper.createStorageFor;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

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
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@ExtendWith({ SnapshotExtension.class, AdempiereTestWatcher.class })
class DraftInventoryLinesCreateCommandTest
{
	private final ProductId PRODUCT_ID_40 = ProductId.ofRepoId(40);
	private final ProductId PRODUCT_ID_2 = ProductId.ofRepoId(2);

	private static final ZoneId orgTimeZone = ZoneId.of("UTC-8");
	private OrgId orgId;

	private Quantity qtyOne;
	private Quantity qtyTwo;
	private Quantity qtyTen;

	private WarehouseId warehouseId;
	private LocatorId locatorId;

	private InventoryService inventoryService;

	private Expect expect;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		POJOLookupMap.setNextIdSupplier(POJONextIdSuppliers.newPerTableSequence());

		orgId = AdempiereTestHelper.createOrgWithTimeZone(orgTimeZone);

		inventoryService = InventoryService.newInstanceForUnitTesting();

		final I_C_UOM uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);

		final I_M_Warehouse warehouseRecord = newInstance(I_M_Warehouse.class);
		saveRecord(warehouseRecord);
		warehouseId = WarehouseId.ofRepoId(warehouseRecord.getM_Warehouse_ID());

		final I_M_Locator locatorRecord = newInstance(I_M_Locator.class);
		locatorRecord.setM_Warehouse_ID(warehouseId.getRepoId());
		saveRecord(locatorRecord);
		locatorId = LocatorId.ofRecord(locatorRecord);

		InventoryTestHelper.createAttributeValues();

		qtyOne = Quantity.of("1", uomRecord);
		qtyTwo = Quantity.of("2", uomRecord);
		qtyTen = Quantity.of("10", uomRecord);
	}

	private Inventory createInventory(final AggregationType aggregationType)
	{
		return inventoryService.createInventoryHeader(
				InventoryHeaderCreateRequest.builder()
						.orgId(orgId)
						.docTypeId(InventoryTestHelper.createDocType(aggregationType.getDocBaseAndSubType()))
						.movementDate(LocalDate.parse("2020-06-15").atStartOfDay(orgTimeZone))
						.warehouseId(warehouseId)
						.documentNo("documentNo")
						.build()
		);
	}

	@Test
	void execute_SingleHUInventoryLineAggregator()
	{
		final DraftInventoryLinesCreateResponse result = inventoryService.createDraftLines(
				DraftInventoryLinesCreateRequest.builder()
						.inventory(createInventory(AggregationType.SINGLE_HU))
						.strategy(this::createAndStreamTestHUs)
						.build()
		);

		final Inventory inventory = inventoryService.getById(result.getInventoryId());
		expect.serializer("orderedJson").toMatchSnapshot(inventory);
	}

	@Test
	void execute_MultipleHUInventoryLineAggregator()
	{
		final Inventory result = execute_MultipleHUInventoryLineAggregator_performTest();

		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	/**
	 * Verifies that the creator also works if an inventory already has some lines/HUs
	 */
	@Test
	void execute_MultipleHUInventoryLineAggregator_preAddedHUs()
	{
		// given
		// create inventory with some HUs
		final Inventory initialInventoryWithHus = execute_MultipleHUInventoryLineAggregator_performTest();
		assertThat(initialInventoryWithHus.getHuIds()).containsExactlyInAnyOrder(HuId.ofRepoId(100), HuId.ofRepoId(200), HuId.ofRepoId(300)); // guard

		// when
		final DraftInventoryLinesCreateResponse result = inventoryService.createDraftLines(
				DraftInventoryLinesCreateRequest.builder()
						.inventory(createInventory(AggregationType.MULTIPLE_HUS))
						.strategy(this::createAndStreamAdditionalTestHU)
						.build()
		);

		// then
		final Inventory inventory = inventoryService.getById(result.getInventoryId());
		assertThat(inventory.getHuIds()).containsExactlyInAnyOrder(
				HuId.ofRepoId(100),
				HuId.ofRepoId(200),
				HuId.ofRepoId(300),
				HuId.ofRepoId(305)/*newly added*/);
		expect.serializer("orderedJson").toMatchSnapshot(inventory);
	}

	private Inventory execute_MultipleHUInventoryLineAggregator_performTest()
	{
		// execute the method under test
		final DraftInventoryLinesCreateResponse result = inventoryService.createDraftLines(
				DraftInventoryLinesCreateRequest.builder()
						.inventory(createInventory(AggregationType.MULTIPLE_HUS))
						.strategy(this::createAndStreamTestHUs)
						.build()
		);

		return inventoryService.getById(result.getInventoryId());

	}

	private Stream<HuForInventoryLine> createAndStreamTestHUs()
	{
		final HuForInventoryLineBuilder builder = HuForInventoryLine.builder()
				.orgId(OrgId.ofRepoId(5))
				.locatorId(locatorId);

		final HuForInventoryLine hu1 = builder.productId(PRODUCT_ID_40).huId(HuId.ofRepoId(100)).quantityBooked(qtyTen).storageAttributesKey(AttributesKey.ofAttributeValueIds(AV1_ID, AV2_ID)).build();
		final HuForInventoryLine hu2_l1 = builder.productId(PRODUCT_ID_40).huId(HuId.ofRepoId(200)).quantityBooked(qtyOne).storageAttributesKey(AttributesKey.ofAttributeValueIds(AV1_ID, AV2_ID)).build();
		final HuForInventoryLine hu2_l2 = builder.productId(PRODUCT_ID_2).huId(HuId.ofRepoId(200)).quantityBooked(qtyOne).storageAttributesKey(AttributesKey.ofAttributeValueIds(AV1_ID, AV2_ID)).build();
		final HuForInventoryLine hu3 = builder.productId(PRODUCT_ID_40).huId(HuId.ofRepoId(300)).quantityBooked(qtyTwo).storageAttributesKey(AttributesKey.ofAttributeValueIds(AV1_ID, AV2_ID, AV3_ID)).build();

		final ImmutableList<HuForInventoryLine> inventoryLines = ImmutableList.of(hu1, hu2_l1, hu2_l2, hu3);
		inventoryLines.forEach(huInvLine -> createStorageFor(
				huInvLine.getProductId(),
				huInvLine.getQuantityBooked(),
				huInvLine.getHuId()));

		return inventoryLines.stream();
	}

	private Stream<HuForInventoryLine> createAndStreamAdditionalTestHU()
	{
		final HuForInventoryLineBuilder builder = HuForInventoryLine.builder()
				.orgId(OrgId.ofRepoId(5))
				.locatorId(locatorId)
				.productId(PRODUCT_ID_40);

		final HuForInventoryLine hu1 = builder.huId(HuId.ofRepoId(100)).quantityBooked(qtyTen).storageAttributesKey(AttributesKey.ofAttributeValueIds(AV1_ID, AV2_ID)).build();
		final HuForInventoryLine hu2 = builder.huId(HuId.ofRepoId(200)).quantityBooked(qtyOne).storageAttributesKey(AttributesKey.ofAttributeValueIds(AV1_ID, AV2_ID)).build();
		final HuForInventoryLine hu3 = builder.huId(HuId.ofRepoId(300)).quantityBooked(qtyTwo).storageAttributesKey(AttributesKey.ofAttributeValueIds(AV1_ID, AV2_ID, AV3_ID)).build();

		final HuForInventoryLine hu4 = builder.huId(HuId.ofRepoId(305)).quantityBooked(qtyTwo).storageAttributesKey(AttributesKey.ofAttributeValueIds(AV1_ID, AV2_ID, AV3_ID)).build();
		createStorageFor(
				hu4.getProductId(),
				hu4.getQuantityBooked(),
				hu4.getHuId());

		return ImmutableList.of(hu1, hu2, hu3, hu4).stream();
	}
}
