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


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.POJOQuery;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_SysConfig;

public class PlainSysConfigDAO extends AbstractSysConfigDAO
{
	public PlainSysConfigDAO()
	{
		super();
	}

	@Override
	public I_AD_SysConfig retrieveSysConfig(final Properties ctx, final String name, final int AD_Client_ID, final int AD_Org_ID, final String trxName)
	{
		return createSysConfigQuery(name, AD_Client_ID, AD_Org_ID, true).firstOnly(I_AD_SysConfig.class);
	}

	/**
	 * 
	 * @param name
	 * @param adClientId
	 * @param adOrgId
	 * @param strictClientOrg if false, also records were AD_Client_ID or AD_Org_ID is ZERO will be returned
	 * @return
	 */
	private final IQuery<I_AD_SysConfig> createSysConfigQuery(final String name, final int adClientId, final int adOrgId, final boolean strictClientOrg)
	{
		return new POJOQuery(I_AD_SysConfig.class)
				.addFilter(new IQueryFilter<Object>()
				{

					@Override
					public boolean accept(final Object o)
					{
						final I_AD_SysConfig pojo = (I_AD_SysConfig)o;
						if (!pojo.getName().equals(name))
						{
							return false;
						}
						
						if (pojo.getAD_Client_ID() != adClientId)
						{
							if (!strictClientOrg && pojo.getAD_Client_ID() > 0)
							{
								return false;
							}
						}
						
						if (pojo.getAD_Org_ID() != adOrgId)
						{
							if (strictClientOrg)
							{
								return false;
							}
							else if (pojo.getAD_Org_ID() == 0)
							{
								// Case: not strict client/org, our record is on Org=0
								// => valid, continue filtering
							}
							else
							{
								// Case: not strict client/org, our record is on Org>0
								// => not valid
								return false;
							}
						}

						if (strictClientOrg && !pojo.isActive())
						{
							return false;
						}

						return true;
					}
				})
				.setOrderBy(sysConfigFindOrderBy);
	}

	@Override
	public String retrieveSysConfigValue(String name, String defaultValue, int AD_Client_ID, int AD_Org_ID)
	{
		final I_AD_SysConfig sysConfig = createSysConfigQuery(name, AD_Client_ID, AD_Org_ID, false).first(I_AD_SysConfig.class);
		if (sysConfig == null)
		{
			return defaultValue;
		}
		return sysConfig.getValue();
	}

	@Override
	public List<String> retrieveNamesForPrefix(final String prefix, final int adClientId, final int adOrgId)
	{
		final List<I_AD_SysConfig> sysConfigs = new POJOQuery(I_AD_SysConfig.class)
				.addFilter(new IQueryFilter<Object>()
				{

					@Override
					public boolean accept(final Object o)
					{
						final I_AD_SysConfig pojo = (I_AD_SysConfig)o;
						if (!pojo.getName().startsWith(prefix))
						{
							return false;
						}
						if (pojo.getAD_Client_ID() != adClientId || pojo.getAD_Client_ID() > 0)
						{
							return false;
						}
						if (pojo.getAD_Org_ID() != adOrgId || pojo.getAD_Org_ID() > 0)
						{
							return false;
						}
						return true;
					}
				})
				.list(I_AD_SysConfig.class);

		final List<String> result = new ArrayList<String>();
		for (final I_AD_SysConfig sysConfig : sysConfigs)
		{
			result.add(sysConfig.getName());
		}

		Collections.sort(result);

		return result;
	}

}
