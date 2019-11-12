package de.metas.rest_api.ordercandidates.impl;

import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.util.Env;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfo;
import de.metas.rest_api.bpartner.request.JsonRequestBPartner;
import de.metas.rest_api.bpartner.request.JsonRequestLocation;
import de.metas.rest_api.common.JsonExternalId;
import de.metas.rest_api.common.SyncAdvise;
import de.metas.rest_api.common.SyncAdvise.IfNotExists;
import de.metas.rest_api.ordercandidates.request.JsonOrganization;
import de.metas.rest_api.ordercandidates.request.JsonRequestBPartnerLocationAndContact;
import de.metas.rest_api.utils.PermissionService;
import de.metas.user.UserId;
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

	private JsonRequestBPartner jsonBPartner;

	private JsonRequestBPartnerLocationAndContact jsonBPartnerInfo;

	private JsonOrganization jsonOrganization;

	private JsonRequestLocation jsonBPartnerLocation;

	private I_C_Country countryRecord;

	@BeforeClass
	public static void beforeAll()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG, o -> JSONObjectMapper.forClass(Object.class).writeValueAsString(o));
	}

	@AfterClass
	public static void afterAll()
	{
		validateSnapshots();
	}

	@Before
	public void beforeEach()
	{
		// note: if i add mockito-junit-jupiter to the dependencies in order to do "@ExtendWith(MockitoExtension.class)",
		// then eclipse can't find my test methods anymore
		MockitoAnnotations.initMocks(this);

		AdempiereTestHelper.get().init();

		UserId loggedUserId = UserId.ofRepoId(1234567);
		Env.setLoggedUserId(Env.getCtx(), loggedUserId);

		countryRecord = newInstance(I_C_Country.class);
		countryRecord.setCountryCode("DE");
		saveRecord(countryRecord);

		masterdataProvider = MasterdataProvider.builder()
				.permissionService(permissionService)
				.build();

		jsonBPartner = JsonRequestBPartner.builder()
				.name("jsonBPartner.name")
				.code("jsonBPartner.code")
				.build();

		jsonBPartnerLocation = JsonRequestLocation.builder()
				.countryCode("DE")
				.externalId(JsonExternalId.of("jsonBPartnerLocation.externalId"))
				.build();

		jsonBPartnerInfo = JsonRequestBPartnerLocationAndContact.builder()
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
	public void getCreateOrgId_createIfNotExists()
	{
		final IOrgDAO orgsRepo = Services.get(IOrgDAO.class);

		final OrgId orgId = masterdataProvider.getCreateOrgId(jsonOrganization);

		// verify AD_Org
		final I_AD_Org orgRecord = orgsRepo.getById(orgId);
		assertThat(orgRecord.getValue()).isEqualTo("jsonOrganization.code");
		assertThat(orgRecord.getName()).isEqualTo("jsonOrganization.name");

		// verify AD_OrgInfo
		final OrgInfo orgInfo = orgsRepo.getOrgInfoById(orgId);
		assertThat(orgInfo.getOrgBPartnerLocationId()).isNotNull();

		final I_C_BPartner_Location orgBPLocation = Services.get(IBPartnerDAO.class).getBPartnerLocationById(orgInfo.getOrgBPartnerLocationId());
		assertThat(orgBPLocation.getExternalId()).isEqualTo(jsonBPartnerLocation.getExternalId().getValue());
		assertThat(orgBPLocation.getC_Location().getC_Country_ID()).isEqualTo(countryRecord.getC_Country_ID());

		// verify C_BPartner
		final I_C_BPartner bpartnerRecord = Services.get(IBPartnerDAO.class).retrieveOrgBPartner(Env.getCtx(), orgId.getRepoId(), I_C_BPartner.class, ITrx.TRXNAME_None);
		assertThat(bpartnerRecord.getValue()).isEqualTo("jsonBPartner.code");
		assertThat(bpartnerRecord.getName()).isEqualTo("jsonBPartner.name");
	}
}
