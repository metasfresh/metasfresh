package de.metas.material.cockpit.stock.eventhandler;

import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.material.cockpit.stock.StockChangeSourceInfo;
import de.metas.material.cockpit.stock.StockDataRecordIdentifier;
import de.metas.material.cockpit.stock.StockDataUpdateRequest;
import de.metas.material.cockpit.stock.StockDataUpdateRequestHandler;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.attributes.AttributesChangedEvent;
import de.metas.material.event.attributes.AttributesKeyWithASI;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * metasfresh-material-cockpit
 * %%
 * Copyright (C) 2026 metas GmbH
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

public class AttributesChangedEventHandlerForStockRecordsTest
{
	private static final int CLIENT_ID = 10;
	private static final int ORG_ID = 20;
	private static final int PRODUCT_ID = 2;
	private static final int WAREHOUSE_ID = 1;
	private static final BigDecimal QTY = new BigDecimal("13");
	private static final AttributeSetInstanceId DUMMY_ASI_ID = AttributeSetInstanceId.ofRepoId(123);

	private StockDataUpdateRequestHandler stockDataUpdateRequestHandler;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		stockDataUpdateRequestHandler = new StockDataUpdateRequestHandler(Mockito.mock(PostMaterialEventService.class));
	}

	@Test
	public void reKeysStockFromOldToNewAttributesKey()
	{
		// Given: seed MD_Stock(product=2, warehouse=1, attributesKey=A) with QtyOnHand=13
		final AttributesKey keyA = attributesKey(1000);
		final AttributesKey keyB = attributesKey(2000);

		seedStock(keyA, QTY);

		// Sanity check: stock on keyA exists before handler call
		assertThat(getQtyOnHand(keyA)).isEqualByComparingTo(QTY);

		// When: the handler processes an AttributesChangedEvent(old=A, new=B)
		final AttributesChangedEventHandlerForStockRecords handler = new AttributesChangedEventHandlerForStockRecords(stockDataUpdateRequestHandler);
		handler.handleEvent(AttributesChangedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(CLIENT_ID, ORG_ID))
				.warehouseId(WarehouseId.ofRepoId(WAREHOUSE_ID))
				.date(Instant.parse("2019-10-03T10:15:30.00Z"))
				.productId(PRODUCT_ID)
				.qty(QTY)
				.oldStorageAttributes(attributesKeyWithASI(1000))
				.newStorageAttributes(attributesKeyWithASI(2000))
				.huId(333)
				.build());

		// Then: qty has moved off the old key and onto the new key
		assertThat(getQtyOnHand(keyA))
				.as("MD_Stock for old attributesKey A must be 0 after re-key")
				.isEqualByComparingTo(BigDecimal.ZERO);
		assertThat(getQtyOnHand(keyB))
				.as("MD_Stock for new attributesKey B must equal QTY after re-key")
				.isEqualByComparingTo(QTY);
	}

	/**
	 * AC2 — full lifecycle: receipt → attribute change → shipment leaves both keys at zero.
	 */
	@Test
	public void fullLifecycle_receiptThenAttrChangeThenShipment_zeroesBothKeys()
	{
		final AttributesKey keyA = attributesKey(5000);
		final AttributesKey keyB = attributesKey(6000);

		// Receipt: seed MD_Stock(product=2, warehouse=1, keyA) = +Q
		seedStock(keyA, QTY);

		// Attribute change: re-key from A → B
		final AttributesChangedEventHandlerForStockRecords handler = new AttributesChangedEventHandlerForStockRecords(stockDataUpdateRequestHandler);
		handler.handleEvent(AttributesChangedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(CLIENT_ID, ORG_ID))
				.warehouseId(WarehouseId.ofRepoId(WAREHOUSE_ID))
				.date(Instant.parse("2026-01-02T00:00:00.00Z"))
				.productId(PRODUCT_ID)
				.qty(QTY)
				.oldStorageAttributes(attributesKeyWithASI(5000))
				.newStorageAttributes(attributesKeyWithASI(6000))
				.huId(666)
				.build());

		// Sanity: after re-key, keyB must hold QTY
		final I_MD_Stock keyBAfterReKey = getMDStockRecord(PRODUCT_ID, keyB);
		assertThat(keyBAfterReKey)
				.as("MD_Stock(keyB) row must exist after re-key")
				.isNotNull();
		assertThat(keyBAfterReKey.getQtyOnHand())
				.as("MD_Stock(keyB).QtyOnHand must equal QTY after re-key")
				.isEqualByComparingTo(QTY);

		// Shipment: issue −Q under keyB (simulates shipment booking under new attributes key)
		seedStock(keyB, QTY.negate());

		// Assert: both keys end at 0 — no phantom +/− pair
		final I_MD_Stock keyAFinal = getMDStockRecord(PRODUCT_ID, keyA);
		assertThat(keyAFinal)
				.as("MD_Stock(keyA) row must exist after full lifecycle (genuinely zeroed)")
				.isNotNull();
		assertThat(keyAFinal.getQtyOnHand())
				.as("MD_Stock(keyA).QtyOnHand must be 0 after full lifecycle")
				.isEqualByComparingTo(BigDecimal.ZERO);

		final I_MD_Stock keyBFinal = getMDStockRecord(PRODUCT_ID, keyB);
		assertThat(keyBFinal)
				.as("MD_Stock(keyB) row must exist after shipment (genuinely zeroed)")
				.isNotNull();
		assertThat(keyBFinal.getQtyOnHand())
				.as("MD_Stock(keyB).QtyOnHand must be 0 after shipment")
				.isEqualByComparingTo(BigDecimal.ZERO);
	}

	/**
	 * AC4 — repeated re-keys (e.g. nightly MonthsUntilExpiry transitions) converge with no leftover on intermediate keys.
	 */
	@Test
	public void repeatedRewrites_convergeNoLeftover()
	{
		final AttributesKey keyA = attributesKey(7000);
		final AttributesKey keyB = attributesKey(8000);
		final AttributesKey keyC = attributesKey(9000);

		// Seed MD_Stock(product=2, warehouse=1, keyA) = +Q
		seedStock(keyA, QTY);

		final AttributesChangedEventHandlerForStockRecords handler = new AttributesChangedEventHandlerForStockRecords(stockDataUpdateRequestHandler);

		// First re-key: A → B
		handler.handleEvent(AttributesChangedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(CLIENT_ID, ORG_ID))
				.warehouseId(WarehouseId.ofRepoId(WAREHOUSE_ID))
				.date(Instant.parse("2026-01-03T00:00:00.00Z"))
				.productId(PRODUCT_ID)
				.qty(QTY)
				.oldStorageAttributes(attributesKeyWithASI(7000))
				.newStorageAttributes(attributesKeyWithASI(8000))
				.huId(777)
				.build());

		// Second re-key: B → C
		handler.handleEvent(AttributesChangedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(CLIENT_ID, ORG_ID))
				.warehouseId(WarehouseId.ofRepoId(WAREHOUSE_ID))
				.date(Instant.parse("2026-01-04T00:00:00.00Z"))
				.productId(PRODUCT_ID)
				.qty(QTY)
				.oldStorageAttributes(attributesKeyWithASI(8000))
				.newStorageAttributes(attributesKeyWithASI(9000))
				.huId(777)
				.build());

		// keyA must be zeroed
		final I_MD_Stock keyARow = getMDStockRecord(PRODUCT_ID, keyA);
		assertThat(keyARow)
				.as("MD_Stock(keyA) row must exist (genuinely zeroed, not absent)")
				.isNotNull();
		assertThat(keyARow.getQtyOnHand())
				.as("MD_Stock(keyA).QtyOnHand must be 0 — no leftover after two re-keys")
				.isEqualByComparingTo(BigDecimal.ZERO);

		// keyB must be zeroed (no accumulation from the intermediate step)
		final I_MD_Stock keyBRow = getMDStockRecord(PRODUCT_ID, keyB);
		assertThat(keyBRow)
				.as("MD_Stock(keyB) row must exist (genuinely zeroed, not absent)")
				.isNotNull();
		assertThat(keyBRow.getQtyOnHand())
				.as("MD_Stock(keyB).QtyOnHand must be 0 — no leftover after second re-key")
				.isEqualByComparingTo(BigDecimal.ZERO);

		// keyC must hold +Q
		final I_MD_Stock keyCRow = getMDStockRecord(PRODUCT_ID, keyC);
		assertThat(keyCRow)
				.as("MD_Stock(keyC) row must exist after second re-key")
				.isNotNull();
		assertThat(keyCRow.getQtyOnHand())
				.as("MD_Stock(keyC).QtyOnHand must equal QTY after two successive re-keys")
				.isEqualByComparingTo(QTY);
	}

	/**
	 * A change that does not alter the storage AttributesKey (old == new — e.g. a non-storage
	 * attribute changed) must be a no-op: no MD_Stock row created, no offsetting deltas.
	 */
	@Test
	public void ignoresChangeThatDoesNotAlterAttributesKey()
	{
		// No seed — no pre-existing MD_Stock row for this key.
		final AttributesKey sameKey = attributesKey(1000);

		// Fire event where old == new (identity re-key: nothing really changed)
		final AttributesChangedEventHandlerForStockRecords handler = new AttributesChangedEventHandlerForStockRecords(stockDataUpdateRequestHandler);
		handler.handleEvent(AttributesChangedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(CLIENT_ID, ORG_ID))
				.warehouseId(WarehouseId.ofRepoId(WAREHOUSE_ID))
				.date(Instant.parse("2026-06-01T00:00:00.00Z"))
				.productId(PRODUCT_ID)
				.qty(new BigDecimal("13"))
				.oldStorageAttributes(attributesKeyWithASI(1000))
				.newStorageAttributes(attributesKeyWithASI(1000))
				.huId(100)
				.build());

		// No row should exist — a no-op event must not create a phantom stock entry.
		assertThat(getMDStockRecord(PRODUCT_ID, sameKey))
				.as("No MD_Stock row should be created when old == new attributes key (identity re-key is a no-op)")
				.isNull();
	}

	/**
	 * U2 — partial re-key: two HUs share keyA (total 20). Moving qty 10 to keyB leaves 10 on keyA.
	 */
	@Test
	public void reKeyMovesOnlyChangedHusQty_leavesRemainderOnOldKey()
	{
		final AttributesKey keyA = attributesKey(2100);
		final AttributesKey keyB = attributesKey(2200);

		// Seed two HUs worth of qty under keyA (total = 20)
		seedStock(keyA, new BigDecimal("10"));
		seedStock(keyA, new BigDecimal("10"));

		// Fire event: move only one HU's qty (10) from keyA → keyB
		final AttributesChangedEventHandlerForStockRecords handler = new AttributesChangedEventHandlerForStockRecords(stockDataUpdateRequestHandler);
		handler.handleEvent(AttributesChangedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(CLIENT_ID, ORG_ID))
				.warehouseId(WarehouseId.ofRepoId(WAREHOUSE_ID))
				.date(Instant.parse("2026-06-02T00:00:00.00Z"))
				.productId(PRODUCT_ID)
				.qty(new BigDecimal("10"))
				.oldStorageAttributes(attributesKeyWithASI(2100))
				.newStorageAttributes(attributesKeyWithASI(2200))
				.huId(201)
				.build());

		// keyA still holds the remainder (10), not zeroed
		final I_MD_Stock keyARow = getMDStockRecord(PRODUCT_ID, keyA);
		assertThat(keyARow)
				.as("MD_Stock(keyA) row must exist after partial re-key")
				.isNotNull();
		assertThat(keyARow.getQtyOnHand())
				.as("MD_Stock(keyA).QtyOnHand must be 10 (remainder — one HU still on old key)")
				.isEqualByComparingTo(new BigDecimal("10"));

		// keyB received exactly the moved qty
		final I_MD_Stock keyBRow = getMDStockRecord(PRODUCT_ID, keyB);
		assertThat(keyBRow)
				.as("MD_Stock(keyB) row must exist after partial re-key")
				.isNotNull();
		assertThat(keyBRow.getQtyOnHand())
				.as("MD_Stock(keyB).QtyOnHand must be 10 (the moved HU qty)")
				.isEqualByComparingTo(new BigDecimal("10"));
	}

	/**
	 * U3 — uneven split: seed 12+8=20 on keyA, move 8 → keyB. keyA must show 12, keyB must show 8.
	 */
	@Test
	public void reKeyUnevenSplit_movesExactChangedQty()
	{
		final AttributesKey keyA = attributesKey(2300);
		final AttributesKey keyB = attributesKey(2400);

		seedStock(keyA, new BigDecimal("12"));
		seedStock(keyA, new BigDecimal("8"));

		final AttributesChangedEventHandlerForStockRecords handler = new AttributesChangedEventHandlerForStockRecords(stockDataUpdateRequestHandler);
		handler.handleEvent(AttributesChangedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(CLIENT_ID, ORG_ID))
				.warehouseId(WarehouseId.ofRepoId(WAREHOUSE_ID))
				.date(Instant.parse("2026-06-03T00:00:00.00Z"))
				.productId(PRODUCT_ID)
				.qty(new BigDecimal("8"))
				.oldStorageAttributes(attributesKeyWithASI(2300))
				.newStorageAttributes(attributesKeyWithASI(2400))
				.huId(230)
				.build());

		// keyA retains the untouched qty (12)
		final I_MD_Stock keyARow = getMDStockRecord(PRODUCT_ID, keyA);
		assertThat(keyARow)
				.as("MD_Stock(keyA) row must exist after uneven split")
				.isNotNull();
		assertThat(keyARow.getQtyOnHand())
				.as("MD_Stock(keyA).QtyOnHand must be 12 after moving 8 to keyB")
				.isEqualByComparingTo(new BigDecimal("12"));

		// keyB receives exactly the moved qty (8)
		final I_MD_Stock keyBRow = getMDStockRecord(PRODUCT_ID, keyB);
		assertThat(keyBRow)
				.as("MD_Stock(keyB) row must exist after uneven split")
				.isNotNull();
		assertThat(keyBRow.getQtyOnHand())
				.as("MD_Stock(keyB).QtyOnHand must be 8 (exact moved qty)")
				.isEqualByComparingTo(new BigDecimal("8"));
	}

	/**
	 * U4 — MRP-excluded warehouse: re-key happens regardless of MRP_Exclude flag.
	 * MD_Stock tracks real on-hand qty even for warehouses excluded from material disposition.
	 */
	@Test
	public void reKeysStockEvenForMrpExcludedWarehouse()
	{
		// Create a warehouse record with MRP_Exclude = "Y"
		final I_M_Warehouse warehouseRecord = newInstance(I_M_Warehouse.class);
		warehouseRecord.setMRP_Exclude("Y");
		saveRecord(warehouseRecord);
		final int mrpExcludedWarehouseId = warehouseRecord.getM_Warehouse_ID();
		final WarehouseId mrpExcludedWarehouseIdObj = WarehouseId.ofRepoId(mrpExcludedWarehouseId);

		final AttributesKey keyA = attributesKey(2500);
		final AttributesKey keyB = attributesKey(2600);

		// Seed stock under the MRP-excluded warehouse
		seedStockForWarehouse(mrpExcludedWarehouseIdObj, keyA, new BigDecimal("13"));

		// Fire re-key event on the MRP-excluded warehouse
		final AttributesChangedEventHandlerForStockRecords handler = new AttributesChangedEventHandlerForStockRecords(stockDataUpdateRequestHandler);
		handler.handleEvent(AttributesChangedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(CLIENT_ID, ORG_ID))
				.warehouseId(mrpExcludedWarehouseIdObj)
				.date(Instant.parse("2026-06-04T00:00:00.00Z"))
				.productId(PRODUCT_ID)
				.qty(new BigDecimal("13"))
				.oldStorageAttributes(attributesKeyWithASI(2500))
				.newStorageAttributes(attributesKeyWithASI(2600))
				.huId(250)
				.build());

		// Re-key must have happened: keyA zeroed, keyB has qty — no MRP-exclusion guard suppresses this
		final I_MD_Stock keyARow = getMDStockRecordForWarehouse(mrpExcludedWarehouseIdObj, PRODUCT_ID, keyA);
		assertThat(keyARow)
				.as("MD_Stock(keyA, mrpExcludedWarehouse) must exist (genuinely zeroed)")
				.isNotNull();
		assertThat(keyARow.getQtyOnHand())
				.as("MD_Stock(keyA).QtyOnHand must be 0 after re-key (MRP-excluded warehouse still tracked)")
				.isEqualByComparingTo(BigDecimal.ZERO);

		final I_MD_Stock keyBRow = getMDStockRecordForWarehouse(mrpExcludedWarehouseIdObj, PRODUCT_ID, keyB);
		assertThat(keyBRow)
				.as("MD_Stock(keyB, mrpExcludedWarehouse) must exist after re-key")
				.isNotNull();
		assertThat(keyBRow.getQtyOnHand())
				.as("MD_Stock(keyB).QtyOnHand must be 13 after re-key (MRP-excluded warehouse still tracked)")
				.isEqualByComparingTo(new BigDecimal("13"));
	}

	// --- helpers ---

	private void seedStock(@NonNull final AttributesKey attributesKey, @NonNull final BigDecimal qty)
	{
		final StockDataRecordIdentifier identifier = StockDataRecordIdentifier.builder()
				.clientId(ClientId.ofRepoId(CLIENT_ID))
				.orgId(OrgId.ofRepoId(ORG_ID))
				.warehouseId(WarehouseId.ofRepoId(WAREHOUSE_ID))
				.productId(ProductId.ofRepoId(PRODUCT_ID))
				.storageAttributesKey(attributesKey)
				.build();

		// seeding represents a receipt/shipment booking (transaction-driven), not an attribute change
		final StockDataUpdateRequest request = StockDataUpdateRequest.builder()
				.identifier(identifier)
				.onHandQtyChange(qty)
				.sourceInfo(StockChangeSourceInfo.ofTransactionId(1))
				.build();

		stockDataUpdateRequestHandler.handleDataUpdateRequest(request);
	}

	private BigDecimal getQtyOnHand(@NonNull final AttributesKey attributesKey)
	{
		final I_MD_Stock record = Services.get(IQueryBL.class)
				.createQueryBuilder(I_MD_Stock.class)
				.addEqualsFilter(I_MD_Stock.COLUMNNAME_M_Product_ID, PRODUCT_ID)
				.addEqualsFilter(I_MD_Stock.COLUMNNAME_M_Warehouse_ID, WAREHOUSE_ID)
				.addEqualsFilter(I_MD_Stock.COLUMN_AttributesKey, attributesKey.getAsString())
				.create()
				.firstOnly(I_MD_Stock.class);

		return record != null ? record.getQtyOnHand() : BigDecimal.ZERO;
	}

	/**
	 * Returns the MD_Stock row for the given product/warehouse/attributesKey, or {@code null} if absent.
	 * Use this for row-existence assertions before reading QtyOnHand — avoids the vacuous-zero of
	 * {@link #getQtyOnHand} which returns ZERO for both a missing row and a genuinely-zeroed row.
	 */
	private I_MD_Stock getMDStockRecord(final int productId, @NonNull final AttributesKey attributesKey)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_MD_Stock.class)
				.addEqualsFilter(I_MD_Stock.COLUMNNAME_M_Product_ID, productId)
				.addEqualsFilter(I_MD_Stock.COLUMNNAME_M_Warehouse_ID, WAREHOUSE_ID)
				.addEqualsFilter(I_MD_Stock.COLUMN_AttributesKey, attributesKey.getAsString())
				.create()
				.firstOnly(I_MD_Stock.class);
	}

	private static AttributesKeyWithASI attributesKeyWithASI(final int attributeValueRepoId)
	{
		return AttributesKeyWithASI.of(attributesKey(attributeValueRepoId), DUMMY_ASI_ID);
	}

	private static AttributesKey attributesKey(final int attributeValueRepoId)
	{
		return AttributesKey.ofAttributeValueIds(AttributeValueId.ofRepoId(attributeValueRepoId));
	}

	/** Variant of {@link #seedStock} that seeds against an arbitrary warehouse (for MRP-exclusion tests). */
	private void seedStockForWarehouse(@NonNull final WarehouseId warehouseId, @NonNull final AttributesKey attributesKey, @NonNull final BigDecimal qty)
	{
		final StockDataRecordIdentifier identifier = StockDataRecordIdentifier.builder()
				.clientId(ClientId.ofRepoId(CLIENT_ID))
				.orgId(OrgId.ofRepoId(ORG_ID))
				.warehouseId(warehouseId)
				.productId(ProductId.ofRepoId(PRODUCT_ID))
				.storageAttributesKey(attributesKey)
				.build();

		stockDataUpdateRequestHandler.handleDataUpdateRequest(StockDataUpdateRequest.builder()
				.identifier(identifier)
				.onHandQtyChange(qty)
				.sourceInfo(StockChangeSourceInfo.ofTransactionId(1))
				.build());
	}

	/** Variant of {@link #getMDStockRecord} that queries against an arbitrary warehouse. */
	private I_MD_Stock getMDStockRecordForWarehouse(@NonNull final WarehouseId warehouseId, final int productId, @NonNull final AttributesKey attributesKey)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_MD_Stock.class)
				.addEqualsFilter(I_MD_Stock.COLUMNNAME_M_Product_ID, productId)
				.addEqualsFilter(I_MD_Stock.COLUMNNAME_M_Warehouse_ID, warehouseId.getRepoId())
				.addEqualsFilter(I_MD_Stock.COLUMN_AttributesKey, attributesKey.getAsString())
				.create()
				.firstOnly(I_MD_Stock.class);
	}
}
