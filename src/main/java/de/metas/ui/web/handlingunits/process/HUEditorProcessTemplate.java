package de.metas.ui.web.handlingunits.process;

import java.util.List;
import java.util.Set;

import org.adempiere.util.Services;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import lombok.NonNull;

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

	enum Select
	{
		ONLY_TOPLEVEL, ALL
	}

	/**
	 * 
	 * @param select if {@link Select#ONLY_TOPLEVEL} then the method only returns row with {@link HUEditorRow#isTopLevel()} {@code == true};
	 * @return
	 */
	protected final List<HUEditorRow> getSelectedRows(@NonNull final Select select)
	{
		final DocumentIdsSelection selectedDocumentIds = getSelectedDocumentIds();
		final List<HUEditorRow> allRows = getView().getByIds(selectedDocumentIds);

		if (select == Select.ALL)
		{
			return allRows;
		}
		return allRows.stream()
				.filter(HUEditorRow::isTopLevel)
				.collect(ImmutableList.toImmutableList());

	}

	/**
	 * Calls {@link #getSelectedRows(Select)} and returns the {@link HUEditorRow}s' {@code M_HU_ID}s.
	 *
	 * @param select
	 * @return
	 */
	protected final Set<Integer> getSelectedHUIds(@NonNull final Select select)
	{
		return getSelectedRows(select)
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
		// get all IDs, we we will check for ourselves if they are toplevel or not
		final Set<Integer> huIds = getSelectedHUIds(Select.ALL);

		return Services.get(IHandlingUnitsDAO.class)
				.createHUQueryBuilder().addOnlyHUIds(huIds)
				.setOnlyTopLevelHUs(select == Select.ONLY_TOPLEVEL)
				.createQuery()
				.list(I_M_HU.class);
	}
}
