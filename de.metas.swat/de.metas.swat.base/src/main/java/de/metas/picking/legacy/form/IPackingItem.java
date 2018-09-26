package de.metas.picking.legacy.form;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;

/*
 * #%L
 * de.metas.swat.base
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

public interface IPackingItem
{
	boolean isSameAs(IPackingItem item);

	IPackingItem copy();

	PackingItemGroupingKey getGroupingKey();

	BPartnerId getBPartnerId();

	BPartnerLocationId getBPartnerLocationId();

	Set<WarehouseId> getWarehouseIds();

	ProductId getProductId();

	I_C_UOM getC_UOM();

	Quantity getQtySum();

	/**
	 * Clears current schedules and set them from given <code>packingItem</code>.
	 */
	void setSchedules(IPackingItem packingItem);

	void addSchedules(IPackingItem packingItem);

	void addSchedules(ShipmentScheduleQtyPickedMap toAdd);

	/**
	 *
	 * @param subtrahent
	 * @return subtracted schedule/qty pairs
	 * @throws PackingItemSubtractException if required qty could not be fully subtracted
	 */
	ShipmentScheduleQtyPickedMap subtract(Quantity subtrahent);

	/**
	 * Subtract the given quantity from this packing item and create a new packing item with it.
	 *
	 * @param subtrahent
	 * @param acceptShipmentSchedulePredicate may be {@code null}.
	 */
	IPackingItem subtractToPackingItem(Quantity subtrahent, Predicate<I_M_ShipmentSchedule> acceptShipmentSchedulePredicate);

	ShipmentScheduleQtyPickedMap getQtys();

	/**
	 * For this item, return the open quantity to be packed for the given {@code sched}.
	 * 
	 * @param sched
	 * @return
	 */
	Quantity getQtyForSched(I_M_ShipmentSchedule sched);

	List<I_M_ShipmentSchedule> getShipmentSchedules();
}
