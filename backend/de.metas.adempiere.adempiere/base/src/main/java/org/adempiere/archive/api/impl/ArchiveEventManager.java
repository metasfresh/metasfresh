package org.adempiere.archive.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.email.EMailAddress;
import de.metas.email.mailboxes.UserEMailConfig;
import de.metas.logging.LogManager;
import de.metas.user.UserId;
import lombok.NonNull;
import org.adempiere.archive.api.ArchiveEmailSentStatus;
import org.adempiere.archive.api.ArchivePrintOutStatus;
import org.adempiere.archive.api.IArchiveEventManager;
import org.adempiere.archive.spi.IArchiveEventListener;
import org.compiere.model.I_AD_Archive;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.concurrent.CopyOnWriteArrayList;

public class ArchiveEventManager implements IArchiveEventManager
{
	private static final Logger logger = LogManager.getLogger(ArchiveEventManager.class);
	private final CopyOnWriteArrayList<IArchiveEventListener> listeners = new CopyOnWriteArrayList<>();

	@Override
	public void registerArchiveEventListener(@NonNull final IArchiveEventListener listener)
	{
		final boolean registered = listeners.addIfAbsent(listener);

		if (registered)
		{
			logger.info("Registered {}", listener);
		}
		else
		{
			logger.warn("Skip registering {} because it was already registered", listener);
		}
	}

	@Override
	public void firePdfUpdate(
			@NonNull final I_AD_Archive archive,
			@Nullable final UserId userId)
	{
		for (final IArchiveEventListener listener : listeners)
		{
			listener.onPdfUpdate(archive, userId);
		}
	}

	@Override
	public void fireEmailSent(
			final I_AD_Archive archive,
			final UserEMailConfig user,
			final EMailAddress emailFrom,
			final EMailAddress emailTo,
			final EMailAddress emailCc,
			final EMailAddress emailBcc,
			final ArchiveEmailSentStatus status)
	{
		for (final IArchiveEventListener listener : listeners)
		{
			listener.onEmailSent(archive, user, emailFrom, emailTo, emailCc, emailBcc, status);
		}
	}

	@Override
	public void firePrintOut(
			final I_AD_Archive archive,
			@Nullable final UserId userId,
			final String printerName,
			final int copies,
			@NonNull final ArchivePrintOutStatus status)
	{
		for (final IArchiveEventListener listener : listeners)
		{
			listener.onPrintOut(archive, userId, printerName, copies, status);
		}
	}

	@Override
	public void fireVoidDocument(final I_AD_Archive archive)
	{
		for (final IArchiveEventListener listener : listeners)
		{
			listener.onVoidDocument(archive);
		}
	}
}
