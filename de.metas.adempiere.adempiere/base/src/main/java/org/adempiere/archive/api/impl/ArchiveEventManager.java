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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.concurrent.CopyOnWriteArrayList;

import org.adempiere.archive.api.IArchiveEventManager;
import org.adempiere.archive.spi.IArchiveEventListener;
import org.adempiere.util.Check;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_AD_User;

public class ArchiveEventManager implements IArchiveEventManager
{
	private final CopyOnWriteArrayList<IArchiveEventListener> listeners = new CopyOnWriteArrayList<>();

	@Override
	public void registerArchiveEventListener(final IArchiveEventListener listener)
	{
		Check.assumeNotNull(listener, "listener not null");
		listeners.addIfAbsent(listener);
	}

	@Override
	public void firePdfUpdate(final I_AD_Archive archive, final I_AD_User user, final String action)
	{
		for (final IArchiveEventListener listener : listeners)
		{
			listener.onPdfUpdate(archive, user, action);
		}
	}

	@Override
	public void fireEmailSent(final I_AD_Archive archive, final String action, final I_AD_User user, final String emailFrom, final String emailTo, final String emailCc, final String emailBcc, final String status)
	{
		for (final IArchiveEventListener listener : listeners)
		{
			listener.onEmailSent(archive, action, user, emailFrom, emailTo, emailCc, emailBcc, status);
		}
	}

	@Override
	public void firePrintOut(final I_AD_Archive archive, final I_AD_User user, final String printerName, final int copies, final String status)
	{
		for (final IArchiveEventListener listener : listeners)
		{
			listener.onPrintOut(archive, user, printerName, copies, status);
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
