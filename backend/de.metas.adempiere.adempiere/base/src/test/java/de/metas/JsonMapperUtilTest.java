/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas;

import de.metas.common.bpartner.v2.response.JsonResponseContact;
import de.metas.common.bpartner.v2.response.JsonResponseUpsert;
import de.metas.common.bpartner.v2.response.JsonResponseUpsertItem;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link JsonMapperUtil#tryDeserializeToType(Object, Class)} tells JSON types apart only by deserialization failing.
 * This needs a strict shared mapper: if unknown properties were ignored, any JSON object would "deserialize" into any type, with null fields
 * (e.g. {@code BPartnerCompositeAuditService} took a {@link JsonResponseUpsert} for a {@link JsonResponseContact} and NPE-ed in {@code auditAdUser}).
 */
class JsonMapperUtilTest
{
	// plain public-field POJOs so Jackson binds by field access, no lombok @Jacksonized
	public static class TypeA
	{
		public String aName;
	}

	public static class TypeB
	{
		public String bName;
	}

	@Test
	void jsonString_of_another_type_gives_empty()
	{
		final Optional<TypeB> result = JsonMapperUtil.tryDeserializeToType("{\"aName\":\"abc\"}", TypeB.class);

		assertThat(result).isEmpty();
	}

	@Test
	void object_of_another_type_gives_empty()
	{
		final TypeA typeA = new TypeA();
		typeA.aName = "abc";

		final Optional<TypeB> result = JsonMapperUtil.tryDeserializeToType(typeA, TypeB.class);

		assertThat(result).isEmpty();
	}

	@Test
	void jsonString_of_the_requested_type_is_deserialized()
	{
		final Optional<TypeB> result = JsonMapperUtil.tryDeserializeToType("{\"bName\":\"abc\"}", TypeB.class);

		assertThat(result).hasValueSatisfying(typeB -> assertThat(typeB.bName).isEqualTo("abc"));
	}

	/**
	 * The real case behind the {@code PUT api/v2/bpartner} 500: the data-export audit gets a {@link JsonResponseUpsert} and probes it as a {@link JsonResponseContact}.
	 */
	@Test
	void jsonResponseUpsert_is_not_a_jsonResponseContact()
	{
		final JsonResponseUpsert upsert = JsonResponseUpsert.builder()
				.responseItem(JsonResponseUpsertItem.builder()
						.identifier("ext-ALBERTA-123")
						.metasfreshId(JsonMetasfreshId.of(1000001))
						.syncOutcome(JsonResponseUpsertItem.SyncOutcome.CREATED)
						.build())
				.build();

		final Optional<JsonResponseContact> result = JsonMapperUtil.tryDeserializeToType(upsert, JsonResponseContact.class);

		assertThat(result).isEmpty();
	}
}
