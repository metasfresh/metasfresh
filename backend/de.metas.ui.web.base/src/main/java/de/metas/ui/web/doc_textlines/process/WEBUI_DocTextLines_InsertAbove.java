package de.metas.ui.web.doc_textlines.process;

import de.metas.doctextline.DocTextLine;
import de.metas.doctextline.DocTextLineRepository;
import de.metas.doctextline.InsertAboveRequest;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.doc_textlines.DocTextLinesView;
import de.metas.ui.web.doc_textlines.InsertAbovePositions;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

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
 * select.
 */
public class WEBUI_DocTextLines_InsertAbove extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	private final DocTextLineRepository docTextLineRepository = SpringContextHolder.instance.getBean(DocTextLineRepository.class);

	@Override
	protected DocTextLinesView getView()
	{
		return DocTextLinesView.cast(super.getView());
	}

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (selectedRowIds.isMoreThanOneDocumentId())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("only one row can be selected");
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
	 * The actual insert-above logic: derives the new row's position from the view's merged ordering, persists
	 * it via {@link DocTextLineRepository#insertAbove}, then adds the resulting row to the view. Public so it
	 * is directly unit-testable without the {@code JavaProcess} parameter/view-loading machinery.
	 *
	 * @param referenceRowId the row to insert above; {@code null} only when the document has no rows at all.
	 */
	public void insertAbove(@NonNull final DocTextLinesView view, @Nullable final DocumentId referenceRowId)
	{
		final InsertAbovePositions positions = view.getInsertAbovePositions(referenceRowId);

		final InsertAboveRequest request = InsertAboveRequest.builder()
				.documentRef(view.getDocumentRef())
				.textLine("")
				.referencePosition(positions.getReferencePosition())
				.previousPosition(positions.getPreviousPosition())
				.articleLineExistsBeforeReferencePosition(positions.isArticleLineExistsBeforeReferencePosition())
				.build();

		final DocTextLine newTextLine = docTextLineRepository.insertAbove(request);

		view.insertRowAbove(referenceRowId, newTextLine);
	}
}
