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
import org.compiere.model.I_C_Country;
import org.compiere.util.Env;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPGroupRepository;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerCompositeQuery;
import de.metas.bpartner.composite.BPartnerCompositeRepository;
import de.metas.bpartner.composite.BPartnerCompositeRepository.ContactIdAndBPartner;
import de.metas.bpartner.composite.BPartnerContactQuery;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.rest_api.JsonExternalId;
import de.metas.rest_api.MetasfreshId;
import de.metas.rest_api.SyncAdvise;
import de.metas.rest_api.SyncAdvise.IfExists;
import de.metas.rest_api.SyncAdvise.IfNotExists;
import de.metas.rest_api.bpartner.JsonBPartner;
import de.metas.rest_api.bpartner.JsonBPartnerComposite;
import de.metas.rest_api.bpartner.JsonBPartnerCompositeList;
import de.metas.rest_api.bpartner.JsonBPartnerLocation;
import de.metas.rest_api.bpartner.JsonBPartnerUpsertRequest;
import de.metas.rest_api.bpartner.JsonBPartnerUpsertRequestItem;
import de.metas.rest_api.bpartner.JsonContact;
import de.metas.rest_api.bpartner.JsonUpsertResponse;
import de.metas.rest_api.bpartner.JsonUpsertResponseItem;
import de.metas.rest_api.bpartner.impl.bpartnercomposite.JsonServiceFactory;
import de.metas.rest_api.utils.JsonConverters;
import de.metas.util.rest.ExternalId;
import lombok.NonNull;

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
	void init()
	{
		AdempiereTestHelper.get().init();

		bpartnerCompositeRepository = new BPartnerCompositeRepository();
		final JsonServiceFactory jsonServiceFactory = new JsonServiceFactory(bpartnerCompositeRepository, new BPGroupRepository());

		bpartnerRestController = new BpartnerRestController(new BPartnerEndpointService(jsonServiceFactory), jsonServiceFactory);

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
		// invoke the method under test
		final ResponseEntity<JsonBPartnerComposite> result = bpartnerRestController.retrieveBPartner("ext-" + C_BPARTNER_EXTERNAL_ID);

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
				"ext-" + C_BPARTNER_EXTERNAL_ID,
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
				"ext-" + C_BPARTNER_EXTERNAL_ID,
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

		final String externalId = C_BPARTNER_EXTERNAL_ID + "_2";
		final JsonBPartnerComposite bpartnerComposite = MockedDataUtil
				.createMockBPartner("ext-" + externalId);

		final JsonBPartner bpartner = bpartnerComposite.getBpartner()
				.toBuilder()
				.group(BP_GROUP_RECORD_NAME)
				.build();

		final JsonBPartnerUpsertRequestItem requestItem = JsonBPartnerUpsertRequestItem.builder()
				.externalId(JsonExternalId.of(externalId))
				.bpartnerComposite(bpartnerComposite.toBuilder()
						.bpartner(bpartner)
						.build())
				.build();

		final JsonBPartnerUpsertRequest bpartnerUpsertRequest = JsonBPartnerUpsertRequest.builder()
				.syncAdvise(SyncAdvise.builder()
						.ifExists(IfExists.UPDATE_MERGE)
						.ifNotExists(IfNotExists.CREATE)
						.build())
				.requestItem(requestItem)
				.build();
		// JSONObjectMapper.forClass(JsonBPartnerUpsertRequest.class).writeValueAsString(bpartnerUpsertRequest);
		// invoke the method under test
		final ResponseEntity<JsonUpsertResponse> result = bpartnerRestController.createOrUpdateBPartner(bpartnerUpsertRequest);

		final MetasfreshId metasfreshId = assertUpsertResultOK(result, externalId);
		BPartnerId bpartnerId = BPartnerId.ofRepoId(metasfreshId.getValue());

		final BPartnerComposite persistedResult = bpartnerCompositeRepository.getById(bpartnerId);
		expect(persistedResult).toMatchSnapshot();
	}

	private MetasfreshId assertUpsertResultOK(final ResponseEntity<JsonUpsertResponse> result, final String externalId)
	{
		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);

		final JsonUpsertResponse resultBody = result.getBody();
		assertThat(resultBody.getResponseItems()).hasSize(1);

		final JsonUpsertResponseItem responseItem = resultBody.getResponseItems().get(0);
		assertThat(responseItem.getExternalId()).isEqualTo(JsonExternalId.of(externalId));

		final MetasfreshId metasfreshId = responseItem.getMetasfreshId();
		return metasfreshId;
	}

	private void createCountryRecord(@NonNull final String countryCode)
	{
		final I_C_Country deCountryRecord = newInstance(I_C_Country.class);
		deCountryRecord.setCountryCode(countryCode);
		saveRecord(deCountryRecord);
	}

	@Test
	void createOrUpdateContact()
	{
		final JsonContact jsonContact = MockedDataUtil.createMockContact("newContact-");
		assertThat(jsonContact.getExternalId()).isNotNull(); // guard

		// invoke the method under test
		final ResponseEntity<JsonUpsertResponseItem> result = bpartnerRestController.createOrUpdateContact("gln-" + C_BPARTNER_LOCATION_GLN, jsonContact);

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);

		final JsonUpsertResponseItem responseItem = result.getBody();

		assertThat(responseItem.getExternalId()).isEqualTo(jsonContact.getExternalId());

		final MetasfreshId metasfreshId = responseItem.getMetasfreshId();

		final BPartnerContactQuery bpartnerContactQuery = BPartnerContactQuery.builder().externalId(JsonConverters.fromJsonOrNull(jsonContact.getExternalId())).build();
		final Optional<ContactIdAndBPartner> optContactIdAndBPartner = bpartnerCompositeRepository.getByContact(bpartnerContactQuery);
		assertThat(optContactIdAndBPartner).isPresent();

		final BPartnerContactId resultContactId = optContactIdAndBPartner.get().getBpartnerContactId();
		assertThat(resultContactId.getRepoId()).isEqualTo(metasfreshId.getValue());

		final BPartnerComposite persistedResult = optContactIdAndBPartner.get().getBpartnerComposite();
		expect(persistedResult).toMatchSnapshot();
	}

	@Test
	void createOrUpdateLocation()
	{
		createCountryRecord("DE");
		final JsonBPartnerLocation jsonLocation = MockedDataUtil.createMockLocation("newLocation-", "DE");
		assertThat(jsonLocation.getExternalId()).isNotNull(); // guard

		// invoke the method under test
		final ResponseEntity<JsonUpsertResponseItem> result = bpartnerRestController.createOrUpdateLocation("ext-" + C_BPARTNER_EXTERNAL_ID, jsonLocation);

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);

		final JsonUpsertResponseItem responseItem = result.getBody();

		assertThat(responseItem.getExternalId()).isEqualTo(jsonLocation.getExternalId());

		final MetasfreshId metasfreshId = responseItem.getMetasfreshId();

		final BPartnerCompositeQuery query = BPartnerCompositeQuery.builder().externalId(ExternalId.of(C_BPARTNER_EXTERNAL_ID)).build();
		final ImmutableList<BPartnerComposite> persistedPage = bpartnerCompositeRepository.getByQuery(query);

		assertThat(persistedPage).hasSize(1);

		final BPartnerComposite persistedResult = persistedPage.get(0);
		final Optional<BPartnerLocation> persistedLocation = persistedResult.extractLocation(JsonConverters.fromJsonOrNull(jsonLocation.getExternalId()));
		assertThat(persistedLocation).isPresent();

		assertThat(persistedLocation.get().getId().getRepoId()).isEqualTo(metasfreshId.getValue());

		expect(persistedResult).toMatchSnapshot();
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

		// invoke the method under test
		final ResponseEntity<JsonBPartnerCompositeList> page1 = bpartnerRestController.retrieveBPartnersSince(0L, null);

		assertThat(page1.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonBPartnerCompositeList page1Body = page1.getBody();
		assertThat(page1Body.getItems()).hasSize(2);

		final String page2Id = page1Body.getPagingDescriptor().getNextPage();
		assertThat(page2Id).isNotEmpty();

		final ResponseEntity<JsonBPartnerCompositeList> page2 = bpartnerRestController.retrieveBPartnersSince(null, page2Id);

		assertThat(page2.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonBPartnerCompositeList page2Body = page2.getBody();
		assertThat(page2Body.getItems()).hasSize(2);

		final String page3Id = page2Body.getPagingDescriptor().getNextPage();
		assertThat(page3Id).isNotEmpty();

		final ResponseEntity<JsonBPartnerCompositeList> page3 = bpartnerRestController.retrieveBPartnersSince(null, page3Id);

		assertThat(page3.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		final JsonBPartnerCompositeList page3Body = page3.getBody();
		assertThat(page3Body.getItems()).hasSize(1);

		assertThat(page3Body.getPagingDescriptor().getNextPage()).isNull();
	}
}
