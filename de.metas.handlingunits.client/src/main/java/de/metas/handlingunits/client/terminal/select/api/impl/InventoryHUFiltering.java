package de.metas.handlingunits.client.terminal.select.api.impl;

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


import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Warehouse;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.client.terminal.inventory.model.IDummyPOSTableRow;
import de.metas.handlingunits.client.terminal.select.api.IPOSTableRow;
import de.metas.util.Services;

/**
 * Filter for warehouses that shall appear in POS now used in de.metas.handlingunits.client.terminal.warehouse.view.WarehouseHandlingUnitsSelectFrame (Handling Units (POS)) and filters for all
 * warehouses that appear in POS Profile. The filter is done by context.
 *
 * @author lc
 *
 */
public class InventoryHUFiltering extends AbstractFiltering
{

	@SuppressWarnings("unchecked")
	@Override
	public Class<? extends IPOSTableRow> getTableRowType()
	{
		return IDummyPOSTableRow.class;
	}

	@Override
	public List<I_M_Warehouse> retrieveWarehouses(final Properties ctx)
	{
		// Retrieve all warehouses.
		return Services.get(IWarehouseDAO.class).retrieveWarehousesForCtx(ctx);
	}

	@Override
	public List<IPOSTableRow> retrieveTableRows(final Properties ctx, final int warehouseId)
	{
		// This filtering doesn't require POS table rows.
		return Collections.emptyList();
	}

	@Override
	public Object getReferencedObject(final IPOSTableRow row)
	{
		// Referencing dummy object. Return null.
		return null;
	}

	@Override
	public void processRows(final Properties ctx, final Set<IPOSTableRow> rows, final Set<HuId> selectedHUs)
	{
		// Nothing to do on this level.
	}

	@Override
	public void closeRows(final Set<IPOSTableRow> rows)
	{
		throw new UnsupportedOperationException("not implemented");

	}

	@Override
	public List<I_C_BPartner> getBPartners(final List<IPOSTableRow> rows)
	{
		// We shouldn't use this for picking filtering.
		throw new UnsupportedOperationException("not implemented");
	}
}
