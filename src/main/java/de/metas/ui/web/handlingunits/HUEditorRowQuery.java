package de.metas.ui.web.handlingunits;

import com.google.common.collect.ImmutableSet;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
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

/**
 * {@link HUEditorRow} query.
 * 
 * @author metas-dev <dev@metasfresh.com>
 */
@Value
public class HUEditorRowQuery
{
	/** User string filter (e.g. what user typed in the lookup field) */
	private final String stringFilter;

	/** If specified only the rows of given type are considered */
	private final HUEditorRowType rowType;

	/**
	 * M_HU_IDs to exclude.
	 * The HUs are excluded AFTER they are exploded,
	 * so if you exclude an M_HU_ID then you will not get it in the result but it's children will be part of the result.
	 */
	private final ImmutableSet<Integer> excludeHUIds;

	@Builder
	private HUEditorRowQuery(final String stringFilter,
			final HUEditorRowType rowType,
			@Singular final ImmutableSet<Integer> excludeHUIds)
	{
		this.stringFilter = stringFilter;
		this.rowType = rowType;

		if (excludeHUIds == null || excludeHUIds.isEmpty())
		{
			this.excludeHUIds = ImmutableSet.of();
		}
		else
		{
			this.excludeHUIds = excludeHUIds.stream().filter(huId -> huId > 0).collect(ImmutableSet.toImmutableSet());
		}
	}
}
