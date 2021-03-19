package de.metas.materialtracking.model.validator;

/*
 * #%L
 * de.metas.materialtracking
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.ModelValidator;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPCostCollectorDAO;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;

import org.eevolution.api.PPOrderId;
import de.metas.materialtracking.IMaterialTrackingBL;
import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.spi.impl.listeners.PPCostCollectorMaterialTrackingListener;
import de.metas.util.Services;

@Interceptor(I_PP_Cost_Collector.class)
public class PP_Cost_Collector
{
	@Init
	public void init()
	{
		final IMaterialTrackingBL materialTrackingBL = Services.get(IMaterialTrackingBL.class);
		materialTrackingBL.addModelTrackingListener(I_PP_Cost_Collector.Table_Name, PPCostCollectorMaterialTrackingListener.instance);
	}

	// note that the linking part is explicitly done in HUPPOrderMaterialTrackingBL
	@DocValidate(timings = { ModelValidator.TIMING_AFTER_VOID,
			ModelValidator.TIMING_AFTER_REACTIVATE,
			ModelValidator.TIMING_AFTER_REVERSEACCRUAL,
			ModelValidator.TIMING_AFTER_REVERSECORRECT })
	public void unlinkFromMaterialTracking(final I_PP_Cost_Collector ppCostCollector)
	{
		final IMaterialTrackingBL materialTrackingBL = Services.get(IMaterialTrackingBL.class);
		final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);
		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);
		final IPPCostCollectorDAO ppCostCollectorDAO = Services.get(IPPCostCollectorDAO.class);

		// Applies only on issue collectors
		if (!ppCostCollectorBL.isAnyComponentIssue(ppCostCollector))
		{
			return;
		}

		// Applies only on those Quality Inspection orders which are linked to a material tracking
		final List<I_M_Material_Tracking> materialTrackings = materialTrackingDAO.retrieveMaterialTrackingsForModel(ppCostCollector);
		if (materialTrackings.isEmpty())
		{
			return;
		}

		// unlink
		materialTrackingBL.unlinkModelFromMaterialTrackings(ppCostCollector);

		// also unlink the ppOrder, if this was the last costCollector
		final PPOrderId ppOrderId = PPOrderId.ofRepoId(ppCostCollector.getPP_Order_ID());
		boolean anyCCLeft = false;
		final List<I_PP_Cost_Collector> costCollectors = Services.get(IPPCostCollectorDAO.class).getCompletedOrClosedByOrderId(ppOrderId);

		for (final I_PP_Cost_Collector cc : costCollectors)
		{
			if (cc.getPP_Cost_Collector_ID() == ppCostCollector.getPP_Cost_Collector_ID())
			{
				continue;
			}
			if (ppCostCollectorBL.isAnyComponentIssue(cc))
			{
				anyCCLeft = true;
				break;
			}
		}
		if (!anyCCLeft)
		{
			final I_PP_Order ppOrder = Services.get(IPPOrderDAO.class).getById(ppOrderId);
			materialTrackingBL.unlinkModelFromMaterialTrackings(ppOrder);
		}
	}
	}
