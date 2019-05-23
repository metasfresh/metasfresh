package de.metas.vertical.pharma.securpharm.model.schema;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * #%L
 * metasfresh-pharma.securpharm
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

public class ExpirationDateTest
{
	private ObjectMapper jsonObjectMapper;

	@Before
	public void init()
	{
		jsonObjectMapper = new ObjectMapper();
	}

	@Test
	public void test_ofString()
	{
		assertThat(ExpirationDate.ofString("210601").toLocalDate())
				.isEqualTo(LocalDate.of(2021, Month.JUNE, 1));

		assertThat(ExpirationDate.ofString("220830").toLocalDate())
				.isEqualTo(LocalDate.of(2022, Month.AUGUST, 30));

		assertThat(ExpirationDate.ofString("220800").toLocalDate())
				.isEqualTo(LocalDate.of(2022, Month.AUGUST, 31));
	}

	@Test
	public void test_ofLocalDate()
	{
		assertThat(ExpirationDate.ofLocalDate(LocalDate.of(2021, Month.JUNE, 1)).toJson())
				.isEqualTo("210601");

		assertThat(ExpirationDate.ofLocalDate(LocalDate.of(2022, Month.AUGUST, 30)).toJson())
				.isEqualTo("220830");

		assertThat(ExpirationDate.ofLocalDate(LocalDate.of(2022, Month.AUGUST, 31)).toJson())
				.isEqualTo("220800");
	}

	@Test
	public void testSerializedDeserialize() throws Exception
	{
		testSerializedDeserialize(ExpirationDate.ofString("210601"));
		testSerializedDeserialize(ExpirationDate.ofString("220800"));
	}

	public void testSerializedDeserialize(final ExpirationDate date) throws Exception
	{
		final String json = jsonObjectMapper.writeValueAsString(date);
		final ExpirationDate date2 = jsonObjectMapper.readValue(json, ExpirationDate.class);
		assertThat(date2).isEqualTo(date);
	}

}
