/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.handlingunits.filter;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.ui.web.handlingunits.filter.HUIdsFilterData;
import de.metas.ui.web.handlingunits.filter.HuIdsFilterList;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class HUIdsFilterDataTest
{
	@Nested
	class acceptAll
	{
		@Test
		void acceptAll_test()
		{
			final HUIdsFilterData data = HUIdsFilterData.acceptAll();
			assertAcceptAll(data);
		}

		private void assertAcceptAll(final HUIdsFilterData data)
		{
			assertThat(data.isAcceptAll()).isTrue();
			assertThat(data.isAcceptNone()).isFalse();
			assertThat(data.getFixedHUIds()).contains(HuIdsFilterList.ALL);
		}

		@Test
		void acceptAll_then_mustID1()
		{
			final HUIdsFilterData data = HUIdsFilterData.acceptAll();
			data.mustHUIds(ImmutableSet.of(HuId.ofRepoId(1)));
			assertAcceptAll(data);
		}

		@Test
		void acceptAll_then_mustID1_then_shallNotID1()
		{
			final HUIdsFilterData data = HUIdsFilterData.acceptAll();
			data.mustHUIds(ImmutableSet.of(HuId.ofRepoId(1)));
			data.shallNotHUIds(ImmutableSet.of(HuId.ofRepoId(1)));
			assertThat(data.isAcceptAll()).isFalse();
			assertThat(data.isAcceptNone()).isFalse();
			assertThat(data.getFixedHUIds()).isEmpty();
		}

		@Test
		void acceptAll_then_shallNotID1()
		{
			final HUIdsFilterData data = HUIdsFilterData.acceptAll();
			data.shallNotHUIds(ImmutableSet.of(HuId.ofRepoId(1)));
			assertThat(data.isAcceptAll()).isFalse();
			assertThat(data.isAcceptNone()).isFalse();
			assertThat(data.getFixedHUIds()).isEmpty();
		}
	}

	@Nested
	class ofHUIds
	{
		@Test
		void acceptNone()
		{
			final HUIdsFilterData data = HUIdsFilterData.ofHUIds(ImmutableSet.of());
			assertThat(data.isAcceptAll()).isFalse();
			assertThat(data.isAcceptNone()).isTrue();
			assertThat(data.getFixedHUIds()).contains(HuIdsFilterList.NONE);
		}

		@Test
		void acceptNone_then_mustID1()
		{
			final HUIdsFilterData data = HUIdsFilterData.ofHUIds(ImmutableSet.of());
			data.mustHUIds(ImmutableSet.of(HuId.ofRepoId(1)));
			assertThat(data.isAcceptAll()).isFalse();
			assertThat(data.isAcceptNone()).isFalse();
			assertThat(data.getFixedHUIds()).contains(HuIdsFilterList.of(HuId.ofRepoId(1)));
		}

		@Test
		void acceptID1()
		{
			final HUIdsFilterData data = HUIdsFilterData.ofHUIds(ImmutableSet.of(HuId.ofRepoId(1)));
			assertThat(data.isAcceptAll()).isFalse();
			assertThat(data.isAcceptNone()).isFalse();
			assertThat(data.getFixedHUIds()).contains(HuIdsFilterList.of(HuId.ofRepoId(1)));
		}

		@Test
		void acceptID1_then_mustID2()
		{
			final HUIdsFilterData data = HUIdsFilterData.ofHUIds(ImmutableSet.of(HuId.ofRepoId(1)));
			data.mustHUIds(ImmutableSet.of(HuId.ofRepoId(2)));
			assertThat(data.isAcceptAll()).isFalse();
			assertThat(data.isAcceptNone()).isFalse();
			assertThat(data.getFixedHUIds()).contains(HuIdsFilterList.of(HuId.ofRepoId(1), HuId.ofRepoId(2)));
		}

		@Test
		void acceptID1_then_mustID2_then_shallNotID1()
		{
			final HUIdsFilterData data = HUIdsFilterData.ofHUIds(ImmutableSet.of(HuId.ofRepoId(1)));
			data.mustHUIds(ImmutableSet.of(HuId.ofRepoId(2)));
			data.shallNotHUIds(ImmutableSet.of(HuId.ofRepoId(1)));
			assertThat(data.isAcceptAll()).isFalse();
			assertThat(data.isAcceptNone()).isFalse();
			assertThat(data.getFixedHUIds()).contains(HuIdsFilterList.of(HuId.ofRepoId(2)));
		}
	}

	@Nested
	class ofHUQuery
	{
		private IHUQueryBuilder newHUQuery()
		{
			final IHUQueryBuilder huQuery = Mockito.mock(IHUQueryBuilder.class);
			Mockito.doReturn(huQuery).when(huQuery).copy();
			return huQuery;
		}

		@Test
		void emptyHUQuery()
		{
			final IHUQueryBuilder initialHUQuery = newHUQuery();
			final HUIdsFilterData data = HUIdsFilterData.ofHUQuery(initialHUQuery);
			assertThat(data.isAcceptAll()).isFalse();
			assertThat(data.isAcceptNone()).isFalse();
			assertThat(data.getFixedHUIds()).isEmpty();
			assertThat(data.getInitialHUQueryCopyOrNull()).isEqualTo(initialHUQuery);
		}
	}

	@Nested
	class isPossibleHighVolume
	{
		@Test
		void acceptAll()
		{
			final HUIdsFilterData data = HUIdsFilterData.acceptAll();
			assertThat(data.isPossibleHighVolume(Integer.MAX_VALUE)).isTrue();
		}

		@Test
		void acceptAll_then_mustID1_mustID2()
		{
			final HUIdsFilterData data = HUIdsFilterData.acceptAll();
			data.mustHUIds(ImmutableSet.of(HuId.ofRepoId(1), HuId.ofRepoId(2)));
			assertThat(data.isPossibleHighVolume(Integer.MAX_VALUE)).isTrue();
		}

		@Test
		void acceptNone()
		{
			final HUIdsFilterData data = HUIdsFilterData.ofHUIds(ImmutableSet.of());
			assertThat(data.isPossibleHighVolume(1)).isFalse();
		}
	}
}