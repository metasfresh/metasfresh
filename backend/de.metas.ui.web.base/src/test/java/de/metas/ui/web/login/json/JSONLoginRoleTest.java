/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.login.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class JSONLoginRoleTest
{
	private ObjectMapper jsonMapper;

	@BeforeEach
	public void init()
	{
		jsonMapper = JsonObjectMapperHolder.newJsonObjectMapper();
	}

	@Test
	public void testSerializeDeserialize() throws IOException
	{
		testSerializeDeserializeObject(JSONLoginRole.builder()
				.caption("caption")
				.roleId(111)
				.tenantId(222)
				.orgId(333)
				.build());
	}

	private void testSerializeDeserializeObject(final Object value) throws IOException
	{
		final Class<?> valueClass = value.getClass();
		final String json = jsonMapper.writeValueAsString(value);
		final Object value2 = jsonMapper.readValue(json, valueClass);
		assertThat(value2).isEqualTo(value);
	}
}