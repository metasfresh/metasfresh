/*
 * #%L
 * de.metas.picking.rest-api
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

package de.metas.picking.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.handlingunits.picking.job.model.PickingJobFacetGroup;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;

@EqualsAndHashCode
public class PickingFiltersList
{
	public static final PickingFiltersList EMPTY = new PickingFiltersList(ImmutableList.of());

	@NonNull @Getter private final ImmutableList<PickingJobFacetGroup> groupsInOrder;
	@NonNull private final ImmutableMap<PickingJobFacetGroup, PickingFilter> filtersByGroup;

	private PickingFiltersList(@NonNull final List<PickingFilter> list)
	{
		this.groupsInOrder = list.stream().sorted(Comparator.comparing(PickingFilter::getSeqNo)).map(PickingFilter::getOption).distinct().collect(ImmutableList.toImmutableList());
		this.filtersByGroup = Maps.uniqueIndex(list, PickingFilter::getOption);

	}

	public static PickingFiltersList ofList(@Nullable final List<PickingFilter> list)
	{
		return list != null && !list.isEmpty() ? new PickingFiltersList(list) : EMPTY;
	}

	public static Collector<PickingFilter, ?, PickingFiltersList> collect() {return GuavaCollectors.collectUsingListAccumulator(PickingFiltersList::ofList);}

	public boolean isEmpty() {return filtersByGroup.isEmpty();}
}
