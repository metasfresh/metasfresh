package de.metas.handlingunits.sourcehu;

import de.metas.adempiere.model.I_M_Product;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.handlingunits.sourcehu.SourceHUsService.MatchingSourceHusQuery;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class ISourceHuService_SourceHusQueryTest
{

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final I_M_HU_PI virtualPI = newInstance(I_M_HU_PI.class);
		virtualPI.setM_HU_PI_ID(HuPackingInstructionsId.VIRTUAL.getRepoId());
		saveRecord(virtualPI);

		final I_M_HU_PI_Version virtualPIVersion = newInstance(I_M_HU_PI_Version.class);
		virtualPIVersion.setM_HU_PI_Version_ID(HuPackingInstructionsVersionId.VIRTUAL.getRepoId());
		virtualPIVersion.setM_HU_PI_ID(virtualPI.getM_HU_PI_ID());
		virtualPIVersion.setIsCurrent(true);
		saveRecord(virtualPIVersion);
	}

	@Test
	public void createFromHuId()
	{
		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		save(warehouse);

		final I_M_HU hu = createHuWithLocatorOfWarehouse(warehouse);

		createStorageOfHuWithProductIdAndQty(hu, BigDecimal.ZERO);
		final ProductId storageProductId2 = createStorageOfHuWithProductIdAndQty(hu, BigDecimal.TEN);
		final ProductId storageProductId3 = createStorageOfHuWithProductIdAndQty(hu, BigDecimal.ONE);

		final MatchingSourceHusQuery query = SourceHUsService.MatchingSourceHusQuery.fromHuId(HuId.ofRepoId(hu.getM_HU_ID()));
		assertThat(query).isNotNull();
		assertThat(query.getWarehouseIds()).containsExactly(WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID()));
		assertThat(query.getProductIds()).containsOnly(storageProductId2, storageProductId3);
	}

	private ProductId createStorageOfHuWithProductIdAndQty(@NonNull final I_M_HU hu, @NonNull final BigDecimal qty)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		save(product);
		final I_C_UOM uom = newInstance(I_C_UOM.class);
		save(uom);

		final I_M_HU_Storage huStorage = newInstance(I_M_HU_Storage.class);
		huStorage.setM_HU(hu);
		huStorage.setM_Product_ID(product.getM_Product_ID());
		huStorage.setC_UOM_ID(uom.getC_UOM_ID());
		huStorage.setQty(qty);
		save(huStorage);

		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	private I_M_HU createHuWithLocatorOfWarehouse(final I_M_Warehouse warehouse)
	{
		final I_M_Locator locator = newInstance(I_M_Locator.class);
		locator.setM_Warehouse(warehouse);
		save(locator);

		final I_M_HU hu = newInstance(I_M_HU.class);
		hu.setM_HU_PI_Version_ID(HuPackingInstructionsVersionId.VIRTUAL.getRepoId());
		hu.setM_Locator_ID(locator.getM_Locator_ID());
		save(hu);
		return hu;
	}
}
