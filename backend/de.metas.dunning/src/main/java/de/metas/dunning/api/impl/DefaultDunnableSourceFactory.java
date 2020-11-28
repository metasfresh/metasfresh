package de.metas.dunning.api.impl;

/*
 * #%L
 * de.metas.dunning
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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.metas.dunning.api.IDunnableSourceFactory;
import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.exception.DunningException;
import de.metas.dunning.spi.IDunnableSource;

public class DefaultDunnableSourceFactory implements IDunnableSourceFactory
{
	private final Map<Class<? extends IDunnableSource>, IDunnableSource> dunnableSources = new HashMap<Class<? extends IDunnableSource>, IDunnableSource>();

	@Override
	public List<IDunnableSource> getSources(IDunningContext context)
	{
		final List<IDunnableSource> result = new ArrayList<IDunnableSource>();
		result.addAll(dunnableSources.values());
		return result;
	}

	private IDunnableSource createSource(Class<? extends IDunnableSource> sourceClass)
	{
		final IDunnableSource source;
		try
		{
			source = sourceClass.newInstance();
			return source;
		}
		catch (Exception e)
		{
			throw new DunningException("Cannot create dunning source for " + sourceClass, e);
		}
	}

	@Override
	public void registerSource(Class<? extends IDunnableSource> clazz)
	{
		if (dunnableSources.containsKey(clazz))
		{
			return;
		}

		final IDunnableSource source = createSource(clazz);
		dunnableSources.put(clazz, source);
	}

	@Override
	public String toString()
	{
		return "DefaultDunnableSourceFactory [dunnableSources=" + dunnableSources + "]";
	}
}
