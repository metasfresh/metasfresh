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

import de.metas.common.bpartner.v2.request.JsonRequestBPartner;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestComposite;
import de.metas.common.bpartner.v2.request.JsonRequestContact;
import de.metas.common.bpartner.v2.request.JsonRequestContactUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestContactUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestLocation;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsertItem;
import de.metas.common.bpartner.v2.response.JsonResponseBPartner;
import de.metas.common.bpartner.v2.response.JsonResponseComposite;
import de.metas.common.bpartner.v2.response.JsonResponseContact;
import de.metas.common.bpartner.v2.response.JsonResponseLocation;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.RESTUtil;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.rest_api.v2.bpartner.BPartnerEndpointService;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class CreateBPartnerV2_StepDef
{

	private final BPartnerEndpointService bpartnerEndpointService;
	private final TestContext testContext;
	private final JsonRequestComposite.JsonRequestCompositeBuilder jsonRequestCompositeBuilder = JsonRequestComposite.builder();

	public CreateBPartnerV2_StepDef(final TestContext testContext)
	{
		this.testContext = testContext;
		this.bpartnerEndpointService = SpringContextHolder.instance.getBean(BPartnerEndpointService.class);
	}

	@Given("the user adds v2 bpartner")
	public void add_bpartner(@NonNull final DataTable dataTable)
	{
		final Map<String, String> dataTableEntries = dataTable.asMaps().get(0);

		final JsonRequestBPartner bPartner = mapBPartnerRequestItem(dataTableEntries);
		jsonRequestCompositeBuilder.bpartner(bPartner);
	}

	@And("the user adds v2 locations")
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

	@And("the user adds v2 contacts")
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

	@And("the request is set in context for v2 bpartnerId {string} and syncAdvise {string}")
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
				.syncAdvise(RESTUtil.mapSyncAdvise(syncAdvise))
				.build();
		testContext.setRequestPayload(new ObjectMapper().writeValueAsString(jsonRequestBPartnerUpsert));
	}

	@Then("verify that bPartner was created for externalIdentifier {string}")
	public void verify_bPartner_was_created_for_externalIdentifier_v2(@NonNull final String externalIdentifier) throws IOException
	{
		//request
		final com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();

		final JsonRequestBPartnerUpsert requestItemsList = mapper.readValue(testContext.getRequestPayload(), JsonRequestBPartnerUpsert.class);
		assertThat(requestItemsList).isNotNull();

		final String reqBPartnerIdentifier = requestItemsList.getRequestItems().get(0).getBpartnerIdentifier();
		assertThat(reqBPartnerIdentifier).isEqualTo(externalIdentifier);

		// persisted value
		final Optional<JsonResponseComposite> persistedResult = bpartnerEndpointService.retrieveBPartner(null, ExternalIdentifier.of(externalIdentifier));

		final JsonResponseBPartner bPartner = persistedResult.get().getBpartner();
		final JsonRequestBPartner jsonRequestBPartner = jsonRequestCompositeBuilder.build().getBpartner();
		validateBPartner(bPartner, jsonRequestBPartner);
	}

	@And("verify that location with locationIdentifier {string} was created for bpartnerIdentifier {string}")
	public void verify_location_is_created_for_bpartnerIdentifier_v2(
			@NonNull final String locationIdentifier,
			@NonNull final String bpartnerIdentifier) throws IOException
	{
		// persisted value
		final Optional<JsonResponseLocation> persistedResult = bpartnerEndpointService.retrieveBPartnerLocation(
				null, ExternalIdentifier.of(bpartnerIdentifier), ExternalIdentifier.of(locationIdentifier));

		final JsonResponseLocation persistedLocation = persistedResult.get();
		final JsonRequestLocationUpsert locationsUpsert = jsonRequestCompositeBuilder.build().getLocationsNotNull();
		final List<JsonRequestLocationUpsertItem> jsonRequestLocations = locationsUpsert.getRequestItems();

		for (final JsonRequestLocationUpsertItem jsonRequestLocation : jsonRequestLocations)
		{
			if (locationIdentifier.equals(jsonRequestLocation.getLocationIdentifier()))
			{
				validateBPartnerLocation(persistedLocation, jsonRequestLocation);
				break;
			}
		}
	}

	@And("verify that contact with contactIdentifier {string} was created for bpartnerIdentifier {string}")
	public void verify_contact_is_created_for_bpartnerIdentifier_v2(
			@NonNull final String contactIdentifier,
			@NonNull final String bpartnerIdentifier) throws IOException
	{
		// persisted value
		final Optional<JsonResponseContact> persistedResult = bpartnerEndpointService.retrieveBPartnerContact(
				null, ExternalIdentifier.of(bpartnerIdentifier), ExternalIdentifier.of(contactIdentifier));

		final JsonResponseContact persistedContact = persistedResult.get();
		final JsonRequestContactUpsert contactsUpsert = jsonRequestCompositeBuilder.build().getContactsNotNull();
		final List<JsonRequestContactUpsertItem> jsonRequestContacts = contactsUpsert.getRequestItems();

		for (final JsonRequestContactUpsertItem jsonRequestContact : jsonRequestContacts)
		{
			if (contactIdentifier.equals(jsonRequestContact.getContactIdentifier()))
			{
				final JsonRequestContact contact = jsonRequestContact.getContact();
				validateBPartnerContact(persistedContact, contact);
				break;
			}
		}
	}

	@NonNull
	private JsonRequestBPartner mapBPartnerRequestItem(@NonNull final Map<String, String> dataTableEntries)
	{
		final String code = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "OPT.Code");
		final String name = DataTableUtil.extractStringForColumnName(dataTableEntries, "Name");
		final String companyName = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "OPT.CompanyName");
		final String parentId = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "OPT.ParentId");
		final String phone = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "OPT.Phone");
		final String language = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "OPT.Language");
		final String url = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "OPT.Url");
		final String group = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "OPT.Group");
		final String vatId = DataTableUtil.extractStringOrNullForColumnName(dataTableEntries, "OPT.VatId");

		final JsonRequestBPartner bPartner = new JsonRequestBPartner();
		bPartner.setCode(code);
		bPartner.setCompanyName(companyName);
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
		final String locationIdentifier = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "locationIdentifier");
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
		location.setGln(gln);
		location.setPostal(postal);

		return JsonRequestLocationUpsertItem.builder()
				.locationIdentifier(locationIdentifier)
				.location(location)
				.build();
	}

	@NonNull
	private JsonRequestContactUpsertItem mapContactRequestItem(@NonNull final Map<String, String> dataTableRow)
	{
		final String contactIdentifier = DataTableUtil.extractStringForColumnName(dataTableRow, "contactIdentifier");
		final String name = DataTableUtil.extractStringForColumnName(dataTableRow, "Name");
		final String email = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT.Email");
		final String fax = DataTableUtil.extractStringOrNullForColumnName(dataTableRow, "OPT.Fax");

		final JsonRequestContact jsonRequestContact = new JsonRequestContact();
		jsonRequestContact.setName(name);
		jsonRequestContact.setEmail(email);
		jsonRequestContact.setFax(fax);

		return JsonRequestContactUpsertItem.builder()
				.contactIdentifier(contactIdentifier)
				.contact(jsonRequestContact).build();
	}

	private void validateBPartner(
			@NonNull final JsonResponseBPartner bPartner,
			@NonNull final JsonRequestBPartner jsonRequestBPartner)
	{
		assertThat(jsonRequestBPartner.getCompanyName()).isEqualTo(bPartner.getCompanyName());
		assertThat(jsonRequestBPartner.getName()).isEqualTo(bPartner.getName());
		assertThat(jsonRequestBPartner.getUrl()).isEqualTo(bPartner.getUrl());
		assertThat(jsonRequestBPartner.getVatId()).isEqualTo(bPartner.getVatId());
		assertThat(bPartner.getLanguage()).contains(jsonRequestBPartner.getLanguage());
	}

	private void validateBPartnerLocation(
			@NonNull final JsonResponseLocation bPartnerLocation,
			@NonNull final JsonRequestLocationUpsertItem requestLocationUpsertItem)
	{

		final JsonRequestLocation jsonRequestLocation = requestLocationUpsertItem.getLocation();
		assertThat(jsonRequestLocation.getAddress1()).isEqualTo(bPartnerLocation.getAddress1());
		assertThat(jsonRequestLocation.getAddress2()).isEqualTo(bPartnerLocation.getAddress2());
		assertThat(jsonRequestLocation.getPostal()).isEqualTo(bPartnerLocation.getPostal());
		assertThat(jsonRequestLocation.getPoBox()).isEqualTo(bPartnerLocation.getPoBox());
		assertThat(jsonRequestLocation.getRegion()).isEqualTo(bPartnerLocation.getRegion());
		assertThat(jsonRequestLocation.getCountryCode()).isEqualTo(bPartnerLocation.getCountryCode());
		assertThat(jsonRequestLocation.getCity()).isEqualTo(bPartnerLocation.getCity());
		assertThat(DataTableUtil.extractValueOrNull(jsonRequestLocation.getDistrict())).isEqualTo(bPartnerLocation.getDistrict());

		final ExternalIdentifier externalIdentifier = ExternalIdentifier.of(requestLocationUpsertItem.getLocationIdentifier());
		if (externalIdentifier.getType() == ExternalIdentifier.Type.GLN)
		{
			final String[] items = requestLocationUpsertItem.getLocationIdentifier().split("-");
			assertThat(bPartnerLocation.getGln()).isEqualTo(items[1]);
		}
	}

	private void validateBPartnerContact(
			@NonNull final JsonResponseContact bPartnerContact,
			@NonNull final JsonRequestContact jsonRequestContact)
	{
		assertThat(jsonRequestContact.getEmail()).isEqualTo(bPartnerContact.getEmail());
		assertThat(jsonRequestContact.getName()).isEqualTo(bPartnerContact.getName());
		assertThat(jsonRequestContact.getFax()).isEqualTo(bPartnerContact.getFax());
	}
}
