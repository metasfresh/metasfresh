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

package de.metas.cache.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.metas.cache.CCacheStatsOrderBy;
import de.metas.cache.CCacheStatsPredicate;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Optional;

@Value
@Builder
@Jacksonized
public class JsonCacheStatsQuery
{
	@Nullable String cacheName;
	@Nullable Integer minSize;
	@Nullable String labels;
	@Nullable String orderByString;

	@JsonIgnore
	public CCacheStatsPredicate toCCacheStatsPredicate()
	{
		return CCacheStatsPredicate.builder()
				.cacheNameContains(cacheName)
				.minSize(minSize)
				.labels(labels)
				.build();
	}

	@JsonIgnore
	public Optional<CCacheStatsOrderBy> toCCacheStatsOrderBy()
	{
		return CCacheStatsOrderBy.parse(orderByString);
	}
}
