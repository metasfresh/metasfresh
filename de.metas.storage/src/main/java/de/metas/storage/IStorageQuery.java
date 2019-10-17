package de.metas.storage;

import java.util.Collection;

/*
 * #%L
 * de.metas.storage
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

import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Locator;

import de.metas.bpartner.BPartnerId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;

/**
 * Used to retrieve {@link IStorageRecord}s. Use {@link IStorageEngine#newStorageQuery()} to get an instance.
 * <p>
 * Also see {@link IStorageSegment}.
 */
public interface IStorageQuery
{
	IStorageQuery addProductId(ProductId productId);

	/**
	 * @param bpartnerId may also be <code>null</code> to indicate that storages without any bpartner assignment shall be matched.
	 */
	IStorageQuery addBPartnerId(BPartnerId bpartnerId);

	IStorageQuery addWarehouseId(WarehouseId warehouseId);

	IStorageQuery addWarehouseIds(Collection<WarehouseId> warehouseIds);

	/**
	 * Add another attribute filter, <b>if</b> the given <code>attribute</code> is relevant according to the respective storage engine implementation.
	 */
	IStorageQuery addAttribute(I_M_Attribute attribute, String attributeValueType, Object attributeValue);

	/**
	 * Add all attributes (that are allowed to be used in storage querying) from given attribute set.
	 *
	 * NOTE: depends on implementation, it could be that not all attributes will be added but only the considered ones.
	 */
	IStorageQuery addAttributes(IAttributeSet attributeSet);

	/**
	 *
	 * @param storageRecord
	 * @return true if given record was matched by this query
	 */
	boolean matches(IStorageRecord storageRecord);

	/**
	 * Set if we shall exclude the after picking locators (i.e. where {@link I_M_Locator#isAfterPickingLocator()} returns <code>true</code>).<br>
	 * By default, after picking locators are excluded
	 */
	IStorageQuery setExcludeAfterPickingLocator(boolean excludeAfterPickingLocator);

	/**
	 * Set if we shall only include storages that are not reserved, or are reserved to the particular given orderLine.
	 */
	IStorageQuery setExcludeReservedToOtherThan(OrderLineId orderLineId);

	IStorageQuery setExcludeReserved();
}
