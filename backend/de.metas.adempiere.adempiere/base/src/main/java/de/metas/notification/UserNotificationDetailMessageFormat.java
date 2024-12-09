package de.metas.notification;

<<<<<<< HEAD
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.ITableRecordReference;
import org.slf4j.Logger;

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.document.engine.IDocumentBL;
import de.metas.event.EventMessageFormatTemplate;
import de.metas.logging.LogManager;
import de.metas.util.Services;
<<<<<<< HEAD
=======
import lombok.NonNull;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.ITableRecordReference;
import org.slf4j.Logger;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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
<<<<<<< HEAD
@SuppressWarnings("serial")
final class UserNotificationDetailMessageFormat extends EventMessageFormatTemplate
{
	public static final UserNotificationDetailMessageFormat newInstance()
=======
final class UserNotificationDetailMessageFormat extends EventMessageFormatTemplate
{
	public static UserNotificationDetailMessageFormat newInstance()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return new UserNotificationDetailMessageFormat();
	}

	private static final Logger logger = LogManager.getLogger(UserNotificationDetailMessageFormat.class);

	private UserNotificationDetailMessageFormat()
	{
		super();
	}

	@Override
<<<<<<< HEAD
	protected String formatTableRecordReference(final ITableRecordReference recordRef)
	{
		// Retrieve the record
		Object record;
=======
	protected String formatTableRecordReference(@NonNull final ITableRecordReference recordRef)
	{
		// Retrieve the record
		final Object record;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

<<<<<<< HEAD
=======
		if(record == null)
		{
			logger.info("Failed retrieving record for " + recordRef);
			return "<" + recordRef.getRecord_ID() + ">";
		}
		
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		final String documentNo = Services.get(IDocumentBL.class).getDocumentNo(record);
		return documentNo;
	}
}
