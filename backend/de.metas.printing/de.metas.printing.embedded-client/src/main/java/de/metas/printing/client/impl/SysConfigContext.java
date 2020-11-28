package de.metas.printing.client.impl;

/*
 * #%L
 * de.metas.printing.embedded-client
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


import org.adempiere.service.ISysConfigBL;

import de.metas.printing.Printing_Constants;
import de.metas.printing.client.IContext;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * {@link IContext} implementation which is forwarding the property requests to {@link ISysConfigBL#getValue(String)}.
 * 
 * All property names will be prefixed with {@link Printing_Constants#SYSCONFIG_PrintingClientApplet_PREFIX}.
 * 
 * @author tsa
 * 
 */
public class SysConfigContext implements IContext
{

	@Override
	public String getProperty(String name)
	{
		if (name == null || Check.isEmpty(name, true))
		{
			return null;
		}

		String nameFQ = name.trim();
		if (!nameFQ.startsWith(Printing_Constants.SYSCONFIG_PrintingClientApplet_PREFIX))
		{
			nameFQ = Printing_Constants.SYSCONFIG_PrintingClientApplet_PREFIX + nameFQ;
		}

		final String value = Services.get(ISysConfigBL.class).getValue(nameFQ);
		return value;
	}

}
