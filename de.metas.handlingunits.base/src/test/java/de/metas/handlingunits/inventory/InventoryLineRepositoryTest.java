package de.metas.handlingunits.inventory;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.math.BigDecimal;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributesKeys;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.DumpPOJOLookupMapOnTestFail;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.inventory.InventoryId;
import de.metas.inventory.InventoryLineId;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;

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

@ExtendWith(DumpPOJOLookupMapOnTestFail.class)
class InventoryLineRepositoryTest
{
	private static final BigDecimal TWO = new BigDecimal("2");
	private static final BigDecimal TWENTY = new BigDecimal("20");

	private InventoryLineRepository inventoryLineRepository;
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


		uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);

		final I_M_Warehouse warehouseRecord = newInstance(I_M_Warehouse.class);
		saveRecord(warehouseRecord);
		locatorRecord = newInstance(I_M_Locator.class);
		locatorRecord.setM_Warehouse(warehouseRecord);
		saveRecord(locatorRecord);

		final I_M_AttributeSetInstance asi = InventoryTestHelper.createAsi();
		asiId = AttributeSetInstanceId.ofRepoId(asi.getM_AttributeSetInstance_ID());

		inventoryLineRepository = new InventoryLineRepository();
	}



	@Test
	void save()
	{
		final I_M_Inventory inventoryRecord = newInstance(I_M_Inventory.class);
		saveRecord(inventoryRecord);

		final I_M_InventoryLine inventoryLineRecord = newInstance(I_M_InventoryLine.class);
		inventoryLineRecord.setM_Inventory(inventoryRecord);
		saveRecord(inventoryLineRecord);

		final InventoryId inventoryId = InventoryId.ofRepoId(inventoryRecord.getM_Inventory_ID());

		final AttributesKey storageAttributesKey = AttributesKeys
				.createAttributesKeyFromASIStorageAttributes(asiId)
				.orElse(AttributesKey.NONE);

		final InventoryLine inventoryLine = InventoryLine
				.builder()
				.inventoryId(inventoryId)
				.id(InventoryLineId.ofRepoId(inventoryLineRecord.getM_InventoryLine_ID()))
				.locatorId(LocatorId.ofRecord(locatorRecord))
				.productId(ProductId.ofRepoId(40))
				.asiId(asiId)
				.storageAttributesKey(storageAttributesKey)
				.singleHUAggregation(false)
				.inventoryLineHU(InventoryLineHU
						.builder()
						.huId(HuId.ofRepoId(100))
						.countQty(Quantity.of(ONE, uomRecord))
						.bookQty(Quantity.of(TEN, uomRecord))
						.build())
				.inventoryLineHU(InventoryLineHU
						.builder()
						.huId(HuId.ofRepoId(200))
						.countQty(Quantity.of(TWO, uomRecord))
						.bookQty(Quantity.of(TWENTY, uomRecord))
						.build())
				.build();

		// invoke the method under test
		inventoryLineRepository.save(inventoryLine);

		final ImmutableList<InventoryLine> reloadedResult = inventoryLineRepository.getByInventoryId(inventoryId);
		expect(reloadedResult).toMatchSnapshot();
	}

}
