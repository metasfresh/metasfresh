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


import java.math.BigDecimal;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.model.I_M_HU;

/**
 * Handling Unit Instance Storage
 *
 * @author tsa
 *
 */
public interface IHUStorage extends IGenericHUStorage
{
	I_M_HU getM_HU();

	List<IHUProductStorage> getProductStorages();

	/**
	 * Gets product storage for given product.
	 *
	 * @param product
	 * @return product storage; never return null;
	 * @throws AdempiereException in case product storage was not found
	 */
	IHUProductStorage getProductStorage(I_M_Product product);

	/**
	 * Gets product storage for given product.
	 *
	 * @param product
	 * @return product storage; if no storage was found, null is returned
	 */
	IHUProductStorage getProductStorageOrNull(I_M_Product product);

	/**
	 * @return full qty of the {@link IHUProductStorage}s of this {@link IHUStorage}
	 */
	BigDecimal getQtyForProductStorages();

	/**
	 * Propagate ALL storage products & quantities - UOM-based - to parent (incremental)
	 */
	void rollup();

	/**
	 * Revert propagation of ALL storage products & quantities - UOM-based - to parent (incremental)
	 */
	void rollupRevert();

	/**
	 * Checks if this is a "Single Product Storage".
	 *
	 * A storage is considered to be a "Single Product Storage" when there is maximum one type of product in that storage.<br/>
	 * An empty storage is considered to be a "Single Product Storage".<br/>
	 * If a storage has more then one M_Product_ID, that's not a single product storage.
	 *
	 * @return true if this storage is "Single Product Storage"
	 */
	boolean isSingleProductStorage();

	/**
	 * Gets the {@link I_M_Product} stored in this HU Storage.
	 * 
	 * @return <ul>
	 *         <li>single product stored in this storage
	 *         <li><code>null</code> if the storage is empty or there are more then one products stored
	 *         </ul>
	 * @see #isSingleProductStorage()
	 */
	I_M_Product getSingleProductOrNull();

	/**
	 * <b>NOTE:</b> HU already contains it's children HUStorages due to storage lines already being created by rollupIncremental.<br>
	 * <br>
	 * <b>Returns</b> <code>null</code> if storage UOMType incompatibility was encountered (i.e one storage does not have it's UOM specified or has it different from the others)<br>
	 * <br>
	 * <b>Returns</b> <code>C_UOM</code> accepted among all the HU storages' UOMTypes<br>
	 *
	 * @return <code>C_UOM</code> or null
	 */
	I_C_UOM getC_UOMOrNull();
}
