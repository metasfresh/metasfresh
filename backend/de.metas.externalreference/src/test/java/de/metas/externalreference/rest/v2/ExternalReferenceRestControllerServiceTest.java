/*
 * #%L
 * de.metas.externalreference
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.externalreference.rest.v2;

import de.metas.common.externalreference.v2.JsonExternalReferenceCreateRequest;
import de.metas.common.externalreference.v2.JsonExternalReferenceLookupItem;
import de.metas.common.externalreference.v2.JsonExternalReferenceLookupRequest;
import de.metas.common.externalreference.v2.JsonExternalReferenceLookupResponse;
import de.metas.common.externalreference.v2.JsonExternalReferenceRequestItem;
import de.metas.common.externalreference.v2.JsonExternalReferenceResponseItem;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.externalreference.ExternalReferenceTypes;
import de.metas.externalreference.ExternalSystems;
import de.metas.externalreference.PlainExternalReferenceType;
import de.metas.externalreference.PlainExternalSystem;
import de.metas.externalreference.model.I_S_ExternalReference;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static de.metas.externalreference.model.I_S_ExternalReference.COLUMNNAME_AD_Org_ID;
import static de.metas.externalreference.model.I_S_ExternalReference.COLUMNNAME_ExternalReference;
import static de.metas.externalreference.model.I_S_ExternalReference.COLUMNNAME_ExternalSystem;
import static de.metas.externalreference.model.I_S_ExternalReference.COLUMNNAME_Record_ID;
import static de.metas.externalreference.model.I_S_ExternalReference.COLUMNNAME_Type;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.groups.Tuple.tuple;

class ExternalReferenceRestControllerServiceTest
{
	ExternalReferenceRestControllerService externalReferenceRestControllerService;
	private OrgId orgId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final ExternalSystems externalSystems = new ExternalSystems();
		externalSystems.registerExternalSystem(new PlainExternalSystem("system"));

		final ExternalReferenceTypes externalReferenceTypes = new ExternalReferenceTypes();
		externalReferenceTypes.registerType(new PlainExternalReferenceType("bpartner", I_C_BPartner.Table_Name));

		final ExternalReferenceRepository externalReferenceRepository = new ExternalReferenceRepository(Services.get(IQueryBL.class),
																										externalSystems,
																										externalReferenceTypes
		);

		externalReferenceRestControllerService = new ExternalReferenceRestControllerService(externalReferenceRepository,
																							externalSystems,
																							externalReferenceTypes);

		orgId = AdempiereTestHelper.createOrgWithTimeZone("orgCode");
	}

	@Test
	void performInsert()
	{
		// given
		final JsonExternalReferenceCreateRequest request = JsonExternalReferenceCreateRequest.builder()
				.systemName(JsonExternalSystemName.of("system"))
				.item(JsonExternalReferenceRequestItem.of(JsonExternalReferenceLookupItem.builder()
														   .externalReference("id1")
														   .type("bpartner")
														   .build(),
														  JsonMetasfreshId.of(23)))
				.item(JsonExternalReferenceRequestItem.of(JsonExternalReferenceLookupItem.builder()
														   .externalReference("id2")
														   .type("bpartner")
														   .build(),
														  JsonMetasfreshId.of(24)))
				.build();

		// when
		externalReferenceRestControllerService.performInsert("orgCode", request);

		// then
		// firstly, verify the the data is ok in our DB
		final List<I_S_ExternalReference> records = POJOLookupMap.get().getRecords(I_S_ExternalReference.class);
		assertThat(records)
				.extracting(COLUMNNAME_AD_Org_ID, COLUMNNAME_ExternalSystem, COLUMNNAME_ExternalReference, COLUMNNAME_Type, COLUMNNAME_Record_ID)
				.containsExactly(
						tuple(orgId.getRepoId(), "system", "id1", "bpartner", 23),
						tuple(orgId.getRepoId(), "system", "id2", "bpartner", 24));

		// secondly, also verify that the service's response is OK as well
		final JsonExternalReferenceLookupItem lookupItem1 = JsonExternalReferenceLookupItem.builder()
				.type("bpartner")
				.metasfreshId(JsonMetasfreshId.of(1))
				.build();
		final JsonExternalReferenceLookupItem lookupItem2 = JsonExternalReferenceLookupItem.builder()
				.type("bpartner")
				.externalReference("id2")
				.build();
		final JsonExternalReferenceLookupItem lookupItem3 = JsonExternalReferenceLookupItem.builder()
				.type("bpartner")
				.metasfreshId(JsonMetasfreshId.of(23))
				.build();

		final JsonExternalReferenceLookupResponse response = externalReferenceRestControllerService.performLookup("orgCode",
																												  JsonExternalReferenceLookupRequest.builder()
																														  .systemName(JsonExternalSystemName.of("system"))
																														  .item(lookupItem1)
																														  .item(lookupItem2)
																														  .item(lookupItem3)
																														  .build());

		final List<JsonExternalReferenceResponseItem> items = response.getItems();

		assertThat(items.get(0).getLookupItem()).isEqualTo(lookupItem1);
		assertThat(items.get(0).getMetasfreshId()).isNull();

		assertThat(items.get(1).getLookupItem()).isEqualTo(lookupItem2);
		assertThat(items.get(1).getMetasfreshId()).isNotNull();
		assertThat(items.get(1).getMetasfreshId().getValue()).isEqualTo(24);

		assertThat(items.get(2).getLookupItem()).isEqualTo(lookupItem3);
		assertThat(items.get(2).getMetasfreshId()).isNotNull();
		assertThat(items.get(2).getMetasfreshId().getValue()).isEqualTo(23);
	}

	@Test
	void performLookup()
	{
		// given
		final JsonExternalReferenceCreateRequest request = JsonExternalReferenceCreateRequest.builder()
				.systemName(JsonExternalSystemName.of("system"))
				.item(JsonExternalReferenceRequestItem.of(JsonExternalReferenceLookupItem.builder()
														   .externalReference("id1")
														   .type("bpartner")
														   .build(),
														  JsonMetasfreshId.of(25)))
				.item(JsonExternalReferenceRequestItem.of(JsonExternalReferenceLookupItem.builder()
														   .externalReference("id2")
														   .type("bpartner")
														   .build(),
														  JsonMetasfreshId.of(26)))
				.build();

		// when
		externalReferenceRestControllerService.performInsert("orgCode", request);

		// then
		// firstly, verify the the data is ok in our DB
		final List<I_S_ExternalReference> records = POJOLookupMap.get().getRecords(I_S_ExternalReference.class);
		assertThat(records)
				.extracting(COLUMNNAME_AD_Org_ID, COLUMNNAME_ExternalSystem, COLUMNNAME_ExternalReference, COLUMNNAME_Type, COLUMNNAME_Record_ID)
				.containsExactly(
						tuple(orgId.getRepoId(), "system", "id1", "bpartner", 25),
						tuple(orgId.getRepoId(), "system", "id2", "bpartner", 26));

		// secondly, also verify that the service's response is OK as well
		final JsonExternalReferenceLookupItem lookupItem1 = JsonExternalReferenceLookupItem.builder()
				.type("bpartner")
				.metasfreshId(JsonMetasfreshId.of(25))
				.build();

		final JsonExternalReferenceLookupResponse response = externalReferenceRestControllerService.performLookup("orgCode",
																												  JsonExternalReferenceLookupRequest.builder()
																														  .systemName(JsonExternalSystemName.of("system"))
																														  .item(lookupItem1)
																														  .build());

		final List<JsonExternalReferenceResponseItem> items = response.getItems();

		assertThat(items.get(0).getLookupItem()).isEqualTo(lookupItem1);
		assertThat(items.get(0).getMetasfreshId()).isNotNull();
		assertThat(items.get(0).getMetasfreshId().getValue()).isEqualTo(25);
		assertThat(items.get(0).getExternalReference()).isEqualTo("id1");
	}
}