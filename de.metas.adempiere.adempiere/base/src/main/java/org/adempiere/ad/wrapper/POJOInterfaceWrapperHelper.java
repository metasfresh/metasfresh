package org.adempiere.ad.wrapper;

import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.persistence.IModelInternalAccessor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.PO;
import org.compiere.util.Evaluatee;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class POJOInterfaceWrapperHelper extends AbstractInterfaceWrapperHelper
{
	private static final Logger logger = LogManager.getLogger(POJOInterfaceWrapperHelper.class);

	@Override
	public boolean canHandled(final Object model)
	{
		return POJOWrapper.isHandled(model);
	}

	@Override
	public <T> T wrap(final Object model, final Class<T> modelClass, final boolean useOldValues)
	{
		if (useOldValues)
		{
			return POJOWrapper.create(model, modelClass, true);
		}
		else
		{
			// preserve "old values" flag
			return POJOWrapper.create(model, modelClass);
		}
	}

	@Override
	public void refresh(final Object model, final boolean discardChanges)
	{
		POJOWrapper.refresh(model, discardChanges, POJOWrapper.getTrxName(model));
	}

	@Override
	public void refresh(final Object model, final String trxName)
	{
		final boolean discardChanges = false;
		POJOWrapper.refresh(model, discardChanges, trxName);
	}

	@Override
	public boolean hasModelColumnName(final Object model, final String columnName)
	{
		final POJOWrapper wrapper = POJOWrapper.getWrapper(model);
		return wrapper.hasColumnName(columnName);
	}

	@Override
	public boolean setValue(final Object model, final String columnName, final Object value, final boolean throwExIfColumnNotFound)
	{
		final POJOWrapper wrapper = POJOWrapper.getWrapper(model);
		if (!wrapper.hasColumnName(columnName))
		{
			final AdempiereException ex = new AdempiereException("No columnName " + columnName + " found for " + model);
			if (throwExIfColumnNotFound)
			{
				throw ex;
			}
			else
			{
				logger.warn(ex.getLocalizedMessage(), ex);
				return false;
			}
		}

		wrapper.setValue(columnName, value);
		return true;
	}

	@Override
	public Properties getCtx(final Object model, final boolean useClientOrgFromModel)
	{
		return POJOWrapper.getCtx(model, useClientOrgFromModel);
	}

	@Override
	public String getTrxName(final Object model, final boolean ignoreIfNotHandled)
	{
		return POJOWrapper.getTrxName(model);
	}

	@Override
	public void setTrxName(final Object model, final String trxName, final boolean ignoreIfNotHandled)
	{
		POJOWrapper.setTrxName(model, trxName);
	}

	@Override
	public int getId(final Object model)
	{
		return POJOWrapper.getWrapper(model).getId();
	}

	@Override
	public String getModelTableNameOrNull(final Object model)
	{
		return POJOWrapper.getWrapper(model).getTableName();
	}

	@Override
	public boolean isNew(final Object model)
	{
		return POJOWrapper.isNew(model);
	}

	@Override
	public <T> T getValue(final Object model, final String columnName, final boolean throwExIfColumnNotFound, final boolean useOverrideColumnIfAvailable)
	{
		final POJOWrapper wrapper = POJOWrapper.getWrapper(model);
		if (useOverrideColumnIfAvailable)
		{
			final IModelInternalAccessor modelAccessor = wrapper.getModelInternalAccessor();
			final T value = getValueOverrideOrNull(modelAccessor, columnName);
			if (value != null)
			{
				return value;
			}
		}
		//
		if (!wrapper.hasColumnName(columnName))
		{
			if (throwExIfColumnNotFound)
			{
				throw new AdempiereException("No columnName " + columnName + " found for " + model);
			}
			else
			{
				return null;
			}
		}
		@SuppressWarnings("unchecked")
		final T value = (T)wrapper.getValuesMap().get(columnName);
		return value;
	}

	@Override
	public boolean isValueChanged(final Object model, final String columnName)
	{
		return POJOWrapper.isValueChanged(model, columnName);
	}

	@Override
	public boolean isValueChanged(final Object model, final Set<String> columnNames)
	{
		return POJOWrapper.isValueChanged(model, columnNames);
	}
	
	@Override
	public boolean isNull(final Object model, final String columnName)
	{
		return POJOWrapper.isNull(model, columnName);
	}

	@Override
	public <T> T getDynAttribute(final Object model, final String attributeName)
	{
		final T value = POJOWrapper.getDynAttribute(model, attributeName);
		return value;
	}

	@Override
	public Object setDynAttribute(final Object model, final String attributeName, final Object value)
	{
		return POJOWrapper.setDynAttribute(model, attributeName, value);
	}

	@Override
	public <T extends PO> T getPO(final Object model, final boolean strict)
	{
		throw new UnsupportedOperationException("Getting PO from '" + model + "' is not supported in JUnit testing mode");
	}
	
	@Override
	public Evaluatee getEvaluatee(final Object model)
	{
		return POJOWrapper.getWrapper(model).asEvaluatee();
	}
}
