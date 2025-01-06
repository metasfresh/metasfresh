package de.metas.ui.web.pporder.process;

import de.metas.handlingunits.model.I_PP_Order;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.pporder.PPOrderLinesView;
import de.metas.util.Services;
import lombok.NonNull;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderPlanningStatus;

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

class WEBUI_PP_Order_ChangePlanningStatus_Template extends WEBUI_PP_Order_Template implements IProcessPrecondition
{
	private final transient IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);

	private final PPOrderPlanningStatus targetPlanningStatus;

	WEBUI_PP_Order_ChangePlanningStatus_Template(@NonNull final PPOrderPlanningStatus targetPlanningStatus)
	{
		this.targetPlanningStatus = targetPlanningStatus;
	}

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final PPOrderLinesView view = getView();
		final PPOrderPlanningStatus planningStatus = view.getPlanningStatus();
		if (!huPPOrderBL.canChangePlanningStatus(planningStatus, targetPlanningStatus))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not applicable for current status");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		huPPOrderBL.processPlanning(getView().getPpOrderId(), targetPlanningStatus);
		return MSG_OK;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		final PPOrderLinesView ppOrderLinesView = getView();
		ppOrderLinesView.invalidateAll();

		final PPOrderId ppOrderId = ppOrderLinesView.getPpOrderId();
		getViewsRepo().notifyRecordsChangedAsync(I_PP_Order.Table_Name, ppOrderId.getRepoId());
	}
}
