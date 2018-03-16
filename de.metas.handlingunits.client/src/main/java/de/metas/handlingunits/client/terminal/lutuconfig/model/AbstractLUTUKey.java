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


import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;

public class AbstractLUTUKey extends AbstractLUTUCUKey
{
	private final I_M_HU_PI huPI;
	private final I_M_HU_PI_Item huPIItemChildJoin;
	private String _id;
	private final KeyNamePair value;
	private final boolean isNoPI;
	private final boolean isVirtualPI;

	public AbstractLUTUKey(final ITerminalContext terminalContext, final I_M_HU_PI huPI)
	{
		this(terminalContext, huPI, null); // huPIItemChildJoin=null
	}

	public AbstractLUTUKey(final ITerminalContext terminalContext, final I_M_HU_PI huPI, final I_M_HU_PI_Item huPIItemChildJoin)
	{
		super(terminalContext);

		Check.assumeNotNull(huPI, "huPI not null");
		this.huPI = huPI;
		this.huPIItemChildJoin = huPIItemChildJoin;

		final int huPIId = huPI.getM_HU_PI_ID();

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		isNoPI = huPIId == handlingUnitsDAO.getPackingItemTemplate_HU_PI_ID();
		isVirtualPI = huPIId == handlingUnitsDAO.getVirtual_HU_PI_ID();

		value = new KeyNamePair(huPIId, huPI.getName());
	}

	@Override
	protected String createName()
	{
		final I_M_HU_PI huPI = getM_HU_PI();
		return huPI.getName();
	}

	@Override
	public final String getId()
	{
		if (_id == null)
		{
			_id = buildId();
			Check.assumeNotNull(_id, "_id not null");
		}
		return _id;
	}

	protected String buildId()
	{
		final int huPIId = getM_HU_PI().getM_HU_PI_ID();

		// final int huPIItemChildJoinId = huPIItemChildJoin == null ? -1 : huPIItemChildJoin.getM_HU_PI_Item_ID();
		final String id = getClass().getName() + "#M_HU_PI_ID=" + huPIId;
		// 07377
		// do not include the child id in the LU TU id.
		// + "#M_HU_PI_Item_ID=" + huPIItemChildJoinId
		;

		return id;
	}

	@Override
	public final KeyNamePair getValue()
	{
		return value;
	}

	@Override
	public final I_M_HU_PI getM_HU_PI()
	{
		return huPI;
	}

	public final I_M_HU_PI_Item getM_HU_PI_Item_ForChildJoin()
	{
		return huPIItemChildJoin;
	}

	public final boolean isNoPI()
	{
		return isNoPI;
	}

	public final boolean isVirtualPI()
	{
		return isVirtualPI;
	}
}
