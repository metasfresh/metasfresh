package de.metas.ui.web.view;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.view.descriptor.SqlViewRowsWhereClause;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.model.DocumentQueryOrderByList;
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

public interface ViewRowIdsOrderedSelectionFactory
{
	ViewRowIdsOrderedSelection createOrderedSelection(ViewEvaluationCtx viewEvalCtx,
			ViewId viewId,
			DocumentFilterList filters,
			final DocumentQueryOrderByList orderBys,
			final boolean applySecurityRestrictions,
			SqlDocumentFilterConverterContext context);

	/**
	 * @return a new {@link ViewRowIdsOrderedSelection} from a given <code>fromSelection</code> ordered by <code>orderBys</code>
	 */
	ViewRowIdsOrderedSelection createOrderedSelectionFromSelection(
			ViewEvaluationCtx viewEvalCtx,
			ViewRowIdsOrderedSelection fromSelection,
			DocumentFilterList filters,
			DocumentQueryOrderByList orderBys,
			final SqlDocumentFilterConverterContext filterConverterCtx);

	SqlViewRowsWhereClause getSqlWhereClause(ViewId viewId, DocumentIdsSelection rowIds);

	ViewRowIdsOrderedSelection addRowIdsToSelection(ViewRowIdsOrderedSelection selection, DocumentIdsSelection rowIds);

	ViewRowIdsOrderedSelection removeRowIdsFromSelection(ViewRowIdsOrderedSelection selection, DocumentIdsSelection rowIds);

	boolean containsAnyOfRowIds(ViewRowIdsOrderedSelection selection, DocumentIdsSelection rowIds);

	default void deleteSelection(@NonNull final String selectionId)
	{
		deleteSelections(ImmutableSet.of(selectionId));
	}

	void deleteSelections(Set<String> selectionIds);

	void scheduleDeleteSelections(Set<String> selectionIds);
}
