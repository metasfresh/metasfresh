package de.metas.ui.web.handlingunits;

import java.util.List;
import java.util.Set;

import de.metas.handlingunits.HuId;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.handlingunits.HUIdsFilterHelper.HUIdsFilterData;
import de.metas.ui.web.view.ViewEvaluationCtx;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewRowIdsOrderedSelection;
import de.metas.ui.web.view.descriptor.SqlViewRowIdsConverter;
import de.metas.ui.web.view.descriptor.SqlViewRowsWhereClause;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.model.DocumentQueryOrderByList;
import de.metas.util.collections.PagedIterator.Page;

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

public interface HUEditorViewRepository
{
	void invalidateCache();

	ViewRowIdsOrderedSelection createSelection(
			ViewEvaluationCtx viewEvalCtx,
			ViewId viewId,
			DocumentFilterList filters,
			DocumentQueryOrderByList orderBys,
			SqlDocumentFilterConverterContext filterConverterCtx);

	ViewRowIdsOrderedSelection createSelectionFromSelection(
			ViewEvaluationCtx viewEvalCtx,
			ViewRowIdsOrderedSelection fromSelection,
			DocumentQueryOrderByList orderBys);

	void deleteSelection(ViewRowIdsOrderedSelection selection);

	List<HUEditorRow> retrieveHUEditorRows(Set<HuId> huIds, HUEditorRowFilter filter);

	/**
	 * Retrieves the {@link HUEditorRow} hierarchy for given M_HU_ID, even if that M_HU_ID is not in scope.
	 *
	 * @param huId
	 * @return {@link HUEditorRow} or null if the huId negative or zero.
	 */
	HUEditorRow retrieveForHUId(HuId huId);

	Set<HuId> retrieveHUIdsEffective(HUIdsFilterData huIdsFilter,
			DocumentFilterList filters,
			SqlDocumentFilterConverterContext context);

	Page<HuId> retrieveHUIdsPage(ViewEvaluationCtx viewEvalCtx, ViewRowIdsOrderedSelection selection, int firstRow, int maxRows);

	ViewRowIdsOrderedSelection addRowIdsToSelection(ViewRowIdsOrderedSelection selection, DocumentIdsSelection rowIdsToAdd);

	ViewRowIdsOrderedSelection removeRowIdsFromSelection(ViewRowIdsOrderedSelection selection, DocumentIdsSelection rowIdsToRemove);

	boolean containsAnyOfRowIds(ViewRowIdsOrderedSelection selection, DocumentIdsSelection rowIds);

	SqlViewRowsWhereClause buildSqlWhereClause(ViewRowIdsOrderedSelection selection, DocumentIdsSelection rowIds);

	SqlViewRowIdsConverter getRowIdsConverter();

	/** Caches the given {@code huIds} in one go. Advised to use this prior to repeated invocations of {@link #retrieveForHUId(HuId)}. */
	void warmUp(Set<HuId> huIds);
}
