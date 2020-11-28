package de.metas.handlingunits.client.terminal.select.model;

import org.compiere.model.I_M_Warehouse;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.terminal.TerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.util.Check;

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
