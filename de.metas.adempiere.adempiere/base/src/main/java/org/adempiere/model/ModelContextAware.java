package org.adempiere.model;

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


import java.util.Properties;

import org.adempiere.util.lang.IContextAware;

/**
 * Note: use {@link InterfaceWrapperHelper#getContextAware(Object)} do get an instance.
 *
 */
/* package */final class ModelContextAware implements IContextAware
{
	private final Object model;

	public ModelContextAware(final Object model)
	{
		super();
		
		// we allow null values because we want that InterfaceWrapperHelper.getContextAware
		// ... to not return null for nulls
		//Check.assumeNotNull(model, "model not null");
		this.model = model;
	}

	@Override
	public Properties getCtx()
	{
		return InterfaceWrapperHelper.getCtx(model);
	}

	@Override
	public String getTrxName()
	{
		return InterfaceWrapperHelper.getTrxName(model);
	}

}
