/**
 *
 */
package org.adempiere.model.tree.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.HashMap;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.tree.IPOTreeSupportFactory;
import org.adempiere.model.tree.spi.IPOTreeSupport;
import org.adempiere.model.tree.spi.impl.DefaultPOTreeSupport;

import de.metas.util.Check;

/**
 * @author tsa
 *
 */
public class POTreeSupportFactory implements IPOTreeSupportFactory
{
	private final Map<String, Class<? extends IPOTreeSupport>> map = new HashMap<String, Class<? extends IPOTreeSupport>>();

	@Override
	public IPOTreeSupport get(final String tableName)
	{
		// NOTE: we need to create a new instance each time because IPOTreeSupport implementations are stateful
		
		final Class<? extends IPOTreeSupport> cl = map.get(tableName);

		final IPOTreeSupport result;

		if (cl == null)
		{
			result = new DefaultPOTreeSupport();
		}
		else
		{
			try
			{
				result = cl.newInstance();
			}
			catch (final Exception e)
			{
				throw new AdempiereException(e);
			}
		}
		result.setTableName(tableName);
		return result;
	}

	@Override
	public void register(final String tableName, final Class<? extends IPOTreeSupport> clazz)
	{
		// do checks
		Check.assumeNotEmpty(tableName, "Param 'tableName is not empty");
		Check.assumeNotNull(clazz, "Param 'clazz' is not null");
		try
		{
			clazz.getConstructor();
		}
		catch (NoSuchMethodException e)
		{
			Check.assume(false, "Param 'clazz' = {} has a default constructor", clazz);
		}
		
		// register
		map.put(tableName, clazz);
	}
}
