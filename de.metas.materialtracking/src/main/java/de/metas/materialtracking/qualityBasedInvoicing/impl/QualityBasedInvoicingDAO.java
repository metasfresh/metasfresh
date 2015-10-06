package de.metas.materialtracking.qualityBasedInvoicing.impl;

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

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.eevolution.model.I_PP_Order;

import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.qualityBasedInvoicing.IMaterialTrackingDocuments;
import de.metas.materialtracking.qualityBasedInvoicing.IProductionMaterial;
import de.metas.materialtracking.qualityBasedInvoicing.IProductionMaterialQuery;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityBasedInvoicingDAO;

public class QualityBasedInvoicingDAO implements IQualityBasedInvoicingDAO
{

	@Override
	public IProductionMaterialQuery createInitialQuery()
	{
		return new ProductionMaterialQuery();
	}

	@Override
	public List<IProductionMaterial> retriveProductionMaterials(final IProductionMaterialQuery query)
	{
		final ProductionMaterialQueryExecutor queryExecutor = new ProductionMaterialQueryExecutor(query);
		return queryExecutor.retriveProductionMaterials();
	}

	@Override
	public IMaterialTrackingDocuments retrieveMaterialTrackingDocuments(final I_M_Material_Tracking materialTracking)
	{
		return new MaterialTrackingDocuments(materialTracking);
	}

	@Override
	public IMaterialTrackingDocuments retrieveMaterialTrackingDocumentsForPPOrder(final I_PP_Order ppOrder)
	{
		final IMaterialTrackingDocuments materialTrackingDocuments = retrieveMaterialTrackingDocumentsForPPOrderOrNull(ppOrder);
		if (materialTrackingDocuments == null)
		{
			throw new AdempiereException("@NotFound@ @M_Material_Tracking_ID@"
					+ "\n @PP_Order_ID@: " + ppOrder);
		}
		return materialTrackingDocuments;
	}

	@Override
	public IMaterialTrackingDocuments retrieveMaterialTrackingDocumentsForPPOrderOrNull(final I_PP_Order ppOrder)
	{
		Check.assumeNotNull(ppOrder, "ppOrder not null");

		//
		// Retrieve Material Tracking for given PP_Order
		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);
		final I_M_Material_Tracking materialTracking = materialTrackingDAO.retrieveMaterialTrackingForModel(ppOrder);
		if (materialTracking == null)
		{
			return null;
		}

		return retrieveMaterialTrackingDocuments(materialTracking);
	}
}
