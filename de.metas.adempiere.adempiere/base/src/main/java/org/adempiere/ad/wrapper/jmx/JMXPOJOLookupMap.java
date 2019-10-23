package org.adempiere.ad.wrapper.jmx;

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


import java.lang.ref.WeakReference;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.exceptions.AdempiereException;

import de.metas.util.Check;

public class JMXPOJOLookupMap implements JMXPOJOLookupMapMBean
{
	private final WeakReference<POJOLookupMap> databaseRef;
	private final String jmxName;

	public JMXPOJOLookupMap(final POJOLookupMap database)
	{
		super();
		Check.assumeNotNull(database, "database not null");
		this.databaseRef = new WeakReference<POJOLookupMap>(database);

		this.jmxName = POJOLookupMap.class.getName() + ":type=" + database.getDatabaseName();
	}

	private POJOLookupMap getDatabase()
	{
		final POJOLookupMap database = databaseRef.get();
		if (database == null)
		{
			throw new AdempiereException("Database expired");
		}

		return database;
	}

	@Override
	public void dump()
	{
		final POJOLookupMap database = getDatabase();
		database.dumpStatus();
	}

	public String getJMXName()
	{
		return jmxName;
	}
}
