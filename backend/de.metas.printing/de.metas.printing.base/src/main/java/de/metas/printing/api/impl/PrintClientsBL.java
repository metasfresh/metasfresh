package de.metas.printing.api.impl;

/*
 * #%L
 * de.metas.printing.base
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


import java.util.Properties;

import lombok.NonNull;
import org.adempiere.ad.session.ISessionBL;
import org.adempiere.ad.session.MFSession;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Env;

import de.metas.printing.api.IPrintClientsBL;
import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.model.I_AD_Print_Clients;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;

import javax.annotation.Nullable;

public class PrintClientsBL implements IPrintClientsBL
{
	@Override
	public I_AD_Print_Clients createPrintClientsEntry(final Properties ctx, final String hostkey)
	{
		final String trxName = ITrx.TRXNAME_None;

		I_AD_Print_Clients printClientsEntry = Services.get(IPrintingDAO.class).retrievePrintClientsEntry(ctx, hostkey);
		if (printClientsEntry == null)
		{
			// task 08021: we want to create the record with AD_Client_ID=0 etc, because there shall be just one record per host, not different ones per user, client, role etc
			final Properties sysContext = Env.createSysContext(ctx);
			printClientsEntry = InterfaceWrapperHelper.create(sysContext, I_AD_Print_Clients.class, trxName);
			printClientsEntry.setHostKey(hostkey);
		}

		printClientsEntry.setAD_Session_ID(Env.getContextAsInt(ctx, Env.CTXNAME_AD_Session_ID));
		printClientsEntry.setDateLastPoll(SystemTime.asTimestamp());
		InterfaceWrapperHelper.save(printClientsEntry);
		
		return printClientsEntry;
	}

	@Override
	@Nullable
	public String getHostKeyOrNull(@NonNull final Properties ctx)
	{
		// Check session
		final MFSession session = Services.get(ISessionBL.class).getCurrentSession(ctx);
		if (session != null)
		{
			return session.getOrCreateHostKey(ctx);
		}
		return null;
	}
}
