/*
 * #%L
 * de-metas-common-externalsystem
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

package de.metas.common.externalsystem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JsonExternalSystemRequestTest
{
	final ObjectMapper mapper = new ObjectMapper();

	@Test
	void serializeDeserialize() throws IOException
	{
		final String sw6ConfigMappings = getExternalSystemShopware6ConfigMappings();

		final JsonExternalSystemRequest requestDeserialized = JsonExternalSystemRequest.builder().externalSystemName(JsonExternalSystemName.of("externalSystem"))
				.orgCode("orgCode")
				.command("command")
				.parameter("parameterName1", "parameterValue1")
				.parameter("parameterName2", "parameterValue2")
				.parameter("configMappings", sw6ConfigMappings)
				.build();

		final String valueAsString = mapper.writeValueAsString(requestDeserialized);

		final JsonExternalSystemRequest readValue = mapper.readValue(valueAsString, JsonExternalSystemRequest.class);

		assertThat(readValue).isEqualTo(requestDeserialized);

		final List<JsonExternalSystemShopware6ConfigMapping> externalSystemShopware6ConfigMappings =
				mapper.readValue(requestDeserialized.getParameters().get("configMappings"),
								 mapper.getTypeFactory().constructCollectionType(List.class, JsonExternalSystemShopware6ConfigMapping.class));

		final JsonExternalSystemShopware6ConfigMapping externalSystemShopware6ConfigMapping = externalSystemShopware6ConfigMappings.get(0);
		assertThat(externalSystemShopware6ConfigMapping).isNotNull();
		assertThat(externalSystemShopware6ConfigMapping.getDescription()).isEqualTo("test");
		assertThat(externalSystemShopware6ConfigMapping.getPaymentRule()).isEqualTo("test");
		assertThat(externalSystemShopware6ConfigMapping.getSw6CustomerGroup()).isEqualTo("test");
		assertThat(externalSystemShopware6ConfigMapping.getSw6PaymentMethod()).isEqualTo("test");
		assertThat(externalSystemShopware6ConfigMapping.getPaymentTerm()).isEqualTo("test");
		assertThat(externalSystemShopware6ConfigMapping.getDocTypeOrder()).isEqualTo("test");
		assertThat(externalSystemShopware6ConfigMapping.getSeqNo()).isEqualTo(10);


	}

	private String getExternalSystemShopware6ConfigMappings() throws JsonProcessingException
	{

		final JsonExternalSystemShopware6ConfigMapping externalSystemShopware6ConfigMapping =
				JsonExternalSystemShopware6ConfigMapping.builder()
						.sw6PaymentMethod("test")
						.sw6CustomerGroup("test")
						.paymentTerm("test")
						.paymentRule("test")
						.docTypeOrder("test")
						.description("test")
						.seqNo(10)
						.build();

		final List<JsonExternalSystemShopware6ConfigMapping> externalSystemShopware6ConfigMappings = new ArrayList<>();
		externalSystemShopware6ConfigMappings.add(externalSystemShopware6ConfigMapping);

		return mapper.writeValueAsString(externalSystemShopware6ConfigMappings);

	}
}