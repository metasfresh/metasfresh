/*
 * #%L
 * de.metas.vatid
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

package de.metas.vatid;

import com.google.common.annotations.VisibleForTesting;
import de.metas.cache.CCache;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_VATaxID_Config;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

/**
 * Repository Tables: {@code VATaxID_Config}.
 *
 * <p>Repository Cluster: sole owner of {@code VATaxID_Config}.
 *
 * <p>Reads the single active per-organisation VAT-ID check configuration (one active row per
 * {@code AD_Org_ID}, enforced by a DB partial unique index — see
 * {@code metasfresh-persistence-layer} skill § "Repository Query Defaults").
 */
@Repository
public class VATaxIDConfigRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final CCache<OrgId, VATaxIDConfig> configsByOrgId =
			CCache.newCache(I_VATaxID_Config.Table_Name, 10, CCache.EXPIREMINUTES_Never);

	@VisibleForTesting
	public static VATaxIDConfigRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(VATaxIDConfigRepository.class, VATaxIDConfigRepository::new);
	}

	/**
	 * @return the active {@code VATaxID_Config} for the given org, or {@code null} if that org has none.
	 *
	 * <p><b>Contract for the {@code null} case:</b> {@code REQUIREMENTS.md} § 3 states
	 * <em>"An organisation with no {@code VATaxID_Config} record keeps today's behaviour exactly: format
	 * check on, VIES check off"</em> — backed by the {@code VATaxID_Config.IsFormatCheckEnabledByDefault}
	 * SysConfig. Applying that default is deliberately <b>not</b> done here: this repository is a thin
	 * query layer over one table, whereas the default is a business rule with its own SysConfig lookup,
	 * shared by several future callers (the save-time interceptor, the nightly recheck process, tax
	 * determination) that do not exist yet. Resolving it once, in a single service-layer place, is a
	 * later task's responsibility. <b>Every current and future caller of this method that must show
	 * "no config" behaviour MUST apply that exact default itself</b> until such a service exists.
	 */
	@Nullable
	public VATaxIDConfig getByOrgId(@NonNull final OrgId orgId)
	{
		return configsByOrgId.getOrLoad(orgId, () -> retrieveByOrgId(orgId));
	}

	@Nullable
	private VATaxIDConfig retrieveByOrgId(@NonNull final OrgId orgId)
	{
		final I_VATaxID_Config record = queryBL
				.createQueryBuilder(I_VATaxID_Config.class)
				.addEqualsFilter(I_VATaxID_Config.COLUMNNAME_AD_Org_ID, orgId)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_VATaxID_Config.class);

		if (record == null)
		{
			return null;
		}

		return toVATaxIDConfig(record);
	}

	@NonNull
	private static VATaxIDConfig toVATaxIDConfig(@NonNull final I_VATaxID_Config record)
	{
		return VATaxIDConfig.builder()
				.id(VATaxIDConfigId.ofRepoId(record.getVATaxID_Config_ID()))
				.formatCheckEnabled(record.isFormatCheckEnabled())
				.viesCheckEnabled(record.isVIESCheckEnabled())
				.restApiBaseURL(record.getRestApiBaseURL())
				.requesterMemberStateCode(record.getRequesterMemberStateCode())
				.requesterNumber(record.getRequesterNumber())
				.recheckAfterDays(record.getRecheckAfterDays())
				.onServiceUnavailable(VATaxIDOnServiceUnavailableAction.ofCode(record.getOnServiceUnavailable()))
				.build();
	}
}
