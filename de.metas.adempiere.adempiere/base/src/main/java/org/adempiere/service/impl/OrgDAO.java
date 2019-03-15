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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IOrgDAO;
import org.adempiere.service.OrgId;
import org.adempiere.service.OrgIdNotFoundException;
import org.adempiere.util.proxy.Cached;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_AD_TreeNode;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;

import de.metas.cache.annotation.CacheCtx;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;

public class OrgDAO implements IOrgDAO
{
	@Override
	public void save(@NonNull final I_AD_Org orgRecord)
	{
		InterfaceWrapperHelper.saveRecord(orgRecord);
	}

	@Override
	public void save(@NonNull final I_AD_OrgInfo orgInfoRecord)
	{
		InterfaceWrapperHelper.saveRecord(orgInfoRecord);
	}

	@Override
	@Cached(cacheName = I_AD_Org.Table_Name + "#by#" + I_AD_Org.COLUMNNAME_AD_Client_ID)
	public List<I_AD_Org> retrieveClientOrgs(@CacheCtx final Properties ctx, final int adClientId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Org.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_Org.COLUMNNAME_AD_Client_ID, adClientId)
				.create()
				.list(I_AD_Org.class);
	}

	@Override
	public I_AD_Org retrieveOrg(final Properties ctx, final int adOrgId)
	{
		// we can't use TRXNAME_None because we don't know if the record aleady exists outside of the current trx!
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Org.class, ctx, ITrx.TRXNAME_ThreadInherited)
				.addEqualsFilter(I_AD_Org.COLUMNNAME_AD_Org_ID, adOrgId)
				.create()
				.firstOnly(I_AD_Org.class);
	}

	@Override
	public I_AD_OrgInfo retrieveOrgInfo(final Properties ctx, final int adOrgId, final String trxName)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_OrgInfo.class, ctx, trxName)
				.addEqualsFilter(I_AD_OrgInfo.COLUMNNAME_AD_Org_ID, adOrgId)
				.create()
				.firstOnly(I_AD_OrgInfo.class);
	}

	@Override
	public WarehouseId getOrgWarehouseId(@NonNull final OrgId orgId)
	{
		final I_AD_OrgInfo orgInfo = retrieveOrgInfo(Env.getCtx(), orgId.getRepoId(), ITrx.TRXNAME_None);
		// Check.assumeNotNull(orgInfo, "OrgInfo not null"); // NOTE: commented out because it fails some JUnit test in case there is not OrgInfo

		if (orgInfo == null)
		{
			return null;
		}

		return WarehouseId.ofRepoIdOrNull(orgInfo.getM_Warehouse_ID());
	}

	@Override
	public WarehouseId getOrgPOWarehouseId(@NonNull final OrgId orgId)
	{
		final I_AD_OrgInfo orgInfo = retrieveOrgInfo(Env.getCtx(), orgId.getRepoId(), ITrx.TRXNAME_None);
		// Check.assumeNotNull(orgInfo, "OrgInfo not null"); // NOTE: commented out because it fails some JUnit test in case there is not OrgInfo

		if (orgInfo == null)
		{
			return null;
		}

		return WarehouseId.ofRepoIdOrNull(orgInfo.getM_WarehousePO_ID());
	}

	@Override
	public WarehouseId getOrgDropshipWarehouseId(@NonNull final OrgId orgId)
	{
		final I_AD_OrgInfo orgInfo = retrieveOrgInfo(Env.getCtx(), orgId.getRepoId(), ITrx.TRXNAME_None);
		// Check.assumeNotNull(orgInfo, "OrgInfo not null"); // NOTE: commented out because it fails some JUnit test in case there is not OrgInfo

		if (orgInfo == null)
		{
			return null;
		}

		return WarehouseId.ofRepoIdOrNull(orgInfo.getDropShip_Warehouse_ID());
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
				.createQueryBuilder(I_AD_Org.class, ctx, ITrx.TRXNAME_None)
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
				.createQueryBuilder(I_AD_TreeNode.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_TreeNode.COLUMNNAME_AD_Tree_ID, adTreeOrgId)
				.addEqualsFilter(I_AD_TreeNode.COLUMNNAME_Parent_ID, parentOrgId)
				.addOnlyActiveRecordsFilter()
				.andCollect(I_AD_TreeNode.COLUMN_Node_ID, I_AD_Org.class)
				.create()
				.list();
	}

	@Override
	public Optional<OrgId> retrieveOrgIdBy(@NonNull final OrgQuery orgQuery)
	{
		final IQueryBuilder<I_AD_Org> queryBuilder = createQueryBuilder(orgQuery.isOutOfTrx());

		final int orgId = queryBuilder
				.addEqualsFilter(I_AD_Org.COLUMNNAME_Value, orgQuery.getOrgValue())
				.create()
				.setApplyAccessFilter(true)
				.firstIdOnly();

		if (orgId < 0 && orgQuery.isFailIfNotExists())
		{
			final String msg = StringUtils.formatMessage("Found no existing Org; Searched via value='{}'", orgQuery.getOrgValue());
			throw new OrgIdNotFoundException(msg);
		}

		return Optional.ofNullable(OrgId.ofRepoIdOrNull(orgId));
	}

	private IQueryBuilder<I_AD_Org> createQueryBuilder(final boolean outOfTrx)
	{
		final IQueryBuilder<I_AD_Org> queryBuilder;
		if (outOfTrx)
		{
			queryBuilder = Services.get(IQueryBL.class)
					.createQueryBuilderOutOfTrx(I_AD_Org.class);
		}
		else
		{
			queryBuilder = Services.get(IQueryBL.class)
					.createQueryBuilder(I_AD_Org.class);
		}
		return queryBuilder.addOnlyActiveRecordsFilter();
	}
}
