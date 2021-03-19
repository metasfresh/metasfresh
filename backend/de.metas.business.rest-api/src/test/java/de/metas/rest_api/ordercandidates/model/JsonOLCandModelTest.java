package de.metas.rest_api.ordercandidates.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

import de.metas.common.rest_api.JsonErrorItem;
import de.metas.common.bpartner.request.JsonRequestBPartner;
import de.metas.common.bpartner.request.JsonRequestContact;
import de.metas.common.bpartner.request.JsonRequestLocation;
import de.metas.rest_api.common.JsonDocTypeInfo;
import de.metas.rest_api.ordercandidates.request.JsonOLCandCreateBulkRequest;
import de.metas.rest_api.ordercandidates.request.JsonOLCandCreateRequest;
import de.metas.rest_api.ordercandidates.request.JsonProductInfo;
import de.metas.rest_api.ordercandidates.request.JsonRequestBPartnerLocationAndContact;
import de.metas.rest_api.ordercandidates.response.JsonOLCand;
import de.metas.rest_api.ordercandidates.response.JsonOLCandCreateBulkResponse;
import de.metas.util.JSONObjectMapper;
import lombok.NonNull;

/*
 * #%L
 * de.metas.ordercandidate.rest-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class JsonOLCandModelTest
{
	@Test
	public void test_JsonProduct() throws Exception
	{
		final JsonProductInfo product = JsonProductInfo.builder()
				.code("code")
				.type(JsonProductInfo.Type.ITEM)
				.build();
		testSerializeDeserialize(product, JSONObjectMapper.forClass(JsonProductInfo.class));
	}

	@Test
	public void test_JsonBPartner() throws Exception
	{
		final JsonRequestBPartner bpartner = new JsonRequestBPartner();
		bpartner.setCode("bp1");
		bpartner.setName("bp1 name");
		bpartner.setVatId("RO 27324777");

		testSerializeDeserialize(bpartner, JSONObjectMapper.forClass(JsonRequestBPartner.class));
	}

	@Test
	public void test_JsonBPartnerLocation() throws Exception
	{
		final JsonRequestLocation bPartnerLocation = new JsonRequestLocation();
		bPartnerLocation.setAddress1("address1");
		bPartnerLocation.setAddress2("address2");
		bPartnerLocation.setPostal("12345");
		bPartnerLocation.setCity("city");
		bPartnerLocation.setCountryCode("DE");

		testSerializeDeserialize(bPartnerLocation, JSONObjectMapper.forClass(JsonRequestLocation.class));
	}

	@Test
	public void test_JsonBPartnerContact() throws Exception
	{
		final JsonRequestContact jsonContact = new JsonRequestContact();
		jsonContact.setName("john doe");
		jsonContact.setEmail("john@doe.com");
		jsonContact.setPhone("+123456789");

		testSerializeDeserialize(jsonContact, JSONObjectMapper.forClass(JsonRequestContact.class));
	}

	@Test
	public void test_JsonBPartnerInfo() throws Exception
	{
		final JsonRequestBPartner bpartner = new JsonRequestBPartner();
		bpartner.setCode("bp1");
		bpartner.setName("bp1 name");

		final JsonRequestLocation location = new JsonRequestLocation();
		location.setAddress1("address1");
		location.setAddress2("address2");
		location.setPostal("12345");
		location.setCity("city");
		location.setCountryCode("DE");

		final JsonRequestContact jsonContact = new JsonRequestContact();
		jsonContact.setName("john doe");
		jsonContact.setEmail("john@doe.com");
		jsonContact.setPhone("+123456789");

		final JsonRequestBPartnerLocationAndContact bPartnerInfo = JsonRequestBPartnerLocationAndContact.builder()
				.bpartner(bpartner)
				.location(location)
				.contact(jsonContact)
				.build();

		testSerializeDeserialize(bPartnerInfo, JSONObjectMapper.forClass(JsonRequestBPartnerLocationAndContact.class));
	}

	@Test
	public void test_JsonDocTypeInfo() throws Exception
	{
		final JsonDocTypeInfo docType = JsonDocTypeInfo.builder()
				.docBaseType("docBaseType")
				.docSubType("docSubType")
				.build();
		testSerializeDeserialize(docType, JSONObjectMapper.forClass(JsonDocTypeInfo.class));
	}

	@Test
	public void test_JsonOLCandCreateRequest() throws Exception
	{
		final JsonOLCandCreateRequest request = createDummyJsonOLCandCreateRequest();
		testSerializeDeserialize(request, JSONObjectMapper.forClass(JsonOLCandCreateRequest.class));
	}

	@Test
	public void test_JsonOLCandCreateBulkRequest() throws Exception
	{
		final JsonOLCandCreateRequest requestForOrderLine = createDummyJsonOLCandCreateRequest();

		final JsonOLCandCreateRequest requestForInvoiceCandidate = requestForOrderLine
				.toBuilder()
				.dataDest("int-DEST.de.metas.invoicecandidate")
				.presetDateInvoiced(LocalDate.of(2019, 03, 13))
				.build();

		final JsonOLCandCreateBulkRequest request = JsonOLCandCreateBulkRequest.builder()
				.request(requestForOrderLine)
				.request(requestForInvoiceCandidate)
				.build();
		testSerializeDeserialize(request, JSONObjectMapper.forClass(JsonOLCandCreateBulkRequest.class));
	}

	@Test
	public void test_JsonOLCandCreateBulkResponse_OK() throws Exception
	{
		final JSONObjectMapper<JsonOLCandCreateBulkResponse> jsonObjectMapper = JSONObjectMapper.forClass(JsonOLCandCreateBulkResponse.class);

		final JsonOLCandCreateBulkResponse response = JsonOLCandCreateBulkResponse.ok(
				ImmutableList.of(JsonOLCand.builder()
						.build()));
		testSerializeDeserialize(response, jsonObjectMapper);
	}

	@Test
	public void test_JsonOLCandCreateBulkResponse_Error() throws Exception
	{
		final JSONObjectMapper<JsonOLCandCreateBulkResponse> jsonObjectMapper = JSONObjectMapper.forClass(JsonOLCandCreateBulkResponse.class);

		final JsonOLCandCreateBulkResponse response = JsonOLCandCreateBulkResponse.error(JsonErrorItem.builder()
				.message("error message")
				.stackTrace("error stacktrace")
				.throwable(null) // REMEMBER: throwable is not serialized
				.build());
		testSerializeDeserialize(response, jsonObjectMapper);
	}

	@Test
	public void test_JsonOLCandCreateBulkResponse_Error_trowable_is_discarded() throws Exception
	{
		final JSONObjectMapper<JsonOLCandCreateBulkResponse> jsonObjectMapper = JSONObjectMapper.forClass(JsonOLCandCreateBulkResponse.class);

		final JsonOLCandCreateBulkResponse response = JsonOLCandCreateBulkResponse.error(JsonErrorItem.builder()
				.message("error message")
				.stackTrace("error stacktrace")
				.throwable(new RuntimeException("whatever"))
				.build());

		final String json = jsonObjectMapper.writeValueAsString(response);
		final JsonOLCandCreateBulkResponse responseDeserialized = jsonObjectMapper.readValue(json);
		assertThat(responseDeserialized).isEqualTo(JsonOLCandCreateBulkResponse.error(JsonErrorItem.builder()
				.message("error message")
				.stackTrace("error stacktrace")
				.build()));
	}

	private JsonOLCandCreateRequest createDummyJsonOLCandCreateRequest()
	{
		final JsonRequestBPartner bpartner = new JsonRequestBPartner();
		bpartner.setCode("bp1");
		bpartner.setName("bp1 name");

		final JsonRequestLocation location = new JsonRequestLocation();
		location.setAddress1("address1");
		location.setAddress2("address2");
		location.setPostal("12345");
		location.setCity("city");
		location.setCountryCode("DE");

		final JsonRequestContact jsonContact = new JsonRequestContact();
		jsonContact.setName("john doe");
		jsonContact.setEmail("john@doe.com");
		jsonContact.setPhone("+123456789");

		return JsonOLCandCreateRequest.builder()
				.bpartner(JsonRequestBPartnerLocationAndContact.builder()
						.bpartner(bpartner)
						.location(location)
						.contact(jsonContact)
						.build())
				.dateRequired(LocalDate.of(2018, 03, 20))
				.dataSource("int-dataSourceInternalName")
				.poReference("poReference")
				.build();
	}

	private <T> void testSerializeDeserialize(
			@NonNull final T obj,
			@NonNull JSONObjectMapper<T> jsonObjectMapper) throws IOException
	{
		// System.out.println("object: " + obj);
		final String json = jsonObjectMapper.writeValueAsString(obj);
		// System.out.println("json: " + json);

		final Object objDeserialized = jsonObjectMapper.readValue(json);
		// System.out.println("object deserialized: " + objDeserialized);

		assertThat(objDeserialized).isEqualTo(obj);
	}
}
