package org.adempiere.ad.wrapper;

import java.util.concurrent.CopyOnWriteArrayList;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Allows to combine a number of different handlers and will delegate the actual works to the particular handler for the particular type of <code>model</code>.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class CompositeInterfaceWrapperHelper implements IInterfaceWrapperHelper
{
	private final CopyOnWriteArrayList<IInterfaceWrapperHelper> helpers = new CopyOnWriteArrayList<>();

	public CompositeInterfaceWrapperHelper()
	{
		super();
	}

	public CompositeInterfaceWrapperHelper addFactory(final IInterfaceWrapperHelper factory)
	{
		Check.assumeNotNull(factory, "factory not null");
		helpers.addIfAbsent(factory);
		return this;
	}

	/**
	 * Always returns <code>true</code>
	 */
	@Override
	public boolean canHandled(final Object model)
	{
		return true;
	}

	private final IInterfaceWrapperHelper getHelperThatCanHandle(final Object model)
	{
		for (final IInterfaceWrapperHelper helper : helpers)
		{
			if (helper.canHandled(model))
			{
				return helper;
			}
		}

		throw new AdempiereException("No helper can support given model: " + model
				+ "\n Class: " + (model == null ? null : model.getClass())
				+ "\n Considered helpers: " + helpers);

	}

	@Override
	public <T> T wrap(final Object model, final Class<T> modelClass, final boolean useOldValues)
	{
		return getHelperThatCanHandle(model)
				.wrap(model, modelClass, useOldValues);
	}

	@Override
	public void refresh(final Object model, final boolean discardChanges)
	{
		getHelperThatCanHandle(model)
				.refresh(model, discardChanges);
	}

	@Override
	public void refresh(final Object model, final String trxName)
	{
		getHelperThatCanHandle(model)
				.refresh(model, trxName);
	}

	@Override
	public boolean hasModelColumnName(final Object model, final String columnName)
	{
		return getHelperThatCanHandle(model)
				.hasModelColumnName(model, columnName);
	}

	@Override
	public boolean setValue(final Object model, final String columnName, final Object value, final boolean throwExIfColumnNotFound)
	{
		return getHelperThatCanHandle(model)
				.setValue(model, columnName, value, throwExIfColumnNotFound);
	}
}
