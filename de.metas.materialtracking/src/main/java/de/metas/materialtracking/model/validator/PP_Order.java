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
import org.eevolution.model.I_PP_Order;

import de.metas.materialtracking.IMaterialTrackingBL;
import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.IMaterialTrackingPPOrderBL;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.qualityBasedInvoicing.IMaterialTrackingDocuments;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityBasedInvoicingDAO;
import de.metas.materialtracking.qualityBasedInvoicing.impl.PPOrderQualityCalculator;
import de.metas.materialtracking.spi.impl.listeners.PPOrderMaterialTrackingListener;

@Interceptor(I_PP_Order.class)
public class PP_Order
{
	@Init
	public void init()
	{
		final IMaterialTrackingBL materialTrackingBL = Services.get(IMaterialTrackingBL.class);
		materialTrackingBL.addModelTrackingListener(I_PP_Order.Table_Name, PPOrderMaterialTrackingListener.instance);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_CLOSE })
	public void updateQualityFields(final I_PP_Order ppOrder)
	{
		// Applies only on Quality Inspection orders
		if (!Services.get(IMaterialTrackingPPOrderBL.class).isQualityInspection(ppOrder))
		{
			return;
		}

		// Applies only on those Quality Inspection orders which are linked to a material tracking
		final I_M_Material_Tracking materialTracking = Services.get(IMaterialTrackingDAO.class).retrieveMaterialTrackingForModel(ppOrder);
		if (materialTracking == null)
		{
			return;
		}

		final IQualityBasedInvoicingDAO qualityBasedInvoicingDAO = Services.get(IQualityBasedInvoicingDAO.class);
		final IMaterialTrackingDocuments materialTrackingDocuments = qualityBasedInvoicingDAO.retrieveMaterialTrackingDocuments(materialTracking);

		final PPOrderQualityCalculator calculator = new PPOrderQualityCalculator();
		calculator.update(materialTrackingDocuments);
	}
}
