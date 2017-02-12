package de.metas.fresh.picking.form;

import java.math.BigDecimal;
import java.util.Set;

import org.adempiere.util.collections.Predicate;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Warehouse;

import de.metas.adempiere.form.IPackingItem;
import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public interface IFreshPackingItem extends IPackingItem
{
	@Override
	IFreshPackingItem copy();

	I_C_BPartner getC_BPartner();

	int getC_BPartner_ID();

	I_M_HU_PI_Item_Product getM_HU_PI_Item_Product();

	/**
	 * Similar to {@link #getC_BPartner_Location()}.
	 *
	 * @return
	 */
	I_C_BPartner_Location getC_BPartner_Location();

	/**
	 *
	 * @return the <i>effective</i> <code>C_BPartner_Location_ID</code> (can be the override value) where the item is supposed to be shipped.
	 */
	int getC_BPartner_Location_ID();

	Set<I_M_Warehouse> getWarehouses();

	Set<Integer> getWarehouseIds();

	/**
	 * Subtract the given quantity from this packing item and create a new packing item with it. Don't alter this packing item.
	 *
	 * @param subtrahent
	 * @param acceptShipmentSchedulePredicate
	 * @return
	 */
	IFreshPackingItem subtractToPackingItem(BigDecimal subtrahent, Predicate<I_M_ShipmentSchedule> acceptShipmentSchedulePredicate);
}
