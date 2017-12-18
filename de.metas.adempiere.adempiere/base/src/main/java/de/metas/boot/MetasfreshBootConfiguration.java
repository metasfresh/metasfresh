package de.metas.boot;

import java.util.List;
import java.util.Set;

import org.adempiere.ad.housekeeping.spi.IStartupHouseKeepingTask;
import org.adempiere.ad.wrapper.IInterfaceWrapperHelper;
import org.adempiere.context.ContextProvider;
import org.adempiere.context.SwingContextProvider;
import org.adempiere.context.ThreadLocalContextProvider;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere.RunMode;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface MetasfreshBootConfiguration
{
	RunMode getRunMode();

	default List<IStartupHouseKeepingTask> getStartupHouseKeepingTasks()
	{
		return ImmutableList.of();
	}

	default ContextProvider createContextProvider()
	{
		final RunMode runMode = getRunMode();
		if (RunMode.SWING_CLIENT == runMode)
		{
			return new SwingContextProvider();
		}
		else if (RunMode.BACKEND == runMode)
		{
			return new ThreadLocalContextProvider();
		}
		else
		{
			throw new AdempiereException("Cannot determine " + ContextProvider.class + " for " + runMode);
		}
	}
	
	default List<IInterfaceWrapperHelper> getInterfaceWrapperHelpers()
	{
		return ImmutableList.of();
	}
	
	default Set<String> getTableNamesToIgnoreFromMigrationScriptsLogging()
	{
		return ImmutableSet.of();
	}
}
