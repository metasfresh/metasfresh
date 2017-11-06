/**
 *
 */
package de.metas.handlingunits.client.terminal.select.api;

/*
 * #%L
 * de.metas.handlingunits.client
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
import java.util.Properties;
import java.util.Set;
import java.util.function.Predicate;

import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Warehouse;

import de.metas.handlingunits.model.I_M_HU;

/**
 * @author cg
 *
 */
public interface IPOSFiltering
{
	<T extends IPOSTableRow> Class<T> getTableRowType();

	/**
	 * Gets available warehouses
	 *
	 * @param ctx
	 * @return
	 */
	List<I_M_Warehouse> retrieveWarehouses(Properties ctx);

	/**
	 * Retrieve Receipt schedules filtered by warehouse
	 *
	 * @param warehouseId
	 * @return
	 */
	List<IPOSTableRow> retrieveTableRows(Properties ctx, int warehouseId);

	/**
	 * Gets partners from source table.
	 *
	 * @param schedules
	 * @return
	 */
	List<I_C_BPartner> getBPartners(List<IPOSTableRow> rows);

	/**
	 * Gets orders from source table.
	 *
	 * @param schedules
	 * @return
	 */
	List<I_C_Order> getOrders(List<IPOSTableRow> rows);

	List<IPOSTableRow> filter(List<IPOSTableRow> rows, Predicate<IPOSTableRow> filter);

	Object getReferencedObject(IPOSTableRow row);

	void processRows(Properties ctx, Set<IPOSTableRow> rows, Set<I_M_HU> selectedHUs);

	void closeRows(Set<IPOSTableRow> rows);

}
