package de.metas.material.cockpit.stock;

import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.stock.ResetStockPInstanceId;
import de.metas.organization.OrgId;
import de.metas.process.PInstanceId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

/**
 * Covers the MD_Stock reset root-cause fix: the reset path is <b>idempotent</b> (set-to-truth) so
 * overlapping concurrent runs can no longer compound corrections into a runaway escalation.
 */
class StockDataUpdateRequestHandlerTest
{
	private static final ClientId CLIENT = ClientId.ofRepoId(1000000);
	private static final OrgId ORG = OrgId.ofRepoId(1000000);
	private static final WarehouseId WAREHOUSE = WarehouseId.ofRepoId(540008);
	private static final ProductId PRODUCT = ProductId.ofRepoId(2010199);
	private static final AttributesKey KEY = AttributesKey.NONE; // -1002, as seen on many corrupted PROD rows

	private PostMaterialEventService postMaterialEventService;
	private StockDataUpdateRequestHandler handler;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		postMaterialEventService = Mockito.mock(PostMaterialEventService.class);
		handler = new StockDataUpdateRequestHandler(postMaterialEventService);
	}

	private static StockDataRecordIdentifier identifier()
	{
		return StockDataRecordIdentifier.builder()
				.clientId(CLIENT).orgId(ORG).warehouseId(WAREHOUSE).productId(PRODUCT)
				.storageAttributesKey(KEY)
				.build();
	}

	private static StockChangeSourceInfo resetSource()
	{
		return StockChangeSourceInfo.ofResetStockPInstanceId(ResetStockPInstanceId.ofPInstanceId(PInstanceId.ofRepoId(1)));
	}

	private static void seedMdStock(final BigDecimal qtyOnHand)
	{
		final I_MD_Stock rec = newInstance(I_MD_Stock.class);
		InterfaceWrapperHelper.setValue(rec, I_MD_Stock.COLUMNNAME_AD_Client_ID, CLIENT.getRepoId());
		rec.setAD_Org_ID(ORG.getRepoId());
		rec.setM_Product_ID(PRODUCT.getRepoId());
		rec.setM_Warehouse_ID(WAREHOUSE.getRepoId());
		rec.setAttributesKey(KEY.getAsString());
		rec.setQtyOnHand(qtyOnHand);
		save(rec);
	}

	private static BigDecimal currentQtyOnHand()
	{
		final I_MD_Stock rec = Services.get(IQueryBL.class)
				.createQueryBuilder(I_MD_Stock.class)
				.addEqualsFilter(I_MD_Stock.COLUMNNAME_M_Product_ID, PRODUCT.getRepoId())
				.create()
				.firstOnlyNotNull(I_MD_Stock.class);
		return rec.getQtyOnHand();
	}

	// --- Create path: retrieveOrCreateDataRecord's create branch (no pre-existing row) ---

	@Test
	void create_newBucket_whenNoneExists()
	{
		// no seedMdStock(...) call: forces the create branch of retrieveOrCreateDataRecord (save + unique-violation catch).
		handler.handleResetToQtyOnHand(identifier(), new BigDecimal("100"), resetSource());
		assertThat(currentQtyOnHand()).isEqualByComparingTo("100");
		verify(postMaterialEventService, times(1)).enqueueEventAfterNextCommit(any());
	}

	// --- Reset path: idempotent set-to-truth (the concurrency root-cause fix) ---

	@Test
	void reset_convergesToTruth_evenFromAnEscalatedQty()
	{
		seedMdStock(new BigDecimal("1E40")); // a corrupted, escalated value
		handler.handleResetToQtyOnHand(identifier(), new BigDecimal("100"), resetSource());
		assertThat(currentQtyOnHand()).isEqualByComparingTo("100");
		verify(postMaterialEventService, times(1)).enqueueEventAfterNextCommit(any()); // the change fired a StockChangedEvent
	}

	@Test
	void reset_isIdempotent_underRepeatedApplication()
	{
		// two overlapping runs applying the same HU-truth must NOT compound (the runaway failure mode)
		seedMdStock(BigDecimal.ZERO);
		handler.handleResetToQtyOnHand(identifier(), new BigDecimal("100"), resetSource());
		handler.handleResetToQtyOnHand(identifier(), new BigDecimal("100"), resetSource());
		assertThat(currentQtyOnHand()).isEqualByComparingTo("100"); // not 200
		verify(postMaterialEventService, times(1)).enqueueEventAfterNextCommit(any()); // only the first call changed anything
	}
}
