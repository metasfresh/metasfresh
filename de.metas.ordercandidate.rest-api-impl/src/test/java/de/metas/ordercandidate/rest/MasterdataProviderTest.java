package de.metas.ordercandidate.rest;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.service.IOrgDAO;
import org.adempiere.service.OrgId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import de.metas.adempiere.model.I_AD_OrgInfo;
import de.metas.ordercandidate.rest.SyncAdvise.IfNotExists;
import de.metas.util.Services;
import mockit.Mocked;

/*
 * #%L
 * de.metas.ordercandidate.rest-api-impl
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class MasterdataProviderTest
{
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	private MasterdataProvider masterdataProvider;

	@Mocked
	private PermissionService permissionService;

	private JsonBPartner jsonBPartner;

	private JsonBPartnerInfo jsonBPartnerInfo;

	private JsonOrganization jsonOrganization;

	private JsonBPartnerLocation jsonBPartnerLocation;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final I_C_Country countryRecord = newInstance(I_C_Country.class);
		countryRecord.setCountryCode("DE");
		saveRecord(countryRecord);

		masterdataProvider = MasterdataProvider
				.builder()
				.permissionService(permissionService)
				.build();

		jsonBPartner = JsonBPartner.builder()
				.name("jsonBPartner.Name")
				.build();

		jsonBPartnerLocation = JsonBPartnerLocation.builder()
				.countryCode("DE")
				.externalId("jsonBPartnerLocation.externalId")
				.build();

		jsonBPartnerInfo = JsonBPartnerInfo.builder()
				.bpartner(jsonBPartner)
				.location(jsonBPartnerLocation)
				.syncAdvise(SyncAdvise.builder().ifNotExists(IfNotExists.CREATE).build())
				.build();

		jsonOrganization = JsonOrganization.builder()
				.code("jsonOrganization.code")
				.bpartner(jsonBPartnerInfo)
				.build();
	}

	@Test
	public void getCreateOrgId()
	{
		final OrgId orgId = masterdataProvider.getCreateOrgId(jsonOrganization);

		final I_AD_OrgInfo orgInfoRecord = create(Services.get(IOrgDAO.class).retrieveOrgInfo(orgId.getRepoId()), I_AD_OrgInfo.class);
		assertThat(orgInfoRecord.getOrgBP_Location_ID()).isGreaterThan(0);

		final I_C_BPartner_Location orgBPLocation = orgInfoRecord.getOrgBP_Location();

		assertThat(orgBPLocation.getExternalId()).isEqualTo(jsonBPartnerLocation.getExternalId());
	}
}
