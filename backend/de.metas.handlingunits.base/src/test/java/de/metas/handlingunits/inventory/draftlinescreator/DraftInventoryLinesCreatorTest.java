package de.metas.handlingunits.inventory.draftlinescreator;

import com.google.common.collect.ImmutableList;
import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocTypeId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inventory.Inventory;
import de.metas.handlingunits.inventory.InventoryRepository;
import de.metas.handlingunits.inventory.InventoryTestHelper;
import de.metas.handlingunits.inventory.draftlinescreator.HuForInventoryLine.HuForInventoryLineBuilder;
import de.metas.handlingunits.inventory.draftlinescreator.InventoryLineAggregatorFactory.MultipleHUInventoryLineAggregator;
import de.metas.handlingunits.inventory.draftlinescreator.InventoryLineAggregatorFactory.SingleHUInventoryLineAggregator;
import de.metas.inventory.AggregationType;
import de.metas.inventory.InventoryId;
import de.metas.material.event.commons.AttributesKey;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.DumpPOJOLookupMapOnTestFail;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static de.metas.handlingunits.inventory.InventoryTestHelper.AV1_ID;
import static de.metas.handlingunits.inventory.InventoryTestHelper.AV2_ID;
import static de.metas.handlingunits.inventory.InventoryTestHelper.AV3_ID;
import static de.metas.handlingunits.inventory.InventoryTestHelper.createStorageFor;
import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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
class DraftInventoryLinesCreatorTest
{
	private static final BigDecimal TWO = new BigDecimal("2");

	private Quantity qtyOne;
	private Quantity qtyTwo;
	private Quantity qtyTen;

	private LocatorId locatorId;

	private InventoryRepository inventoryRepo;
	// private InventoryId inventoryId;

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

		inventoryRepo = new InventoryRepository();

		final I_C_UOM uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);

		final I_M_Warehouse warehouseRecord = newInstance(I_M_Warehouse.class);
		saveRecord(warehouseRecord);

		final I_M_Locator locatorRecord = newInstance(I_M_Locator.class);
		locatorRecord.setM_Warehouse(warehouseRecord);
		saveRecord(locatorRecord);
		locatorId = LocatorId.ofRecord(locatorRecord);

		InventoryTestHelper.createAttributeValues();

		qtyOne = Quantity.of(ONE, uomRecord);
		qtyTwo = Quantity.of(TWO, uomRecord);
		qtyTen = Quantity.of(TEN, uomRecord);
	}

	private InventoryId createInventoryRecord(final DocBaseAndSubType docBaseAndSubType)
	{
		final DocTypeId docTypeId = InventoryTestHelper.createDocType(docBaseAndSubType);

		final I_M_Inventory inventoryRecord = newInstance(I_M_Inventory.class);
		inventoryRecord.setC_DocType_ID(docTypeId.getRepoId());
		saveRecord(inventoryRecord);
		return InventoryId.ofRepoId(inventoryRecord.getM_Inventory_ID());
	}

	@Test
	void execute_SingleHUInventoryLineAggregator()
	{
		final InventoryId inventoryId = createInventoryRecord(AggregationType.SINGLE_HU.getDocBaseAndSubType());
		final InventoryLinesCreationCtx ctx = createContext(
				inventoryId,
				this::createAndStreamTestHUs,
				SingleHUInventoryLineAggregator.INSTANCE);

		// execute the method under test
		new DraftInventoryLinesCreator(ctx).execute();

		final Inventory result = inventoryRepo.getById(inventoryId);
		expect(result).toMatchSnapshot();
	}

	@Test
	void execute_MultipleHUInventoryLineAggregator()
	{
		final InventoryId inventoryId = createInventoryRecord(AggregationType.SINGLE_HU.getDocBaseAndSubType());
		final InventoryLinesCreationCtx ctx = createContext(
				inventoryId,
				this::createAndStreamTestHUs,
				MultipleHUInventoryLineAggregator.INSTANCE);

		// execute the method under test
		new DraftInventoryLinesCreator(ctx).execute();

		final Inventory result = inventoryRepo.getById(inventoryId);
		expect(result).toMatchSnapshot();
	}

	private Stream<HuForInventoryLine> createAndStreamTestHUs()
	{
		final HuForInventoryLineBuilder builder = HuForInventoryLine.builder()
				.orgId(OrgId.ofRepoId(5))
				.locatorId(locatorId)
				.productId(ProductId.ofRepoId(40));

		final HuForInventoryLine hu1 = builder.huId(HuId.ofRepoId(100)).quantity(qtyTen).storageAttributesKey(AttributesKey.ofAttributeValueIds(AV1_ID, AV2_ID)).build();
		final HuForInventoryLine hu2 = builder.huId(HuId.ofRepoId(200)).quantity(qtyOne).storageAttributesKey(AttributesKey.ofAttributeValueIds(AV1_ID, AV2_ID)).build();
		final HuForInventoryLine hu3 = builder.huId(HuId.ofRepoId(300)).quantity(qtyTwo).storageAttributesKey(AttributesKey.ofAttributeValueIds(AV1_ID, AV2_ID, AV3_ID)).build();

		final ImmutableList<HuForInventoryLine> inventoryLines = ImmutableList.of(hu1, hu2, hu3);
		inventoryLines.forEach(huInvLine -> createStorageFor(huInvLine.getProductId(),  huInvLine.getQuantity(), huInvLine.getHuId()));

		return inventoryLines.stream();
	}

	private InventoryLinesCreationCtx createContext(
			@NonNull final InventoryId inventoryId,
			@NonNull final HUsForInventoryStrategy strategy,
			@NonNull final InventoryLineAggregator aggregator)
	{
		final InventoryLinesCreationCtx ctx = InventoryLinesCreationCtx.builder()
				.inventory(inventoryRepo.getById(inventoryId))
				.inventoryRepo(inventoryRepo)
				.inventoryLineAggregator(aggregator)
				.strategy(strategy)
				.build();
		return ctx;
	}

}
