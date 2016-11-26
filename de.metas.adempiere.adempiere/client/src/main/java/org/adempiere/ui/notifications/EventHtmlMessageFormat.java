package org.adempiere.ui.notifications;

import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.text.MapFormat;
import org.compiere.util.Util;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


import org.slf4j.Logger;

import de.metas.adempiere.util.ADHyperlinkBuilder;
import de.metas.logging.LogManager;

/**
 * Extension of {@link MapFormat} which produces HTML text.
 * 
 * Mainly,
 * <ul>
 * <li>handles {@link ITableRecordReference} parameters and converts them to URLs
 * <li>escape all plain text chunks
 * </ul>
 * 
 * @author tsa
 *
 */
class EventHtmlMessageFormat extends MapFormat
{
	private static final long serialVersionUID = 1L;

	private static final transient Logger logger = LogManager.getLogger(EventHtmlMessageFormat.class);

	@Override
	protected String formatObject(final Object obj)
	{
		if (obj instanceof ITableRecordReference)
		{
			final ITableRecordReference modelRef = (ITableRecordReference)obj;
			try
			{
				return new ADHyperlinkBuilder().createShowWindowHTML(modelRef);
			}
			catch (Exception e)
			{
				logger.warn("Failed building URL for " + modelRef, e);
				return "?";
			}
		}
		else
		{
			return super.formatObject(obj);
		}
	}

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
