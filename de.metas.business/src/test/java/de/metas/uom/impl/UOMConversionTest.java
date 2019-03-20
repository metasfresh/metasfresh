package de.metas.uom.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

import de.metas.uom.UOMConversion;
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

public class UOMConversionTest
{
	@Test
	public void test_standardCase()
	{
		final UomId meterUomId = UomId.ofRepoId(1);
		final UomId centimeterUomId = UomId.ofRepoId(2);

		final UOMConversion conv = UOMConversion.builder()
				.fromUomId(meterUomId)
				.toUomId(centimeterUomId)
				.fromToMultiplier(new BigDecimal("100"))
				.toFromMultiplier(new BigDecimal("0.01"))
				.build();

		assertThat(conv.convert(new BigDecimal("1"), meterUomId, centimeterUomId)).isEqualTo("100");
		assertThat(conv.convert(new BigDecimal("100"), centimeterUomId, meterUomId)).isEqualTo("1.00");
	}
}
