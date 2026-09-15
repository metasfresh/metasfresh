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
 * The {@link de.metas.ui.web.doc_textlines.DocTextLinesView} quick action that moves the selected text row
 * later in the merged order, exchanging it with the row immediately below it -- an article row's own position
 * is never touched, so exchanging with one is a reposition of the text row alone. Thin glue: all persistence
 * and locking live behind {@link DocTextLinesView#moveRow(DocumentId, boolean)}.
 */
public class WEBUI_DocTextLines_MoveDown extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	@Override
	protected DocTextLinesView getView()
	{
		return DocTextLinesView.cast(super.getView());
	}

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		return checkMoveDownPreconditions(getView(), getSelectedRowIds());
	}

	/**
	 * Exactly one row must be selected, it must be a text row, and a row of any kind must follow it in the
	 * merged order -- otherwise it is already the last row and there is nothing to exchange it with.
	 * <p>
	 * Public so it is directly unit-testable without the {@code JavaProcess} parameter/view-loading machinery,
	 * same rationale as {@link de.metas.ui.web.doc_textlines.process.WEBUI_DocTextLines_InsertAbove}.
	 */
	public static ProcessPreconditionsResolution checkMoveDownPreconditions(
			@NonNull final DocTextLinesView view,
			@NonNull final DocumentIdsSelection selectedRowIds)
	{
		if (selectedRowIds.isMoreThanOneDocumentId())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("only one row can be selected");
		}

		if (selectedRowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final DocumentId rowId = selectedRowIds.getSingleDocumentId();
		final DocTextLinesRow row = view.getById(rowId);
		if (row.isArticleLine())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("article lines are read-only and cannot be moved");
		}

		if (!view.hasNeighbor(rowId, false))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("already the last row");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final DocumentId rowId = getSelectedRowIds().getSingleDocumentId();
		moveDown(getView(), rowId);

		return MSG_OK;
	}

	/** Public so it is directly unit-testable without the {@code JavaProcess} parameter/view-loading machinery. */
	public void moveDown(@NonNull final DocTextLinesView view, @NonNull final DocumentId rowId)
	{
		view.moveRow(rowId, false);
	}
}
