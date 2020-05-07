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
import java.util.Comparator;

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.allocation.IAllocationRequest;

public interface IProductStorage
{
	/** Comparator used to sort {@link IProductStorage}s by Product Name */
	Comparator<IProductStorage> COMPARATOR_ByProductName = new Comparator<IProductStorage>()
	{

		@Override
		public int compare(final IProductStorage productStorage1, final IProductStorage productStorage2)
		{
			final String productName1 = getProductName(productStorage1);
			final String productName2 = getProductName(productStorage2);
			return productName1.compareTo(productName2);
		}

		private final String getProductName(final IProductStorage productStorage)
		{
			if (productStorage == null)
			{
				return "";
			}
			final I_M_Product product = productStorage.getM_Product();
			if (product == null)
			{
				return "";
			}
			final String productName = product.getName();
			if (productName == null)
			{
				return "";
			}
			return productName;
		}
	};

	/**
	 * @return product; never <code>null</code>
	 */
	I_M_Product getM_Product();
	
	default int getM_Product_ID()
	{
		final I_M_Product product = getM_Product();
		return product == null ? -1 : product.getM_Product_ID();
	}

	I_C_UOM getC_UOM();

	BigDecimal getQtyFree();

	BigDecimal getQty();

	/**
	 * Gets storage Qty, converted to given UOM.
	 *
	 * @param uom
	 * @return Qty converted to given UOM.
	 */
	BigDecimal getQty(I_C_UOM uom);

	/**
	 * @return storage capacity
	 */
	BigDecimal getQtyCapacity();

	IAllocationRequest removeQty(IAllocationRequest request);

	IAllocationRequest addQty(IAllocationRequest request);

	void markStaled();

	boolean isEmpty();

	/**
	 * @return true if this storage allows negative storages
	 */
	boolean isAllowNegativeStorage();
}
