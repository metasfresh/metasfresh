package de.metas.storage.spi.hu.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.product.ProductId;
import de.metas.storage.IStorageQuery;

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

class HUStorageQueryTest
{
	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void createHUQueryBuilder()
	{
		final I_C_BPartner bPartnerRecord = newInstance(I_C_BPartner.class);
		saveRecord(bPartnerRecord);
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(bPartnerRecord.getC_BPartner_ID());

		final I_M_Product productRecord = newInstance(I_M_Product.class);
		saveRecord(productRecord);
		final ProductId productId = ProductId.ofRepoId(productRecord.getM_Product_ID());

		final I_M_Warehouse warehouseRecord = newInstance(I_M_Warehouse.class);
		saveRecord(warehouseRecord);
		final WarehouseId warehouseId = WarehouseId.ofRepoId(warehouseRecord.getM_Warehouse_ID());

		final IStorageQuery storageQuery = HUStorageEngine.instance
				.newStorageQuery()
				.addProductId(productId)
				.addWarehouseId(warehouseId)
				.addBPartnerId(bPartnerId)
				.addBPartnerId(null);

		final IHUQueryBuilder queryBuilder = HUStorageQuery
				.cast(storageQuery)
				.createHUQueryBuilder();

		assertThat(queryBuilder.getOnlyWithProductIds()).containsOnly(productId);
		assertThat(queryBuilder.getOnlyInWarehouseIds()).containsOnly(warehouseId);
		assertThat(queryBuilder.getOnlyInBPartnerIds()).containsOnly(bPartnerId, null);
	}

}
