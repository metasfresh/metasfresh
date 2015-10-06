package de.metas.materialtracking.qualityBasedInvoicing;

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

import de.metas.materialtracking.model.I_M_Material_Tracking;

/**
 * Material tracking documents: contains all quality inspection orders together with other relevant documents for a tracking number.
 *
 * @author tsa
 *
 */
public interface IMaterialTrackingDocuments
{
	/**
	 *
	 * @return material tracking; never return null
	 */
	I_M_Material_Tracking getM_Material_Tracking();

	/**
	 * @return all {@link IQualityInspectionOrder}s linked to this material tracking, ordered by the value of {@link IQualityInspectionOrder#getDateOfProduction()}
	 */
	List<IQualityInspectionOrder> getQualityInspectionOrders();

	/**
	 * Gets {@link IQualityInspectionOrder} for given manufacturing order.
	 *
	 * NOTE: the returned {@link IQualityInspectionOrder} it could contain other instance of given manufacturing order (identification is made by ID only)
	 *
	 * @param ppOrder
	 * @return {@link IQualityInspectionOrder}; never return null
	 * @throws AdempiereException if order was not found
	 */
	IQualityInspectionOrder getQualityInspectionOrder(org.eevolution.model.I_PP_Order ppOrder);

	/**
	 * Links given model to material tracking.
	 *
	 * @param model
	 */
	void linkModelToMaterialTracking(Object model);
}
