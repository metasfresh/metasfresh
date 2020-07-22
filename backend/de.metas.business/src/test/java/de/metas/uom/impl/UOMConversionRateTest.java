package de.metas.uom.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.metas.uom.UOMConversionRate;
import de.metas.uom.UOMPrecision;
import de.metas.uom.UomId;

/*
 * #%L
 * de.metas.business
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

public class UOMConversionRateTest
{
	@Nested
	public class builder
	{
		@Test
		public void both_rates_specified()
		{
			UOMConversionRate.builder()
					.fromUomId(UomId.ofRepoId(1))
					.toUomId(UomId.ofRepoId(2))
					.fromToMultiplier(new BigDecimal("2"))
					.toFromMultiplier(new BigDecimal("3"))
					.build();
			// assert no exceptions
		}

		@Test
		public void only_fromToMultiplier()
		{
			UOMConversionRate.builder()
					.fromUomId(UomId.ofRepoId(1))
					.toUomId(UomId.ofRepoId(2))
					.fromToMultiplier(new BigDecimal("2"))
					.build();
			// assert no exceptions
		}

		@Test
		public void only_fromToMultiplier_but_zero()
		{
			assertThatThrownBy(() -> UOMConversionRate.builder()
					.fromUomId(UomId.ofRepoId(1))
					.toUomId(UomId.ofRepoId(2))
					.fromToMultiplier(new BigDecimal("0"))
					.build())
							.hasMessageContaining("invalid fromToMultiplier");
		}

		@Test
		public void only_toFromMultiplier()
		{
			UOMConversionRate.builder()
					.fromUomId(UomId.ofRepoId(1))
					.toUomId(UomId.ofRepoId(2))
					.toFromMultiplier(new BigDecimal("3"))
					.build();
			// assert no exceptions
		}

		@Test
		public void only_toFromMultiplier_but_zero()
		{
			assertThatThrownBy(() -> UOMConversionRate.builder()
					.fromUomId(UomId.ofRepoId(1))
					.toUomId(UomId.ofRepoId(2))
					.toFromMultiplier(new BigDecimal("0"))
					.build())
							.hasMessageContaining("invalid toFromMultiplier");
		}

	}

	@Test
	public void isOne()
	{
		assertThat(UOMConversionRate.one(UomId.ofRepoId(1234)).isOne()).isTrue();
	}

	@Test
	public void invert()
	{
		final UOMConversionRate rate = UOMConversionRate.builder()
				.fromUomId(UomId.ofRepoId(1))
				.toUomId(UomId.ofRepoId(2))
				.fromToMultiplier(new BigDecimal("2"))
				.toFromMultiplier(new BigDecimal("3"))
				.build();

		assertThat(rate.invert().invert()).isEqualTo(rate);
	}

	@Nested
	public class getFromToMultiplier
	{
		@Test
		public void when_fromToMultiplierWasSet()
		{
			final UOMConversionRate rate = UOMConversionRate.builder()
					.fromUomId(UomId.ofRepoId(1))
					.toUomId(UomId.ofRepoId(2))
					.fromToMultiplier(new BigDecimal("2"))
					.build();

			assertThat(rate.getFromToMultiplier()).isEqualTo("2");
		}

		@Test
		public void when_fromToMultiplierWasNotSet()
		{
			final UOMConversionRate rate = UOMConversionRate.builder()
					.fromUomId(UomId.ofRepoId(1))
					.toUomId(UomId.ofRepoId(2))
					.toFromMultiplier(new BigDecimal("2"))
					.build();

			assertThat(rate.getFromToMultiplier()).isEqualTo("0.5");
		}

	}

	@Nested
	public class onlyFromToMultiplier
	{
		/**
		 * Test converting from Meter (precision=2) to Centimeter (precision=2)
		 */
		@Test
		public void meter_to_centimeter_and_back()
		{
			final UomId meterUomId = UomId.ofRepoId(1);
			final UomId centimeterUomId = UomId.ofRepoId(2);

			final UOMConversionRate rate = UOMConversionRate.builder()
					.fromUomId(meterUomId)
					.toUomId(centimeterUomId)
					.fromToMultiplier(new BigDecimal("100"))
					// .toFromMultiplier(new BigDecimal("0.01"))
					.build();

			assertThat(rate.convert(new BigDecimal("1"), UOMPrecision.TWO)).isEqualTo("100.00");
			assertThat(rate.invert().convert(new BigDecimal("100"), UOMPrecision.TWO)).isEqualTo("1.00");
		}

		/**
		 * Test converting from Each (precision=0) to Kilogram (precision=2)
		 */
		@Test
		public void each_to_kg_and_back()
		{
			final UomId kgUomId = UomId.ofRepoId(1);
			final UomId eachUomId = UomId.ofRepoId(2);

			final UOMConversionRate rate_Each_to_Kg = UOMConversionRate.builder()
					.fromUomId(eachUomId)
					.toUomId(kgUomId)
					.fromToMultiplier(new BigDecimal("0.15"))
					// .toFromMultiplier(new BigDecimal("6.666666666667"))
					.build();

			assertThat(rate_Each_to_Kg.convert(new BigDecimal("180"), UOMPrecision.TWO))
					.isEqualTo("27.00");

			final UOMConversionRate rate_Kg_to_Each = rate_Each_to_Kg.invert();

			assertThat(rate_Kg_to_Each.convert(new BigDecimal("27"), UOMPrecision.ZERO))
					.isEqualTo(new BigDecimal("180"));
		}
	}
}
