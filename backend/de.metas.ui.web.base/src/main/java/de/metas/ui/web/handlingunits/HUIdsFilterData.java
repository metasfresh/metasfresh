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

package de.metas.ui.web.handlingunits;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUQueryBuilder;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@ToString
public final class HUIdsFilterData
{
	/**
	 * Creates a new instance with the given {@code huIds}.
	 *
	 * @param huIds may be empty, but not null. Empty means that <b>no</b> HU will be matched.
	 */
	public static HUIdsFilterData ofHUIds(@NonNull final Collection<HuId> huIds) { return new HUIdsFilterData(huIds); }

	public static HUIdsFilterData ofHUQuery(@NonNull final IHUQueryBuilder initialHUQuery) { return new HUIdsFilterData(initialHUQuery); }

	public static HUIdsFilterData acceptAll() { return new HUIdsFilterData(); }

	/**
	 * Important: {@code null} means "no restriction" (i.e. we can select allHUs) whereas empty means that no HU matches the filter.
	 */
	@Nullable private final ImmutableSet<HuId> initialHUIds;
	@Nullable private final IHUQueryBuilder initialHUQuery;

	private final HashSet<HuId> mustHUIds;
	private final HashSet<HuId> shallNotHUIds;

	private HUIdsFilterData(@NonNull final Collection<HuId> initialHUIds)
	{
		this.initialHUIds = ImmutableSet.copyOf(initialHUIds);
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
		this.initialHUIds = null;
		this.initialHUQuery = null;
		mustHUIds = new HashSet<>();
		shallNotHUIds = new HashSet<>();
	}

	private HUIdsFilterData(@NonNull final HUIdsFilterData from)
	{
		initialHUIds = from.initialHUIds;
		initialHUQuery = from.initialHUQuery != null ? from.initialHUQuery.copy() : null;

		mustHUIds = new HashSet<>(from.mustHUIds);
		shallNotHUIds = new HashSet<>(from.shallNotHUIds);
	}

	public HUIdsFilterData copy() { return new HUIdsFilterData(this); }

	public boolean isAcceptAll() { return initialHUQuery == null && initialHUIds == null && mustHUIds.isEmpty(); }

	public boolean isAcceptNone()
	{
		return initialHUQuery == null
				&& getFixedHUIds().filter(ImmutableSet::isEmpty).isPresent();
	}

	public boolean hasNoInitialHUQuery() { return initialHUQuery == null; }

	@Nullable
	public IHUQueryBuilder getInitialHUQueryOrNull() { return initialHUQuery != null ? initialHUQuery.copy() : null; }

	public void mustHUIds(@NonNull final Collection<HuId> mustHUIdsToAdd)
	{
		if (mustHUIdsToAdd.isEmpty())
		{
			return;
		}

		mustHUIds.addAll(mustHUIdsToAdd);
		shallNotHUIds.removeAll(mustHUIdsToAdd);
	}

	public ImmutableSet<HuId> getShallNotHUIds() { return ImmutableSet.copyOf(shallNotHUIds); }

	public void shallNotHUIds(@NonNull final Collection<HuId> shallNotHUIdsToAdd)
	{
		if (shallNotHUIdsToAdd.isEmpty())
		{
			return;
		}

		shallNotHUIds.addAll(shallNotHUIdsToAdd);
		mustHUIds.removeAll(shallNotHUIdsToAdd);
	}

	/**
	 * @return fixed HU Ids; Optional.empty() means that the fixed HU Ids could not be determined (i.e. we have an initial HU query too)
	 */
	public Optional<ImmutableSet<HuId>> getFixedHUIds()
	{
		// If there is an initialHUQuery specified we don't know the effective HU Ids
		if (initialHUQuery != null)
		{
			return Optional.empty();
		}

		if (initialHUIds == null && mustHUIds.isEmpty())
		{
			return Optional.empty();
		}

		final ImmutableSet<HuId> fixedHUIds = Stream.concat(
				initialHUIds != null ? initialHUIds.stream() : Stream.empty(),
				mustHUIds.stream())
				.distinct()
				.filter(huId -> !shallNotHUIds.contains(huId)) // not excluded
				.collect(ImmutableSet.toImmutableSet());

		return Optional.of(fixedHUIds);
	}

	public boolean isPossibleHighVolume(final int highVolumeThreshold)
	{
		final Integer estimatedSize = getFixedHUIds().map(Set::size).orElse(null);
		return estimatedSize == null || estimatedSize > highVolumeThreshold;
	}
}
