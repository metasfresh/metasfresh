/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.allocation.spi.impl;

import de.metas.uom.UOMPrecision;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.Assert.assertThat;

public class AggregateHUTrxListenerTests
{
	@ParameterizedTest
	@ValueSource(ints = { 3, 4 })
	public void testComputeSplitQty(final int precisionScale)
	{
		final UOMPrecision precision = UOMPrecision.ofInt(precisionScale);
		assertThat(AggregateHUTrxListener.INSTANCE.computeSplitQty(new BigDecimal("18"), new BigDecimal("3"), precision), comparesEqualTo(BigDecimal.ZERO));
		assertThat(AggregateHUTrxListener.INSTANCE.computeSplitQty(new BigDecimal("17"), new BigDecimal("3"), precision), comparesEqualTo(new BigDecimal("2")));

		// this is the one that failed and triggered gh #1203
		assertThat(AggregateHUTrxListener.INSTANCE.computeSplitQty(new BigDecimal("19"), new BigDecimal("3"), precision), comparesEqualTo(BigDecimal.ONE));

		assertThat(AggregateHUTrxListener.INSTANCE.computeSplitQty(new BigDecimal("60"), new BigDecimal("20"), precision), comparesEqualTo(BigDecimal.ZERO));
		assertThat(AggregateHUTrxListener.INSTANCE.computeSplitQty(new BigDecimal("60.003"), new BigDecimal("20.001"), precision), comparesEqualTo(BigDecimal.ZERO));
		assertThat(AggregateHUTrxListener.INSTANCE.computeSplitQty(new BigDecimal("60.004"), new BigDecimal("20.001"), precision), comparesEqualTo(new BigDecimal("0.001")));
	}

	@Test
	public void testComputeSplitQtyForRealData()
	{
		assertThat(AggregateHUTrxListener.INSTANCE.computeSplitQty(new BigDecimal("39.379"), new BigDecimal("2.461176471"), UOMPrecision.ofInt(3)), comparesEqualTo(BigDecimal.ZERO));
		assertThat(AggregateHUTrxListener.INSTANCE.computeSplitQty(new BigDecimal("39.379"), new BigDecimal("2.461176471"), UOMPrecision.ofInt(4)), comparesEqualTo(new BigDecimal("0.0002")));
	}
}
