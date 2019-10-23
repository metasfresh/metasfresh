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


import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.util.lang.ObjectUtils;
import org.compiere.apps.search.IGridTabRowBuilder;

/**
 * Implementation which groups several {@link IGridTabRowBuilder} and behave like one {@link IGridTabRowBuilder}.
 * 
 * @author tsa
 * 
 */
public class CompositeGridTabRowBuilder implements IGridTabRowBuilder
{
	private static final transient Logger logger = LogManager.getLogger(CompositeGridTabRowBuilder.class);

	private final List<IGridTabRowBuilder> builders = new ArrayList<IGridTabRowBuilder>();

	public void addGridTabRowBuilder(final IGridTabRowBuilder builder)
	{
		if (builder == null)
		{
			return;
		}
		if (builders.contains(builder))
		{
			return;
		}

		builders.add(builder);
	}

	@Override
	public void apply(final Object model)
	{
		for (final IGridTabRowBuilder builder : builders)
		{
			if (!builder.isValid())
			{
				logger.debug("Skip builder because it's not valid: {}", builder);
				continue;
			}

			builder.apply(model);
			logger.debug("Applied {} to {}", new Object[] { builder, model });
		}
	}

	@Override
	public boolean isCreateNewRecord()
	{
		boolean createNewRecord = true;

		for (final IGridTabRowBuilder builder : builders)
		{
			if (!builder.isValid())
			{
				createNewRecord = false;

				continue;
			}

			if (!builder.isCreateNewRecord())
			{
				createNewRecord = false;
			}
		}

		return createNewRecord;
	}

	/**
	 * @return true if at least one builder is valid
	 */
	@Override
	public boolean isValid()
	{
		for (final IGridTabRowBuilder builder : builders)
		{
			if (builder.isValid())
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public void setSource(Object model)
	{
		for (final IGridTabRowBuilder builder : builders)
		{
			builder.setSource(model);
		}
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}
}
