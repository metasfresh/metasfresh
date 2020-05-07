package org.compiere.server.devtools;

/*
 * #%L
 * de.metas.adempiere.adempiere.serverRoot.base
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

import org.adempiere.util.Check;
import org.compiere.Adempiere.RunMode;
import org.compiere.server.AdempiereServerMgr;
import org.compiere.util.Env;

/**
 * Start ADempiere server (for development use).
 *
 * NOTE:
 * <ul>
 * <li>It will start all processors (accounting, schedulers), all workpackage processors etc.
 * <li>It will NOT start EJBs.
 * </ul>
 * 
 * Eclipse config:
 * <ul>
 * <li>make sure you the eclipse luncher is in endcustomer project (e.g. de.metas.endcustomer..serverRoot)
 * <li>make sure the luncher is configuring the PropertyFile
 * </ul>
 *
 * @author tsa
 *
 */
public class Server_RunNow
{

	public static void main(final String[] args) throws InterruptedException
	{
		// NOTE: We expect the "PropertyFile" to be configured from eclipse luncher.
		// final String username = System.getProperty("user.name");
		// final String propertyDir = "c:/workspaces//de.metas.endcustomer./";
		// final String propertyFile = new File(propertyDir, "Adempiere.properties_" + username).getAbsolutePath();
		// System.setProperty("PropertyFile", propertyFile);
		if (Check.isEmpty(System.getProperty("PropertyFile"), true))
		{
			throw new IllegalStateException("Make sure the PropertyFile is configured in eclipse luncher");
		}

		Env.getSingleAdempiereInstance(null).startup(RunMode.BACKEND);

		final Properties ctx = Env.getCtx();
		Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, 0);
		Env.setContext(ctx, Env.CTXNAME_AD_Org_ID, 0);
		Env.setContext(ctx, Env.CTXNAME_AD_User_ID, 0);
		Env.setContext(ctx, Env.CTXNAME_AD_Role_ID, 0);
		Env.setContext(ctx, Env.CTXNAME_AD_Session_ID, Env.CTXVALUE_AD_SESSION_ID_NONE);

		//
		// Start all servers
		AdempiereServerMgr.get(); // adempiereAlreadyStarted=true
	}

}
