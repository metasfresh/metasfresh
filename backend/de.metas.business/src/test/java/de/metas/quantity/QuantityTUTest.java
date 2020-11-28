package de.metas.quantity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.JsonObjectMapperHolder;

/*
 * #%L
 * de.metas.business
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

public class QuantityTUTest
{
	private ObjectMapper jsonObjectMapper;

	@BeforeEach
	public void beforeEach()
	{
		jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
	}

	public static QuantityTU[] getSerializationTestValues()
	{
		return new QuantityTU[] {
				QuantityTU.ofInt(-123),
				QuantityTU.ofInt(-1),
				QuantityTU.ofInt(0),
				QuantityTU.ofInt(1),
				QuantityTU.ofInt(10),
				QuantityTU.ofInt(123)
		};
	}

	@ParameterizedTest
	@MethodSource("getSerializationTestValues")
	public void testSerializeDeseriaze(final QuantityTU value)
	{
		try
		{
			final String json = jsonObjectMapper.writeValueAsString(value);
			final QuantityTU valueDeserialized = jsonObjectMapper.readValue(json, QuantityTU.class);

			assertThat(valueDeserialized).isEqualTo(value);
		}
		catch (JsonProcessingException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
		catch (IOException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	@Nested
	public class ofInt
	{
		@Test
		public void cachedValues()
		{
			for (int i = 0; i <= 10; i++)
			{
				final QuantityTU qtyTUs = QuantityTU.ofInt(i);
				assertThat(qtyTUs).isSameAs(QuantityTU.ofInt(i));
				assertThat(qtyTUs.toInt()).isEqualTo(i);
			}
		}

		@Test
		public void from_minus100_to_plus100()
		{
			for (int i = -100; i <= 100; i++)
			{
				final QuantityTU qtyTUs = QuantityTU.ofInt(i);
				assertThat(qtyTUs.toInt()).isEqualTo(i);
			}
		}
	}

	@Nested
	public class ofBigDecimal
	{
		@Test
		public void intValue()
		{
			assertThat(QuantityTU.ofBigDecimal(new BigDecimal("145")).toInt()).isEqualTo(145);
		}

		@Test
		public void nonIntValue()
		{
			assertThatThrownBy(() -> QuantityTU.ofBigDecimal(new BigDecimal("145.22")))
					.hasMessageStartingWith("Quantity TUs shall be integer: 145.22");
		}
	}

	@Nested
	public class add
	{
		@Test
		public void add_to_zero()
		{
			QuantityTU qtyTUs = QuantityTU.ofInt(1234);
			assertThat(QuantityTU.ZERO.add(qtyTUs)).isSameAs(qtyTUs);
		}

		@Test
		public void add_zero()
		{
			QuantityTU qtyTUs = QuantityTU.ofInt(1234);
			assertThat(qtyTUs.add(QuantityTU.ZERO)).isSameAs(qtyTUs);
		}

		@Test
		public void standardCases()
		{
			assertThat(QuantityTU.ofInt(100).add(QuantityTU.ofInt(200))).isEqualTo(QuantityTU.ofInt(300));
			assertThat(QuantityTU.ofInt(400).add(QuantityTU.ofInt(-350))).isEqualTo(QuantityTU.ofInt(50));
		}
	}

	@Nested
	public class subtract
	{
		@Test
		public void subtract_zero()
		{
			QuantityTU qtyTUs = QuantityTU.ofInt(1234);
			assertThat(qtyTUs.subtract(QuantityTU.ZERO)).isSameAs(qtyTUs);
		}

		@Test
		public void standardCases()
		{
			assertThat(QuantityTU.ofInt(400).subtract(QuantityTU.ofInt(100))).isEqualTo(QuantityTU.ofInt(300));
			assertThat(QuantityTU.ofInt(400).subtract(QuantityTU.ofInt(-350))).isEqualTo(QuantityTU.ofInt(750));
		}
	}

}
