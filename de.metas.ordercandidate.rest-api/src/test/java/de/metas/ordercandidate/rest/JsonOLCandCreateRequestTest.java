package de.metas.ordercandidate.rest;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

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

public class JsonOLCandCreateRequestTest
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
		final JsonBPartner pPartner = JsonBPartner.builder()
				.code("bp1")
				.name("bp1 name")
				.build();
		testSerializeDeserialize(pPartner, JSONObjectMapper.forClass(JsonBPartner.class));
	}

	@Test
	public void test_JsonBPartnerLocation() throws Exception
	{
		final JsonBPartnerLocation bPartnerLocation = JsonBPartnerLocation.builder()
				.address1("address1")
				.address2("address2")
				.postal("12345")
				.city("city")
				.countryCode("DE")
				.build();
		testSerializeDeserialize(bPartnerLocation, JSONObjectMapper.forClass(JsonBPartnerLocation.class));
	}

	@Test
	public void test_JsonBPartnerContact() throws Exception
	{
		final JsonBPartnerContact contact = JsonBPartnerContact.builder()
				.name("john doe")
				.email("john@doe.com")
				.phone("+123456789")
				.build();
		testSerializeDeserialize(contact, JSONObjectMapper.forClass(JsonBPartnerContact.class));
	}

	@Test
	public void test_JsonBPartnerInfo() throws Exception
	{
		final JsonBPartnerInfo bPartnerInfo = JsonBPartnerInfo.builder()
				.bpartner(JsonBPartner.builder()
						.code("bp1")
						.name("bp1 name")
						.build())
				.location(JsonBPartnerLocation.builder()
						.address1("address1")
						.address2("address2")
						.postal("12345")
						.city("city")
						.countryCode("DE")
						.build())
				.contact(JsonBPartnerContact.builder()
						.name("john doe")
						.email("john@doe.com")
						.phone("+123456789")
						.build())
				.build();

		testSerializeDeserialize(bPartnerInfo, JSONObjectMapper.forClass(JsonBPartnerInfo.class));
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
				.dataDestInternalName("DEST.de.metas.invoicecandidate")
				.dateInvoiced(LocalDate.of(2019, 03, 13))
				.build();

		final JsonOLCandCreateBulkRequest request = JsonOLCandCreateBulkRequest.builder()
				.request(requestForOrderLine)
				.request(requestForInvoiceCandidate)
				.build();
		testSerializeDeserialize(request, JSONObjectMapper.forClass(JsonOLCandCreateBulkRequest.class));
	}

	private JsonOLCandCreateRequest createDummyJsonOLCandCreateRequest()
	{
		return JsonOLCandCreateRequest.builder()
				.bpartner(JsonBPartnerInfo.builder()
						.bpartner(JsonBPartner.builder()
								.code("bp1")
								.name("bp1 name")
								.build())
						.location(JsonBPartnerLocation.builder()
								.address1("address1")
								.address2("address2")
								.postal("12345")
								.city("city")
								.countryCode("DE")
								.build())
						.contact(JsonBPartnerContact.builder()
								.name("john doe")
								.email("john@doe.com")
								.phone("+123456789")
								.build())
						.build())
				.dateRequired(LocalDate.of(2018, 03, 20))
				.dataSourceInternalName("dataSourceInternalName")
				.poReference("poReference")
				.build();
	}

	@Test
	public void test_realJson() throws IOException
	{
		final JsonOLCandCreateBulkRequest bulkRequest = JsonOLCandUtil.fromResource("/JsonOLCandCreateBulkRequest.json");
		testSerializeDeserialize(bulkRequest, JSONObjectMapper.forClass(JsonOLCandCreateBulkRequest.class));
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

		Assert.assertEquals(obj, objDeserialized);
	}
}
