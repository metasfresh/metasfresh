package de.metas.handlingunits.inventory.draftlinescreator.process;

import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;

import de.metas.adempiere.model.I_M_Product;
import de.metas.handlingunits.inventory.Inventory;
import de.metas.handlingunits.inventory.draftlinescreator.HUsForInventoryStrategies;
import de.metas.handlingunits.inventory.draftlinescreator.HuForInventoryLineFactory;
import de.metas.handlingunits.inventory.draftlinescreator.LocatorAndProductStrategy;
import de.metas.handlingunits.model.I_M_Locator;
import de.metas.process.Param;
import de.metas.product.ProductId;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class M_Inventory_CreateLines_ForLocatorAndProduct extends DraftInventoryBase
{
	private final HuForInventoryLineFactory huForInventoryLineFactory = SpringContextHolder.instance.getBean(HuForInventoryLineFactory.class);

	@Param(parameterName = I_M_Locator.COLUMNNAME_M_Locator_ID)
	private int locatorId;

	@Param(parameterName = I_M_Product.COLUMNNAME_M_Product_ID)
	private int productId;

	@Override
	protected LocatorAndProductStrategy createStrategy(@NonNull final Inventory inventory)
	{
		final WarehouseId warehouseId = inventory.getWarehouseId();

		return HUsForInventoryStrategies.locatorAndProduct()
				.warehouseId(warehouseId)
				.locatorId(LocatorId.ofRepoIdOrNull(warehouseId, locatorId))
				.productId(ProductId.ofRepoIdOrNull(productId))
				.huForInventoryLineFactory(huForInventoryLineFactory)
				.build();
	}
}
