package de.metas.ui.web.notification;

import org.adempiere.model.IContextAware;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.slf4j.Logger;

import de.metas.document.engine.IDocActionBL;
import de.metas.event.EventMessageFormatTemplate;
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

/**
 * {@link UserNotification}'s message formatter.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@SuppressWarnings("serial")
final class UserNotificationDetailMessageFormat extends EventMessageFormatTemplate
{
	public static final UserNotificationDetailMessageFormat newInstance()
	{
		return new UserNotificationDetailMessageFormat();
	}

	private static final Logger logger = LogManager.getLogger(UserNotificationDetailMessageFormat.class);

	private UserNotificationDetailMessageFormat()
	{
		super();
	}

	@Override
	protected String formatTableRecordReference(final ITableRecordReference recordRef)
	{
		// Retrieve the record
		Object record;
		try
		{
			final IContextAware context = PlainContextAware.createUsingOutOfTransaction();
			record = recordRef.getModel(context);
		}
		catch (final Exception e)
		{
			logger.info("Failed retrieving record for " + recordRef, e);
			return "<" + recordRef.getRecord_ID() + ">";
		}

		final String documentNo = Services.get(IDocActionBL.class).getDocumentNo(record);
		return documentNo;
	}
}
