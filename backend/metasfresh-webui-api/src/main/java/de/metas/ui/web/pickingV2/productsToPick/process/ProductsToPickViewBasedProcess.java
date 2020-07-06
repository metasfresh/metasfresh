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

package de.metas.ui.web.pickingV2.productsToPick.process;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.pickingV2.PickingConstantsV2;
import de.metas.ui.web.pickingV2.productsToPick.ProductsToPickView;
import de.metas.ui.web.pickingV2.productsToPick.rows.ProductsToPickRow;
import de.metas.ui.web.pickingV2.productsToPick.rows.WebuiPickHUResult;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import lombok.NonNull;

import java.util.List;
import java.util.stream.Stream;

public abstract class ProductsToPickViewBasedProcess extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	@Override
	protected abstract ProcessPreconditionsResolution checkPreconditionsApplicable();

	protected final boolean isPickerProfile()
	{
		return getViewProfileId() == null;
	}

	protected final boolean isReviewProfile()
	{
		return PickingConstantsV2.PROFILE_ID_ProductsToPickView_Review.equals(getViewProfileId());
	}

	@Override
	protected final ProductsToPickView getView()
	{
		return ProductsToPickView.cast(super.getView());
	}

	protected final List<ProductsToPickRow> getSelectedRows()
	{
		final DocumentIdsSelection rowIds = getSelectedRowIds();
		return getView()
				.streamByIds(rowIds)
				.collect(ImmutableList.toImmutableList());
	}

	protected final List<ProductsToPickRow> getAllRows()
	{
		return streamAllRows()
				.collect(ImmutableList.toImmutableList());
	}

	protected Stream<ProductsToPickRow> streamAllRows()
	{
		return getView()
				.streamByIds(DocumentIdsSelection.ALL);
	}

	protected void updateViewRowFromPickingCandidate(@NonNull final DocumentId rowId, @NonNull final PickingCandidate pickingCandidate)
	{
		getView().updateViewRowFromPickingCandidate(rowId, pickingCandidate);
	}

	protected void updateViewRowFromPickingCandidate(@NonNull final ImmutableList<WebuiPickHUResult> pickHUResults)
	{
		pickHUResults.forEach(r -> updateViewRowFromPickingCandidate(r.getDocumentId(), r.getPickingCandidate()));
	}
}
