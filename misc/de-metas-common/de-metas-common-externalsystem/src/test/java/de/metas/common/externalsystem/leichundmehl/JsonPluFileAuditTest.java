/*
 * #%L
 * de-metas-common-externalsystem
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

package de.metas.common.externalsystem.leichundmehl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JsonPluFileAuditTest
{
	private ObjectMapper objectMapper;

	@BeforeEach
	public void beforeEach()
	{
		objectMapper = new ObjectMapper();
	}

	@Test
	public void test() throws Exception
	{
		final JsonProcessedKeys processedKeys = JsonProcessedKeys.builder()
				.key("key")
				.oldValue("oldValue")
				.newValue("newValue")
				.build();

		final JsonPluFileAudit jsonPluFileAudit = JsonPluFileAudit.builder()
				.fileName("fileName")
				.missingKey("missingKey")
				.processedKey(processedKeys)
				.build();

		testSerializeDeserialize(jsonPluFileAudit);

		testSerializeDeserialize(JsonMetasfreshId.of(1));
	}

	@Test
	public void test_noProcessedKeys() throws Exception
	{
		final JsonPluFileAudit jsonPluFileAudit = JsonPluFileAudit.builder()
				.fileName("fileName")
				.missingKeys(ImmutableList.of("key1", "key2"))
				.build();

		testSerializeDeserialize(jsonPluFileAudit);
	}

	private void testSerializeDeserialize(final Object obj) throws JsonProcessingException
	{
		System.out.println("Object: " + obj);

		final String json = objectMapper.writeValueAsString(obj);
		System.out.println("Object->JSON: " + json);

		final Object objDeserialized = objectMapper.readValue(json, obj.getClass());
		System.out.println("Object deserialized: " + objDeserialized);
		Assertions.assertThat(objDeserialized).isEqualTo(obj);
	}
}
