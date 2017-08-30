/**
 *
 */
package de.metas.clientupdate;

/*
 * #%L
 * de.metas.swat.base
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


import java.text.MessageFormat;

import javax.swing.JOptionPane;

import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.session.MFSession;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_System;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;

/**
 * This model validator starts a thread which check if the Client Application is up2date.
 *
 * @author teo.sarca@gmail.com
 */
public class ClientUpdateValidator extends AbstractModuleInterceptor
{
	public static final String SYSCONFIG_Enabled = "de.metas.clientcheck.Enabled";
	public static final String SYSCONFIG_CheckInterval = "de.metas.clientcheck.CheckInterval";
	public static final String MSG_ErrorMessage = "de.metas.clientcheck.ErrorMessage";
	public static final int DEFAULT_CheckInterval = 10; // mins

	class Checker implements Runnable
	{
		private boolean stop = false;

		@Override
		public void run()
		{
			while (!stop)
			{
				final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
				final boolean enabled = sysConfigBL.getBooleanValue(SYSCONFIG_Enabled, false, Env.getAD_Client_ID(Env.getCtx()));
				if (enabled)
				{
					try
					{
						checkImplementationVersion();
					}
					catch (Exception e)
					{
						log.error("Error", e);
					}
				}
				//
				// Sleep
				int checkIntervalMin = sysConfigBL.getIntValue(SYSCONFIG_CheckInterval, DEFAULT_CheckInterval, Env.getAD_Client_ID(Env.getCtx()));
				try
				{
					synchronized (checker)
					{
						checker.wait(checkIntervalMin * 60 * 1000); // minutes
					}
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	private Logger log = LogManager.getLogger(getClass());

	private Checker checker = null;

	@Override
	public void onUserLogin(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		if (Ini.isClient())
		{
			final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
			final boolean enabled = sysConfigBL.getBooleanValue(SYSCONFIG_Enabled, false, Env.getAD_Client_ID(Env.getCtx()));
			if (enabled)
			{
				try
				{
					checkImplementationVersion();
				}
				catch (Exception e)
				{
					log.error("Error", e);
				}
			}
			checker = new Checker();
			final Thread thread = new Thread(checker, Checker.class.getCanonicalName());
			thread.start();
		}
	}

	/**
	 *
	 */
	private final void checkImplementationVersion()
	{
		final String clientVersion = Adempiere.getImplementationVersion();
		Check.assumeNotNull(clientVersion, "Adempiere.getImplementationVersion() is not null");
		if (clientVersion.endsWith(Adempiere.CLIENT_VERSION_UNPROCESSED)
				|| clientVersion.endsWith(Adempiere.CLIENT_VERSION_LOCAL_BUILD))
		{
			log.info("Adempiere ImplementationVersion=" + clientVersion + "! Not checking against DB");
			return;
		}
		final String newVersion = DB.getSQLValueStringEx(null, "SELECT " + I_AD_System.COLUMNNAME_LastBuildInfo + " FROM " + I_AD_System.Table_Name);
		log.info("Build DB=" + newVersion);
		log.info("Build Cl=" + clientVersion);
		// Identical DB version
		if (clientVersion != null && clientVersion.equals(newVersion))
		{
			return;
		}

		final String title = org.compiere.Adempiere.getName() + " " + Services.get(IMsgBL.class).getMsg(Env.getCtx(), MSG_ErrorMessage, true);
		// Client version {} is available (current version is {}).
		String msg = Services.get(IMsgBL.class).getMsg(Env.getCtx(), MSG_ErrorMessage);   // complete message
		msg = MessageFormat.format(msg, new Object[] { clientVersion, newVersion });
		JOptionPane.showMessageDialog(null,
				msg,
				title,
				JOptionPane.ERROR_MESSAGE);

		Env.exitEnv(1);
	}


	@Override
	public void beforeLogout(final MFSession session)
	{
		if (checker == null)
		{
			return; // nothing to do
		}

		// stop the checker
		checker.stop = true;
		synchronized (checker)
		{
			checker.notify();
		}

	}
}
