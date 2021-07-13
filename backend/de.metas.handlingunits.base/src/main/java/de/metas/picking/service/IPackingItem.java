package de.metas.picking.service;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;

import java.util.Set;
import java.util.function.Predicate;

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

/**
 * You can obtain an instance via {@link PackingItems#newPackingItem(java.util.Map)}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
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

	HUPIItemProductId getPackingMaterialId();

	Set<ShipmentScheduleId> getShipmentScheduleIds();

	PackingItemParts getParts();

	/**
	 * Clears current schedules and set them from given <code>packingItem</code>.
	 */
	void setPartsFrom(IPackingItem packingItem);

	void addParts(IPackingItem packingItem);

	IPackingItem addPartsAndReturn(IPackingItem packingItem);

	void addParts(PackingItemParts toAdd);

	/**
	 * @return subtracted parts
	 * @throws PackingItemSubtractException if required qty could not be fully subtracted
	 */
	PackingItemParts subtract(Quantity subtrahent);

	/**
	 * Subtract the given quantity from this packing item and create a new packing item with it.
	 *
	 * @param subtrahent
	 * @param partsFilter may be {@code null}.
	 */
	IPackingItem subtractToPackingItem(Quantity subtrahent, Predicate<PackingItemPart> partsFilter);
}
