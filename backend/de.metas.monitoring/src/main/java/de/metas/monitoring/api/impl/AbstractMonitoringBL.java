package de.metas.monitoring.api.impl;

/*
 * #%L
 * de.metas.monitoring
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

import de.metas.monitoring.api.IMeter;
import de.metas.monitoring.api.IMonitoringBL;
import de.metas.util.Check;

public abstract class AbstractMonitoringBL implements IMonitoringBL
{
	/**
	 * NOTE: don't access this field on methods which are not synchronized/thread safe.
	 */
	private final Map<String, IMeter> names2Meters = new HashMap<String, IMeter>();

	@Override
	public final synchronized IMeter createOrGet(final String moduleName, final String meterName)
	{
		Check.errorIf(Check.isEmpty(moduleName), "Param 'moduleName' may not be empty");
		Check.errorIf(Check.isEmpty(meterName), "Param 'meterName' may not be empty");

		final String jmxName = mkJmxName(moduleName, meterName);
		if (names2Meters.containsKey(jmxName))
		{
			return names2Meters.get(jmxName);
		}

		final Meter meter = new Meter();
		registerJMX(jmxName, meter);
		names2Meters.put(jmxName, meter);

		return meter;
	}

	private final String mkJmxName(final String moduleName, final String meterName)
	{
		final String jmxName = moduleName + ":type=" + meterName;
		return jmxName;
	}

	protected abstract void registerJMX(final String jmxName, final Meter meter);
}
