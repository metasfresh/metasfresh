package de.metas.uom.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.adempiere.exceptions.NoUOMConversionException;
import org.compiere.Adempiere;
import org.junit.Before;
import org.junit.Test;

import de.metas.uom.UOMConversionRate;
import de.metas.uom.UOMConversionsMap;
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

public class UOMConversionsMapTest
{
	private final UomId uomId1 = UomId.ofRepoId(1);
	private final UomId uomId2 = UomId.ofRepoId(2);
	private final UomId uomId3 = UomId.ofRepoId(3);

	@Before
	public void init()
	{
		Adempiere.enableUnitTestMode();
	}

	@Test
	public void test_getRate()
	{
		final UOMConversionRate rate = UOMConversionRate.builder()
				.fromUomId(uomId1)
				.toUomId(uomId2)
				.fromToMultiplier(new BigDecimal("100"))
				.toFromMultiplier(new BigDecimal("0.01"))
				.build();

		final UOMConversionsMap conversions = UOMConversionsMap.builder()
				.rate(rate)
				.build();

		assertThat(conversions.getRate(uomId1, uomId2)).isEqualTo(rate);
		assertThat(conversions.getRate(uomId2, uomId1)).isEqualTo(rate.invert());

		assertThat(conversions.getRate(uomId1, uomId1)).isEqualTo(UOMConversionRate.one(uomId1));
		assertThat(conversions.getRate(uomId2, uomId2)).isEqualTo(UOMConversionRate.one(uomId2));

		assertThrows(NoUOMConversionException.class, () -> conversions.getRate(uomId1, uomId3));
	}

}
