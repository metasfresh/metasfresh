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

import java.util.Date;
import java.util.Properties;

import org.adempiere.util.Check;
import org.compiere.Adempiere.RunMode;
import org.compiere.model.MAcctProcessor;
import org.compiere.server.AcctProcessor;
import org.compiere.util.Env;

import com.google.common.base.Stopwatch;

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

		final long sleepMS = 10 * 1000;

		while (true)
		{
			for (final MAcctProcessor acctProcessorModel : MAcctProcessor.getActive(ctx))
			{
				final AcctProcessor acctProcessor = new AcctProcessor(acctProcessorModel);
				acctProcessor.setInitialNapSeconds(0);
				System.out.println("\n\nRunning " + acctProcessor);
				final Stopwatch duration = Stopwatch.createStarted();
				acctProcessor.runNow();
				System.out.println("Done " + acctProcessor + ": " + acctProcessor.getServerInfo());
				System.out.println("Duration: " + duration);
				// acctProcessor.start();
				// acctProcessor.join();
			}

			System.out.println("Sleeping " + sleepMS + "ms until " + new Date(System.currentTimeMillis() + sleepMS));
			Thread.sleep(sleepMS);
		}
	}

}
