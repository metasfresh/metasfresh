package de.metas.ui.web.handlingunits;

import java.util.Set;

import de.metas.handlingunits.HuId;
import de.metas.ui.web.view.descriptor.SqlViewRowIdsConverter;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;

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

public final class HUSqlViewRowIdsConverter implements SqlViewRowIdsConverter
{
	public static final transient HUSqlViewRowIdsConverter instance = new HUSqlViewRowIdsConverter();

	private HUSqlViewRowIdsConverter()
	{
	}

	@Override
	public Set<Integer> convertToRecordIds(final DocumentIdsSelection rowIds)
	{
		final Set<HuId> huIds = HUEditorRowId.extractHUIdsOnly(rowIds);
		return HuId.toRepoIds(huIds);
	}
}
