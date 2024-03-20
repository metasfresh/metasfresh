package de.metas.ui.web.window.datatypes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
 * #%L
 * metasfresh-webui-api
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

public class WindowIdTest
{
	@Test
	public void testEquals()
	{
		assertThat(WindowId.of(1234))
				.isEqualTo(WindowId.fromJson("1234"));
	}

	@Nested
	public class Serialization
	{
		private ObjectMapper jsonObjectMapper;

		@BeforeEach
		public void init()
		{
			jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		}

		@Test
		public void testSerializeDeserialize()
		{
			testSerializeDeserialize(WindowId.of(123));
			testSerializeDeserialize(WindowId.fromJson("windowId"));
		}

		private void testSerializeDeserialize(final WindowId windowId)
		{
			final WindowId windowIdDeserialized = fromJson(toJson(windowId));
			assertThat(windowIdDeserialized).isEqualTo(windowId);
		}

		private String toJson(final WindowId windowId)
		{
			try
			{
				return jsonObjectMapper.writeValueAsString(windowId);
			}
			catch (final JsonProcessingException e)
			{
				throw new AdempiereException("Failed serializing " + windowId, e);
			}
		}

		private WindowId fromJson(final String json)
		{
			try
			{
				return jsonObjectMapper.readValue(json, WindowId.class);
			}
			catch (final IOException e)
			{
				throw new AdempiereException("Failed deserializing:\n" + json, e);
			}
		}
	}

	@Nested
	public class toInt
	{
		@Test
		public void parseOK()
		{
			assertThat(WindowId.fromJson("123").toInt()).isEqualTo(123);
		}

		@Test
		public void parseError()
		{
			final WindowId windowId = WindowId.fromJson("123a");
			assertThatThrownBy(() -> windowId.toInt())
					.isInstanceOf(AdempiereException.class)
					.hasMessage("WindowId cannot be converted to int: 123a");
		}

	}

	@Nested
	public class toIntOr
	{
		@Test
		public void parseOK()
		{
			assertThat(WindowId.fromJson("123").toIntOr(-1)).isEqualTo(123);
		}

		@Test
		public void parseError()
		{
			final WindowId windowId = WindowId.fromJson("123a");
			assertThat(windowId.toIntOr(-1)).isEqualTo(-1);
		}
	}

	@Nested
	public class isInt
	{
		@Test
		void integerValue()
		{
			assertThat(WindowId.fromJson("123").isInt()).isTrue();
		}

		@Test
		void nonIntegerValue()
		{
			assertThat(WindowId.fromJson("123a").isInt()).isFalse();
		}
	}
}
