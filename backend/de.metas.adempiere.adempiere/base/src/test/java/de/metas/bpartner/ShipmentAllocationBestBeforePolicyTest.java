package de.metas.bpartner;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class ShipmentAllocationBestBeforePolicyTest
{
	private final BestBeforeDateHolder bb_2019_09_01 = BestBeforeDateHolder.of(LocalDate.of(2019, Month.SEPTEMBER, 1));
	private final BestBeforeDateHolder bb_2019_09_02 = BestBeforeDateHolder.of(LocalDate.of(2019, Month.SEPTEMBER, 2));
	private final BestBeforeDateHolder bb_null = BestBeforeDateHolder.of(null);

	@Test
	public void comparator_ExpiringFirst()
	{
		final List<BestBeforeDateHolder> result = Stream.of(bb_2019_09_01, bb_2019_09_02)
				.sorted(ShipmentAllocationBestBeforePolicy.Expiring_First.comparator(BestBeforeDateHolder::getBestBeforeDate))
				.collect(Collectors.toList());
		assertThat(result).containsExactly(bb_2019_09_01, bb_2019_09_02);
	}

	@Test
	public void comparator_ExpiringFirst_NullsLast()
	{
		final List<BestBeforeDateHolder> result = Stream.of(bb_2019_09_01, bb_null)
				.sorted(ShipmentAllocationBestBeforePolicy.Expiring_First.comparator(BestBeforeDateHolder::getBestBeforeDate))
				.collect(Collectors.toList());
		assertThat(result).containsExactly(bb_2019_09_01, bb_null);
	}

	@Test
	public void comparator_ExpiringLast()
	{
		final List<BestBeforeDateHolder> result = Stream.of(bb_2019_09_01, bb_2019_09_02)
				.sorted(ShipmentAllocationBestBeforePolicy.Newest_First.comparator(BestBeforeDateHolder::getBestBeforeDate))
				.collect(Collectors.toList());
		assertThat(result).containsExactly(bb_2019_09_02, bb_2019_09_01);
	}

	@Test
	public void comparator_ExpiringLast_NullsLast()
	{
		final List<BestBeforeDateHolder> result = Stream.of(bb_2019_09_01, bb_null)
				.sorted(ShipmentAllocationBestBeforePolicy.Newest_First.comparator(BestBeforeDateHolder::getBestBeforeDate))
				.collect(Collectors.toList());
		assertThat(result).containsExactly(bb_2019_09_01, bb_null);
	}

	@Value(staticConstructor = "of")
	private static class BestBeforeDateHolder
	{
		LocalDate bestBeforeDate;
	}
}
