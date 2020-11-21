package de.metas.rest_api.ordercandidates.impl;

import de.metas.bpartner.BPGroupRepository;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.currency.CurrencyRepository;
import de.metas.greeting.GreetingRepository;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfo;
import de.metas.rest_api.bpartner.impl.BPartnerEndpointService;
import de.metas.rest_api.bpartner.impl.BpartnerRestController;
import de.metas.rest_api.bpartner.impl.JsonRequestConsolidateService;
import de.metas.rest_api.bpartner.impl.bpartnercomposite.JsonServiceFactory;
import de.metas.rest_api.bpartner.request.JsonRequestBPartner;
import de.metas.rest_api.bpartner.request.JsonRequestContact;
import de.metas.rest_api.bpartner.request.JsonRequestLocation;
import de.metas.rest_api.common.JsonExternalId;
import de.metas.rest_api.common.SyncAdvise;
import de.metas.rest_api.common.SyncAdvise.IfExists;
import de.metas.rest_api.common.SyncAdvise.IfNotExists;
import de.metas.rest_api.ordercandidates.request.JsonOrganization;
import de.metas.rest_api.ordercandidates.request.JsonRequestBPartnerLocationAndContact;
import de.metas.rest_api.utils.BPartnerQueryService;
import de.metas.security.PermissionService;
import de.metas.user.UserId;
import de.metas.user.UserRepository;
import de.metas.util.JSONObjectMapper;
import de.metas.util.Services;
import org.adempiere.ad.table.MockLogEntriesRepository;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.util.Env;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

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

	private JsonRequestBPartner jsonBPartner;

	private JsonRequestBPartnerLocationAndContact jsonBPartnerInfo;

	private JsonOrganization jsonOrganization;

	private JsonRequestLocation jsonBPartnerLocation;

	private I_C_Country countryRecord;

	@BeforeAll
	public static void beforeAll()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG, o -> JSONObjectMapper.forClass(Object.class).writeValueAsString(o));
	}

	@AfterAll
	public static void afterAll()
	{
		validateSnapshots();
	}

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		Services.registerService(IBPartnerBL.class, new BPartnerBL(new UserRepository()));
		SpringContextHolder.registerJUnitBean(new GreetingRepository());

		UserId loggedUserId = UserId.ofRepoId(1234567);
		Env.setLoggedUserId(Env.getCtx(), loggedUserId);

		countryRecord = newInstance(I_C_Country.class);
		countryRecord.setCountryCode("DE");
		saveRecord(countryRecord);

		// default bpartner group
		final I_C_BP_Group groupRecord = newInstance(I_C_BP_Group.class);
		groupRecord.setIsDefault(true);
		groupRecord.setName("DefaultGroup");
		InterfaceWrapperHelper.setValue(groupRecord, I_C_BP_Group.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH.getRepoId());
		saveRecord(groupRecord);

		// bpartnerRestController
		final BPartnerCompositeRepository bpartnerCompositeRepository = new BPartnerCompositeRepository(new MockLogEntriesRepository());
		final CurrencyRepository currencyRepository = new CurrencyRepository();
		final JsonServiceFactory jsonServiceFactory = new JsonServiceFactory(
				new JsonRequestConsolidateService(),
				new BPartnerQueryService(),
				bpartnerCompositeRepository,
				new BPGroupRepository(),
				new GreetingRepository(),
				currencyRepository);
		final BpartnerRestController bpartnerRestController = new BpartnerRestController(
				new BPartnerEndpointService(jsonServiceFactory),
				jsonServiceFactory,
				new JsonRequestConsolidateService());

		final PermissionService permissionService = Mockito.mock(PermissionService.class);
		masterdataProvider = MasterdataProvider.builder()
				.permissionService(permissionService)
				.bpartnerRestController(bpartnerRestController)
				.build();

		jsonBPartner = new JsonRequestBPartner();
		jsonBPartner.setName("jsonBPartner.name");
		jsonBPartner.setCode("jsonBPartner.code");

		jsonBPartnerLocation = new JsonRequestLocation();
		jsonBPartnerLocation.setCountryCode("DE");
		jsonBPartnerLocation.setExternalId(JsonExternalId.of("jsonBPartnerLocation.externalId"));

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
	void getCreateOrgId_createIfNotExists()
	{
		final IOrgDAO orgsRepo = Services.get(IOrgDAO.class);

		final OrgId orgId = masterdataProvider.getCreateOrgIdInTrx(jsonOrganization);

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

	/** Create a C_BPartner, and then add a location and a contact to it via masterDataProvider.*/
	@Test
	void getCreateBPartnerInfo()
	{
		final OrgId orgId = AdempiereTestHelper.createOrgWithTimeZone();

		final I_C_BP_Group bpGroupRecord = newInstance(I_C_BP_Group.class);
		bpGroupRecord.setName("bpGroup");
		saveRecord(bpGroupRecord);

		final I_C_BPartner bpartnerRecord = newInstance(I_C_BPartner.class);
		bpartnerRecord.setValue("jsonBPartner.code");
		bpartnerRecord.setName("jsonBPartner.name");
		bpartnerRecord.setC_BP_Group_ID(bpGroupRecord.getC_BP_Group_ID());
		saveRecord(bpartnerRecord);

		final JsonRequestBPartner jsonBPartner = new JsonRequestBPartner();
		jsonBPartner.setCode("jsonBPartner.code");
		jsonBPartner.setSyncAdvise(SyncAdvise.builder().ifNotExists(IfNotExists.FAIL).ifExists(IfExists.DONT_UPDATE).build());

		final JsonRequestLocation jsonBPartnerLocation = new JsonRequestLocation();
		jsonBPartnerLocation.setExternalId(JsonExternalId.of("externalId"));
		jsonBPartnerLocation.setName("Dr. Evil");
		jsonBPartnerLocation.setAddress1("Teufelgasse 1234");
		jsonBPartnerLocation.setBpartnerName("Ärztezentrum Gesundheitsquadrat");
		jsonBPartnerLocation.setCity("Düsselldorf");
		jsonBPartnerLocation.setPostal("54321");
		jsonBPartnerLocation.setCountryCode("DE");
		jsonBPartnerLocation.setSyncAdvise(SyncAdvise.builder().ifNotExists(IfNotExists.CREATE).ifExists(IfExists.DONT_UPDATE).build());

		final JsonRequestContact jsonContact = new JsonRequestContact();
		jsonContact.setExternalId(JsonExternalId.of("externalId"));
		jsonContact.setName("Dr. Evil");
		jsonContact.setFirstName("Dr.");
		jsonContact.setLastName("Evil");
		jsonContact.setPhone(null);
		jsonContact.setFax(null);
		jsonContact.setEmail(null);
		jsonContact.setSyncAdvise(SyncAdvise.builder().ifNotExists(IfNotExists.CREATE).ifExists(IfExists.DONT_UPDATE).build());

		final JsonRequestBPartnerLocationAndContact jsonBPartnerInfo = JsonRequestBPartnerLocationAndContact.builder()
				.syncAdvise(SyncAdvise.builder().ifNotExists(IfNotExists.CREATE).ifExists(IfExists.DONT_UPDATE).build())
				.bpartner(jsonBPartner)
				.location(jsonBPartnerLocation)
				.contact(jsonContact)
				.build();

		masterdataProvider.getCreateBPartnerInfoInTrx(jsonBPartnerInfo, true/* billTo */, orgId);
		assertThat(POJOLookupMap.get().getRecords(I_AD_User.class, l -> "externalId".equals(l.getExternalId()))).hasSize(1);
		assertThat(POJOLookupMap.get().getRecords(I_C_BPartner_Location.class, l -> "externalId".equals(l.getExternalId()))).hasSize(1);

		masterdataProvider.getCreateBPartnerInfoInTrx(jsonBPartnerInfo, true/* billTo */, orgId);
		assertThat(POJOLookupMap.get().getRecords(I_AD_User.class, l -> "externalId".equals(l.getExternalId()))).hasSize(1);
		assertThat(POJOLookupMap.get().getRecords(I_C_BPartner_Location.class, l -> "externalId".equals(l.getExternalId()))).hasSize(1);
	}

}
