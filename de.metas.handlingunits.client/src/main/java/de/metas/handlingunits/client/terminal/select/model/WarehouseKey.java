package de.metas.handlingunits.client.terminal.select.model;

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


import org.adempiere.util.Check;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.terminal.TerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;

public class WarehouseKey extends TerminalKey
{
	private final String id;
	private final String name;
	private final KeyNamePair value;
	private final I_M_Warehouse warehouse;

	public WarehouseKey(final ITerminalContext terminalContext, final I_M_Warehouse warehouse)
	{
		super(terminalContext);

		Check.assumeNotNull(warehouse, "warehouse not null");
		this.warehouse = warehouse;

		id = "M_Warehouse#" + warehouse.getM_Warehouse_ID();
		final int warehouseId = warehouse.getM_Warehouse_ID();
		name = warehouse.getName();
		value = new KeyNamePair(warehouseId, name);
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public Object getName()
	{
		return name;
	}

	@Override
	public String getTableName()
	{
		return I_M_Warehouse.Table_Name;
	}

	@Override
	public KeyNamePair getValue()
	{
		return value;
	}

	public final int getM_Warehouse_ID()
	{
		return warehouse.getM_Warehouse_ID();
	}

	public final I_M_Warehouse getM_Warehouse()
	{
		return warehouse;
	}
}
