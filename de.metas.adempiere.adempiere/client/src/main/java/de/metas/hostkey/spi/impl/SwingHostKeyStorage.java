package de.metas.hostkey.spi.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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
import org.compiere.util.Ini;

import de.metas.hostkey.spi.IHostKeyStorage;

/**
 * {@link IHostKeyStorage} implementations which stores the HostKey in {@link Ini}'s property file.
 * 
 * @author tsa
 * 
 */
public class SwingHostKeyStorage implements IHostKeyStorage
{
	private static final String PARAM_HostKey = "HostKey";

	public SwingHostKeyStorage()
	{
		Check.assume(Ini.isClient(), "Swing client is not started");
	}

	@Override
	public void setHostKey(String hostkey)
	{
		Ini.setProperty(PARAM_HostKey, hostkey);
		Ini.saveProperties();
	}

	@Override
	public String getHostKey()
	{
		return Ini.getProperty(PARAM_HostKey);
	}
}
