package org.adempiere.ad.wrapper;

import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.persistence.IModelInternalAccessor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.POWrapper;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * This handler is a wrapper/delegator for {@link POWrapper}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class POInterfaceWrapperHelper extends AbstractInterfaceWrapperHelper
{
	private static final Logger logger = LogManager.getLogger(POInterfaceWrapperHelper.class);

	@Override
	public boolean canHandled(final Object model)
	{
		return POWrapper.isHandled(model);
	}

	@Override
	public <T> T wrap(final Object model, final Class<T> cl, final boolean useOldValues)
	{
		if (useOldValues)
		{
			return POWrapper.create(model, cl, true);
		}
		else
		{
			// preserve "old values" flag
			return POWrapper.create(model, cl);
		}
	}

	@Override
	public void refresh(final Object model, final boolean discardChanges)
	{
		POWrapper.refresh(model);
	}

	@Override
	public void refresh(final Object model, final String trxName)
	{
		POWrapper.refresh(model, trxName);
	}

	@Override
	public boolean hasModelColumnName(final Object model, final String columnName)
	{
		return POWrapper.hasModelColumnName(model, columnName);
	}

	@Override
	public boolean setValue(final Object model, final String columnName, final Object value, final boolean throwExIfColumnNotFound)
	{
		final PO po = POWrapper.getStrictPO(model);
		final int idx = po.get_ColumnIndex(columnName);
		if (idx < 0)
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

		po.set_ValueOfColumn(columnName, value);
		return true;
	}

	@Override
	public Properties getCtx(final Object model, final boolean useClientOrgFromModel)
	{
		return POWrapper.getCtx(model, useClientOrgFromModel);
	}

	@Override
	public String getTrxName(final Object model, final boolean ignoreIfNotHandled)
	{
		return POWrapper.getTrxName(model);
	}

	@Override
	public void setTrxName(final Object model, final String trxName, final boolean ignoreIfNotHandled)
	{
		POWrapper.setTrxName(model, trxName);
	}

	@Override
	public int getId(final Object model)
	{
		final PO po = POWrapper.getStrictPO(model);
		if (po == null)
		{
			return -1;
		}

		final String[] keyColumns = po.get_KeyColumns();
		if (keyColumns == null || keyColumns.length != 1)
		{
			return -1;
		}

		return po.get_ID();
	}

	@Override
	public String getModelTableNameOrNull(final Object model)
	{
		return POWrapper.getStrictPO(model).get_TableName();
	}

	@Override
	public boolean isNew(final Object model)
	{
		return POWrapper.isNew(model);
	}

	@Override
	public <T> T getValue(final Object model, final String columnName, final boolean throwExIfColumnNotFound, final boolean useOverrideColumnIfAvailable)
	{
		if (useOverrideColumnIfAvailable)
		{
			final IModelInternalAccessor modelAccessor = POWrapper.getModelInternalAccessor(model);
			final T value = getValueOverrideOrNull(modelAccessor, columnName);
			if (value != null)
			{
				return value;
			}
		}
		//
		final PO po = POWrapper.getStrictPO(model);
		final int idxColumnName = po.get_ColumnIndex(columnName);
		if (idxColumnName < 0)
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
		final T value = (T)po.get_Value(idxColumnName);
		return value;
	}

	@Override
	public boolean isValueChanged(final Object model, final String columnName)
	{
		return POWrapper.isValueChanged(model, columnName);
	}

	@Override
	public boolean isValueChanged(final Object model, final Set<String> columnNames)
	{
		return POWrapper.isValueChanged(model, columnNames);
	}

	@Override
	public boolean isNull(final Object model, final String columnName)
	{
		return POWrapper.isNull(model, columnName);
	}

	@Override
	public <T> T getDynAttribute(final Object model, final String attributeName)
	{
		final T value = POWrapper.getDynAttribute(model, attributeName);
		return value;
	}

	@Override
	public Object setDynAttribute(final Object model, final String attributeName, final Object value)
	{
		return POWrapper.setDynAttribute(model, attributeName, value);
	}

	@Override
	public <T extends PO> T getPO(final Object model, final boolean strict)
	{
		// always strict, else other wrapper helpers will handle it!
		return POWrapper.getStrictPO(model);
	}

	@Override
	public Evaluatee getEvaluatee(final Object model)
	{
		return POWrapper.getStrictPO(model);
	}

	@Override
	public boolean isCopy(final Object model)
	{
		return getDynAttribute(model, PO.DYNATTR_CopyRecordSupport_OldValue) != null;
	}
}
