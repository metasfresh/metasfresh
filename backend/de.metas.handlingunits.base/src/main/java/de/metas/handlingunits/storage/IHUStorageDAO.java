package de.metas.handlingunits.storage;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;

import org.compiere.model.I_C_UOM;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Item_Storage;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.product.ProductId;
import de.metas.uom.UOMType;

public interface IHUStorageDAO
{
	<T> T newInstance(Class<T> modelClass, Object contextProvider);

	/**
	 * Called by API to notify that a new HU was just created.
	 *
	 * NOTE: don't call it directly
	 *
	 * @param item
	 */
	void initHUStorages(I_M_HU hu);

	/**
	 * Called by API to notify that a new HU Item was just created.
	 *
	 * NOTE: don't call it directly
	 *
	 * @param item
	 */
	void initHUItemStorages(I_M_HU_Item item);

	I_M_HU_Storage retrieveStorage(I_M_HU hu, ProductId productId);

	void save(I_M_HU_Storage storage);

	List<I_M_HU_Storage> retrieveStorages(I_M_HU hu);

	void save(I_M_HU_Item_Storage storageLine);

	List<I_M_HU_Item_Storage> retrieveItemStorages(I_M_HU_Item item);

	I_M_HU_Item_Storage retrieveItemStorage(I_M_HU_Item item, ProductId productId);

	void save(I_M_HU_Item item);

	/**
	 * <b>NOTE:</b> HU already contains it's children HUStorages due to storage lines already being created by rollupIncremental.<br>
	 * <br>
	 * <b>Returns</b> <code>null</code> if storage UOMType incompatibility was encountered (i.e one storage does not have it's UOM specified or has it different from the others)<br>
	 * <br>
	 * <b>Returns</b> <code>C_UOM</code> accepted among all the HU storages' UOMTypes<br>
	 *
	 * @param hu
	 * @return <code>C_UOM</code> or null
	 */
	I_C_UOM getC_UOMOrNull(I_M_HU hu);

	/**
	 * <b>NOTE:</b> HU already contains it's children HUStorages due to storage lines already being created by rollupIncremental.<br>
	 * <br>
	 * <b>Returns</b> <code>null</code> if storage UOMType incompatibility was encountered (i.e one storage does not have it's UOM specified or has it different from the others)<br>
	 * <br>
	 * <b>Returns</b> <code>C_UOM.UOMType</code> accepted among all the HU storages' UOMs<br>
	 *
	 * @param hu
	 * @return <code>C_UOM.UOMType</code> or null
	 */
	UOMType getC_UOMTypeOrNull(I_M_HU hu);

}
