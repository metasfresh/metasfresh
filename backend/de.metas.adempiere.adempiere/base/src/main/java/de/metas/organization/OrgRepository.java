/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.organization;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.organization.impl.OrgDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.springframework.stereotype.Repository;

@Repository
public class OrgRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	// Cache invalidates on AD_Org changes; we also read AD_OrgInfo.TimeZone, so invalidate on that too.
	private final CCache<Integer, OrgMap> cache = CCache.<Integer, OrgMap>builder()
			.tableName(I_AD_Org.Table_Name)
			.additionalTableNameToResetFor(I_AD_OrgInfo.Table_Name)
			.build();

	@VisibleForTesting
	public static OrgRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(OrgRepository.class, OrgRepository::new);
	}

	@NonNull
	public Org getById(@NonNull final OrgId orgId)
	{
		return getOrgMap().getById(orgId);
	}

	private OrgMap getOrgMap()
	{
		return cache.getOrLoadNonNull(0, this::retrieveOrgMap);
	}

	@NonNull
	private OrgMap retrieveOrgMap()
	{
		final ImmutableList<I_AD_Org> orgRecords = queryBL.createQueryBuilder(I_AD_Org.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.listImmutable(I_AD_Org.class);

		final ImmutableMap<OrgId, OrgInfo> orgInfosByOrgId = retrieveOrgInfosByOrgId(orgRecords);

		final ImmutableList<Org> orgs = orgRecords.stream()
				.map(record -> fromRecord(record, orgInfosByOrgId))
				.collect(ImmutableList.toImmutableList());
		return new OrgMap(orgs);
	}

	@NonNull
	private ImmutableMap<OrgId, OrgInfo> retrieveOrgInfosByOrgId(@NonNull final ImmutableList<I_AD_Org> orgRecords)
	{
		final ImmutableSet<OrgId> orgIds = orgRecords.stream()
				.map(record -> OrgId.ofRepoId(record.getAD_Org_ID()))
				.collect(ImmutableSet.toImmutableSet());
		if (orgIds.isEmpty())
		{
			return ImmutableMap.of();
		}
		return queryBL.createQueryBuilder(I_AD_OrgInfo.class)
				.addInArrayFilter(I_AD_OrgInfo.COLUMNNAME_AD_Org_ID, orgIds)
				.create()
				.stream()
				.map(OrgDAO::toOrgInfo)
				.collect(ImmutableMap.toImmutableMap(
						OrgInfo::getOrgId,
						info -> info));
	}

	private static Org fromRecord(
			@NonNull final I_AD_Org record,
			@NonNull final ImmutableMap<OrgId, OrgInfo> orgInfosByOrgId)
	{
		final OrgId orgId = OrgId.ofRepoId(record.getAD_Org_ID());
		final OrgInfo orgInfo = orgInfosByOrgId.get(orgId);
		if (orgInfo == null)
		{
			throw new AdempiereException("Active AD_Org with id=" + orgId
					+ " has no AD_OrgInfo row; expected one for every org (production seed and the WebUI org-creation flow both create it).");
		}
		return Org.builder()
				.orgId(orgId)
				.name(record.getName())
				.value(record.getValue())
				.orgInfo(orgInfo)
				.build();
	}

	//
	//
	//

	private static final class OrgMap
	{
		private final ImmutableMap<OrgId, Org> byId;

		OrgMap(@NonNull final ImmutableList<Org> list)
		{
			this.byId = Maps.uniqueIndex(list, Org::getOrgId);
		}

		@NonNull
		Org getById(@NonNull final OrgId id)
		{
			final Org org = byId.get(id);
			if (org == null)
			{
				throw new AdempiereException("Org not found by ID: " + id);
			}
			return org;
		}
	}
}
