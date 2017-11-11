package de.metas.ui.web.handlingunits;

import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.util.Services;

import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.ui.web.handlingunits.HUEditorRowFilter.Select;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.stream.StreamUtils;
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

	protected final Stream<HUEditorRow> streamSelectedRows(@NonNull final HUEditorRowFilter filter)
	{
		final DocumentIdsSelection selectedDocumentIds = getSelectedDocumentIds();
		return getView()
				.streamByIds(selectedDocumentIds)
				.filter(row -> matches(row, filter));
	}

	protected final Stream<Integer> streamSelectedHUIds(@NonNull final Select select)
	{
		return streamSelectedHUIds(HUEditorRowFilter.select(select));
	}

	protected final Stream<Integer> streamSelectedHUIds(@NonNull final HUEditorRowFilter filter)
	{
		return streamSelectedRows(filter)
				.map(HUEditorRow::getM_HU_ID)
				.filter(huId -> huId > 0);
	}

	/**
	 * Gets <b>all</b> selected {@link HUEditorRow}s and loads the top level-HUs from those.
	 * I.e. this method does not rely on {@link HUEditorRow#isTopLevel()}, but checks the underlying HU.
	 *
	 * @param select
	 * @return
	 */
	protected final Stream<I_M_HU> streamSelectedHUs(@NonNull final Select select)
	{
		return streamSelectedHUs(HUEditorRowFilter.select(select));
	}

	protected final Stream<I_M_HU> streamSelectedHUs(@NonNull final HUEditorRowFilter filter)
	{
		final Stream<Integer> huIds = streamSelectedHUIds(HUEditorRowFilter.ALL);
		return StreamUtils.dice(huIds, 100)
				.flatMap(huIdsChunk -> toHUQueryBuilder(filter)
						.addOnlyHUIds(huIdsChunk)
						.createQuery()
						.stream(I_M_HU.class));
	}

	private static final IHUQueryBuilder toHUQueryBuilder(final HUEditorRowFilter filter)
	{
		return Services.get(IHandlingUnitsDAO.class)
				.createHUQueryBuilder()
				.setOnlyTopLevelHUs(filter.getSelect() == Select.ONLY_TOPLEVEL)
				.addHUStatusesToInclude(filter.getOnlyHUStatuses());
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
}
