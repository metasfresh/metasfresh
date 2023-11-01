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
import de.metas.bpartner.BPGroupRepository;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.bpartner.service.BPartnerCreditLimitRepository;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.bpartner.user.role.repository.UserRoleRepository;
import de.metas.common.bpartner.v2.request.JsonRequestContact;
import de.metas.common.bpartner.v2.request.JsonRequestContactUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestContactUpsertItem;
import de.metas.common.bpartner.v2.response.JsonResponseContact;
import de.metas.common.bpartner.v2.response.JsonResponseContactList;
import de.metas.common.bpartner.v2.response.JsonResponseUpsert;
import de.metas.common.bpartner.v2.response.JsonResponseUpsertItem.SyncOutcome;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.rest_api.v2.SyncAdvise.IfExists;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyRepository;
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.externalreference.ExternalReferenceTypes;
import de.metas.externalreference.ExternalSystems;
import de.metas.externalreference.rest.v2.ExternalReferenceRestControllerService;
import de.metas.greeting.GreetingRepository;
import de.metas.i18n.TranslatableStrings;
import de.metas.incoterms.repository.IncotermsRepository;
import de.metas.job.JobService;
import de.metas.rest_api.utils.BPartnerQueryService;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonServiceFactory;
import de.metas.sectionCode.SectionCodeRepository;
import de.metas.sectionCode.SectionCodeService;
import de.metas.title.TitleRepository;
import de.metas.user.UserId;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import de.metas.util.lang.UIDStringUtil;
import de.metas.vertical.healthcare.alberta.bpartner.AlbertaBPartnerCompositeService;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.MockLogEntriesRepository;
import org.adempiere.ad.table.RecordChangeLogEntry;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJONextIdSuppliers;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BP_Group;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.AD_ORG_ID;
import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.AD_USER_EXTERNAL_ID;
import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.AD_USER_EXTERNAL_ID_NEW;
import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.AD_USER_ID;
import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.BP_GROUP_RECORD_NAME;
import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.C_BPARTNER_ID;
import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.C_BP_GROUP_ID;
import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.createBPartnerData;
import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.resetTimeSource;
import static de.metas.rest_api.v2.bpartner.BPartnerRecordsUtil.setupTimeSource;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

@ExtendWith({ AdempiereTestWatcher.class, SnapshotExtension.class })
class ContactRestControllerTest
{
	private ContactRestController contactRestController;

	private BPartnerCompositeRepository bpartnerCompositeRepository;

	private MockLogEntriesRepository recordChangeLogRepository;

	private SectionCodeRepository sectionCodeRepository;

	private IncotermsRepository incotermsRepository;

	private Expect expect;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		POJOLookupMap.setNextIdSupplier(POJONextIdSuppliers.newPerTableSequence());
		Env.setLoggedUserId(Env.getCtx(), UserId.ofRepoId(BPartnerRecordsUtil.AD_USER_ID));

		final BPartnerBL partnerBL = new BPartnerBL(new UserRepository());
		//Services.registerService(IBPartnerBL.class, partnerBL);
		SpringContextHolder.registerJUnitBean(new GreetingRepository());

		recordChangeLogRepository = new MockLogEntriesRepository();

		final ExternalReferenceRepository externalReferenceRepository =
				new ExternalReferenceRepository(Services.get(IQueryBL.class), new ExternalSystems(), new ExternalReferenceTypes());

		final ExternalReferenceRestControllerService externalReferenceRestControllerService =
				new ExternalReferenceRestControllerService(externalReferenceRepository, new ExternalSystems(), new ExternalReferenceTypes());

		bpartnerCompositeRepository = new BPartnerCompositeRepository(partnerBL, recordChangeLogRepository, new UserRoleRepository(), new BPartnerCreditLimitRepository());

		sectionCodeRepository = new SectionCodeRepository();

		incotermsRepository = new IncotermsRepository();
		final JsonServiceFactory jsonServiceFactory = new JsonServiceFactory(
				new JsonRequestConsolidateService(),
				new BPartnerQueryService(),
				bpartnerCompositeRepository,
				new BPGroupRepository(),
				new GreetingRepository(),
				new TitleRepository(),
				new CurrencyRepository(),
				JobService.newInstanceForUnitTesting(),
				externalReferenceRestControllerService,
				Mockito.mock(AlbertaBPartnerCompositeService.class),
				new SectionCodeService(sectionCodeRepository),
				incotermsRepository,
				new BPartnerCreditLimitRepository(),
				new JsonGreetingService(new GreetingRepository(), Mockito.mock(ExternalReferenceRestControllerService.class)));

		contactRestController = new ContactRestController(
				new BPartnerEndpointService(jsonServiceFactory),
				jsonServiceFactory,
				new JsonRequestConsolidateService());

		final I_C_BP_Group bpGroupRecord = newInstance(I_C_BP_Group.class);
		bpGroupRecord.setC_BP_Group_ID(C_BP_GROUP_ID);
		bpGroupRecord.setName(BP_GROUP_RECORD_NAME);
		saveRecord(bpGroupRecord);

		createBPartnerData(0);

		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_Org_ID, AD_ORG_ID);
	}

	@Test
	void retrieveContactsSince()
	{
		final I_AD_SysConfig sysConfigRecord = newInstance(I_AD_SysConfig.class);
		sysConfigRecord.setName(BPartnerEndpointService.SYSCFG_BPARTNER_PAGE_SIZE);
		sysConfigRecord.setValue("2");
		saveRecord(sysConfigRecord);

		createBPartnerData(1);
		createBPartnerData(2);
		createBPartnerData(3);
		createBPartnerData(4);

		setupTimeSource();
		// set some AD_Users' CreatedBy and UpdatedBy to -1 to check if this is handeled gracefully too
		final I_AD_User userRecord1 = load(AD_USER_ID + 1, I_AD_User.class);
		InterfaceWrapperHelper.setValue(userRecord1, I_AD_User.COLUMNNAME_CreatedBy, -1);
		saveRecord(userRecord1);
		final I_AD_User userRecord2 = load(AD_USER_ID + 2, I_AD_User.class);
		InterfaceWrapperHelper.setValue(userRecord2, I_AD_User.COLUMNNAME_UpdatedBy, -1);
		saveRecord(userRecord2);
		final I_AD_User userRecord3 = load(AD_USER_ID + 3, I_AD_User.class);
		InterfaceWrapperHelper.setValue(userRecord3, I_AD_User.COLUMNNAME_CreatedBy, -1);
		InterfaceWrapperHelper.setValue(userRecord3, I_AD_User.COLUMNNAME_UpdatedBy, -1);
		saveRecord(userRecord3);
		resetTimeSource();

		SystemTime.setTimeSource(() -> 1561014385); // Thu, 20 Jun 2019 07:06:25 GMT
		UIDStringUtil.setRandomUUIDSource(() -> "e57d6ba2-e91e-4557-8fc7-cb3c0acfe1f1");

		// invoke the method under test
		final ResponseEntity<JsonResponseContactList> page1 = contactRestController.retrieveContactsSince(0L, null);

		assertThat(page1.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonResponseContactList page1Body = page1.getBody();
		assertThat(page1Body.getContacts()).hasSize(2);

		assertThat(page1Body.getPagingDescriptor().getResultTimestamp()).isEqualTo(1561014385);

		final String page2Id = page1Body.getPagingDescriptor().getNextPage();
		assertThat(page2Id).isNotEmpty();

		final ResponseEntity<JsonResponseContactList> page2 = contactRestController.retrieveContactsSince(null, page2Id);

		assertThat(page2.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonResponseContactList page2Body = page2.getBody();
		assertThat(page2Body.getContacts()).hasSize(2);

		assertThat(page2Body.getPagingDescriptor().getResultTimestamp()).isEqualTo(1561014385);

		final String page3Id = page2Body.getPagingDescriptor().getNextPage();
		assertThat(page3Id).isNotEmpty();

		final ResponseEntity<JsonResponseContactList> page3 = contactRestController.retrieveContactsSince(null, page3Id);

		assertThat(page3.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonResponseContactList page3Body = page3.getBody();
		assertThat(page3Body.getContacts()).hasSize(1);

		assertThat(page3Body.getPagingDescriptor().getNextPage()).isNull();
		assertThat(page3Body.getPagingDescriptor().getResultTimestamp()).isEqualTo(1561014385);

		expect.scenario("page1").serializer("orderedJson").toMatchSnapshot(page1Body);
		expect.scenario("page2").serializer("orderedJson").toMatchSnapshot(page2Body);
		expect.scenario("page3").serializer("orderedJson").toMatchSnapshot(page3Body);
	}

	@Test
	void retrieveContact_ext()
	{
		// invoke the method under test
		final ResponseEntity<JsonResponseContact> result = contactRestController.retrieveContact("ext-" + "Other" + "-" + AD_USER_EXTERNAL_ID);

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonResponseContact resultBody = result.getBody();

		expect.serializer("orderedJson").toMatchSnapshot(resultBody);
	}

	@Test
	void retrieveContact_id()
	{
		// invoke the method under test
		final ResponseEntity<JsonResponseContact> result = contactRestController.retrieveContact(Integer.toString(AD_USER_ID));

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonResponseContact resultBody = result.getBody();

		expect.serializer("orderedJson").toMatchSnapshot(resultBody);
	}

	@Test
	void retrieveContact_id_unspecified_CreatedByUpdatedBy()
	{
		setupTimeSource();
		final I_AD_User userRecord = load(AD_USER_ID, I_AD_User.class);
		InterfaceWrapperHelper.setValue(userRecord, I_AD_User.COLUMNNAME_CreatedBy, -1);
		InterfaceWrapperHelper.setValue(userRecord, I_AD_User.COLUMNNAME_UpdatedBy, -1);
		saveRecord(userRecord);

		recordChangeLogRepository.add(TableRecordReference.of(userRecord), RecordChangeLogEntry.builder()
				.changedByUserId(null)
				.changedTimestamp(SystemTime.asInstant())
				.columnName(I_AD_User.COLUMNNAME_Name)
				.columnDisplayName(TranslatableStrings.constant("columnDisplayName"))
				.valueOld("valueOld")
				.valueNew("valueNew")
				.build());
		resetTimeSource();

		// invoke the method under test
		final ResponseEntity<JsonResponseContact> result = contactRestController.retrieveContact(Integer.toString(AD_USER_ID));

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonResponseContact resultBody = result.getBody();

		assertThat(resultBody.getChangeInfo().getChangeLogs()).isNotEmpty();

		expect.serializer("orderedJson").toMatchSnapshot(resultBody);
	}

	@Test
	void createOrUpdateContact_create()
	{
		final JsonRequestContact jsonContact = new JsonRequestContact();
		jsonContact.setName("jsonContact.name");
		jsonContact.setCode("jsonContact.code");
		jsonContact.setMetasfreshBPartnerId(JsonMetasfreshId.of(C_BPARTNER_ID));

		final String contactIdentifier = "ext-Other-" + AD_USER_EXTERNAL_ID_NEW;

		final JsonRequestContactUpsert upsertRequest = JsonRequestContactUpsert.builder()
				.syncAdvise(SyncAdvise.builder().ifExists(IfExists.UPDATE_MERGE).build())
				.requestItem(JsonRequestContactUpsertItem
						.builder()
						.contactIdentifier(contactIdentifier)
						.contact(jsonContact)
						.build())
				.build();

		SystemTime.setTimeSource(() -> 1561134560); // Fri, 21 Jun 2019 16:29:20 GMT

		final ResponseEntity<JsonResponseUpsert> result = contactRestController.createOrUpdateContact(upsertRequest);

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);
		final JsonResponseUpsert resultBody = result.getBody();

		assertThat(resultBody.getResponseItems()).hasSize(1);
		assertThat(resultBody.getResponseItems().get(0).getIdentifier()).isEqualTo(contactIdentifier);
		assertThat(resultBody.getResponseItems().get(0).getSyncOutcome()).isEqualTo(SyncOutcome.CREATED);

		final JsonMetasfreshId insertedMetasfreshId = resultBody.getResponseItems().get(0).getMetasfreshId();

		final BPartnerContactId insertedContactId = BPartnerContactId.ofRepoId(C_BPARTNER_ID, insertedMetasfreshId.getValue());

		final BPartnerComposite persistedResult = bpartnerCompositeRepository.getById(insertedContactId.getBpartnerId());
		final Optional<BPartnerContact> insertedContact = persistedResult.extractContact(insertedContactId);

		expect.serializer("orderedJson").toMatchSnapshot(insertedContact.get());
	}

	@Test
	void createOrUpdateContact_update_extContactIdentifier()
	{
		final BPartnerContact updateContact = perform_createOrUpdateContact_update("ext-" + "Other" + "-" + AD_USER_EXTERNAL_ID);
		expect.serializer("orderedJson").toMatchSnapshot(updateContact);
	}

	@Test
	void createOrUpdateContact_update_idContactIdentifier()
	{
		final BPartnerContact updateContact = perform_createOrUpdateContact_update(Integer.toString(AD_USER_ID));
		expect.serializer("orderedJson").toMatchSnapshot(updateContact);
	}

	private BPartnerContact perform_createOrUpdateContact_update(@NonNull final String contactIdentifier)
	{
		final JsonRequestContact jsonContact = new JsonRequestContact();
		jsonContact.setName("jsonContact.name-UPDATED");
		jsonContact.setCode("jsonContact.code-UPDATED");
		jsonContact.setMetasfreshBPartnerId(JsonMetasfreshId.of(C_BPARTNER_ID));

		final JsonRequestContactUpsert upsertRequest = JsonRequestContactUpsert.builder()
				.syncAdvise(SyncAdvise.builder().ifExists(IfExists.UPDATE_MERGE).build())
				.requestItem(JsonRequestContactUpsertItem
						.builder()
						.contactIdentifier(contactIdentifier)
						.contact(jsonContact)
						.build())
				.build();

		SystemTime.setTimeSource(() -> 1561134560); // Fri, 21 Jun 2019 16:29:20 GMT

		final ResponseEntity<JsonResponseUpsert> result = contactRestController.createOrUpdateContact(upsertRequest);

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);
		final JsonResponseUpsert resultBody = result.getBody();

		assertThat(resultBody.getResponseItems()).hasSize(1);
		assertThat(resultBody.getResponseItems().get(0).getIdentifier()).isEqualTo(contactIdentifier);

		final JsonMetasfreshId updatedMetasfreshId = resultBody.getResponseItems().get(0).getMetasfreshId();

		final BPartnerContactId updatedContactId = BPartnerContactId.ofRepoId(C_BPARTNER_ID, updatedMetasfreshId.getValue());

		final BPartnerComposite persistedResult = bpartnerCompositeRepository.getById(updatedContactId.getBpartnerId());
		final Optional<BPartnerContact> updatedContact = persistedResult.extractContact(updatedContactId);
		assertThat(updatedContact).isPresent();

		return updatedContact.get();
	}
}
