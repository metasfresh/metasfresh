package de.metas.marketing.gateway.cleverreach.restapi.models;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * #%L
 * marketing-cleverreach
 * %%
 * Copyright (C) 2020 metas GmbH
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

class ReceiverTest
{

	@Test
	void serialize_deserialize() throws IOException
	{
		final Receiver receiver = Receiver.builder().activated(100L).active(true)
				.bounced(1)
				.deactivated(90L)
				.email("email")
				.groups_id(20)
				.id(30)
				.imported(80)
				.last_client("last_client")
				.last_ip("last_ip")
				.last_location("last_location")
				.attribute("attributeKey1", "attributeValue")
				.attribute("attributeKey2", "attributeValue")
				.global_attribute("global_attributeKey1", "global_attributeValue")
				.global_attribute("global_attributeKey2", "global_attributeValue")
				.build();
		final ObjectMapper objectMapper = new ObjectMapper();
		final String jsonString = objectMapper.writeValueAsString(receiver);

		final Receiver receiverFromJson = objectMapper.readValue(jsonString, Receiver.class);
		assertThat(receiverFromJson).isEqualTo(receiver);
	}

}
