package org.adempiere.ad.wrapper;

import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.persistence.IModelInternalAccessor;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.GridTabWrapper;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.PO;
import org.compiere.util.Env;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class GridTabInterfaceWrapperHelper extends AbstractInterfaceWrapperHelper
{
	private static final Logger logger = LogManager.getLogger(GridTabInterfaceWrapperHelper.class);

	@Override
	public boolean canHandled(final Object model)
	{
		return GridTabWrapper.isHandled(model);
	}

	@Override
	@SuppressWarnings("deprecation")
	public <T> T wrap(final Object model, final Class<T> cl, final boolean useOldValues)
	{
		if (useOldValues)
		{
			return GridTabWrapper.create(model, cl, true);
		}
		else
		{
			// preserve "old values" flag
			return GridTabWrapper.create(model, cl);
		}

	}

	@Override
	public void refresh(final Object model, final boolean discardChanges)
	{
		GridTabWrapper.refresh(model);
	}

	@Override
	public void refresh(final Object model, final String trxName)
	{
		GridTabWrapper.refresh(model);
	}

	@Override
	public boolean hasModelColumnName(final Object model, final String columnName)
	{
		return GridTabWrapper.hasColumnName(model, columnName);
	}

	@Override
	public boolean setValue(final Object model, final String columnName, final Object value, final boolean throwExIfColumnNotFound)
	{
		final GridTab gridTab = GridTabWrapper.getGridTab(model);
		final GridField gridField = gridTab.getField(columnName);
		if (gridField == null)
		{
			final AdempiereException ex = new AdempiereException("No field with ColumnName=" + columnName + " found in " + gridTab + " for " + model);
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

		gridTab.setValue(gridField, value);
		return true;
	}

	@Override
	public Properties getCtx(final Object model, final boolean useClientOrgFromModel)
	{
		return Env.getCtx();
	}

	@Override
	public String getTrxName(final Object model, final boolean ignoreIfNotHandled)
	{
		return ITrx.TRXNAME_None;
	}

	@Override
	public void setTrxName(final Object model, final String trxName, final boolean ignoreIfNotHandled)
	{
		// nothing
	}

	@Override
	public int getId(final Object model)
	{
		return GridTabWrapper.getId(model);
	}

	@Override
	public String getModelTableNameOrNull(final Object model)
	{
		return GridTabWrapper.getGridTab(model).getTableName();
	}

	@Override
	public boolean isNew(final Object model)
	{
		return GridTabWrapper.isNew(model);
	}

	@Override
	public <T> T getValue(final Object model, final String columnName, final boolean throwExIfColumnNotFound, final boolean useOverrideColumnIfAvailable)
	{
		final GridTab gridTab = GridTabWrapper.getGridTab(model);
		if (useOverrideColumnIfAvailable)
		{
			final IModelInternalAccessor modelAccessor = GridTabWrapper.getModelInternalAccessor(model);
			final T value = getValueOverrideOrNull(modelAccessor, columnName);
			if (value != null)
			{
				return value;
			}
		}
		//
		final GridField gridField = gridTab.getField(columnName);
		if (gridField == null)
		{
			if (throwExIfColumnNotFound)
			{
				throw new AdempiereException("No field with ColumnName=" + columnName + " found in " + gridTab + " for " + model);
			}
			else
			{
				return null;
			}
		}

		@SuppressWarnings("unchecked")
		final T value = (T)gridField.getValue();
		return value;
	}

	@Override
	public boolean isValueChanged(final Object model, final String columnName)
	{
		final GridTab gridTab = GridTabWrapper.getGridTab(model);
		if (gridTab == null)
		{
			return false;
		}

		final GridField field = gridTab.getField(columnName);
		if (field == null)
		{
			return false;
		}

		return field.isValueChanged();
	}

	@Override
	public boolean isValueChanged(final Object model, final Set<String> columnNames)
	{
		if (columnNames == null || columnNames.isEmpty())
		{
			return false;
		}

		final GridTab gridTab = GridTabWrapper.getGridTab(model);
		if (gridTab == null)
		{
			return false;
		}

		for (final String columnName : columnNames)
		{
			final GridField field = gridTab.getField(columnName);
			if (field == null)
			{
				continue;
			}

			if (field.isValueChanged())
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isNull(final Object model, final String columnName)
	{
		return GridTabWrapper.isNull(model, columnName);
	}

	@Override
	public <T> T getDynAttribute(final Object model, final String attributeName)
	{
		final T value = GridTabWrapper.getWrapper(model).getDynAttribute(attributeName);
		return value;
	}

	@Override
	public Object setDynAttribute(final Object model, final String attributeName, final Object value)
	{
		return GridTabWrapper.getWrapper(model).setDynAttribute(attributeName, value);
	}

	@Override
	public <T extends PO> T getPO(final Object model, final boolean strict)
	{
		if (strict)
		{
			return null;
		}

		if (model instanceof GridTab)
		{
			final GridTab gridTab = (GridTab)model;
			return GridTabWrapper.getPO(Env.getCtx(), gridTab);
		}

		final GridTabWrapper wrapper = GridTabWrapper.getWrapper(model);
		if (wrapper == null)
		{
			throw new AdempiereException("Cannot extract " + GridTabWrapper.class + " from " + model);
		}

		return wrapper.getPO();
	}

	@Override
	public Evaluatee getEvaluatee(final Object model)
	{
		return GridTabWrapper.getGridTab(model);
	}

	@Override
	public boolean isCopy(final Object model)
	{
		return GridTabWrapper.getGridTab(model).getTableModel().isRecordCopyingMode();
	}
}
