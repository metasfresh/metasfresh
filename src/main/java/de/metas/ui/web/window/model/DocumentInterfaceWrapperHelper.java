package de.metas.ui.web.window.model;

import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.AbstractInterfaceWrapperHelper;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/*
 * #%L
 * metasfresh-webui-api
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

public class DocumentInterfaceWrapperHelper extends AbstractInterfaceWrapperHelper
{
	private static final Logger logger = LogManager.getLogger(DocumentInterfaceWrapperHelper.class);

	@Override
	public boolean canHandled(final Object model)
	{
		return DocumentInterfaceWrapper.isHandled(model);
	}

	@Override
	public <T> T wrap(final Object model, final Class<T> modelClass, final boolean useOldValues)
	{
		if (useOldValues)
		{
			return DocumentInterfaceWrapper.wrapUsingOldValues(model, modelClass);
		}
		else
		{
			// preserve "old values" flag
			return DocumentInterfaceWrapper.wrap(model, modelClass);
		}

	}

	@Override
	public void refresh(final Object model, final boolean discardChanges)
	{
		DocumentInterfaceWrapper.refresh(model);
	}

	@Override
	public void refresh(final Object model, final String trxName)
	{
		DocumentInterfaceWrapper.refresh(model);
	}

	@Override
	public boolean hasModelColumnName(final Object model, final String columnName)
	{
		return DocumentInterfaceWrapper.hasColumnName(model, columnName);
	}

	@Override
	public boolean setValue(final Object model, final String columnName, final Object value, final boolean throwExIfColumnNotFound)
	{
		final Document document = DocumentInterfaceWrapper.getDocument(model);
		return DocumentInterfaceWrapper.setValue(document, columnName, value, throwExIfColumnNotFound);
	}

	@Override
	public Properties getCtx(final Object model, final boolean useClientOrgFromModel)
	{
		final Document document = DocumentInterfaceWrapper.getDocument(model);
		if (document != null)
		{
			return document.getCtx();
		}

		// Notify developer that (s)he is using wrong models
		if (Services.get(IDeveloperModeBL.class).isEnabled())
		{
			final AdempiereException e = new AdempiereException("Cannot get context from model " + model + " because is not supported. Returning global context.");
			logger.warn(e.getLocalizedMessage(), e);
		}

		return Env.getCtx(); // fallback to global context
	}

	@Override
	public String getTrxName(final Object model, final boolean ignoreIfNotHandled)
	{
		return ITrx.TRXNAME_None;
	}

	@Override
	public int getId(final Object model)
	{
		return DocumentInterfaceWrapper.getId(model);
	}

	@Override
	public String getModelTableNameOrNull(final Object model)
	{
		final Document document = DocumentInterfaceWrapper.getDocument(model);
		return document.getEntityDescriptor().getTableNameOrNull();
	}

	@Override
	public boolean isNew(final Object model)
	{
		return DocumentInterfaceWrapper.isNew(model);
	}

	@Override
	public <T> T getValue(final Object model, final String columnName, final boolean throwExIfColumnNotFound, final boolean useOverrideColumnIfAvailable)
	{
		//
		// Get <columnName>_Override's value, if any
		final DocumentInterfaceWrapper wrapper = DocumentInterfaceWrapper.getWrapper(model);
		if (useOverrideColumnIfAvailable)
		{
			final T value = getValueOverrideOrNull(wrapper, columnName);
			if (value != null)
			{
				return value;
			}
		}

		//
		// Get columnName's value
		if (!wrapper.hasColumnName(columnName))
		{
			if (throwExIfColumnNotFound)
			{
				throw new AdempiereException("No field with ColumnName=" + columnName + " found in " + wrapper + " for " + model);
			}
			else
			{
				return null;
			}
		}

		@SuppressWarnings("unchecked")
		final T value = (T)wrapper.getValue(columnName, Object.class);
		return value;
	}

	@Override
	public boolean isValueChanged(final Object model, final String columnName)
	{
		final Document document = DocumentInterfaceWrapper.getDocument(model);
		if (document == null)
		{
			return false;
		}

		final IDocumentFieldView field = document.getFieldViewOrNull(columnName);
		if (field == null)
		{
			return false;
		}

		return field.hasChangesToSave();
	}

	@Override
	public boolean isValueChanged(final Object model, final Set<String> columnNames)
	{
		if (columnNames == null || columnNames.isEmpty())
		{
			return false;
		}

		final Document document = DocumentInterfaceWrapper.getDocument(model);
		if (document == null)
		{
			return false;
		}

		for (final String columnName : columnNames)
		{
			final IDocumentFieldView field = document.getFieldViewOrNull(columnName);
			if (field == null)
			{
				continue;
			}

			if (field.hasChangesToSave())
			{
				return true;
			}
		}

		return false;
	}
	
	@Override
	public boolean isNull(final Object model, final String columnName)
	{
		final Document document = DocumentInterfaceWrapper.getDocument(model);
		final IDocumentFieldView field = document.getFieldViewOrNull(columnName);
		if(field == null)
		{
			return true;
		}
		
		final Object value = field.getValue();
		return value == null;
	}

	@Override
	public <T> T getDynAttribute(final Object model, final String attributeName)
	{
		return DocumentInterfaceWrapper.getDocument(model).getDynAttribute(attributeName);
	}

	@Override
	public Object setDynAttribute(final Object model, final String attributeName, final Object value)
	{
		return DocumentInterfaceWrapper.getDocument(model).setDynAttribute(attributeName, value);
	}

	@Override
	public <T extends PO> T getPO(final Object model, final boolean strict)
	{
		if (strict)
		{
			return null;
		}

		final Document document = DocumentInterfaceWrapper.getDocument(model);
		if (document == null)
		{
			throw new AdempiereException("Cannot extract " + Document.class + " from " + model);
		}

		final String tableName = document.getEntityDescriptor().getTableName();
		final boolean checkCache = false;
		@SuppressWarnings("unchecked")
		final T po = (T)TableModelLoader.instance.getPO(document.getCtx(), tableName, document.getDocumentIdAsInt(), checkCache, ITrx.TRXNAME_ThreadInherited);
		return po;
	}
	
	@Override
	public Evaluatee getEvaluatee(final Object model)
	{
		return DocumentInterfaceWrapper.getDocument(model).asEvaluatee();
	}
}
