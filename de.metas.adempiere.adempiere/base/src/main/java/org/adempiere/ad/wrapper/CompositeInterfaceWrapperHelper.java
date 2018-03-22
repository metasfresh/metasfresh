package org.adempiere.ad.wrapper;

import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import lombok.NonNull;

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
	private static final Logger logger = LogManager.getLogger(CompositeInterfaceWrapperHelper.class);

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

	private final IInterfaceWrapperHelper getHelperThatCanHandleOrNull(final Object model)
	{
		for (final IInterfaceWrapperHelper helper : helpers)
		{
			if (helper.canHandled(model))
			{
				return helper;
			}
		}

		return null;
	}

	private final IInterfaceWrapperHelper getHelperThatCanHandle(final Object model)
	{
		final IInterfaceWrapperHelper helper = getHelperThatCanHandleOrNull(model);
		if (helper == null)
		{
			throw new AdempiereException("No helper can support given model: " + model
					+ "\n Class: " + (model == null ? null : model.getClass())
					+ "\n Considered helpers: " + helpers);
		}

		return helper;
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

	@Override
	public Properties getCtx(final Object model, final boolean useClientOrgFromModel)
	{
		final IInterfaceWrapperHelper helper = getHelperThatCanHandleOrNull(model);
		if (helper == null)
		{
			final AdempiereException ex = new AdempiereException("Cannot get context from object: " + model + ". Returning global context.");
			logger.warn(ex.getLocalizedMessage(), ex);
			return Env.getCtx();
		}

		return helper.getCtx(model, useClientOrgFromModel);
	}

	@Override
	public String getTrxName(final Object model, final boolean ignoreIfNotHandled)
	{
		final IInterfaceWrapperHelper helper = getHelperThatCanHandleOrNull(model);
		if (helper == null)
		{
			if (!ignoreIfNotHandled)
			{
				final AdempiereException ex = new AdempiereException("Cannot get trxName from object: " + model + ". Returning null.");
				logger.warn(ex.getLocalizedMessage(), ex);
			}

			return ITrx.TRXNAME_None;
		}

		return helper.getTrxName(model, ignoreIfNotHandled);
	}

	@Override
	public void setTrxName(final Object model, final String trxName, final boolean ignoreIfNotHandled)
	{
		final IInterfaceWrapperHelper helper = getHelperThatCanHandleOrNull(model);
		if (helper == null)
		{
			if (ignoreIfNotHandled)
			{
				return;
			}

			throw new AdempiereException("Not supported model " + model + " (class:" + (model == null ? null : model.getClass()) + ")");
		}

		helper.setTrxName(model, trxName, ignoreIfNotHandled);
	}

	@Override
	public int getId(final Object model)
	{
		return getHelperThatCanHandle(model)
				.getId(model);
	}

	@Override
	public String getModelTableNameOrNull(final Object model)
	{
		if (model == null)
		{
			return null;
		}
		final IInterfaceWrapperHelper helper = getHelperThatCanHandleOrNull(model);
		if (helper == null)
		{
			return null;
		}
		return helper.getModelTableNameOrNull(model);
	}

	@Override
	public boolean isNew(final Object model)
	{
		Check.assumeNotNull(model, "model not null");
		return getHelperThatCanHandle(model)
				.isNew(model);
	}

	@Override
	public <T> T getValue(final Object model, final String columnName, final boolean throwExIfColumnNotFound, final boolean useOverrideColumnIfAvailable)
	{
		Check.assumeNotNull(model, "model is not null");
		Check.assumeNotNull(columnName, "columnName is not null");

		return getHelperThatCanHandle(model)
				.getValue(model, columnName, throwExIfColumnNotFound, useOverrideColumnIfAvailable);
	}

	@Override
	public boolean isValueChanged(final Object model, final String columnName)
	{
		return getHelperThatCanHandle(model)
				.isValueChanged(model, columnName);
	}

	@Override
	public boolean isValueChanged(final Object model, final Set<String> columnNames)
	{
		return getHelperThatCanHandle(model)
				.isValueChanged(model, columnNames);
	}

	@Override
	public boolean isNull(final Object model, final String columnName)
	{
		if (model == null)
		{
			return true;
		}

		return getHelperThatCanHandle(model)
				.isNull(model, columnName);
	}

	@Override
	public <T> T getDynAttribute(final Object model, final String attributeName)
	{
		return getHelperThatCanHandle(model)
				.getDynAttribute(model, attributeName);
	}

	@Override
	public Object setDynAttribute(final Object model, final String attributeName, final Object value)
	{
		return getHelperThatCanHandle(model)
				.setDynAttribute(model, attributeName, value);
	}

	@Override
	public <T extends PO> T getPO(final Object model, final boolean strict)
	{
		if (model == null)
		{
			return null;
		}

		// Short-circuit: model is already a PO instance
		if (model instanceof PO)
		{
			@SuppressWarnings("unchecked")
			final T po = (T)model;
			return po;
		}

		return getHelperThatCanHandle(model)
				.getPO(model, strict);
	}

	@Override
	public Evaluatee getEvaluatee(final Object model)
	{
		if (model == null)
		{
			return null;
		}
		else if (model instanceof Evaluatee)
		{
			final Evaluatee evaluatee = (Evaluatee)model;
			return evaluatee;
		}

		return getHelperThatCanHandle(model)
				.getEvaluatee(model);
	}

	@Override
	public boolean isCopy(@NonNull final Object model)
	{
		return getHelperThatCanHandle(model).isCopy(model);
	}
}
