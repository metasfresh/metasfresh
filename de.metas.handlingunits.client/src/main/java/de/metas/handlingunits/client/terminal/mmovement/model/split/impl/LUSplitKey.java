package de.metas.handlingunits.client.terminal.mmovement.model.split.impl;

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

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.client.terminal.lutuconfig.model.LUKey;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;

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
