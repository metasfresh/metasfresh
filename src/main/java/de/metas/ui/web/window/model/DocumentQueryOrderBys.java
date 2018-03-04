package de.metas.ui.web.window.model;

import java.util.Comparator;
import java.util.List;

import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.window.model.DocumentQueryOrderBy.FieldValueExtractor;
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

public class DocumentQueryOrderBys
{
	public static Comparator<IViewRow> asComparator(@NonNull final List<DocumentQueryOrderBy> orderBys)
	{
		final FieldValueExtractor<IViewRow> fieldValueExtractor = (row, fieldName) -> row
				.getFieldNameAndJsonValues()
				.get(fieldName);

		// used in case orderBys is empty or whatever else goes wrong
		final Comparator<IViewRow> noopComparator = (o1, o2) -> 0;

		return orderBys.stream()
				.map(orderBy -> orderBy.asComparator(fieldValueExtractor))
				.reduce(Comparator::thenComparing)
				.orElse(noopComparator);
	}
}
