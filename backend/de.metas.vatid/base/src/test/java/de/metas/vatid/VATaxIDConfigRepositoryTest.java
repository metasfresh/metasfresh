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

import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_VATaxID_Config;
import org.compiere.model.X_VATaxID_Config;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link VATaxIDConfigRepository}.
 *
 * Covers: a saved active {@code VATaxID_Config} record is read back with every field intact (format
 * check and VIES check can each be switched on/off per organisation, so both flags need to round-trip
 * independently), both {@link VATaxIDOnServiceUnavailableAction} values round-trip through
 * {@code OnServiceUnavailable}, an org with no record gets the synthesized SysConfig-backed default
 * rather than another org's config, an inactive record for the org is not returned (the "one active row
 * per org" contract), the synthesized default follows a live change of the
 * {@code VATaxID_Config.IsFormatCheckEnabledByDefault} SysConfig, and — the critical case — a config
 * already cached for a no-record org picks up a later SysConfig change rather than serving a stale value
 * forever (the cache's {@code additionalTableNameToResetFor(AD_SysConfig)} contract).
 */
class VATaxIDConfigRepositoryTest
{
	private static final OrgId ORG_WITH_CONFIG = OrgId.ofRepoId(1000001);
	private static final OrgId ORG_WITHOUT_CONFIG = OrgId.ofRepoId(1000002);
	private static final OrgId ORG_WITH_ONLY_INACTIVE_CONFIG = OrgId.ofRepoId(1000003);
	private static final OrgId ORG_WITH_FAIL_CLOSED_CONFIG = OrgId.ofRepoId(1000004);
	private static final OrgId ORG_WITHOUT_CONFIG_2 = OrgId.ofRepoId(1000005);
	private static final OrgId ORG_WITHOUT_CONFIG_3 = OrgId.ofRepoId(1000006);

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private VATaxIDConfigRepository vataxIDConfigRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		vataxIDConfigRepository = VATaxIDConfigRepository.newInstanceForUnitTesting();
	}

	private I_VATaxID_Config createConfigRecord(final OrgId orgId, final boolean isActive, final String onServiceUnavailable)
	{
		final I_VATaxID_Config record = InterfaceWrapperHelper.newInstance(I_VATaxID_Config.class);
		record.setAD_Org_ID(orgId.getRepoId());
		record.setIsActive(isActive);
		record.setIsFormatCheckEnabled(true);
		record.setIsVIESCheckEnabled(true);
		record.setRestApiBaseURL("https://ec.europa.eu/taxation_customs/vies/rest-api/ms/DE/vat/DE123456789");
		record.setRequesterMemberStateCode("DE");
		record.setRequesterNumber("123456789");
		record.setRecheckAfterDays(90);
		record.setOnServiceUnavailable(onServiceUnavailable);
		InterfaceWrapperHelper.saveRecord(record);
		return record;
	}

	@Test
	void getByOrgId_returnsTheActiveConfig_withEveryFieldIntact()
	{
		final I_VATaxID_Config record = createConfigRecord(ORG_WITH_CONFIG, true, X_VATaxID_Config.ONSERVICEUNAVAILABLE_ServiceUnavailable);

		final VATaxIDConfig config = vataxIDConfigRepository.getByOrgId(ORG_WITH_CONFIG);

		assertThat(config).isNotNull();
		assertThat(config.getId()).isEqualTo(VATaxIDConfigId.ofRepoId(record.getVATaxID_Config_ID()));
		assertThat(config.isFormatCheckEnabled()).isTrue();
		assertThat(config.isViesCheckEnabled()).isTrue();
		assertThat(config.getRestApiBaseURL()).isEqualTo("https://ec.europa.eu/taxation_customs/vies/rest-api/ms/DE/vat/DE123456789");
		assertThat(config.getRequesterMemberStateCode()).isEqualTo("DE");
		assertThat(config.getRequesterNumber()).isEqualTo("123456789");
		assertThat(config.getRecheckAfterDays()).isEqualTo(90);
		assertThat(config.getOnServiceUnavailable()).isEqualTo(VATaxIDOnServiceUnavailableAction.ServiceUnavailable);
		assertThat(config.getOnServiceUnavailable().toVATaxIDStatus()).isEqualTo(VATaxIDStatus.ServiceUnavailable);
	}

	@Test
	void getByOrgId_returnsTheActiveConfig_withTheFailClosedOnServiceUnavailableValue()
	{
		createConfigRecord(ORG_WITH_FAIL_CLOSED_CONFIG, true, X_VATaxID_Config.ONSERVICEUNAVAILABLE_Invalid);

		final VATaxIDConfig config = vataxIDConfigRepository.getByOrgId(ORG_WITH_FAIL_CLOSED_CONFIG);

		assertThat(config).isNotNull();
		assertThat(config.getOnServiceUnavailable()).isEqualTo(VATaxIDOnServiceUnavailableAction.Invalid);
		assertThat(config.getOnServiceUnavailable().toVATaxIDStatus()).isEqualTo(VATaxIDStatus.Invalid);
	}

	@Test
	void getByOrgId_returnsSynthesizedDefault_whenOrgHasNoConfigRecord()
	{
		createConfigRecord(ORG_WITH_CONFIG, true, X_VATaxID_Config.ONSERVICEUNAVAILABLE_ServiceUnavailable);

		final VATaxIDConfig config = vataxIDConfigRepository.getByOrgId(ORG_WITHOUT_CONFIG);

		// Never null, and not ORG_WITH_CONFIG's record: today's exact no-record behaviour is format check
		// on, VIES check off -- and there is no record to point at.
		assertThat(config).isNotNull();
		assertThat(config.getId()).isNull();
		assertThat(config.isFormatCheckEnabled()).isTrue(); // IsFormatCheckEnabledByDefault ships as System 'Y'
		assertThat(config.isViesCheckEnabled()).isFalse();
	}

	@Test
	void getByOrgId_ignoresAnInactiveRecord()
	{
		createConfigRecord(ORG_WITH_ONLY_INACTIVE_CONFIG, false, X_VATaxID_Config.ONSERVICEUNAVAILABLE_ServiceUnavailable);

		final VATaxIDConfig config = vataxIDConfigRepository.getByOrgId(ORG_WITH_ONLY_INACTIVE_CONFIG);

		assertThat(config).isNotNull();
		assertThat(config.getId()).isNull(); // the inactive record must not be surfaced -- this is the synthesized default
		assertThat(config.isFormatCheckEnabled()).isTrue();
	}

	@Test
	void getByOrgId_synthesizedDefault_followsSysConfigForFormatCheck_whenSetToN()
	{
		sysConfigBL.setValue(VATaxIDConfigRepository.SYSCONFIG_IsFormatCheckEnabledByDefault, false, ClientId.SYSTEM, OrgId.ANY);

		final VATaxIDConfig config = vataxIDConfigRepository.getByOrgId(ORG_WITHOUT_CONFIG);

		assertThat(config.isFormatCheckEnabled()).isFalse();
		assertThat(config.isViesCheckEnabled()).isFalse(); // the SysConfig governs only the format half
	}

	/**
	 * The cache-invalidation contract that actually matters: {@code configsByOrgId} has NO expiry, so once a
	 * no-record org's synthesized default is cached, ONLY {@code additionalTableNameToResetFor(AD_SysConfig)}
	 * can make a later SysConfig change visible. This test deliberately reads BEFORE changing the SysConfig —
	 * reversing that order would pass even if the cache never invalidated at all, proving nothing.
	 */
	@Test
	void getByOrgId_cacheInvalidatesOnSysConfigChange_forOrgWithNoConfigRecord()
	{
		// 1. Populate the cache for this org FIRST, under the shipped System default (Y).
		final VATaxIDConfig configBeforeChange = vataxIDConfigRepository.getByOrgId(ORG_WITHOUT_CONFIG_2);
		assertThat(configBeforeChange.isFormatCheckEnabled()).isTrue();

		// 2. ONLY THEN change the SysConfig the cached value was composed from.
		sysConfigBL.setValue(VATaxIDConfigRepository.SYSCONFIG_IsFormatCheckEnabledByDefault, false, ClientId.SYSTEM, OrgId.ANY);

		// 3. The next read for the SAME org must observe the new value -- if the cache were keyed only on
		// VATaxID_Config (not also on AD_SysConfig), this would still return the stale 'true' from step 1.
		final VATaxIDConfig configAfterChange = vataxIDConfigRepository.getByOrgId(ORG_WITHOUT_CONFIG_2);
		assertThat(configAfterChange.isFormatCheckEnabled()).isFalse();
	}

	/**
	 * Companion to the test above, same ordering discipline, opposite direction (N -> Y) with a DIFFERENT
	 * org id so the two cache-invalidation tests cannot mask each other via a shared cache entry.
	 */
	@Test
	void getByOrgId_cacheInvalidatesOnSysConfigChange_forOrgWithNoConfigRecord_reverseDirection()
	{
		sysConfigBL.setValue(VATaxIDConfigRepository.SYSCONFIG_IsFormatCheckEnabledByDefault, false, ClientId.SYSTEM, OrgId.ANY);

		// 1. Populate the cache FIRST, under the now-N SysConfig.
		final VATaxIDConfig configBeforeChange = vataxIDConfigRepository.getByOrgId(ORG_WITHOUT_CONFIG_3);
		assertThat(configBeforeChange.isFormatCheckEnabled()).isFalse();

		// 2. ONLY THEN flip the SysConfig back.
		sysConfigBL.setValue(VATaxIDConfigRepository.SYSCONFIG_IsFormatCheckEnabledByDefault, true, ClientId.SYSTEM, OrgId.ANY);

		// 3. The next read must observe the flip, not the value cached in step 1.
		final VATaxIDConfig configAfterChange = vataxIDConfigRepository.getByOrgId(ORG_WITHOUT_CONFIG_3);
		assertThat(configAfterChange.isFormatCheckEnabled()).isTrue();
	}
}
