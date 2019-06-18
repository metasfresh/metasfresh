package de.metas.rest_api.bpartner.impl;

import static io.github.jsonSnapshot.SnapshotMatcher.expect;
import static io.github.jsonSnapshot.SnapshotMatcher.start;
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Postal;
import org.compiere.util.Env;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import de.metas.bpartner.BPGroupRepository;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerCompositeRepository;
import de.metas.rest_api.JsonExternalId;
import de.metas.rest_api.SyncAdvise;
import de.metas.rest_api.SyncAdvise.IfExists;
import de.metas.rest_api.SyncAdvise.IfNotExists;
import de.metas.rest_api.bpartner.JsonBPartner;
import de.metas.rest_api.bpartner.JsonBPartnerComposite;
import de.metas.rest_api.bpartner.JsonBPartnerLocation;
import de.metas.rest_api.bpartner.JsonBPartnerUpsertRequest;
import de.metas.rest_api.bpartner.JsonBPartnerUpsertRequestItem;
import de.metas.rest_api.bpartner.JsonContact;
import de.metas.rest_api.bpartner.JsonUpsertResponse;
import de.metas.rest_api.bpartner.JsonUpsertResponseItem;
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

class BpartnerRestControllerTest
{
	private static final String C_COUNTRY_RECORD_COUNTRY_CODE = "countryRecord.countryCode";
	private static final String BP_GROUP_RECORD_NAME = "bpGroupRecord.name";
	private static final String C_BPARTNER_LOCATION_GLN = "bpartnerLocationRecord.gln";
	private static final String C_BPARTNER_LOCATION_EXTERNAL_ID = "bpartnerLocation.externalId";
	private static final int AD_ORG_ID = 10;
	private static final String AD_USER_EXTERNAL_ID = "abcde";
	private static final String C_BBPARTNER_EXTERNAL_ID = "fghij";

	private static final int C_BPARTNER_ID = 20;
	private static final int AD_USER_ID = 30;
	private static final int C_BBPARTNER_LOCATION_ID = 40;

	private BpartnerRestController bpartnerRestController;

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

		bpartnerRestController = new BpartnerRestController(new BPartnerEndpointService(jsonServiceFactory), jsonServiceFactory);

		final I_C_BP_Group bpGroupRecord = newInstance(I_C_BP_Group.class);
		bpGroupRecord.setName(BP_GROUP_RECORD_NAME);
		saveRecord(bpGroupRecord);

		final I_C_BPartner bpartnerRecord = newInstance(I_C_BPartner.class);
		bpartnerRecord.setC_BPartner_ID(C_BPARTNER_ID);
		bpartnerRecord.setAD_Org_ID(AD_ORG_ID);
		bpartnerRecord.setExternalId(C_BBPARTNER_EXTERNAL_ID);
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

		final I_C_Country countryRecord = newInstance(I_C_Country.class);
		countryRecord.setCountryCode(C_COUNTRY_RECORD_COUNTRY_CODE);
		saveRecord(countryRecord);

		final I_C_Postal postalRecord = newInstance(I_C_Postal.class);
		postalRecord.setC_Country(countryRecord);
		postalRecord.setPostal("postalRecord.postal");
		postalRecord.setDistrict("postalRecord.district");
		saveRecord(postalRecord);

		final I_C_Location locationRecord = newInstance(I_C_Location.class);
		locationRecord.setC_Postal(postalRecord);
		locationRecord.setC_Country(countryRecord);
		locationRecord.setAddress1("locationRecord.address1");
		locationRecord.setAddress2("locationRecord.address2");
		locationRecord.setPOBox("locationRecord.poBox");
		locationRecord.setPostal("locationRecord.postal");
		locationRecord.setCity("locationRecord.city");
		locationRecord.setRegionName("locationRecord.regionName");
		locationRecord.setAddress2("locationRecord.address2");
		locationRecord.setAddress2("locationRecord.address2");
		saveRecord(locationRecord);

		final I_C_BPartner_Location bpartnerLocationRecord = newInstance(I_C_BPartner_Location.class);
		bpartnerLocationRecord.setAD_Org_ID(AD_ORG_ID);
		bpartnerLocationRecord.setC_BPartner_Location_ID(C_BBPARTNER_LOCATION_ID);
		bpartnerLocationRecord.setC_BPartner(bpartnerRecord);
		bpartnerLocationRecord.setC_Location(locationRecord);
		bpartnerLocationRecord.setGLN(C_BPARTNER_LOCATION_GLN);
		bpartnerLocationRecord.setExternalId(C_BPARTNER_LOCATION_EXTERNAL_ID);
		saveRecord(bpartnerLocationRecord);

		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_Org_ID, AD_ORG_ID);
	}

	@Test
	void retrieveBPartner_ext()
	{
		// invoke the method under test
		final ResponseEntity<JsonBPartnerComposite> result = bpartnerRestController.retrieveBPartner("ext-" + C_BBPARTNER_EXTERNAL_ID);

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonBPartnerComposite resultBody = result.getBody();

		expect(resultBody).toMatchSnapshot();
	}

	@Test
	void retrieveBPartner_id()
	{
		// invoke the method under test
		final ResponseEntity<JsonBPartnerComposite> result = bpartnerRestController.retrieveBPartner(Integer.toString(C_BPARTNER_ID));

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonBPartnerComposite resultBody = result.getBody();

		expect(resultBody).toMatchSnapshot();
	}

	@Test
	void retrieveBPartner_gln()
	{
		// invoke the method under test
		final ResponseEntity<JsonBPartnerComposite> result = bpartnerRestController.retrieveBPartner("gln-" + C_BPARTNER_LOCATION_GLN);

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonBPartnerComposite resultBody = result.getBody();

		expect(resultBody).toMatchSnapshot();
	}

	@Test
	void retrieveBPartnerContact()
	{
		// invoke the method under test
		final ResponseEntity<JsonContact> result = bpartnerRestController.retrieveBPartnerContact(
				"ext-" + C_BBPARTNER_EXTERNAL_ID,
				Integer.toString(AD_USER_ID));

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonContact resultBody = result.getBody();

		expect(resultBody).toMatchSnapshot();
	}

	@Test
	void retrieveBPartnerLocation()
	{
		// invoke the method under test
		final ResponseEntity<JsonBPartnerLocation> result = bpartnerRestController.retrieveBPartnerLocation(
				"ext-" + C_BBPARTNER_EXTERNAL_ID,
				"gln-" + C_BPARTNER_LOCATION_GLN);

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonBPartnerLocation resultBody = result.getBody();

		expect(resultBody).toMatchSnapshot();
	}

	@Test
	void createOrUpdateBPartner()
	{
		createCountryRecord("CH");
		createCountryRecord("DE");

		final JsonBPartnerComposite bpartnerComposite = new MockedBPartnerEndpointService()
				.createBPartner("ext-" + C_BBPARTNER_EXTERNAL_ID + "_2");

		final JsonBPartner bpartner = bpartnerComposite.getBpartner()
				.toBuilder()
				.group(BP_GROUP_RECORD_NAME)
				.build();

		final JsonBPartnerUpsertRequestItem requestItem = JsonBPartnerUpsertRequestItem.builder()
				.externalId(JsonExternalId.of(C_BBPARTNER_EXTERNAL_ID + "_2"))
				.bpartnerComposite(bpartnerComposite.toBuilder()
						.bpartner(bpartner)
						.build())
				.build();

		final JsonBPartnerUpsertRequest bpartners = JsonBPartnerUpsertRequest.builder()
				.syncAdvise(SyncAdvise.builder()
						.ifExists(IfExists.UPDATE_MERGE)
						.ifNotExists(IfNotExists.CREATE)
						.build())
				.requestItem(requestItem)
				.build();

		// invoke the method under test
		final ResponseEntity<JsonUpsertResponse> result = bpartnerRestController.createOrUpdateBPartner(bpartners);

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);

		final JsonUpsertResponse resultBody = result.getBody();
		assertThat(resultBody.getResponseItems()).hasSize(1);

		final JsonUpsertResponseItem responseItem = resultBody.getResponseItems().get(0);
		assertThat(responseItem.getExternalId()).isEqualTo(JsonExternalId.of(C_BBPARTNER_EXTERNAL_ID + "_2"));

		BPartnerId bpartnerId = BPartnerId.ofRepoId(responseItem.getMetasfreshId().getValue());

		final BPartnerComposite persistedResult = bpartnerCompositeRepository.getById(bpartnerId);
		expect(persistedResult).toMatchSnapshot();
	}

	private void createCountryRecord(final String de)
	{
		final I_C_Country deCountryRecord = newInstance(I_C_Country.class);
		deCountryRecord.setCountryCode(de);
		saveRecord(deCountryRecord);
	}
}
