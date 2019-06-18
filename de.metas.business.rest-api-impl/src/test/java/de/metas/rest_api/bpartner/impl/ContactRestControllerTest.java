package de.metas.rest_api.bpartner.impl;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
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
import de.metas.rest_api.SyncAdvise.IfExists;
import de.metas.rest_api.bpartner.JsonContact;
import de.metas.rest_api.bpartner.JsonContactList;
import de.metas.rest_api.bpartner.JsonContactUpsertRequest;
import de.metas.rest_api.bpartner.JsonContactUpsertRequestItem;
import de.metas.rest_api.bpartner.JsonUpsertResponse;
import de.metas.rest_api.bpartner.impl.bpartnercomposite.JsonServiceFactory;
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
	private static final int AD_ORG_ID = 10;
	private static final String AD_USER_EXTERNAL_ID = "abcde";
	private static final int C_BPARTNER_ID = 20;
	private static final int AD_USER_ID = 30;

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
	public void init()
	{
		AdempiereTestHelper.get().init();

		bpartnerCompositeRepository = new BPartnerCompositeRepository();
		final JsonServiceFactory jsonServiceFactory = new JsonServiceFactory(bpartnerCompositeRepository, new BPGroupRepository());

		contactRestController = new ContactRestController(new BPartnerEndpointService(jsonServiceFactory), jsonServiceFactory);

		final I_C_BP_Group bpGroupRecord = newInstance(I_C_BP_Group.class);
		bpGroupRecord.setName("bpGroupRecord.name");
		saveRecord(bpGroupRecord);

		final I_C_BPartner bpartnerRecord = newInstance(I_C_BPartner.class);
		bpartnerRecord.setC_BPartner_ID(C_BPARTNER_ID);
		bpartnerRecord.setAD_Org_ID(AD_ORG_ID);
		bpartnerRecord.setName("bpartnerRecord.name");
		bpartnerRecord.setValue("bpartnerRecord.value");
		bpartnerRecord.setC_BP_Group(bpGroupRecord);
		saveRecord(bpartnerRecord);

		final I_AD_User contactRecord = newInstance(I_AD_User.class);
		contactRecord.setAD_Org_ID(AD_ORG_ID);
		contactRecord.setAD_User_ID(AD_USER_ID);
		contactRecord.setC_BPartner(bpartnerRecord);
		contactRecord.setExternalId(AD_USER_EXTERNAL_ID);
		contactRecord.setValue("bpartnerRecord.value");
		contactRecord.setName("bpartnerRecord.name");
		contactRecord.setLastname("bpartnerRecord.lastName");
		contactRecord.setFirstname("bpartnerRecord.firstName");
		contactRecord.setEMail("bpartnerRecord.email");
		contactRecord.setPhone("bpartnerRecord.phone");
		saveRecord(contactRecord);

		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_Org_ID, AD_ORG_ID);
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
	void retrieveContactsSince()
	{
		SystemTime.setTimeSource(() -> 1560866562494L);

		final ResponseEntity<JsonContactList> result = contactRestController.retrieveContactsSince(0L, null);

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonContactList resultBody = result.getBody();

		expect(resultBody).toMatchSnapshot();
	}

	@Test
	void createOrUpdateContact()
	{
		// Env.setContext(Env.getCtx(), Env.CTXNAME_AD_Org_ID, AD_ORG_ID);

		final JsonContact jsonContact = JsonContact.builder()
				.name("jsonContact.name")
				.metasfreshBPartnerId(MetasfreshId.of(C_BPARTNER_ID))
				.build();

		final JsonExternalId upsertExternalId = JsonExternalId.of("externalId-1");

		final JsonContactUpsertRequest upsertRequest = JsonContactUpsertRequest.builder()
				.ifExists(IfExists.UPDATE_MERGE)
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
