/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.rest_api.v2.bpartner;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPGroupRepository;
import de.metas.bpartner.BPartnerBankAccountId;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.composite.BPartnerBankAccount;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerCompositeAndContactId;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.bpartner.service.BPartnerContactQuery;
import de.metas.bpartner.service.BPartnerCreditLimitRepository;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.bpartner.user.role.repository.UserRoleRepository;
import de.metas.common.bpartner.v2.request.JsonRequestBPartner;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestBankAccountUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestBankAccountsUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestComposite;
import de.metas.common.bpartner.v2.request.JsonRequestContact;
import de.metas.common.bpartner.v2.request.JsonRequestContactUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestContactUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsertItem;
import de.metas.common.bpartner.v2.response.JsonResponseBPartnerCompositeUpsert;
import de.metas.common.bpartner.v2.response.JsonResponseBPartnerCompositeUpsertItem;
import de.metas.common.bpartner.v2.response.JsonResponseComposite;
import de.metas.common.bpartner.v2.response.JsonResponseCompositeList;
import de.metas.common.bpartner.v2.response.JsonResponseContact;
import de.metas.common.bpartner.v2.response.JsonResponseLocation;
import de.metas.common.bpartner.v2.response.JsonResponseUpsert;
import de.metas.common.bpartner.v2.response.JsonResponseUpsertItem;
import de.metas.common.product.v2.response.JsonResponseProductBPartner;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.util.time.SystemTime;
import de.metas.common.util.time.TimeSource;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.externalreference.ExternalBusinessKey;
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.externalreference.ExternalReferenceTypes;
import de.metas.externalreference.ExternalSystems;
import de.metas.externalreference.bpartner.BPartnerExternalReferenceType;
import de.metas.externalreference.model.I_S_ExternalReference;
import de.metas.externalreference.rest.v2.ExternalReferenceRestControllerService;
import de.metas.greeting.GreetingRepository;
import de.metas.incoterms.repository.IncotermsRepository;
import de.metas.job.JobService;
import de.metas.rest_api.utils.BPartnerQueryService;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonServiceFactory;
import de.metas.rest_api.v2.bpartner.creditLimit.CreditLimitService;
import de.metas.sectionCode.SectionCodeRepository;
import de.metas.sectionCode.SectionCodeService;
import de.metas.title.TitleRepository;
import de.metas.user.UserId;
import de.metas.user.UserRepository;
import de.metas.util.JSONObjectMapper;
import de.metas.util.Services;
import de.metas.util.lang.UIDStringUtil;
import de.metas.util.web.exception.MissingResourceException;
import de.metas.vertical.healthcare.alberta.bpartner.AlbertaBPartnerCompositeService;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.MockLogEntriesRepository;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJONextIdSuppliers;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_CreditLimit;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.InputStream;
import java.time.ZoneId;
import java.util.Optional;
import java.util.TimeZone;

import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.AD_ORG_ID;
import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.AD_USER_EXTERNAL_ID;
import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.AD_USER_ID;
import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.BP_EXTERNAL_VERSION;
import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.BP_GROUP_RECORD_NAME;
import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.C_BBPARTNER_LOCATION_ID;
import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.C_BPARTNER_EXTERNAL_ID;
import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.C_BPARTNER_ID;
import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.C_BPARTNER_LOCATION_EXTERNAL_ID;
import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.C_BPARTNER_LOCATION_GLN;
import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.C_BP_GROUP_ID;
import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.C_CONTACT_EXTERNAL_ID;
import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.EXTERNAL_SYSTEM_NAME;
import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.createBPartnerData;
import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.createExternalReference;
import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.getExternalReference;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith({AdempiereTestWatcher.class, SnapshotExtension.class})
class BpartnerRestControllerTest
{
	private BpartnerRestController bpartnerRestController;

	private BPartnerCompositeRepository bpartnerCompositeRepository;
	private CurrencyRepository currencyRepository;
	private ExternalReferenceRestControllerService externalReferenceRestControllerService;
	private SectionCodeRepository sectionCodeRepository;
	private IncotermsRepository incotermsRepository;
	private BPartnerCreditLimitRepository bPartnerCreditLimitRepository;

	private Expect expect;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		POJOLookupMap.setNextIdSupplier(POJONextIdSuppliers.newPerTableSequence());

		SpringContextHolder.registerJUnitBean(new GreetingRepository());

		final ExternalReferenceTypes externalReferenceTypes = new ExternalReferenceTypes();

		final ExternalSystems externalSystems = new ExternalSystems();

		final ExternalReferenceRepository externalReferenceRepository =
				new ExternalReferenceRepository(Services.get(IQueryBL.class), externalSystems, externalReferenceTypes);

		externalReferenceRestControllerService = new ExternalReferenceRestControllerService(externalReferenceRepository, new ExternalSystems(), new ExternalReferenceTypes());

		final BPartnerBL partnerBL = new BPartnerBL(new UserRepository());
		//Services.registerService(IBPartnerBL.class, partnerBL);

		bPartnerCreditLimitRepository = new BPartnerCreditLimitRepository();
		bpartnerCompositeRepository = new BPartnerCompositeRepository(partnerBL, new MockLogEntriesRepository(), new UserRoleRepository(), bPartnerCreditLimitRepository);
		currencyRepository = new CurrencyRepository();

		sectionCodeRepository = new SectionCodeRepository();

		incotermsRepository = new IncotermsRepository();

		final JsonServiceFactory jsonServiceFactory = new JsonServiceFactory(
				new JsonRequestConsolidateService(),
				new BPartnerQueryService(),
				bpartnerCompositeRepository,
				new BPGroupRepository(),
				new GreetingRepository(),
				new TitleRepository(),
				currencyRepository,
				JobService.newInstanceForUnitTesting(),
				externalReferenceRestControllerService,
				new SectionCodeService(sectionCodeRepository),
				incotermsRepository,
				Mockito.mock(AlbertaBPartnerCompositeService.class),
				bPartnerCreditLimitRepository);

		bpartnerRestController = new BpartnerRestController(
				new BPartnerEndpointService(jsonServiceFactory),
				jsonServiceFactory,
				new JsonRequestConsolidateService(),
				new CreditLimitService(bPartnerCreditLimitRepository, jsonServiceFactory));

		final I_C_BP_Group bpGroupRecord = newInstance(I_C_BP_Group.class);
		bpGroupRecord.setC_BP_Group_ID(C_BP_GROUP_ID);
		bpGroupRecord.setName(BP_GROUP_RECORD_NAME);
		saveRecord(bpGroupRecord);

		createBPartnerData(0);

		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_Org_ID, AD_ORG_ID);
	}

	@Test
	void retrieveBPartner_ext()

	{
		final String bPartnerExternalIdentifier = String.join("-", "ext", EXTERNAL_SYSTEM_NAME, C_BPARTNER_EXTERNAL_ID);

		// invoke the method under test
		final ResponseEntity<JsonResponseComposite> result = bpartnerRestController.retrieveBPartner(bPartnerExternalIdentifier);

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonResponseComposite resultBody = result.getBody();

		expect.serializer("orderedJson").toMatchSnapshot(resultBody);
	}

	@Test
	void retrieveBPartner_id()
	{
		// invoke the method under test
		final ResponseEntity<JsonResponseComposite> result = bpartnerRestController.retrieveBPartner(Integer.toString(C_BPARTNER_ID));

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonResponseComposite resultBody = result.getBody();

		expect.serializer("orderedJson").toMatchSnapshot(resultBody);
	}

	@Test
	void retrieveBPartner_gln()
	{
		final String bPartnerExternalIdentifier = String.join("-", "gln", C_BPARTNER_LOCATION_GLN);

		// invoke the method under test
		final ResponseEntity<JsonResponseComposite> result = bpartnerRestController.retrieveBPartner(bPartnerExternalIdentifier);

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonResponseComposite resultBody = result.getBody();

		expect.serializer("orderedJson").toMatchSnapshot(resultBody);
	}

	@Test
	void retrieveBPartnerContact_id()
	{
		final String bPartnerExternalIdentifier = String.join("-", "ext", EXTERNAL_SYSTEM_NAME, C_BPARTNER_EXTERNAL_ID);
		final String bPartnerContactExternalIdentifier = Integer.toString(AD_USER_ID);

		// invoke the method under test
		final ResponseEntity<JsonResponseContact> result = bpartnerRestController.retrieveBPartnerContact(
				bPartnerExternalIdentifier,
				bPartnerContactExternalIdentifier);

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonResponseContact resultBody = result.getBody();

		expect.serializer("orderedJson").toMatchSnapshot(resultBody);
	}

	@Test
	void retrieveBPartnerContact_ext()
	{
		final String bPartnerExternalIdentifier = String.join("-", "ext", EXTERNAL_SYSTEM_NAME, C_BPARTNER_EXTERNAL_ID);
		final String bPartnerContactExternalIdentifier = String.join("-", "ext", EXTERNAL_SYSTEM_NAME, C_CONTACT_EXTERNAL_ID);

		// invoke the method under test
		final ResponseEntity<JsonResponseContact> result = bpartnerRestController.retrieveBPartnerContact(
				bPartnerExternalIdentifier,
				bPartnerContactExternalIdentifier);

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonResponseContact resultBody = result.getBody();

		expect.serializer("orderedJson").toMatchSnapshot(resultBody);
	}

	@Test
	void retrieveBPartnerLocation_id()
	{
		final String bPartnerExternalIdentifier = String.join("-", "ext", EXTERNAL_SYSTEM_NAME, C_BPARTNER_EXTERNAL_ID);
		final String bPartnerLocationExternalIdentifier = String.valueOf(C_BBPARTNER_LOCATION_ID);

		// invoke the method under test
		final ResponseEntity<JsonResponseLocation> result = bpartnerRestController.retrieveBPartnerLocation(
				bPartnerExternalIdentifier,
				bPartnerLocationExternalIdentifier);

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonResponseLocation resultBody = result.getBody();

		expect.serializer("orderedJson").toMatchSnapshot(resultBody);
	}

	@Test
	void retrieveBPartnerLocation_ext()
	{
		final String bPartnerExternalIdentifier = String.join("-", "ext", EXTERNAL_SYSTEM_NAME, C_BPARTNER_EXTERNAL_ID);
		final String bPartnerLocationExternalIdentifier = String.join("-", "ext", EXTERNAL_SYSTEM_NAME, C_BPARTNER_LOCATION_EXTERNAL_ID);

		// invoke the method under test
		final ResponseEntity<JsonResponseLocation> result = bpartnerRestController.retrieveBPartnerLocation(
				bPartnerExternalIdentifier,
				bPartnerLocationExternalIdentifier);

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonResponseLocation resultBody = result.getBody();

		expect.serializer("orderedJson").toMatchSnapshot(resultBody);
	}

	@Test
	void retrieveBPartnerLocation_gln()
	{
		final String bPartnerExternalIdentifier = String.join("-", "ext", EXTERNAL_SYSTEM_NAME, C_BPARTNER_EXTERNAL_ID);
		final String bPartnerLocationExternalIdentifier = String.join("-", "gln", C_BPARTNER_LOCATION_GLN);

		// invoke the method under test
		final ResponseEntity<JsonResponseLocation> result = bpartnerRestController.retrieveBPartnerLocation(
				bPartnerExternalIdentifier,
				bPartnerLocationExternalIdentifier);

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonResponseLocation resultBody = result.getBody();

		expect.serializer("orderedJson").toMatchSnapshot(resultBody);
	}

	@Test
	void createOrUpdateBPartner_create()
	{
		final int initialBPartnerRecordCount = POJOLookupMap.get().getRecords(I_C_BPartner.class).size();
		final int initialUserRecordCount = POJOLookupMap.get().getRecords(I_AD_User.class).size();
		final int initialBPartnerLocationRecordCount = POJOLookupMap.get().getRecords(I_C_BPartner_Location.class).size();
		final int initialLocationRecordCount = POJOLookupMap.get().getRecords(I_C_Location.class).size();
		final int initialBpartnerCreditLimitRecordCount = POJOLookupMap.get().getRecords(I_C_BPartner_CreditLimit.class).size();

		createCountryRecord("CH");
		createCountryRecord("DE");

		final String bPartnerIdentifier = "ext-" + EXTERNAL_SYSTEM_NAME + "-b1_" + C_BPARTNER_EXTERNAL_ID;
		final JsonRequestComposite bpartnerComposite = MockedDataUtil.createMockBPartner(bPartnerIdentifier);

		assertThat(bpartnerComposite.getContactsNotNull().getRequestItems()).hasSize(2); // guard
		assertThat(bpartnerComposite.getLocationsNotNull().getRequestItems()).hasSize(2);// guard
		assertThat(bpartnerComposite.getCreditLimitsNotNull().getRequestItems()).hasSize(1);// guard

		final JsonRequestBPartner bpartner = bpartnerComposite.getBpartner();
		bpartner.setGroup(BP_GROUP_RECORD_NAME);

		final JsonRequestBPartnerUpsertItem requestItem = JsonRequestBPartnerUpsertItem.builder()
				.bpartnerIdentifier(bPartnerIdentifier)
				.externalVersion(BP_EXTERNAL_VERSION)
				.bpartnerComposite(bpartnerComposite)
				.build();

		final JsonRequestBPartnerUpsert bpartnerUpsertRequest = JsonRequestBPartnerUpsert.builder()
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.requestItem(requestItem)
				.build();

		SystemTime.setTimeSource( new TimeSource()
		{
			@Override
			public long millis()
			{
				return 1561134560;
			}

			@Override
			public ZoneId zoneId()
			{
				return TimeZone.getTimeZone("GMT").toZoneId();
			}
		});

		Env.setLoggedUserId(Env.getCtx(), UserId.ofRepoId(BPartnerRecordsUtil.AD_USER_ID));

		// invoke the method under test
		final ResponseEntity<JsonResponseBPartnerCompositeUpsert> result = bpartnerRestController.createOrUpdateBPartner(bpartnerUpsertRequest);

		final JsonMetasfreshId metasfreshId = assertUpsertResultOK(result, bPartnerIdentifier);
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(metasfreshId.getValue());

		final BPartnerComposite persistedResult = bpartnerCompositeRepository.getById(bpartnerId);
		expect.serializer("orderedJson").toMatchSnapshot(persistedResult);

		assertThat(POJOLookupMap.get().getRecords(I_C_BPartner.class)).hasSize(initialBPartnerRecordCount + 1);
		assertThat(POJOLookupMap.get().getRecords(I_AD_User.class)).hasSize(initialUserRecordCount + 2);
		assertThat(POJOLookupMap.get().getRecords(I_C_BPartner_Location.class)).hasSize(initialBPartnerLocationRecordCount + 2);
		assertThat(POJOLookupMap.get().getRecords(I_C_Location.class)).hasSize(initialLocationRecordCount + 2);
		assertThat(POJOLookupMap.get().getRecords(I_C_BPartner_CreditLimit.class)).hasSize(initialBpartnerCreditLimitRecordCount + 1);
	}

	/**
	 * Verifies that you can identify a bpartner by {@code ext-ALBERTA-1234567} and then updated its code.
	 */
	@Test
	void createOrUpdateBPartner_update_builder()
	{
		JsonRequestBPartner partner = new JsonRequestBPartner();
		partner.setCompanyName("otherCompanyName");

		partner.setCode("other12345");
		final JsonRequestBPartnerUpsert bpartnerUpsertRequest = JsonRequestBPartnerUpsert.builder()
				.syncAdvise(SyncAdvise.builder()
									.ifExists(SyncAdvise.IfExists.UPDATE_MERGE)
									.ifNotExists(SyncAdvise.IfNotExists.CREATE)
									.build())
				.requestItem(JsonRequestBPartnerUpsertItem.builder()
									 .bpartnerIdentifier("ext-ALBERTA-1234567")
									 .bpartnerComposite(JsonRequestComposite.builder()
																.bpartner(partner)
																.build())
									 .build())
				.build();

		createOrUpdateBPartner_update_performTest(bpartnerUpsertRequest);
	}

	/**
	 * Verifies that you can identify a bpartner by {@code ext-ALBERTA-1234567} and then updated its code.
	 */
	@Test
	void createOrUpdateBPartner_update_withExternalBusinessKey()
	{
		JsonRequestBPartner partner = new JsonRequestBPartner();
		partner.setCompanyName("otherCompanyName");

		partner.setCode("ext-ALBERTA-esb");
		final JsonRequestBPartnerUpsert bpartnerUpsertRequest = JsonRequestBPartnerUpsert.builder()
				.syncAdvise(SyncAdvise.builder()
									.ifExists(SyncAdvise.IfExists.UPDATE_MERGE)
									.ifNotExists(SyncAdvise.IfNotExists.CREATE)
									.build())
				.requestItem(JsonRequestBPartnerUpsertItem.builder()
									 .bpartnerIdentifier("ext-ALBERTA-1234567")
									 .bpartnerComposite(JsonRequestComposite.builder()
																.bpartner(partner)
																.build())
									 .build())
				.build();

		createOrUpdateBPartner_update_performTest(bpartnerUpsertRequest);
	}

	/**
	 * Like {@link #createOrUpdateBPartner_update_builder()}, but get the rest file from a JSON string
	 */
	@Test
	void createOrUpdateBPartner_update_json()
	{
		final JsonRequestBPartnerUpsert bpartnerUpsertRequest = loadUpsertRequest("BpartnerRestControllerTest_Simple_BPartner.json");
		final I_C_BPartner bpartnerRecord = createOrUpdateBPartner_update_performTest(bpartnerUpsertRequest);

		assertThat(bpartnerRecord.getBPartner_Parent_ID()).isLessThanOrEqualTo(0);
	}

	/**
	 * Update a bpartner and insert a location at the same time.
	 */
	@Test
	void createOrUpdateBPartner_Update_BPartner_Name_Insert_Location()
	{
		SystemTime.setTimeSource(() -> 1563553074); // Fri, 19 Jul 2019 16:17:54 GMT
		Env.setLoggedUserId(Env.getCtx(), UserId.ofRepoId(BPartnerRecordsUtil.AD_USER_ID));

		final JsonRequestBPartnerUpsert bpartnerUpsertRequest = loadUpsertRequest("BPartnerRestControllerTest_Update_BPartner_Name_Insert_Location.json");

		assertThat(bpartnerUpsertRequest.getRequestItems()).hasSize(1);
		assertThat(bpartnerUpsertRequest.getRequestItems().get(0).getBpartnerComposite().getLocationsNotNull().getRequestItems()).hasSize(1);

		final I_C_BPartner bpartnerRecord = newInstance(I_C_BPartner.class);
		bpartnerRecord.setC_BPartner_ID(12345);
		bpartnerRecord.setAD_Org_ID(AD_ORG_ID);
		bpartnerRecord.setName("bpartnerRecord.name");
		bpartnerRecord.setValue("12345");
		bpartnerRecord.setCompanyName("bpartnerRecord.companyName");
		bpartnerRecord.setC_BP_Group_ID(C_BP_GROUP_ID);
		saveRecord(bpartnerRecord);

		final RecordCounts initialCounts = new RecordCounts();

		// invoke the method under test
		bpartnerRestController.createOrUpdateBPartner(bpartnerUpsertRequest);

		initialCounts.assertBPartnerCountChangedBy(0);
		initialCounts.assertUserCountChangedBy(0);
		initialCounts.assertLocationCountChangedBy(1);

		// verify that the bpartner-record was updated
		refresh(bpartnerRecord);
		assertThat(bpartnerRecord.getValue()).isEqualTo("12345"); // shall be unchanged
		assertThat(bpartnerRecord.getName()).isEqualTo("bpartnerRecord.name_updated");

		// use the rest controller to get the json that we can then verify
		final ResponseEntity<JsonResponseComposite> result = bpartnerRestController.retrieveBPartner("12345");
		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonResponseComposite resultBody = result.getBody();
		expect.serializer("orderedJson").toMatchSnapshot(resultBody);

		// finally, also make sure that if we repeat the same invocation, no new location record is created
		final RecordCounts countsBefore2ndInvocation = new RecordCounts();
		bpartnerRestController.createOrUpdateBPartner(bpartnerUpsertRequest);
		countsBefore2ndInvocation.assertCountsUnchanged();
	}

	@Test
	void createOrUpdateBPartner_update_C_BPartner_Value_NoSuchPartner()
	{
		final JsonRequestBPartnerUpsert bpartnerUpsertRequest = loadUpsertRequest("BpartnerRestControllerTest_update_C_BPartner_ExternalReference.json");

		final I_C_BPartner bpartnerRecord = newInstance(I_C_BPartner.class);
		bpartnerRecord.setAD_Org_ID(AD_ORG_ID);
		bpartnerRecord.setName("bpartnerRecord.name");
		bpartnerRecord.setValue("12345nosuchvalue");
		bpartnerRecord.setCompanyName("bpartnerRecord.companyName");
		bpartnerRecord.setC_BP_Group_ID(C_BP_GROUP_ID);
		saveRecord(bpartnerRecord);

		// invoke the method under test
		assertThatThrownBy(() -> bpartnerRestController.createOrUpdateBPartner(bpartnerUpsertRequest))
				.isInstanceOf(MissingResourceException.class)
				.hasMessageContaining("bpartner");
	}

	/**
	 * Verifies that if an upsert request which contains two locations that identify the same C_BPartner_Location record, then they are applied one after another.
	 */
	@Test
	void createOrUpdateBPartner_duplicate_location()
	{
		final JsonRequestBPartnerUpsert bpartnerUpsertRequest = loadUpsertRequest("BpartnerRestControllerTest_duplicate_location.json");

		// invoke the method under test
		bpartnerRestController.createOrUpdateBPartner(bpartnerUpsertRequest);

		final I_C_BPartner_Location bpartnerLocationRecord = load(C_BBPARTNER_LOCATION_ID, I_C_BPartner_Location.class);
		assertThat(bpartnerLocationRecord.isActive()).isFalse(); // from the second JSONLocation

		final I_C_Location locationRecord = bpartnerLocationRecord.getC_Location();
		assertThat(locationRecord.getAddress1()).isEqualTo("address1_metasfreshId"); // from the first JSONLocation
		assertThat(locationRecord.getAddress2()).isEqualTo("address2_extId"); // from the second JSONLocation
	}

	private JsonRequestBPartnerUpsert loadUpsertRequest(final String jsonFileName)
	{
		final InputStream stream = getClass().getClassLoader().getResourceAsStream("de/metas/rest_api/bpartner/impl/v2/" + jsonFileName);
		assertThat(stream).isNotNull();
		final JsonRequestBPartnerUpsert bpartnerUpsertRequest = JSONObjectMapper.forClass(JsonRequestBPartnerUpsert.class).readValue(stream);
		return bpartnerUpsertRequest;
	}

	private JsonMetasfreshId assertUpsertResultOK(
			@NonNull final ResponseEntity<JsonResponseBPartnerCompositeUpsert> result,
			@NonNull final String bpartnerIdentifier)
	{
		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);

		final JsonResponseBPartnerCompositeUpsert resultBody = result.getBody();
		assertThat(resultBody.getResponseItems()).hasSize(1);

		final JsonResponseBPartnerCompositeUpsertItem responseCompositeItem = resultBody.getResponseItems().get(0);
		assertThat(responseCompositeItem.getResponseBPartnerItem().getIdentifier()).isEqualTo(bpartnerIdentifier);

		return responseCompositeItem.getResponseBPartnerItem().getMetasfreshId();
	}

	private I_C_BPartner createOrUpdateBPartner_update_performTest(@NonNull final JsonRequestBPartnerUpsert bpartnerUpsertRequest)
	{
		final String initialValue = "bpartnerRecord.value";
		final I_C_BPartner bpartnerRecord = newInstance(I_C_BPartner.class);
		bpartnerRecord.setAD_Org_ID(AD_ORG_ID);
		bpartnerRecord.setName("bpartnerRecord.name");
		bpartnerRecord.setValue(initialValue);
		bpartnerRecord.setCompanyName("bpartnerRecord.companyName");
		bpartnerRecord.setC_BP_Group_ID(C_BP_GROUP_ID);
		bpartnerRecord.setBPartner_Parent_ID(123); // in one test this shall be updated to null
		saveRecord(bpartnerRecord);

		createExternalReference("1234567", "BPartner", bpartnerRecord.getC_BPartner_ID(), BPartnerRecordsUtil.ALBERTA_EXTERNAL_SYSTEM_CONFIG_ID, true);

		final RecordCounts initialCounts = new RecordCounts();

		// invoke the method under test
		final ResponseEntity<JsonResponseBPartnerCompositeUpsert> result = bpartnerRestController.createOrUpdateBPartner(bpartnerUpsertRequest);

		initialCounts.assertCountsUnchanged();

		assertThat(result.getBody().getResponseItems()).hasSize(1);
		final JsonResponseBPartnerCompositeUpsertItem jsonResponseCompositeUpsertItem = result.getBody().getResponseItems().get(0);

		assertThat(jsonResponseCompositeUpsertItem.getResponseBPartnerItem().getIdentifier()).isEqualTo("ext-ALBERTA-1234567");
		assertThat(jsonResponseCompositeUpsertItem.getResponseBPartnerItem().getMetasfreshId().getValue()).isEqualTo(bpartnerRecord.getC_BPartner_ID());

		// verify that the bpartner-record was updated
		refresh(bpartnerRecord);
		assertThat(bpartnerRecord.getCompanyName()).isEqualTo("otherCompanyName");

		final ExternalBusinessKey externalBusinessKey =
				ExternalBusinessKey.of(bpartnerUpsertRequest.getRequestItems().get(0).getBpartnerComposite().getBpartner().getCode());

		if (externalBusinessKey.getType().equals(ExternalBusinessKey.Type.VALUE))
		{
			assertThat(bpartnerRecord.getValue()).isEqualTo(externalBusinessKey.asValue());
		}
		else if (externalBusinessKey.getType().equals(ExternalBusinessKey.Type.EXTERNAL_REFERENCE))
		{
			assertThat(bpartnerRecord.getValue()).isEqualTo(initialValue);
			initialCounts.assertExternalReferencesCountChangedBy(1);
			final I_S_ExternalReference externalReference = getExternalReference(externalBusinessKey.asExternalValueAndSystem().getValue(),
																				 BPartnerExternalReferenceType.BPARTNER_VALUE.getCode());

			assertThat(externalReference.getRecord_ID()).isEqualTo(bpartnerRecord.getC_BPartner_ID());
		}
		return bpartnerRecord;
	}

	@Value
	private static class RecordCounts
	{
		int initialBPartnerRecordCount;
		int initialUserRecordCount;
		int initialBPartnerLocationRecordCount;
		int initialLocationRecordCount;
		int initialExternalReferencesRecordCount;

		private RecordCounts()
		{
			initialBPartnerRecordCount = POJOLookupMap.get().getRecords(I_C_BPartner.class).size();
			initialUserRecordCount = POJOLookupMap.get().getRecords(I_AD_User.class).size();
			initialBPartnerLocationRecordCount = POJOLookupMap.get().getRecords(I_C_BPartner_Location.class).size();
			initialLocationRecordCount = POJOLookupMap.get().getRecords(I_C_Location.class).size();
			initialExternalReferencesRecordCount = POJOLookupMap.get().getRecords(I_S_ExternalReference.class).size();
		}

		private void assertCountsUnchanged()
		{
			assertBPartnerCountChangedBy(0);
			assertUserCountChangedBy(0);
			assertLocationCountChangedBy(0);
		}

		private void assertUserCountChangedBy(final int offset)
		{
			assertThat(POJOLookupMap.get().getRecords(I_AD_User.class)).hasSize(initialUserRecordCount + offset);
		}

		private void assertLocationCountChangedBy(final int offset)
		{
			assertThat(POJOLookupMap.get().getRecords(I_C_BPartner_Location.class)).hasSize(initialBPartnerLocationRecordCount + offset);
			assertThat(POJOLookupMap.get().getRecords(I_C_Location.class)).hasSize(initialLocationRecordCount + offset);
		}

		private void assertBPartnerCountChangedBy(final int offset)
		{
			assertThat(POJOLookupMap.get().getRecords(I_C_BPartner.class)).hasSize(initialBPartnerRecordCount + offset);
		}

		public void assertExternalReferencesCountChangedBy(final int offset)
		{
			assertThat(POJOLookupMap.get().getRecords(I_S_ExternalReference.class)).hasSize(initialExternalReferencesRecordCount + offset);
		}
	}

	private void createCountryRecord(@NonNull final String countryCode)
	{
		final I_C_Country deCountryRecord = newInstance(I_C_Country.class);
		deCountryRecord.setCountryCode(countryCode);
		saveRecord(deCountryRecord);
	}

	@Test
	void createOrUpdateContact_create()
	{
		final JsonRequestContactUpsertItem jsonContact = MockedDataUtil.createMockContact("newContact");
		final String bPartnerIdentifier = "ext-" + EXTERNAL_SYSTEM_NAME + "-" + C_BPARTNER_EXTERNAL_ID;

		SystemTime.setTimeSource(() -> 1561134560); // Fri, 21 Jun 2019 16:29:20 GMT
		Env.setLoggedUserId(Env.getCtx(), UserId.ofRepoId(BPartnerRecordsUtil.AD_USER_ID));

		// invoke the method under test
		final ResponseEntity<JsonResponseUpsert> result = bpartnerRestController.createOrUpdateContact(
				bPartnerIdentifier,
				JsonRequestContactUpsert.builder().requestItem(jsonContact).build());

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);

		final JsonResponseUpsert response = result.getBody();
		assertThat(response.getResponseItems()).hasSize(1);
		final JsonResponseUpsertItem responseItem = response.getResponseItems().get(0);
		assertThat(responseItem.getIdentifier()).isEqualTo(jsonContact.getContactIdentifier());

		final JsonMetasfreshId metasfreshId = responseItem.getMetasfreshId();

		final BPartnerContactQuery bpartnerContactQuery = BPartnerContactQuery.builder().userId(UserId.ofRepoId(metasfreshId.getValue())).build();
		final Optional<BPartnerCompositeAndContactId> optContactIdAndBPartner = bpartnerCompositeRepository.getByContact(bpartnerContactQuery);
		assertThat(optContactIdAndBPartner).isPresent();

		final BPartnerContactId resultContactId = optContactIdAndBPartner.get().getBpartnerContactId();
		assertThat(resultContactId.getRepoId()).isEqualTo(metasfreshId.getValue());

		final BPartnerComposite persistedResult = optContactIdAndBPartner.get().getBpartnerComposite();
		expect.serializer("orderedJson").toMatchSnapshot(persistedResult);
	}

	@Test
	void createOrUpdateContact_update_extContactIdentifier()
	{
		final BPartnerComposite persistedResult = perform_createOrUpdateContact_update("ext-" + EXTERNAL_SYSTEM_NAME + "-" + AD_USER_EXTERNAL_ID);
		expect.serializer("orderedJson").toMatchSnapshot(persistedResult);
	}

	@Test
	void createOrUpdateContact_update_idContactIdentifier()
	{
		final BPartnerComposite persistedResult = perform_createOrUpdateContact_update(Integer.toString(AD_USER_ID));
		expect.serializer("orderedJson").toMatchSnapshot(persistedResult);
	}

	private BPartnerComposite perform_createOrUpdateContact_update(@NonNull final String contactIdentifier)
	{
		final JsonRequestContact jsonContact = new JsonRequestContact();
		jsonContact.setName("jsonContact.name-UPDATED");
		jsonContact.setCode("jsonContact.code-UPDATED");

		SystemTime.setTimeSource(() -> 1561134560); // Fri, 21 Jun 2019 16:29:20 GMT
		Env.setLoggedUserId(Env.getCtx(), UserId.ofRepoId(BPartnerRecordsUtil.AD_USER_ID));

		// invoke the method under test
		final ResponseEntity<JsonResponseUpsert> result = bpartnerRestController.createOrUpdateContact(
				"ext-" + EXTERNAL_SYSTEM_NAME + "-" + C_BPARTNER_EXTERNAL_ID,
				JsonRequestContactUpsert.builder().requestItem(JsonRequestContactUpsertItem
																	   .builder()
																	   .contactIdentifier(contactIdentifier)
																	   .contact(jsonContact)
																	   .build()).build());

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);

		final JsonResponseUpsert response = result.getBody();
		assertThat(response.getResponseItems()).hasSize(1);
		final JsonResponseUpsertItem responseItem = response.getResponseItems().get(0);
		assertThat(responseItem.getIdentifier()).isEqualTo(contactIdentifier);

		final JsonMetasfreshId metasfreshId = responseItem.getMetasfreshId();

		final BPartnerContactQuery bpartnerContactQuery = BPartnerContactQuery.builder().userId(UserId.ofRepoId(metasfreshId.getValue())).build();
		final Optional<BPartnerCompositeAndContactId> optContactIdAndBPartner = bpartnerCompositeRepository.getByContact(bpartnerContactQuery);
		assertThat(optContactIdAndBPartner).isPresent();

		final BPartnerContactId resultContactId = optContactIdAndBPartner.get().getBpartnerContactId();
		assertThat(resultContactId.getRepoId()).isEqualTo(metasfreshId.getValue());

		final BPartnerComposite persistedResult = optContactIdAndBPartner.get().getBpartnerComposite();
		return persistedResult;
	}

	@Test
	void createOrUpdateLocation_create()
	{
		createCountryRecord("DE");
		final JsonRequestLocationUpsertItem jsonLocation = MockedDataUtil.createMockLocation("newLocation", "DE");

		SystemTime.setTimeSource(() -> 1561134560); // Fri, 21 Jun 2019 16:29:20 GMT
		Env.setLoggedUserId(Env.getCtx(), UserId.ofRepoId(BPartnerRecordsUtil.AD_USER_ID));

		// invoke the method under test
		final ResponseEntity<JsonResponseUpsert> result = bpartnerRestController.createOrUpdateLocation(
				"ext-" + EXTERNAL_SYSTEM_NAME + "-" + C_BPARTNER_EXTERNAL_ID,
				JsonRequestLocationUpsert.builder().requestItem(jsonLocation).build());

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);

		final JsonResponseUpsert response = result.getBody();
		assertThat(response.getResponseItems()).hasSize(1);
		final JsonResponseUpsertItem responseItem = response.getResponseItems().get(0);

		assertThat(responseItem.getIdentifier()).isEqualTo(jsonLocation.getLocationIdentifier());

		final JsonMetasfreshId metasfreshId = responseItem.getMetasfreshId();

		final BPartnerQuery query = BPartnerQuery.builder().bPartnerId(BPartnerId.ofRepoId(C_BPARTNER_ID)).build();
		final ImmutableList<BPartnerComposite> persistedPage = bpartnerCompositeRepository.getByQuery(query);

		assertThat(persistedPage).hasSize(1);

		final BPartnerComposite persistedResult = persistedPage.get(0);
		final Optional<BPartnerLocation> persistedLocation = persistedResult.extractLocation(BPartnerLocationId.ofRepoId(persistedResult.getBpartner().getId(), metasfreshId.getValue()));
		assertThat(persistedLocation).isPresent();

		assertThat(persistedLocation.get().getId().getRepoId()).isEqualTo(metasfreshId.getValue());

		expect.serializer("orderedJson").toMatchSnapshot(persistedResult);
	}

	@Test
	void createOrUpdateBankAccount_create()
	{
		final ResponseEntity<JsonResponseUpsert> result = bpartnerRestController.createOrUpdateBankAccount(
				String.valueOf(C_BPARTNER_ID),
				JsonRequestBankAccountsUpsert.builder()
						.requestItem(JsonRequestBankAccountUpsertItem.builder()
											 .iban("iban-1")
											 .currencyCode("EUR")
											 .build())
						.build());

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);
		final JsonResponseUpsert response = result.getBody();
		assertThat(response.getResponseItems()).hasSize(1);
		final JsonResponseUpsertItem responseItem = response.getResponseItems().get(0);
		assertThat(responseItem.getIdentifier()).isEqualTo("iban-1");
		assertThat(responseItem.getMetasfreshId()).isNotNull();

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(C_BPARTNER_ID);
		final BPartnerComposite bpartnerComposite = bpartnerCompositeRepository.getById(bpartnerId);

		final BPartnerBankAccount bankAccount = bpartnerComposite.getBankAccountByIBAN("iban-1").get();
		assertThat(bankAccount.getId()).isEqualTo(BPartnerBankAccountId.ofRepoId(bpartnerId, responseItem.getMetasfreshId().getValue()));
		assertThat(bankAccount.getIban()).isEqualTo("iban-1");
		assertThat(bankAccount.getCurrencyId()).isEqualTo(currencyRepository.getCurrencyIdByCurrencyCode(CurrencyCode.ofThreeLetterCode("EUR")));
		assertThat(bankAccount.isActive()).isTrue();
	}

	@Test
	void retrieveBPartnersSince()
	{

		final I_AD_SysConfig sysConfigRecord = newInstance(I_AD_SysConfig.class);
		sysConfigRecord.setName(BPartnerEndpointService.SYSCFG_BPARTNER_PAGE_SIZE);
		sysConfigRecord.setValue("2");
		saveRecord(sysConfigRecord);

		createBPartnerData(1);
		createBPartnerData(2);
		createBPartnerData(3);
		createBPartnerData(4);

		SystemTime.setTimeSource(() -> 1561014385); // Thu, 20 Jun 2019 07:06:25 GMT
		Env.setLoggedUserId(Env.getCtx(), UserId.ofRepoId(BPartnerRecordsUtil.AD_USER_ID));
		UIDStringUtil.setRandomUUIDSource(() -> "e57d6ba2-e91e-4557-8fc7-cb3c0acfe1f1");

		// invoke the method under test
		final ResponseEntity<JsonResponseCompositeList> page1 = bpartnerRestController.retrieveBPartnersSince(0L, null);

		assertThat(page1.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonResponseCompositeList page1Body = page1.getBody();
		assertThat(page1Body.getItems()).hasSize(2);

		final String page2Id = page1Body.getPagingDescriptor().getNextPage();
		assertThat(page2Id).isNotEmpty();

		final ResponseEntity<JsonResponseCompositeList> page2 = bpartnerRestController.retrieveBPartnersSince(null, page2Id);

		assertThat(page2.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonResponseCompositeList page2Body = page2.getBody();
		assertThat(page2Body.getItems()).hasSize(2);

		final String page3Id = page2Body.getPagingDescriptor().getNextPage();
		assertThat(page3Id).isNotEmpty();

		final ResponseEntity<JsonResponseCompositeList> page3 = bpartnerRestController.retrieveBPartnersSince(null, page3Id);

		assertThat(page3.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonResponseCompositeList page3Body = page3.getBody();
		assertThat(page3Body.getItems()).hasSize(1);

		assertThat(page3Body.getPagingDescriptor().getNextPage()).isNull();

		expect.scenario("page1").serializer("orderedJson").toMatchSnapshot(page1Body);
		expect.scenario("page2").serializer("orderedJson").toMatchSnapshot(page2Body);
		expect.scenario("page3").serializer("orderedJson").toMatchSnapshot(page3Body);
	}

	@Test
	void retrieveBPartnersProducts()
	{
		createBPartnerData(1);

		final I_C_BPartner_Product bPartnerProductRecord = newInstance(I_C_BPartner_Product.class);
		bPartnerProductRecord.setC_BPartner_ID(21);
		bPartnerProductRecord.setM_Product_ID(10);
		saveRecord(bPartnerProductRecord);

		final ResponseEntity<JsonResponseProductBPartner> page1 = bpartnerRestController.retrieveBPartnerProducts(String.valueOf(bPartnerProductRecord.getC_BPartner_ID()));
		assertThat(page1.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

		final JsonResponseProductBPartner responseProductBPartner = page1.getBody();
		assertThat(responseProductBPartner).isNotNull();
		assertThat(responseProductBPartner.getBPartnerProducts()).hasSize(1);

		expect.serializer("orderedJson").toMatchSnapshot(responseProductBPartner);
	}
}
