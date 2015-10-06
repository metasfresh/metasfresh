package de.metas.adempiere.util.jmx;

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


import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.Services;
import org.adempiere.util.trxConstraints.api.IOpenTrxBL;
import org.compiere.util.CLogMgt;
import org.compiere.util.CacheMgt;
import org.compiere.util.Env;
import org.compiere.util.Ini;

public class Swat implements SwatMBean
{
	public static final String JMXNAME_PREFIX = "de.metas.adempiere:type=Swat";
	
	private static final SwatMBean instance = new Swat();

	public static void register()
	{ 
		// task 04585: append RunMode to avoid JMX name collisions when we have multiple addempiere applications within one app server.
		register(JMXNAME_PREFIX + "_" + Ini.getRunMode(), instance);
	}
	
	private static void register(final String jmxName, final Object mbean)
	{
		final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		try
		{
			final ObjectName name = new ObjectName(jmxName);
			mbs.registerMBean(mbean, name);
		}
		catch (MalformedObjectNameException e)
		{
			e.printStackTrace();
		}
		catch (InstanceAlreadyExistsException e)
		{
			e.printStackTrace();
		}
		catch (MBeanRegistrationException e)
		{
			e.printStackTrace();
		}
		catch (NotCompliantMBeanException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public String[] getActiveTrxNames()
	{
		ITrx[] trxs = Services.get(ITrxManager.class).getActiveTransactions();
		String[] result = new String[trxs.length];
		for (int i = 0; i < trxs.length; i++)
		{
			result[i] = trxs[i].getTrxName();
		}
		return result;
	}

	@Override
	public String getStrackTrace(String trxName)
	{
		return Services.get(IOpenTrxBL.class).getCreationStackTrace(trxName);
	}
	
	@Override
	public String[] getServerContext()
	{
		final Properties ctx = Env.getCtx();
		String[] context = Env.getEntireContext(ctx);
		Arrays.sort(context);
		return context;
	}

	@Override
	public void setLogLevel(String levelName)
	{
		CLogMgt.setLevel(levelName);
	}

	@Override
	public String getLogLevel()
	{
		final Level level = CLogMgt.getLevel();
		return level == null ? null : level.getName();
	}
	
	@Override
	public void runFinalization()
	{
		System.runFinalization();
	}
	
	@Override
	public void resetLocalCache()
	{
		CacheMgt.get().reset();
	}
}
