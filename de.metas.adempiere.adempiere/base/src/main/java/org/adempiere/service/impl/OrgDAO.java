package org.adempiere.service.impl;

/*
 * #%L
 * ADempiere ERP - Base
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

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.IOrgDAO;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_AD_TreeNode;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;

public class OrgDAO implements IOrgDAO
{
	@Override
	@Cached(cacheName = I_AD_Org.Table_Name + "#by#" + I_AD_Org.COLUMNNAME_AD_Client_ID)
	public List<I_AD_Org> retrieveClientOrgs(@CacheCtx final Properties ctx, final int adClientId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Org.class)
				.setContext(ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_Org.COLUMNNAME_AD_Client_ID, adClientId)
				.create()
				.list(I_AD_Org.class);
	}

	@Override
	@Cached(cacheName = I_AD_Org.Table_Name)
	public I_AD_Org retrieveOrg(@CacheCtx final Properties ctx, final int adOrgId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Org.class)
				.setContext(ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_Org.COLUMNNAME_AD_Org_ID, adOrgId)
				.create()
				.firstOnly(I_AD_Org.class);
	}

	@Override
	@Cached(cacheName = I_AD_OrgInfo.Table_Name)
	public I_AD_OrgInfo retrieveOrgInfo(@CacheCtx final Properties ctx, final int adOrgId, @CacheTrx final String trxName)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_OrgInfo.class)
				.setContext(ctx, trxName)
				.addEqualsFilter(I_AD_OrgInfo.COLUMNNAME_AD_Org_ID, adOrgId)
				.create()
				.firstOnly(I_AD_OrgInfo.class);
	}

	@Override
	public I_AD_Org retrieveOrganizationByValue(final Properties ctx, final String value)
	{
		if (value == null)
		{
			return null;
		}

		final String valueFixed = value.trim();

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Org.class)
				.setContext(ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_Org.COLUMNNAME_Value, valueFixed)
				.create()
				.setClient_ID()
				.firstOnly(I_AD_Org.class);
	}

	@Override
	public List<I_AD_Org> retrieveChildOrgs(final Properties ctx, final int parentOrgId, final int adTreeOrgId)
	{
		if (parentOrgId <= 0)
		{
			return ImmutableList.of();
		}

		// Do we look for trees?
		if (adTreeOrgId <= 0)
		{
			return ImmutableList.of();
		}

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_TreeNode.class)
				.setContext(ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_TreeNode.COLUMNNAME_AD_Tree_ID, adTreeOrgId)
				.addEqualsFilter(I_AD_TreeNode.COLUMNNAME_Parent_ID, parentOrgId)
				.addOnlyActiveRecordsFilter()
				.andCollect(I_AD_TreeNode.COLUMN_Node_ID, I_AD_Org.class)
				.create()
				.list();
	}
}
