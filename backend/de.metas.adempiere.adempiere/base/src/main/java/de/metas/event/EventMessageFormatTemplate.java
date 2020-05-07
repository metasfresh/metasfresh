package de.metas.event;

import java.util.Map;

import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.text.MapFormat;
import org.compiere.util.Util;
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
 * {@link Event}'s message formatter template.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@SuppressWarnings("serial")
public abstract class EventMessageFormatTemplate extends MapFormat
{
	private static final Logger logger = LogManager.getLogger(EventMessageFormatTemplate.class);

	protected EventMessageFormatTemplate()
	{
		super();
		setLeftBrace("{");
		setRightBrace("}");
		setThrowExceptionIfKeyWasNotFound(false);
	}

	@Override
	protected final String formatObject(final Object obj)
	{
		final ITableRecordReference modelRef = extractTableRecordReferenceOrNull(obj);
		if (modelRef != null)
		{
			try
			{
				return formatTableRecordReference(modelRef);
			}
			catch (final Exception e)
			{
				logger.warn("Failed formatting: {}", modelRef, e);
				return "?";
			}
		}
		
		// Default/fallback
		return super.formatObject(obj);
	}
	
	private static final ITableRecordReference extractTableRecordReferenceOrNull(final Object obj)
	{
		if(obj == null)
		{
			return null;
		}

		if (obj instanceof ITableRecordReference)
		{
			return (ITableRecordReference)obj;
		}

		// Extract the TableRecordReference from Map.
		// Usually that's the case when the parameters were deserialized and the the TableRecordRefererence was deserialized as Map.
		if(obj instanceof Map)
		{
			final Map<?, ?> map = (Map<?, ?>)obj;
			return TableRecordReference.ofMapOrNull(map);
		}

		return null;
	}

	protected abstract String formatTableRecordReference(final ITableRecordReference recordRef);

	@Override
	protected String formatText(final String text)
	{
		if (text == null || text.isEmpty())
		{
			return "";
		}

		return Util.maskHTML(text);
	}

}
