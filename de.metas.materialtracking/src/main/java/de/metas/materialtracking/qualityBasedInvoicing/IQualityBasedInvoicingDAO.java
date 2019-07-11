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

import org.eevolution.model.I_PP_Order;

import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.util.ISingletonService;

public interface IQualityBasedInvoicingDAO extends ISingletonService
{

	IProductionMaterialQuery createInitialQuery();

	/**
	 * Note: shall work for both normal and quality-inspection PP_orders.
	 *
	 * @return the matching materials from the given <code>ppOrder</code>
	 */
	List<IProductionMaterial> retriveProductionMaterials(IProductionMaterialQuery query);

	IMaterialTrackingDocuments retrieveMaterialTrackingDocuments(I_M_Material_Tracking materialTracking);

	/**
	 * @return {@link IMaterialTrackingDocuments} which contains given model (via <code>M_Material_Tracking_Ref</code>); never return <code>null</code>.
	 */
	IMaterialTrackingDocuments retrieveMaterialTrackingDocumentsFor(I_PP_Order model);

	/**
	 *
	 * @param model
	 * @return see {@link #retrieveMaterialTrackingDocumentsFor(Object)}; if manufacturing order was not assigned to a material tracking then <code>null</code> will be returned;
	 */
	IMaterialTrackingDocuments retrieveMaterialTrackingDocumentsOrNullFor(I_PP_Order model);
}
