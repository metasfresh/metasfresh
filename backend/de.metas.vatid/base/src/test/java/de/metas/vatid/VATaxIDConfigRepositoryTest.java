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
import org.adempiere.model.InterfaceWrapperHelper;
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
 * {@code OnServiceUnavailable}, an org with no record returns {@code null} rather than another org's
 * config, and an inactive record for the org is not returned (the "one active row per org" contract).
 */
class VATaxIDConfigRepositoryTest
{
	private static final OrgId ORG_WITH_CONFIG = OrgId.ofRepoId(1000001);
	private static final OrgId ORG_WITHOUT_CONFIG = OrgId.ofRepoId(1000002);
	private static final OrgId ORG_WITH_ONLY_INACTIVE_CONFIG = OrgId.ofRepoId(1000003);
	private static final OrgId ORG_WITH_FAIL_CLOSED_CONFIG = OrgId.ofRepoId(1000004);

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
	void getByOrgId_returnsNull_whenOrgHasNoConfigRecord()
	{
		createConfigRecord(ORG_WITH_CONFIG, true, X_VATaxID_Config.ONSERVICEUNAVAILABLE_ServiceUnavailable);

		assertThat(vataxIDConfigRepository.getByOrgId(ORG_WITHOUT_CONFIG)).isNull();
	}

	@Test
	void getByOrgId_ignoresAnInactiveRecord()
	{
		createConfigRecord(ORG_WITH_ONLY_INACTIVE_CONFIG, false, X_VATaxID_Config.ONSERVICEUNAVAILABLE_ServiceUnavailable);

		assertThat(vataxIDConfigRepository.getByOrgId(ORG_WITH_ONLY_INACTIVE_CONFIG)).isNull();
	}
}
