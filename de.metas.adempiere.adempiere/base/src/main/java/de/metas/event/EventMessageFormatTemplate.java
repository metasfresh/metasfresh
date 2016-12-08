package de.metas.event;

import org.adempiere.util.lang.ITableRecordReference;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
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
		if (obj instanceof ITableRecordReference)
		{
			final ITableRecordReference modelRef = (ITableRecordReference)obj;
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
		else
		{
			return super.formatObject(obj);
		}
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
