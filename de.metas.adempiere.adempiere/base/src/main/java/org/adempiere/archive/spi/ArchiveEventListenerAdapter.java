package org.adempiere.archive.spi;

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


import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_AD_User;

public class ArchiveEventListenerAdapter implements IArchiveEventListener
{
	@Override
	public void onPdfUpdate(final I_AD_Archive archive, final I_AD_User user, final String action)
	{
		// nothing
	}

	@Override
	public void onEmailSent(final I_AD_Archive archive, final String action, final I_AD_User user, final String from, final String to, final String cc, final String bcc, final String status)
	{
		// nothing
	}

	@Override
	public void onPrintOut(final I_AD_Archive archive, final I_AD_User user, final String printerName, final int copies, final String status)
	{
		// nothing
	}

	@Override
	public void onVoidDocument(I_AD_Archive archive)
	{
		// nothing
	}
}
