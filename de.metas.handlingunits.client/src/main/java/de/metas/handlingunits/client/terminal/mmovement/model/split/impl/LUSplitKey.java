package de.metas.handlingunits.client.terminal.mmovement.model.split.impl;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.client.terminal.lutuconfig.model.LUKey;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.util.Check;

/**
 * Load Unit Key
 *
 * @author tsa
 * @author al
 *
 */
public class LUSplitKey extends LUKey
{
	private final String unitType;

	public LUSplitKey(
			final ITerminalContext terminalContext,
			final I_M_HU_PI_Item luPIItem,
			final I_M_HU_PI luPI,
			final String unitType)
	{
		super(terminalContext, luPI, luPIItem);

		Check.assumeNotNull(luPIItem, "huPIItem not null");
		Check.assumeNotNull(luPI, "luPI not null");

		Check.assumeNotNull(unitType, "unitType not null");
		this.unitType = unitType;
	}

	public String getUnitType()
	{
		return unitType;
	}
}
