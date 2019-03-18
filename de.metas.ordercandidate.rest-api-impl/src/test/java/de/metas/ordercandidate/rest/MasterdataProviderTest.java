package de.metas.ordercandidate.rest;

import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.IOrgDAO;
import org.adempiere.service.OrgId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.util.Env;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.metas.adempiere.model.I_AD_OrgInfo;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.ordercandidate.rest.SyncAdvise.IfNotExists;
import de.metas.util.JSONObjectMapper;
import de.metas.util.Services;

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
	private MasterdataProvider masterdataProvider;

	@Mock
	private PermissionService permissionService;

	private JsonBPartner jsonBPartner;

	private JsonBPartnerInfo jsonBPartnerInfo;

	private JsonOrganization jsonOrganization;

	private JsonBPartnerLocation jsonBPartnerLocation;

	private I_C_Country countryRecord;

	@BeforeAll
	static void beforeAll()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG, o -> JSONObjectMapper.forClass(Object.class).writeValueAsString(o));
	}

	@AfterAll
	static void afterAll()
	{
		validateSnapshots();
	}

	@BeforeEach
	void beforeEach()
	{
		// note: if i add mockito-junit-jupiter to the dependencies in order to do "@ExtendWith(MockitoExtension.class)",
		// then eclipse can't find my test methods anymore
		MockitoAnnotations.initMocks(this);

		AdempiereTestHelper.get().init();

		countryRecord = newInstance(I_C_Country.class);
		countryRecord.setCountryCode("DE");
		saveRecord(countryRecord);

		masterdataProvider = MasterdataProvider
				.builder()
				.permissionService(permissionService)
				.build();

		jsonBPartner = JsonBPartner.builder()
				.name("jsonBPartner.name")
				.code("jsonBPartner.code")
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
				.name("jsonOrganization.name")
				.bpartner(jsonBPartnerInfo)
				.syncAdvise(SyncAdvise.builder().ifNotExists(IfNotExists.CREATE).build())
				.build();
	}

	@Test
	void getCreateOrgId_createIfNotExists()
	{
		final OrgId orgId = masterdataProvider.getCreateOrgId(jsonOrganization);

		// verify AD_Org
		final I_AD_Org orgRecord = load(orgId, I_AD_Org.class);
		assertThat(orgRecord.getValue()).isEqualTo("jsonOrganization.code");
		assertThat(orgRecord.getName()).isEqualTo("jsonOrganization.name");

		// verify AD_OrgInfo
		final I_AD_OrgInfo orgInfoRecord = create(Services.get(IOrgDAO.class).retrieveOrgInfo(orgId.getRepoId()), I_AD_OrgInfo.class);
		assertThat(orgInfoRecord.getOrgBP_Location_ID()).isGreaterThan(0);

		final I_C_BPartner_Location orgBPLocation = orgInfoRecord.getOrgBP_Location();
		assertThat(orgBPLocation.getExternalId()).isEqualTo(jsonBPartnerLocation.getExternalId());
		assertThat(orgBPLocation.getC_Location().getC_Country_ID()).isEqualTo(countryRecord.getC_Country_ID());

		// verify C_BPartner
		final I_C_BPartner bpartnerRecord = Services.get(IBPartnerDAO.class).retrieveOrgBPartner(Env.getCtx(), orgId.getRepoId(), I_C_BPartner.class, ITrx.TRXNAME_None);
		assertThat(bpartnerRecord.getValue()).isEqualTo("jsonBPartner.code");
		assertThat(bpartnerRecord.getName()).isEqualTo("jsonBPartner.name");
	}
}
