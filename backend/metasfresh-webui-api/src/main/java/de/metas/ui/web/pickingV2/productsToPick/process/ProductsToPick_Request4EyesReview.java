package de.metas.ui.web.pickingV2.productsToPick.process;

import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.globalaction.GlobalActionEvent;
import de.metas.ui.web.globalaction.GlobalActionEvents;
import de.metas.ui.web.pickingV2.PickingConstantsV2;
import de.metas.ui.web.pickingV2.productsToPick.ProductsToPickView;
import de.metas.ui.web.view.ViewId;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class ProductsToPick_Request4EyesReview extends ProductsToPickViewBasedProcess
{
	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (isReviewProfile())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("already reviewing");
		}

		final ProductsToPickView view = getView();
		if (!view.isEligibleForReview())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not all rows are eligible");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final ProductsToPickView view = getView();
		final ViewId viewId = view.getViewId();
		final GlobalActionEvent openViewEvent = GlobalActionEvents.openView(viewId, PickingConstantsV2.PROFILE_ID_ProductsToPickView_Review);

		getResult().setDisplayQRCode(openViewEvent.toDisplayQRCodeProcessResult());
		return MSG_OK;
	}

}
