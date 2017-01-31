package de.metas.handlingunits;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.Date;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;

public interface IHUCapacityBL extends ISingletonService
{
	/**
	 * Create Infinite capacity
	 *
	 * @param product
	 * @param uom
	 * @return inifinite capacity
	 */
	IHUCapacityDefinition createInfiniteCapacity(I_M_Product product, I_C_UOM uom);

	IHUCapacityDefinition createCapacity(BigDecimal qty, I_M_Product product, I_C_UOM uom, boolean allowNegativeCapacity);

	IStatefulHUCapacityDefinition createStatefulCapacity(IHUCapacityDefinition capacity, BigDecimal qtyUsed);

	/**
	 *
	 * @param itemDefProduct
	 * @param product the product to be returned in the resulting capacity definition; optional, unless the given <code>itemDefProduct</code> has <code>AllowAnyProduct='Y'</code>;
	 *            if <code>null</code> , then the given <code>itemDefProduct</code>'s M_Product is used.
	 * @param uom
	 * @return
	 * @throws HUException if <code>product!=null</code> and the product's ID is different from <code>itemDefProduct</code>'s <code>M_Product_ID</code>.
	 *             Also, if <code>product==null</code> and <code>itemDefProduct</code> does not reference any product either.
	 */
	IHUCapacityDefinition getCapacity(I_M_HU_PI_Item_Product itemDefProduct, I_M_Product product, I_C_UOM uom);

	/**
	 * Retrieve the and evaluate the {@link I_M_HU_PI_Item_Product} for the given <code>huItem</code>, <code>product</code> and <code>date</code>. If there is no such record, if returns a capacity definition with a
	 * zero-capacity. If a record is found, return the result of {@link #getCapacity(I_M_HU_PI_Item_Product, I_M_Product, I_C_UOM)}.
	 *
	 * @param huItem
	 * @param product
	 * @param uom
	 * @param date
	 * @return
	 * @see IHUPIItemProductDAO#retrievePIMaterialItemProduct(I_M_HU_Item, I_M_Product, Date) to learn which I_M_HU_PI_Item_Product's capacitiy is returned if there is more than one.
	 */
	IHUCapacityDefinition getCapacity(I_M_HU_Item huItem, I_M_Product product, I_C_UOM uom, final Date date);

	boolean isInfiniteCapacity(I_M_HU_PI_Item_Product itemDefProduct);

	/**
	 * Evaluate the given {@code capacityDefinition} and create a new one based how much the given {@code qtyUsed} and {@code qtyUsedUOM} would take away.
	 * 
	 * @param qtyUsed
	 * @param qtyUsedUOM
	 * @param capacityDefinition
	 * @return
	 */
	IHUCapacityDefinition getAvailableCapacity(BigDecimal qtyUsed, I_C_UOM qtyUsedUOM, IHUCapacityDefinition capacityDefinition);

	/**
	 * Calculates how many capacities like <code>capacityDef</code> do we need to cover given <code>qty</code>
	 *
	 * <p>
	 * e.g. if Qty=13 and Capacity=10 then QtyPacks=2 (13/10 rounded up).
	 *
	 * @param qty
	 * @param uom quantity's unit of measure
	 * @param capacityDef
	 * @return how many capacities are required or NULL if capacity is not available
	 */
	Integer calculateQtyPacks(BigDecimal qty, I_C_UOM uom, IHUCapacityDefinition capacityDef);

	IHUCapacityDefinition multiply(final IHUCapacityDefinition capacityDef, int multiplier);

	boolean isValidItemProduct(final I_M_HU_PI_Item_Product itemDefProduct);

	/**
	 * Convert given capacity to <code>uomTo</code>
	 *
	 * @param customCapacity
	 * @param uom
	 * @return capacity converted to <code>uomTo</code>
	 */
	IHUCapacityDefinition convertToUOM(IHUCapacityDefinition capacity, I_C_UOM uomTo);

}
