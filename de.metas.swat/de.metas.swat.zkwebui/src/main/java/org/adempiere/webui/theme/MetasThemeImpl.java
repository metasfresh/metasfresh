/**
 * 
 */
package org.adempiere.webui.theme;

/*
 * #%L
 * de.metas.swat.zkwebui
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
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;

import javax.servlet.ServletRequest;

import org.adempiere.model.POWrapper;
import org.adempiere.util.proxy.Cached;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.compiere.model.I_AD_Image;
import org.compiere.model.MImage;
import org.compiere.model.MOrgInfo;
import org.compiere.model.Query;
import org.compiere.util.CCache;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.zkoss.image.AImage;
import org.zkoss.image.Image;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;

import de.metas.adempiere.model.I_AD_OrgInfo;

/**
 * @author tsa
 * 
 */
public class MetasThemeImpl extends DefaultThemeImpl
{
	private final CLogger log = CLogger.getCLogger(getClass());

	private final CCache<MultiKey, Object> logoCache = new CCache<MultiKey, Object>(I_AD_OrgInfo.Table_Name + "_LogoCache", 10, 0);
	private final static Object NullImage = new Object();

	@Override
	public String getLargeLogo()
	{
		return null;
	}

	@Override
	public org.zkoss.image.Image getLargeLogoImage()
	{
		final int AD_Org_ID = Env.getAD_Org_ID(Env.getCtx());
		final String domainName = getDomainName();
		final MultiKey key = new MultiKey(AD_Org_ID, domainName, "large");
		Object image = logoCache.get(key);
		if (image != null)
		{
			return image == MetasThemeImpl.NullImage ? null : (Image)image;
		}

		I_AD_OrgInfo orgInfo = getOrgInfo(AD_Org_ID, domainName);
		while (orgInfo != null && orgInfo.getZK_Logo_Large_ID() <= 0)
		{
			orgInfo = getParentOrgInfo(orgInfo);
		}
		if (orgInfo == null)
		{
			logoCache.put(key, MetasThemeImpl.NullImage);
			return null;
		}

		image = getImage(orgInfo.getZK_Logo_Large());
		logoCache.put(key, image == null ? MetasThemeImpl.NullImage : image);
		return (Image)image;
	}

	@Override
	public String getSmallLogo()
	{
		return null;
	}

	@Override
	public org.zkoss.image.Image getSmallLogoImage()
	{
		final int AD_Org_ID = Env.getAD_Org_ID(Env.getCtx());
		final String domainName = getDomainName();
		final MultiKey key = new MultiKey(AD_Org_ID, domainName, "small");
		Object image = logoCache.get(key);
		if (image != null)
		{
			return image == MetasThemeImpl.NullImage ? null : (Image)image;
		}

		I_AD_OrgInfo orgInfo = getOrgInfo(AD_Org_ID, domainName);
		while (orgInfo != null && orgInfo.getZK_Logo_Small_ID() <= 0)
		{
			orgInfo = getParentOrgInfo(orgInfo);
		}
		if (orgInfo == null)
		{
			return null;
		}

		image = getImage(orgInfo.getZK_Logo_Small());
		logoCache.put(key, image == null ? MetasThemeImpl.NullImage : image);
		return (Image)image;
	}

	private String getDomainName()
	{
		final Execution execution = Executions.getCurrent();
		if (execution == null)
		{
			return null;
		}

		final Object n = execution.getNativeRequest();
		if (n instanceof ServletRequest)
		{
			return ((ServletRequest)n).getServerName();
		}
		return null;
	}

	private I_AD_OrgInfo getOrgInfoByDomainName(final String domainName)
	{
		final Properties ctx = Env.getCtx();
		final String whereClause = I_AD_OrgInfo.COLUMNNAME_ZK_DomainName + " IS NOT NULL";
		final List<I_AD_OrgInfo> list = new Query(ctx, org.compiere.model.I_AD_OrgInfo.Table_Name, whereClause, null)
				.setOrderBy(org.compiere.model.I_AD_OrgInfo.COLUMNNAME_AD_Org_ID)
				.list(I_AD_OrgInfo.class);

		for (final I_AD_OrgInfo orgInfo : list)
		{
			final StringTokenizer st = new StringTokenizer(orgInfo.getZK_DomainName(), ", ");
			while (st.hasMoreElements())
			{
				final String orgDomain = st.nextToken();
				if (domainName.equals(orgDomain))
				{
					return orgInfo;
				}
			}
		}

		return null;
	}

	@Cached
	private I_AD_OrgInfo getOrgInfo(final int AD_Org_ID, final String domainName)
	{
		I_AD_OrgInfo orgInfo;
		if (AD_Org_ID > 0)
		{
			orgInfo = POWrapper.create(MOrgInfo.get(Env.getCtx(), AD_Org_ID), I_AD_OrgInfo.class);
		}
		else
		{
			orgInfo = getOrgInfoByDomainName(domainName);
		}
		return orgInfo;
	}

	private I_AD_OrgInfo getParentOrgInfo(final I_AD_OrgInfo orgInfo)
	{
		if (orgInfo == null || orgInfo.getParent_Org_ID() <= 0)
		{
			return null;
		}
		return POWrapper.create(MOrgInfo.get(Env.getCtx(), orgInfo.getParent_Org_ID()), I_AD_OrgInfo.class);
	}

	private Image getImage(final I_AD_Image image)
	{
		byte[] data;
		if (image == null)
		{
			return null;
		}
		else if (image instanceof MImage)
		{
			data = ((MImage)image).getData();
		}
		else
		{
			data = image.getBinaryData();
		}

		String name = image.getName();
		name = new File(name).getName();

		try
		{
			return new AImage(name, data);
		}
		catch (final IOException e)
		{
			log.log(Level.SEVERE, e.getMessage(), e);
			return null;
		}
	}
}
