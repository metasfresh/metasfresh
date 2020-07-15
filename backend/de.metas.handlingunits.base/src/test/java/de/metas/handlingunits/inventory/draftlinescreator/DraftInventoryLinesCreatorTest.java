package de.metas.handlingunits.inventory.draftlinescreator;

import static de.metas.handlingunits.inventory.InventoryTestHelper.AV1_ID;
import static de.metas.handlingunits.inventory.InventoryTestHelper.AV2_ID;
import static de.metas.handlingunits.inventory.InventoryTestHelper.AV3_ID;
import static de.metas.handlingunits.inventory.InventoryTestHelper.createStorageFor;
import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.stream.Stream;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.common.collect.ImmutableList;

import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocTypeId;
import de.metas.document.engine.DocStatus;
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
import de.metas.organization.StoreCreditCardNumberMode;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.NonNull;

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

@ExtendWith(AdempiereTestWatcher.class)
class DraftInventoryLinesCreatorTest
{
	private static final ZoneId orgTimeZone = ZoneId.of("UTC-8");
	private OrgId orgId;

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

		orgId = createOrg(orgTimeZone);

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

		qtyOne = Quantity.of("1", uomRecord);
		qtyTwo = Quantity.of("2", uomRecord);
		qtyTen = Quantity.of("10", uomRecord);
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

	private InventoryId createInventoryRecord(final DocBaseAndSubType docBaseAndSubType)
	{
		final DocTypeId docTypeId = InventoryTestHelper.createDocType(docBaseAndSubType);

		final I_M_Inventory inventoryRecord = newInstance(I_M_Inventory.class);
		inventoryRecord.setAD_Org_ID(orgId.getRepoId());
		inventoryRecord.setC_DocType_ID(docTypeId.getRepoId());
		inventoryRecord.setDocStatus(DocStatus.Drafted.getCode());
		inventoryRecord.setMovementDate(TimeUtil.asTimestamp(LocalDate.parse("2020-06-15"), orgTimeZone));
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

		final HuForInventoryLine hu1 = builder.huId(HuId.ofRepoId(100)).quantityBooked(qtyTen).storageAttributesKey(AttributesKey.ofAttributeValueIds(AV1_ID, AV2_ID)).build();
		final HuForInventoryLine hu2 = builder.huId(HuId.ofRepoId(200)).quantityBooked(qtyOne).storageAttributesKey(AttributesKey.ofAttributeValueIds(AV1_ID, AV2_ID)).build();
		final HuForInventoryLine hu3 = builder.huId(HuId.ofRepoId(300)).quantityBooked(qtyTwo).storageAttributesKey(AttributesKey.ofAttributeValueIds(AV1_ID, AV2_ID, AV3_ID)).build();

		final ImmutableList<HuForInventoryLine> inventoryLines = ImmutableList.of(hu1, hu2, hu3);
		inventoryLines.forEach(huInvLine -> createStorageFor(
				huInvLine.getProductId(),
				huInvLine.getQuantityBooked(),
				huInvLine.getHuId()));

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
