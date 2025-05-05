/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.ui.web.project.step;

import com.google.common.collect.Maps;
import de.metas.ui.web.view.template.IRowsData;
import de.metas.ui.web.view.template.SynchronizedRowsIndexHolder;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WOProjectStepResourceRows implements IRowsData<WOProjectStepResourceRow>
{
	public static WOProjectStepResourceRows cast(final IRowsData<WOProjectStepResourceRow> rowsData)
	{
		return (WOProjectStepResourceRows)rowsData;
	}

	private final ConcurrentHashMap<DocumentId, WOProjectStepResourceRow> rowsById;
	private final SynchronizedRowsIndexHolder<WOProjectStepResourceRow> rowsHolder;

	@NonNull
	private final WOProjectStepRowInvalidateService woProjectStepRowInvalidateService;

	@Builder
	private WOProjectStepResourceRows(
			@NonNull final List<WOProjectStepResourceRow> rows,
			@NonNull final WOProjectStepRowInvalidateService woProjectStepRowInvalidateService)
	{
		this.woProjectStepRowInvalidateService = woProjectStepRowInvalidateService;
		this.rowsById = new ConcurrentHashMap<>(Maps.uniqueIndex(rows, WOProjectStepResourceRow::getId));
		this.rowsHolder = SynchronizedRowsIndexHolder.of(rows);
	}

	@Override
	public Map<DocumentId, WOProjectStepResourceRow> getDocumentId2TopLevelRows()
	{
		return rowsHolder.getDocumentId2TopLevelRows();
	}

	@Override
	public DocumentIdsSelection getDocumentIdsToInvalidate(final TableRecordReferenceSet recordRefs)
	{
		return rowsById.keySet()
				.stream()
				.collect(DocumentIdsSelection.toDocumentIdsSelection());
	}

	@Override
	public void invalidateAll()
	{
		invalidate(DocumentIdsSelection.ALL);
	}

	@Override
	public void invalidate(final DocumentIdsSelection rowIds)
	{
		rowsHolder.compute(rows -> rows.replacingRows(rowIds, woProjectStepRowInvalidateService.getInvalidatedRows(rowsById)));
	}
}
