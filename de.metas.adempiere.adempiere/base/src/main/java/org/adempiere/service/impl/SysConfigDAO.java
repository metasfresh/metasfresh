package org.adempiere.service.impl;

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

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Check;
import org.adempiere.util.proxy.Cached;
import org.compiere.Adempiere;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.MSysConfig;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;

import de.metas.logging.LogManager;

public class SysConfigDAO extends AbstractSysConfigDAO
{
	private final Logger logger = LogManager.getLogger(SysConfigDAO.class);

	@Override
	public I_AD_SysConfig retrieveSysConfig(final Properties ctx, final String name, final int AD_Client_ID, final int AD_Org_ID, final String trxName)
	{
		final String whereClause = I_AD_SysConfig.COLUMNNAME_Name + "=?"
				+ " AND " + I_AD_SysConfig.COLUMNNAME_AD_Client_ID + "=?"
				+ " AND " + I_AD_SysConfig.COLUMNNAME_AD_Org_ID + "=?";

		final I_AD_SysConfig sysConfig = new Query(ctx, I_AD_SysConfig.Table_Name, whereClause, trxName)
				.setParameters(name, AD_Client_ID, AD_Org_ID)
				.firstOnly(I_AD_SysConfig.class);

		return sysConfig;
	}

	@Override
	public String retrieveSysConfigValue(final String Name, final String defaultValue, final int AD_Client_ID, final int AD_Org_ID)
	{
		final ApplicationContext springApplicationContext = Adempiere.getSpringApplicationContext();
		if (springApplicationContext != null)
		{
			final String springContextValue = springApplicationContext.getEnvironment().getProperty(Name);
			if (!Check.isEmpty(springContextValue, true))
			{
				logger.debug("Returning the spring context's value {}={} instead of looking up the AD_SysConfig record", new Object[] { Name, springContextValue });
				return springContextValue.trim();
			}
		}
		else
		{
			// If there is no Spring context then go an check JVM System Properties.
			// Usually we will get here when we will run some tools based on metasfresh framework.
			
			final Properties systemProperties = System.getProperties();
			final String systemPropertyValue = systemProperties.getProperty(Name);
			if(!Check.isEmpty(systemPropertyValue, true))
			{
				logger.debug("Returning the JVM system property's value {}={} instead of looking up the AD_SysConfig record", new Object[] { Name, systemPropertyValue });
				return systemPropertyValue.trim();
			}
		}

		return MSysConfig.getValue(Name, defaultValue, AD_Client_ID, AD_Org_ID);
	}

	@Override
	@Cached(cacheName = I_AD_SysConfig.Table_Name + "#NamesForPrefix", expireMinutes = Cached.EXPIREMINUTES_Never)
	public List<String> retrieveNamesForPrefix(final String prefix, final int adClientId, final int adOrgId)
	{
		Check.errorUnless(!Check.isEmpty(prefix, true), "prefix is empty");

		final String whereClause = I_AD_SysConfig.COLUMNNAME_Name + " LIKE ?"
				+ " AND " + I_AD_SysConfig.COLUMNNAME_AD_Client_ID + " IN (0,?)"
				+ " AND " + I_AD_SysConfig.COLUMNNAME_AD_Org_ID + " IN (0,?)"
				+ " AND " + I_AD_SysConfig.COLUMNNAME_IsActive + "=?";

		final String sqlPrefix = prefix + "%";

		return new Query(Env.getCtx(), I_AD_SysConfig.Table_Name, whereClause, ITrx.TRXNAME_None)
				.setParameters(sqlPrefix, adClientId, adOrgId, true)
				.setOrderBy(I_AD_SysConfig.COLUMNNAME_Name)
				.aggregateList(I_AD_SysConfig.COLUMNNAME_Name, IQuery.AGGREGATE_DISTINCT, String.class);
	}

}
