package org.adempiere.service;

import static de.metas.util.Check.assumeNotEmpty;
import static org.compiere.util.Util.coalesce;

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

import javax.annotation.Nullable;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.util.Env;

import de.metas.util.ISingletonService;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

public interface IOrgDAO extends ISingletonService
{
	void save(I_AD_Org orgRecord);

	void save(I_AD_OrgInfo orgInfoRecord);

	Optional<OrgId> retrieveOrgIdBy(OrgQuery orgQuery);

	@Value
	public static class OrgQuery
	{
		public static OrgQuery ofValue(@NonNull final String value)
		{
			return OrgQuery.builder().orgValue(value).build();
		}

		boolean outOfTrx;
		boolean failIfNotExists;
		String orgValue;

		@Builder
		private OrgQuery(
				@Nullable final Boolean outOfTrx,
				@Nullable final Boolean failIfNotExists,
				@NonNull final String orgValue)
		{
			this.outOfTrx = coalesce(outOfTrx, false);
			this.failIfNotExists = coalesce(failIfNotExists, false);
			this.orgValue = assumeNotEmpty(orgValue.trim(), "Parameter 'value' may not be empty");
		}
	}

	I_AD_Org retrieveOrg(Properties ctx, int adOrgId);

	default I_AD_Org getById(@NonNull final OrgId orgId)
	{
		return retrieveOrg(Env.getCtx(), orgId.getRepoId());
	}

	default I_AD_Org getById(int adOrgId)
	{
		return retrieveOrg(Env.getCtx(), adOrgId);
	}

	default I_AD_Org retrieveOrg(final int adOrgId)
	{
		return retrieveOrg(Env.getCtx(), adOrgId);
	}

	default String retrieveOrgName(final int adOrgId)
	{
		return retrieveOrgName(OrgId.ofRepoId(adOrgId));
	}

	default String retrieveOrgName(@NonNull final OrgId adOrgId)
	{
		final I_AD_Org org = getById(adOrgId);
		return org != null ? org.getName() : "<" + adOrgId.getRepoId() + ">";
	}

	I_AD_OrgInfo retrieveOrgInfo(Properties ctx, int adOrgId, String trxName);

	default I_AD_OrgInfo retrieveOrgInfo(int adOrgId)
	{
		return retrieveOrgInfo(Env.getCtx(), adOrgId, ITrx.TRXNAME_None);
	}

	WarehouseId getOrgWarehouseId(OrgId orgId);

	WarehouseId getOrgPOWarehouseId(OrgId orgId);

	WarehouseId getOrgDropshipWarehouseId(OrgId orgId);

	/**
	 * Search for the organization when the value is known
	 *
	 * @param ctx
	 * @param value
	 * @return AD_Org Object if the organization was found, null otherwise.
	 */
	I_AD_Org retrieveOrganizationByValue(Properties ctx, String value);

	List<I_AD_Org> retrieveClientOrgs(Properties ctx, int adClientId);

	default List<I_AD_Org> retrieveClientOrgs(final int adClientId)
	{
		return retrieveClientOrgs(Env.getCtx(), adClientId);
	}

	List<I_AD_Org> retrieveChildOrgs(Properties ctx, int parentOrgId, int adTreeOrgId);
}
