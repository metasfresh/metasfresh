/*
 * #%L
 * de-metas-camel-grssignum
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

package de.metas.camel.externalsystems.grssignum.to_grs.api.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class JsonBPartnerProductAdditionalInfoTest
{
	private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	@Test
	public void testDeserializeWithUnknownField() throws Exception
	{
		//given
		final String candidate =
				"{\n"
				+ "         \"ATTACHMENT\": [\n"
				+ "                {\n"
				+ "     				 \"ID\": \"100\",\n"
				+ "  					 \"dummyValue\": \"TEST\",\n"
				+ "   					 \"FileName\": \"FileName\",\n"
				+ "   					 \"ValidUntil\": \"2022-03-01T00:00:0\",\n"
				+ "   					 \"DocumentGroup\": \"DocumentGroup\",\n"
				+ "   					 \"DocumentType\": \"DocumentType\"\n"
				+ "       		    }\n"
				+ "       	 ]\n"
				+ "}";
		//when
		final JsonBPartnerProductAdditionalInfo jsonBPartnerProductAdditionalInfo = objectMapper.readValue(candidate, JsonBPartnerProductAdditionalInfo.class);

		//then
		final JsonAttachment jsonAttachment = JsonAttachment.builder()
				.id("100")
				.fileName("FileName")
				.documentGroup("DocumentGroup")
				.documentType("DocumentType")
				.validUntil("2022-03-01T00:00:0")
				.build();

		final JsonBPartnerProductAdditionalInfo expectedJsonBPartnerProductAdditionalInfo = JsonBPartnerProductAdditionalInfo.builder()
				.attachments(ImmutableList.of(jsonAttachment))
				.build();

		assertThat(jsonBPartnerProductAdditionalInfo).isEqualTo(expectedJsonBPartnerProductAdditionalInfo);
	}

	@Test
	public void serialize_deserialize_test() throws Exception
	{
		//given
		final JsonAttachment jsonAttachment = JsonAttachment.builder()
				.id("100")
				.fileName("FileName")
				.documentGroup("DocumentGroup")
				.documentType("DocumentType")
				.validUntil("2022-03-01T00:00:0")
				.build();

		final JsonBPartnerProductAdditionalInfo jsonBPartnerProductAdditionalInfo = JsonBPartnerProductAdditionalInfo.builder()
				.attachments(ImmutableList.of(jsonAttachment))
				.build();

		//when
		final String serialized = objectMapper.writeValueAsString(jsonBPartnerProductAdditionalInfo);

		final JsonBPartnerProductAdditionalInfo deserialized = objectMapper.readValue(serialized, JsonBPartnerProductAdditionalInfo.class);

		//then
		assertThat(deserialized).isEqualTo(jsonBPartnerProductAdditionalInfo);
	}
}
