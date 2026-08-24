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
import com.google.common.collect.ImmutableMap;
import de.metas.cache.CCache;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.ISysConfigBL;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.I_VATaxID_Config;
import org.springframework.stereotype.Repository;

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
	/**
	 * Governs the save-time and online-check format gate for an organisation that has <b>no</b>
	 * {@code VATaxID_Config} record. Has no effect on an organisation that has one — that record's own
	 * {@code IsFormatCheckEnabled} column governs instead. System-level only by design (no per-org
	 * override is part of this SysConfig's contract); see {@link #getByOrgId(OrgId)}.
	 */
	@VisibleForTesting
	public static final String SYSCONFIG_IsFormatCheckEnabledByDefault = "VATaxID_Config.IsFormatCheckEnabledByDefault";

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@NonNull private final CCache<OrgId, VATaxIDConfig> configsByOrgId = CCache.<OrgId, VATaxIDConfig>builder()
			.tableName(I_VATaxID_Config.Table_Name)
			.initialCapacity(10)
			.expireMinutes(CCache.EXPIREMINUTES_Never)
			// The synthesized no-record default (below) reads AD_SysConfig, so a value composed for a
			// cached org must also be dropped when that second table changes — see the
			// metasfresh-persistence-layer skill § "Don't manually CacheMgt.reset(...)" and its
			// WarehouseDAO.allWarehousePickingGroups precedent (a cache whose value is itself composed
			// from a second table, not merely indexed by it — same mechanism).
			.additionalTableNameToResetFor(I_AD_SysConfig.Table_Name)
			.build();

	@VisibleForTesting
	public static VATaxIDConfigRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(VATaxIDConfigRepository.class, VATaxIDConfigRepository::new);
	}

	/**
	 * @return the organisation's configuration, <b>never {@code null}</b>. With an active
	 * {@code VATaxID_Config} record: that record. Without one: a synthesized configuration whose
	 * {@link VATaxIDConfig#isFormatCheckEnabled()} follows {@link #SYSCONFIG_IsFormatCheckEnabledByDefault}
	 * (System-level, {@code Y} as shipped) and whose {@link VATaxIDConfig#isViesCheckEnabled()} is always
	 * {@code false} — that SysConfig governs the format half only. {@link VATaxIDConfig#getId()} is
	 * {@code null} there, as no record backs it.
	 *
	 * <p>The single place resolving that default: both save-time interceptors and
	 * {@code VATaxIDCheckService} call this and cannot resolve the "no config" case any other way, which is
	 * what stops them diverging on the same business question.
	 */
	@NonNull
	public VATaxIDConfig getByOrgId(@NonNull final OrgId orgId)
	{
		return configsByOrgId.getOrLoad(orgId, () -> retrieveByOrgId(orgId));
	}

	/**
	 * @return the recheck window of every organisation that has the online check switched ON, keyed by
	 * organisation. Organisations with no {@code VATaxID_Config} record are absent: no record means the
	 * check is off ({@link #synthesizeDefaultWithoutRecord()}), so there is nothing for a run to select.
	 *
	 * <p>Exists because the nightly run must enumerate the organisations it should sweep, which
	 * {@link #getByOrgId(OrgId)} — a lookup by a known org — cannot answer.
	 */
	@NonNull
	public ImmutableMap<OrgId, Integer> getRecheckAfterDaysByViesEnabledOrgId()
	{
		return queryBL.createQueryBuilder(I_VATaxID_Config.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_VATaxID_Config.COLUMNNAME_IsVIESCheckEnabled, true)
				.create()
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						record -> OrgId.ofRepoId(record.getAD_Org_ID()),
						I_VATaxID_Config::getRecheckAfterDays,
						(first, second) -> first));
	}

	@NonNull
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
			return synthesizeDefaultWithoutRecord();
		}

		return toVATaxIDConfig(record);
	}

	/**
	 * The configuration an organisation with no {@code VATaxID_Config} record effectively has:
	 * {@code recheckAfterDays} and {@code onServiceUnavailable} are unreachable while
	 * {@code viesCheckEnabled} is {@code false}; they carry the fail-open values rather than being left to
	 * look meaningful.
	 */
	@NonNull
	private VATaxIDConfig synthesizeDefaultWithoutRecord()
	{
		return VATaxIDConfig.builder()
				.formatCheckEnabled(sysConfigBL.getBooleanValue(SYSCONFIG_IsFormatCheckEnabledByDefault, true))
				.viesCheckEnabled(false)
				.recheckAfterDays(0)
				.onServiceUnavailable(VATaxIDOnServiceUnavailableAction.ServiceUnavailable)
				.build();
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
