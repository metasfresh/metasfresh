package de.metas.ui.web.handlingunits;

import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.model.X_M_HU;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import lombok.NonNull;
import lombok.Singular;

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

@lombok.Value
public final class HUEditorRowFilter
{
	public static final HUEditorRowFilter ALL = builder().select(Select.ALL).build();

	public static final HUEditorRowFilter select(final Select select)
	{
		return builder().select(select).build();
	}

	public static final HUEditorRowFilter onlyRowIds(DocumentIdsSelection rowIds)
	{
		if (rowIds.isAll())
		{
			return ALL;
		}
		else
		{
			return builder().onlyRowIdsFromSelection(rowIds).build();
		}
	}

	private final Select select;

	public enum Select
	{
		ONLY_TOPLEVEL, ALL, LU, TU, CU
	}

	private final ImmutableSet<HUEditorRowId> onlyRowIds;

	private final ImmutableSet<String> onlyHUStatuses;

	/**
	 * Note: this list is never {@code null}, empty means "no restriction".
	 */
	private final ImmutableSet<String> excludeHUStatuses;

	/**
	 * M_HU_IDs to exclude.
	 * The HUs are excluded AFTER they are exploded,
	 * so if you exclude an M_HU_ID then you will not get it in the result but it's children will be part of the result.
	 *
	 * Note: this list is never {@code null}, empty means "no restriction".
	 */
	private final ImmutableSet<Integer> excludeHUIds;

	/** User string filter (e.g. what user typed in the lookup field) */
	private final String userInputFilter;

	@lombok.Builder(builderClassName = "Builder", toBuilder = true)
	private HUEditorRowFilter(
			@Nullable final Select select,
			@NonNull @Singular final Set<HUEditorRowId> onlyRowIds,
			@NonNull @Singular final ImmutableSet<String> onlyHUStatuses,
			@NonNull @Singular final ImmutableSet<String> excludeHUStatuses,
			@NonNull @Singular final ImmutableSet<Integer> excludeHUIds,
			@Nullable final String userInputFilter)
	{
		this.select = select != null ? select : Select.ALL;
		this.onlyRowIds = ImmutableSet.copyOf(onlyRowIds);
		this.onlyHUStatuses = onlyHUStatuses;
		this.excludeHUStatuses = excludeHUStatuses;
		this.userInputFilter = userInputFilter;

		if (excludeHUIds == null || excludeHUIds.isEmpty())
		{
			this.excludeHUIds = ImmutableSet.of();
		}
		else
		{
			this.excludeHUIds = excludeHUIds.stream().filter(huId -> huId > 0).collect(ImmutableSet.toImmutableSet());
		}
	}

	public HUEditorRowFilter andOnlyRowIds(final DocumentIdsSelection rowIds)
	{
		if (rowIds.isAll())
		{
			// nothing to do
			return this;
		}
		else
		{
			return toBuilder().onlyRowIdsFromSelection(rowIds).build();
		}
	}

	public static final class Builder
	{
		public Builder onlyActiveHUs()
		{
			onlyHUStatus(X_M_HU.HUSTATUS_Active);
			return this;
		}

		public Builder onlyRowIdsFromSelection(DocumentIdsSelection rowIds)
		{
			if (rowIds.isAll())
			{
				// nothing
			}
			else if (rowIds.isEmpty())
			{
				throw new AdempiereException("Empty rowIds is not allowed");
			}
			else
			{
				onlyRowIds(rowIds.toSet(HUEditorRowId::ofDocumentId));
			}
			return this;
		}
	}
}
