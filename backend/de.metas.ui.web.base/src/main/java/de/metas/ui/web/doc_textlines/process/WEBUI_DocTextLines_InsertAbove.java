package de.metas.ui.web.doc_textlines.process;

import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.doc_textlines.DocTextLinesView;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import lombok.NonNull;

import javax.annotation.Nullable;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2026 metas GmbH
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
 * The {@link de.metas.ui.web.doc_textlines.DocTextLinesView} quick action that inserts a new, empty text row
 * immediately above the selected row -- or as the document's only row, when the view has no rows at all to
 * select. Thin glue: all persistence and merged-order arithmetic live behind
 * {@link DocTextLinesView#insertRowAbove(DocumentId, String)}.
 */
public class WEBUI_DocTextLines_InsertAbove extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	@Override
	protected DocTextLinesView getView()
	{
		return DocTextLinesView.cast(super.getView());
	}

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		return checkInsertAbovePreconditions(getView(), getSelectedRowIds());
	}

	/**
	 * A row must be selected to insert above -- except when the view has no rows at all to select, which is
	 * how this action also works on an empty document.
	 * <p>
	 * Public so it is directly unit-testable without the {@code JavaProcess} parameter/view-loading machinery,
	 * same rationale as {@link #insertAbove}.
	 */
	public static ProcessPreconditionsResolution checkInsertAbovePreconditions(
			@NonNull final DocTextLinesView view,
			@NonNull final DocumentIdsSelection selectedRowIds)
	{
		if (selectedRowIds.isMoreThanOneDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		if (selectedRowIds.isEmpty() && view.size() > 0)
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		final DocumentId referenceRowId = selectedRowIds.isEmpty() ? null : selectedRowIds.getSingleDocumentId();

		insertAbove(getView(), referenceRowId);

		return MSG_OK;
	}

	/**
	 * Public so it is directly unit-testable without the {@code JavaProcess} parameter/view-loading machinery.
	 *
	 * @param referenceRowId the row to insert above; {@code null} only when the document has no rows at all.
	 */
	public void insertAbove(@NonNull final DocTextLinesView view, @Nullable final DocumentId referenceRowId)
	{
		view.insertRowAbove(referenceRowId, "");
	}
}
