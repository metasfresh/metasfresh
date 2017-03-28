package de.metas.handlingunits.allocation.spi.impl;

import java.math.BigDecimal;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class AggregateHUTrxListenerTests
{

	/**
	 * Verifies the behavior of {@link AggregateHUTrxListener#computeSplitQty(BigDecimal, BigDecimal)}.
	 * 
	 * @task https://github.com/metasfresh/metasfresh/issues/1203
	 */
	@Test
	public void testComputeSplitQty()
	{
		assertThat(AggregateHUTrxListener.INSTANCE.computeSplitQty(new BigDecimal("18"), new BigDecimal("3")), comparesEqualTo(BigDecimal.ZERO));
		assertThat(AggregateHUTrxListener.INSTANCE.computeSplitQty(new BigDecimal("17"), new BigDecimal("3")), comparesEqualTo(new BigDecimal("2")));

		// this is the one that failed and triggered gh #1203
		assertThat(AggregateHUTrxListener.INSTANCE.computeSplitQty(new BigDecimal("19"), new BigDecimal("3")), comparesEqualTo(BigDecimal.ONE));

		assertThat(AggregateHUTrxListener.INSTANCE.computeSplitQty(new BigDecimal("60"), new BigDecimal("20")), comparesEqualTo(BigDecimal.ZERO));
		assertThat(AggregateHUTrxListener.INSTANCE.computeSplitQty(new BigDecimal("60.003"), new BigDecimal("20.001")), comparesEqualTo(BigDecimal.ZERO));
		assertThat(AggregateHUTrxListener.INSTANCE.computeSplitQty(new BigDecimal("60.004"), new BigDecimal("20.001")), comparesEqualTo(new BigDecimal("0.001")));
	}
}
