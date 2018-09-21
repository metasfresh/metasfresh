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


import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.TerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.util.Check;

/**
 * Immutable {@link ITerminalKey} implementation which wraps an {@link I_C_BPartner_Location}.
 *
 * @author tsa
 *
 */
public class BPartnerLocationKey extends TerminalKey
{
	private final String id;
	private final String name;
	private final KeyNamePair value;
	private final I_C_BPartner_Location bpartnerLocation;
	private final int bpartnerLocationId;

	public BPartnerLocationKey(final ITerminalContext terminalContext, final I_C_BPartner_Location bpartnerLocation)
	{
		super(terminalContext);

		Check.assumeNotNull(bpartnerLocation, "bpartnerLocation not null");
		this.bpartnerLocation = bpartnerLocation;
		bpartnerLocationId = bpartnerLocation.getC_BPartner_Location_ID();

		id = I_C_BPartner_Location.Table_Name + "#" + bpartnerLocation.getC_BPartner_Location_ID();
		name = bpartnerLocation.getName();
		value = new KeyNamePair(bpartnerLocationId, name);
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
		return I_C_BPartner_Location.Table_Name;
	}

	@Override
	public KeyNamePair getValue()
	{
		return value;
	}

	public int getC_BPartner_Location_ID()
	{
		return bpartnerLocationId;
	}

	/** @return underlying {@link I_C_BPartner_Location}; never null */
	public I_C_BPartner_Location getC_BPartner_Location()
	{
		return bpartnerLocation;
	}
	
	/** @return underlying {@link I_C_BPartner_Location}; never null */
	public <T extends I_C_BPartner_Location> T getC_BPartner_Location(Class<T> modelClass)
	{
		return InterfaceWrapperHelper.create(bpartnerLocation, modelClass);
	}
}
