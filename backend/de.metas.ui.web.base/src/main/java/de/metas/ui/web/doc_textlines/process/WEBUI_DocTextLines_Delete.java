package de.metas.ui.web.doc_textlines.process;

import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.doc_textlines.DocTextLinesRow;
import de.metas.ui.web.doc_textlines.DocTextLinesView;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import lombok.NonNull;

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
 * The {@link de.metas.ui.web.doc_textlines.DocTextLinesView} quick action that deletes the selected text row.
 * Thin glue: all persistence and locking live behind {@link DocTextLinesView#deleteRow(DocumentId)}.
 */
public class WEBUI_DocTextLines_Delete extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	@Override
	protected DocTextLinesView getView()
	{
		return DocTextLinesView.cast(super.getView());
	}

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		return checkDeletePreconditions(getView(), getSelectedRowIds());
	}

	/**
	 * Exactly one row must be selected, and it must be a text row -- article rows are read-only, shown only for
	 * orientation.
	 * <p>
	 * Public so it is directly unit-testable without the {@code JavaProcess} parameter/view-loading machinery,
	 * same rationale as {@link de.metas.ui.web.doc_textlines.process.WEBUI_DocTextLines_InsertAbove}.
	 */
	public static ProcessPreconditionsResolution checkDeletePreconditions(
			@NonNull final DocTextLinesView view,
			@NonNull final DocumentIdsSelection selectedRowIds)
	{
		if (selectedRowIds.isMoreThanOneDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		if (selectedRowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final DocTextLinesRow row = view.getById(selectedRowIds.getSingleDocumentId());
		if (row.isArticleLine())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("article lines are read-only and cannot be deleted");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final DocumentId rowId = getSelectedRowIds().getSingleDocumentId();
		delete(getView(), rowId);

		return MSG_OK;
	}

	/** Public so it is directly unit-testable without the {@code JavaProcess} parameter/view-loading machinery. */
	public void delete(@NonNull final DocTextLinesView view, @NonNull final DocumentId rowId)
	{
		view.deleteRow(rowId);
	}
}
