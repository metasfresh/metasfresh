package de.metas.adempiere.gui.search;

/*
 * #%L
 * de.metas.swat.base
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


public interface IInfoProductDetail
{
	/**
	 * Generic wrapper for refresh of detail tabs.
	 * We add all possible parameters here and use the ones we need in implementations.
	 * 
	 * @param M_Product_ID
	 * @param M_Warehouse_ID
	 * @param M_AttributeSetInstance_ID
	 * @param M_PriceList_Version_ID
	 */
	void refresh(int M_Product_ID,
			int M_Warehouse_ID,
			int M_AttributeSetInstance_ID,
			int M_PriceList_Version_ID);
}
