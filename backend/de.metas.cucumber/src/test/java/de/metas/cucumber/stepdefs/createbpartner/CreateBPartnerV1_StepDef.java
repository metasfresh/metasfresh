/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.createbpartner;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.composite.BPartner;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.common.bpartner.v1.request.JsonRequestBPartner;
import de.metas.common.bpartner.v1.request.JsonRequestBPartnerUpsert;
import de.metas.common.bpartner.v1.request.JsonRequestBPartnerUpsertItem;
import de.metas.common.bpartner.v1.request.JsonRequestComposite;
import de.metas.common.bpartner.v1.request.JsonRequestContact;
import de.metas.common.bpartner.v1.request.JsonRequestContactUpsert;
import de.metas.common.bpartner.v1.request.JsonRequestContactUpsertItem;
import de.metas.common.bpartner.v1.request.JsonRequestLocation;
import de.metas.common.bpartner.v1.request.JsonRequestLocationUpsert;
import de.metas.common.bpartner.v1.request.JsonRequestLocationUpsertItem;
import de.metas.common.bpartner.v1.response.JsonResponseBPartnerCompositeUpsert;
import de.metas.common.bpartner.v1.response.JsonResponseBPartnerCompositeUpsertItem;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.RESTUtil;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.i18n.Language;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class CreateBPartnerV1_StepDef
{

	private final BPartnerCompositeRepository bPartnerCompositeRepository;
	private final TestContext testContext;
	private final JsonRequestComposite.JsonRequestCompositeBuilder jsonRequestCompositeBuilder = JsonRequestComposite.builder();

	public CreateBPartnerV1_StepDef(final TestContext testContext)
	{
		this.testContext = testContext;
		this.bPartnerCompositeRepository = SpringContextHolder.instance.getBean(BPartnerCompositeRepository.class);
	}

	@Given("the user adds bpartner")
	public void add_bpartner(@NonNull final DataTable dataTable)
	{
		final Map<String, String> dataTableEntries = dataTable.asMaps().get(0);

		final JsonRequestBPartner bPartner = mapBPartnerRequestItem(dataTableEntries);
		jsonRequestCompositeBuilder.bpartner(bPartner);
	}

	@And("the user adds locations")
	public void add_locations(@NonNull final DataTable dataTable)
	{
		final ImmutableList.Builder<JsonRequestLocationUpsertItem> requestItems = ImmutableList.builder();

		final List<Map<String, String>> locationsTableList = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : locationsTableList)
		{
			requestItems.add(mapLocationRequestItem(dataTableRow));
		}

		final JsonRequestLocationUpsert locationUpsert = JsonRequestLocationUpsert.builder()
				.requestItems(requestItems.build())
				.build();
		jsonRequestCompositeBuilder.locations(locationUpsert);
	}

	@And("the user adds contacts")
	public void add_contacts(@NonNull final DataTable dataTable)
	{
		final ImmutableList.Builder<JsonRequestContactUpsertItem> requestItems = ImmutableList.builder();

		final List<Map<String, String>> contactsTableList = dataTable.asMaps();
		for (final Map<String, String> dataTableRow : contactsTableList)
		{
			requestItems.add(mapContactRequestItem(dataTableRow));
		}
		final JsonRequestContactUpsert contactUpsert = JsonRequestContactUpsert.builder()
				.requestItems(requestItems.build())
				.build();
		jsonRequestCompositeBuilder.contacts(contactUpsert);
	}

	@And("the request is set in context for bpartnerId {string} and syncAdvise {string}")
	public void set_req_in_context(@NonNull final String bpartnerId, @NonNull final String syncAdvise) throws JsonProcessingException
	{
		final JsonRequestBPartnerUpsertItem jsonRequestBPartnerUpsertItem = JsonRequestBPartnerUpsertItem.builder()
				.bpartnerIdentifier(bpartnerId)
				.bpartnerComposite(jsonRequestCompositeBuilder.build())
				.build();

		final ImmutableList.Builder<JsonRequestBPartnerUpsertItem> jsonRequestBPartnerUpsertItems = ImmutableList.builder();
		jsonRequestBPartnerUpsertItems.add(jsonRequestBPartnerUpsertItem);

		final JsonRequestBPartnerUpsert jsonRequestBPartnerUpsert = JsonRequestBPartnerUpsert.builder()
				.requestItems(jsonRequestBPartnerUpsertItems.build())
				.syncAdvise(RESTUtil.mapSyncAdviseV1(syncAdvise))
				.build();
		testContext.setRequestPayload(new ObjectMapper().writeValueAsString(jsonRequestBPartnerUpsert));
	}

	@Then("verify if data is persisted correctly for bpartnerId {string}")
	public void verify_data_is_persisted_correctly(@NonNull final String bpartnerId) throws IOException
	{
		//request
		final String responseJson = testContext.getApiResponse().getContent();
		final com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();

		final JsonRequestBPartnerUpsert requestItemsList = mapper.readValue(testContext.getRequestPayload(), JsonRequestBPartnerUpsert.class);
		assertThat(requestItemsList).isNotNull();

		final String reqBPartnerIdentifier = requestItemsList.getRequestItems().get(0).getBpartnerIdentifier();
		assertThat(reqBPartnerIdentifier).isEqualTo(bpartnerId);

		//response
		final JsonResponseBPartnerCompositeUpsert response = mapper.readValue(responseJson, JsonResponseBPartnerCompositeUpsert.class);
		assertThat(response.getResponseItems()).hasSize(1);

		final JsonResponseBPartnerCompositeUpsertItem jsonResponseBPartnerCompositeUpsertItem = response.getResponseItems().get(0);
		final JsonMetasfreshId metasfreshId = jsonResponseBPartnerCompositeUpsertItem.getResponseBPartnerItem().getMetasfreshId();

		final BPartnerComposite persistedResult = bPartnerCompositeRepository.getById(BPartnerId.ofRepoId(metasfreshId.getValue()));
		assertThat(persistedResult.getBpartner().getExternalId().getValue()).isEqualTo(reqBPartnerIdentifier);

		//bpartner
		final BPartner bPartner = persistedResult.getBpartner();
		final JsonRequestBPartner jsonRequestBPartner = jsonRequestCompositeBuilder.build().getBpartner();
		validateBPartner(bPartner, jsonRequestBPartner);

		//location
		final List<BPartnerLocation> locations = persistedResult.getLocations();
		final JsonRequestLocationUpsert locationsUpsert = jsonRequestCompositeBuilder.build().getLocationsNotNull();
		final List<JsonRequestLocationUpsertItem> jsonRequestLocations = locationsUpsert.getRequestItems();

		for (final JsonRequestLocationUpsertItem jsonRequestLocation : jsonRequestLocations)
		{
			final JsonRequestLocation location = jsonRequestLocation.getLocation();
			locations.stream()
					.filter(item -> item.getExternalId().getValue().equals(location.getExternalId().getValue()))
					.findFirst().ifPresent(bPartnerLocation -> validateBPartnerLocation(bPartnerLocation, location));
		}

		//contact
		final List<BPartnerContact> contacts = persistedResult.getContacts();
		final JsonRequestContactUpsert contactsUpsert = jsonRequestCompositeBuilder.build().getContactsNotNull();
		final List<JsonRequestContactUpsertItem> jsonRequestContacts = contactsUpsert.getRequestItems();

		for (final JsonRequestContactUpsertItem jsonRequestContact : jsonRequestContacts)
		{
			final JsonRequestContact contact = jsonRequestContact.getContact();
			contacts.stream()
					.filter(item -> item.getExternalId().getValue().equals(contact.getExternalId().getValue()))
					.findFirst().ifPresent(bPartnerContact -> validateBPartnerContact(bPartnerContact, contact));
		}
	}

	@NonNull
	private JsonRequestBPartner mapBPartnerRequestItem(@NonNull final Map<String, String> dataTableEntries)
	{
		final String externalId = DataTableUtil.extractStringForColumnName(dataTableEntries, "ExternalId");
		final String code = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "OPT.Code");
		final String name = DataTableUtil.extractStringForColumnName(dataTableEntries, "Name");
		final String companyName = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "OPT.CompanyName");
		final String parentId = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "OPT.ParentId");
		final String phone = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "OPT.Phone");
		final String language = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "OPT.Language");
		final String url = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "OPT.Url");
		final String group = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "OPT.Group");
		final String vatId = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "OPT.VatId");

		final JsonExternalId jsonExternalId = JsonExternalId.of(externalId);

		final JsonRequestBPartner bPartner = new JsonRequestBPartner();
		bPartner.setCode(code);
		bPartner.setCompanyName(companyName);
		bPartner.setExternalId(jsonExternalId);
		bPartner.setGroup(group);
		bPartner.setLanguage(language);
		bPartner.setName(name);
		bPartner.setPhone(phone);
		bPartner.setUrl(url);
		bPartner.setVatId(vatId);

		if (DataTableUtil.extractValueOrNull(parentId) != null)
		{
			bPartner.setParentId(JsonMetasfreshId.of(Integer.valueOf(parentId)));
		}

		return bPartner;
	}

	@NonNull
	private JsonRequestLocationUpsertItem mapLocationRequestItem(@NonNull final Map<String, String> dataTableRow)
	{
		final String externalId = DataTableUtil.extractStringForColumnName(dataTableRow, "ExternalId");
		final String address1 = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT.Address1");
		final String address2 = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT.Address2");
		final String postal = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT.Postal");
		final String poBox = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT.PoBox");
		final String district = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT.District");
		final String region = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT.Region");
		final String city = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT.City");
		final String countryCode = DataTableUtil.extractStringForColumnName(dataTableRow, "CountryCode");
		final String gln = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT.Gln");

		final JsonRequestLocation location = new JsonRequestLocation();
		location.setAddress1(address1);
		location.setAddress2(address2);
		location.setPoBox(poBox);
		location.setDistrict(district);
		location.setRegion(region);
		location.setCity(city);
		location.setCountryCode(countryCode);
		location.setExternalId(JsonExternalId.of(externalId));
		location.setGln(gln);
		location.setPostal(postal);

		return JsonRequestLocationUpsertItem.builder()
				.locationIdentifier(externalId)
				.location(location)
				.build();
	}

	@NonNull
	private JsonRequestContactUpsertItem mapContactRequestItem(@NonNull final Map<String, String> dataTableRow)
	{
		final String externalId = DataTableUtil.extractStringForColumnName(dataTableRow, "ExternalId");
		final String name = DataTableUtil.extractStringForColumnName(dataTableRow, "Name");
		final String email = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT.Email");
		final String fax = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT.Fax");

		final JsonRequestContact jsonRequestContact = new JsonRequestContact();
		jsonRequestContact.setExternalId(JsonExternalId.of(externalId));
		jsonRequestContact.setName(name);
		jsonRequestContact.setEmail(email);
		jsonRequestContact.setFax(fax);

		return JsonRequestContactUpsertItem.builder()
				.contactIdentifier(externalId)
				.contact(jsonRequestContact).build();
	}

	private void validateBPartner(
			@NonNull final BPartner bPartner,
			@NonNull final JsonRequestBPartner jsonRequestBPartner)
	{
		assertThat(jsonRequestBPartner.getCompanyName()).isEqualTo(bPartner.getCompanyName());
		assertThat(Language.asLanguage(jsonRequestBPartner.getLanguage())).isEqualTo(bPartner.getLanguage());
		assertThat(jsonRequestBPartner.getName()).isEqualTo(bPartner.getName());
		assertThat(jsonRequestBPartner.getUrl()).isEqualTo(bPartner.getUrl());
		assertThat(jsonRequestBPartner.getVatId()).isEqualTo(bPartner.getVatId());
	}

	private void validateBPartnerLocation(
			@NonNull final BPartnerLocation bPartnerLocation,
			@NonNull final JsonRequestLocation jsonRequestLocation)
	{
		assertThat(jsonRequestLocation.getExternalId().getValue()).isEqualTo(bPartnerLocation.getExternalId().getValue());
		assertThat(jsonRequestLocation.getAddress1()).isEqualTo(bPartnerLocation.getAddress1());
		assertThat(jsonRequestLocation.getAddress2()).isEqualTo(bPartnerLocation.getAddress2());
		assertThat(jsonRequestLocation.getPostal()).isEqualTo(bPartnerLocation.getPostal());
		assertThat(jsonRequestLocation.getPoBox()).isEqualTo(bPartnerLocation.getPoBox());
		assertThat(jsonRequestLocation.getRegion()).isEqualTo(bPartnerLocation.getRegion());
		assertThat(jsonRequestLocation.getCountryCode()).isEqualTo(bPartnerLocation.getCountryCode());
		assertThat(jsonRequestLocation.getCity()).isEqualTo(bPartnerLocation.getCity());
		assertThat(DataTableUtil.extractValueOrNull(jsonRequestLocation.getDistrict())).isEqualTo(bPartnerLocation.getDistrict());

		assertThat(jsonRequestLocation.getGln()).isEqualTo(bPartnerLocation.getGln() != null ? bPartnerLocation.getGln().getCode() : null);
	}

	private void validateBPartnerContact(
			@NonNull final BPartnerContact bPartnerContact,
			@NonNull final JsonRequestContact jsonRequestContact)
	{
		assertThat(jsonRequestContact.getExternalId().getValue()).isEqualTo(bPartnerContact.getExternalId().getValue());
		assertThat(jsonRequestContact.getEmail()).isEqualTo(bPartnerContact.getEmail());
		assertThat(jsonRequestContact.getName()).isEqualTo(bPartnerContact.getName());
		assertThat(jsonRequestContact.getFax()).isEqualTo(bPartnerContact.getFax());
	}
}
