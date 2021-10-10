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
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
@SuppressWarnings({ "OptionalUsedAsFieldOrParameterType", "OptionalAssignedToNull" })
public final class HUIdsFilterData
{
	/**
	 * Creates a new instance with the given {@code huIds}.
	 *
	 * @param huIds may be empty, but not null. Empty means that <b>no</b> HU will be matched.
	 */
	public static HUIdsFilterData ofHUIds(@NonNull final Collection<HuId> huIds) {return new HUIdsFilterData(huIds);}

	public static HUIdsFilterData ofHUQuery(@NonNull final IHUQueryBuilder initialHUQuery) {return new HUIdsFilterData(initialHUQuery);}

	public static HUIdsFilterData acceptAll() {return new HUIdsFilterData();}

	/**
	 * Important: {@code null} means "no restriction" (i.e. we can select allHUs) whereas empty means that no HU matches the filter.
	 */
	@Nullable private final HuIdsFilterList initialHUIds;
	@Nullable private final IHUQueryBuilder initialHUQuery;

	private final HashSet<HuId> mustHUIds;
	private final HashSet<HuId> shallNotHUIds;

	private transient Optional<HuIdsFilterList> _fixedHUIds = null; // lazy

	private HUIdsFilterData(@NonNull final Collection<HuId> initialHUIds)
	{
		this.initialHUIds = HuIdsFilterList.of(initialHUIds);
		this.initialHUQuery = null;

		mustHUIds = new HashSet<>();
		shallNotHUIds = new HashSet<>();
	}

	private HUIdsFilterData(@NonNull final IHUQueryBuilder initialHUQuery)
	{
		this.initialHUIds = null;
		this.initialHUQuery = initialHUQuery.copy();

		mustHUIds = new HashSet<>();
		shallNotHUIds = new HashSet<>();
	}

	/**
	 * Accept All constructor
	 */
	private HUIdsFilterData()
	{
		this.initialHUIds = HuIdsFilterList.ALL;
		this.initialHUQuery = null;

		mustHUIds = new HashSet<>();
		shallNotHUIds = new HashSet<>();
	}

	private HUIdsFilterData(@NonNull final HUIdsFilterData from)
	{
		this.initialHUIds = from.initialHUIds;
		this.initialHUQuery = from.initialHUQuery != null ? from.initialHUQuery.copy() : null;

		this.mustHUIds = new HashSet<>(from.mustHUIds);
		this.shallNotHUIds = new HashSet<>(from.shallNotHUIds);

		this._fixedHUIds = from._fixedHUIds;
	}

	public synchronized HUIdsFilterData copy() {return new HUIdsFilterData(this);}

	public synchronized boolean isAcceptAll()
	{
		final HuIdsFilterList huIds = getFixedHUIds().orElse(null);
		return huIds != null && huIds.isAll();
	}

	public synchronized boolean isAcceptNone()
	{
		final HuIdsFilterList huIds = getFixedHUIds().orElse(null);
		return huIds != null && huIds.isNone();
	}

	@Nullable
	public synchronized IHUQueryBuilder getInitialHUQueryCopyOrNull() {return initialHUQuery != null ? initialHUQuery.copy() : null;}

	public synchronized void mustHUIds(@NonNull final Collection<HuId> mustHUIdsToAdd)
	{
		if (mustHUIdsToAdd.isEmpty())
		{
			return;
		}

		mustHUIds.addAll(mustHUIdsToAdd);
		shallNotHUIds.removeAll(mustHUIdsToAdd);
		_fixedHUIds = null;
	}

	public void shallNotHUIds(@NonNull final Collection<HuId> shallNotHUIdsToAdd)
	{
		if (shallNotHUIdsToAdd.isEmpty())
		{
			return;
		}

		shallNotHUIds.addAll(shallNotHUIdsToAdd);
		mustHUIds.removeAll(shallNotHUIdsToAdd);
		_fixedHUIds = null;
	}

	/**
	 * @return fixed HU Ids; Optional.empty() means that the fixed HU Ids could not be determined (i.e. we have an initial HU query)
	 */
	public synchronized Optional<HuIdsFilterList> getFixedHUIds()
	{
		Optional<HuIdsFilterList> fixedHUIds = this._fixedHUIds;
		if (fixedHUIds == null)
		{
			fixedHUIds = this._fixedHUIds = Optional.ofNullable(computeFixedHUIds());
		}
		return fixedHUIds;
	}

	@Nullable
	private HuIdsFilterList computeFixedHUIds()
	{
		return convert(new CaseConverter<HuIdsFilterList>()
		{
			@Override
			public HuIdsFilterList acceptAll()
			{
				return HuIdsFilterList.ALL;
			}

			@Override
			public HuIdsFilterList acceptAllBut(@NonNull final Set<HuId> alwaysIncludeHUIds, @NonNull final Set<HuId> excludeHUIds)
			{
				if (excludeHUIds.isEmpty())
				{
					return HuIdsFilterList.ALL;
				}
				else
				{
					return null; // cannot compute
				}
			}

			@Override
			public HuIdsFilterList acceptNone()
			{
				return HuIdsFilterList.NONE;
			}

			@Override
			public HuIdsFilterList acceptOnly(@NonNull final HuIdsFilterList fixedHUIds, @NonNull final Set<HuId> alwaysIncludeHUIds)
			{
				return fixedHUIds;
			}

			@Override
			public HuIdsFilterList huQuery(@NonNull final IHUQueryBuilder initialHUQueryCopy, @NonNull final Set<HuId> alwaysIncludeHUIds, @NonNull final Set<HuId> excludeHUIds)
			{
				return null; // cannot compute
			}
		});
	}

	public boolean isPossibleHighVolume(final int highVolumeThreshold)
	{
		final Integer estimatedSize = estimateSize();
		return estimatedSize == null || estimatedSize > highVolumeThreshold;
	}

	@Nullable
	private Integer estimateSize()
	{
		return getFixedHUIds().map(HuIdsFilterList::estimateSize).orElse(null);
	}

	interface CaseConverter<T>
	{
		T acceptAll();

		T acceptAllBut(@NonNull Set<HuId> alwaysIncludeHUIds, @NonNull Set<HuId> excludeHUIds);

		T acceptNone();

		T acceptOnly(@NonNull HuIdsFilterList fixedHUIds, @NonNull Set<HuId> alwaysIncludeHUIds);

		T huQuery(@NonNull IHUQueryBuilder initialHUQueryCopy, @NonNull Set<HuId> alwaysIncludeHUIds, @NonNull Set<HuId> excludeHUIds);
	}

	public synchronized <T> T convert(@NonNull final CaseConverter<T> converter)
	{
		if (initialHUQuery == null)
		{
			if (initialHUIds == null)
			{
				throw new IllegalStateException("initialHUIds shall not be null for " + this);
			}
			else if (initialHUIds.isAll())
			{
				if (mustHUIds.isEmpty() && shallNotHUIds.isEmpty())
				{
					return converter.acceptAll();
				}
				else
				{
					return converter.acceptAllBut(ImmutableSet.copyOf(mustHUIds), ImmutableSet.copyOf(shallNotHUIds));
				}
			}
			else
			{
				final ImmutableSet<HuId> fixedHUIds = Stream.concat(
								initialHUIds.stream(),
								mustHUIds.stream())
						.distinct()
						.filter(huId -> !shallNotHUIds.contains(huId)) // not excluded
						.collect(ImmutableSet.toImmutableSet());

				if (fixedHUIds.isEmpty())
				{
					return converter.acceptNone();
				}
				else
				{
					return converter.acceptOnly(HuIdsFilterList.of(fixedHUIds), ImmutableSet.copyOf(mustHUIds));
				}
			}
		}
		else
		{
			return converter.huQuery(initialHUQuery.copy(), ImmutableSet.copyOf(mustHUIds), ImmutableSet.copyOf(shallNotHUIds));
		}
	}
}
