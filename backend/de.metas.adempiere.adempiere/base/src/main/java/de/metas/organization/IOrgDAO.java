package de.metas.organization;

import java.time.ZoneId;

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

import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_AD_Org;
import org.compiere.util.Env;

import de.metas.util.ISingletonService;
import lombok.NonNull;

public interface IOrgDAO extends ISingletonService
{
	void save(I_AD_Org orgRecord);

	ClientId getClientIdByOrgId(OrgId orgId);

	Optional<OrgId> retrieveOrgIdBy(OrgQuery orgQuery);

	@Deprecated
	I_AD_Org retrieveOrg(Properties ctx, int adOrgId);

	default I_AD_Org getById(@NonNull final OrgId orgId)
	{
		return retrieveOrg(Env.getCtx(), orgId.getRepoId());
	}

	default I_AD_Org getById(int adOrgId)
	{
		return retrieveOrg(Env.getCtx(), adOrgId);
	}

	default String retrieveOrgName(final int adOrgId)
	{
		return retrieveOrgName(OrgId.ofRepoIdOrNull(adOrgId));
	}

	default String retrieveOrgName(@Nullable final OrgId adOrgId)
	{
		if (adOrgId == null)
		{
			return "?";
		}
		else if (adOrgId.isAny())
		{
			return "*";
		}
		else
		{
			final I_AD_Org orgRecord = getById(adOrgId);
			return orgRecord != null ? orgRecord.getName() : "<" + adOrgId.getRepoId() + ">";
		}
	}

	default String retrieveOrgValue(final int adOrgId)
	{
		return retrieveOrgValue(OrgId.ofRepoIdOrNull(adOrgId));
	}

	default String retrieveOrgValue(@Nullable final OrgId adOrgId)
	{
		if (adOrgId == null)
		{
			return "?";
		}
		else if (adOrgId.isAny())
		{
			//return "*";
			return "0"; // "*" is the name of the "any" org, but it's org-value is 0
		}
		else
		{
			final I_AD_Org orgRecord = getById(adOrgId);
			return orgRecord != null ? orgRecord.getValue() : "<" + adOrgId.getRepoId() + ">";
		}
	}

	OrgInfo createOrUpdateOrgInfo(OrgInfoUpdateRequest request);

	OrgInfo getOrgInfoById(OrgId adOrgId);

	OrgInfo getOrgInfoByIdInTrx(OrgId adOrgId);

	WarehouseId getOrgWarehouseId(OrgId orgId);

	WarehouseId getOrgPOWarehouseId(OrgId orgId);

	WarehouseId getOrgDropshipWarehouseId(OrgId orgId);

	/**
	 * Search for the organization when the value is known
	 *
	 * @return AD_Org Object if the organization was found, null otherwise.
	 */
	I_AD_Org retrieveOrganizationByValue(Properties ctx, String value);

	List<I_AD_Org> retrieveClientOrgs(Properties ctx, int adClientId);

	default List<I_AD_Org> retrieveClientOrgs(final int adClientId)
	{
		return retrieveClientOrgs(Env.getCtx(), adClientId);
	}

	/** @return organization's time zone or system time zone; never returns null */
	ZoneId getTimeZone(OrgId orgId);
}
