package de.metas.handlingunits.client.terminal.lutuconfig.model;

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


import java.math.BigDecimal;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.quantity.Quantity;
import de.metas.util.Check;

public class LUKey extends AbstractLUTUKey
{
	public LUKey(final ITerminalContext terminalContext, final I_M_HU_PI huPI, final I_M_HU_PI_Item huPIItemChildJoin)
	{
		super(terminalContext, huPI, huPIItemChildJoin);

		Check.assumeNotNull(huPIItemChildJoin, "huPIItemChildJoin not null");
	}

	@Override
	protected String createName()
	{
		return super.createName();
	}

	/**
	 * Gets Standard TUs/LU quantity.
	 *
	 * @return Standard TUs/LU quantity
	 */
	public final BigDecimal getQtyTUsPerLU()
	{
		if (isNoPI())
		{
			return BigDecimal.ZERO;
		}
		if (isVirtualPI())
		{
			return Quantity.QTY_INFINITE;
		}

		final I_M_HU_PI_Item piItem = getM_HU_PI_Item_ForChildJoin();
		final BigDecimal qtyTUsPerLU = piItem.getQty();
		return qtyTUsPerLU;
	}
}
