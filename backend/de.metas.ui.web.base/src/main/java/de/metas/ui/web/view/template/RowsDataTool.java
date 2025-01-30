package de.metas.ui.web.view.template;

import com.google.common.collect.ImmutableMap;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


@UtilityClass
public class RowsDataTool
{
	public static <T extends IViewRow> Map<DocumentId, T> extractAllRows(final Collection<T> topLevelRows)
	{
		final ImmutableMap.Builder<DocumentId, T> allRows = ImmutableMap.builder();
		topLevelRows.forEach(topLevelRow -> {

			allRows.put(topLevelRow.getId(), topLevelRow);
			allRows.putAll(extractAllIncludedRows(topLevelRow));
		});

		return allRows.build();
	}

	private static <T extends IViewRow> Map<DocumentId, T> extractAllIncludedRows(@NonNull final T topLevelRow)
	{
		@SuppressWarnings("unchecked")
		final List<T> includedRows = (List<T>)topLevelRow.getIncludedRows();

		final ImmutableMap.Builder<DocumentId, T> resultOfThisInvocation = ImmutableMap.builder();
		for (final T includedRow : includedRows)
		{
			resultOfThisInvocation.put(includedRow.getId(), includedRow);
			resultOfThisInvocation.putAll(extractAllIncludedRows(includedRow));
		}

		return resultOfThisInvocation.build();
	}
}