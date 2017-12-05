package de.metas.ui.web.view.descriptor;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import lombok.experimental.UtilityClass;

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

@UtilityClass
public final class SqlViewRowIdsConverters
{
	public static final transient SqlViewRowIdsConverter TO_INT_STRICT = new StrictToIntConverter();
	public static final transient SqlViewRowIdsConverter TO_INT_EXCLUDING_STRINGS = new ToIntExcludingStringsConverter();

	private final class StrictToIntConverter implements SqlViewRowIdsConverter
	{
		@Override
		public Set<Integer> convertToRecordIds(DocumentIdsSelection rowIds)
		{
			return rowIds.toIntSet();
		}
	}

	private static final class ToIntExcludingStringsConverter implements SqlViewRowIdsConverter
	{
		@Override
		public Set<Integer> convertToRecordIds(final DocumentIdsSelection rowIds)
		{
			return rowIds.stream()
					.filter(DocumentId::isInt) // exclude non-int values
					.map(DocumentId::toInt)
					.collect(ImmutableSet.toImmutableSet());
		}
	}

}
