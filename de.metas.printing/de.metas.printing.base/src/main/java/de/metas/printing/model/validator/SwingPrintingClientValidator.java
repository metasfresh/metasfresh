package de.metas.printing.model.validator;

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

import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;
import org.compiere.model.MSession;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.model.I_AD_Session;
import de.metas.hostkey.api.IHostKeyBL;
import de.metas.hostkey.spi.impl.SwingHostKeyStorage;
import de.metas.logging.LogManager;
import de.metas.printing.Printing_Constants;
import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.client.IPrintingClientDelegate;
import de.metas.printing.model.I_AD_Printer_Config;

/**
 * Printing client thread lifecycle manager. This validator is responsible with setup and starting the printing client.
 * 
 * @author tsa
 * 
 */
public class SwingPrintingClientValidator extends AbstractModuleInterceptor
{
	private static final transient Logger logger = LogManager.getLogger(SwingPrintingClientValidator.class);

	private Throwable clientStartupIssue = null;

	private boolean isAvailable()
	{
		if (!Printing_Constants.isEnabled())
		{
			return false;
		}
		
		return Ini.isClient() && clientStartupIssue == null;
	}

	@Override
	protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		if (!isAvailable())
		{
			return;
		}

		// TODO: i think we needed to run it only if Ini.isClient
		final IHostKeyBL hostKeyBL = Services.get(IHostKeyBL.class);

		final SwingHostKeyStorage hostkeyStorage = new SwingHostKeyStorage();
		hostKeyBL.setHostKeyStorage(hostkeyStorage);
	}

	@Override
	public void onUserLogin(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		if (!isAvailable())
		{
			return;
		}

		// Don't start the printing client for System Administrators because we will clutter our migration scripts
		if (AD_Role_ID == Env.CTXVALUE_AD_Role_ID_System)
		{
			return;
		}

		try
		{
			//
			// Config AD_Session
			final String hostKey = Services.get(IHostKeyBL.class).getHostKey();

			final Properties ctx = Env.getCtx();
			final MSession sessionPO = MSession.get(ctx, false);
			final I_AD_Session session = InterfaceWrapperHelper.create(sessionPO, I_AD_Session.class);
			session.setHostKey(hostKey);
			InterfaceWrapperHelper.save(session);
			sessionPO.updateContext(false);

			//
			// Create/Start the printing client thread *if* we do not use another client's config
			final I_AD_Printer_Config printerConfig = Services.get(IPrintingDAO.class).retrievePrinterConfig(new PlainContextAware(ctx), hostKey, -1);
			if (printerConfig == null // no print config yet, so it can't be set to "use shared" 
					|| printerConfig.getAD_Printer_Config_Shared_ID() <= 0)
			{
				final IPrintingClientDelegate printingClient = Services.get(IPrintingClientDelegate.class);
				if (!printingClient.isStarted())
				{
					printingClient.start();
				}
			}
		}
		catch (Throwable t)
		{
			clientStartupIssue = t;

			logger.warn(t.getLocalizedMessage(), t);
			Services.get(IClientUI.class).error(0, "Printing client not available", t.getLocalizedMessage());
		}
	}

	@Override
	public void beforeLogout(final org.compiere.model.I_AD_Session session)
	{
		// make sure printing client is stopped BEFORE logout, when session is still active and database accessible
		final IPrintingClientDelegate printingClient = Services.get(IPrintingClientDelegate.class);
		printingClient.stop();
	}

	@Override
	public void afterLogout(final org.compiere.model.I_AD_Session session)
	{
		// reset, because for another user/role, the client startup might be OK
		clientStartupIssue = null;
	}
}
