package de.metas.handlingunits.inventory;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.handlingunits.HuId;
import de.metas.inventory.HUAggregationType;
import de.metas.inventory.InventoryLineId;
import de.metas.material.event.commons.AttributesKey;
import de.metas.organization.OrgId;
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

class InventoryLineTest
{
	private I_C_UOM uomRecord;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		uomRecord = newInstance(I_C_UOM.class);
		uomRecord.setUOMSymbol("Ea");
		uomRecord.setName("Each");
		saveRecord(uomRecord);
	}

	private Quantity qty(final int qtyInt)
	{
		return Quantity.of(BigDecimal.valueOf(qtyInt), uomRecord);
	}

	@Test
	void distributeQtyCountToHUs_3_to_19()
	{
		final InventoryLine inventoryLine = createInventoryLine();
		assertThat(inventoryLine.getInventoryLineHUs()) // guard
				.extracting("huId", "qtyBook", "qtyCount")
				.containsOnly(
						tuple(HuId.ofRepoId(100), qty(10), qty(1)),
						tuple(HuId.ofRepoId(200), qty(20), qty(2)));

		// invoke the method under test
		final InventoryLine result = inventoryLine.distributeQtyCountToHUs(qty(19));

		assertThat(result.getInventoryLineHUs())
				.extracting("huId", "qtyBook", "qtyCount")
				.containsOnly(
						tuple(HuId.ofRepoId(100), qty(10), qty(17)),
						tuple(HuId.ofRepoId(200), qty(20), qty(2)));
	}

	@Test
	void distributeQtyCountToHUs_3_to_1()
	{
		final InventoryLine inventoryLine = createInventoryLine();
		assertThat(inventoryLine.getInventoryLineHUs()) // guard
				.extracting("huId", "qtyBook", "qtyCount")
				.containsOnly(
						tuple(HuId.ofRepoId(100), qty(10), qty(1)),
						tuple(HuId.ofRepoId(200), qty(20), qty(2)));

		// invoke the method under test
		final InventoryLine result = inventoryLine.distributeQtyCountToHUs(qty(1));

		assertThat(result.getInventoryLineHUs())
				.extracting("huId", "qtyBook", "qtyCount")
				.containsOnly(
						tuple(HuId.ofRepoId(100), qty(10), qty(0)),
						tuple(HuId.ofRepoId(200), qty(20), qty(1)));
	}

	@Test
	void distributeQtyCountToHUs_empty_to_19()
	{
		final InventoryLine inventoryLine = createInventoryLine()
				.toBuilder()
				.clearInventoryLineHUs()
				.inventoryLineHU(InventoryLineHU.zeroPhysicalInventory(uomRecord))
				.build();
		assertThat(inventoryLine.getInventoryLineHUs())
				.containsExactly(InventoryLineHU.zeroPhysicalInventory(uomRecord));

		// invoke the method under test
		final InventoryLine result = inventoryLine.distributeQtyCountToHUs(qty(19));

		assertThat(result.getInventoryLineHUs())
				.extracting("huId", "qtyBook", "qtyCount")
				.containsOnly(tuple(null, qty(0), qty(19)));
	}

	private InventoryLine createInventoryLine()
	{
		return InventoryLine.builder()
				.orgId(OrgId.ofRepoId(1))
				.id(InventoryLineId.ofRepoId(20))
				.locatorId(LocatorId.ofRepoId(WarehouseId.ofRepoId(30), 35))
				.productId(ProductId.ofRepoId(40))
				.storageAttributesKey(AttributesKey.ofAttributeValueIds(10000, 10001, 10002))
				.huAggregationType(HUAggregationType.MULTI_HU)
				.inventoryLineHU(InventoryLineHU.builder()
						.huId(HuId.ofRepoId(100))
						.qtyBook(qty(10))
						.qtyCount(qty(1))
						.build())
				.inventoryLineHU(InventoryLineHU.builder()
						.huId(HuId.ofRepoId(200))
						.qtyBook(qty(20))
						.qtyCount(qty(2))
						.build())
				.build();
	}

}
