package de.metas.storage;

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
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;

/**
 * Used to retrieve {@link IStorageRecord}s. Use {@link IStorageBL#createStorageQuery()} to get an instance.
 * <p>
 * Also see {@link IStorageSegment}.
 *
 *
 */
public interface IStorageQuery
{
	/**
	 * @return user readable summary of this query
	 */
	String getSummary();

	IStorageQuery addProduct(I_M_Product product);

	IStorageQuery addPartner(I_C_BPartner bpartner);

	IStorageQuery addWarehouse(I_M_Warehouse warehouse);

	/**
	 * Add another attribute filter, <b>if</b> the given <code>attribute</code> is relevant according to the respective storage engine implementation.
	 *
	 * @param attribute
	 * @param attributeValueType
	 * @param attributeValue
	 * @return
	 */
	IStorageQuery addAttribute(I_M_Attribute attribute, String attributeValueType, Object attributeValue);

	/**
	 * Add all attributes (that are allowed to be used in storage querying) from given attribute set.
	 *
	 * NOTE: depends on implementation, it could be that not all attributes will be added but only the considered ones.
	 *
	 * @param attributeSet
	 * @return this
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
	 *
	 * @param excludeAfterPickingLocator
	 * @return this
	 */
	IStorageQuery setExcludeAfterPickingLocator(boolean excludeAfterPickingLocator);
}
