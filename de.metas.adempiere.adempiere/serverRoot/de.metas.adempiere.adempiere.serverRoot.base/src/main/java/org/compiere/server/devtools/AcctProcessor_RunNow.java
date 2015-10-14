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


import java.io.File;
import java.util.Properties;

import org.compiere.Adempiere.RunMode;
import org.compiere.model.MAcctProcessor;
import org.compiere.server.AcctProcessor;
import org.compiere.util.Env;

/**
 * Helper class used by developer to manually start AcctProcessor or to compute the costs.
 * 
 * Use it more like a code snippet.
 * 
 * @author tsa
 *
 */
public class AcctProcessor_RunNow
{

	public static void main(String[] args) throws InterruptedException
	{
		final String username = System.getProperty("user.name");
		
		// final String propertyDir = "../de.metas.endcustomer./";
		//final String propertyDir = "c:/workspaces/live/de.metas.endcustomer./";
		final String propertyDir = "c:/workspaces//de.metas.endcustomer./";
		
		final String propertyFile = new File(propertyDir, "Adempiere.properties_" + username).getAbsolutePath();
		System.setProperty("PropertyFile", propertyFile);

		Env.getSingleAdempiereInstance().startup(RunMode.BACKEND);

		final Properties ctx = Env.getCtx();
		Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, 0);
		Env.setContext(ctx, Env.CTXNAME_AD_Org_ID, 0);
		Env.setContext(ctx, Env.CTXNAME_AD_User_ID, 0);
		Env.setContext(ctx, Env.CTXNAME_AD_Role_ID, 0);
		for (final MAcctProcessor acctProcessorModel : MAcctProcessor.getActive(ctx))
		{
			 final AcctProcessor acctProcessor = new AcctProcessor(acctProcessorModel);
			 acctProcessor.setInitialNapSeconds(0);
			 System.out.println("Running " + acctProcessor);
//			 acctProcessor.runNow();
			 acctProcessor.start();
			 acctProcessor.join();
			 

//			final I_AD_Client adClient = acctProcessorModel.getAD_Client();
//			Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, adClient.getAD_Client_ID());
//			System.out.println("Running MCost.create for " + adClient);
//			MCost.create(adClient);
		}
	}

}
