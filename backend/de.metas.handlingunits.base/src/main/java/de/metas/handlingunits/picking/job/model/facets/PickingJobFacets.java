/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.handlingunits.picking.job.model.facets;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.picking.api.Packageable;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collector;

@Value
@Builder
public class PickingJobFacets
{
	public static PickingJobFacets EMPTY = PickingJobFacets.builder().build();

	@NonNull @Builder.Default ImmutableSet<PickingJobFacet> facets = ImmutableSet.of();

	public static Collector<Packageable, ?, PickingJobFacets> collectFromPackageables(@NonNull final CollectingParameters parameters)
	{
		return PickingJobFacetsAccumulator.collect(parameters);
	}

	public <T extends PickingJobFacet, R> ImmutableList<R> toList(@NonNull final Class<T> type, @NonNull Function<T, R> mapper)
	{
		return facets.stream()
				.map(facet -> facet.asTypeOrNull(type))
				.filter(Objects::nonNull)
				.map(mapper)
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
	}
}
