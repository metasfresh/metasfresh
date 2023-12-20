/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.pporder.process;

import de.metas.handlingunits.sourcehu.SourceHUsService;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.pporder.PPOrderLineRowId;
import de.metas.ui.web.pporder.PPOrderLinesView;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.NonNull;
import org.eevolution.api.PPOrderBOMLineId;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class WEBUI_PP_Order_HUEditor_ProcessBase extends HUEditorProcessTemplate
{
	protected final Stream<HUEditorRow> retrieveSelectedAndEligibleHUEditorRows()
	{
		final HUEditorView huEditorView = HUEditorView.cast(super.getView());
		final Stream<HUEditorRow> huEditorRows = huEditorView.streamByIds(getSelectedRowIds());

		return retrieveEligibleHUEditorRows(huEditorRows);
	}

	protected static Stream<HUEditorRow> retrieveEligibleHUEditorRows(@NonNull final Stream<HUEditorRow> inputStream)
	{
		final SourceHUsService sourceHuService = SourceHUsService.get();

		return inputStream
				.filter(HUEditorRow::isHUStatusActive)
				.filter(huRow -> !sourceHuService.isHuOrAnyParentSourceHu(huRow.getHuId()));
	}

	protected Optional<PPOrderLinesView> getPPOrderView()
	{
		final ViewId parentViewId = getView().getParentViewId();
		if (parentViewId == null)
		{
			return Optional.empty();
		}

		final PPOrderLinesView ppOrderView = getViewsRepo().getView(parentViewId, PPOrderLinesView.class);
		return Optional.of(ppOrderView);
	}

	@Nullable
	protected PPOrderBOMLineId getSelectedOrderBOMLineId()
	{
		final DocumentId documentId = getView().getParentRowId();
		if (documentId == null)
		{
			return null;
		}

		return PPOrderLineRowId.fromDocumentId(documentId)
				.getPPOrderBOMLineIdIfApplies()
				.orElse(null);
	}
}
