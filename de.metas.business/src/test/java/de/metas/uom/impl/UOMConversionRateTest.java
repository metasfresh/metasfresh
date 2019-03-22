package de.metas.uom.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

import de.metas.uom.UOMConversionRate;
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
	@Test
	public void test_isOne()
	{
		assertThat(UOMConversionRate.one(UomId.ofRepoId(1234)).isOne()).isTrue();
	}

	@Test
	public void test_invert()
	{
		final UOMConversionRate rate = UOMConversionRate.builder()
				.fromUomId(UomId.ofRepoId(1))
				.toUomId(UomId.ofRepoId(2))
				.fromToMultiplier(new BigDecimal("2"))
				.toFromMultiplier(new BigDecimal("0.5"))
				.build();

		assertThat(rate.invert().invert()).isEqualTo(rate);
	}

	@Test
	public void test_standardCase()
	{
		final UomId meterUomId = UomId.ofRepoId(1);
		final UomId centimeterUomId = UomId.ofRepoId(2);

		final UOMConversionRate rate = UOMConversionRate.builder()
				.fromUomId(meterUomId)
				.toUomId(centimeterUomId)
				.fromToMultiplier(new BigDecimal("100"))
				.toFromMultiplier(new BigDecimal("0.01"))
				.build();

		assertThat(rate.convert(new BigDecimal("1"))).isEqualTo("100");
		assertThat(rate.invert().convert(new BigDecimal("100"))).isEqualTo("1.00");
	}
}
