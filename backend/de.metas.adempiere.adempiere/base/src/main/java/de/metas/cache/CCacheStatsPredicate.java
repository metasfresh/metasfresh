/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.cache;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.function.Predicate;

@ToString
public class CCacheStatsPredicate implements Predicate<CCacheStats>
{
	@Nullable private final String cacheNameContainsLC;
	private final int minSize;
	@NonNull private final ImmutableSet<CacheLabel> labels;

	@Builder
	private CCacheStatsPredicate(
			@Nullable final String cacheNameContains,
			@Nullable final Integer minSize,
			@Nullable final String labels)
	{
		this.cacheNameContainsLC = StringUtils.trimBlankToOptional(cacheNameContains)
				.map(String::toLowerCase)
				.orElse(null);
		this.minSize = minSize != null ? Math.max(minSize, 0) : 0;
		this.labels = parseLabels(labels);
	}

	private static ImmutableSet<CacheLabel> parseLabels(final String labels)
	{
		final String labelsNorm = StringUtils.trimBlankToNull(labels);
		if (labelsNorm == null)
		{
			return ImmutableSet.of();
		}

		return Splitter.on(",")
				.trimResults()
				.omitEmptyStrings()
				.splitToStream(labelsNorm)
				.map(CacheLabel::ofTableName)
				.collect(ImmutableSet.toImmutableSet());
	}

	@Override
	public boolean test(@NonNull final CCacheStats stats)
	{
		if (cacheNameContainsLC != null && !stats.getName().toLowerCase().contains(cacheNameContainsLC))
		{
			return false;
		}

		if (minSize > 0 && stats.getSize() < minSize)
		{
			return false;
		}

		if (!labels.isEmpty() && !stats.getLabels().containsAll(labels))
		{
			return false;
		}

		return true;
	}
}
