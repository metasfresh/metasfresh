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
import org.compiere.SpringContextHolder;
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
		SpringContextHolder.registerJUnitBean(stockDataUpdateRequestHandler);
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

		// When: call the stub handler with an AttributesChangedEvent(old=A, new=B)
		final AttributesChangedEventHandlerForStockRecords handler = new AttributesChangedEventHandlerForStockRecords();
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

		// Then (RED): stub no-ops, so keyA still has QTY and keyB has nothing
		// Task 3 will implement re-key logic to make these assertions pass green.
		assertThat(getQtyOnHand(keyA))
				.as("MD_Stock for old attributesKey A must be 0 after re-key")
				.isEqualByComparingTo(BigDecimal.ZERO);
		assertThat(getQtyOnHand(keyB))
				.as("MD_Stock for new attributesKey B must equal QTY after re-key")
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

	private static AttributesKeyWithASI attributesKeyWithASI(final int attributeValueRepoId)
	{
		return AttributesKeyWithASI.of(attributesKey(attributeValueRepoId), DUMMY_ASI_ID);
	}

	private static AttributesKey attributesKey(final int attributeValueRepoId)
	{
		return AttributesKey.ofAttributeValueIds(AttributeValueId.ofRepoId(attributeValueRepoId));
	}
}
