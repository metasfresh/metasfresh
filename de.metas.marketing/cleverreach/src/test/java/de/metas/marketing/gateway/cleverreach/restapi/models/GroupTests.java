package de.metas.marketing.gateway.cleverreach.restapi.models;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.JsonObjectMapperHolder;

/*
 * #%L
 * marketing-cleverreach
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

public class GroupTests
{
	@Test
	public void deserialize() throws JsonParseException, JsonMappingException, IOException
	{
		final String serializedGroups = "[{\"id\":565397,\"name\":\"rainbows and unicorns\",\"stamp\":1522324591,\"last_mailing\":1522325535,\"last_changed\":1522324674,\"isLocked\":false}]";

		final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		final List<Group> groups = objectMapper.readValue(serializedGroups, new TypeReference<List<Group>>() {});
		assertThat(groups).hasSize(1);

		final Group group = groups.get(0);
		assertThat(group.getName()).isEqualTo("rainbows and unicorns");
		assertThat(group.getId()).isEqualTo(565397);
	}
}
