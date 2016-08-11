package de.metas.ui.web.window.model;

import org.adempiere.ad.wrapper.IInterfaceWrapperHelper;
import org.adempiere.exceptions.AdempiereException;
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

public class DocumentInterfaceWrapperHelper implements IInterfaceWrapperHelper
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
			return DocumentInterfaceWrapper.createOld(model, modelClass);
		}
		else
		{
			// preserve "old values" flag
			return DocumentInterfaceWrapper.create(model, modelClass);
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
		if (!document.hasField(columnName))
		{
			final AdempiereException ex = new AdempiereException("No field with ColumnName=" + columnName + " found in " + document + " for " + model);
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

		final FieldChangedEventCollector eventsCollector = FieldChangedEventCollector.newInstance(); // FIXME use a thread local delegate?
		document.setValueFromJsonObject(columnName, value, eventsCollector);
		return true;
	}

}
