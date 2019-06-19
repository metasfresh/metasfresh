package de.metas.rest_api.bpartner.impl;

import static de.metas.rest_api.bpartner.impl.BPartnerRecordsUtil.*;
import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

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
import de.metas.bpartner.composite.BPartnerCompositeRepository;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.rest_api.JsonExternalId;
import de.metas.rest_api.MetasfreshId;
import de.metas.rest_api.SyncAdvise;
import de.metas.rest_api.SyncAdvise.IfExists;
import de.metas.rest_api.bpartner.JsonContact;
import de.metas.rest_api.bpartner.JsonContactList;
import de.metas.rest_api.bpartner.JsonContactUpsertRequest;
import de.metas.rest_api.bpartner.JsonContactUpsertRequestItem;
import de.metas.rest_api.bpartner.JsonUpsertResponse;
import de.metas.rest_api.bpartner.impl.bpartnercomposite.JsonServiceFactory;

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

		bpartnerCompositeRepository = new BPartnerCompositeRepository();
		final JsonServiceFactory jsonServiceFactory = new JsonServiceFactory(bpartnerCompositeRepository, new BPGroupRepository());

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

		// invoke the method under test
		final ResponseEntity<JsonContactList> page1 = contactRestController.retrieveContactsSince(0L, null);

		assertThat(page1.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonContactList page1Body = page1.getBody();
		assertThat(page1Body.getContacts()).hasSize(2);

		final String page2Id = page1Body.getPagingDescriptor().getNextPage();
		assertThat(page2Id).isNotEmpty();

		final ResponseEntity<JsonContactList> page2 = contactRestController.retrieveContactsSince(null, page2Id);

		assertThat(page2.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonContactList page2Body = page2.getBody();
		assertThat(page2Body.getContacts()).hasSize(2);

		final String page3Id = page2Body.getPagingDescriptor().getNextPage();
		assertThat(page3Id).isNotEmpty();

		final ResponseEntity<JsonContactList> page3 = contactRestController.retrieveContactsSince(null, page3Id);

		assertThat(page3.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonContactList page3Body = page3.getBody();
		assertThat(page3Body.getContacts()).hasSize(1);

		assertThat(page3Body.getPagingDescriptor().getNextPage()).isNull();
	}

	@Test
	void retrieveContact_ext()
	{
		// invoke the method under test
		final ResponseEntity<JsonContact> result = contactRestController.retrieveContact("ext-" + AD_USER_EXTERNAL_ID);

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonContact resultBody = result.getBody();

		expect(resultBody).toMatchSnapshot();
	}

	@Test
	void retrieveContact_id()
	{
		// invoke the method under test
		final ResponseEntity<JsonContact> result = contactRestController.retrieveContact(Integer.toString(AD_USER_ID));

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonContact resultBody = result.getBody();

		expect(resultBody).toMatchSnapshot();
	}

	@Test
	void retrieveContact_val()
	{
		// invoke the method under test
		final ResponseEntity<JsonContact> result = contactRestController.retrieveContact("val-" + "bpartnerRecord.value");

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonContact resultBody = result.getBody();

		expect(resultBody).toMatchSnapshot();
	}

	@Test
	void createOrUpdateContact()
	{
		// Env.setContext(Env.getCtx(), Env.CTXNAME_AD_Org_ID, AD_ORG_ID);

		final JsonContact jsonContact = JsonContact.builder()
				.name("jsonContact.name")
				.code("jsonContact.code")
				.metasfreshBPartnerId(MetasfreshId.of(C_BPARTNER_ID))
				.build();

		final JsonExternalId upsertExternalId = JsonExternalId.of("externalId-1");

		final JsonContactUpsertRequest upsertRequest = JsonContactUpsertRequest.builder()
				.syncAdvise(SyncAdvise.builder().ifExists(IfExists.UPDATE_MERGE).build())
				.requestItem(JsonContactUpsertRequestItem
						.builder()
						.externalId(upsertExternalId)
						.contact(jsonContact)
						.build())
				.build();
		final ResponseEntity<JsonUpsertResponse> result = contactRestController.createOrUpdateContact(upsertRequest);

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);
		final JsonUpsertResponse resultBody = result.getBody();

		assertThat(resultBody.getResponseItems()).hasSize(1);
		assertThat(resultBody.getResponseItems().get(0).getExternalId()).isEqualTo(upsertExternalId);

		final MetasfreshId insertedMetasfreshId = resultBody.getResponseItems().get(0).getMetasfreshId();

		final BPartnerContactId insertedContactId = BPartnerContactId.ofRepoId(C_BPARTNER_ID, insertedMetasfreshId.getValue());

		final BPartnerComposite persistedResult = bpartnerCompositeRepository.getById(insertedContactId.getBpartnerId());
		final Optional<BPartnerContact> insertedContact = persistedResult.getContact(insertedContactId);

		expect(insertedContact.get()).toMatchSnapshot();
	}
}
