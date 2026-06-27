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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;

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

		final StockDataUpdateRequest request = StockDataUpdateRequest.builder()
				.identifier(identifier)
				.onHandQtyChange(qty)
				.sourceInfo(StockChangeSourceInfo.ofHuAttributeChange(333))
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
}
