package org.compiere.apps.search.impl;

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
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.compiere.apps.search.IGridTabRowBuilder;
import org.compiere.apps.search.IInfoWindowGridRowBuilders;
import org.compiere.apps.search.NullGridTabRowBuilder;
import org.compiere.util.Env;

/**
 * Default implementation of {@link IInfoWindowGridRowBuilders}
 * 
 * @author tsa
 * 
 */
public class InfoWindowGridRowBuilders implements IInfoWindowGridRowBuilders
{
	private final Map<Integer, CompositeGridTabRowBuilder> builders = new HashMap<Integer, CompositeGridTabRowBuilder>();

	public InfoWindowGridRowBuilders()
	{

	}

	@Override
	public void addGridTabRowBuilder(final int recordId, final IGridTabRowBuilder builder)
	{
		CompositeGridTabRowBuilder recordBuilders = builders.get(recordId);
		if (recordBuilders == null)
		{
			recordBuilders = new CompositeGridTabRowBuilder();
			builders.put(recordId, recordBuilders);
		}

		recordBuilders.addGridTabRowBuilder(builder);
	}

	@Override
	public Set<Integer> getRecordIds()
	{
		final Set<Integer> recordIds = new HashSet<Integer>();
		for (final Map.Entry<Integer, CompositeGridTabRowBuilder> e : builders.entrySet())
		{
			final CompositeGridTabRowBuilder builder = e.getValue();
			if (!builder.isValid())
			{
				continue;
			}
			
			if (!builder.isCreateNewRecord())
			{
				continue;
			}

			final Integer recordId = e.getKey();
			recordIds.add(recordId);

		}
		return recordIds;
	}

	@Override
	public IGridTabRowBuilder getGridTabRowBuilder(final int recordId)
	{
		final CompositeGridTabRowBuilder recordBuilders = builders.get(recordId);
		if (recordBuilders == null)
		{
			return NullGridTabRowBuilder.instance;
		}

		return recordBuilders;
	}

	private static final String createContextName(final int windowNo)
	{
		final String ctxName = windowNo + "|" + InfoWindowGridRowBuilders.class.getName();
		return ctxName;
	}

	public void saveToContext(final Properties ctx, final int windowNo)
	{
		final String ctxName = createContextName(windowNo);
		Env.put(ctx, ctxName, this);
	}

	/**
	 * Gets the builders from context and then it clears the context
	 * 
	 * @param ctx
	 * @param windowNo
	 * @return builders or null
	 */
	public static IInfoWindowGridRowBuilders getFromContextOrNull(final Properties ctx, final int windowNo)
	{
		final String ctxName = createContextName(windowNo);
		final IInfoWindowGridRowBuilders builders = Env.getAndRemove(ctx, ctxName);
		return builders;
	}

	@Override
	public String toString()
	{
		return String.format("InfoWindowGridRowBuilders [builders=%s]", builders);
	}
}
