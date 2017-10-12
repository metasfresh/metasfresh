package de.metas.ui.web.handlingunits;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.adempiere.util.Services;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate.HUEditorRowFilter.Select;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
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

/**
 * A {@link ViewBasedProcessTemplate} implementation template which add convenient functionalities around {@link HUEditorView}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public abstract class HUEditorProcessTemplate extends ViewBasedProcessTemplate
{
	@Override
	protected final HUEditorView getView()
	{
		return super.getView(HUEditorView.class);
	}

	@Override
	protected final HUEditorRow getSingleSelectedRow()
	{
		return HUEditorRow.cast(super.getSingleSelectedRow());
	}

	protected final List<HUEditorRow> getSelectedRows(@NonNull final HUEditorRowFilter filter)
	{
		final DocumentIdsSelection selectedDocumentIds = getSelectedDocumentIds();
		final List<HUEditorRow> allRows = getView().getByIds(selectedDocumentIds);

		return allRows.stream()
				.filter(toPredicate(filter))
				.collect(ImmutableList.toImmutableList());

	}

	/**
	 * Calls {@link #getSelectedRows(Select)} and returns the {@link HUEditorRow}s' {@code M_HU_ID}s.
	 *
	 * @param select
	 */
	protected final Set<Integer> getSelectedHUIds(@NonNull final Select select)
	{
		return getSelectedHUIds(HUEditorRowFilter.select(select));
	}

	protected final Set<Integer> getSelectedHUIds(@NonNull final HUEditorRowFilter filter)
	{
		return getSelectedRows(filter)
				.stream()
				.map(HUEditorRow::getM_HU_ID)
				.filter(huId -> huId > 0)
				.collect(ImmutableSet.toImmutableSet());
	}

	/**
	 * Gets <b>all</b> selected {@link HUEditorRow}s and loads the top level-HUs from those.
	 * I.e. this method does not rely on {@link HUEditorRow#isTopLevel()}, but checks the underlying HU.
	 * 
	 * @param select
	 * @return
	 */
	protected final List<I_M_HU> getSelectedHUs(@NonNull final Select select)
	{
		return getSelectedHUs(HUEditorRowFilter.select(select));
	}

	protected final List<I_M_HU> getSelectedHUs(@NonNull final HUEditorRowFilter filter)
	{
		// get all IDs, we we will enforce the filter using HUQueryBuilder
		final Set<Integer> huIds = getSelectedHUIds(HUEditorRowFilter.ALL);

		return toHUQueryBuilder(filter)
				.addOnlyHUIds(huIds)
				.createQuery()
				.list(I_M_HU.class);
	}

	private static final IHUQueryBuilder toHUQueryBuilder(final HUEditorRowFilter filter)
	{
		return Services.get(IHandlingUnitsDAO.class)
				.createHUQueryBuilder()
				.setOnlyTopLevelHUs(filter.getSelect() == Select.ONLY_TOPLEVEL)
				.addHUStatusesToInclude(filter.getOnlyHUStatuses());
	}

	private static final Predicate<HUEditorRow> toPredicate(@NonNull final HUEditorRowFilter filter)
	{
		return row -> matches(row, filter);
	}

	private static final boolean matches(final HUEditorRow row, final HUEditorRowFilter filter)
	{
		final Select select = filter.getSelect();
		if (select == Select.ONLY_TOPLEVEL && !row.isTopLevel())
		{
			return false;
		}

		final Set<String> onlyHUStatuses = filter.getOnlyHUStatuses();
		final boolean huStatusDoesntMatter = onlyHUStatuses.isEmpty();
		if (huStatusDoesntMatter)
		{
			return true;
		}

		return onlyHUStatuses.contains(row.getHUStatusKey());
	}

	@lombok.Builder(toBuilder = true)
	@lombok.Value
	public static final class HUEditorRowFilter
	{
		@NonNull
		private final Select select;

		@Singular
		private final ImmutableSet<String> onlyHUStatuses;

		public enum Select
		{
			ONLY_TOPLEVEL, ALL
		}

		public static final HUEditorRowFilter ALL = builder().select(Select.ALL).build();

		public static final HUEditorRowFilter select(Select select)
		{
			return builder().select(select).build();
		}

		public static final class HUEditorRowFilterBuilder
		{
			public HUEditorRowFilterBuilder onlyActiveHUs()
			{
				onlyHUStatus(X_M_HU.HUSTATUS_Active);
				return this;
			}
		}
	}
}
