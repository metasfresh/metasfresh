package de.metas.ui.web.pporder.process;

import org.compiere.Adempiere;

import de.metas.handlingunits.model.I_PP_Order;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.pporder.PPOrderLinesView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.util.Services;

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
	private final IViewsRepository viewsRepo = Adempiere.getBean(IViewsRepository.class);

	private final String targetPlanningStatus;
	

	WEBUI_PP_Order_ChangePlanningStatus_Template(final String targetPlanningStatus)
	{
		this.targetPlanningStatus = targetPlanningStatus;
	}

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final String planningStatus = getView().getPlanningStatus();
		if (!huPPOrderBL.canChangePlanningStatus(planningStatus, targetPlanningStatus))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not applicable for current status");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		huPPOrderBL.processPlanning(targetPlanningStatus, getView().getPP_Order_ID());
		return MSG_OK;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		final PPOrderLinesView ppOrderLinesView = getView();
		ppOrderLinesView.invalidateAll();
		
		final int ppOrderId = ppOrderLinesView.getPP_Order_ID();
		viewsRepo.notifyRecordChanged(I_PP_Order.Table_Name, ppOrderId);
	}
}
