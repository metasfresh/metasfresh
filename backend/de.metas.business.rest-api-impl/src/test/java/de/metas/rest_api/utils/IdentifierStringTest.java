package de.metas.rest_api.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableMap;

import de.metas.bpartner.GLN;
import de.metas.rest_api.common.MetasfreshId;
import de.metas.rest_api.exception.InvalidIdentifierException;
import de.metas.rest_api.utils.IdentifierString.Type;
import de.metas.util.lang.ExternalId;

/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2019 metas GmbH
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

class IdentifierStringTest
{
	@Test
	void of_ExternalId()
	{
		final IdentifierString testee = IdentifierString.of("ext-abcd");

		assertThat(testee.getType()).isEqualTo(Type.EXTERNAL_ID);
		assertThat(testee.asExternalId()).isEqualTo(ExternalId.of("abcd"));
	}

	@Test
	void invalid_ExternalId()
	{
		assertThatThrownBy(() -> IdentifierString.of("ext-"))
				.hasMessage("Invalid external ID: `ext-`");
		assertThatThrownBy(() -> IdentifierString.of("ext-      "))
				.hasMessage("Invalid external ID: `ext-      `");
	}

	@Test
	void of_Value()
	{
		final IdentifierString testee = IdentifierString.of("val-abcd");

		assertThat(testee.getType()).isEqualTo(Type.VALUE);
		assertThat(testee.asValue()).isEqualTo("abcd");
	}

	@Test
	void invalid_Value()
	{
		assertThatThrownBy(() -> IdentifierString.of("val-"))
				.hasMessage("Invalid value: `val-`");
		assertThatThrownBy(() -> IdentifierString.of("val-      "))
				.hasMessage("Invalid value: `val-      `");
	}

	@Test
	void of_GLN()
	{
		final IdentifierString testee = IdentifierString.of("gln-abcd");

		assertThat(testee.getType()).isEqualTo(Type.GLN);
		assertThat(testee.asGLN()).isEqualTo(GLN.ofString("abcd"));
	}

	@Test
	void invalid_GLN()
	{
		assertThatThrownBy(() -> IdentifierString.of("gln-"))
				.hasMessage("Invalid GLN: `gln-`");
		assertThatThrownBy(() -> IdentifierString.of("gln-      "))
				.hasMessage("Invalid GLN: `gln-      `");
	}

	@Test
	void of_MetasfreshId()
	{
		final IdentifierString testee = IdentifierString.of("12345");

		assertThat(testee.getType()).isEqualTo(Type.METASFRESH_ID);
		assertThat(testee.asMetasfreshId()).isEqualTo(MetasfreshId.of(12345));
	}

	@Test
	void invalid_MetasfreshId()
	{
		assertThatThrownBy(() -> IdentifierString.of("12345x"))
				.isInstanceOf(InvalidIdentifierException.class)
				.hasMessageContaining("12345x");
	}

	@Test
	void testFromToJson()
	{
		final ImmutableMap<Type, String> testValues = ImmutableMap.<Type, String> builder()
				.put(Type.METASFRESH_ID, "12345")
				.put(Type.EXTERNAL_ID, "ext-someExternalId")
				.put(Type.VALUE, "val-someValue")
				.put(Type.GLN, "gln-someGLN")
				.put(Type.INTERNALNAME, "int-someInternalName")
				.build();

		for (final Type type : Type.values())
		{
			final String testValue = testValues.get(type);
			if (testValue == null)
			{
				throw new AdempiereException("No test value defined for type=" + type);
			}

			final IdentifierString identifierString = IdentifierString.of(testValue);
			assertThat(identifierString.toJson()).isEqualTo(testValue);
		}
	}

}
