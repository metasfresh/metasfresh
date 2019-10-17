package de.metas.rest_api.bpartner.impl;

import static de.metas.rest_api.bpartner.impl.BPartnerRecordsUtil.*;
import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.adempiere.ad.table.RecordChangeLogRepository;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.I_C_BP_Group;
import org.compiere.util.Env;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import de.metas.bpartner.BPGroupRepository;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.greeting.GreetingRepository;
import de.metas.rest_api.MetasfreshId;
import de.metas.rest_api.SyncAdvise;
import de.metas.rest_api.SyncAdvise.IfExists;
import de.metas.rest_api.bpartner.impl.bpartnercomposite.JsonServiceFactory;
import de.metas.rest_api.bpartner.request.JsonRequestContact;
import de.metas.rest_api.bpartner.request.JsonRequestContactUpsert;
import de.metas.rest_api.bpartner.request.JsonRequestContactUpsertItem;
import de.metas.rest_api.bpartner.request.JsonResponseUpsert;
import de.metas.rest_api.bpartner.response.JsonResponseContact;
import de.metas.rest_api.bpartner.response.JsonResponseContactList;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import de.metas.util.lang.UIDStringUtil;
import de.metas.util.time.SystemTime;

/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2019 metas GmbH
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

class ContactRestControllerTest
{
	private ContactRestController contactRestController;

	private BPartnerCompositeRepository bpartnerCompositeRepository;

	@BeforeAll
	static void initStatic()
	{
		start(AdempiereTestHelper.SNAPSHOT_CONFIG);
	}

	@AfterAll
	static void afterAll()
	{
		validateSnapshots();
	}

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();

		Services.registerService(IBPartnerBL.class,new BPartnerBL(new UserRepository()));

		bpartnerCompositeRepository = new BPartnerCompositeRepository(new MockLogEntriesRepository());
		final JsonServiceFactory jsonServiceFactory = new JsonServiceFactory(
				bpartnerCompositeRepository,
				new BPGroupRepository(),
				new GreetingRepository(),
				new RecordChangeLogRepository());

		contactRestController = new ContactRestController(new BPartnerEndpointService(jsonServiceFactory), jsonServiceFactory);

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

		expect(page1Body, page2Body, page3Body).toMatchSnapshot();
	}

	@Test
	void retrieveContact_ext()
	{
		// invoke the method under test
		final ResponseEntity<JsonResponseContact> result = contactRestController.retrieveContact("ext-" + AD_USER_EXTERNAL_ID);

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonResponseContact resultBody = result.getBody();

		expect(resultBody).toMatchSnapshot();
	}

	@Test
	void retrieveContact_id()
	{
		// invoke the method under test
		final ResponseEntity<JsonResponseContact> result = contactRestController.retrieveContact(Integer.toString(AD_USER_ID));

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonResponseContact resultBody = result.getBody();

		expect(resultBody).toMatchSnapshot();
	}

	@Test
	void retrieveContact_val()
	{
		// invoke the method under test
		final ResponseEntity<JsonResponseContact> result = contactRestController.retrieveContact("val-" + "bpartnerRecord.value");

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonResponseContact resultBody = result.getBody();

		expect(resultBody).toMatchSnapshot();
	}

	@Test
	void createOrUpdateContact()
	{
		final JsonRequestContact jsonContact = JsonRequestContact.builder()
				.name("jsonContact.name")
				.code("jsonContact.code")
				.metasfreshBPartnerId(MetasfreshId.of(C_BPARTNER_ID))
				.build();

		final String contactIdentifier = "ext-externalId-1";

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

		final MetasfreshId insertedMetasfreshId = resultBody.getResponseItems().get(0).getMetasfreshId();

		final BPartnerContactId insertedContactId = BPartnerContactId.ofRepoId(C_BPARTNER_ID, insertedMetasfreshId.getValue());

		final BPartnerComposite persistedResult = bpartnerCompositeRepository.getById(insertedContactId.getBpartnerId());
		final Optional<BPartnerContact> insertedContact = persistedResult.getContact(insertedContactId);

		expect(insertedContact.get()).toMatchSnapshot();
	}
}
