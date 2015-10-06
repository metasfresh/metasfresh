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


import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.model.I_PP_Cost_Collector;

import de.metas.materialtracking.IMaterialTrackingBL;
import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.MTLinkRequest;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.spi.impl.listeners.PPCostCollectorMaterialTrackingListener;

@Interceptor(I_PP_Cost_Collector.class)
public class PP_Cost_Collector
{
	@Init
	public void init()
	{
		final IMaterialTrackingBL materialTrackingBL = Services.get(IMaterialTrackingBL.class);
		materialTrackingBL.addModelTrackingListener(I_PP_Cost_Collector.Table_Name, PPCostCollectorMaterialTrackingListener.instance);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void linkToMaterialTracking(final I_PP_Cost_Collector ppCostCollector)
	{
		final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);

		// Applies only on issue collectors
		if (!ppCostCollectorBL.isMaterialIssue(ppCostCollector, false))
		{
			return;
		}

		// Applies only on those orders which are linked to a material tracking
		final I_M_Material_Tracking materialTracking = Services.get(IMaterialTrackingDAO.class).retrieveMaterialTrackingForModel(ppCostCollector.getPP_Order());
		if (materialTracking == null)
		{
			return;
		}

		// link
		linkCostCollector(ppCostCollector, materialTracking);

	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_VOID })
	public void unlinkToMaterialTracking(final I_PP_Cost_Collector ppCostCollector)
	{
		final IMaterialTrackingBL materialTrackingBL = Services.get(IMaterialTrackingBL.class);
		final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);

		// Applies only on issue collectors
		if (!ppCostCollectorBL.isMaterialIssue(ppCostCollector, false))
		{
			return;
		}

		// Applies only on those Quality Inspection orders which are linked to a material tracking
		final I_M_Material_Tracking materialTracking = Services.get(IMaterialTrackingDAO.class).retrieveMaterialTrackingForModel(ppCostCollector);
		if (materialTracking == null)
		{
			return;
		}

		// unlink
		materialTrackingBL.unlinkModelFromMaterialTracking(ppCostCollector);

	}

	private void linkCostCollector(final I_PP_Cost_Collector ppCostCollector, final I_M_Material_Tracking materialTracking)
	{

		final IMaterialTrackingBL materialTrackingBL = Services.get(IMaterialTrackingBL.class);
		materialTrackingBL.linkModelToMaterialTracking(
				MTLinkRequest.builder()
						.setModel(ppCostCollector)
						.setMaterialTracking(materialTracking)
						.build());
	}

}
